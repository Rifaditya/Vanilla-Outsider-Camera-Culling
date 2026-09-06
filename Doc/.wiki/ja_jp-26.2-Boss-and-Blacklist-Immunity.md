# 👑 ボスとブラックリスト免疫 (Minecraft 26.2)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

戦闘時の公平性と状況把握を維持するため、危険な脅威やペット・相棒モブが壁の向こうに消えたり、アグレッシブなカリングアルゴリズムによって非表示になったりしてはなりません。

**Camera Culling** は動的なボス識別および2段階の免疫ブラックリストシステムを搭載しています。

---

## 📋 免疫システム基本情報

| 項目 | 設定値 |
| :--- | :--- |
| **メジャーボス体力閾値** | `bossHealthThreshold`（デフォルト: `150.0 HP` / ハート75個） |
| **ミニボス体力閾値** | `miniBossHealthThreshold`（デフォルト: `50.0 HP` / ハート25個） |
| **クライアントブラックリスト** | `config/camera-culling.json` (`clientBlacklist` 配列) |
| **サーバーブラックリスト** | `config/camera-culling-server.json` (`serverBlacklist` 配列) |
| **免疫適用範囲** | ブロック遮蔽カリング、密集オーバードロー、テクスチャLODから完全免除 |

---

## 🐲 動的ボス・ミニボス検出

Camera Culling は2つの仕組みを用いてボスの免疫を判定します:

### 1. 動的体力閾値判定
`getMaxHealth()` が設定された閾値以上の `LivingEntity` には無条件で免疫が付与されます:
* `maxHealth >= 150.0` $\implies$ メジャーボス（エンドラ、ウィザー、ウォーデン）。
* `maxHealth >= 50.0` $\implies$ ミニボス（エルダーガーディアン、ラヴェジャー、アイアンゴーレム、ピグリンブルート、ブリーズ、Modのチャンピオンなど）。

### 2. レジストリおよび識別子キーワードヒューリスティクス
以下の文字列を名前に含むエンティティは自動的にボスとして識別されます:
```text
dragon, wither, warden, elder_guardian, ravager, evoker, iron_golem, 
piglin_brute, breeze, boss, miniboss, mini_boss, titan, leviathan, 
harbinger, monarch, behemoth, champion, elite, brute
```

---

## 🛡️ 2段階免疫ブラックリストシステム

```text
免疫判定フロー
├── ローカルプレイヤー / 搭乗中の乗り物 ──► 100% 描画
├── 発光エフェクト (Glowing) 有効 ────────► 100% 描画
├── ボスまたはミニボスを検出 ─────────────► 100% 描画
├── クライアントブラックリストに一致 ────► 100% 描画
└── サーバー管理者ブラックリストに一致 ──► 100% 描画
```

### 1. クライアント個別ブラックリスト
プレイヤーはゲーム内コマンドを使用して、保護したい相棒エンティティをローカルに追加できます:
```sql
/cameraculling blacklist add minecraft:wolf
/cameraculling blacklist add minecraft:allay
/cameraculling blacklist add minecraft:cat
```

### 2. サーバー管理者ブラックリスト
サーバー管理者は `config/camera-culling-server.json` または `/cameraculling serverblacklist add <id>` コマンドによってサーバー全体の免疫リストを定義できます。接続中のすべてのクライアントはサーバー設定を最優先で適用します。

---

## 🔗 関連ページ

- [[エンティティの遮蔽カリング|ja_jp-26.2-Entity-Occlusion-Culling]]
- [[距離ベースのテクスチャLOD|ja_jp-26.2-Distance-Texture-LOD]]
- [[コマンドと設定|ja_jp-26.2-Commands-and-Configuration]]
- [[MC 26.2 概要に戻る|ja_jp-26.2-Home]]
