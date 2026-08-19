# Release Queue: Camera Culling (MC 26.2)

## [1.8.4+26.2] - 2026-08-19
- Restored active leaves occlusion culling (`BlockTags.LEAVES`).
- Implemented distance-scaled temporal hysteresis (4 / 8 / 12 frames).

## [1.8.3+26.2] - 2026-08-19
- Added toggleable real-time diagnostic logger suite.
- Added `/cameraculling debug [true|false]` command.

## [1.8.2+26.2] - 2026-08-19
- Fixed open-air view bobbing ground grazing false occlusion with floor hit recognition.
- Elevated ray sampling (head crown, eyes, upper torso).
- Dual OpenGL and Vulkan stability optimizations.

## [1.8.0+26.2] - 2026-08-19
- 2-Frame Temporal Hysteresis Anti-Flicker Architecture.
- Anatomical Multi-Vector Sampling (Eyes, center, pelvis, feet, deflated perimeter).
- Expanded Contact Tolerance ($0.25\text{m}^2$) and Proximity Safety Bubble ($3.5\text{m}$).

## [1.7.0+26.2] - 2026-08-19
- Added Block & Texture Animation Culling.
- Added `/cameraculling animations [true|false]` command and `cullAnimations` config option.

## [1.6.0+26.2] - 2026-08-19
- Added Particle Occlusion Culling with 4m proximity safety bubble.
- Added `/cameraculling particles [true|false]` command and `cullParticles` config option.

## [1.5.0+26.2] - 2026-08-18
- Added Two-Tier Entity Immunity Blacklist (Client personal list + Server admin enforcement).
- Added `/cameraculling blacklist` and `/cameraculling serverblacklist` subcommands.

## [1.4.0+26.2] - 2026-08-18
- Added Dynamic Boss & Mini-Boss Detection and Full Immunity.
- Added player-configurable health limits (`bossHealthThreshold: 150.0`, `miniBossHealthThreshold: 50.0`).
- Added `/cameraculling bossimmunity`, `/cameraculling bosshealth`, and `/cameraculling minibosshealth` commands.

## [1.3.0+26.2] - 2026-08-18
- Added Distance-Based Mob Texture LOD (Decoupled 3-tier OpenGL Mipmap LOD biasing).
- Added `/cameraculling texturlod` and `/cameraculling texturlod range` commands.

## [1.2.0+26.2] - 2026-08-18
- Added Entity-Behind-Entity (Crowd / Mob Overdraw) Culling.
- Added Cluster Density Cap (max 8 mobs per 1.5 blocks).
- Added `/cameraculling entityculling` and `/cameraculling maxcluster` commands.

## [1.1.0+26.2] - 2026-08-18
- Added 4 culling profiles: LOW, MEDIUM, HIGH, SUPER.
- Persistent JSON configuration (`config/camera-culling.json`).
- Added client command `/cameraculling` (status, toggle, set, reload).

## [1.0.0+26.2] - 2026-08-18
- Initial release of Camera Culling for Minecraft 26.2.
- Client-side entity and block entity occlusion culling.
- Automated testing and zero-crash Knot ClassLoader guard.
