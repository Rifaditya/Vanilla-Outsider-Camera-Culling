// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.vanillaoutsider.culling.mixin;

import net.minecraft.client.renderer.texture.TextureAtlas;
import net.vanillaoutsider.culling.util.AnimationCullingHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TextureAtlas.class)
public abstract class TextureAtlasMixin {

    @Inject(
        method = "cycleAnimationFrames",
        at = @At("HEAD"),
        cancellable = true
    )
    private void onCycleAnimationFrames(CallbackInfo ci) {
        if (AnimationCullingHelper.shouldPauseAtlasAnimation()) {
            ci.cancel();
        }
    }
}
