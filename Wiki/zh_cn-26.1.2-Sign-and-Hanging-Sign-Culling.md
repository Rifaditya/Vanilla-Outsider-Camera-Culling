# 🪧 双面告示牌与悬挂告示牌文本剔除 (Minecraft 26.1.2)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

在 Minecraft 26.1.2 中，告示牌具备双面文本渲染特性（`getFrontText()` 与 `getBackText()`）。游戏的字体渲染引擎会同时对正反两面的字符四边形、颜色及荧光轮廓进行渲染。

**Camera Culling** 通过计算告示牌朝向法向量与视线向量的点积（$\vec{N} \cdot \vec{V}$），配合空白文本快速放行逻辑，智能停止渲染玩家不可见或无内容的告示牌文本面。

---

## 📋 告示牌剔除信息概览

| 属性 | 设定值 |
| :--- | :--- |
| **目标管线** | `BlockEntityRenderDispatcher.tryExtractRenderState` `@At("RETURN")` |
| **API 拦截点** | `signState.frontText = null;` / `signState.backText = null;` |
| **支持类型** | 墙上告示牌、立式告示牌、墙上悬挂告示牌、天花板悬挂告示牌 |
| **空白面快速放行** | 自动跳过不含有效文本字符的空白面（无需执行三角几何计算） |
| **点积容差裕度** | $\pm 0.05$ 容差可防止视线平行于告示牌边缘时产生文字突变 |

---

## 📐 向量法线点积数学

为了判定告示牌的正面或背面是否朝向摄像机，Camera Culling 计算告示牌法向量 $\vec{N} = (N_x, N_z)$ 与告示牌中心指向摄像机的视线向量 $\vec{V} = (V_x, V_z)$ 之间的点积：

$$V_x = X_{\text{cam}} - (X_{\text{sign}} + 0.5), \quad V_z = Z_{\text{cam}} - (Z_{\text{sign}} + 0.5)$$

### 1. 墙上告示牌与墙上悬挂告示牌
法向量直接取自方块的朝向方向属性 `Direction`：
$$N_x = \text{facing.getStepX()}, \quad N_z = \text{facing.getStepZ()}$$

### 2. 立式告示牌与天花板悬挂告示牌
旋转属性由整数 $0 \dots 15$ 表示。对应的弧度角 $\theta$ 计算如下：
$$\theta = \left(\frac{\text{rotation} \times 360^\circ}{16}\right) \times \frac{\pi}{180}$$
$$N_x = -\sin\theta, \quad N_z = \cos\theta$$

### 3. 可见性判定
点积 $D$ 评估观测视角的夹角关系：
$$D = N_x V_x + N_z V_z$$

* **正面评估**：当 $D < -0.05$ 时剔除（摄像机处于告示牌正面法线背向区域）。
* **背面评估**：当 $D > 0.05$ 时剔除（摄像机处于告示牌正面法线朝向区域）。

---

## ⚡ 空面快速放行

如果玩家仅在告示牌的一侧书写了文字，或者仅仅将告示牌作为阻挡水流或装饰用的隔板，Camera Culling 会在第一时间检查文本行内容：
```java
public static boolean isTextEmpty(SignText text) {
    if (text == null) return true;
    for (int i = 0; i < 4; i++) {
        Component msg = text.getMessage(i, false);
        if (msg != null && !msg.getString().trim().isEmpty()) {
            return false;
        }
    }
    return true;
}
```
空白的文本面会直接被置空（`null`），完全无需调用任何昂贵的三角函数或向量点积计算。

---

## 🔗 相关页面

- [[方块实体遮挡剔除|zh_cn-26.1.2-Block-Entity-Culling]]
- [[指令与配置系统|zh_cn-26.1.2-Commands-and-Configuration]]
- [[架构与 Mixin|zh_cn-26.1.2-Architecture-and-Mixins]]
- [[返回 MC 26.1.2 概览|zh_cn-26.1.2-Home]]
