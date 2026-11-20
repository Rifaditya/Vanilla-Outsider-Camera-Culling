# 🎫 [BL-CC-014] Fix ParticleCullingHelper ClipContext Null Entity NPE in Anchor 1.20.1

- **Ticket ID**: `[BL-CC-014]`
- **Category**: `[BUGFIX]`
- **Priority**: `[HIGH]`
- **Scope**: `Anchor 1.20.1` (`Camera Culling v1.20.1`)
- **Status**: `✅ RESOLVED`
- **Target Repository / Root**: `Vanilla Outsider Collections\Camera Culling`

---

## 🎯 1. Problem Statement & Player Impact
When rendering particles (e.g. torches, smoke, campfires, running dust) in Minecraft 1.20.1 with Camera Culling installed, the client immediately crashes with:
```
Description: Rendering Particle

java.lang.NullPointerException: Cannot invoke "net.minecraft.class_1297.method_21752()" because "$$0" is null
    at knot//net.minecraft.class_3727.<init>(class_3727.java:42)
    at knot//net.minecraft.class_3726.method_16195(class_3726.java:15)
    at knot//net.minecraft.class_3959.<init>(class_3959.java:28)
    at knot//net.vanillaoutsider.culling.util.ParticleCullingHelper.shouldCullParticle(ParticleCullingHelper.java:92)
    at knot//net.minecraft.class_3940.handler$zzj000$camera-culling$onRender(class_3940.java:533)
```
In `ParticleCullingHelper.java:92`, `new ClipContext(camPos, particleVec, ClipContext.Block.VISUAL, ClipContext.Fluid.NONE, null)` passed `null` for the entity parameter. In Minecraft 1.20.1, passing `null` invokes `CollisionContext.of(null)` $\rightarrow$ `new EntityCollisionContext(null)`, where field initialization invokes `entity.isDescending()` on a null pointer, crashing the particle render loop.

## 🔬 2. Architectural Blueprint & Target Classes
- Target Class: `net.vanillaoutsider.culling.util.ParticleCullingHelper` in `Camera Culling v1.20.1/Camera Culling 1.20.1/src/main/java/`
- Target Method / Call Sites:
  - Line 92 in `shouldCullParticle`: Resolve the active camera entity via `camera.getEntity()` with fallback to `Minecraft.getInstance().cameraEntity` and `Minecraft.getInstance().player`. If null (e.g. headless test or unattached camera), safely return `false` (do not cull) without throwing an exception.
  - Pass the resolved non-null `Entity` into `new ClipContext(...)`.

## ⚓ 3. Multi-Era Anchor Parity Specifications
- **MC 1.20.1**: Resolve active camera entity and guard against null.
- **MC 1.21.1 / 1.21.11 / 26.1 / 26.2 / 26.3**: Verified already using `CollisionContext.empty()`. Anchor 1.20.1 achieves 100% architectural parity and zero crash on particle rendering.

## 🛡️ 4. Anti-Goals (Creep Shield)
1. **Zero Particle Culling Logic Drift**: Do not alter proximity safety bubble distance ($4.0\text{m}$) or far cutoff distance ($64.0\text{m}$).
2. **Zero Overhead Hot-Path Gating**: Maintain $O(1)$ fast returns before raycast calculation.
3. **Zero Anchor Cross-Contamination**: Strictly isolate version bump to MC 1.20.1 (`1.0.4+1.20.1`).

## 🚀 5. Implementation Steps
1. Update `ParticleCullingHelper.java` to resolve the non-null camera entity and pass it to `new ClipContext(...)`.
2. Add regression unit tests in `CameraCullingTest.java` verifying `ParticleCullingHelper.shouldCullParticle` null safety.
3. Bump `mod_version=1.0.4+1.20.1` in `gradle.properties`.
4. Execute `./gradlew test build --no-daemon --console=plain`.
5. Deploy `vanilla-outsider-camera-culling-1.0.4+1.20.1.jar` to Modrinth test profile and purge `1.0.3`.
6. Record in `CHANGELOG.md` and `RELEASE_QUEUE.md`.
7. Mark ticket `✅ RESOLVED`.
