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
        if (prev >= required && CullingDiagnosticsHelper.isDebugEnabled()) {
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
        if (streak == required && CullingDiagnosticsHelper.isDebugEnabled()) {
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
        double dx = camX - entity.getX();
        double dy = camY - entity.getY();
        double dz = camZ - entity.getZ();
        double distSq = dx * dx + dy * dy + dz * dz;

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

        double centerX = (box.minX + box.maxX) * 0.5;
        double centerY = (box.minY + box.maxY) * 0.5;
        double centerZ = (box.minZ + box.maxZ) * 0.5;
        int samplePoints = cullingLevel.getSamplePoints();

        // Sample 1: Top of Entity Head / Bounding Box (Highest priority, completely above ground plane)
        double topY = box.maxY - 0.05;
        if (hasLineOfSight(level, camPos, centerX, topY, centerZ)) {
            if (CameraCullingConfig.isCullEntitiesBehindEntities()) {
                if (isEntityOccludedByCloserEntities(entity, level, camPos, box, distSq)) {
                    return recordOccluded(entity, camPos, distSq, "Crowd / Mob Overdraw Occlusion");
                }
            }
            return recordVisible(entity, camPos, distSq, "Sightline (Head Top)");
        }

        // Sample 2: Anatomical Eye Position
        if (hasLineOfSight(level, camPos, entity.getX(), entity.getEyeY(), entity.getZ())) {
            if (CameraCullingConfig.isCullEntitiesBehindEntities()) {
                if (isEntityOccludedByCloserEntities(entity, level, camPos, box, distSq)) {
                    return recordOccluded(entity, camPos, distSq, "Crowd / Mob Overdraw Occlusion");
                }
            }
            return recordVisible(entity, camPos, distSq, "Sightline (Eyes)");
        }

        // Sample 3: Upper Torso / Chest (above ground-grazing threshold)
        if (samplePoints >= 3) {
            double torsoY = box.minY + box.getYsize() * 0.70;
            if (hasLineOfSight(level, camPos, centerX, torsoY, centerZ)) {
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
            if (hasLineOfSight(level, camPos, centerX, centerY, centerZ)) {
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
            double minX = box.minX + 0.15;
            double maxX = box.maxX - 0.15;
            double minZ = box.minZ + 0.15;
            double maxZ = box.maxZ - 0.15;
            if (hasLineOfSight(level, camPos, minX, flankY, minZ) || hasLineOfSight(level, camPos, maxX, flankY, minZ)
                || hasLineOfSight(level, camPos, minX, flankY, maxZ) || hasLineOfSight(level, camPos, maxX, flankY, maxZ)) {
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
     * Safeguarded local cluster check: (1) 16m distance fast-fail, (2) Tight 1.5m local cluster density cap.
     */
    public static boolean isEntityOccludedByCloserEntities(Entity target, Level level, Vec3 camPos, AABB targetBox, double targetDistSq) {
        if (target == null || level == null || camPos == null) {
            return false;
        }

        // Fast-fail: only apply crowd overdraw culling within 16 meters (256.0 sq dist)
        if (targetDistSq > 256.0) {
            return false;
        }

        // 1. Cluster Density Cap in tight 1.5-block sphere (targets dense mob pens / grinders)
        int maxCluster = CameraCullingConfig.getMaxEntitiesPerCluster();
        AABB clusterBox = targetBox.inflate(1.5);
        List<Entity> clusterEntities = level.getEntities(target, clusterBox, e -> e instanceof LivingEntity && !isTransparentOrDecorative(e));
        
        if (clusterEntities.size() < maxCluster) {
            return false;
        }

        int closerInCluster = 0;
        for (Entity e : clusterEntities) {
            double dx = camPos.x - e.getX();
            double dy = camPos.y - e.getY();
            double dz = camPos.z - e.getZ();
            double distSq = dx * dx + dy * dy + dz * dz;
            if (distSq < targetDistSq) {
                closerInCluster++;
                if (closerInCluster >= maxCluster) {
                    return true; // Culled due to cluster density cap in packed mob grinder pen
                }
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
        double centerX = pos.getX() + 0.5;
        double centerY = pos.getY() + 0.5;
        double centerZ = pos.getZ() + 0.5;

        double dx = cameraPos.x - centerX;
        double dy = cameraPos.y - centerY;
        double dz = cameraPos.z - centerZ;
        double distSq = dx * dx + dy * dy + dz * dz;

        if (distSq < cullingLevel.getMinDistanceSq()) {
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
        if (hasLineOfSightToBlock(level, cameraPos, centerX, centerY, centerZ, pos)) {
            CameraCullingClient.incrementRenderedBlockEntities();
            return false;
        }

        CameraCullingClient.incrementCulledBlockEntities();
        return true;
    }

    public static boolean hasLineOfSight(Level level, Vec3 from, double toX, double toY, double toZ) {
        Vec3 to = new Vec3(toX, toY, toZ);
        ClipContext ctx = new ClipContext(from, to, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, CollisionContext.empty());
        BlockHitResult hit = level.clip(ctx);
        if (hit.getType() == HitResult.Type.MISS) {
            return true;
        }

        // Floor / Ground hit recognition: hitting top face of a block at target base height is ground, not a wall
        if (hit.getDirection() == net.minecraft.core.Direction.UP && hit.getLocation().y <= toY + 0.15) {
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
        double dx = from.x - toX;
        double dy = from.y - toY;
        double dz = from.z - toZ;
        double targetDistSq = dx * dx + dy * dy + dz * dz;
        return hitDistSq >= (targetDistSq - 0.25);
    }

    public static boolean hasLineOfSightToBlock(Level level, Vec3 from, double toX, double toY, double toZ, BlockPos targetPos) {
        Vec3 to = new Vec3(toX, toY, toZ);
        ClipContext ctx = new ClipContext(from, to, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, CollisionContext.empty());
        BlockHitResult hit = level.clip(ctx);
        if (hit.getType() == HitResult.Type.MISS) {
            return true;
        }
        return hit.getBlockPos().equals(targetPos);
    }
}
