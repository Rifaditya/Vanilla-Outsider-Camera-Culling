# ✨ 粒子與動畫遮擋剔除 (Minecraft 26.1.2)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

在原版 Minecraft 中，地底深處的岩漿滴落、洞穴粉塵、傳送門粒子以及被牆壁遮擋的火把會在實心石牆背後不斷生成數百個粒子面片，並提交至 GPU 渲染。同時，即便玩家在單人遊戲中處於暫停狀態或正在瀏覽功能表，方塊紋理圖集的動畫幀依然會不間斷地向 GPU 上傳數據。

**Camera Culling** 為粒子系統帶來了超高速的視線阻擋檢測，並提供了紋理圖集無用上傳抑制機制。

---

## 📋 粒子與動畫剔除資訊概覽

| 屬性 | 設定值 |
| :--- | :--- |
| **粒子攔截目標** | `SingleQuadParticle.extract(QuadParticleRenderState, Camera, float)` |
| **動畫攔截目標** | `TextureAtlas.cycleAnimationFrames()` |
| **近距安全氣泡** | 攝影機周圍 4.0 公尺 ($16.0\text{m}^2$) 範圍內絕對保留 |
| **遠距離硬截止** | 超過 64.0 公尺 ($4096.0\text{m}^2$) 的粒子直接剔除 |
| **圖集暫停觸發條件** | 單人遊戲處於暫停狀態 OR 客戶端無活躍的世界/玩家實例 |

---

## 🌪️ 粒子遮擋判定管線

```text
在 (X, Y, Z) 處生成粒子
        │
        ▼
[1] 距離 <= 4公尺? ──────────► 正常渲染 (近距安全氣泡)
        │ 否
        ▼
[2] 距離 > 64公尺? ──────────► 停止渲染 (遠距離硬截止)
        │ 否
        ▼
[3] 位於實心方塊內部? ──────► 停止渲染 (密閉剔除)
        │ 否
        ▼
[4] 視覺裁剪射線被阻擋?
        ├── 是 ──────────────► 停止渲染 (視線被遮擋)
        └── 否 ───────────────► 正常渲染 (視線通暢)
```

1. **近距離安全氣泡 (4.0m)**：攝影機 4 公尺範圍內釋放的粒子（如藥水漩渦、疾跑揚塵、橫掃攻擊弧光）無條件渲染，檢測開銷 $< 0.0001\mu\text{s}$。
2. **遠距離硬截止 (64.0m)**：生成在 64 公尺之外的微小粒子會被主動剔除，以釋放 GPU 的四邊形填充率（Quad Fillrate）。
3. **實心方塊密閉檢測**：若粒子座標所在的方塊 `BlockPos.containing(x, y, z)` 具有 `isSolidRender() == true`，則立即丟棄該粒子。
4. **視覺裁剪射線投射**：使用 `ClipContext.Block.VISUAL` 從攝影機向粒子空間向量投射射線。若在距離目標 $> 0.35\text{m}$ 處被不透明方塊阻截，則跳過渲染。

---

## 🎬 方塊與紋理圖集動畫凍結

動態紋理圖集動畫（例如潮湧核心、流動的海晶燈、流動水與岩漿、火焰、指南針、時鐘等）需要週期性循環幀索引並向 GPU 顯存上傳紋理數據。

Camera Culling 在 `TextureAtlasMixin` 中調用 `AnimationCullingHelper.shouldPauseAtlasAnimation()` 進行攔截：
* 當單人遊戲處於暫停狀態時（`mc.isPaused() == true`），完全凍結紋理圖集的數據上傳。
* 當處於主介面、設定功能表或斷開與伺服器的連線時，停止一切後台紋理循環。

---

## 🔗 相關頁面

- [[實體遮擋剔除|zh_tw-26.1.2-Entity-Occlusion-Culling]]
- [[指令與設定系統|zh_tw-26.1.2-Commands-and-Configuration]]
- [[返回 MC 26.1.2 概覽|zh_tw-26.1.2-Home]]
