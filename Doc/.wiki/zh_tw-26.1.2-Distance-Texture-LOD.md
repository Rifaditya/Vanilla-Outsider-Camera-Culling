# 🎨 基於距離的生物紋理 LOD (Minecraft 26.1.2)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

當遠處的生物在玩家螢幕上僅佔據 4x4 像素大小時，依然強制對其採樣完整的 1024x1024 或高畫質材質紋理，會嚴重浪費顯存頻寬（VRAM Bandwidth）與紋理採樣快取行。

**Camera Culling** 內建了一套解耦的 3級 **OpenGL 紋理 LOD 偏移引擎**，能夠根據實體與玩家之間的實際距離動態調節 Mipmap 採樣層級。

---

## 📋 距離紋理 LOD 資訊概覽

| 屬性 | 設定值 |
| :--- | :--- |
| **管線注入掛鉤** | `LivingEntityRenderer.submit(...)` `@At("HEAD")` 與 `@At("RETURN")` |
| **OpenGL 調用參數** | `GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, bias)` |
| **近距離階段** | $< 16.0$ 格 $\implies 0.0\text{f}$ 偏移量（100% 原生全解析度紋理） |
| **中距離階段** | $16.0 - 32.0$ 格 $\implies 1.0\text{f}$ 偏移量（50% 解析度半採樣 Mipmap） |
| **遠距離階段** | $> 32.0$ 格 $\implies 2.5\text{f}$ 偏移量（25% 超低解析度 Mipmap） |
| **無條件豁免對象** | 處於發光狀態的實體、玩家自身、Boss 與 Mini-Boss、黑名單保護生物 |

---

## 🔬 數學距離 LOD 偏移

```text
攝影機
  │
  ├── [ 0公尺 到 16公尺 ] ──► 偏移量 0.0f  (100% 原生全解析度紋理)
  │
  ├── [ 16公尺 到 32公尺 ] ──► 偏移量 1.0f  (50% 次級 Mipmap 採樣)
  │
  └── [ > 32公尺 ] ──────► 偏移量 2.5f  (25% 低階 Mipmap 採樣)
```

LOD 偏移量 $B$ 純粹基於距離的平方計算，完全無多餘開銷：
$$B(d) = \begin{cases} 
0.0\text{f} & \text{if } d^2 < \text{startDist}^2 \ (16.0^2) \\
1.0\text{f} & \text{if } \text{startDist}^2 \le d^2 < \text{farDist}^2 \ (32.0^2) \\
2.5\text{f} & \text{if } d^2 \ge \text{farDist}^2
\end{cases}$$

### OpenGL 狀態絕對隔離
在渲染生物時，`LivingEntityRendererMixin` 在 `submit:HEAD` 注入並設定計算出的偏移量，並在該生物渲染完成後的 `submit:RETURN` 瞬間將其無條件重設回 `0.0f`。這確保了方塊模型、手持物品以及 UI 介面元素絕對不受任何影響。

---

## 🔗 相關頁面

- [[Boss 與黑名單免疫|zh_tw-26.1.2-Boss-and-Blacklist-Immunity]]
- [[圖形化介面設定 (YACL)|zh_tw-26.1.2-GUI-Configuration]]
- [[返回 MC 26.1.2 概覽|zh_tw-26.1.2-Home]]
