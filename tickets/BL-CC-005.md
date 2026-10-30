# 🎫 [BL-CC-005] Purge ModVersionGuard String Reflection Runtime Crash Across All Anchors (GitHub Issues #3 & #5)

- **Category**: `[BUGFIX]`
- **Priority**: `[HIGH]`
- **Status**: `✅ RESOLVED`
- **Target Component(s)**: `CameraCullingClient.java`, `ModVersionGuard.java` across anchors (`1.20.1`, `1.21.1`, `1.21.11`, `26.1`, `26.2`, `26.3`)
- **Date Added**: 2026-10-11
- **GitHub Reference**: [Issue #3](https://github.com/Rifaditya/Vanilla-Outsider-Camera-Culling/issues/3), [Issue #5](https://github.com/Rifaditya/Vanilla-Outsider-Camera-Culling/issues/5)

#### ❓ Problem / Context
In production Fabric environments using Knot classloading (such as Modrinth App or Prism Launcher), vanilla classes are remapped to Intermediary (e.g. `net.minecraft.class_898` for `EntityRenderer`).
In `CameraCullingClient.java`, `ModVersionGuard.checkClass("Camera Culling", "net.minecraft.client.renderer.entity.EntityRenderer")` executes `Class.forName(String)`.
Because string literals are not remapped by Fabric Loom, `Class.forName` fails with `ClassNotFoundException` at runtime in production builds, throwing a fatal `RuntimeException` ("Minecraft API Mismatch!") and preventing the client from launching.

Reported by `@xbxct` in [Issue #3](https://github.com/Rifaditya/Vanilla-Outsider-Camera-Culling/issues/3) on MC 1.21.1, and confirmed by `@jagerius` in [Issue #5](https://github.com/Rifaditya/Vanilla-Outsider-Camera-Culling/issues/5) on MC 1.20.1 (`1.0.0+1.20.1`).

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
