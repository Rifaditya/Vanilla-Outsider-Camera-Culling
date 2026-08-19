// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.vanillaoutsider.culling.util;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.monster.Vex;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.vanillaoutsider.culling.CameraCullingClient;
import net.vanillaoutsider.culling.config.CameraCullingConfig;
import net.vanillaoutsider.culling.config.CullingLevel;

import java.util.List;

public final class CullingRaycastHelper {

    private CullingRaycastHelper() {}

    private static final it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap OCCLUDED_STREAK = new it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap();

    public static void resetState() {
        OCCLUDED_STREAK.clear();
    }

    private static int getRequiredGraceStreak(double distSq) {
        if (distSq > 64.0 * 64.0) {
            return 12; // Far distance (>64m): 12-frame buffer absorbs sub-voxel ridge-peeking jitter
        } else if (distSq > 32.0 * 32.0) {
            return 8; // Medium distance (32-64m): 8-frame buffer
        }
        return 4; // Near distance (<=32m): 4-frame buffer
    }

    private static boolean recordVisible(Entity entity, Vec3 camPos, double distSq, String reason) {
        int prev = OCCLUDED_STREAK.remove(entity.getId());
        int required = getRequiredGraceStreak(distSq);
        if (prev >= required) {
            CullingDiagnosticsHelper.logStateTransition(entity, camPos, false, reason);
        }
        CameraCullingClient.incrementRenderedEntities();
        return false;
    }

    private static boolean recordOccluded(Entity entity, Vec3 camPos, double distSq, String reason) {
        if (OCCLUDED_STREAK.size() > 2048) {
            OCCLUDED_STREAK.clear();
        }
        int streak = OCCLUDED_STREAK.addTo(entity.getId(), 1) + 1;
        int required = getRequiredGraceStreak(distSq);
        if (streak < required) {
            // Distance-scaled grace decay: prevent walking view bobbing and edge grazing flicker
            CameraCullingClient.incrementRenderedEntities();
            return false;
        }
        if (streak == required) {
            CullingDiagnosticsHelper.logStateTransition(entity, camPos, true, reason);
        }
        CameraCullingClient.incrementCulledEntities();
        return true;
    }

    /**
     * Determines whether an entity is fully occluded from the camera position.
     * Returns true if occluded (should NOT render), false if visible (should render).
     */
    public static boolean isEntityOccluded(Entity entity, double camX, double camY, double camZ) {
        if (!CameraCullingConfig.isEnabled()) {
            return false;
        }
        if (entity == null) {
            return false;
        }
        Level level = entity.level();
        if (level == null || !level.isClientSide()) {
            return false;
        }

        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) {
            return false;
        }

        Vec3 camPos = new Vec3(camX, camY, camZ);
        double distSq = camPos.distanceToSqr(entity.getX(), entity.getY(), entity.getZ());

        // 1. Local player and mounted vehicles are never culled
        if (mc.player == entity || mc.player.getVehicle() == entity || entity.hasPassenger(mc.player)) {
            return recordVisible(entity, camPos, distSq, "Player / Vehicle Immunity");
        }

        // 2. Glowing entities must remain visible through blocks
        if (mc.shouldEntityAppearGlowing(entity)) {
            return recordVisible(entity, camPos, distSq, "Glowing Immunity");
        }

        // 3. Boss & Mini-Boss immunity: Ender Dragon, Wither, Warden, modded bosses & elites
        if (BossDetectionHelper.isBossOrMiniBoss(entity)) {
            return recordVisible(entity, camPos, distSq, "Boss Immunity");
        }

        // 4. Client & Server Blacklist immunity (Wolf, Allay, custom mobs)
        if (BlacklistHelper.isBlacklisted(entity)) {
            return recordVisible(entity, camPos, distSq, "Blacklist Immunity");
        }

        CullingLevel cullingLevel = CameraCullingConfig.getLevel();

        // 5. Decorative entity check (e.g. Armor stands / Item frames)
        if (!cullingLevel.shouldCullDecorativeEntities() && (entity instanceof ArmorStand || entity instanceof ItemFrame)) {
            return recordVisible(entity, camPos, distSq, "Decorative Entity Immunity");
        }

        if (distSq < cullingLevel.getMinDistanceSq()) {
            return recordVisible(entity, camPos, distSq, "Proximity Safety Bubble");
        }

        AABB box = entity.getBoundingBox();
        if (box.getSize() <= 0.0 || box.hasNaN()) {
            return recordVisible(entity, camPos, distSq, "Invalid Bounding Box");
        }

        double padding = cullingLevel.getPadding();
        if (padding != 0.0) {
            box = box.inflate(padding);
        }

        Vec3 center = box.getCenter();
        int samplePoints = cullingLevel.getSamplePoints();

