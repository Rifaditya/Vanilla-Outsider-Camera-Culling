# 🎫 [BL-CC-004] Fix EntityRendererMixin shouldRender 6-arg Float Parameter Crash on MC 26.3 (GitHub Issue #4)

- **Category**: `[BUGFIX]`
- **Priority**: `[HIGH]`
- **Status**: `✅ RESOLVED`
- **Target Component(s)**: `Camera Culling v26.3/Camera Culling 26.3/src/main/java/net/vanillaoutsider/culling/mixin/EntityRendererMixin.java`, `gradle.properties`
- **Date Added**: 2026-10-11
- **GitHub Reference**: [Issue #4](https://github.com/Rifaditya/Vanilla-Outsider-Camera-Culling/issues/4)

#### ❓ Problem / Context
In Minecraft 26.3, the vanilla method signature for `EntityRenderer.shouldRender` was updated to accept 6 arguments: `(Entity entity, Frustum culler, double camX, double camY, double camZ, float partialTicks)`.
The existing `@Inject` in `EntityRendererMixin.java` under MC 26.3 targets the legacy 5-argument signature without `float partialTicks`.
During Minecraft 26.3 client startup, SpongePowered Mixin fails with an `InvalidInjectionException` (`Critical injection failure: @Inject annotation on onShouldRender could not find any targets matching 'shouldRender' in net.minecraft.class_898`), causing an immediate client launch crash.

#### 💡 Proposed Solution & Technical Specifications
1. In `EntityRendererMixin.java` (`Camera Culling 26.3`), update the method signature to include `float partialTicks`:
   ```java
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
       float partialTicks,
       CallbackInfoReturnable<Boolean> cir
   ) {
       if (!cir.getReturnValueZ()) {
           return;
       }

       if (CullingRaycastHelper.isEntityOccluded(entity, camX, camY, camZ)) {
           cir.setReturnValue(false);
       }
   }
   ```
2. Pre-compile bump in `gradle.properties`: `1.10.5+26.3` -> `1.10.6+26.3`.
3. Compile via `./gradlew build --no-daemon`, verify clean tests, and deploy to launcher test profile (`26.3`).

#### 🧪 Verification & Acceptance Criteria
- [x] Method descriptor in `EntityRendererMixin.java` matches vanilla 26.3 signature with `float partialTicks`.
- [x] `./gradlew test` passes cleanly.
- [x] `./gradlew build` compiles successfully and auto-deploys to `26.3` Modrinth profile.
- [x] Client launches past bootstrap and title screen without MixinApplyError.
