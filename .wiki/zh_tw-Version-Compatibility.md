# 🌐 版本相容性與生命週期矩陣

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

本文檔詳細說明了 **Camera Culling** 的活躍跨時代發布矩陣、依賴版本邊界、Java 執行環境規範以及各版本間的位元組碼 API 差異。

> 📌 **儲存庫原始碼免責聲明**：本 Wiki 中的文檔反映了**儲存庫中的當前原始碼狀態**，可能包含領先於 CurseForge 和 Modrinth 正式發布版本的未發布提交或開發中功能。

---

## 📋 多版本生命週期矩陣

| Minecraft 時代基準 | 目標 MC 版本 | 當前發布版本 | Java 環境要求 | Fabric Loader 邊界 | Fabric API 邊界 | 發布狀態 |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| **Modern Sovereign (26.3)** | `26.3` | `1.10.1+26.3` | Java 25 (`release = 25`) | `>=0.18.4` | `0.156.1+26.3` | 🟢 活躍發布 (Active Release) |
| **Modern Sovereign (26.2)** | `26.2` | `1.10.1+26.2` | Java 25 (`release = 25`) | `>=0.18.4` | `0.145.4+26.2` | 🟢 活躍發布 (Active Release) |
| **Modern Sovereign (26.1.2)** | `26.1.2` | `1.10.2+26.1.2` | Java 25 (`release = 25`) | `>=0.18.4` | `0.145.4+26.1.2` | 🟢 活躍發布 (Active Release) |

---

## 🛠️ 各時代基準間的位元組碼 API 差異

### 1. 方塊實體 RenderState 提取管線
* **Minecraft 26.1.2**：
  - `BlockEntityRenderDispatcher.tryExtractRenderState(BlockEntity, float, ModelFeatureRenderer$CrumblingOverlay)` —— 接受 **3 個參數**。
* **Minecraft 26.2 與 26.3**：
  - `BlockEntityRenderDispatcher.tryExtractRenderState(BlockEntity, float, ModelFeatureRenderer$CrumblingOverlay, boolean isGloballyRendered)` —— 接受 **4 個參數**。

### 2. 告示牌文本數據獲取 API
* **Minecraft 26.1.2 與 26.2**：
  - 使用 `SignBlockEntity.getFrontText()` 與 `SignBlockEntity.getBackText()` 獲取 `SignText`。
  - 使用 `SignText.getMessage(int index, boolean filtered)` 獲取具體行文本的 `Component`。
* **Minecraft 26.3**：
  - 使用 `SignBlockEntity.getText(SignTextSlot.FRONT)` 與 `SignBlockEntity.getText(SignTextSlot.BACK)` 獲取 `SignText`。
  - 使用 `SignText.getMessages(boolean filtered)` 獲取包含全部行文本的 `Component` 陣列。

---

## 📦 建置產物封存結構

所有正式發布建置均會自動編譯並封存保存在主儲存庫的集中封存目錄中：

```text
Archive Jar of all versions/
├── MC 26.1.2/
│   ├── vanilla-outsider-camera-culling-1.10.1+26.1.2.jar
│   └── vanilla-outsider-camera-culling-1.10.1+26.1.2-sources.jar
├── MC 26.2/
│   ├── vanilla-outsider-camera-culling-1.10.0+26.2.jar
│   └── vanilla-outsider-camera-culling-1.10.0+26.2-sources.jar
└── MC 26.3/
    ├── vanilla-outsider-camera-culling-1.10.0+26.3.jar
    └── vanilla-outsider-camera-culling-1.10.0+26.3-sources.jar
```

---

## 🔗 快速連結

- [[👉 進入 Minecraft 26.3 百科|zh_tw-26.3-Home]]
- [[👉 進入 Minecraft 26.2 百科|zh_tw-26.2-Home]]
- [[👉 進入 Minecraft 26.1.2 百科|zh_tw-26.1.2-Home]]
- [[返回版本門戶|zh_tw-Home]]
