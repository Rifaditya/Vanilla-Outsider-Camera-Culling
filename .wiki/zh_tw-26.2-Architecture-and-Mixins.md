# 🏛️ 架構與 Mixin 技術參考 (Minecraft 26.2)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Camera Culling 採用嚴苛的 **“單一職責” (1 File, 1 Purpose)** 架構理念，專門針對非混淆的現代 **Minecraft 26.2** 客戶端渲染引擎設計了零額外開銷的輕量 Mixin 掛鉤。

---

## 📋 Mixin 注入點技術帳本

| Mixin 注入類別 | 目標 Minecraft 類別 | 攔截方法與注入點 | 功能說明 |
| :--- | :--- | :--- | :--- |
| `EntityRendererMixin` | `net.minecraft.client.renderer.entity.EntityRenderer` | `shouldRender` `@At("HEAD")` | 多點射線遮擋剔除與生物集群密度判定 |
| `BlockEntityRenderDispatcherMixin` | `net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher` | `tryExtractRenderState` `@At("HEAD")` | 6面完全密閉與射線方塊實體遮擋剔除 |
| `BlockEntityRenderDispatcherMixin` | `net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher` | `tryExtractRenderState` `@At("RETURN")` | 雙面告示牌反面與空白文本剔除 |
| `LivingEntityRendererMixin` | `net.minecraft.client.renderer.entity.LivingEntityRenderer` | `submit` `@At("HEAD")` 與 `@At("RETURN")` | 設定與重設 OpenGL 紋理 Mipmap 偏移量 (`GL_TEXTURE_LOD_BIAS`) |
| `SingleQuadParticleMixin` | `net.minecraft.client.particle.SingleQuadParticle` | `extract` `@At("HEAD")` | 粒子面片針對實心幾何體的遮擋剔除 |
| `TextureAtlasMixin` | `net.minecraft.client.renderer.texture.TextureAtlas` | `cycleAnimationFrames` `@At("HEAD")` | 抑制單人暫停及功能表下的非必要紋理上傳 |

---

## 🌳 原始碼套件結構層次

```text
net.vanillaoutsider.culling
├── CameraCullingClient.java               (模組客戶端初始化入口與統計計數器)
├── ModVersionGuard.java                   (Knot 類別載入器版本完整性校驗防護)
│
├── command
│   └── CameraCullingCommand.java          (FabricClientCommandSource Brigadier 指令樹)
│
├── config
│   ├── CameraCullingConfig.java           (客戶端與伺服器端設定的 JSON 序列化管理)
│   ├── CullingLevel.java                  (LOW, MEDIUM, HIGH, SUPER 強度設定檔列舉)
│   ├── ModMenuIntegration.java            (ModMenu API 入口點與延遲 YACL 畫面工廠)
│   └── YaclScreenHelper.java              (YetAnotherConfigLib v3 圖形介面構建器)
│
├── mixin
│   ├── BlockEntityRenderDispatcherMixin.java
│   ├── EntityRendererMixin.java
│   ├── LivingEntityRendererMixin.java
│   ├── SingleQuadParticleMixin.java
│   └── TextureAtlasMixin.java
│
└── util
    ├── AnimationCullingHelper.java        (暫停與功能表狀態下的紋理圖集凍結判定)
    ├── BlacklistHelper.java               (客戶端與伺服器端生物免疫黑名單判定)
    ├── BossDetectionHelper.java           (動態生命值閾值與名稱關鍵字啟發式判定)
    ├── CullingDiagnosticsHelper.java      (即時聊天欄與日誌的狀態轉換追蹤)
    ├── CullingRaycastHelper.java          (零堆分配原生射線投射與時間遲滯計算)
    ├── ParticleCullingHelper.java         (4公尺安全氣泡與視覺裁剪射線投射)
    ├── SignTextCullingHelper.java         (雙面告示牌朝向法向量點積數學計算)
    └── TextureLodHelper.java              (3級 OpenGL Mipmap LOD 偏移量計算)
```

---

## 🔗 相關頁面

- [[開發者環境與構建|zh_tw-26.2-Developer-Setup-and-Building]]
- [[API 與模組整合|zh_tw-26.2-API-and-Integration]]
- [[返回 MC 26.2 概覽|zh_tw-26.2-Home]]
