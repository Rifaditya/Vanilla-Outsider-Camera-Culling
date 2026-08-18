// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.vanillaoutsider.culling.mixin;

import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.world.entity.Entity;
import net.vanillaoutsider.culling.util.CullingRaycastHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityRenderer.class)
public abstract class EntityRendererMixin<T extends Entity, S extends EntityRenderState> {

    @Inject(
        method = "shouldRender",
        at = @At("RETURN"),
        cancellable = true
    )
    private void onShouldRender(
        T entity,
        Frustum culler,
        double camX,
        double camY,
        double camZ,
        CallbackInfoReturnable<Boolean> cir
    ) {
        if (!cir.getReturnValueZ()) {
            return;
        }

        if (CullingRaycastHelper.isEntityOccluded(entity, camX, camY, camZ)) {
            cir.setReturnValue(false);
        }
    }
}
