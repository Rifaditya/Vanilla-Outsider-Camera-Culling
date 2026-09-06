# 📦 方塊實體遮擋剔除 (Minecraft 26.1.2)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

方塊實體（箱子、終界箱、告示牌、旗幟、頭顱、飾紋陶罐、鐘以及信標）是 Minecraft 中的動態渲染元素。因為它們繞過了原版的靜態區塊網格（Chunk Mesh）編譯流程，所以每幀都會提交獨立的繪製調用（Draw Calls）。在擁有數百個箱子的大型儲物室或自動化分類倉儲區中，這會產生嚴重的 GPU 繪製瓶頸。

---

## 📋 方塊實體剔除資訊概覽

| 屬性 | 設定值 |
| :--- | :--- |
| **目標管線** | `BlockEntityRenderDispatcher.tryExtractRenderState(BlockEntity, float, CrumblingOverlay) (3 arguments)` （接受 3 個參數） |
| **密閉檢測** | 快速檢查周圍全部 6 個相鄰面: `up`, `down`, `north`, `south`, `east`, `west` |
| **保守模式** | 在 `LOW` 設定檔下僅執行密閉快速檢測 |
| **激進模式** | 在 `MEDIUM`、`HIGH`、`SUPER` 設定檔下執行完整的視線射線投射檢測 |
| **剔除效果** | 返回 `null` RenderState 以完全跳過後續的幾何生成與渲染提交 |

---

## 🔍 密閉與視線驗證架構

```text
               [UP]
                │
   [WEST] ── [CHEST] ── [EAST]
                │
              [DOWN]
```

### 1. 6面實心完全密閉快速判定
在執行任何耗費算力的射線運算之前，Camera Culling 會首先查詢方塊實體周圍 6 個方向的相鄰方塊狀態：
```java
BlockState up = level.getBlockState(pos.above());
BlockState down = level.getBlockState(pos.below());
BlockState north = level.getBlockState(pos.north());
BlockState south = level.getBlockState(pos.south());
BlockState east = level.getBlockState(pos.east());
BlockState west = level.getBlockState(pos.west());

if (up.isSolidRender() && down.isSolidRender() && north.isSolidRender()
    && south.isSolidRender() && east.isSolidRender() && west.isSolidRender()) {
    return true; // 100% 密閉遮擋 —— 完全跳過渲染
}
```
深埋在牆體內部或完全封閉在實心地下地基中的箱子，無需任何射線投射計算即可被快速剔除，耗時幾乎為零（$< 0.0001\mu\text{s}$）。

### 2. 視線射線投射檢測
在 `MEDIUM`、`HIGH` 和 `SUPER` 設定檔（`cullAllBlockEntities = true`）下，Camera Culling 會從玩家攝影機向方塊實體幾何中心 $(X + 0.5, Y + 0.5, Z + 0.5)$ 發射一條視線檢測射線：
* 若射線在觸達目標方塊實體前撞擊到了遮擋視線的實心方塊，則丟棄其渲染狀態。
* 若存在清晰無遮擋的視野通道，則正常執行方塊實體的完整渲染。

---

## 🔗 相關頁面

- [[告示牌與懸掛告示牌文本剔除|zh_tw-26.1.2-Sign-and-Hanging-Sign-Culling]]
- [[實體遮擋剔除|zh_tw-26.1.2-Entity-Occlusion-Culling]]
- [[架構與 Mixin|zh_tw-26.1.2-Architecture-and-Mixins]]
- [[返回 MC 26.1.2 概覽|zh_tw-26.1.2-Home]]
