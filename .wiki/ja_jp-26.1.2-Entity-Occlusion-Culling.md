# 🧱 エンティティの遮蔽カリング (Minecraft 26.1.2)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Minecraft 26.1.2 では、クライアントのエンティティレンダリングは、洞窟、崖、建築物の背後に隠れている場合であっても、カメラ錐台内のすべてのエンティティに対してレンダー状態を抽出します。**Camera Culling** はこの処理を遮り、遮蔽されたモブがCPUジオメトリ処理やGPU描画コールを浪費するのを防ぎます。

---

## 📋 エンティティカリング基本情報

| 項目 | 設定値 |
| :--- | :--- |
| **対象パイプライン** | `EntityRenderer.shouldRender(T, Frustum, double, double, double)` |
| **デフォルトプロファイル** | `SUPER`（最高強度） |
| **クリップコンテキスト** | `ClipContext.Block.COLLIDER`, `ClipContext.Fluid.NONE` |
| **床面フィルタリング** | 衝突高さが $\le Y + 0.15\text{m}$ の場合の `Direction.UP` 衝突を無視 |
| **葉ブロックの遮蔽** | 固体描画ブロックおよび `BlockTags.LEAVES` ブロックが視線を遮蔽 |
| **免疫バブル** | 距離の2乗 $< \text{minDistanceSq}$ |

---

## 🔬 マルチポイント解剖学的視線サンプリング

コーナー付近でモブがチラついて出現・消失する単一ポイントの単純なレイキャストとは異なり、Camera Culling は選択された[[カリングプロファイル|ja_jp-26.1.2-Commands-and-Configuration]]に基づいてマルチポイントの解剖学的サンプリングを実行します:

```text
       [1] Head Top (maxY - 0.05)
          \
           [2] Eye Position (entity.getEyeY())
            \
             [3] Upper Torso (minY + height * 0.70)
              \
               [4] Center of Mass (centerY)
                \
        [5-8] Elevated Perimeter Flanks (width/depth checks)
```

1. **サンプル 1: 頭頂部 (`maxY - 0.05`)**
   - 最優先判定。低い胸壁やフェンス越しに顔を覗かせている背の高いエンティティを検出します。
2. **サンプル 2: 解剖学的な目の位置 (`getEyeY()`)**
   - カメラからモブの視点への直接的な視線判定。
3. **サンプル 3: 上部胴体 / 胸部 (`minY + height * 0.70`)**
   - 地面の影響を受けにくい高さで上半身の視線を安全に評価します。
4. **サンプル 4: 重心・幾何学的中心 (`(minY + maxY) * 0.5`)**
   - 全体的な幾何学的中心点の判定。
5. **サンプル 5–8: 隆起周囲フランク**
   - $(X_{\min} + 0.15, Z_{\min} + 0.15)$ や $(X_{\max} - 0.15, Z_{\min} + 0.15)$ などを判定。大型ボス（ラヴェジャー、ウォーデン、クモなど）の肩先が角から現れた際に確実に視認可能にします。

---

## 🛡️ 方向性床面および傾斜フィルタリング

プレイヤーが凸凹な地形に立つモブを見下ろす際、標準的なレイキャストは足元付近のブロック上面に衝突し、地面を遮蔽壁と誤認してしまう場合があります。

Camera Culling は**方向性床面衝突フィルタリング**を搭載しています:
$$\text{もし } \text{hit.getDirection()} == \text{Direction.UP} \quad \text{かつ} \quad Y_{\text{hit}} \le Y_{\text{target}} + 0.15\text{m} \implies \text{有効な視線（遮蔽されていない）}$$

これにより、丘陵地、階段、不規則な地形を移動中のモブが誤ってカリングされるのを完全に防止します。

---

## ⚡ ゼロアロケーションレイキャストエンジン

過去の実装では、100体のエンティティに対して8箇所のサンプルポイントを判定する毎秒18万回以上の `new Vec3()` ヒープ割り当てが発生し、JVM Young-Gen ガベージコレクションによるカクつきの原因となっていました。

26.1.2 では、`CullingRaycastHelper` は生のプリミティブ座標を直接渡します:
```java
public static boolean hasLineOfSight(Level level, Vec3 from, double toX, double toY, double toZ)
```
このアーキテクチャにより、フレームごとの中間ヒープ割り当てが完全にゼロになります。

---

## 🔗 関連ページ

- [[密集モブのオーバードロー防御|ja_jp-26.1.2-Mob-Crowd-Overdraw-Defense]]
- [[時間的ヒステリシスとゼロアロケーション|ja_jp-26.1.2-Temporal-Hysteresis-and-Zero-Allocation]]
- [[ボスとブラックリスト免疫|ja_jp-26.1.2-Boss-and-Blacklist-Immunity]]
- [[MC 26.1.2 概要に戻る|ja_jp-26.1.2-Home]]
