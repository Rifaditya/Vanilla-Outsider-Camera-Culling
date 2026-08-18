// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.vanillaoutsider.culling.util;

import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.vanillaoutsider.culling.config.CameraCullingConfig;

public final class BlacklistHelper {

    private BlacklistHelper() {}

    /**
     * Checks if an entity is blacklisted from culling (either via client preference or server enforcement).
     */
    public static boolean isBlacklisted(Entity entity) {
        if (entity == null) {
            return false;
        }
        EntityType<?> type = entity.getType();
        return isBlacklistedType(type);
    }

    /**
     * Checks if an EntityRenderState is blacklisted from culling.
     */
    public static boolean isBlacklisted(EntityRenderState state) {
        if (state == null || state.entityType == null) {
            return false;
        }
        return isBlacklistedType(state.entityType);
    }

    public static boolean isBlacklistedType(EntityType<?> type) {
        if (type == null) {
            return false;
        }
        String id = type.toShortString();
        return CameraCullingConfig.isEntityBlacklisted(id);
    }
}
