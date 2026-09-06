# 🔌 API 與模組整合 (Minecraft 26.2)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Camera Culling 經過專業架構設計，能夠與主流客戶端渲染最佳化模組（如 Sodium、Iris、Canvas）以及增加自訂實體與方塊實體的內容模組實現 100% 無縫協同運行。

---

## 🤝 第三方渲染最佳化模組相容性

### 1. Sodium 與 Embeddium
* **靜態地形區塊**：Sodium 負責深度最佳化 16x16 區塊網格生成與靜態方塊面的渲染管線。
* **動態實體與方塊**：Camera Culling 專注於最佳化動態實體、箱子、告示牌與粒子系統。
* **相容性評級**：100% 完美共存，兩者沒有任何重疊的 Mixin 注入點或渲染狀態衝突。

### 2. Iris 與光影渲染 (Shaders)
* **光影全域統一變數**：光影包主要在提取出的幀緩衝區（FrameBuffer）上執行後處理。
* **幾何級遮擋收益**：由於被 Camera Culling 剔除的實體被直接阻斷向 G-Buffer 提交幾何頂點，光影在實體密集區域的渲染幀率會得到數倍的大幅提升。

---

## 🛠️ Java API 程式化掛鉤

其他模組可以透過靜態門面類別直接查詢或整合 Camera Culling 的底層功能：

### 1. 查詢剔除引擎即時狀態
```java
import net.vanillaoutsider.culling.CameraCullingClient;

boolean isEnabled = CameraCullingClient.isCullingEnabled();
long culledMobs = CameraCullingClient.getCulledEntitiesCount();
long renderedMobs = CameraCullingClient.getRenderedEntitiesCount();
```

### 2. 程式化實體免疫白名單
```java
import net.vanillaoutsider.culling.config.CameraCullingConfig;

// 將自訂隨從實體加入剔除白名單中
CameraCullingConfig.addClientBlacklist("mymod:custom_companion");
```

### 3. 直接調用零堆分配視線驗證
```java
import net.vanillaoutsider.culling.util.CullingRaycastHelper;

// 執行一次完全零堆記憶體開銷的視線檢測
boolean visible = CullingRaycastHelper.hasLineOfSight(level, camPos, targetX, targetY, targetZ);
```

---

## 🔗 相關頁面

- [[架構與 Mixin|zh_tw-26.2-Architecture-and-Mixins]]
- [[開發者環境與構建|zh_tw-26.2-Developer-Setup-and-Building]]
- [[返回 MC 26.2 概覽|zh_tw-26.2-Home]]
