// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.vanillaoutsider.culling.util;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.vanillaoutsider.culling.config.CameraCullingConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class CullingDiagnosticsHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(CullingDiagnosticsHelper.class);

    private CullingDiagnosticsHelper() {}

    public static boolean isDebugEnabled() {
        return CameraCullingConfig.isDebugMode() || LOGGER.isDebugEnabled();
    }

    public static void logStateTransition(Entity entity, Vec3 camPos, boolean nowOccluded, String reason) {
        if (!isDebugEnabled()) {
            return;
        }
        if (entity == null) {
            return;
        }

        String entityName = entity.getType().toShortString() + " #" + entity.getId();
        String stateStr = nowOccluded ? "§c[CULLED]§r" : "§a[VISIBLE]§r";
        String logState = nowOccluded ? "[CULLED]" : "[VISIBLE]";

        double dist = camPos != null ? Math.sqrt(camPos.distanceToSqr(entity.getX(), entity.getY(), entity.getZ())) : 0.0;
        String message = String.format(
            "%s %s at (%.1f, %.1f, %.1f) - Dist: %.1fm | Reason: %s",
            logState,
            entityName,
            entity.getX(),
            entity.getY(),
            entity.getZ(),
            dist,
            reason
        );

        LOGGER.info("[CameraCulling Debug] {}", message);

        Minecraft mc = Minecraft.getInstance();
        if (mc.player != null && CameraCullingConfig.isDebugMode()) {
            mc.player.sendSystemMessage(
                Component.literal("§6[CC-Debug]§r " + stateStr + " §e" + entityName + "§7 (dist: §f" + String.format("%.1fm", dist) + "§7) -> §b" + reason)
            );
        }
    }

    public static void logRaycastHit(String sampleName, String blockName, double hitDist, double targetDist, boolean blocked, String details) {
        if (!isDebugEnabled()) {
            return;
        }
        LOGGER.debug(
            "[CameraCulling Raycast] Sample: {} | Block: {} | HitDist: {:.2f}m vs TargetDist: {:.2f}m | Blocked: {} | Details: {}",
            sampleName,
            blockName,
            hitDist,
            targetDist,
            blocked,
            details
        );
    }
}
