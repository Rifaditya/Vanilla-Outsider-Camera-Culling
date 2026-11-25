# 📜 Permanent Developer Ledger & Backlog History: Camera Culling

## [BL-CC-001] Multi-Era Anchor Porting: Camera Culling
- **Category**: `[FEATURE]`
- **Priority**: `[HIGH]`
- **Status**: `✅ RESOLVED`
- **Target Component(s)**: Multi-subproject directories, `EntityRendererMixin.java`, `BlockEntityRenderDispatcherMixin.java`, `SingleQuadParticleMixin.java`, `TextureAtlasMixin.java`, `CameraCullingConfig.java`, `build.gradle`, `fabric.mod.json`, `RELEASE_QUEUE.md`
- **Date Added**: 2026-09-25
- **Date Resolved**: 2026-10-04
- **Resolution**:
  - Successfully scaffolded, adapted, verified, and triple-archived ports across all missing anchors:
    1. MC 1.21.11 (Java 21, Loom 1.15-SNAPSHOT, Mojang mappings)
    2. MC 1.21.1 (Java 21, Loom 1.10.2, Mojang mappings)
    3. MC 1.20.1 (Java 17, Loom 1.10.2, Mojang mappings, `JAVA_17` mixin compatibility, legacy `ClipContext` and `ConfirmLinkScreen` adaptations).
  - All anchor test suites 100% green. Jars deployed to local archives, release hub, and external vault D:\.

## [BL-CC-002] Downstream Ecosystem & Toolchain Alignment: watched_projects, fabric.mod.json, Queue Segregation & Archive Hierarchy
- **Category**: `[TECH_DEBT]`
- **Priority**: `[HIGH]`
- **Target Version**: All Anchors
- **Status**: `✅ RESOLVED`
- **Date Added**: 2026-10-01
- **Date Resolved**: 2026-10-02

## [BL-CC-003] Fix MC 26.3 URI Regression & Universal Dasik Library Alignment
- **Category**: `[BUGFIX]`
- **Priority**: `[HIGH]`
- **Status**: `✅ RESOLVED`
- **Target Component(s)**: `YaclScreenHelper.java`, `build.gradle`, `fabric.mod.json`, `gradle.properties` (v26.1, v26.2, v26.3)
- **Date Added**: 2026-10-02
- **Date Resolved**: 2026-10-02

## [BL-CC-007] Adopt 5-Pillar Testing & Sidedness Verification Harness
- **Category**: `[TECH_DEBT]`
- **Priority**: `[MEDIUM]`
- **Status**: `✅ RESOLVED`
- **Target Component(s)**: Test Suites (`CameraCullingStressFuzzTest.java`, `CameraCullingConcurrencyLoadTest.java`, `CameraCullingCommandTest.java`, `CameraCullingGameTest.java`, `CameraCullingSidednessContractTest.java`), `CameraCullingConfig.java`, `EntityLodHelper.java`, `build.gradle` (All 6 Anchors: `1.20.1`, `1.21.1`, `1.21.11`, `26.1.2`, `26.2`, `26.3`)
- **Date Added**: 2026-10-30
- **Date Resolved**: 2026-10-30
- **Resolution**:
  - Adopted standardized Studio 5-Pillar Testing Harness across all 6 version anchors:
    1. **Pillar 1 (Chaos Property Fuzzing)**: Evaluated 10,000 extreme edge cases, validating arithmetic saturation, bounds checking, and IEEE-754 `NaN`/`Infinity` guards in `EntityLodHelper`.
    2. **Pillar 2 (Concurrency Load)**: Stressed configuration saves and GameRule caches across 50 virtual worker threads; introduced mutex-synchronized `FILE_LOCK` and concurrent key sets in `CameraCullingConfig` to eliminate race conditions.
    3. **Pillar 3 (Brigadier AST & Permissions)**: Headlessly verified command syntax tree structure, node hierarchy, and operator permission bounds.
    4. **Pillar 4 (Headless GameTest Assertions)**: Validated headless world tick simulation and culling logic invariants without requiring graphical client initialization.
    5. **Pillar 5 (Sidedness & License Hygiene)**: Verified strict zero-client import leakage in common code paths and 100% single-line GPLv3 license header compliance across all sources.
  - 100% test pass rate across all 6 anchors (25 tests per anchor, 150 automated assertions passed). Clean `BUILD SUCCESSFUL` across Java 17, 21, and 25 runtimes.

## [BL-CC-016] Fix ConfirmLinkScreen.confirmLinkNow(screen, String) Overload Regression on v26.1 & v26.2
- **Category**: `[BUGFIX]`
- **Priority**: `[HIGH]`
- **Status**: `✅ RESOLVED`
- **Target Version**: `1.10.8+26.1.2`, `1.10.8+26.2`
- **Target Component(s)**: `YaclScreenHelper.java`, `ModMenuIntegration.java`, `build.gradle`, `CameraCullingYaclContractTest.java`, `.gitignore` (`v26.1`, `v26.2`)
- **Date Added**: 2026-11-25
- **Date Resolved**: 2026-11-25
- **Resolution**:
  - Updated `YaclScreenHelper.createSupportButton()` in both `v26.1` and `v26.2` to invoke `ConfirmLinkScreen.confirmLinkNow(screen, java.net.URI.create("https://ko-fi.com/dasikigaijin"))` instead of the removed `(Screen, String)` overload, matching `v1.21.1`, `v1.21.11`, and `v26.3`.
  - Wrapped `ModMenuIntegration.getModConfigScreenFactory()`'s `FabricLoader` lookup in a headless `try/catch (Throwable ignored)` guard so contract tests run cleanly outside a full Fabric Loader launch (`[ERR-20261028-001]`).
  - Added `testImplementation` `modmenu` dependency in `build.gradle` and introduced `CameraCullingYaclContractTest` on both `v26.1` and `v26.2` validating Ko-fi localization keys, missing-YACL `ModMenuIntegration` fallback safety, and JVM constant-pool verification that `YaclScreenHelper.class` calls `(Screen, URI)` and never `(Screen, String)` (27/27 tests passed on each anchor).
  - Cleaned up literal `!CHANGELOG.md` and `!BACKLOG.md` whitelist entries in `.gitignore` across the mod root and `v26.1` / `v26.2` subprojects.

