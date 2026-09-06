# 🌐 版本兼容性与生命周期矩阵

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

本文档详细说明了 **Camera Culling** 的活跃跨时代发布矩阵、依赖版本边界、Java 运行时规范以及各版本间的字节码 API 差异。

> 📌 **仓库源代码免责声明**：本 Wiki 中的文档反映了**仓库中的当前源代码状态**，可能包含领先于 CurseForge 和 Modrinth 正式发布版本的未发布提交或开发中功能。

---

## 📋 多版本生命周期矩阵

| Minecraft 时代基准 | 目标 MC 版本 | 当前发布版本 | Java 环境要求 | Fabric Loader 边界 | Fabric API 边界 | 发布状态 |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| **Modern Sovereign (26.3)** | `26.3` | `1.10.1+26.3` | Java 25 (`release = 25`) | `>=0.18.4` | `0.156.1+26.3` | 🟢 活跃发布 (Active Release) |
| **Modern Sovereign (26.2)** | `26.2` | `1.10.1+26.2` | Java 25 (`release = 25`) | `>=0.18.4` | `0.145.4+26.2` | 🟢 活跃发布 (Active Release) |
| **Modern Sovereign (26.1.2)** | `26.1.2` | `1.10.2+26.1.2` | Java 25 (`release = 25`) | `>=0.18.4` | `0.145.4+26.1.2` | 🟢 活跃发布 (Active Release) |

---

## 🛠️ 各时代基准间的字节码 API 差异

### 1. 方块实体 RenderState 提取管线
* **Minecraft 26.1.2**：
  - `BlockEntityRenderDispatcher.tryExtractRenderState(BlockEntity, float, ModelFeatureRenderer$CrumblingOverlay)` —— 接受 **3 个参数**。
* **Minecraft 26.2 与 26.3**：
  - `BlockEntityRenderDispatcher.tryExtractRenderState(BlockEntity, float, ModelFeatureRenderer$CrumblingOverlay, boolean isGloballyRendered)` —— 接受 **4 个参数**。

### 2. 告示牌文本数据获取 API
* **Minecraft 26.1.2 与 26.2**：
  - 使用 `SignBlockEntity.getFrontText()` 与 `SignBlockEntity.getBackText()` 获取 `SignText`。
  - 使用 `SignText.getMessage(int index, boolean filtered)` 获取具体行文本的 `Component`。
* **Minecraft 26.3**：
  - 使用 `SignBlockEntity.getText(SignTextSlot.FRONT)` 与 `SignBlockEntity.getText(SignTextSlot.BACK)` 获取 `SignText`。
  - 使用 `SignText.getMessages(boolean filtered)` 获取包含全部行文本的 `Component` 数组。

---

## 📦 构建产物归档结构

所有正式发布构建均会自动编译并归档保存在主仓库的集中归档目录中：

```text
Archive Jar of all versions/
├── MC 26.1.2/
│   ├── vanilla-outsider-camera-culling-1.10.1+26.1.2.jar
│   └── vanilla-outsider-camera-culling-1.10.1+26.1.2-sources.jar
├── MC 26.2/
│   ├── vanilla-outsider-camera-culling-1.10.0+26.2.jar
│   └── vanilla-outsider-camera-culling-1.10.0+26.2-sources.jar
└── MC 26.3/
    ├── vanilla-outsider-camera-culling-1.10.0+26.3.jar
    └── vanilla-outsider-camera-culling-1.10.0+26.3-sources.jar
```

---

## 🔗 快捷链接

- [[👉 进入 Minecraft 26.3 百科|zh_cn-26.3-Home]]
- [[👉 进入 Minecraft 26.2 百科|zh_cn-26.2-Home]]
- [[👉 进入 Minecraft 26.1.2 百科|zh_cn-26.1.2-Home]]
- [[返回版本门户|zh_cn-Home]]
