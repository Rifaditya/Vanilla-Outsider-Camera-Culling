# 🟢 Camera Culling (Minecraft 26.3)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

**Camera Culling** (`v1.10.0+26.3`) の **Minecraft 26.3** 専用ドキュメントハブへようこそ。

> 📌 **リポジトリソースコード免責事項**: このWikiのドキュメントは**リポジトリ内の現在のソースコード状態**を反映しており、CurseForgeおよびModrinthでの公式リリースビルドに先行する未リリースの最新コミットや開発中機能が含まれている場合があります。

---

## 📋 Minecraft 26.3 基本情報

| 項目 | 設定値 |
| :--- | :--- |
| **対象Minecraftバージョン** | `26.3` |
| **リリースバージョン** | `1.10.1+26.3` |
| **Java要件** | Java 25 (`release = 25`) |
| **Fabric Loader** | `>=0.18.4` |
| **Fabric API** | `0.156.1+26.3` |
| **ライセンス** | GNU General Public License v3.0 (GPLv3) |
| **サブプロジェクトパス** | `Camera Culling v26.3/Camera Culling 26.3` |

---

## ⚡ コア機能マトリクス

```text
Camera Culling 26.3 Pipeline
├── Entity Occlusion (Zero-Allocation Raycast Engine)
├── Block Entity Occlusion (6-Sided Enclosure & Sightlines)
├── 2-Sided Sign Text Culling (SignTextSlot API + Normal Vector Dot Product)
├── Particle Occlusion (4m Proximity Safety + Visual Clip)
├── Animation Culling (TextureAtlas Upload Suppression)
├── Distance Texture LOD (3-Tier OpenGL Mipmap Bias)
├── Boss & Mini-Boss Immunity (Configurable HP Thresholds)
├── Anti-Flicker Temporal Hysteresis (Adaptive 4/8/12-Frame Buffer)
└── Graphical GUI (YACL v3 & ModMenu) + In-Game Commands
```

---

## 📚 26.3 ドキュメントインデックス

1. [[エンティティの遮蔽カリング|ja_jp-26.3-Entity-Occlusion-Culling]] — マルチポイントレイキャストと床面判定フィルタリング。
2. [[ブロックエンティティの間引き|ja_jp-26.3-Block-Entity-Culling]] — チェストやブロックエンティティの閉塞判定。
3. [[看板と吊り看板のテキスト間引き|ja_jp-26.3-Sign-and-Hanging-Sign-Culling]] — 両面法線ベクトルの内積計算と `SignTextSlot`。
4. [[パーティクルとアニメーションの間引き|ja_jp-26.3-Particle-and-Animation-Culling]] — 地下パーティクルの遮蔽とアトラスアニメーションの凍結。
5. [[密集モブのオーバードロー防御|ja_jp-26.3-Mob-Crowd-Overdraw-Defense]] — 16m距離制限と1.5mクラスタ密度キャップ。
6. [[距離ベースのテクスチャLOD|ja_jp-26.3-Distance-Texture-LOD]] — 遠方の群れに対するOpenGLミップマップLODバイアス。
7. [[ボスとブラックリスト免疫|ja_jp-26.3-Boss-and-Blacklist-Immunity]] — ボス保護と2段階ブラックリストシステム。
8. [[時間的ヒステリシスとゼロアロケーション|ja_jp-26.3-Temporal-Hysteresis-and-Zero-Allocation]] — 猶予バッファとゼロアロケーションエンジン。
9. [[コマンドと設定|ja_jp-26.3-Commands-and-Configuration]] — Brigadierコマンド構文リファレンス。
10. [[グラフィカルGUI設定 (YACL)|ja_jp-26.3-GUI-Configuration]] — グラフィカル設定メニューガイド。
11. [[デバッグログと診断|ja_jp-26.3-Debug-Logging-and-Diagnostics]] — 状態遷移のリアルタイムチャットおよびログ追跡。
12. [[アーキテクチャとMixin|ja_jp-26.3-Architecture-and-Mixins]] — パッケージ階層とMixin注入先テーブル。
13. [[開発環境のセットアップとビルド|ja_jp-26.3-Developer-Setup-and-Building]] — JDK 25のセットアップとLoom Gradleビルド手順。
14. [[APIとMod連携|ja_jp-26.3-API-and-Integration]] — プログラムによる連携フック。

---

[[バージョンポータルに戻る|ja_jp-Home]] &bull; [[バージョン互換性マトリクス|ja_jp-Version-Compatibility]]
