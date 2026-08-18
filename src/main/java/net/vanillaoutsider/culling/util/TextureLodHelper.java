// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.vanillaoutsider.culling.util;

import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.world.entity.EntityType;
import net.vanillaoutsider.culling.config.CameraCullingConfig;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;

public final class TextureLodHelper {

    private TextureLodHelper() {}

    /**
     * Calculates the OpenGL Mipmap LOD bias based on the entity render state distance to camera.
     * Returns 0.0f (Full resolution), 1.0f (Half resolution), or 2.5f (Quarter resolution / low-res mipmap).
     */
    public static float getLodBias(EntityRenderState state) {
        if (!CameraCullingConfig.isDistanceTextureLod()) {
            return 0.0f;
        }
        if (state == null) {
            return 0.0f;
        }

        // Exemptions: Glowing entities, player, bosses & mini-bosses, blacklisted entities
        if (state.appearsGlowing()) {
            return 0.0f;
        }

        EntityType<?> type = state.entityType;
        if (type != null) {
            if (type.toShortString().contains("player")) {
                return 0.0f;
            }
        }

        if (BossDetectionHelper.isBossOrMiniBoss(state)) {
            return 0.0f;
        }

        if (BlacklistHelper.isBlacklisted(state)) {
            return 0.0f;
        }

        return calculateLodBias(
            state.distanceToCameraSq,
            CameraCullingConfig.getDistanceTextureLodStart(),
            CameraCullingConfig.getDistanceTextureLodFar()
        );
    }

    /**
     * Pure calculation helper for distance LOD bias.
     */
    public static float calculateLodBias(double distanceToCameraSq, double startDist, double farDist) {
        if (distanceToCameraSq <= 0.0) {
            return 0.0f;
        }
        double startDistSq = startDist * startDist;
        if (distanceToCameraSq < startDistSq) {
            return 0.0f; // Near (<16m): 100% full resolution
        }
        double farDistSq = farDist * farDist;
        if (distanceToCameraSq < farDistSq) {
            return 1.0f; // Medium (16-32m): 1/2 resolution
        }
        return 2.5f; // Far (>32m): 1/4 resolution / low-res mipmap
    }

    /**
     * Applies the OpenGL texture LOD bias to the current texture unit if an active OpenGL context exists.
     */
    public static void applyLodBias(float bias) {
        if (bias <= 0.0f) {
            return;
        }
        try {
            if (GL.getCapabilities() != null) {
                GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, bias);
            }
        } catch (Throwable ignored) {
            // Headless / Mocked test environment safety
        }
    }

    /**
     * Resets the OpenGL texture LOD bias back to default (0.0f) if an active OpenGL context exists.
     */
    public static void resetLodBias() {
        try {
            if (GL.getCapabilities() != null) {
                GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, 0.0f);
            }
        } catch (Throwable ignored) {
        }
    }
}
