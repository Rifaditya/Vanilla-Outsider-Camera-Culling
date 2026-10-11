# 📌 Camera Culling Backlog

This file tracks planned features, technical refinements, performance optimizations, and deferred improvements for **Camera Culling**.

---

## 📊 Backlog Summary

| ID | Category | Title | Priority | Target Version | Status |
| :--- | :--- | :--- | :--- | :--- | :--- |
| `[BL-CC-004]` | `[BUGFIX]` | Fix EntityRendererMixin shouldRender 6-arg Float Parameter Crash on MC 26.3 (GitHub Issue #4) | `[HIGH]` | `MC 26.3` | `✅ RESOLVED` |
| `[BL-CC-005]` | `[BUGFIX]` | Purge ModVersionGuard String Reflection Runtime Crash Across All Anchors (GitHub Issue #3) | `[HIGH]` | `All Anchors` | `✅ RESOLVED` |

---

## 🏷 Legend & Status Tags
- **Categories**: `[FEATURE]`, `[REFINEMENT]`, `[BUGFIX]`, `[PERF]`, `[TECH_DEBT]`
- **Priorities**: `[HIGH]` (Critical logic/porting task), `[MEDIUM]` (Quality of life), `[LOW]` (Minor polish)
- **Statuses**: `📌 DEFERRED` (Queued for future work), `🚧 IN_PROGRESS` (Active development), `✅ RESOLVED` (Implemented and verified)

---

## 📝 Detailed Backlog Entries

### [BL-CC-004] Fix EntityRendererMixin shouldRender 6-arg Float Parameter Crash on MC 26.3 (GitHub Issue #4)
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

---

### [BL-CC-005] Purge ModVersionGuard String Reflection Runtime Crash Across All Anchors (GitHub Issue #3)
- **Category**: `[BUGFIX]`
- **Priority**: `[HIGH]`
- **Status**: `✅ RESOLVED`
- **Target Component(s)**: `CameraCullingClient.java`, `ModVersionGuard.java` across anchors (`1.20.1`, `1.21.1`, `1.21.11`, `26.1`, `26.2`, `26.3`)
- **Date Added**: 2026-10-11
- **GitHub Reference**: [Issue #3](https://github.com/Rifaditya/Vanilla-Outsider-Camera-Culling/issues/3)

#### ❓ Problem / Context
In production Fabric environments using Knot classloading (such as Modrinth App or Prism Launcher), vanilla classes are remapped to Intermediary (e.g. `net.minecraft.class_898` for `EntityRenderer`).
In `CameraCullingClient.java`, `ModVersionGuard.checkClass("Camera Culling", "net.minecraft.client.renderer.entity.EntityRenderer")` executes `Class.forName(String)`.
Because string literals are not remapped by Fabric Loom, `Class.forName` fails with `ClassNotFoundException` at runtime in production builds, throwing a fatal `RuntimeException` ("Minecraft API Mismatch!") and preventing the client from launching.

#### 💡 Proposed Solution & Technical Specifications
1. Remove `ModVersionGuard.checkClass(...)` invocation from `CameraCullingClient.java` across all version anchors.
2. Delete `ModVersionGuard.java`.
3. Rely on Fabric Loader's native metadata dependency system (`fabric.mod.json`) and direct bytecode classloading.
4. Pre-compile bump patch versions in `gradle.properties`:
   - Modern anchors (`26.1`, `26.2`, `26.3`): `1.10.5+mc` -> `1.10.6+mc`
   - Older anchors (`1.20.1`, `1.21.1`, `1.21.11`): `1.0.0+mc` -> `1.0.1+mc` (forward-calibrated from published baseline)
5. Rebuild with `./gradlew build --no-daemon` and auto-deploy to respective launcher profiles.

#### 🧪 Verification & Acceptance Criteria
- [x] `ModVersionGuard.checkClass` removed from client initializers across all anchors.
- [x] `ModVersionGuard.java` deleted from all subprojects.
- [x] Clean `./gradlew test` and `./gradlew build` across all 6 anchors.
- [x] Auto-deployed to respective test profiles.
- [x] Game launches cleanly past `onInitializeClient()` in production test instances without `API Mismatch` crash.

---

## 🗺️ Active Atomic Roadmap

Tracking artifact: `camera_culling_backlog_roadmap.md`

| Step # | Goal | Target Anchors | Target Version | Status |
| :---: | :--- | :--- | :--- | :---: |
| **Step 1** | Sovereign Lead MC 26.3 Repair (`[BL-CC-004]` & `[BL-CC-005]`) | `MC 26.3` | `1.10.6+26.3` | ✅ **RESOLVED** |
| **Step 2** | Modern Anchors Parity Sync (`[BL-CC-005]`) | `MC 26.2`, `MC 26.1.2` | `1.10.6+26.2`, `1.10.6+26.1.2` | ✅ **RESOLVED** |
| **Step 3** | Multi-Era Older Anchors Parity Sync (`[BL-CC-005]`) | `MC 1.21.11`, `MC 1.21.1`, `MC 1.20.1` | `1.0.1+1.21.11`, `1.0.1+1.21.1`, `1.0.1+1.20.1` | ✅ **RESOLVED** |
| **Step 4** | Downstream Toolchain Sync, Archives & Remote Git Push | All 6 Anchors | Full Sync | ✅ **RESOLVED** |

