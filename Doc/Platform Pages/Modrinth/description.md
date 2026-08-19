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

**Active Version Policy:** I build **1 JAR for 1 Version**. I only update and maintain the latest active Minecraft version (e.g. when 26.3 is released, 26.2 is retired). No backports or legacy version maintenance. Please do not ask.

> **Unrender the unseen. Lightweight, high-performance camera occlusion culling & distance texture LOD.**

**The Vanilla Problem:** In vanilla Minecraft, the game's renderer extracts render states and draws thousands of entities and block entities even when they are completely hidden behind solid walls, buried in deep underground caves, or smothered behind dense walls of other mobs. Furthermore, rendering fully-resolved textures on distant mobs consumes precious GPU fillrate and VRAM bandwidth.

**Camera Culling** solves this at the rendering root. It performs lightning-fast client-side raycasting and frustum occlusion checks against block collisions to dynamically unrender occluded entities and block entities. Combined with a smart mob crowd overdraw defense, distance-based texture LOD mipmap scaling, dynamic boss/mini-boss protections, and a two-tier immunity blacklist, Camera Culling drastically increases frame rates without altering world simulation or network packets.

Part of the **Vanilla Outsider Collection** — mods that refine the vanilla experience with modern standards.

---

## ✨ Features

### 🧱 Entity Occlusion Culling (Solid Block Sightlines)

Never waste GPU cycles rendering mobs you cannot see. Fast multi-point raytracing against solid block collision geometry unrenders entities hidden behind walls, cave ceilings, and terrain.

- **Multi-Point Precision**: Evaluates key entity sightlines (center, head, feet, and bounding box corners) to guarantee mobs are never culled prematurely when partially visible around corners.
- **Safety Buffers**: Incorporates a proximity safety margin so mobs near your crosshairs or close to your camera are always rendered smoothly.

> [!NOTE]
> **Zero Simulation Impact**: Culling operates strictly on client-side render submission. Entity ticks, server synchronization, sound events, and physics remain 100% active.

### 👥 Entity-Behind-Entity Culling (Crowd Overdraw Defense)

Dense mob farms and crowded pens are major FPS killers. Camera Culling detects when mobs are completely obscured behind closer, opaque mobs in front of them.

- **Geometric Raycast AABB Clipping**: Projects sightlines through candidate foreground mobs to cull entities entirely hidden in the crowd.
- **Cluster Density Cap**: Automatically caps rendered entities in tight 1.5-block clusters (default: 8 mobs per cluster), preventing rendering lag in cramming farms.
- **Decorative & Small Mob Bypass**: Transparent or small entities (Slimes, Magma Cubes, Vexes, Armor Stands) never block sightlines or occlude other mobs.

> [!TIP]
> **Profile Linking**: Entity-behind-entity culling is active by default on `HIGH` and `SUPER` presets, and can be toggled independently via `/cameraculling entityculling <true|false|auto>`.

### 🎨 Distance-Based Mob Texture LOD (Texture Resolution Reduction)

Distant mobs don't need 4K-level crisp textures. Camera Culling features a decoupled, 3-tier OpenGL Mipmap LOD biasing engine that dynamically reduces texture resolution on distant mobs:

- **Near (< 16 blocks)**: 100% Native Full-Resolution crisp textures (0.0 LOD bias).
- **Medium (16 – 32 blocks)**: Half-Resolution texture sampling (1.0 LOD bias).
- **Far (> 32 blocks)**: Quarter-Resolution low-res mipmap sampling (2.5 LOD bias).
- **GPU Fillrate Optimization**: Drastically reduces VRAM fillrate and memory bandwidth on large herds of distant mobs without degrading close-up visual fidelity.

> [!NOTE]
> **Standalone Setting**: Texture LOD operates independently of culling presets. Configure custom thresholds anytime via `/cameraculling texturlod range <near> <far>`.

### 🛡️ Two-Tier Entity Immunity Blacklist

Want specific entities to never be culled under any circumstances? Camera Culling provides a robust two-tier blacklist system:

- **Client Personal Blacklist**: Stored locally in `config/camera-culling.json`. Players can blacklist their favorite companions (e.g. `minecraft:wolf`, `minecraft:allay`, `minecraft:cat`).
- **Server Admin Blacklist**: Configured in `config/camera-culling-server.json`. Server operators can enforce server-wide entity immunity across all connected clients.
- **Full Immunity**: Blacklisted entities are 100% exempt from block occlusion culling, crowd overdraw culling, and texture LOD downscaling.

> [!TIP]
> **Command Management**: Add or remove entities in-game using `/cameraculling blacklist <add|remove|list|clear> <id>` or `/cameraculling serverblacklist <add|remove|list|clear> <id>`.

### 👑 Dynamic Boss & Mini-Boss Immunity

Never lose sight of dangerous foes. Camera Culling automatically identifies both vanilla and modded bosses & mini-bosses:

- **Automatic Recognition**: Protects Ender Dragons, Withers, Wardens, Elder Guardians, Ravagers, Iron Golems, Piglin Brutes, Evokers, Breezes, and modded champions/titans.
- **Configurable Health Thresholds**:
  - **Major Boss Threshold**: `bossHealthThreshold` (Default: `150.0 HP` / 75 hearts).
  - **Mini-Boss Threshold**: `miniBossHealthThreshold` (Default: `50.0 HP` / 25 hearts).
- **Absolute Sightline Protection**: Bosses and mini-bosses are never culled behind solid blocks, never culled by crowd caps, and never downscaled by texture LOD.

