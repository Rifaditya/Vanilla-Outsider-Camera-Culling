# 🏛️ アーキテクチャとMixin (Minecraft 26.2)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Camera Culling は厳格な **「1ファイル、1責務」** のアーキテクチャと、難読化されていない近代的な **Minecraft 26.2** レンダリングエンジン向けに設計されたゼロオーバーヘッドの Mixin フックで構築されています。

---

## 📋 Mixin 注入先テーブル

| Mixin クラス | 対象Minecraftクラス | 対象メソッドおよび注入ポイント | 機能 |
| :--- | :--- | :--- | :--- |
| `EntityRendererMixin` | `net.minecraft.client.renderer.entity.EntityRenderer` | `shouldRender` `@At("HEAD")` | マルチポイントレイキャスト遮蔽カリングおよびモブクラスタ密度チェック |
| `BlockEntityRenderDispatcherMixin` | `net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher` | `tryExtractRenderState` `@At("HEAD")` | 6面固体閉塞判定およびレイキャストによるブロックエンティティカリング |
| `BlockEntityRenderDispatcherMixin` | `net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher` | `tryExtractRenderState` `@At("RETURN")` | 両面看板の背面および空白テキストのカリング |
| `LivingEntityRendererMixin` | `net.minecraft.client.renderer.entity.LivingEntityRenderer` | `submit` `@At("HEAD")` & `@At("RETURN")` | OpenGL テクスチャ LOD ミップマップバイアス（`GL_TEXTURE_LOD_BIAS`）の適用およびリセット |
| `SingleQuadParticleMixin` | `net.minecraft.client.particle.SingleQuadParticle` | `extract` `@At("HEAD")` | 固体ブロックに対する QuadParticle の遮蔽カリング |
| `TextureAtlasMixin` | `net.minecraft.client.renderer.texture.TextureAtlas` | `cycleAnimationFrames` `@At("HEAD")` | 画面外のアニメーションテクスチャアップロードの抑制 |

---

## 🌳 パッケージ構造

```text
net.vanillaoutsider.culling
├── CameraCullingClient.java               (ClientModInitializer および統計カウンター)
├── ModVersionGuard.java                   (Knot クラスローダーのバージョン整合性ガード)
│
├── command
│   └── CameraCullingCommand.java          (FabricClientCommandSource Brigadier 構文ツリー)
│
├── config
│   ├── CameraCullingConfig.java           (クライアント・サーバー設定の JSON シリアライズ)
│   ├── CullingLevel.java                  (LOW, MEDIUM, HIGH, SUPER 強度プロファイル)
│   ├── ModMenuIntegration.java            (遅延 YACL ファクトリを備えた ModMenu API エントリポイント)
│   └── YaclScreenHelper.java              (YetAnotherConfigLib v3 画面ビルダー)
│
├── mixin
│   ├── BlockEntityRenderDispatcherMixin.java
│   ├── EntityRendererMixin.java
│   ├── LivingEntityRendererMixin.java
│   ├── SingleQuadParticleMixin.java
│   └── TextureAtlasMixin.java
│
└── util
    ├── AnimationCullingHelper.java        (一時停止・メニュー表示時のアトラスアップロード一時停止)
    ├── BlacklistHelper.java               (クライアント・サーバーのエンティティブラックリスト判定)
    ├── BossDetectionHelper.java           (動的 HP 閾値および名前ヒューリスティクス判定)
    ├── CullingDiagnosticsHelper.java      (チャット・ログへのリアルタイム状態遷移追跡)
    ├── CullingRaycastHelper.java          (ゼロアロケーションプリミティブレイキャストとヒステリシス)
    ├── ParticleCullingHelper.java         (4m 近接バブルと視覚クリップレイキャスト)
    ├── SignTextCullingHelper.java         (両面看板用法線ベクトル内積計算)
    └── TextureLodHelper.java              (3段階 OpenGL ミップマップ LOD バイアス計算)
```

---

## 🔗 関連ページ

- [[開発環境のセットアップとビルド|ja_jp-26.2-Developer-Setup-and-Building]]
- [[APIとMod連携|ja_jp-26.2-API-and-Integration]]
- [[MC 26.2 概要に戻る|ja_jp-26.2-Home]]
