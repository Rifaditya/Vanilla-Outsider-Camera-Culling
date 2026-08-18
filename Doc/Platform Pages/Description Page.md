# Camera Culling

**Camera Culling** is a lightweight, purely client-side Fabric performance mod that dynamically unrenders entities and block entities hidden behind walls, outside your field of view, or buried behind dense crowds of other mobs, while downscaling textures on distant mobs to dramatically improve FPS and lower GPU memory bandwidth.

---

## ⚡ Key Features

- **Entity Occlusion Culling**: Fast multi-point raytracing against block collision shapes unrenders mobs hidden behind solid walls, dramatically boosting FPS in mob-heavy areas and caves.
- **Entity-Behind-Entity Culling (Crowd Overdraw)**: Detects when mobs are completely hidden behind other closer opaque mobs or packed inside cramming pens (cluster density cap of 8 mobs per 1.5 blocks).
- **Distance-Based Mob Texture LOD (Texture Resolution Reduction)**:
  - Completely independent standalone setting (does not force or sync with culling profiles).
  - **< 16 blocks**: 100% Native full-resolution crisp textures.
  - **16 – 32 blocks**: Half-resolution texture sampling (OpenGL Mipmap LOD bias +1.0).
  - **> 32 blocks**: Quarter-resolution low-res mipmap sampling (OpenGL Mipmap LOD bias +2.5).
  - Drastically lowers VRAM fillrate and texture sampling bandwidth on large amounts of distant mobs.
- **Two-Tier Entity Immunity Blacklist**:
  - **Personal Client Blacklist**: Whitelist specific mobs (e.g. `minecraft:wolf`, `minecraft:allay`) that you never want culled or texture-reduced.
  - **Server Admin Enforcement**: Server admins can enforce global immunity across all connected clients.
- **Dynamic Boss & Mini-Boss Detection and Full Immunity**:
  - Automatically identifies both vanilla and modded bosses & mini-bosses (Elder Guardians, Ravagers, Iron Golems, Piglin Brutes, Evokers, Breezes, and modded champions/elites).
  - **Player-Configurable Health Limits**: Set custom thresholds for Major Bosses (`bossHealthThreshold: 150.0 HP`) and Mini-Bosses (`miniBossHealthThreshold: 50.0 HP`).
  - Mobs classified as bosses or mini-bosses are **never culled behind walls**, **never culled by crowd density caps**, and **never downscaled by texture LOD**.
- **Block Entity Culling**: Skips render extraction for chests, signs, banners, skulls, and decorated pots that are enclosed in opaque blocks or hidden behind walls.
- **4 Culling Intensity Profiles**:
  - **LOW (Conservative)**: 4.0-block safety buffer, 7-point sampling, padded hitboxes.
  - **MEDIUM (Balanced - Default)**: 2.0-block safety buffer, 3-point sampling + corner checks.
  - **HIGH (Aggressive)**: 1.0-block safety buffer, fast 2-point sampling, entity-behind-entity culling enabled, aggressive culling of all block entities, item frames, and armor stands.
  - **SUPER (Extreme / Potato PC)**: 0.25-block safety buffer, ultra-fast 1-point center check for maximum FPS on low-end hardware.
- **In-Game Commands**:
  - `/cameraculling status` — View active level, entity culling state, texture LOD range, boss/mini-boss thresholds, blacklist counts, and real-time culled vs rendered counts.
  - `/cameraculling set <low|medium|high|super>` — Change culling intensity on the fly.
  - `/cameraculling blacklist <add|remove|list|clear> <id>` — Manage personal immunity blacklist.
  - `/cameraculling serverblacklist <add|remove|list|clear> <id>` — Manage server-wide immunity blacklist (OP/Admin).
  - `/cameraculling texturlod <true|false>` — Toggle distance texture LOD independently.
  - `/cameraculling texturlod range <near> <far>` — Set custom distance thresholds for half-res and quarter-res textures.
  - `/cameraculling bossimmunity <true|false>` — Toggle Boss & Mini-Boss immunity on or off.
  - `/cameraculling bosshealth <hp>` — Adjust major boss health threshold (e.g. `200`).
  - `/cameraculling minibosshealth <hp>` — Adjust mini-boss health threshold (e.g. `60`).
  - `/cameraculling bosshealth <boss_hp> <miniboss_hp>` — Adjust both health thresholds simultaneously.
  - `/cameraculling entityculling <true|false|auto>` — Toggle entity-behind-entity culling independently.
  - `/cameraculling maxcluster <1-128>` — Configure maximum mobs rendered per 1.5-block cluster.
  - `/cameraculling toggle` — Toggle culling on or off.
- **Zero Multiplayer Desync**: Purely client-side rendering hooks; world simulation, network packets, and mob AI remain 100% untouched.

---

## 📦 Supported Minecraft Versions

- **Minecraft 26.1.2**
- **Minecraft 26.2**
- **Minecraft 26.3**

---

## 📜 License

Licensed under the **GNU General Public License v3.0 (GPLv3)**.
