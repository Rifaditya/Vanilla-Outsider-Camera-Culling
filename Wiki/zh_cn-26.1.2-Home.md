# 🟣 Camera Culling (Minecraft 26.1.2)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

欢迎来到 **Camera Culling** (`v1.10.2+26.1.2`) 的 **Minecraft 26.1.2** 专属文档中心。

> 📌 **仓库源代码免责声明**：本 Wiki 中的文档反映了**仓库中的当前源代码状态**，可能包含领先于 CurseForge 和 Modrinth 正式发布版本的未发布提交或开发中功能。

---

## 📋 Minecraft 26.1.2 快速信息概览

| 属性 | 设定值 |
| :--- | :--- |
| **目标 Minecraft 版本** | `26.1.2` |
| **模组发布版本** | `1.10.2+26.1.2` |
| **Java 环境要求** | Java 25 (`release = 25`) |
| **Fabric Loader** | `>=0.18.4` |
| **Fabric API** | `0.145.4+26.1.2` |
| **开源许可证** | GNU General Public License v3.0 (GPLv3) |
| **子项目目录路径** | `Camera Culling v26.1/Camera Culling 26.1` |

---

## ⚡ 核心功能架构

```text
Camera Culling 26.1.2 渲染管线
├── 实体遮挡剔除 (零堆分配射线投射引擎)
├── 方块实体遮挡剔除 (3参数 tryExtractRenderState 挂钩)
├── 双面告示牌文本剔除 (法向量点积数学计算)
├── 粒子遮挡剔除 (4米近距安全气泡 + 视觉裁剪检测)
├── 动画剔除 (纹理图集 TextureAtlas 上传抑制)
├── 基于距离的纹理 LOD (3级 OpenGL Mipmap 偏移)
├── Boss 与 Mini-Boss 免疫 (可自定义生命值阈值)
├── 抗闪烁时间迟滞缓冲 (自适应 4/8/12 帧宽限队列)
└── 图形化配置界面 (YACL v3 & ModMenu) + 游戏内指令系统
```

---

## 📚 26.1.2 完整文档索引

1. [[实体遮挡剔除|zh_cn-26.1.2-Entity-Occlusion-Culling]] — 多点解剖学视线采样与地面判定过滤。
2. [[方块实体遮挡剔除|zh_cn-26.1.2-Block-Entity-Culling]] — 箱子及方块实体的 6 面密闭与视线检测。
3. [[告示牌与悬挂告示牌文本剔除|zh_cn-26.1.2-Sign-and-Hanging-Sign-Culling]] — 双面法向量点积数学计算与空白面快速放行。
4. [[粒子与动画遮挡剔除|zh_cn-26.1.2-Particle-and-Animation-Culling]] — 地底粒子剔除与纹理图集动画冻结。
5. [[生物密集过度绘制防御|zh_cn-26.1.2-Mob-Crowd-Overdraw-Defense]] — 16米距离快速判定与 1.5米集群密度上限。
6. [[基于距离的纹理 LOD|zh_cn-26.1.2-Distance-Texture-LOD]] — 远距离生物群的 OpenGL Mipmap 纹理降级。
7. [[Boss 与黑名单免疫|zh_cn-26.1.2-Boss-and-Blacklist-Immunity]] — 首领生物保护机制与双层免疫黑名单。
8. [[时间迟滞缓冲与零堆分配数学|zh_cn-26.1.2-Temporal-Hysteresis-and-Zero-Allocation]] — 宽限缓冲区与零堆分配高性能架构。
9. [[指令与配置系统|zh_cn-26.1.2-Commands-and-Configuration]] — 完整的 Brigadier 指令语法与 JSON 配置参考。
10. [[图形化界面配置 (YACL)|zh_cn-26.1.2-GUI-Configuration]] — 现代化图形配置菜单操作指南。
11. [[调试日志与诊断追踪|zh_cn-26.1.2-Debug-Logging-and-Diagnostics]] — 实时状态转换日志与诊断输出系统。
12. [[架构与 Mixin|zh_cn-26.1.2-Architecture-and-Mixins]] — 源码包结构与 Mixin 注入点技术参考。
13. [[开发者环境与构建|zh_cn-26.1.2-Developer-Setup-and-Building]] — JDK 25 环境配置与 Loom Gradle 编译打包流程。
14. [[API 与模组集成|zh_cn-26.1.2-API-and-Integration]] — 编程式集成钩子与第三方渲染模组兼容性。

---

[[返回版本门户|zh_cn-Home]] &bull; [[版本兼容性|zh_cn-Version-Compatibility]]
