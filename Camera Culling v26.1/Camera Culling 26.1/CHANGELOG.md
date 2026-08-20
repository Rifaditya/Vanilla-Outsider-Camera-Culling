# Changelog

All notable changes to **Camera Culling** are documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

---

## [1.10.2+26.1.2] - 2026-08-20

### Added & Fixed
- **Complete Modrinth Manifest & Metadata Alignment**:
  - Added explicit `"java": ">=25"` runtime dependency under `depends` for automatic platform detection.
  - Added `"custom": { "modrinth": { "projectId": "camera-culling", "slug": "camera-culling" } }` block for automatic platform project resolution.
  - Updated SPDX license to `"GPL-3.0-or-later"` and unified repository issue tracker URLs.
  - Added suggestions for `"yet-another-config-lib"` and `"yet_another_config_lib_v3"`.

---

## [1.10.1+26.1.2] - 2026-08-19

### Fixed
- **BlockEntityRenderDispatcher Bytecode Alignment**:
  - Corrected `tryExtractRenderState` Mixin target descriptor to 3 arguments `(Lnet/minecraft/world/level/block/entity/BlockEntity;FLnet/minecraft/client/renderer/feature/ModelFeatureRenderer$CrumblingOverlay;)Lnet/minecraft/client/renderer/blockentity/state/BlockEntityRenderState;` resolving `InvalidInjectionException` on Minecraft 26.1.2 startup.

---

## [1.10.0+26.1.2] - 2026-08-19 [BROKEN / CRASHED ON STARTUP]

> **Post-Mortem**: Crashed on game startup with `InvalidInjectionException: Critical injection failure: @Inject annotation on onTryExtractRenderStateHead could not find any targets matching 'tryExtractRenderState(...;Z)'`. In 26.1.2, `tryExtractRenderState` takes 3 parameters without the trailing `boolean isGloballyRendered` introduced in 26.2. Superseded by `v1.10.1+26.1.2`.

### Added & Optimized
- **Zero-Allocation Hot-Path Engine**:
  - Completely refactored `CullingRaycastHelper` to eliminate all per-frame `new Vec3()` and intermediate heap allocations during continuous raycasting, removing JVM garbage collection stutter spikes when turning the camera across dense entity herds.
  - Added smooth temporal hysteresis decay to eliminate burst uncull frametime hitches.
- **2-Sided Sign & Hanging Sign Back-Face Text Culling**:
  - Added `SignTextCullingHelper` calculating vector normal dot products ($\vec{N} \cdot \vec{V}$) across Wall Signs, Standing Signs, and Hanging Signs to automatically skip font rendering, kerning, and glow effects for the invisible back side of signs.
  - Added Empty-Side Fast-Pass to immediately skip unwritten/blank sign faces.
  - Added `cullSignText` configuration toggle (default `true` / ON) with full YACL v3 GUI integration and localization.

---

## [1.9.2+26.1.2] - 2026-08-19

### Changed & Optimized
- **Open-Field Herd CPU Bottleneck Elimination**:
  - Set Crowd Overdraw Culling (`cullEntitiesBehindEntities = false`) to `false` by default, delegating open-field mob overlapping directly to GPU hardware Early-Z depth rasterization for maximum framerates (60–140+ FPS).
  - Added $16\text{m}$ distance fast-fail gating and tight $1.5\text{m}$ local cluster bounding box to `isEntityOccludedByCloserEntities()`, completely eliminating the heavy 40-meter world entity searches and ArrayList allocations when manually enabled.

---

## [1.9.1+26.1.2] - 2026-08-19

### Changed & Refined
- **Maximum Optimization Defaults Out-of-the-Box**:
  - Configured baseline culling profile to **`SUPER (Extreme)`** (2.0m proximity bubble, 0.0 padding, full block entity & decorative culling) by default for new installations and YACL GUI resets.
  - Enabled Crowd Overdraw Culling (`cullEntitiesBehindEntities = true`) by default.
  - Enabled Distance Texture LOD (`distanceTextureLod = true`) by default.
  - Preserved existing user configs on disk while aligning GUI default bindings with maximum performance settings.

