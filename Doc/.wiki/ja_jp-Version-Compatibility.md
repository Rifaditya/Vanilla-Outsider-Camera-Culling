# 🌐 バージョン互換性とライフサイクルマトリクス

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

このドキュメントでは、**Camera Culling** のアクティブなマルチエラリリースマトリクス、依存関係の範囲、Javaランタイム仕様、およびバイトコードAPIの差異について説明します。

> 📌 **リポジトリソースコード免責事項**: このWikiのドキュメントは**リポジトリ内の現在のソースコード状態**を反映しており、CurseForgeおよびModrinthでの公式リリースビルドに先行する未リリースの最新コミットや開発中機能が含まれている場合があります。

---

## 📋 マルチバージョンライフサイクルマトリクス

| Minecraft アンカー世代 | 対象MCバージョン | 現在のリリースバージョン | Java要件 | Fabric Loader 範囲 | Fabric API 範囲 | リリース状況 |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| **Modern Sovereign (26.3)** | `26.3` | `1.10.1+26.3` | Java 25 (`release = 25`) | `>=0.18.4` | `0.156.1+26.3` | 🟢 アクティブリリース |
| **Modern Sovereign (26.2)** | `26.2` | `1.10.1+26.2` | Java 25 (`release = 25`) | `>=0.18.4` | `0.145.4+26.2` | 🟢 アクティブリリース |
| **Modern Sovereign (26.1.2)** | `26.1.2` | `1.10.2+26.1.2` | Java 25 (`release = 25`) | `>=0.18.4` | `0.145.4+26.1.2` | 🟢 アクティブリリース |

---

## 🛠️ 各アンカーにおけるバイトコードAPIの差異

### 1. ブロックエンティティの RenderState 抽出パイプライン
* **Minecraft 26.1.2**:
  - `BlockEntityRenderDispatcher.tryExtractRenderState(BlockEntity, float, ModelFeatureRenderer$CrumblingOverlay)` — **3個の引数**を受け取ります。
* **Minecraft 26.2 & 26.3**:
  - `BlockEntityRenderDispatcher.tryExtractRenderState(BlockEntity, float, ModelFeatureRenderer$CrumblingOverlay, boolean isGloballyRendered)` — **4個の引数**を受け取ります。

### 2. 看板テキストデータ取得API
* **Minecraft 26.1.2 & 26.2**:
  - `SignBlockEntity.getFrontText()` および `SignBlockEntity.getBackText()` で `SignText` を取得します。
  - `SignText.getMessage(int index, boolean filtered)` で各行の `Component` を取得します。
* **Minecraft 26.3**:
  - `SignBlockEntity.getText(SignTextSlot.FRONT)` および `SignBlockEntity.getText(SignTextSlot.BACK)` で `SignText` を取得します。
  - `SignText.getMessages(boolean filtered)` で各行の `Component` 配列を取得します。

---

## 📦 ビルド成果物アーカイブの配置場所

すべてのリリースビルドは自動的にコンパイルされ、親リポジトリの一元管理アーカイブ構造に保存されます:

```text
Archive Jar of all versions/
├── MC 26.1.2/
│   ├── vanilla-outsider-camera-culling-1.10.1+26.1.2.jar
│   └── vanilla-outsider-camera-culling-1.10.1+26.1.2-sources.jar
├── MC 26.2/
│   ├── vanilla-outsider-camera-culling-1.10.0+26.2.jar
│   └── vanilla-outsider-camera-culling-1.10.0+26.2-sources.jar
└── MC 26.3/
    ├── vanilla-outsider-camera-culling-1.10.0+26.3.jar
    └── vanilla-outsider-camera-culling-1.10.0+26.3-sources.jar
```

---

## 🔗 クイックリンク

- [[👉 Minecraft 26.3 Wiki に入る|ja_jp-26.3-Home]]
- [[👉 Minecraft 26.2 Wiki に入る|ja_jp-26.2-Home]]
- [[👉 Minecraft 26.1.2 Wiki に入る|ja_jp-26.1.2-Home]]
- [[ポータルに戻る|ja_jp-Home]]
