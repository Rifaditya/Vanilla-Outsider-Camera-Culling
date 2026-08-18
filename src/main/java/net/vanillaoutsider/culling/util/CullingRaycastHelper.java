// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.vanillaoutsider.culling.util;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.decoration.ItemFrame;
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

public final class CullingRaycastHelper {

    private CullingRaycastHelper() {}

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

        // Local player and mounted vehicles are never culled
        if (mc.player == entity || mc.player.getVehicle() == entity || entity.hasPassenger(mc.player)) {
            return false;
        }

        // Glowing entities must remain visible through blocks
        if (mc.shouldEntityAppearGlowing(entity)) {
            return false;
        }

        CullingLevel cullingLevel = CameraCullingConfig.getLevel();

        // Decorative entity check (e.g. Armor stands / Item frames)
        if (!cullingLevel.shouldCullDecorativeEntities() && (entity instanceof ArmorStand || entity instanceof ItemFrame)) {
            return false;
        }

        Vec3 camPos = new Vec3(camX, camY, camZ);
        double distSq = camPos.distanceToSqr(entity.getX(), entity.getY(), entity.getZ());
        if (distSq < cullingLevel.getMinDistanceSq()) {
            return false;
        }

        AABB box = entity.getBoundingBox();
        if (box.getSize() <= 0.0 || box.hasNaN()) {
            return false;
        }

        double padding = cullingLevel.getPadding();
        if (padding != 0.0) {
            box = box.inflate(padding);
        }

        Vec3 center = box.getCenter();
        int samplePoints = cullingLevel.getSamplePoints();

        // Sample 1: Center
        if (hasLineOfSight(level, camPos, center)) {
            CameraCullingClient.incrementRenderedEntities();
            return false;
        }

        // Sample 2: Upper / Head (if level >= 2 points)
        if (samplePoints >= 2) {
            Vec3 head = new Vec3(center.x, Math.max(box.minY, box.maxY - 0.1), center.z);
            if (hasLineOfSight(level, camPos, head)) {
                CameraCullingClient.incrementRenderedEntities();
                return false;
            }
        }

        // Sample 3: Lower / Feet (if level >= 3 points)
        if (samplePoints >= 3) {
            Vec3 feet = new Vec3(center.x, Math.min(box.maxY, box.minY + 0.1), center.z);
            if (hasLineOfSight(level, camPos, feet)) {
                CameraCullingClient.incrementRenderedEntities();
                return false;
            }
        }

        // Sample 4-7: Corners (if level >= 7 points or wide entity)
        if (samplePoints >= 7 || box.getXsize() > 1.2 || box.getZsize() > 1.2) {
            Vec3 c1 = new Vec3(box.minX + 0.1, center.y, box.minZ + 0.1);
            Vec3 c2 = new Vec3(box.maxX - 0.1, center.y, box.minZ + 0.1);
            Vec3 c3 = new Vec3(box.minX + 0.1, center.y, box.maxZ - 0.1);
            Vec3 c4 = new Vec3(box.maxX - 0.1, center.y, box.maxZ - 0.1);
            if (hasLineOfSight(level, camPos, c1) || hasLineOfSight(level, camPos, c2)
                || hasLineOfSight(level, camPos, c3) || hasLineOfSight(level, camPos, c4)) {
                CameraCullingClient.incrementRenderedEntities();
                return false;
            }
        }

        CameraCullingClient.incrementCulledEntities();
        return true;
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
        ClipContext ctx = new ClipContext(from, to, ClipContext.Block.VISUAL, ClipContext.Fluid.NONE, CollisionContext.empty());
        BlockHitResult hit = level.clip(ctx);
        if (hit.getType() == HitResult.Type.MISS) {
            return true;
        }
        double hitDistSq = from.distanceToSqr(hit.getLocation());
        double targetDistSq = from.distanceToSqr(to);
        return hitDistSq >= (targetDistSq - 0.05);
    }

    public static boolean hasLineOfSightToBlock(Level level, Vec3 from, Vec3 to, BlockPos targetPos) {
        ClipContext ctx = new ClipContext(from, to, ClipContext.Block.VISUAL, ClipContext.Fluid.NONE, CollisionContext.empty());
        BlockHitResult hit = level.clip(ctx);
        if (hit.getType() == HitResult.Type.MISS) {
            return true;
        }
        return hit.getBlockPos().equals(targetPos);
    }
}
