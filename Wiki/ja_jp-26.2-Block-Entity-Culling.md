# 📦 ブロックエンティティの間引き (Minecraft 26.2)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

ブロックエンティティ（チェスト、エンダーチェスト、看板、旗、頭蓋骨、飾り壺、鐘、ビーコン）は毎フレーム個別の描画コールを発行します。大規模な倉庫部屋や自動仕分け施設では、これがGPU描画コールの激しい競合を引き起こします。

---

## 📋 ブロックエンティティカリング基本情報

| 項目 | 設定値 |
| :--- | :--- |
| **対象パイプライン** | `BlockEntityRenderDispatcher.tryExtractRenderState(BlockEntity, float, CrumblingOverlay, boolean)` |
| **閉塞検出** | 隣接する6面すべてをチェック: `up`, `down`, `north`, `south`, `east`, `west` |
| **保守的モード** | `LOW` プロファイルでの完全密閉判定のみ |
| **積極的モード** | `MEDIUM`, `HIGH`, `SUPER` での完全レイキャスト視線検証 |
| **判定結果** | RenderState として `null` を返し描画送信をスキップ |

---

## 🔍 閉塞判定および視線検証アーキテクチャ

```text
               [UP]
                │
   [WEST] ── [CHEST] ── [EAST]
                │
              [DOWN]
```

### 1. 6面固体閉塞ファストパス
レイキャスト計算を行う前に、Camera Culling はまず隣接するブロック状態を照会します:
```java
BlockState up = level.getBlockState(pos.above());
BlockState down = level.getBlockState(pos.below());
BlockState north = level.getBlockState(pos.north());
BlockState south = level.getBlockState(pos.south());
BlockState east = level.getBlockState(pos.east());
BlockState west = level.getBlockState(pos.west());

if (up.isSolidRender() && down.isSolidRender() && north.isSolidRender()
    && south.isSolidRender() && east.isSolidRender() && west.isSolidRender()) {
    return true; // 100% 遮蔽 — 描画をスキップ
}
```
壁の奥に埋め込まれたチェストや地下の密閉土台に囲まれたブロックエンティティは、ほぼゼロのCPU負荷（$< 0.0001\mu\text{s}$）で瞬時に描画が省かれます。

### 2. 視線レイキャスト検証
`MEDIUM`, `HIGH`, `SUPER` プロファイル（`cullAllBlockEntities = true`）では、Camera Culling はカメラ位置からブロックエンティティの中心 $(X + 0.5, Y + 0.5, Z + 0.5)$ に向けてレイキャストを照射します:
* 対象ブロックエンティティに到達する前に不透明ブロックに衝突した場合、レンダー状態は破棄されます。
* 視線が通っている場合、ブロックエンティティは完全な視覚品質で通常通り描画されます。

---

## 🔗 関連ページ

- [[看板と吊り看板のテキスト間引き|ja_jp-26.2-Sign-and-Hanging-Sign-Culling]]
- [[エンティティの遮蔽カリング|ja_jp-26.2-Entity-Occlusion-Culling]]
- [[アーキテクチャとMixin|ja_jp-26.2-Architecture-and-Mixins]]
- [[MC 26.2 概要に戻る|ja_jp-26.2-Home]]