> [!NOTE]
> **Heart Conversion Display**: Adjust thresholds in-game with instant heart feedback via `/cameraculling bosshealth <hp>` and `/cameraculling minibosshealth <hp>`.

### 📦 Block Entity Occlusion Culling

Skips render extraction for block entities that are completely encased or occluded:

- **Enclosure Check**: Automatically unrenders chests, signs, banners, skulls, and decorated pots that are fully surrounded on all 6 faces by solid opaque blocks.
- **Line-of-Sight Check**: Skips rendering when blocked from camera view on aggressive culling profiles.

### ⚙️ 4 Culling Intensity Profiles

Choose the ideal balance of performance and visual fidelity:

- **LOW (Conservative)**: 4.0-block safety buffer, 7-point sampling, padded hitboxes.
- **MEDIUM (Balanced - Default)**: 2.0-block safety buffer, 3-point sampling + corner checks.
- **HIGH (Aggressive)**: 1.0-block safety buffer, fast 2-point sampling, entity-behind-entity culling active, aggressive block entity culling.
- **SUPER (Extreme / Potato PC)**: 0.25-block safety buffer, ultra-fast 1-point center check for maximum FPS on low-end hardware.

### 🎮 Zero Multiplayer Desync (100% Client-Side)

Camera Culling hooks strictly into the client render pipeline (`EntityRenderer`, `BlockEntityRenderDispatcher`, `LivingEntityRenderer`). It sends zero network packets, requires no server-side installation, and never interferes with mob AI, spawning, or physics.

---

## 📋 Quick Command Reference

All settings can be inspected and adjusted in-game via the `/cameraculling` command suite:

```sql
/cameraculling status                               → View live statistics, active level, and thresholds
/cameraculling toggle                               → Toggle culling on or off
/cameraculling set <low|medium|high|super>          → Change culling intensity preset
/cameraculling blacklist add <entity_id>            → Add mob to personal client immunity list (e.g. minecraft:wolf)
/cameraculling blacklist remove <entity_id>         → Remove mob from personal immunity list
/cameraculling blacklist list                       → List all personal blacklisted entities
/cameraculling serverblacklist add <entity_id>      → Add mob to server-wide admin immunity list (OP)
/cameraculling texturlod <true|false>               → Toggle distance texture LOD independently
/cameraculling texturlod range <near> <far>         → Set custom texture LOD distances (e.g. 16.0 32.0)
/cameraculling bossimmunity <true|false>            → Toggle boss & mini-boss immunity
/cameraculling bosshealth <hp>                      → Set major boss health threshold (e.g. 150)
/cameraculling minibosshealth <hp>                  → Set mini-boss health threshold (e.g. 50)
/cameraculling entityculling <true|false|auto>      → Toggle entity-behind-entity crowd culling
/cameraculling maxcluster <1-128>                   → Set cluster density cap for packed mob pens
/cameraculling reload                               → Reload configuration from disk
```

---

## ⚙️ Configuration

Settings are saved directly to `config/camera-culling.json`:

```json
{
  "enabled": true,
  "level": "MEDIUM",
  "cullEntitiesBehindEntities": null,
  "maxEntitiesPerCluster": 8,
  "distanceTextureLod": true,
  "distanceTextureLodStart": 16.0,
  "distanceTextureLodFar": 32.0,
  "bossImmunity": true,
  "bossHealthThreshold": 150.0,
  "miniBossHealthThreshold": 50.0,
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
2. Download the latest **Camera Culling** JAR for your Minecraft version and place it in your `.minecraft/mods` folder.
3. Launch Minecraft and enjoy smoother frame rates!

---

## ☕ Support

If you enjoy **Camera Culling** and the **Vanilla Outsider** philosophy, consider fueling the next update!

[![Ko-fi](https://img.shields.io/badge/Ko--fi-Support%20Me-FF5E5B?style=for-the-badge&logo=ko-fi&logoColor=white)](https://ko-fi.com/dasikigaijin/tip)
[![SocioBuzz](https://img.shields.io/badge/SocioBuzz-Local_Support-7BB32E?style=for-the-badge)](https://sociabuzz.com/dasikigaijin/tribe)
[![Saweria](https://img.shields.io/badge/Saweria-Local_Support-FFA500?style=for-the-badge)](https://saweria.co/DasikIgaijinn)

> [!NOTE]
> **Indonesian Users:** SocioBuzz and Saweria support local payment methods (Gopay, OVO, Dana, etc.) if you want to support me without using PayPal/Ko-fi!

---

## 📜 Credits

| Role | Author |
| :--- | :--- |
| **Creator** | **Dasik** (Rifaditya) |
| **Collection** | Vanilla Outsider |
| **License** | GNU GPLv3 |

---

> [!IMPORTANT]
> **📦 Modpack Permissions & Distribution:** You are free to include this mod in any modpack on any platform. However, the mod itself must be downloaded from its official distribution pages on **Modrinth** or **CurseForge**. Re-uploading or redistributing the mod jar file to third-party sites is strictly prohibited unless explicitly permitted by the creator.
> 
> **License & Forks:** Since the source code is licensed under **GNU GPLv3**, you are fully permitted to fork the repository, make modifications, build your own versions, and distribute them under the terms of the GPLv3. The prohibition on third-party redistribution applies exclusively to the official compiled releases/jars published by the original creator (Dasik/Rifaditya). Forks must be published as distinct projects, not direct re-uploads of official builds.

---

<div align="center">

**Made with ❤️ for the Minecraft community**

*Part of the Vanilla Outsider Collection*

</div>
