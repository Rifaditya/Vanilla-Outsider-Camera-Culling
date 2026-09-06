# 🛠️ 開発環境のセットアップとビルド (Minecraft 26.3)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

この技術ガイドでは、**Minecraft 26.3** 向け **Camera Culling** をビルドするための環境要件、Gradle Loom ツール設定、およびコンパイル手順について解説します。

---

## 📋 開発環境の要件

* **Java Development Kit (JDK)**: **JDK 25+**（例: Eclipse Adoptium Temurin 25）。
* **Gradle Wrapper**: Loom 1.15.5（`net.fabricmc.fabric-loom`）を含むバージョン 9.3+。
* **ランタイム**: 非難読化 Mojang ランタイム（26.x では `build.gradle` 内の mappings ブロックの記述は厳格に禁止されています）。

---

## 🏗️ Gradle ビルドコマンド

サブプロジェクトディレクトリ `Camera Culling v26.3/Camera Culling 26.3` でターミナルを開きます:

```bash
# 単体テストスイートの実行
./gradlew test --no-daemon

# リリース JAR のコンパイルおよびパッケージング
./gradlew build --no-daemon
```

### 自動アーカイブパイプライン
サブプロジェクトの `build.gradle` には自動アーカイブタスクが含まれています:
* 出力 JAR: `build/libs/vanilla-outsider-camera-culling-1.10.0+26.3.jar`
* 自動保存先: `Archive Jar of all versions/MC 26.3/`
* Modrinth プロファイル同期: ローカルのランチャープロファイル（`Fabric 26.3ish/mods/`）に自動的にインストールされます。

---

## 📄 `fabric.mod.json` メタデータ

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
    "minecraft": ">=26.3-",
    "fabric-api": "*"
  }
}
```

---

## 🔗 関連ページ

- [[アーキテクチャとMixin|ja_jp-26.3-Architecture-and-Mixins]]
- [[APIとMod連携|ja_jp-26.3-API-and-Integration]]
- [[MC 26.3 概要に戻る|ja_jp-26.3-Home]]
