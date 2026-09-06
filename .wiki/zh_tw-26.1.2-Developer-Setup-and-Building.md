# 🛠️ 開發者環境與構建 (Minecraft 26.1.2)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

本技術指南詳細介紹了在 **Minecraft 26.1.2** 上編譯和建置 **Camera Culling** 所需的先決條件、Gradle Loom 工具鏈配置以及具體操作指令。

---

## 📋 開發環境先決條件

* **Java 開發工具包 (JDK)**：**JDK 25+**（推薦使用 Eclipse Adoptium Temurin 25）。
* **Gradle Wrapper**：版本 9.3+，搭配 Fabric Loom 1.15.5 (`net.fabricmc.fabric-loom`)。
* **執行環境**：Mojang 原生非混淆執行環境（在 26.x 時代中，`build.gradle` 內嚴格禁止 mappings 程式碼區塊）。

---

## 🏗️ Gradle 建置指令

在終端機中進入 `Camera Culling v26.1/Camera Culling 26.1` 子專案目錄：

```bash
# 執行單元測試套件
./gradlew test --no-daemon

# 編譯並打包正式發布版 JAR
./gradlew build --no-daemon
```

### 自動化封存管線
子專案的 `build.gradle` 中配置了自動化任務：
* 編譯產物 JAR：`build/libs/vanilla-outsider-camera-culling-1.10.1+26.1.2.jar`
* 自動封存保存路徑：`Archive Jar of all versions/MC 26.1.2/`
* 本地啟動器設定目錄同步：自動安裝至本地客戶端目錄（`Fabric 26.1.2/mods/`）。

---

## 📄 `fabric.mod.json` 中繼資料規範

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
    "minecraft": ">=26.1.2-",
    "fabric-api": "*"
  }
}
```

---

## 🔗 相關頁面

- [[架構與 Mixin|zh_tw-26.1.2-Architecture-and-Mixins]]
- [[API 與模組整合|zh_tw-26.1.2-API-and-Integration]]
- [[返回 MC 26.1.2 概覽|zh_tw-26.1.2-Home]]
