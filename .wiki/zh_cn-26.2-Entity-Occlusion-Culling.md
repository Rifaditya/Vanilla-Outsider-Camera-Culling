# 🧱 实体遮挡剔除 (Minecraft 26.2)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

在 Minecraft 26.2 中，客户端实体渲染管线会为视锥体（Frustum）内的所有实体提取渲染状态（`EntityRenderState`）——即使它们完全隐藏在山脉、厚实的石头墙壁之后或处于地下深处的地牢中。**Camera Culling** 通过在渲染管线前端介入视线检测，彻底阻断被遮挡生物对 CPU 几何处理与 GPU 绘制调用（Draw Calls）的无谓消耗。

---

## 📋 实体剔除信息概览

| 属性 | 设定值 |
| :--- | :--- |
| **目标管线** | `EntityRenderer.shouldRender(T, Frustum, double, double, double)` |
| **默认配置档** | `SUPER` (Extreme) |
| **裁剪上下文** | `ClipContext.Block.COLLIDER`, `ClipContext.Fluid.NONE` |
| **地面判定过滤** | 命中高度 $\le Y + 0.15\text{m}$ 且命中面为 `Direction.UP` 时判定为有效视线 |
| **树叶遮挡特性** | 实心渲染方块与 `BlockTags.LEAVES` 标签方块均参与视线阻挡计算 |
| **免疫安全气泡** | 距离平方 $< \text{minDistanceSq}$ |

---

## 🔬 多点解剖学视线采样

不同于会导致生物在转角处频繁出现闪烁或画面撕裂的简陋单点射线投射，Camera Culling 会根据玩家选择的[[配置档|zh_cn-26.2-Commands-and-Configuration]]执行多点解剖学视线采样：

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

1. **采样点 1：实体头顶最高点 (`maxY - 0.05`)**
   - 极高优先级采样点。可精准检测仅从低矮障碍物、矮墙或栅栏后露出头顶的高大实体。
2. **采样点 2：解剖学生理眼部位置 (`getEyeY()`)**
   - 评估从摄像机到生物眼部的直视视线。
3. **采样点 3：上躯干 / 胸部 (`minY + height * 0.70`)**
   - 评估生物上半身的视野通道，安全避开贴近地表的杂物遮挡。
4. **采样点 4：实体几何质量中心 (`(minY + maxY) * 0.5`)**
   - 实体的几何中点综合验证。
5. **采样点 5–8：抬高的外围边缘采样点**
   - 分别评估 $(X_{\min} + 0.15, Z_{\min} + 0.15)$、$(X_{\max} - 0.15, Z_{\min} + 0.15)$ 等边界点。确保体型庞大的首领生物（如劫掠兽、监守者、蜘蛛）在肩膀刚刚露出转角的一瞬间即可被玩家看见。

---

## 🛡️ 定向地面与坡度过滤

当玩家从高处向下俯视站立在不平整地形上的生物时，常规的直线射线投射容易碰撞到生物脚边的方块顶面，从而错误地将平坦地面当成阻挡视线的掩体墙壁。

Camera Culling 引入了**定向地面碰撞过滤机制**：
$$\text{If } \text{hit.getDirection()} == \text{Direction.UP} \quad \text{and} \quad Y_{\text{hit}} \le Y_{\text{target}} + 0.15\text{m} \implies \text{Valid Sightline (Not Blocked)}$$

这一数学防线保证了生物在爬坡、走楼梯以及在自然起伏地形上漫步时绝不会被误剔除。

---

## ⚡ 零堆分配射线投射引擎

在早期的旧式实现中，每帧对 100 只实体计算 8 个采样点会产生超过每秒 180,000 次的 `new Vec3()` 堆内存分配，极易引发 JVM 新生代（Young-Gen）垃圾回收卡顿与帧率骤降。

在 26.2 中，`CullingRaycastHelper` 直接传递原生基本类型坐标：
```java
public static boolean hasLineOfSight(Level level, Vec3 from, double toX, double toY, double toZ)
```
此架构消除了每一帧上的中间堆对象构造，实现完全零内存开销的高速运算。

---

## 🔗 相关页面

- [[生物密集过度绘制防御|zh_cn-26.2-Mob-Crowd-Overdraw-Defense]]
- [[时间迟滞缓冲与零堆分配数学|zh_cn-26.2-Temporal-Hysteresis-and-Zero-Allocation]]
- [[Boss 与黑名单免疫|zh_cn-26.2-Boss-and-Blacklist-Immunity]]
- [[返回 MC 26.2 概览|zh_cn-26.2-Home]]