        // Sample 1: Top of Entity Head / Bounding Box (Highest priority, completely above ground plane)
        Vec3 top = new Vec3(center.x, box.maxY - 0.05, center.z);
        if (hasLineOfSight(level, camPos, top)) {
            if (CameraCullingConfig.isCullEntitiesBehindEntities()) {
                if (isEntityOccludedByCloserEntities(entity, level, camPos, box, distSq)) {
                    return recordOccluded(entity, camPos, distSq, "Crowd / Mob Overdraw Occlusion");
                }
            }
            return recordVisible(entity, camPos, distSq, "Sightline (Head Top)");
        }

        // Sample 2: Anatomical Eye Position
        Vec3 eyePos = entity.getEyePosition();
        if (hasLineOfSight(level, camPos, eyePos)) {
            if (CameraCullingConfig.isCullEntitiesBehindEntities()) {
                if (isEntityOccludedByCloserEntities(entity, level, camPos, box, distSq)) {
                    return recordOccluded(entity, camPos, distSq, "Crowd / Mob Overdraw Occlusion");
                }
            }
            return recordVisible(entity, camPos, distSq, "Sightline (Eyes)");
        }

        // Sample 3: Upper Torso / Chest (above ground-grazing threshold)
        if (samplePoints >= 3) {
            Vec3 upperTorso = new Vec3(center.x, box.minY + box.getYsize() * 0.70, center.z);
            if (hasLineOfSight(level, camPos, upperTorso)) {
                if (CameraCullingConfig.isCullEntitiesBehindEntities()) {
                    if (isEntityOccludedByCloserEntities(entity, level, camPos, box, distSq)) {
                        return recordOccluded(entity, camPos, distSq, "Crowd / Mob Overdraw Occlusion");
                    }
                }
                return recordVisible(entity, camPos, distSq, "Sightline (Upper Torso)");
            }
        }

        // Sample 4: Center of Mass
        if (samplePoints >= 4) {
            if (hasLineOfSight(level, camPos, center)) {
                if (CameraCullingConfig.isCullEntitiesBehindEntities()) {
                    if (isEntityOccludedByCloserEntities(entity, level, camPos, box, distSq)) {
                        return recordOccluded(entity, camPos, distSq, "Crowd / Mob Overdraw Occlusion");
                    }
                }
                return recordVisible(entity, camPos, distSq, "Sightline (Center)");
            }
        }

        // Sample 5-8: Elevated Perimeter Flanks (for wide entities or high precision)
        if (samplePoints >= 5 || box.getXsize() > 1.0 || box.getZsize() > 1.0) {
            double flankY = box.minY + box.getYsize() * 0.60;
            Vec3 c1 = new Vec3(box.minX + 0.15, flankY, box.minZ + 0.15);
            Vec3 c2 = new Vec3(box.maxX - 0.15, flankY, box.minZ + 0.15);
            Vec3 c3 = new Vec3(box.minX + 0.15, flankY, box.maxZ - 0.15);
            Vec3 c4 = new Vec3(box.maxX - 0.15, flankY, box.maxZ - 0.15);
            if (hasLineOfSight(level, camPos, c1) || hasLineOfSight(level, camPos, c2)
                || hasLineOfSight(level, camPos, c3) || hasLineOfSight(level, camPos, c4)) {
                if (CameraCullingConfig.isCullEntitiesBehindEntities()) {
                    if (isEntityOccludedByCloserEntities(entity, level, camPos, box, distSq)) {
                        return recordOccluded(entity, camPos, distSq, "Crowd / Mob Overdraw Occlusion");
                    }
                }
                return recordVisible(entity, camPos, distSq, "Sightline (Flank)");
            }
        }