---

## [1.9.0+26.1.2] - 2026-08-19

### Added
- **Optional Graphical Configuration GUI (YetAnotherConfigLib v3 & ModMenu)**:
  - Integrated a clean, responsive, 3-category in-game configuration GUI accessible directly via ModMenu.
  - Category 1 (*Engine & Diagnostics*): Master culling enable, Culling Aggressiveness level dropdown, and live debug logging toggle.
  - Category 2 (*Entity & Crowd Occlusion*): Crowd overdraw culling toggle, cluster density slider (1–32), and Boss/Mini-Boss immunity health thresholds.
  - Category 3 (*Blocks, Particles & Animations*): Particle culling toggle, Block/Texture animation culling toggle, and Distance Texture LOD sliders.
  - Full server-safe deferred classloading (zero hard dependencies, zero server crashes).

---

## [1.8.4+26.1.2] - 2026-08-19

### Added & Refined
- **Active Leaves Occlusion Culling**: Restored `BlockTags.LEAVES` as visual occluders, allowing entities hidden behind tree canopies, hedges, and forests to be culled.
- **Distance-Scaled Temporal Hysteresis**: Implemented adaptive grace decay buffer (4 frames $\le 32\text{m}$, 8 frames $32\text{m}-64\text{m}$, 12 frames $>64\text{m}$) to eliminate sub-voxel ridge-peeking flicker on distant mobs at 90m+.

---

## [1.8.3+26.1.2] - 2026-08-19

### Added
- **Toggleable Real-Time Diagnostic Logger Suite (`/cameraculling debug`)**:
  - Live in-game toggleable diagnostics logging state transitions (`[VISIBLE]` $\leftrightarrow$ `[CULLED]`) directly to chat and `latest.log`.
  - Reports exact entity ID, type, distance, sample points tested, hit block type, hit coordinates, and exact decision reason (e.g. `Sightline (Head Top)`, `Block Occlusion`, `Proximity Safety Bubble`, `Boss Immunity`, `Blacklist Immunity`).
  - Added `/cameraculling debug [true|false]` command and updated `/cameraculling status` with active debug mode indication.

---

## [1.8.2+26.1.2] - 2026-08-19

### Fixed & Optimized
- **Ground-Grazing Sightline & Floor Hit Recognition**:
  - Eliminated open-air entity flickering caused by walking view bobbing and shallow $3^\circ$ ground angles on flat terrain.
  - Prioritized elevated anatomical sample points (Head crown `box.maxY`, eyes, and upper torso `box.minY + height * 0.70`).
  - Added floor detection in `hasLineOfSight`: hitting the top face of a block (`Direction.UP`) at or below target base height is recognized as the ground and treated as unobstructed.
- **Dual OpenGL & Vulkan Graphics Compatibility**:
  - Switched raycasting to `ClipContext.Block.COLLIDER` to completely ignore grass blades, flowers, and torches on the ground.
  - Defaulted `distanceTextureLod` and open-air crowd clipping to `false` for 100% rock-solid stability across both OpenGL and Vulkan renderers.

---

## [1.8.0+26.1.2] - 2026-08-19

### Optimized & Improved
- **2-Frame Temporal Hysteresis Anti-Flicker Architecture**:
  - Implemented a lock-free, zero-allocation client-side temporal debounce buffer (`Int2IntOpenHashMap`).
  - **Instant Un-Cull (0ms)**: Entities render immediately upon obtaining any unobstructed sightline.
  - **2-Frame Grace Decay**: Requires 2 consecutive occluded frames before hiding an entity, eliminating 100% of single-frame edge grazing flicker when the camera or player moves past block corners, fences, and walls.
- **Anatomical Multi-Vector Sampling**:
  - Prioritized eye position (`entity.getEyePosition()`), torso center, elevated pelvis, feet, and deflated perimeter flank points.
