# 🎨 基于距离的生物纹理 LOD (Minecraft 26.2)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

当远处的生物在玩家屏幕上仅占据 4x4 像素大小时，依然强制对其采样完整的 1024x1024 或高清材质纹理，会严重浪费显存带宽（VRAM Bandwidth）与纹理采样缓存行。

**Camera Culling** 内置了一套解耦的 3级 **OpenGL 纹理 LOD 偏移引擎**，能够根据实体与玩家之间的实际距离动态调节 Mipmap 采样层级。

---

## 📋 距离纹理 LOD 信息概览

| 属性 | 设定值 |
| :--- | :--- |
| **管线注入挂钩** | `LivingEntityRenderer.submit(...)` `@At("HEAD")` 与 `@At("RETURN")` |
| **OpenGL 调用参数** | `GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, bias)` |
| **近距离阶段** | $< 16.0$ 格 $\implies 0.0\text{f}$ 偏移量（100% 原生全分辨率纹理） |
| **中距离阶段** | $16.0 - 32.0$ 格 $\implies 1.0\text{f}$ 偏移量（50% 分辨率半采样 Mipmap） |
| **远距离阶段** | $> 32.0$ 格 $\implies 2.5\text{f}$ 偏移量（25% 超低分辨率 Mipmap） |
| **无条件豁免对象** | 处于发光状态的实体、玩家自身、Boss 与 Mini-Boss、黑名单保护生物 |

---

## 🔬 数学距离 LOD 偏移

```text
摄像机
  │
  ├── [ 0米 到 16米 ] ──► 偏移量 0.0f  (100% 原生全分辨率纹理)
  │
  ├── [ 16米 到 32米 ] ──► 偏移量 1.0f  (50% 次级 Mipmap 采样)
  │
  └── [ > 32米 ] ──────► 偏移量 2.5f  (25% 低阶 Mipmap 采样)
```

LOD 偏移量 $B$ 纯粹基于距离的平方计算，完全无多余开销：
$$B(d) = \begin{cases} 
0.0\text{f} & \text{if } d^2 < \text{startDist}^2 \ (16.0^2) \\
1.0\text{f} & \text{if } \text{startDist}^2 \le d^2 < \text{farDist}^2 \ (32.0^2) \\
2.5\text{f} & \text{if } d^2 \ge \text{farDist}^2
\end{cases}$$

### OpenGL 状态绝对隔离
在渲染生物时，`LivingEntityRendererMixin` 在 `submit:HEAD` 注入并设置计算出的偏移量，并在该生物渲染完成后的 `submit:RETURN` 瞬间将其无条件重置回 `0.0f`。这确保了方块模型、手持物品以及 UI 界面元素绝对不受任何影响。

---

## 🔗 相关页面

- [[Boss 与黑名单免疫|zh_cn-26.2-Boss-and-Blacklist-Immunity]]
- [[图形化界面配置 (YACL)|zh_cn-26.2-GUI-Configuration]]
- [[返回 MC 26.2 概览|zh_cn-26.2-Home]]