        return recordOccluded(entity, camPos, distSq, "Block Occlusion");
    }

    /**
     * Hybrid check: (1) Cluster Density Cap for packed mob pens, (2) Geometric raycast AABB intersection.
     */
    public static boolean isEntityOccludedByCloserEntities(Entity target, Level level, Vec3 camPos, AABB targetBox, double targetDistSq) {
        if (target == null || level == null || camPos == null) {
            return false;
        }

        // 1. Cluster Density Cap in tight 1.5-block sphere
        int maxCluster = CameraCullingConfig.getMaxEntitiesPerCluster();
        AABB clusterBox = target.getBoundingBox().inflate(1.5);
        List<Entity> clusterEntities = level.getEntities(target, clusterBox, e -> e instanceof LivingEntity && !isTransparentOrDecorative(e));
        
        int closerInCluster = 0;
        for (Entity e : clusterEntities) {
            double distSq = camPos.distanceToSqr(e.getX(), e.getY(), e.getZ());
            if (distSq < targetDistSq) {
                closerInCluster++;
                if (closerInCluster >= maxCluster) {
                    return true; // Culled due to cluster density cap
                }
            }
        }

        // 2. Direct Geometric Raycast against closer entities between camera and target
        AABB pathBox = targetBox.minmax(new AABB(camPos.x, camPos.y, camPos.z, camPos.x, camPos.y, camPos.z));
        List<Entity> candidateOccluders = level.getEntities(target, pathBox, e -> {
            if (e == target || isTransparentOrDecorative(e)) return false;
            double d = camPos.distanceToSqr(e.getX(), e.getY(), e.getZ());
            return d < (targetDistSq - 0.25); // Closer to camera than target
        });

        if (candidateOccluders.isEmpty()) {
            return false;
        }

        Vec3 center = targetBox.getCenter();
        Vec3 head = new Vec3(center.x, Math.max(targetBox.minY, targetBox.maxY - 0.1), center.z);
        Vec3 feet = new Vec3(center.x, Math.min(targetBox.maxY, targetBox.minY + 0.1), center.z);

        boolean centerBlocked = false;
        boolean headBlocked = false;
        boolean feetBlocked = false;

        for (Entity occluder : candidateOccluders) {
            AABB occBox = occluder.getBoundingBox();
            if (!centerBlocked && occBox.clip(camPos, center).isPresent()) {
                centerBlocked = true;
            }
            if (!headBlocked && occBox.clip(camPos, head).isPresent()) {
                headBlocked = true;
            }
            if (!feetBlocked && occBox.clip(camPos, feet).isPresent()) {
                feetBlocked = true;
            }
            if (centerBlocked && headBlocked && feetBlocked) {
                return true; // All key sightlines blocked by closer entities
            }
        }

        return false;
    }

    private static boolean isTransparentOrDecorative(Entity entity) {
        if (entity == null) return true;
        if (entity instanceof Vex || entity instanceof ArmorStand || entity instanceof ItemFrame) {
            return true;
        }
        String className = entity.getClass().getSimpleName();
        return className.contains("Slime") || className.contains("Cube");
    }

    /**
     * Determines whether a block entity is completely occluded or encased in solid blocks.
     */
    public static boolean isBlockEntityOccluded(BlockEntity blockEntity, Vec3 cameraPos) {
        if (!CameraCullingConfig.isEnabled()) {
            return false;
        }
        if (blockEntity == null || cameraPos == null) {
            return false;
        }
        Level level = blockEntity.getLevel();
        if (level == null || !level.isClientSide()) {
            return false;
        }

        CullingLevel cullingLevel = CameraCullingConfig.getLevel();
        BlockPos pos = blockEntity.getBlockPos();
        Vec3 center = Vec3.atCenterOf(pos);
        if (cameraPos.distanceToSqr(center) < cullingLevel.getMinDistanceSq()) {
            return false;
        }

        // Fast enclosure check: if surrounded on all 6 faces by opaque solid blocks, it's 100% occluded
        BlockState up = level.getBlockState(pos.above());
        BlockState down = level.getBlockState(pos.below());
        BlockState north = level.getBlockState(pos.north());
        BlockState south = level.getBlockState(pos.south());
        BlockState east = level.getBlockState(pos.east());
        BlockState west = level.getBlockState(pos.west());
        if (up.isSolidRender() && down.isSolidRender() && north.isSolidRender()
            && south.isSolidRender() && east.isSolidRender() && west.isSolidRender()) {
            CameraCullingClient.incrementCulledBlockEntities();
            return true;
        }

        // If conservative LOW mode and not 100% enclosed, do not aggressively cull
        if (!cullingLevel.shouldCullAllBlockEntities()) {
            return false;
        }

        // Raycast check to center
        if (hasLineOfSightToBlock(level, cameraPos, center, pos)) {
            CameraCullingClient.incrementRenderedBlockEntities();
            return false;
        }

        CameraCullingClient.incrementCulledBlockEntities();
        return true;
    }

    public static boolean hasLineOfSight(Level level, Vec3 from, Vec3 to) {
        ClipContext ctx = new ClipContext(from, to, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, CollisionContext.empty());
        BlockHitResult hit = level.clip(ctx);
        if (hit.getType() == HitResult.Type.MISS) {
            return true;
        }

        // Floor / Ground hit recognition: hitting top face of a block at target base height is ground, not a wall
        if (hit.getDirection() == net.minecraft.core.Direction.UP && hit.getLocation().y <= to.y + 0.15) {
            return true;
        }

        // Active Leaves Culling: tree leaves and solid blocks DO occlude; non-solid deco does not
        BlockState hitState = level.getBlockState(hit.getBlockPos());
        boolean isLeaves = hitState.is(net.minecraft.tags.BlockTags.LEAVES);
        boolean isSolid = hitState.canOcclude() && hitState.isSolidRender();
        if (!isLeaves && !isSolid) {
            return true;
        }

        double hitDistSq = from.distanceToSqr(hit.getLocation());
        double targetDistSq = from.distanceToSqr(to);
        return hitDistSq >= (targetDistSq - 0.25);
    }

    public static boolean hasLineOfSightToBlock(Level level, Vec3 from, Vec3 to, BlockPos targetPos) {
        ClipContext ctx = new ClipContext(from, to, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, CollisionContext.empty());
        BlockHitResult hit = level.clip(ctx);
        if (hit.getType() == HitResult.Type.MISS) {
            return true;
        }
        return hit.getBlockPos().equals(targetPos);
    }
}
