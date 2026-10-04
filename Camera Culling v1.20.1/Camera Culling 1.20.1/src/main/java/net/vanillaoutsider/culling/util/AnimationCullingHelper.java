// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.vanillaoutsider.culling.util;

import net.minecraft.client.Minecraft;
import net.vanillaoutsider.culling.config.CameraCullingConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Helper utility to manage and throttle animated block texture atlas rendering
 * and 3D block entity animations when animations are paused, in menus, or when culling is enabled.
 */
public final class AnimationCullingHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(AnimationCullingHelper.class);

    private AnimationCullingHelper() {}

    /**
     * Determines whether the global texture atlas animation cycle should be paused.
     * When paused in singleplayer or in a modal menu, texture atlas GPU render passes are completely skipped.
     */
    public static boolean shouldPauseAtlasAnimation() {
        if (!CameraCullingConfig.isEnabled() || !CameraCullingConfig.isCullAnimations()) {
            return false;
        }

        Minecraft mc = Minecraft.getInstance();
        if (mc == null) {
            return false;
        }

        // Pause atlas upload if the singleplayer game is paused
        if (mc.isPaused()) {
            return true;
        }

        // When not in a world (main menu / title screen / disconnected), pause block atlas animation
        return mc.level == null || mc.player == null;
    }
}