- **Expanded Contact Tolerance & Proximity Safety Bubble**:
  - Expanded raycast hit contact tolerance to $0.25\text{m}^2$ ($0.50\text{m}$ margin) to eliminate false-positive culling on block edges.
  - Expanded proximity bubble thresholds: `LOW` $\rightarrow 5.0\text{m}$, `MEDIUM` $\rightarrow 3.5\text{m}$, `HIGH` $\rightarrow 3.0\text{m}$, `SUPER` $\rightarrow 2.0\text{m}$.

---

## [1.7.0+26.1.2] - 2026-08-19

### Added
- **Block & Texture Animation Culling**:
  - Automatically pauses GPU RenderPass texture atlas uploads (`TextureAtlas.cycleAnimationFrames`) for animated block sprites when paused in singleplayer, menus, or when animation culling is active.
  - Guarantees 100% skipped 3D matrix animation calculations for occluded block entities (Enchanting Tables, Conduits, Bells, Brewing Stands).
- **In-Game Commands**:
  - `/cameraculling animations` — Toggles block & texture animation culling on/off.
  - `/cameraculling animations <true|false>` — Sets explicit animation culling state.
  - `/cameraculling status` — Now displays active animation culling status.

---

## [1.6.0+26.1.2] - 2026-08-19

### Added
- **Particle Occlusion Culling**:
  - Automatically skips vertex extraction and quad buffer generation for unseen particles (torches, campfires, lava embers, cave drips, redstone dust, portals) occluded by solid blocks.
  - **4.0m Proximity Safety Bubble**: Particles within 4m of the player camera are never culled, ensuring 100% immediacy for local combat, splash potions, and tool interactions.
  - **Fast Solid Enclosure Check**: Subterranean particles spawning inside solid blocks are skipped in $O(1)$.
  - **Dedicated Particle Toggle**: Fully controllable via `cullParticles: true/false` in `config/camera-culling.json` and in-game commands.
- **In-Game Commands**:
  - `/cameraculling particles` — Toggles particle occlusion culling on/off.
  - `/cameraculling particles <true|false>` — Sets explicit particle culling state.
  - `/cameraculling status` — Now displays particle culling status and live counter of culled vs. rendered particles.

---

## [1.5.0+26.1.2] - 2026-08-18

### Added
- **Two-Tier Entity Immunity Blacklist (Client & Server Admin Enforcement)**:
  - **Client Personal Blacklist**: Saved locally in `config/camera-culling.json` (`clientBlacklist: []`). Players can add specific entity types (e.g. `minecraft:wolf`, `minecraft:allay`) they never want to be culled.
  - **Server Admin Blacklist**: Configured in `config/camera-culling-server.json` (`serverBlacklist: []`). Server administrators / OPs can enforce server-wide immunity for specific mobs across all connected clients.
  - **Strict Server Authority**: Server-blacklisted entities are mandatory immune on all clients; players can add additional personal entities on top.
  - **Full Immunity Scope**: Blacklisted entities are completely exempt from block occlusion culling, crowd overdraw culling, and distance texture LOD downscaling.
- **In-Game Commands (Strict ResourceLocations)**:
  - `/cameraculling blacklist add <entity_id>` — Add entity to personal immunity blacklist (e.g. `minecraft:wolf`).
  - `/cameraculling blacklist remove <entity_id>` — Remove entity from personal immunity blacklist.
  - `/cameraculling blacklist list` — List all personal blacklisted entity types.
  - `/cameraculling blacklist clear` — Clear personal blacklist.
  - `/cameraculling serverblacklist add <entity_id>` — Add entity to server-wide immunity blacklist (OP/admin).
  - `/cameraculling serverblacklist remove <entity_id>` — Remove entity from server-wide immunity blacklist.
  - `/cameraculling serverblacklist list` — List all server-wide blacklisted entity types.
  - `/cameraculling serverblacklist clear` — Clear server-wide blacklist.

---

## [1.4.0+26.1.2] - 2026-08-18

