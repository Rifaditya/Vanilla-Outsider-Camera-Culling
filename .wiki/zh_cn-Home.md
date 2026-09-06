# 📷 Camera Culling 百科

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

欢迎来到 **Camera Culling** 官方文档门户。Camera Culling 是一款依据 **Vanilla Outsider** 设计哲学开发的适用于 Minecraft **26.1.2**、**26.2** 和 **26.3** 的高性能客户端渲染优化模组。

> 📌 **仓库源代码免责声明**：本 Wiki 中的文档反映了**仓库中的当前源代码状态**，可能包含领先于 CurseForge 和 Modrinth 正式发布版本的未发布提交或开发中功能。

---

## 🧭 多版本切换门户

Camera Culling 遵循严格的 **1 JAR 1 Version**（一个版本一个 JAR）原则开发。请在下方选择目标 Minecraft 版本以进入其专属、独立的文档树：

| 目标 Minecraft 版本 | 模组发布版本 | Java 运行环境 | 构建工具链 | 专属 Wiki 门户 |
| :--- | :--- | :--- | :--- | :--- |
| **Minecraft 26.1.2** | `1.10.2+26.1.2` | Java 25+ | Fabric Loom 1.15.5 | [[👉 进入 MC 26.1.2 百科|zh_cn-26.1.2-Home]] |
| **Minecraft 26.2** | `1.10.1+26.2` | Java 25+ | Fabric Loom 1.15.5 | [[👉 进入 MC 26.2 百科|zh_cn-26.2-Home]] |
| **Minecraft 26.3** | `1.10.1+26.3` | Java 25+ | Fabric Loom 1.15.5 | [[👉 进入 MC 26.3 百科|zh_cn-26.3-Home]] |

---

## ⚡ 核心优化矩阵

| 优化系统 | 主要机制 | 性能增益 |
| :--- | :--- | :--- |
| **零堆分配射线投射引擎** | 原生基本类型坐标视线检测 | 彻底消除视角转动时的 JVM 新生代 GC 停顿尖峰 |
| **抗闪烁时间迟滞** | 自适应 4/8/12 帧距离宽限缓冲区 | 消除方块边缘擦碰及视角晃动造成的画面闪烁 |
| **双面告示牌文本剔除** | 朝向法向量点积判定 ($ec{N} \cdot ec{V}$) | 减少 50%–100% 的告示牌文本绘制调用 (Draw Calls) |
| **粒子遮挡剔除** | 4米近距离安全气泡 + 视觉裁剪射线投射 | 停止渲染地底与被遮挡的粒子面片 (Quads) |
| **动画剔除** | 纹理图集 (Atlas) 上传抑制 | 暂停 3D 方块动画及屏幕外的纹理图集上传 |
| **生物密集过度绘制防御** | 16米快速失败 + 1.5米集群密度上限 | 消除密集刷怪场及动物繁育栏中的帧率卡顿 |
| **基于距离的纹理 LOD** | 3级 OpenGL Mipmap 偏移量 ($0.0 \to 1.0 \to 2.5$) | 极大降低远距离生物群的显存像素填充率 (VRAM Fillrate) |
| **Boss 与 Mini-Boss 免疫** | 动态生命值阈值判定与命名启发式过滤 | 防止战斗关键首领生物被误剔除 |
| **双层免疫黑名单** | 本地客户端 JSON + 服务端管理员同步 | 为随从与宠物定制专属白名单 |
| **方块实体遮挡剔除** | 6面实体完全密闭检测 | 跳过被实心方块完全包围的箱子与方块实体的渲染提取 |

---

## 📚 全局导航

- [[版本兼容性与生命周期矩阵|zh_cn-Version-Compatibility]]
- [[Minecraft 26.1.2 文档树|zh_cn-26.1.2-Home]]
- [[Minecraft 26.2 文档树|zh_cn-26.2-Home]]
- [[Minecraft 26.3 文档树|zh_cn-26.3-Home]]

---

<p align="center">
  <em>开发者: <strong>Dasik (Rifaditya)</strong> | 使用 <strong>GNU General Public License v3.0 (GPLv3)</strong> 授权</em>
</p>
