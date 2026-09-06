# 🧱 實體遮擋剔除 (Minecraft 26.1.2)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

在 Minecraft 26.1.2 中，客戶端實體渲染管線會為視錐體（Frustum）內的所有實體提取渲染狀態（`EntityRenderState`）——即使它們完全隱藏在山脈、厚實的石頭牆壁之後或處於地下深處的地牢中。**Camera Culling** 透過在渲染管線前端介入視線檢測，徹底阻斷被遮擋生物對 CPU 幾何處理與 GPU 繪製調用（Draw Calls）的無謂消耗。

---

## 📋 實體剔除資訊概覽

| 屬性 | 設定值 |
| :--- | :--- |
| **目標管線** | `EntityRenderer.shouldRender(T, Frustum, double, double, double)` |
| **預設配置檔** | `SUPER` (Extreme) |
| **裁剪上下文** | `ClipContext.Block.COLLIDER`, `ClipContext.Fluid.NONE` |
| **地面判定過濾** | 命中高度 $\le Y + 0.15\text{m}$ 且命中面為 `Direction.UP` 時判定為有效視線 |
| **樹葉遮擋特性** | 實心渲染方塊與 `BlockTags.LEAVES` 標籤方塊均參與視線阻擋計算 |
| **免疫安全氣泡** | 距離平方 $< \text{minDistanceSq}$ |

---

## 🔬 多點解剖學視線採樣

不同於會導致生物在轉角處頻繁出現閃爍或畫面撕裂的簡陋單點射線投射，Camera Culling 會根據玩家選擇的[[設定檔|zh_tw-26.1.2-Commands-and-Configuration]]執行多點解剖學視線採樣：

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

1. **採樣點 1：實體頭頂最高點 (`maxY - 0.05`)**
   - 極高優先級採樣點。可精準檢測僅從低矮障礙物、矮牆或柵欄後露出頭頂的高大實體。
2. **採樣點 2：解剖學生理眼部位置 (`getEyeY()`)**
   - 評估從攝影機到生物眼部的直視視線。
3. **採樣點 3：上軀幹 / 胸部 (`minY + height * 0.70`)**
   - 評估生物上半身的視野通道，安全避開貼近地表的雜物遮擋。
4. **採樣點 4：實體幾何質量中心 (`(minY + maxY) * 0.5`)**
   - 實體的幾何中點綜合驗證。
5. **採樣點 5–8：抬高的外圍邊緣採樣點**
   - 分別評估 $(X_{\min} + 0.15, Z_{\min} + 0.15)$、$(X_{\max} - 0.15, Z_{\min} + 0.15)$ 等邊界點。確保體型龐大的首領生物（如劫掠獸、伏守者、蜘蛛）在肩膀剛剛露出轉角的一瞬間即可被玩家看見。

---

## 🛡️ 定向地面與坡度過濾

當玩家從高處向下俯視站立在不平整地形上的生物時，常規的直線射線投射容易碰撞到生物腳邊的方塊頂面，從而錯誤地將平坦地面當成阻擋視線的掩體牆壁。

Camera Culling 引入了**定向地面碰撞過濾機制**：
$$\text{If } \text{hit.getDirection()} == \text{Direction.UP} \quad \text{and} \quad Y_{\text{hit}} \le Y_{\text{target}} + 0.15\text{m} \implies \text{Valid Sightline (Not Blocked)}$$

這一數學防線保證了生物在爬坡、走樓梯以及在自然起伏地形上漫步時絕不會被誤剔除。

---

## ⚡ 零堆分配射線投射引擎

在早期的舊式實現中，每幀對 100 隻實體計算 8 個採樣點會產生超過每秒 180,000 次的 `new Vec3()` 堆記憶體分配，極易引發 JVM 新生代（Young-Gen）垃圾回收卡頓與幀率驟降。

在 26.1.2 中，`CullingRaycastHelper` 直接傳遞原生基本型別座標：
```java
public static boolean hasLineOfSight(Level level, Vec3 from, double toX, double toY, double toZ)
```
此架構消除了每一幀上的中間堆物件構造，實現完全零記憶體開銷的高速運算。

---

## 🔗 相關頁面

- [[生物密集過度繪製防禦|zh_tw-26.1.2-Mob-Crowd-Overdraw-Defense]]
- [[時間遲滯緩衝與零堆分配數學|zh_tw-26.1.2-Temporal-Hysteresis-and-Zero-Allocation]]
- [[Boss 與黑名單免疫|zh_tw-26.1.2-Boss-and-Blacklist-Immunity]]
- [[返回 MC 26.1.2 概覽|zh_tw-26.1.2-Home]]
