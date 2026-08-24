// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.vanillaoutsider.culling.util;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.EntityType;
import net.vanillaoutsider.culling.config.CameraCullingConfig;

public final class EntityLodHelper {

    private EntityLodHelper() {}

    /**
     * Determines whether secondary render layers (armor overlays, accessories, micro-attachments)
     * should be rendered on the entity based on distance and immunities.
     * Returns true if layers should be rendered (normal), false if layers should be culled (LOD).
     */
    public static boolean shouldRenderSecondaryLayers(LivingEntityRenderState state) {
        if (!CameraCullingConfig.isEntityDetailLod()) {
            return true;
        }
        if (state == null) {
            return true;
        }

        // Exemptions: Glowing entities, player, bosses & mini-bosses, blacklisted entities
        if (state.appearsGlowing()) {
            return true;
        }

        EntityType<?> type = state.entityType;
        if (type != null && type.toShortString().contains("player")) {
            return true;
        }

        if (BossDetectionHelper.isBossOrMiniBoss(state)) {
            return true;
        }

        if (BlacklistHelper.isBlacklisted(state)) {
            return true;
        }

        double lodDist = CameraCullingConfig.getEntityLodDistance();
        double lodDistSq = lodDist * lodDist;
        return state.distanceToCameraSq <= lodDistSq;
    }

    /**
     * Calculates the LOD-scaled shadow radius for an entity based on distance.
     * Fades smoothly between start distance (e.g. 32m) and far distance (e.g. 48m),
     * returning 0.0f (completely culled shadow) beyond the far distance.
     */
    public static float getLodShadowRadius(LivingEntityRenderState state, float originalRadius) {
        if (!CameraCullingConfig.isEntityDetailLod() || originalRadius <= 0.0f) {
            return originalRadius;
        }
        if (state == null || state.appearsGlowing()) {
            return originalRadius;
        }

        EntityType<?> type = state.entityType;
        if (type != null && type.toShortString().contains("player")) {
            return originalRadius;
        }

        if (BossDetectionHelper.isBossOrMiniBoss(state)) {
            return originalRadius;
        }

        if (BlacklistHelper.isBlacklisted(state)) {
            return originalRadius;
        }

        double startDist = CameraCullingConfig.getEntityLodDistance();
        float scale = calculateShadowScale(state.distanceToCameraSq, startDist, 16.0);
        return originalRadius * scale;
    }

    /**
     * Pure mathematical calculation for distance shadow fade scaling (0.0 to 1.0).
     */
    public static float calculateShadowScale(double distanceToCameraSq, double startDist, double fadeRange) {
        if (distanceToCameraSq <= 0.0 || startDist <= 0.0 || fadeRange <= 0.0) {
            return 1.0f;
        }
        double startDistSq = startDist * startDist;
        if (distanceToCameraSq <= startDistSq) {
            return 1.0f;
        }
        double farDist = startDist + fadeRange;
        double farDistSq = farDist * farDist;
        if (distanceToCameraSq >= farDistSq) {
            return 0.0f;
        }
        double distance = Math.sqrt(distanceToCameraSq);
        double fade = 1.0 - ((distance - startDist) / fadeRange);
        return (float) Math.max(0.0, Math.min(1.0, fade));
    }
}
