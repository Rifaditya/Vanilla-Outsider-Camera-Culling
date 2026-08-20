# 📷 Camera Culling

[![Requires Fabric API](https://img.shields.io/badge/Requires-Fabric_API-blue?style=for-the-badge&logo=fabric)](https://modrinth.com/mod/fabric-api)
[![Java 25](https://img.shields.io/badge/Language-Java_25-orange?style=for-the-badge&logo=java)](https://adoptium.net)
[![License: GPLv3](https://img.shields.io/badge/License-GPLv3-green?style=for-the-badge)](https://www.gnu.org/licenses/gpl-3.0)
[![Minecraft 26.1+ - 26.3](https://img.shields.io/badge/Minecraft-26.1.2_|_26.2_|_26.3-brightgreen?style=for-the-badge)](https://github.com/Rifaditya/Vanilla-Outsider-Camera-Culling/releases)

**Camera Culling** is a high-performance, purely client-side Fabric rendering optimization mod for Minecraft **26.1.2**, **26.2**, and **26.3**. It eliminates rendering overhead at the root by unrendering entities, block entities, and particles that are hidden behind walls, unrendering unviewed sign text faces, freezing off-screen texture animations, applying distance-based texture LOD mipmap scaling, and protecting Bosses/Mini-Bosses—all with **zero per-frame heap allocations** and **zero simulation impact**.

Part of the **Vanilla Outsider Collection** — modern enhancements that maintain vanilla design integrity without unnecessary hand-holding or bloat.

🔗 **Official GitHub Wiki**: [https://github.com/Rifaditya/Vanilla-Outsider-Camera-Culling/wiki](https://github.com/Rifaditya/Vanilla-Outsider-Camera-Culling/wiki)

---

## ⚡ Key Optimization Features

- **Entity Occlusion Culling (Zero-Allocation Raycast Engine)**:
  - Multi-point anatomical raytracing against solid block collision geometry unrenders hidden mobs behind terrain, cave walls, and buildings.
  - Zero intermediate `new Vec3()` heap allocations in continuous frame rendering, removing JVM garbage collection stutter spikes when panning camera across dense mob herds.
  - Directional floor hit filtering (`Direction.UP`) eliminates false-positive culling on gentle slopes and uneven ground.
- **Anti-Flicker Temporal Hysteresis**:
  - Distance-scaled grace decay buffer (4 frames near $\le 32\text{m}$, 8 frames medium $32-64\text{m}$, 12 frames far $>64\text{m}$) eliminates camera-grazing and walking view-bobbing flicker.
- **2-Sided Sign & Hanging Sign Back-Face Text Culling**:
  - Normal vector dot-product calculation ($\vec{N} \cdot \vec{V}$) automatically skips font glyph rendering, layout, kerning, and glow effects for the rear side of wall signs, standing signs, and hanging signs.
  - **Empty-Side Fast-Pass**: Blank sign faces with 4 empty lines are skipped immediately with zero math overhead.
- **Particle Occlusion Culling**:
  - Culls particles located behind solid blocks and subterranean geometry, with a 4.0-meter proximity safety bubble around the player's camera.
- **Block & Texture Animation Culling**:
  - Skips off-screen TextureAtlas animation frame uploads and freezes 3D block animations when menus are open or the game is paused.
- **Distance-Based Mob Texture LOD**:
  - Decoupled 3-tier OpenGL Mipmap LOD bias scaling:
    - `< 16.0 blocks`: 100% Native Full-Resolution (0.0 bias).
    - `16.0 – 32.0 blocks`: Half-Resolution sampling (1.0 bias).
    - `> 32.0 blocks`: Quarter-Resolution mipmap sampling (2.5 bias).
  - Drastically reduces VRAM fillrate and memory bandwidth on distant herds.
- **Dynamic Boss & Mini-Boss Immunity**:
  - Automatically identifies vanilla and modded bosses (Ender Dragon, Wither, Warden, Elder Guardian, Ravager, Iron Golem, Piglin Brute, Breeze, and modded champions).
  - Configurable health thresholds: Major Boss (`150.0 HP` / 75 hearts), Mini-Boss (`50.0 HP` / 25 hearts).
  - Bosses are **never culled behind walls**, **never culled by crowd caps**, and **never downscaled by texture LOD**.
- **Two-Tier Immunity Blacklist**:
  - **Client Blacklist**: Custom player whitelists stored locally (`config/camera-culling.json`).
  - **Server Admin Blacklist**: Server-enforced global immunity for multiplayer worlds (`config/camera-culling-server.json`).
- **Block Entity Occlusion Culling**:
  - 6-sided solid block enclosure detection and raycast sightlines skip rendering encased chests, signs, banners, skulls, and decorated pots.
- **Graphical Configuration GUI & In-Game Commands**:
  - Optional YetAnotherConfigLib (YACL v3) & ModMenu configuration screen with 3 organized categories.
  - Comprehensive Brigadier command suite (`/cameraculling status`, `set`, `toggle`, `texturlod`, `bossimmunity`, `bosshealth`, `particles`, `animations`, `blacklist`, `debug`).
- **Toggleable Real-Time Diagnostic Logger**:
  - `/cameraculling debug [true|false]` traces real-time state transitions directly to chat and logs for instant verification.

---

## 🎮 In-Game Commands

| Command | Description |
| :--- | :--- |
| `/cameraculling status` | View active culling level, entity/block/particle counts, texture LOD range, and blacklist stats. |
| `/cameraculling set <low\|medium\|high\|super>` | Switch culling intensity profile on the fly. |
| `/cameraculling toggle` | Toggle camera culling engine on or off. |
| `/cameraculling particles [true\|false]` | Toggle particle occlusion culling. |
| `/cameraculling animations [true\|false]` | Toggle block and texture atlas animation culling. |
| `/cameraculling texturlod <true\|false>` | Toggle distance texture LOD mipmap scaling. |
| `/cameraculling texturlod range <start> <far>` | Set custom distance thresholds for half-res and quarter-res textures (e.g. `16.0 32.0`). |
| `/cameraculling bossimmunity <true\|false>` | Toggle Boss & Mini-Boss sightline immunity. |
| `/cameraculling bosshealth <hp>` | Set Major Boss minimum health threshold (e.g. `150.0`). |
| `/cameraculling minibosshealth <hp>` | Set Mini-Boss minimum health threshold (e.g. `50.0`). |
| `/cameraculling crowdculling <true\|false>` | Toggle crowd overdraw / entity-behind-entity culling. |
| `/cameraculling cluster <1-128>` | Set maximum rendered mobs per 1.5-block cluster (default: `8`). |
| `/cameraculling blacklist <add\|remove\|list\|clear> <id>` | Manage personal client-side immunity blacklist. |
| `/cameraculling serverblacklist <add\|remove\|list\|clear> <id>` | Manage server-enforced immunity blacklist (OP/Admin). |
| `/cameraculling debug [true\|false]` | Toggle real-time chat & log diagnostic tracing. |
| `/cameraculling reload` | Reload configuration from disk. |

---

## 📦 Supported Versions & Multi-Era Matrix

| Minecraft Version | Release Version | Build Tooling | Java Runtime | Release Status |
| :---: | :---: | :---: | :---: | :---: |
| **Minecraft 26.1.2** | `1.10.1+26.1.2` | Loom 1.15.5 | Java 25+ | ✅ Active Release |
| **Minecraft 26.2** | `1.10.0+26.2` | Loom 1.15.5 | Java 25+ | ✅ Active Release |
| **Minecraft 26.3** | `1.10.0+26.3` | Loom 1.15.5 | Java 25+ | ✅ Active Release |

---

## 📄 License

This project is licensed under the **GNU General Public License v3.0 (GPLv3)**.  
Copyright (C) 2026 **Dasik (Rifaditya)**.
