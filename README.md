# Camera Culling

**Camera Culling** is a lightweight, purely client-side Fabric performance mod for Minecraft **26.1.2**, **26.2**, and **26.3** that optimizes frame rates and rendering performance by unrendering entities and block entities hidden behind walls, outside your field of view, or buried behind dense crowds of other mobs, while dynamically reducing texture resolution on distant mobs to reduce GPU memory bandwidth.

---

## Key Features

- **Entity Occlusion Culling**: Fast multi-point raytracing against block collision shapes unrenders mobs and entities that are hidden behind walls.
- **Entity-Behind-Entity Culling (Crowd Overdraw)**: Detects when mobs are completely obscured by closer opaque mobs in front of them or packed inside cramming pens (cluster density cap of 8 mobs per 1.5 blocks).
- **Distance-Based Mob Texture LOD (Texture Resolution Reduction)**:
  - Completely independent standalone setting (does not sync with culling profiles).
  - `< 16.0 blocks`: 100% Native full crisp resolution.
  - `16.0 – 32.0 blocks`: Half-resolution texture sampling.
  - `> 32.0 blocks`: Quarter-resolution / low-res mipmap sampling.
  - Significantly reduces VRAM fillrate and memory bandwidth on distant mobs.
- **Block Entity Occlusion Culling**: Skips rendering chests, signs, banners, and skulls that are encased in opaque blocks or blocked by walls.
- **4 Culling Intensity Profiles**:
  - `LOW` (Conservative): 4.0-block safety buffer, 7-point sampling, padded hitboxes.
  - `MEDIUM` (Balanced - Default): 2.0-block safety buffer, 3-point sampling + corner checks.
  - `HIGH` (Aggressive): 1.0-block buffer, fast 2-point sampling, entity-behind-entity culling active.
  - `SUPER` (Extreme): 0.25-block buffer, ultra-fast 1-point center check for potato PCs.
- **In-Game Commands**:
  - `/cameraculling status` — View active level, entity-behind-entity state, texture LOD range, and real-time culled vs rendered counts.
  - `/cameraculling set <low|medium|high|super>` — Change intensity on the fly.
  - `/cameraculling texturlod <true|false>` — Toggle distance texture LOD independently.
  - `/cameraculling texturlod range <near> <far>` — Set custom distance thresholds for half-res and quarter-res textures.
  - `/cameraculling entityculling <true|false|auto>` — Toggle entity-behind-entity culling independently.
  - `/cameraculling maxcluster <1-128>` — Configure maximum mobs rendered per 1.5-block cluster.
  - `/cameraculling toggle` — Toggle culling on or off.
- **Zero Multiplayer Desync**: Purely client-side rendering hooks; world simulation, network packets, and mob ticks remain 100% untouched.
- **Crucial Edge-Case Protections**:
  - Glowing entities, local player, and mounted vehicles are never culled or texture-reduced.
  - Bosses (Ender Dragon, Wither, Warden) are immune to culling and texture reduction.
  - Transparent mobs (Slimes, Magma Cubes, Vexes) never block visibility.

---

## Supported Versions

- **Minecraft 26.1.2** (`+26.1.2`)
- **Minecraft 26.2** (`+26.2`)
- **Minecraft 26.3** (`+26.3`)

---

## License

This project is licensed under the **GNU General Public License v3.0 (GPLv3)**.
