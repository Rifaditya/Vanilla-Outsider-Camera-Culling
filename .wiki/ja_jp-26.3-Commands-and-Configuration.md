# 🎮 コマンドと設定 (Minecraft 26.3)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Camera Culling はゲーム内の完全な Brigadier コマンド体系（`/cameraculling`）と整然とした JSON 設定ファイル（`config/camera-culling.json`）を提供します。

---

## 📋 コマンドリファレンステーブル

```sql
/cameraculling status
/cameraculling toggle
/cameraculling set <low|medium|high|super>
/cameraculling particles [true|false]
/cameraculling animations [true|false]
/cameraculling crowdculling <true|false>
/cameraculling cluster <1-128>
/cameraculling texturlod <true|false>
/cameraculling texturlod range <start_dist> <far_dist>
/cameraculling bossimmunity <true|false>
/cameraculling bosshealth <boss_hp>
/cameraculling bosshealth <boss_hp> <miniboss_hp>
/cameraculling minibosshealth <miniboss_hp>
/cameraculling blacklist <add|remove|list|clear> <entity_id>
/cameraculling serverblacklist <add|remove|list|clear> <entity_id>
/cameraculling debug [true|false]
/cameraculling reload
```

---

## ⚙️ コマンド詳細解説

| コマンド構文 | 引数 | 機能 |
| :--- | :--- | :--- |
| `/cameraculling status` | なし | リアルタイム統計、描画中 vs カリング済みカウンター、アクティブなプロファイル、ブラックリスト登録数を表示。 |
| `/cameraculling toggle` | なし | カリング機能全体の有効/無効を切り替え。 |
| `/cameraculling set <profile>` | `low`, `medium`, `high`, `super` | アクティブなカリング強度プロファイルを変更。 |
| `/cameraculling particles [bool]` | `true`, `false`（省略可能） | 固体ブロック裏のパーティクル遮蔽カリングを切り替え。 |
| `/cameraculling animations [bool]` | `true`, `false`（省略可能） | ブロックおよびテクスチャアトラスのアニメーション一時停止を切り替え。 |
| `/cameraculling crowdculling <bool>` | `true`, `false` | モブ密集時の重なりオーバードローカリングを切り替え。 |
| `/cameraculling cluster <int>` | `1` 〜 `128` | 1.5ブロックのクラスタ内で描画を許可する最大モブ数を設定（デフォルト: `8`）。 |
| `/cameraculling texturlod <bool>` | `true`, `false` | 距離に応じたテクスチャLODミップマップスケーリングの有効/無効を切り替え。 |
| `/cameraculling texturlod range <start> <far>` | `1.0..256.0`, `2.0..512.0` | テクスチャLOD適用の開始距離および遠距離閾値を設定（例: `16.0 32.0`）。 |
| `/cameraculling bossimmunity <bool>` | `true`, `false` | ボスおよびミニボスの視線カリング保護を切り替え。 |
| `/cameraculling bosshealth <hp>` | `1.0` 〜 `10000.0` | メジャーボスのHP閾値を設定（例: `150.0`）。 |
| `/cameraculling minibosshealth <hp>` | `1.0` 〜 `10000.0` | ミニボスのHP閾値を設定（例: `50.0`）。 |
| `/cameraculling blacklist add <id>` | エンティティID文字列 | 個人用クライアント免疫リストにエンティティ（例: `minecraft:wolf`）を追加。 |
| `/cameraculling blacklist remove <id>` | エンティティID文字列 | 個人用クライアント免疫リストからエンティティを削除。 |
| `/cameraculling blacklist list` | なし | 個人用免疫リストに登録されている全エンティティを一覧表示。 |
| `/cameraculling blacklist clear` | なし | 個人用免疫リストの全項目を消去。 |
| `/cameraculling serverblacklist ...` | サブコマンド + ID | サーバー全体に強制適用する免疫リストを設定（要OP権限）。 |
| `/cameraculling debug [bool]` | `true`, `false`（省略可能） | リアルタイムの状態遷移診断ログのチャット・ログ出力を切り替え。 |
| `/cameraculling reload` | なし | ディスクから設定ファイルを再読み込み。 |

---

## 📄 JSON 設定ファイルフォーマット

設定ファイルは `.minecraft/config/` ディレクトリに保存されます:

### クライアント設定 (`config/camera-culling.json`)
```json
{
  "enabled": true,
  "level": "SUPER",
  "cullEntitiesBehindEntities": false,
  "maxEntitiesPerCluster": 8,
  "distanceTextureLod": true,
  "distanceTextureLodStart": 16.0,
  "distanceTextureLodFar": 32.0,
  "bossImmunity": true,
  "bossHealthThreshold": 150.0,
  "miniBossHealthThreshold": 50.0,
  "cullParticles": true,
  "cullAnimations": true,
  "cullSignText": true,
  "clientBlacklist": [
    "minecraft:wolf",
    "minecraft:allay"
  ],
  "debugMode": false
}
```

### サーバー設定 (`config/camera-culling-server.json`)
```json
{
  "serverBlacklist": []
}
```

---

## 🔗 関連ページ

- [[グラフィカルGUI設定 (YACL)|ja_jp-26.3-GUI-Configuration]]
- [[デバッグログと診断|ja_jp-26.3-Debug-Logging-and-Diagnostics]]
- [[MC 26.3 概要に戻る|ja_jp-26.3-Home]]
