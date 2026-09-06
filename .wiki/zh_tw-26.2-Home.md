# 🔵 Camera Culling (Minecraft 26.2)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

歡迎來到 **Camera Culling** (`v1.10.1+26.2`) 的 **Minecraft 26.2** 專屬文檔中心。

> 📌 **儲存庫原始碼免責聲明**：本 Wiki 中的文檔反映了**儲存庫中的當前原始碼狀態**，可能包含領先於 CurseForge 和 Modrinth 正式發布版本的未發布提交或開發中功能。

---

## 📋 Minecraft 26.2 快速資訊概覽

| 屬性 | 設定值 |
| :--- | :--- |
| **目標 Minecraft 版本** | `26.2` |
| **模組發布版本** | `1.10.1+26.2` |
| **Java 環境要求** | Java 25 (`release = 25`) |
| **Fabric Loader** | `>=0.18.4` |
| **Fabric API** | `0.145.4+26.2` |
| **開源許可證** | GNU General Public License v3.0 (GPLv3) |
| **子專案目錄路徑** | `Camera Culling v26.2/Camera Culling 26.2` |

---

## ⚡ 核心功能架構

```text
Camera Culling 26.2 渲染管線
├── 實體遮擋剔除 (零堆分配射線投射引擎)
├── 方塊實體遮擋剔除 (6面完全密閉與視線檢測)
├── 雙面告示牌文本剔除 (法向量點積數學計算)
├── 粒子遮擋剔除 (4公尺近距安全氣泡 + 視覺裁剪檢測)
├── 動畫剔除 (紋理圖集 TextureAtlas 上傳抑制)
├── 基於距離的紋理 LOD (3級 OpenGL Mipmap 偏移)
├── Boss 與 Mini-Boss 免疫 (可自訂生命值閾值)
├── 抗閃爍時間遲滯緩衝 (自適應 4/8/12 幀寬限隊列)
└── 圖形化設定介面 (YACL v3 & ModMenu) + 遊戲內指令系統
```

---

## 📚 26.2 完整文檔索引

1. [[實體遮擋剔除|zh_tw-26.2-Entity-Occlusion-Culling]] — 多點解剖學視線採樣與地面判定過濾。
2. [[方塊實體遮擋剔除|zh_tw-26.2-Block-Entity-Culling]] — 箱子及方塊實體的 6 面密閉與視線檢測。
3. [[告示牌與懸掛告示牌文本剔除|zh_tw-26.2-Sign-and-Hanging-Sign-Culling]] — 雙面法向量點積數學計算與空白面快速放行。
4. [[粒子與動畫遮擋剔除|zh_tw-26.2-Particle-and-Animation-Culling]] — 地底粒子剔除與紋理圖集動畫凍結。
5. [[生物密集過度繪製防禦|zh_tw-26.2-Mob-Crowd-Overdraw-Defense]] — 16公尺距離快速判定與 1.5公尺集群密度上限。
6. [[基於距離的紋理 LOD|zh_tw-26.2-Distance-Texture-LOD]] — 遠距離生物群的 OpenGL Mipmap 紋理降級。
7. [[Boss 與黑名單免疫|zh_tw-26.2-Boss-and-Blacklist-Immunity]] — 首領生物保護機制與雙層免疫黑名單。
8. [[時間遲滯緩衝與零堆分配數學|zh_tw-26.2-Temporal-Hysteresis-and-Zero-Allocation]] — 寬限緩衝區與零堆分配高效能架構。
9. [[指令與設定系統|zh_tw-26.2-Commands-and-Configuration]] — 完整的 Brigadier 指令語法與 JSON 設定參考。
10. [[圖形化介面設定 (YACL)|zh_tw-26.2-GUI-Configuration]] — 現代化圖形設定功能表操作指南。
11. [[調試日誌與診斷追蹤|zh_tw-26.2-Debug-Logging-and-Diagnostics]] — 即時狀態轉換日誌與診斷輸出系統。
12. [[架構與 Mixin|zh_tw-26.2-Architecture-and-Mixins]] — 原始碼套件結構與 Mixin 注入點技術參考。
13. [[開發者環境與構建|zh_tw-26.2-Developer-Setup-and-Building]] — JDK 25 環境配置與 Loom Gradle 編譯打包流程。
14. [[API 與模組整合|zh_tw-26.2-API-and-Integration]] — 程式化整合鉤子與第三方渲染模組相容性。

---

[[返回版本門戶|zh_tw-Home]] &bull; [[版本相容性與生命週期矩陣|zh_tw-Version-Compatibility]]
