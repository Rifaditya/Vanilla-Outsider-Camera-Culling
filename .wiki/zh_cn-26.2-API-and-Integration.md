# 🔌 API 与模组集成 (Minecraft 26.2)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Camera Culling 经过专业架构设计，能够与主流客户端渲染优化模组（如 Sodium、Iris、Canvas）以及增加自定义实体与方块实体的内容模组实现 100% 无缝协同运行。

---

## 🤝 第三方渲染优化模组兼容性

### 1. Sodium 与 Embeddium
* **静态地形区块**：Sodium 负责深度优化 16x16 区块网格生成与静态方块面的渲染管线。
* **动态实体与方块**：Camera Culling 专注于优化动态实体、箱子、告示牌与粒子系统。
* **兼容性评级**：100% 完美共存，两者没有任何重叠的 Mixin 注入点或渲染状态冲突。

### 2. Iris 与光影渲染 (Shaders)
* **光影全局统一变量**：光影包主要在提取出的帧缓冲区（FrameBuffer）上执行后处理。
* **几何级遮挡收益**：由于被 Camera Culling 剔除的实体被直接阻断向 G-Buffer 提交几何顶点，光影在实体密集区域的渲染帧率会得到数倍的大幅提升。

---

## 🛠️ Java API 编程挂钩

其他模组可以通过静态门面类直接查询或集成 Camera Culling 的底层功能：

### 1. 查询剔除引擎实时状态
```java
import net.vanillaoutsider.culling.CameraCullingClient;

boolean isEnabled = CameraCullingClient.isCullingEnabled();
long culledMobs = CameraCullingClient.getCulledEntitiesCount();
long renderedMobs = CameraCullingClient.getRenderedEntitiesCount();
```

### 2. 编程式实体免疫白名单
```java
import net.vanillaoutsider.culling.config.CameraCullingConfig;

// 将自定义随从实体加入剔除白名单中
CameraCullingConfig.addClientBlacklist("mymod:custom_companion");
```

### 3. 直接调用零堆分配视线验证
```java
import net.vanillaoutsider.culling.util.CullingRaycastHelper;

// 执行一次完全零堆内存开销的视线检测
boolean visible = CullingRaycastHelper.hasLineOfSight(level, camPos, targetX, targetY, targetZ);
```

---

## 🔗 相关页面

- [[架构与 Mixin|zh_cn-26.2-Architecture-and-Mixins]]
- [[开发者环境与构建|zh_cn-26.2-Developer-Setup-and-Building]]
- [[返回 MC 26.2 概览|zh_cn-26.2-Home]]
