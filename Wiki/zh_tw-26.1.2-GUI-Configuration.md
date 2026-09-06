# 🖥️ 圖形化介面設定 (Minecraft 26.1.2)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Camera Culling 提供了由 **YetAnotherConfigLib (YACL v3)** 和 **ModMenu** 強力驅動的現代化圖形設定功能表支援。

---

## 📋 GUI 整合資訊概覽

| 屬性 | 設定值 |
| :--- | :--- |
| **支援的 GUI 引擎** | YetAnotherConfigLib v3 (YACL) + ModMenu |
| **整合架構模式** | 延遲類別載入 (`ConfigScreenFactory`) |
| **伺服器端防崩安全** | 100% 絕對安全 —— 伺服器端入口點絕不引用客戶端 GUI 類別 |
| **功能表分類結構** | 3 個獨立的標籤頁分類 |

---

## 🗂️ GUI 功能表分類結構

```text
Camera Culling 設定主畫面
├── 1. 引擎與診斷追蹤 (Engine & Diagnostics)
│   ├── 主功能啟用開關 (勾選框)
│   ├── 剔除強度等級 (下拉列表: LOW, MEDIUM, HIGH, SUPER)
│   └── 即時調試日誌開關 (勾選框)
│
├── 2. 實體與密集遮擋 (Entity & Crowd Occlusion)
│   ├── 生物密集過度繪製防禦 (勾選框)
│   ├── 單集群生物數量上限 (滑塊: 1 到 32)
│   ├── Boss 與 Mini-Boss 免疫 (勾選框)
│   ├── 主要 Boss 生命值閾值 (數值輸入框, 預設: 150.0 HP)
│   └── Mini-Boss 生命值閾值 (數值輸入框, 預設: 50.0 HP)
│
└── 3. 方塊、粒子與動畫 (Blocks, Particles & Animations)
    ├── 粒子遮擋剔除 (勾選框)
    ├── 方塊與紋理圖集動畫凍結 (勾選框)
    ├── 雙面告示牌文本剔除 (勾選框)
    ├── 基於距離的紋理 LOD (勾選框)
    ├── 紋理 LOD 近距離起點 (滑塊: 8m 到 64m)
    └── 紋理 LOD 遠距離終點 (滑塊: 16m 到 128m)
```

---

## 🛡️ 延遲類別載入與防崩潰安全設計

為了保證 Camera Culling 在未安裝 YACL 的輕量客戶端或專用獨立伺服器（Dedicated Server）上啟動時絕不拋出 `ClassNotFoundException`，`ModMenuIntegration` 嚴格實現了延遲類別載入工廠模式：

```java
public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        if (FabricLoader.getInstance().isModLoaded("yet_another_config_lib_v3")) {
            return YaclScreenHelper.createFactory();
        }
        return parent -> null;
    }
}
```

若環境中未安裝 YACL，遊戲依然能夠無縫啟動，玩家可以透過[[遊戲內指令|zh_tw-26.1.2-Commands-and-Configuration]]或直接編輯 `config/camera-culling.json` 調整所有設定。

---

## 🔗 相關頁面

- [[指令與設定系統|zh_tw-26.1.2-Commands-and-Configuration]]
- [[調試日誌與診斷追蹤|zh_tw-26.1.2-Debug-Logging-and-Diagnostics]]
- [[返回 MC 26.1.2 概覽|zh_tw-26.1.2-Home]]
