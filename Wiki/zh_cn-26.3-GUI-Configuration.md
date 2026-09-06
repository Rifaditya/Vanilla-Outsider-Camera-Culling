# 🖥️ 图形化界面配置 (Minecraft 26.3)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Camera Culling 提供了由 **YetAnotherConfigLib (YACL v3)** 和 **ModMenu** 强力驱动的现代化图形配置菜单支持。

---

## 📋 GUI 集成信息概览

| 属性 | 设定值 |
| :--- | :--- |
| **支持的 GUI 引擎** | YetAnotherConfigLib v3 (YACL) + ModMenu |
| **集成架构模式** | 延迟类加载 (`ConfigScreenFactory`) |
| **服务端防崩安全** | 100% 绝对安全 —— 服务端入口点绝不引用客户端 GUI 类 |
| **菜单分类结构** | 3 个独立的标签页分类 |

---

## 🗂️ GUI 菜单分类结构

```text
Camera Culling 设置主屏幕
├── 1. 引擎与诊断追踪 (Engine & Diagnostics)
│   ├── 主功能启用开关 (勾选框)
│   ├── 剔除强度等级 (下拉列表: LOW, MEDIUM, HIGH, SUPER)
│   └── 实时调试日志开关 (勾选框)
│
├── 2. 实体与密集遮挡 (Entity & Crowd Occlusion)
│   ├── 生物密集过度绘制防御 (勾选框)
│   ├── 单集群生物数量上限 (滑块: 1 到 32)
│   ├── Boss 与 Mini-Boss 免疫 (勾选框)
│   ├── 主要 Boss 生命值阈值 (数值输入框, 默认: 150.0 HP)
│   └── Mini-Boss 生命值阈值 (数值输入框, 默认: 50.0 HP)
│
└── 3. 方块、粒子与动画 (Blocks, Particles & Animations)
    ├── 粒子遮挡剔除 (勾选框)
    ├── 方块与纹理图集动画冻结 (勾选框)
    ├── 双面告示牌文本剔除 (勾选框)
    ├── 基于距离的纹理 LOD (勾选框)
    ├── 纹理 LOD 近距离起点 (滑块: 8m 到 64m)
    └── 纹理 LOD 远距离终点 (滑块: 16m 到 128m)
```

---

## 🛡️ 延迟类加载与防崩溃安全设计

为了保证 Camera Culling 在未安装 YACL 的轻量客户端或专用独立服务器（Dedicated Server）上启动时绝不抛出 `ClassNotFoundException`，`ModMenuIntegration` 严格实现了延迟类加载工厂模式：

```java
public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        if (FabricLoader.getInstance().isModLoaded("yet_another_config_lib_v3")) {
            return YaclScreenHelper.createFactory();
        }
        return parent -> null;
    }
}
```

若环境中未安装 YACL，游戏依然能够无缝启动，玩家可以通过[[游戏内指令|zh_cn-26.3-Commands-and-Configuration]]或直接编辑 `config/camera-culling.json` 调整所有配置。

---

## 🔗 相关页面

- [[指令与配置系统|zh_cn-26.3-Commands-and-Configuration]]
- [[调试日志与诊断追踪|zh_cn-26.3-Debug-Logging-and-Diagnostics]]
- [[返回 MC 26.3 概览|zh_cn-26.3-Home]]
