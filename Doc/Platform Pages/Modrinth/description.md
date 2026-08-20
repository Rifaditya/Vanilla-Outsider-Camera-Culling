<div align="center">

<img src="https://raw.githubusercontent.com/Rifaditya/Vanilla-Outsider-Camera-Culling/main/Doc/Media/icon.png" alt="Camera Culling Mod Icon" width="180">

</div>
<p align="center">
    <a href="https://modrinth.com/mod/fabric-api"><img src="https://img.shields.io/badge/Requires-Fabric_API-blue?style=for-the-badge&logo=fabric" alt="Requires Fabric API"></a>
    <img src="https://img.shields.io/badge/Language-Java_25-orange?style=for-the-badge&logo=java" alt="Java 25">
    <img src="https://img.shields.io/badge/License-GPLv3-green?style=for-the-badge" alt="License">
    <img src="https://img.shields.io/badge/Minecraft-26.1+-brightgreen?style=for-the-badge" alt="Minecraft 26.1+">
</p>

# 📷 Camera Culling

**Active Version Policy:** I build **1 JAR for 1 Version**. I support active Minecraft versions (**26.1.2**, **26.2**, and **26.3**). Each version is compiled natively with zero compromises.

> **Unrender the unseen. Lightweight, zero-allocation camera occlusion culling, 2-sided sign text culling & distance texture LOD.**

**The Vanilla Problem:** In vanilla Minecraft, the game's renderer extracts render states and draws thousands of entities, block entities, particle quads, and sign text glyphs even when they are hidden behind solid walls, underground in caves, or obscured from your field of view. Furthermore, rendering fully-resolved textures on distant mobs consumes precious GPU fillrate and memory bandwidth.

**Camera Culling** solves this at the rendering root. It performs lightning-fast client-side raycasting and frustum occlusion checks against block collisions to dynamically unrender occluded entities, block entities, particles, and sign text faces. Combined with a smart crowd overdraw defense, distance-based texture LOD mipmap scaling, dynamic boss/mini-boss protections, zero per-frame heap allocations, and an anti-flicker temporal hysteresis buffer, Camera Culling drastically increases frame rates without altering world simulation or network packets.

Part of the **Vanilla Outsider Collection** — mods that refine the vanilla experience with modern standards.

