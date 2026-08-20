# 📷 Camera Culling (Minecraft 26.2)

[![Requires Fabric API](https://img.shields.io/badge/Requires-Fabric_API-blue?style=for-the-badge&logo=fabric)](https://modrinth.com/mod/fabric-api)
[![Java 25](https://img.shields.io/badge/Language-Java_25-orange?style=for-the-badge&logo=java)](https://adoptium.net)
[![License: GPLv3](https://img.shields.io/badge/License-GPLv3-green?style=for-the-badge)](https://www.gnu.org/licenses/gpl-3.0)
[![Minecraft 26.2](https://img.shields.io/badge/Minecraft-26.2-brightgreen?style=for-the-badge)](https://github.com/Rifaditya/Vanilla-Outsider-Camera-Culling/releases)

Dedicated Minecraft **26.2** subproject build of **Camera Culling**.

🔗 **Official GitHub Wiki**: [https://github.com/Rifaditya/Vanilla-Outsider-Camera-Culling/wiki](https://github.com/Rifaditya/Vanilla-Outsider-Camera-Culling/wiki)

## Features Included
- **Zero-Allocation Raycast Engine**: Zero per-frame heap allocations during continuous raycasting.
- **2-Sided Sign & Hanging Sign Text Culling**: Skips font rendering on the occluded back side of signs.
- **Particle & Animation Culling**: 4m proximity safety bubble and TextureAtlas animation freezing.
- **Distance-Based Mob Texture LOD**: 3-tier mipmap LOD bias (Full / Half / Quarter resolution).
- **Dynamic Boss & Mini-Boss Immunity**: Full protection for major bosses and configurable mini-bosses.
- **Two-Tier Immunity Blacklist**: Client personal and server admin whitelists.
- **YetAnotherConfigLib (YACL v3) GUI**: 3-category graphical configuration menu.

## Build Instructions
```bash
./gradlew build --no-daemon
```
Compiled artifact will be output to `build/libs/` and auto-archived to `Archive Jar of all versions/MC 26.2/`.
