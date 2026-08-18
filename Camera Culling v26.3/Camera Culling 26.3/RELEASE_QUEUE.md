# Release Queue: Camera Culling (MC 26.3)

## [1.3.0+26.3] - 2026-08-18
- Added Distance-Based Mob Texture LOD (Decoupled 3-tier OpenGL Mipmap LOD biasing).
- Added `/cameraculling texturlod` and `/cameraculling texturlod range` commands.
- Enabled by default with Boss, Player, and glowing entity exemptions.

## [1.2.0+26.3] - 2026-08-18
- Added Entity-Behind-Entity (Crowd / Mob Overdraw) Culling.
- Added Cluster Density Cap (max 8 mobs per 1.5 blocks).
- Added `/cameraculling entityculling` and `/cameraculling maxcluster` commands.

## [1.1.0+26.3] - 2026-08-18
- Added 4 culling profiles: LOW, MEDIUM, HIGH, SUPER.
- Persistent JSON configuration (`config/camera-culling.json`).
- Added client command `/cameraculling` (status, toggle, set, reload).

## [1.0.0+26.3] - 2026-08-18
- Initial release of Camera Culling for Minecraft 26.3.
- Client-side entity and block entity occlusion culling.
- Automated testing and zero-crash Knot ClassLoader guard.
