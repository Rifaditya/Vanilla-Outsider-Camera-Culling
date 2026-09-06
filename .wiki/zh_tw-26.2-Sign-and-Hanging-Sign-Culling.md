# 🪧 雙面告示牌與懸掛告示牌文本剔除 (Minecraft 26.2)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

在 Minecraft 26.2 中，告示牌具備雙面文本渲染特性（`getFrontText()` 與 `getBackText()`）。遊戲的字型渲染引擎會同時對正反兩面的字元四邊形、顏色及螢光輪廓進行渲染。

**Camera Culling** 透過計算告示牌朝向法向量與視線向量的點積（$\vec{N} \cdot \vec{V}$），配合空白文本快速放行邏輯，智慧停止渲染玩家不可見或無內容的告示牌文本面。

---

## 📋 告示牌剔除資訊概覽

| 屬性 | 設定值 |
| :--- | :--- |
| **目標管線** | `BlockEntityRenderDispatcher.tryExtractRenderState` `@At("RETURN")` |
| **API 攔截點** | `signState.frontText = null;` / `signState.backText = null;` |
| **支援類型** | 牆上告示牌、立式告示牌、牆上懸掛告示牌、天花板懸掛告示牌 |
| **空白面快速放行** | 自動跳過不含有效文本字元的空白面（無需執行三角幾何計算） |
| **點積容差裕度** | $\pm 0.05$ 容差可防止視線平行於告示牌邊緣時產生文字突變 |

---

## 📐 向量法線點積數學

為了判定告示牌的正面或背面是否朝向攝影機，Camera Culling 計算告示牌法向量 $\vec{N} = (N_x, N_z)$ 與告示牌中心指向攝影機的視線向量 $\vec{V} = (V_x, V_z)$ 之間的點積：

$$V_x = X_{\text{cam}} - (X_{\text{sign}} + 0.5), \quad V_z = Z_{\text{cam}} - (Z_{\text{sign}} + 0.5)$$

### 1. 牆上告示牌與牆上懸掛告示牌
法向量直接取自方塊的朝向方向屬性 `Direction`：
$$N_x = \text{facing.getStepX()}, \quad N_z = \text{facing.getStepZ()}$$

### 2. 立式告示牌與天花板懸掛告示牌
旋轉屬性由整數 $0 \dots 15$ 表示。對應的弧度角 $\theta$ 計算如下：
$$\theta = \left(\frac{\text{rotation} \times 360^\circ}{16}\right) \times \frac{\pi}{180}$$
$$N_x = -\sin\theta, \quad N_z = \cos\theta$$

### 3. 可見性判定
點積 $D$ 評估觀測視角的夾角關係：
$$D = N_x V_x + N_z V_z$$

* **正面評估**：當 $D < -0.05$ 時剔除（攝影機處於告示牌正面法線背向區域）。
* **背面評估**：當 $D > 0.05$ 時剔除（攝影機處於告示牌正面法線朝向區域）。

---

## ⚡ 空面快速放行

如果玩家僅在告示牌的一側書寫了文字，或者僅僅將告示牌作為阻擋水流或裝飾用的隔板，Camera Culling 會在第一時間檢查文本行內容：
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
空白的文本面會直接被置空（`null`），完全無需調用任何昂貴的三角函數或向量點積計算。

---

## 🔗 相關頁面

- [[方塊實體遮擋剔除|zh_tw-26.2-Block-Entity-Culling]]
- [[指令與設定系統|zh_tw-26.2-Commands-and-Configuration]]
- [[架構與 Mixin|zh_tw-26.2-Architecture-and-Mixins]]
- [[返回 MC 26.2 概覽|zh_tw-26.2-Home]]
