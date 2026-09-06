# 🖥️ グラフィカルGUI設定 (YACL) (Minecraft 26.1.2)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Camera Culling は **YetAnotherConfigLib (YACL v3)** および **ModMenu** を利用した、モダンで視覚的な設定画面の表示をサポートしています。

---

## 📋 GUI 連携基本情報

| 項目 | 設定値 |
| :--- | :--- |
| **対応GUIエンジン** | YetAnotherConfigLib v3 (YACL) + ModMenu |
| **連携方式** | クラスの遅延ロード（`ConfigScreenFactory`） |
| **サーバー安全性** | 100% 安全 — サーバー側のエントリポイントにはクライアントGUIクラスへの参照が一切含まれません |
| **メニューカテゴリ** | 3つのタブ付き専用カテゴリ |

---

## 🗂️ GUI カテゴリ構成

```text
Camera Culling 設定画面
├── 1. Engine & Diagnostics
│   ├── Master Enable (チェックボックス)
│   ├── Culling Level (ドロップダウン: LOW, MEDIUM, HIGH, SUPER)
│   └── Real-Time Debug Logging (チェックボックス)
│
├── 2. Entity & Crowd Occlusion
│   ├── Crowd Overdraw Culling (チェックボックス)
│   ├── Max Cluster Entities Cap (スライダー: 1 〜 32)
│   ├── Boss & Mini-Boss Immunity (チェックボックス)
│   ├── Major Boss Health Threshold (数値入力フィールド、デフォルト: 150.0 HP)
│   └── Mini-Boss Health Threshold (数値入力フィールド、デフォルト: 50.0 HP)
│
└── 3. Blocks, Particles & Animations
    ├── Particle Culling (チェックボックス)
    ├── Block & Texture Animation Culling (チェックボックス)
    ├── 2-Sided Sign Text Culling (チェックボックス)
    ├── Distance Texture LOD (チェックボックス)
    ├── Distance Texture LOD Start Distance (スライダー: 8m 〜 64m)
    └── Distance Texture LOD Far Distance (スライダー: 16m 〜 128m)
```

---

## 🛡️ クラスの遅延ロードとクラッシュ対策

YACL が導入されていない環境や専用サーバーで Camera Culling がクラッシュするのを防ぐため、`ModMenuIntegration` では遅延ロードを採用しています:

```java
public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        if (FabricLoader.getInstance().isModLoaded("yet_another_config_lib_v3")) {
            return YaclScreenHelper.createFactory();
        }
        return parent -> null;
    }
}
```

YACL がインストールされていない場合でも、ゲームは通常通り起動し、すべての設定項目は[[ゲーム内コマンド|ja_jp-26.1.2-Commands-and-Configuration]]または `config/camera-culling.json` の直接編集によって設定可能です。

---

## 🔗 関連ページ

- [[コマンドと設定|ja_jp-26.1.2-Commands-and-Configuration]]
- [[デバッグログと診断|ja_jp-26.1.2-Debug-Logging-and-Diagnostics]]
- [[MC 26.1.2 概要に戻る|ja_jp-26.1.2-Home]]
