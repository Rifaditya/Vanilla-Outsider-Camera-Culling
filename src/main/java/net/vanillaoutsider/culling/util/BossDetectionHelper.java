// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.vanillaoutsider.culling.util;

import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.vanillaoutsider.culling.config.CameraCullingConfig;

public final class BossDetectionHelper {

    private BossDetectionHelper() {}

    /**
     * Determines whether an entity is a Boss or Mini-Boss based on health thresholds,
     * tags, vanilla classes, or registry identifier keywords.
     */
    public static boolean isBossOrMiniBoss(Entity entity) {
        if (!CameraCullingConfig.isBossImmunity()) {
            return false;
        }
        if (entity == null) {
            return false;
        }

        // 1. Health Threshold check on LivingEntity
        if (entity instanceof LivingEntity living) {
            float maxHp = living.getMaxHealth();
            double miniThreshold = CameraCullingConfig.getMiniBossHealthThreshold();
            if (maxHp >= miniThreshold) {
                return true;
            }
        }

        // 2. Keyword heuristic check on entity type string
        EntityType<?> type = entity.getType();
        if (type != null) {
            String name = type.toShortString().toLowerCase();
            if (isBossOrMiniBossName(name)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Determines whether a client EntityRenderState represents a Boss or Mini-Boss.
     */
    public static boolean isBossOrMiniBoss(EntityRenderState state) {
        if (!CameraCullingConfig.isBossImmunity()) {
            return false;
        }
        if (state == null || state.entityType == null) {
            return false;
        }

        String name = state.entityType.toShortString().toLowerCase();
        return isBossOrMiniBossName(name);
    }

    public static boolean isBossOrMiniBossName(String name) {
        if (name == null || name.isEmpty()) {
            return false;
        }
        return name.contains("dragon")
            || name.contains("wither")
            || name.contains("warden")
            || name.contains("elder_guardian")
            || name.contains("ravager")
            || name.contains("evoker")
            || name.contains("iron_golem")
            || name.contains("piglin_brute")
            || name.contains("breeze")
            || name.contains("boss")
            || name.contains("miniboss")
            || name.contains("mini_boss")
            || name.contains("titan")
            || name.contains("leviathan")
            || name.contains("harbinger")
            || name.contains("monarch")
            || name.contains("behemoth")
            || name.contains("champion")
            || name.contains("elite")
            || name.contains("brute");
    }
}
