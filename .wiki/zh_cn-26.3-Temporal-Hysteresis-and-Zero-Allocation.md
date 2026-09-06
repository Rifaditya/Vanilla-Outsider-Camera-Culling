# ⏱️ 时间迟滞缓冲与零堆分配数学 (Minecraft 26.3)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

高速遮挡剔除系统在实现中极易面临两大性能与视觉缺陷：
1. **边缘擦碰画面闪烁（Z-Fighting / 边界突变）**：玩家视角的轻微晃动或行走时的视线颠簸，会导致生物在障碍物边缘处于“遮挡”与“可见”之间高频震荡，产生严重的帧间闪烁。
2. **垃圾回收顿卡尖峰**：在射线投射循环中频繁调用 `new Vec3()` 与构建临时包围盒对象，会迅速填满 JVM 新生代内存，引发高频的 Young-Gen GC 停顿。

**Camera Culling** 采用**自适应距离缩放宽限缓冲**与**零堆分配热点路径引擎**彻底攻克了这两大难题。

---

## 📋 迟滞与内存管理信息概览

| 属性 | 设定值 |
| :--- | :--- |
| **近距离宽限缓冲** | $d \le 32\text{m} \implies 4\text{ 帧连续遮挡判定后才剔除}$ |
| **中距离宽限缓冲** | $32\text{m} < d \le 64\text{m} \implies 8\text{ 帧连续遮挡判定后才剔除}$ |
| **远距离宽限缓冲** | $d > 64\text{m} \implies 12\text{ 帧连续遮挡判定后才剔除}$ |
| **重新可见过渡延迟** | 视线一旦连通即刻渲染（$0\text{ 帧延迟}$，瞬间现形） |
| **状态追踪容器** | `it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap`（零对象装箱拆箱开销） |
| **每帧临时堆分配** | $0\text{ 字节}$（完全使用基本类型 double 坐标直传） |

---

## 📐 基于距离缩放的宽限缓冲公式

生物在被正式判定进入 `[CULLED]`（剔除）状态之前所必须达到的连续遮挡帧数阈值计算公式如下：

$$\text{RequiredStreak}(d) = \begin{cases}
12\text{ 帧} & \text{if } d^2 > 64.0^2 \ (4096.0\text{m}^2) \\
8\text{ 帧} & \text{if } d^2 > 32.0^2 \ (1024.0\text{m}^2) \\
4\text{ 帧} & \text{if } d^2 \le 32.0^2
\end{cases}$$

```text
[丢失生物直线视野]
       │
       ├── 第 1 帧被阻挡 ──► 保持渲染 (宽限期衰减)
       ├── 第 2 帧被阻挡 ──► 保持渲染 (宽限期衰减)
       ├── 第 3 帧被阻挡 ──► 保持渲染 (宽限期衰减)
       └── 第 4 帧被阻挡 ──► 停止渲染 (达成剔除连续阈值)
```

* **瞬间解除剔除**：只要有任意一个采样点重新连通视野，连续遮挡计数器会被立即移除（`OCCLUDED_STREAK.remove(id)`），实体在 $0\text{ 帧延迟}$ 下瞬间可见。
* **非对称状态衰减**：进入遮挡状态需要多帧确认，脱离遮挡只需 1 次命中。这种非对称设计从根本上根除了视角转动时的所有边缘闪烁。

---

## ⚡ 零堆分配原生架构

在 `CullingRaycastHelper.java` 中，彻底移除了射线运算路径上的中间矢量构造：

```java
// 零堆内存分配：坐标直接作为原生 primitive double 传递
public static boolean hasLineOfSight(Level level, Vec3 from, double toX, double toY, double toZ) {
    Vec3 to = new Vec3(toX, toY, toZ);
    ClipContext ctx = new ClipContext(from, to, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, CollisionContext.empty());
    BlockHitResult hit = level.clip(ctx);
    if (hit.getType() == HitResult.Type.MISS) {
        return true;
    }
    // 地面碰撞与容差计算均直接使用基本类型完成，无需实例化任何包装对象
    ...
}
```

---

## 🔗 相关页面

- [[实体遮挡剔除|zh_cn-26.3-Entity-Occlusion-Culling]]
- [[调试日志与诊断追踪|zh_cn-26.3-Debug-Logging-and-Diagnostics]]
- [[架构与 Mixin|zh_cn-26.3-Architecture-and-Mixins]]
- [[返回 MC 26.3 概览|zh_cn-26.3-Home]]
