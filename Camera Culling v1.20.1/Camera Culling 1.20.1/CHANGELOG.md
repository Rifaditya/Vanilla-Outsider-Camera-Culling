# Changelog

All notable changes to **Camera Culling** are documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

---

## [1.0.1+1.20.1] - 2026-10-11

### Fixed
- **Production Knot ClassLoader Compatibility**:
  - Purged unremapped reflection guard (`ModVersionGuard`), resolving fatal `ClassNotFoundException` during client initialization in production launchers.

---

## [1.0.0+1.20.1] - 2026-10-04

### Added
- **Initial Minecraft 1.20.1 Ground-Zero Port (Java 17)**:
  - Full client-side occlusion culling architecture adapted for Minecraft 1.20.1 and Java 17 toolchain.
  - Multi-vector sightline raycasting against block collision geometry with zero-allocation fast paths.
  - Enclosed block entity culling via `BlockEntityRenderDispatcher` head injection.
  - Particle occlusion culling with 4m proximity safety bubble and fast solid enclosure checks.
  - Texture atlas animation throttling when paused or in menus.
  - Entity detail LOD gating and linear distance-scaled shadow fading.
  - Boss and mini-boss immunity heuristics with configurable health thresholds.
  - Client personal and server-enforced entity immunity blacklists.
  - In-game Brigadier client command suite (`/cameraculling`) registered via Fabric API client command registration.
  - Optional graphical configuration GUI integrated via YetAnotherConfigLib (YACL v3) and ModMenu.
  - Seamless interoperability and runtime integration with `dasik-library` 1.1.0+1.20.1.
