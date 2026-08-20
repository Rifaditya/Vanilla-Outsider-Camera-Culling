# 📷 Camera Culling Wiki

Welcome to the official **Camera Culling** documentation portal. Camera Culling is a high-performance, client-side rendering optimization mod for Minecraft **26.1.2**, **26.2**, and **26.3** developed under the **Vanilla Outsider** philosophy.

> 📌 **Repository Source Disclaimer**: The documentation in this Wiki reflects the **current source code state in the repository**, which may include recent unreleased commits or developmental features ahead of public release builds on CurseForge and Modrinth.

---

## 🧭 Multi-Version Switchboard Portal

Camera Culling is developed under the strict **1 JAR 1 Version** law. Select your targeted Minecraft version below to enter its dedicated, isolated documentation tree:

| Targeted Minecraft Version | Mod Release Version | Java Runtime | Build Tooling | Dedicated Wiki Portal |
| :--- | :--- | :--- | :--- | :--- |
| **Minecraft 26.1.2** | `1.10.2+26.1.2` | Java 25+ | Fabric Loom 1.15.5 | [[👉 Enter MC 26.1.2 Wiki|26.1.2-Home]] |
| **Minecraft 26.2** | `1.10.1+26.2` | Java 25+ | Fabric Loom 1.15.5 | [[👉 Enter MC 26.2 Wiki|26.2-Home]] |
| **Minecraft 26.3** | `1.10.1+26.3` | Java 25+ | Fabric Loom 1.15.5 | [[👉 Enter MC 26.3 Wiki|26.3-Home]] |

---

## ⚡ Core Optimization Matrix

| Optimization System | Primary Mechanism | Performance Benefit |
| :--- | :--- | :--- |
| **Zero-Allocation Raycast Engine** | Primitive-coordinate sightline checking | Eliminates JVM Young-Gen GC pause spikes during camera turning |
| **Anti-Flicker Temporal Hysteresis** | Adaptive 4/8/12-frame distance grace buffer | Eliminates edge grazing and view-bobbing flicker |
| **2-Sided Sign Text Culling** | Face normal vector dot products ($\vec{N} \cdot \vec{V}$) | 50%–100% reduction in sign text draw calls |
| **Particle Occlusion Culling** | 4m safety bubble + visual clip raycasts | Unrenders underground & occluded particle quads |
| **Animation Culling** | Texture atlas upload suppression | Freezes 3D block animations and off-screen texture uploads |
| **Mob Crowd Overdraw Defense** | 16m fast-fail + 1.5m cluster density caps | Eliminates lag spikes in cramming mob pens & farms |
| **Distance Texture LOD** | 3-tier OpenGL mipmap bias ($0.0 \to 1.0 \to 2.5$) | Drastically reduces VRAM fillrate on distant herds |
| **Boss & Mini-Boss Immunity** | Dynamic health thresholds & name heuristics | Prevents gameplay-breaking culling of bosses |
| **Two-Tier Immunity Blacklist** | Local client JSON + Server admin sync | Custom whitelist for companion mobs |
| **Block Entity Culling** | 6-sided solid enclosure detection | Skips render extraction for encased chests and blocks |

---

## 📚 Global Navigation

- [[Version Compatibility & Lifecycle Matrix|Version-Compatibility]]
- [[Minecraft 26.1.2 Documentation Tree|26.1.2-Home]]
- [[Minecraft 26.2 Documentation Tree|26.2-Home]]
- [[Minecraft 26.3 Documentation Tree|26.3-Home]]

---

<p align="center">
  <em>Developed by <strong>Dasik (Rifaditya)</strong> | Licensed under <strong>GNU General Public License v3.0 (GPLv3)</strong></em>
</p>
