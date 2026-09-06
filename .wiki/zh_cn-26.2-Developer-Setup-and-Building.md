# 🛠️ 开发者环境与构建 (Minecraft 26.2)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

本技术指南详细介绍了在 **Minecraft 26.2** 上编译和构建 **Camera Culling** 所需的先决条件、Gradle Loom 工具链配置以及具体操作指令。

---

## 📋 开发环境先决条件

* **Java 开发工具包 (JDK)**：**JDK 25+**（推荐使用 Eclipse Adoptium Temurin 25）。
* **Gradle Wrapper**：版本 9.3+，搭配 Fabric Loom 1.15.5 (`net.fabricmc.fabric-loom`)。
* **运行时环境**：Mojang 原生非混淆运行时（在 26.x 时代中，`build.gradle` 内严格禁止 mappings 代码块）。

---

## 🏗️ Gradle 构建指令

在终端中进入 `Camera Culling v26.2/Camera Culling 26.2` 子项目目录：

```bash
# 执行单元测试套件
./gradlew test --no-daemon

# 编译并打包正式发布版 JAR
./gradlew build --no-daemon
```

### 自动化归档管线
子项目的 `build.gradle` 中配置了自动化任务：
* 编译产物 JAR：`build/libs/vanilla-outsider-camera-culling-1.10.0+26.2.jar`
* 自动归档保存路径：`Archive Jar of all versions/MC 26.2/`
* 本地启动器配置目录同步：自动安装至本地客户端目录（`Fabric 26.2/mods/`）。

---

## 📄 `fabric.mod.json` 元数据规范

```json
{
  "schemaVersion": 1,
  "id": "camera-culling",
  "version": "${version}",
  "name": "Camera Culling",
  "description": "High-performance camera occlusion culling, 2-sided sign text culling & distance texture LOD.",
  "authors": [
    "Dasik (Rifaditya)"
  ],
  "license": "GPL-3.0-or-later",
  "environment": "client",
  "entrypoints": {
    "client": [
      "net.vanillaoutsider.culling.CameraCullingClient"
    ],
    "modmenu": [
      "net.vanillaoutsider.culling.config.ModMenuIntegration"
    ]
  },
  "mixins": [
    "camera-culling.mixins.json"
  ],
  "depends": {
    "fabricloader": ">=0.18.4",
    "minecraft": ">=26.2-",
    "fabric-api": "*"
  }
}
```

---

## 🔗 相关页面

- [[架构与 Mixin|zh_cn-26.2-Architecture-and-Mixins]]
- [[API 与模组集成|zh_cn-26.2-API-and-Integration]]
- [[返回 MC 26.2 概览|zh_cn-26.2-Home]]
