// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.vanillaoutsider.culling.mixin;

import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.renderer.state.level.QuadParticleRenderState;
import net.vanillaoutsider.culling.util.ParticleCullingHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SingleQuadParticle.class)
public abstract class SingleQuadParticleMixin extends Particle {

    protected SingleQuadParticleMixin(ClientLevel level, double x, double y, double z) {
        super(level, x, y, z);
    }

    @Inject(
        method = "extract",
        at = @At("HEAD"),
        cancellable = true
    )
    private void onExtract(
        QuadParticleRenderState state,
        Camera camera,
        float partialTickTime,
        CallbackInfo ci
    ) {
        if (ParticleCullingHelper.shouldCullParticle(this.x, this.y, this.z, camera, this.level)) {
            ci.cancel();
        }
    }
}