🔗 **Official GitHub Wiki**: [https://github.com/Rifaditya/Vanilla-Outsider-Camera-Culling/wiki](https://github.com/Rifaditya/Vanilla-Outsider-Camera-Culling/wiki)

---

## ✨ Features

### 🧱 Entity Occlusion Culling (Zero-Allocation Raycast Engine)

Never waste GPU cycles rendering mobs you cannot see. Fast multi-point raytracing against solid block collision geometry unrenders entities hidden behind walls, cave ceilings, and terrain.

- **Zero-Allocation Hot-Path Engine**: Passing primitive coordinates directly eliminates intermediate `new Vec3()` heap allocations in continuous frame rendering, removing JVM Young-Gen garbage collection stutter spikes when panning the camera across dense entity herds.
- **Multi-Point Precision**: Evaluates key entity sightlines (head top, anatomical eye height, upper torso, center, and elevated flanks) to guarantee mobs are never culled prematurely when partially visible around corners.
- **Directional Floor Filtering**: Ignores upward block normal collisions (`Direction.UP`) at entity base level, preventing ground-grazing false culling on slopes and hills.
- **Proximity Safety Margin**: Mobs within your close proximity safety bubble are always rendered smoothly without delay.

> [!NOTE]
> **Zero Simulation Impact**: Culling operates strictly on client-side render submission. Entity ticks, server synchronization, sound events, and physics remain 100% active.

### 🛡️ Anti-Flicker Temporal Hysteresis

- **Distance-Scaled Grace Buffer**: Implements an adaptive frame debounce decay ($4\text{ frames near} \le 32\text{m}$, $8\text{ frames medium} \ 32-64\text{m}$, $12\text{ frames far} > 64\text{m}$) that absorbs camera rotation and walking view-bobbing jitter without sudden pop-in.

### 🪧 2-Sided Sign & Hanging Sign Back-Face Text Culling

Signs with text on both sides render font glyphs, kerning tables, and glow outlines on both faces simultaneously—even when looking at only one side.

- **Normal Vector Dot-Product Math**: Calculates the sign face normal vector ($\vec{N} \cdot \vec{V}$) across Wall Signs, Standing Signs, and Hanging Signs to automatically skip font rendering passes for whichever side faces away from the camera.
- **Empty-Side Fast-Pass**: Automatically detects blank/unwritten sign faces and skips text layout immediately with zero math overhead.
- **50% to 100% Draw Call Reduction** on signs in storage warehouses and multiplayer towns.

### ✨ Particle & Animation Occlusion Culling

- **Particle Occlusion**: Raycasts QuadParticle sightlines against visual collision shapes with a 4.0-meter proximity safety bubble around the player's camera.
- **TextureAtlas Animation Freezing**: Freezes 3D block animations and skips off-screen texture atlas frame cycling when the game is paused or modal menus are open.

### 👥 Entity-Behind-Entity Culling (Crowd Overdraw Defense)

Dense mob farms and crowded pens are major FPS killers. Camera Culling detects when mobs are completely obscured behind closer, opaque mobs in front of them.

- **Cluster Density Cap**: Automatically caps rendered entities in tight 1.5-block clusters (default: 8 mobs per cluster), preventing rendering lag in cramming farms.
- **16-Meter Distance Fast-Fail**: Completely bypasses crowd queries beyond 16 meters, ensuring zero CPU overhead in large open-field herds.
- **Decorative & Small Mob Bypass**: Transparent or small entities (Slimes, Magma Cubes, Vexes, Armor Stands) never block sightlines or occlude other mobs.

### 🎨 Distance-Based Mob Texture LOD (Texture Resolution Reduction)

Distant mobs don't need 4K-level crisp textures. Camera Culling features a decoupled, 3-tier OpenGL Mipmap LOD biasing engine that dynamically reduces texture resolution on distant mobs:

- **Near (< 16 blocks)**: 100% Native Full-Resolution crisp textures (0.0 LOD bias).
- **Medium (16 – 32 blocks)**: Half-Resolution texture sampling (1.0 LOD bias).
- **Far (> 32 blocks)**: Quarter-Resolution low-res mipmap sampling (2.5 LOD bias).
- **GPU Fillrate Optimization**: Drastically reduces VRAM fillrate and memory bandwidth on large herds of distant mobs without degrading close-up visual fidelity.

### 🛡️ Two-Tier Entity Immunity Blacklist

Want specific entities to never be culled under any circumstances? Camera Culling provides a robust two-tier blacklist system:

- **Client Personal Blacklist**: Stored locally in `config/camera-culling.json`. Players can blacklist their favorite companions (e.g. `minecraft:wolf`, `minecraft:allay`, `minecraft:cat`).
- **Server Admin Blacklist**: Configured in `config/camera-culling-server.json`. Server operators can enforce server-wide entity immunity across all connected clients.
- **Full Immunity**: Blacklisted entities are 100% exempt from block occlusion culling, crowd overdraw culling, and texture LOD downscaling.

### 👑 Dynamic Boss & Mini-Boss Immunity

Never lose sight of dangerous foes. Camera Culling automatically identifies both vanilla and modded bosses & mini-bosses:

- **Automatic Recognition**: Protects Ender Dragons, Withers, Wardens, Elder Guardians, Ravagers, Iron Golems, Piglin Brutes, Evokers, Breezes, and modded champions/titans.
- **Configurable Health Thresholds**:
  - **Major Boss Threshold**: `bossHealthThreshold` (Default: `150.0 HP` / 75 hearts).
  - **Mini-Boss Threshold**: `miniBossHealthThreshold` (Default: `50.0 HP` / 25 hearts).
- **Absolute Sightline Protection**: Bosses and mini-bosses are never culled behind solid blocks, never culled by crowd caps, and never downscaled by texture LOD.

### 📦 Block Entity Occlusion Culling

Skips render extraction for block entities that are completely encased or occluded:

- **Enclosure Check**: Automatically unrenders chests, signs, banners, skulls, and decorated pots that are fully surrounded on all 6 faces by solid opaque blocks.
- **Line-of-Sight Check**: Skips rendering when blocked from camera view on aggressive culling profiles.

### ⚙️ 4 Culling Intensity Profiles

- **LOW (Conservative)**: 4.0-block safety buffer, 7-point sampling, padded hitboxes.
- **MEDIUM (Balanced)**: 2.0-block safety buffer, 5-point sampling.
- **HIGH (Aggressive)**: 1.0-block safety buffer, fast 3-point sampling, aggressive block entity culling.
- **SUPER (Extreme - Default)**: 0.25-block safety buffer, ultra-fast 2-point check for maximum FPS on modern systems.

---

## 📋 Quick Command Reference

All settings can be inspected and adjusted in-game via the `/cameraculling` command suite:

```sql
/cameraculling status                               → View live statistics, active level, and thresholds
/cameraculling toggle                               → Toggle culling on or off
/cameraculling set <low|medium|high|super>          → Change culling intensity preset
/cameraculling particles [true|false]               → Toggle particle occlusion culling
/cameraculling animations [true|false]              → Toggle block & texture atlas animation culling
/cameraculling crowdculling <true|false>            → Toggle crowd overdraw / entity-behind-entity culling
/cameraculling cluster <1-128>                      → Set cluster density cap for packed mob pens
/cameraculling texturlod <true|false>               → Toggle distance texture LOD independently
/cameraculling texturlod range <near> <far>         → Set custom texture LOD distances (e.g. 16.0 32.0)
/cameraculling bossimmunity <true|false>            → Toggle boss & mini-boss immunity
/cameraculling bosshealth <hp>                      → Set major boss health threshold (e.g. 150)
/cameraculling minibosshealth <hp>                  → Set mini-boss health threshold (e.g. 50)
/cameraculling blacklist add <entity_id>            → Add mob to personal client immunity list (e.g. minecraft:wolf)
/cameraculling blacklist remove <entity_id>         → Remove mob from personal immunity list
/cameraculling blacklist list                       → List all personal blacklisted entities
/cameraculling blacklist clear                      → Clear personal immunity blacklist
/cameraculling serverblacklist add <entity_id>      → Add mob to server-wide admin immunity list (OP)
/cameraculling serverblacklist remove <entity_id>   → Remove mob from server admin immunity list
/cameraculling serverblacklist list                 → List all server blacklisted entities
/cameraculling serverblacklist clear                → Clear server admin blacklist
/cameraculling debug [true|false]                   → Toggle real-time diagnostic tracing in chat & logs
/cameraculling reload                               → Reload configuration from disk
```

---

## ⚙️ Configuration File

Settings are saved directly to `config/camera-culling.json`:

```json
{
  "enabled": true,
  "level": "SUPER",
  "cullEntitiesBehindEntities": false,
  "maxEntitiesPerCluster": 8,
  "distanceTextureLod": true,
  "distanceTextureLodStart": 16.0,
  "distanceTextureLodFar": 32.0,
  "bossImmunity": true,
  "bossHealthThreshold": 150.0,
  "miniBossHealthThreshold": 50.0,
  "cullParticles": true,
  "cullAnimations": true,
  "cullSignText": true,
  "clientBlacklist": [],
  "debugMode": false
}
```

---

## 📦 Installation & Environment

### ⚛️ Environment Support
* [x] **Client-side only**: All functionality is done client-side and is compatible with vanilla servers.
  * [x] Works in singleplayer too
  * [x] Compatible with any multiplayer server
* [ ] **Server-side only**: Server-only installation.
* [ ] **Client and server**: Requires installation on both sides.

### 📥 Install Instructions
1. Install **[Fabric API](https://modrinth.com/mod/fabric-api)**.
2. (Optional) Install **[ModMenu](https://modrinth.com/mod/modmenu)** and **[YetAnotherConfigLib](https://modrinth.com/mod/yacl)** for in-game graphical settings.
3. Download the latest **Camera Culling** JAR for your Minecraft version and place it in your `.minecraft/mods` folder.
4. Launch Minecraft and enjoy smooth, optimized frame rates!

---

## ☕ Support

If you enjoy **Camera Culling** and the **Vanilla Outsider** philosophy, consider fueling the next update!

[![Ko-fi](https://img.shields.io/badge/Ko--fi-Support%20Me-FF5E5B?style=for-the-badge&logo=ko-fi&logoColor=white)](https://ko-fi.com/dasikigaijin/tip)
[![SocioBuzz](https://img.shields.io/badge/SocioBuzz-Local_Support-7BB32E?style=for-the-badge)](https://sociabuzz.com/dasikigaijin/tribe)
[![Saweria](https://img.shields.io/badge/Saweria-Local_Support-FFA500?style=for-the-badge)](https://saweria.co/DasikIgaijinn)

---

## 📜 Credits

| Role | Author |
| :--- | :--- |
| **Creator** | **Dasik** (Rifaditya) |
| **Collection** | Vanilla Outsider |
| **License** | GNU GPLv3 |

---

> [!IMPORTANT]
> **📦 Modpack Permissions & Distribution:** You are free to include this mod in any modpack on any platform. However, the mod itself must be downloaded from its official distribution pages on **Modrinth** or **CurseForge**. Re-uploading or redistributing official JAR files to third-party sites is strictly prohibited.
> 
> **License & Forks:** Since the source code is licensed under **GNU GPLv3**, you are fully permitted to fork the repository, make modifications, build your own versions, and distribute them under the terms of the GPLv3 as distinct projects.

---

<div align="center">

**Made with ❤️ for the Minecraft community**

*Part of the Vanilla Outsider Collection*

</div>
