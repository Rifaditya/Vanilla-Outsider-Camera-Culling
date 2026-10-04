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
