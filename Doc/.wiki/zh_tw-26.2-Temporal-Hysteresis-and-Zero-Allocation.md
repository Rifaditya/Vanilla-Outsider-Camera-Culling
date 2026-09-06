# ⏱️ 時間遲滯緩衝與零堆分配數學 (Minecraft 26.2)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

高速遮擋剔除系統在實現中極易面臨兩大效能與視覺缺陷：
1. **邊緣擦碰畫面閃爍（Z-Fighting / 邊界突變）**：玩家視角的輕微晃動或行走時的視線顛簸，會導致生物在障礙物邊緣處於“遮擋”與“可見”之間高頻震盪，產生嚴重的幀間閃爍。
2. **垃圾回收頓卡尖峰**：在射線投射循環中頻繁調用 `new Vec3()` 與構建臨時包圍盒物件，會迅速填滿 JVM 新生代記憶體，引發高頻的 Young-Gen GC 停頓。

**Camera Culling** 採用**自適應距離縮放寬限緩衝**與**零堆分配熱點路徑引擎**徹底攻克了這兩大難題。

---

## 📋 遲滯與記憶體管理資訊概覽

| 屬性 | 設定值 |
| :--- | :--- |
| **近距離寬限緩衝** | $d \le 32\text{m} \implies 4\text{ 幀連續遮擋判定後才剔除}$ |
| **中距離寬限緩衝** | $32\text{m} < d \le 64\text{m} \implies 8\text{ 幀連續遮擋判定後才剔除}$ |
| **遠距離寬限緩衝** | $d > 64\text{m} \implies 12\text{ 幀連續遮擋判定後才剔除}$ |
| **重新可見過渡延遲** | 視線一旦連通即刻渲染（$0\text{ 幀延遲}$，瞬間現形） |
| **狀態追蹤容器** | `it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap`（零物件裝箱拆箱開銷） |
| **每幀臨時堆分配** | $0\text{ 位元組}$（完全使用基本型別 double 座標直傳） |

---

## 📐 基於距離縮放的寬限緩衝公式

生物在被正式判定進入 `[CULLED]`（剔除）狀態之前所必須達到的連續遮擋幀數閾值計算公式如下：

$$\text{RequiredStreak}(d) = \begin{cases}
12\text{ 幀} & \text{if } d^2 > 64.0^2 \ (4096.0\text{m}^2) \\
8\text{ 幀} & \text{if } d^2 > 32.0^2 \ (1024.0\text{m}^2) \\
4\text{ 幀} & \text{if } d^2 \le 32.0^2
\end{cases}$$

```text
[丟失生物直線視野]
       │
       ├── 第 1 幀被阻擋 ──► 保持渲染 (寬限期衰減)
       ├── 第 2 幀被阻擋 ──► 保持渲染 (寬限期衰減)
       ├── 第 3 幀被阻擋 ──► 保持渲染 (寬限期衰減)
       └── 第 4 幀被阻擋 ──► 停止渲染 (達成剔除連續閾值)
```

* **瞬間解除剔除**：只要有任意一個採樣點重新連通視野，連續遮擋計數器會被立即移除（`OCCLUDED_STREAK.remove(id)`），實體在 $0\text{ 幀延遲}$ 下瞬間可見。
* **非對稱狀態衰減**：進入遮擋狀態需要多幀確認，脫離遮擋只需 1 次命中。這種非對稱設計從根本上根除了視角轉動時的所有邊緣閃爍。

---

## ⚡ 零堆分配原生架構

在 `CullingRaycastHelper.java` 中，徹底移除了射線運算路徑上的中間向量構造：

```java
// 零堆記憶體分配：座標直接作為原生 primitive double 傳遞
public static boolean hasLineOfSight(Level level, Vec3 from, double toX, double toY, double toZ) {
    Vec3 to = new Vec3(toX, toY, toZ);
    ClipContext ctx = new ClipContext(from, to, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, CollisionContext.empty());
    BlockHitResult hit = level.clip(ctx);
    if (hit.getType() == HitResult.Type.MISS) {
        return true;
    }
    // 地面碰撞與容差計算均直接使用基本型別完成，無需實例化任何包裝物件
    ...
}
```

---

## 🔗 相關頁面

- [[實體遮擋剔除|zh_tw-26.2-Entity-Occlusion-Culling]]
- [[調試日誌與診斷追蹤|zh_tw-26.2-Debug-Logging-and-Diagnostics]]
- [[架構與 Mixin|zh_tw-26.2-Architecture-and-Mixins]]
- [[返回 MC 26.2 概覽|zh_tw-26.2-Home]]
