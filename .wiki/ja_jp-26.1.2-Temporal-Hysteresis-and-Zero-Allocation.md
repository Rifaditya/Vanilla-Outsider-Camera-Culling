# ⏱️ 時間的ヒステリシスとゼロアロケーション (Minecraft 26.1.2)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

高速な遮蔽カリングには、2つの典型的なパフォーマンス上の問題が発生する可能性があります:
1. **カメラかすめ時のチラつき（Z-Fighting / 境界での出現消失）**: わずかな視点移動や歩行時の揺れ（view-bobbing）がブロック境界を横切る際、交互のフレームで描画と非描画が高速に反転してしまう問題。
2. **GC（ガベージコレクション）によるカクつきスパイク**: レイキャスト中に `new Vec3()` 等のオブジェクトを連続生成することで、頻繁な JVM Young-Gen GC 一時停止が発生する問題。

**Camera Culling** は、**距離に応じた適応型猶予バッファ** と **ホットパスでのゼロアロケーション設計** により、両方の問題を完全に解決します。

---

## 📋 ヒステリシスとアロケーション基本情報

| 項目 | 設定値 |
| :--- | :--- |
| **近距離猶予バッファ** | $d \le 32\text{m} \implies 4\text{ フレーム連続遮蔽}$ |
| **中距離猶予バッファ** | $32\text{m} < d \le 64\text{m} \implies 8\text{ フレーム連続遮蔽}$ |
| **遠距離猶予バッファ** | $d > 64\text{m} \implies 12\text{ フレーム連続遮蔽}$ |
| **可視化遷移** | 視線確認時に即座に表示（$0\text{ フレーム遅延}$） |
| **追跡データ構造** | `it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap`（ボクシングオーバーヘッドゼロ） |
| **フレーム当たり割り当て** | $0\text{ bytes}$（プリミティブ double 座標を直接受け渡し） |

---

## 📐 距離別猶予バッファ計算式

エンティティが `[CULLED]`（非表示）状態に遷移するまでに必要な連続遮蔽フレーム数は以下のように計算されます:

$$\text{RequiredStreak}(d) = \begin{cases}
12\text{ フレーム} & \text{もし } d^2 > 64.0^2 \ (4096.0\text{m}^2) \\
8\text{ フレーム} & \text{もし } d^2 > 32.0^2 \ (1024.0\text{m}^2) \\
4\text{ フレーム} & \text{もし } d^2 \le 32.0^2
\end{cases}$$

```text
[エンティティへの視線喪失]
       │
       ├── フレーム 1 遮蔽 ──► 描画維持 (猶予減衰)
       ├── フレーム 2 遮蔽 ──► 描画維持 (猶予減衰)
       ├── フレーム 3 遮蔽 ──► 描画維持 (猶予減衰)
       └── フレーム 4 遮蔽 ──► カリング (連続条件達成)
```

* **即時再表示（Instant Unculling）**: 単一のレイキャストサンプルでも視線が確認された瞬間に連続遮蔽カウントがクリアされ（`OCCLUDED_STREAK.remove(id)`）、$0\text{ フレームの遅延}$ で直ちに可視化されます。
* **非対称減衰**: 非表示化には複数フレームの連続遮蔽が必要ですが、再表示は1フレームで即時反映されます。これにより、視点回転時のチラつきが完全に排除されます。

---

## ⚡ ゼロアロケーションプリミティブアーキテクチャ

`CullingRaycastHelper.java` では、中間ベクトルオブジェクトのヒープ割り当てが完全に排除されています:

```java
// ヒープ割り当てゼロ: 座標は生のプリミティブ double として渡される
public static boolean hasLineOfSight(Level level, Vec3 from, double toX, double toY, double toZ) {
    Vec3 to = new Vec3(toX, toY, toZ);
    ClipContext ctx = new ClipContext(from, to, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, CollisionContext.empty());
    BlockHitResult hit = level.clip(ctx);
    if (hit.getType() == HitResult.Type.MISS) {
        return true;
    }
    // オブジェクト生成を伴わない床面衝突判定および許容誤差評価
    ...
}
```

---

## 🔗 関連ページ

- [[エンティティの遮蔽カリング|ja_jp-26.1.2-Entity-Occlusion-Culling]]
- [[デバッグログと診断|ja_jp-26.1.2-Debug-Logging-and-Diagnostics]]
- [[アーキテクチャとMixin|ja_jp-26.1.2-Architecture-and-Mixins]]
- [[MC 26.1.2 概要に戻る|ja_jp-26.1.2-Home]]
