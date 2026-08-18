# Camera Culling

**Camera Culling** is a lightweight, purely client-side Fabric performance mod that dynamically unrenders entities and block entities hidden behind walls, outside your field of view, or buried behind dense crowds of other mobs.

---

## ⚡ Key Features

- **Entity Occlusion Culling**: Fast multi-point raytracing against block collision shapes unrenders mobs hidden behind solid walls, dramatically boosting FPS in mob-heavy areas and farms.
- **Entity-Behind-Entity Culling (Crowd Overdraw)**: Detects when mobs are completely hidden behind other closer opaque mobs or packed inside cramming pens (cluster density cap of 8 mobs per 1.5 blocks).
- **Block Entity Culling**: Skips render extraction for chests, signs, banners, skulls, and decorated pots that are enclosed in opaque blocks or hidden behind walls.
- **4 Culling Intensity Profiles**:
  - **LOW (Conservative)**: 4.0-block safety buffer, 7-point sampling, padded hitboxes, culls block entities only when 100% enclosed by solid blocks.
  - **MEDIUM (Balanced - Default)**: 2.0-block safety buffer, 3-point sampling + corner checks, standard block entity culling.
  - **HIGH (Aggressive)**: 1.0-block safety buffer, fast 2-point sampling, entity-behind-entity culling enabled, aggressive culling of all block entities, item frames, and armor stands.
  - **SUPER (Extreme / Potato PC)**: 0.25-block safety buffer, ultra-fast 1-point center check, aggressive crowd and entity culling for maximum FPS on low-end hardware.
- **In-Game Commands**:
  - `/cameraculling status` — View active level, entity-behind-entity state, and real-time culled vs rendered counts.
  - `/cameraculling set <low|medium|high|super>` — Change intensity on the fly.
  - `/cameraculling entityculling <true|false|auto>` — Toggle entity-behind-entity culling independently.
  - `/cameraculling maxcluster <1-128>` — Configure maximum mobs rendered per 1.5-block cluster.
  - `/cameraculling toggle` — Toggle culling on or off.
- **Zero Multiplayer Desync**: Purely client-side rendering hooks; world simulation, network packets, and mob AI remain 100% untouched.
- **Essential Safety Guards**: Glowing entities, the player character, mounted vehicles, and Bosses (Ender Dragon, Wither, Warden) are immune to culling. Slimes and Vexes never block visibility.

---

## 📦 Supported Minecraft Versions

- **Minecraft 26.1.2**
- **Minecraft 26.2**
- **Minecraft 26.3**

---

## 📜 License

Licensed under the **GNU General Public License v3.0 (GPLv3)**.
