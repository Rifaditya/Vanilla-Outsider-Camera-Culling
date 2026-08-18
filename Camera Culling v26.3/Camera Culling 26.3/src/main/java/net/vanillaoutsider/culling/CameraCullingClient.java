// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.vanillaoutsider.culling;

import net.fabricmc.api.ClientModInitializer;
import net.vanillaoutsider.culling.command.CameraCullingCommand;
import net.vanillaoutsider.culling.config.CameraCullingConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CameraCullingClient implements ClientModInitializer {
    public static final String MOD_ID = "camera-culling";
    public static final Logger LOGGER = LoggerFactory.getLogger(CameraCullingClient.class);

    private static long culledEntitiesCount = 0;
    private static long renderedEntitiesCount = 0;
    private static long culledBlockEntitiesCount = 0;
    private static long renderedBlockEntitiesCount = 0;

    @Override
    public void onInitializeClient() {
        ModVersionGuard.checkClass("Camera Culling", "net.minecraft.client.renderer.entity.EntityRenderer");
        CameraCullingConfig.load();
        CameraCullingCommand.register();
        LOGGER.info("[Camera Culling] Client-Side Camera Frustum & Occlusion Culling active (Profile: {}).", CameraCullingConfig.getLevel().getDisplayName());
    }

    public static boolean isCullingEnabled() {
        return CameraCullingConfig.isEnabled();
    }

    public static void setCullingEnabled(boolean enabled) {
        CameraCullingConfig.setEnabled(enabled);
    }

    public static boolean isDebugMode() {
        return CameraCullingConfig.isDebugMode();
    }

    public static void setDebugMode(boolean debug) {
        CameraCullingConfig.setDebugMode(debug);
    }

    public static void incrementCulledEntities() {
        culledEntitiesCount++;
    }

    public static void incrementRenderedEntities() {
        renderedEntitiesCount++;
    }

    public static void incrementCulledBlockEntities() {
        culledBlockEntitiesCount++;
    }

    public static void incrementRenderedBlockEntities() {
        renderedBlockEntitiesCount++;
    }

    public static long getCulledEntitiesCount() {
        return culledEntitiesCount;
    }

    public static long getRenderedEntitiesCount() {
        return renderedEntitiesCount;
    }

    public static long getCulledBlockEntitiesCount() {
        return culledBlockEntitiesCount;
    }

    public static long getRenderedBlockEntitiesCount() {
        return renderedBlockEntitiesCount;
    }
}
