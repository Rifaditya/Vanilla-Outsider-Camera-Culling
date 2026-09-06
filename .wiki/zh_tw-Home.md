# 📷 Camera Culling 百科

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

歡迎來到 **Camera Culling** 官方文檔門戶。Camera Culling 是一款依據 **Vanilla Outsider** 設計哲學開發的適用於 Minecraft **26.1.2**、**26.2** 和 **26.3** 的高效能客戶端渲染最佳化模組。

> 📌 **儲存庫原始碼免責聲明**：本 Wiki 中的文檔反映了**儲存庫中的當前原始碼狀態**，可能包含領先於 CurseForge 和 Modrinth 正式發布版本的未發布提交或開發中功能。

---

## 🧭 多版本切換門戶

Camera Culling 遵循嚴格的 **1 JAR 1 Version**（一個版本一個 JAR）原則開發。請在下方選擇目標 Minecraft 版本以進入其專屬、獨立的文檔樹：

| 目標 Minecraft 版本 | 模組發布版本 | Java 執行環境 | 建置工具鏈 | 專屬 Wiki 門戶 |
| :--- | :--- | :--- | :--- | :--- |
| **Minecraft 26.1.2** | `1.10.2+26.1.2` | Java 25+ | Fabric Loom 1.15.5 | [[👉 進入 MC 26.1.2 百科|zh_tw-26.1.2-Home]] |
| **Minecraft 26.2** | `1.10.1+26.2` | Java 25+ | Fabric Loom 1.15.5 | [[👉 進入 MC 26.2 百科|zh_tw-26.2-Home]] |
| **Minecraft 26.3** | `1.10.1+26.3` | Java 25+ | Fabric Loom 1.15.5 | [[👉 進入 MC 26.3 百科|zh_tw-26.3-Home]] |

---

## ⚡ 核心最佳化矩陣

| 最佳化系統 | 主要機制 | 效能增益 |
| :--- | :--- | :--- |
| **零堆分配射線投射引擎** | 原生基本型別座標視線檢測 | 徹底消除視角轉動時的 JVM 新生代 GC 停頓尖峰 |
| **抗閃爍時間遲滯** | 自適應 4/8/12 幀距離寬限緩衝區 | 消除方塊邊緣擦碰及視角晃動造成的畫面閃爍 |
| **雙面告示牌文本剔除** | 朝向法向量點積判定 ($ec{N} \cdot ec{V}$) | 減少 50%–100% 的告示牌文本繪製調用 (Draw Calls) |
| **粒子遮擋剔除** | 4公尺近距離安全氣泡 + 視覺裁剪射線投射 | 停止渲染地底與被遮擋的粒子面片 (Quads) |
| **動畫剔除** | 紋理圖集 (Atlas) 上傳抑制 | 暫停 3D 方塊動畫及螢幕外的紋理圖集上傳 |
| **生物密集過度繪製防禦** | 16公尺快速失敗 + 1.5公尺集群密度上限 | 消除密集刷怪場及動物繁育欄中的幀率卡頓 |
| **基於距離的紋理 LOD** | 3級 OpenGL Mipmap 偏移量 ($0.0 \to 1.0 \to 2.5$) | 極大降低遠距離生物群的顯存像素填充率 (VRAM Fillrate) |
| **Boss 與 Mini-Boss 免疫** | 動態生命值閾值判定與命名啟發式過濾 | 防止戰鬥關鍵首領生物被誤剔除 |
| **雙層免疫黑名單** | 本地客戶端 JSON + 伺服器管理員同步 | 為隨從與寵物定制專屬白名單 |
| **方塊實體遮擋剔除** | 6面實體完全密閉檢測 | 跳過被實心方塊完全包圍的箱子與方塊實體的渲染提取 |

---

## 📚 全域導覽

- [[版本相容性與生命週期矩陣|zh_tw-Version-Compatibility]]
- [[Minecraft 26.1.2 文檔樹|zh_tw-26.1.2-Home]]
- [[Minecraft 26.2 文檔樹|zh_tw-26.2-Home]]
- [[Minecraft 26.3 文檔樹|zh_tw-26.3-Home]]

---

<p align="center">
  <em>開發者: <strong>Dasik (Rifaditya)</strong> | 使用 <strong>GNU General Public License v3.0 (GPLv3)</strong> 授權</em>
</p>
