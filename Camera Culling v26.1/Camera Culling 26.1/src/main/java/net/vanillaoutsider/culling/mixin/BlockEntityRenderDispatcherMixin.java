// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.vanillaoutsider.culling.mixin;

import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.blockentity.state.SignRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.phys.Vec3;
import net.vanillaoutsider.culling.util.CullingRaycastHelper;
import net.vanillaoutsider.culling.util.SignTextCullingHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockEntityRenderDispatcher.class)
public abstract class BlockEntityRenderDispatcherMixin {

    @Shadow
    private Vec3 cameraPos;

    @Inject(
        method = "tryExtractRenderState(Lnet/minecraft/world/level/block/entity/BlockEntity;FLnet/minecraft/client/renderer/feature/ModelFeatureRenderer$CrumblingOverlay;)Lnet/minecraft/client/renderer/blockentity/state/BlockEntityRenderState;",
        at = @At("HEAD"),
        cancellable = true
    )
    private <E extends BlockEntity, S extends BlockEntityRenderState> void onTryExtractRenderStateHead(
        E blockEntity,
        float partialTicks,
        ModelFeatureRenderer.CrumblingOverlay breakProgress,
        CallbackInfoReturnable<S> cir
    ) {
        if (this.cameraPos != null && CullingRaycastHelper.isBlockEntityOccluded(blockEntity, this.cameraPos)) {
            cir.setReturnValue(null);
        }
    }

    @Inject(
        method = "tryExtractRenderState(Lnet/minecraft/world/level/block/entity/BlockEntity;FLnet/minecraft/client/renderer/feature/ModelFeatureRenderer$CrumblingOverlay;)Lnet/minecraft/client/renderer/blockentity/state/BlockEntityRenderState;",
        at = @At("RETURN")
    )
    private <E extends BlockEntity, S extends BlockEntityRenderState> void onTryExtractRenderStateReturn(
        E blockEntity,
        float partialTicks,
        ModelFeatureRenderer.CrumblingOverlay breakProgress,
        CallbackInfoReturnable<S> cir
    ) {
        S state = cir.getReturnValue();
        if (state instanceof SignRenderState signState && blockEntity instanceof SignBlockEntity sign && this.cameraPos != null) {
            if (SignTextCullingHelper.shouldCullSignTextSide(sign, this.cameraPos, true)) {
                signState.frontText = null;
            }
            if (SignTextCullingHelper.shouldCullSignTextSide(sign, this.cameraPos, false)) {
                signState.backText = null;
            }
        }
    }
}
