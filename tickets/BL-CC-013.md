# 🎫 [BL-CC-013] Fix ClipContext Null Entity NPE in Anchor 1.20.1

- **Ticket ID**: `[BL-CC-013]`
- **Category**: `[BUGFIX]`
- **Priority**: `[HIGH]`
- **Scope**: `Anchor 1.20.1` (`Camera Culling v1.20.1`)
- **Status**: `✅ RESOLVED`
- **Target Repository / Root**: `Vanilla Outsider Collections\Camera Culling`

---

## 🎯 1. Problem Statement & Player Impact
When launching Minecraft 1.20.1 with Camera Culling installed, joining a world immediately causes a fatal client crash:
```
java.lang.NullPointerException: Cannot invoke "net.minecraft.class_1297.method_21752()" because "$$0" is null
    at knot//net.minecraft.class_3727.<init>(class_3727.java:42)
    at knot//net.minecraft.class_3726.method_16195(class_3726.java:15)
    at knot//net.minecraft.class_3959.<init>(class_3959.java:28)
    at knot//net.vanillaoutsider.culling.util.CullingRaycastHelper.hasLineOfSight(CullingRaycastHelper.java:320)
    at knot//net.vanillaoutsider.culling.util.CullingRaycastHelper.isEntityOccluded(CullingRaycastHelper.java:147)
    at knot//net.minecraft.class_897.handler$zzh000$camera-culling$onShouldRender(class_897.java:533)
```
In MC 1.20.1, `CullingRaycastHelper.java` invoked `new ClipContext(from, to, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, null)`. Passing `null` as the 5th argument calls the `(Vec3, Vec3, Block, Fluid, Entity)` constructor overload, which in 1.20.1 executes `CollisionContext.of(null)` $\rightarrow$ `new EntityCollisionContext(null)`. `EntityCollisionContext` assumes a non-null entity and invokes `entity.isDescending()` on field initialization, throwing an uncatchable `NullPointerException` during entity rendering.

## 🔬 2. Architectural Blueprint & Target Classes
- Target Class: `net.vanillaoutsider.culling.util.CullingRaycastHelper` in `Camera Culling v1.20.1/Camera Culling 1.20.1/src/main/java/`
- Target Method / Call Sites:
  - Line 320 in `hasLineOfSight`: replace `null` with `CollisionContext.empty()`
  - Line 349 in `hasLineOfSightToBlock`: replace `null` with `CollisionContext.empty()`
- Required Import:
  - `import net.minecraft.world.phys.shapes.CollisionContext;`

## ⚓ 3. Multi-Era Anchor Parity Specifications
- **MC 1.20.1**: Replace `null` with `CollisionContext.empty()`.
- **MC 1.21.1 / 1.21.11 / 26.1 / 26.2 / 26.3**: Verified already using `CollisionContext.empty()`. Anchor 1.20.1 achieves 100% architectural parity with modern anchors.

## 🛡️ 4. Anti-Goals (Creep Shield)
1. **Zero Mixin Mutation**: Do not alter `EntityRendererMixin` or occlusion gating logic.
2. **Zero Config Mutation**: Do not alter config options or default culling behavior.
3. **Zero Anchor Cross-Contamination**: Strictly isolate version bump to MC 1.20.1 (`1.0.3+1.20.1`).

## 🚀 5. Implementation Steps
1. Add `import net.minecraft.world.phys.shapes.CollisionContext;` to `CullingRaycastHelper.java` in 1.20.1.
2. Update line 320 and line 349 to pass `CollisionContext.empty()`.
3. Add a dedicated regression unit test in `CameraCullingTest.java` verifying `hasLineOfSight` with `CollisionContext.empty()`.
4. Bump `mod_version=1.0.3+1.20.1` in `gradle.properties`.
5. Execute `./gradlew test build --no-daemon --console=plain`.
6. Deploy `vanilla-outsider-camera-culling-1.0.3+1.20.1.jar` to Modrinth test profile.
7. Record in `CHANGELOG.md` and `RELEASE_QUEUE.md`.
8. Mark ticket `✅ RESOLVED`.
