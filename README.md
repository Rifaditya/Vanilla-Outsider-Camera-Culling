# Camera Culling

**Camera Culling** is a lightweight, purely client-side Fabric mod prototype for Minecraft **26.1.2**, **26.2**, and **26.3** that optimizes frame rates and rendering performance by unrendering entities and block entities outside the camera view or hidden behind solid walls.

---

## Key Features

- **Entity Occlusion Culling**: Fast multi-point raytracing against block collision shapes unrenders mobs and entities that are hidden behind walls.
- **Block Entity Occlusion Culling**: Skips rendering chests, signs, banners, and skulls that are encased in opaque blocks or blocked by walls.
- **Frustum Integration**: Integrates directly with vanilla's camera frustum test to fast-fail entities outside the camera's FOV.
- **Zero Multiplayer Desync**: Purely client-side rendering hooks; world simulation, network packets, and mob ticks remain 100% untouched.
- **Crucial Edge-Case Protections**:
  - Glowing entities remain visible through blocks.
  - Local player and mounted vehicles are never culled.
  - Leashed entities retain visibility if their lead holder is in view.

---

## Supported Versions

- **Minecraft 26.1.2** (`+26.1.2`)
- **Minecraft 26.2** (`+26.2`)
- **Minecraft 26.3** (`+26.3`)

---

## License

This project is licensed under the **GNU General Public License v3.0 (GPLv3)**.
