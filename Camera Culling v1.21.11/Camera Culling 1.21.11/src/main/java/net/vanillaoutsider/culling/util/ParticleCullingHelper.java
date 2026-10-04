// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.vanillaoutsider.culling.util;

import net.minecraft.client.Camera;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.vanillaoutsider.culling.CameraCullingClient;
import net.vanillaoutsider.culling.config.CameraCullingConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Single-purpose helper utility for culling occluded or subterranean particles
 * behind solid geometry from the player's camera perspective.
 */
public final class ParticleCullingHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(ParticleCullingHelper.class);

    private static final double PROXIMITY_SAFETY_DIST_SQ = 16.0; // 4.0 blocks
    private static final double MAX_PARTICLE_DIST_SQ = 4096.0;   // 64.0 blocks

    private ParticleCullingHelper() {}

    /**
     * Determines whether a particle at (x, y, z) should be culled from rendering.
     *
     * @param x      Particle X position
     * @param y      Particle Y position
     * @param z      Particle Z position
     * @param camera The active rendering camera
     * @param level  The client level
     * @return true if the particle is occluded and should skip extraction/rendering
     */
    public static boolean shouldCullParticle(double x, double y, double z, Camera camera, Level level) {
        if (!CameraCullingConfig.isEnabled() || !CameraCullingConfig.isCullParticles()) {
            return false;
        }

        if (camera == null) {
            return false;
        }

        Vec3 camPos;
        try {
            camPos = camera.position();
        } catch (Throwable t) {
            return false;
        }

        if (camPos == null) {
            return false;
        }

        double dx = x - camPos.x;
        double dy = y - camPos.y;
        double dz = z - camPos.z;
        double distSq = dx * dx + dy * dy + dz * dz;

        // 1. Proximity Safety: Particles within 4m of the player are never culled (0.0001μs)
        if (distSq <= PROXIMITY_SAFETY_DIST_SQ) {
            CameraCullingClient.incrementRenderedParticles();
            return false;
        }

        // 2. Far Distance Cutoff: Cull particles beyond 64 blocks
        if (distSq > MAX_PARTICLE_DIST_SQ) {
            CameraCullingClient.incrementCulledParticles();
            return true;
        }

        if (level == null) {
            return false;
        }

        // 3. Fast Enclosure Check: If the particle block itself is solid
        BlockPos particlePos = BlockPos.containing(x, y, z);
        try {
            if (level.getBlockState(particlePos).isSolidRender()) {
                CameraCullingClient.incrementCulledParticles();
                return true;
            }
        } catch (Throwable ignored) {
        }

        // 4. Line-of-Sight Raycast: Check if a solid block occludes the particle
        Vec3 particleVec = new Vec3(x, y, z);
        ClipContext ctx = new ClipContext(camPos, particleVec, ClipContext.Block.VISUAL, ClipContext.Fluid.NONE, CollisionContext.empty());
        BlockHitResult hit = level.clip(ctx);

        if (hit.getType() == HitResult.Type.MISS) {
            CameraCullingClient.incrementRenderedParticles();
            return false; // Direct clear sightline
        }

        double hitDistSq = camPos.distanceToSqr(hit.getLocation());
        // If the ray hit a solid block before reaching the particle (with 0.35m tolerance), it is occluded
        if (hitDistSq < (distSq - 0.1225)) {
            CameraCullingClient.incrementCulledParticles();
            return true;
        }

        CameraCullingClient.incrementRenderedParticles();
        return false;
    }
}
