# 🏛️ 架构与 Mixin 技术参考 (Minecraft 26.3)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Camera Culling 采用严苛的 **“单一职责” (1 File, 1 Purpose)** 架构理念，专门针对非混淆的现代 **Minecraft 26.3** 客户端渲染引擎设计了零额外开销的轻量 Mixin 挂钩。

---

## 📋 Mixin 注入点技术账本

| Mixin 注入类 | 目标 Minecraft 类 | 拦截方法与注入点 | 功能说明 |
| :--- | :--- | :--- | :--- |
| `EntityRendererMixin` | `net.minecraft.client.renderer.entity.EntityRenderer` | `shouldRender` `@At("HEAD")` | 多点射线遮挡剔除与生物集群密度判定 |
| `BlockEntityRenderDispatcherMixin` | `net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher` | `tryExtractRenderState` `@At("HEAD")` | 6面完全密闭与射线方块实体遮挡剔除 |
| `BlockEntityRenderDispatcherMixin` | `net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher` | `tryExtractRenderState` `@At("RETURN")` | 双面告示牌反面与空白文本剔除 |
| `LivingEntityRendererMixin` | `net.minecraft.client.renderer.entity.LivingEntityRenderer` | `submit` `@At("HEAD")` 与 `@At("RETURN")` | 设定与重置 OpenGL 纹理 Mipmap 偏移量 (`GL_TEXTURE_LOD_BIAS`) |
| `SingleQuadParticleMixin` | `net.minecraft.client.particle.SingleQuadParticle` | `extract` `@At("HEAD")` | 粒子面片针对实心几何体的遮挡剔除 |
| `TextureAtlasMixin` | `net.minecraft.client.renderer.texture.TextureAtlas` | `cycleAnimationFrames` `@At("HEAD")` | 抑制单人暂停及菜单下的非必要纹理上传 |

---

## 🌳 源码包结构层次

```text
net.vanillaoutsider.culling
├── CameraCullingClient.java               (模组客户端初始化入口与统计计数器)
├── ModVersionGuard.java                   (Knot 类加载器版本完整性校验防护)
│
├── command
│   └── CameraCullingCommand.java          (FabricClientCommandSource Brigadier 指令树)
│
├── config
│   ├── CameraCullingConfig.java           (客户端与服务端配置的 JSON 序列化管理)
│   ├── CullingLevel.java                  (LOW, MEDIUM, HIGH, SUPER 强度配置档枚举)
│   ├── ModMenuIntegration.java            (ModMenu API 入口点与延迟 YACL 屏幕工厂)
│   └── YaclScreenHelper.java              (YetAnotherConfigLib v3 图形界面构建器)
│
├── mixin
│   ├── BlockEntityRenderDispatcherMixin.java
│   ├── EntityRendererMixin.java
│   ├── LivingEntityRendererMixin.java
│   ├── SingleQuadParticleMixin.java
│   └── TextureAtlasMixin.java
│
└── util
    ├── AnimationCullingHelper.java        (暂停与菜单状态下的纹理图集冻结判定)
    ├── BlacklistHelper.java               (客户端与服务端生物免疫黑名单判定)
    ├── BossDetectionHelper.java           (动态生命值阈值与名称关键词启发式判定)
    ├── CullingDiagnosticsHelper.java      (实时聊天栏与日志的状态转换追踪)
    ├── CullingRaycastHelper.java          (零堆分配原生射线投射与时间迟滞计算)
    ├── ParticleCullingHelper.java         (4米安全气泡与视觉裁剪射线投射)
    ├── SignTextCullingHelper.java         (双面告示牌朝向法向量点积数学计算)
    └── TextureLodHelper.java              (3级 OpenGL Mipmap LOD 偏移量计算)
```

---

## 🔗 相关页面

- [[开发者环境与构建|zh_cn-26.3-Developer-Setup-and-Building]]
- [[API 与模组集成|zh_cn-26.3-API-and-Integration]]
- [[返回 MC 26.3 概览|zh_cn-26.3-Home]]