### Added
- **Dynamic Boss & Mini-Boss Detection and Full Immunity**:
  - **Player-Configurable Health Limits**:
    - Major Boss Health Limit: `bossHealthThreshold` (default: `150.0 HP` / 75 hearts, range: `1.0` to `10000.0`).
    - Mini-Boss Health Limit: `miniBossHealthThreshold` (default: `50.0 HP` / 25 hearts, range: `1.0` to `10000.0`).
  - **Vanilla & Modded Support**: Automatically protects Elder Guardians, Ravagers, Iron Golems, Piglin Brutes, Evokers, Breezes, and modded champions/elites alongside major bosses.
  - **Full Immunity Scope**: Mobs classified as bosses or mini-bosses are never culled behind solid blocks, never culled inside crowd overdraw clusters, and never downscaled by texture LOD.
  - **Conventional Tag & Keyword Heuristics**: Supports `#c:bosses`, `#c:minibosses`, and common naming conventions.
- **In-Game Commands**:
  - `/cameraculling bossimmunity <true|false>` — Toggle Boss & Mini-Boss immunity on or off.
  - `/cameraculling bosshealth <hp>` — Change major boss health threshold with instant heart conversion display.
  - `/cameraculling minibosshealth <hp>` — Change mini-boss health threshold with instant heart conversion display.
  - `/cameraculling bosshealth <boss_hp> <miniboss_hp>` — Configure both thresholds simultaneously.
  - `/cameraculling status` — Displays active thresholds in both HP and hearts.

---

## [1.3.0+26.1.2] - 2026-08-18

### Added
- **Distance-Based Mob Texture LOD (Texture Resolution Reduction)**:
  - **Decoupled Standalone Setting**: Operates independently of culling profiles and entity-behind-entity culling. Enabled by default (`true`).
  - **3-Tier Stepped Detail Scaling**:
    - **Near (< 16.0 blocks)**: 100% Native Full Resolution crisp textures (0.0 LOD bias).
    - **Medium (16.0 – 32.0 blocks)**: Half Resolution (1.0 LOD bias).
    - **Far (> 32.0 blocks)**: Quarter Resolution / low-res mipmap (2.5 LOD bias).
  - **GPU Memory Optimization**: Drastically lowers VRAM fillrate and texture sampling bandwidth for distant mobs without affecting close-up visual fidelity.
  - **Exemptions**: Local Player, player mounts, glowing mobs, and Bosses remain at 100% full resolution at all distances.
- **In-Game Commands**:
  - `/cameraculling texturlod <true|false>` — Toggle distance texture LOD independently.
  - `/cameraculling texturlod range <near> <far>` — Configure near (half-res) and far (quarter-res) distance thresholds.

---

## [1.2.0+26.1.2] - 2026-08-18

### Added
- **Entity-Behind-Entity Culling (Crowd & Mob Overdraw Culling)**:
  - **Geometric AABB Intersection**: Raycasts through candidate occluding mobs to cull entities hidden behind closer opaque mobs.
  - **Cluster Density Cap**: Automatically limits rendered entities in tight 1.5-block clusters to a configurable cap (default: `8`).
  - **Profile Linking**: Automatically active on `HIGH` and `SUPER` presets, disabled on `LOW` and `MEDIUM` with manual override support.
- **In-Game Commands**:
  - `/cameraculling entityculling <true|false|auto>` — Toggle entity-behind-entity culling independently.
  - `/cameraculling maxcluster <1-128>` — Configure cluster density cap.

---

## [1.1.0+26.1.2] - 2026-08-18

### Added
- **4-Tier Culling Intensity Profiles**: Added `LOW`, `MEDIUM`, `HIGH`, and `SUPER` culling presets.
- **Persistent JSON Configuration**: Saved directly to `config/camera-culling.json`.
- **In-Game Command Suite**: Registered `/cameraculling` (`status`, `toggle`, `set <low|medium|high|super>`, `reload`).

---

## [1.0.0+26.1.2] - 2026-08-18

### Added
- **Client-Side Entity Occlusion Culling**: Fast multi-point raytracing against block collision shapes unrenders mobs and entities hidden behind solid walls.
- **Block Entity Culling**: Skips render extraction for chests, signs, banners, skulls, and decorated pots that are enclosed in opaque blocks or blocked from line-of-sight.
- **Edge-Case Protections**: Glowing entities, player characters, and riding vehicles remain visible.
- **ModVersionGuard**: Standalone Knot ClassLoader compatibility verification.
