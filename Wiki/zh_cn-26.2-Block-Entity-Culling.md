# 📦 方块实体遮挡剔除 (Minecraft 26.2)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

方块实体（箱子、末影箱、告示牌、旗帜、头颅、饰纹陶罐、钟以及信标）是 Minecraft 中的动态渲染元素。因为它们绕过了原版的静态区块网格（Chunk Mesh）编译流程，所以每帧都会提交独立的绘制调用（Draw Calls）。在拥有数百个箱子的大型储物室或自动化分类仓储区中，这会产生严重的 GPU 绘制瓶颈。

---

## 📋 方块实体剔除信息概览

| 属性 | 设定值 |
| :--- | :--- |
| **目标管线** | `BlockEntityRenderDispatcher.tryExtractRenderState(...)` （接受 4 个参数） |
| **密闭检测** | 快速检查周围全部 6 个相邻面: `up`, `down`, `north`, `south`, `east`, `west` |
| **保守模式** | 在 `LOW` 配置档下仅执行密闭快速检测 |
| **激进模式** | 在 `MEDIUM`、`HIGH`、`SUPER` 配置档下执行完整的视线射线投射检测 |
| **剔除效果** | 返回 `null` RenderState 以完全跳过后续的几何生成与渲染提交 |

---

## 🔍 密闭与视线验证架构

```text
               [UP]
                │
   [WEST] ── [CHEST] ── [EAST]
                │
              [DOWN]
```

### 1. 6面实心完全密闭快速判定
在执行任何耗费算力的射线运算之前，Camera Culling 会首先查询方块实体周围 6 个方向的相邻方块状态：
```java
BlockState up = level.getBlockState(pos.above());
BlockState down = level.getBlockState(pos.below());
BlockState north = level.getBlockState(pos.north());
BlockState south = level.getBlockState(pos.south());
BlockState east = level.getBlockState(pos.east());
BlockState west = level.getBlockState(pos.west());

if (up.isSolidRender() && down.isSolidRender() && north.isSolidRender()
    && south.isSolidRender() && east.isSolidRender() && west.isSolidRender()) {
    return true; // 100% 密闭遮挡 —— 完全跳过渲染
}
```
深埋在墙体内部或完全封闭在实心地下地基中的箱子，无需任何射线投射计算即可被快速剔除，耗时几乎为零（$< 0.0001\mu\text{s}$）。

### 2. 视线射线投射检测
在 `MEDIUM`、`HIGH` 和 `SUPER` 配置档（`cullAllBlockEntities = true`）下，Camera Culling 会从玩家摄像机向方块实体几何中心 $(X + 0.5, Y + 0.5, Z + 0.5)$ 发射一条视线检测射线：
* 若射线在触达目标方块实体前撞击到了遮挡视线的实心方块，则丢弃其渲染状态。
* 若存在清晰无遮挡的视野通道，则正常执行方块实体的完整渲染。

---

## 🔗 相关页面

- [[告示牌与悬挂告示牌文本剔除|zh_cn-26.2-Sign-and-Hanging-Sign-Culling]]
- [[实体遮挡剔除|zh_cn-26.2-Entity-Occlusion-Culling]]
- [[架构与 Mixin|zh_cn-26.2-Architecture-and-Mixins]]
- [[返回 MC 26.2 概览|zh_cn-26.2-Home]]
