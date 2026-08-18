# Camera Culling

**Camera Culling** is a lightweight, purely client-side Fabric performance mod that dynamically unrenders entities and block entities hidden behind walls or outside your field of view.

---

## ⚡ Key Features

- **Entity Occlusion Culling**: Fast multi-point raytracing against block collision shapes unrenders mobs hidden behind solid walls, dramatically boosting FPS in mob-heavy areas and farms.
- **Block Entity Culling**: Skips render extraction for chests, signs, banners, skulls, and decorated pots that are enclosed in opaque blocks or hidden behind walls.
- **4 Culling Intensity Profiles**:
  - **LOW (Conservative)**: 4.0-block safety buffer, 7-point sampling, padded hitboxes, culls block entities only when 100% enclosed by solid blocks.
  - **MEDIUM (Balanced - Default)**: 2.0-block safety buffer, 3-point sampling + corner checks, standard block entity culling.
  - **HIGH (Aggressive)**: 1.0-block safety buffer, fast 2-point sampling, aggressive culling of all block entities, item frames, and armor stands.
  - **SUPER (Extreme / Potato PC)**: 0.25-block safety buffer, ultra-fast 1-point center check, maximum FPS boost on low-end hardware.
- **In-Game Commands**:
  - `/cameraculling status` — View active level, toggle state, and real-time culled vs rendered counts.
  - `/cameraculling set <low|medium|high|super>` — Change intensity on the fly.
  - `/cameraculling toggle` — Toggle culling on or off.
- **Zero Multiplayer Desync**: Purely client-side rendering hooks; world simulation, network packets, and mob AI remain 100% untouched.
- **Essential Safety Guards**: Glowing entities, the player character, mounted vehicles, and entities within close proximity are preserved.

---

## 📦 Supported Minecraft Versions

- **Minecraft 26.1.2**
- **Minecraft 26.2**
- **Minecraft 26.3**

---

## 📜 License

Licensed under the **GNU General Public License v3.0 (GPLv3)**.
