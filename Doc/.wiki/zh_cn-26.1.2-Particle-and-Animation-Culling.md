# ✨ 粒子与动画遮挡剔除 (Minecraft 26.1.2)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

在原版 Minecraft 中，地底深处的岩浆滴落、洞穴粉尘、传送门粒子以及被墙壁遮挡的火把会在实心石墙背后不断生成数百个粒子面片，并提交至 GPU 渲染。同时，即便玩家在单人游戏中处于暂停状态或正在浏览菜单，方块纹理图集的动画帧依然会不间断地向 GPU 上传数据。

**Camera Culling** 为粒子系统带来了超高速的视线阻挡检测，并提供了纹理图集无用上传抑制机制。

---

## 📋 粒子与动画剔除信息概览

| 属性 | 设定值 |
| :--- | :--- |
| **粒子拦截目标** | `SingleQuadParticle.extract(QuadParticleRenderState, Camera, float)` |
| **动画拦截目标** | `TextureAtlas.cycleAnimationFrames()` |
| **近距安全气泡** | 摄像机周围 4.0 米 ($16.0\text{m}^2$) 范围内绝对保留 |
| **远距离硬截止** | 超过 64.0 米 ($4096.0\text{m}^2$) 的粒子直接剔除 |
| **图集暂停触发条件** | 单人游戏处于暂停状态 OR 客户端无活跃的世界/玩家实例 |

---

## 🌪️ 粒子遮挡判定管线

```text
在 (X, Y, Z) 处生成粒子
        │
        ▼
[1] 距离 <= 4米? ──────────► 正常渲染 (近距安全气泡)
        │ 否
        ▼
[2] 距离 > 64米? ──────────► 停止渲染 (远距离硬截止)
        │ 否
        ▼
[3] 位于实心方块内部? ──────► 停止渲染 (密闭剔除)
        │ 否
        ▼
[4] 视觉裁剪射线被阻挡?
        ├── 是 ──────────────► 停止渲染 (视线被遮挡)
        └── 否 ───────────────► 正常渲染 (视线通畅)
```

1. **近距离安全气泡 (4.0m)**：摄像机 4 米范围内释放的粒子（如药水漩涡、疾跑扬尘、横扫攻击弧光）无条件渲染，检测开销 $< 0.0001\mu\text{s}$。
2. **远距离硬截止 (64.0m)**：生成在 64 米之外的微小粒子会被主动剔除，以释放 GPU 的四边形填充率（Quad Fillrate）。
3. **实心方块密闭检测**：若粒子坐标所在的方块 `BlockPos.containing(x, y, z)` 具有 `isSolidRender() == true`，则立即丢弃该粒子。
4. **视觉裁剪射线投射**：使用 `ClipContext.Block.VISUAL` 从摄像机向粒子空间向量投射射线。若在距离目标 $> 0.35\text{m}$ 处被不透明方块阻截，则跳过渲染。

---

## 🎬 方块与纹理图集动画冻结

动态纹理图集动画（例如潮涌核心、流动的海晶灯、流动水与岩浆、火焰、指南针、时钟等）需要周期性循环帧索引并向 GPU 显存上传纹理数据。

Camera Culling 在 `TextureAtlasMixin` 中调用 `AnimationCullingHelper.shouldPauseAtlasAnimation()` 进行拦截：
* 当单人游戏处于暂停状态时（`mc.isPaused() == true`），完全冻结纹理图集的数据上传。
* 当处于主界面、设置菜单或断开与服务器的连接时，停止一切后台纹理循环。

---

## 🔗 相关页面

- [[实体遮挡剔除|zh_cn-26.1.2-Entity-Occlusion-Culling]]
- [[指令与配置系统|zh_cn-26.1.2-Commands-and-Configuration]]
- [[返回 MC 26.1.2 概览|zh_cn-26.1.2-Home]]
