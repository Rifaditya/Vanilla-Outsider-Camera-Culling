# 🏛️ Architektur & Mixins (Minecraft 26.1.2)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Camera Culling basiert auf einer strikten **„1 Datei, 1 Zweck“**-Architektur und latenzfreien Mixin-Hooks, die speziell für die moderne, unobfuszierte **Minecraft 26.1.2**-Rendering-Engine entwickelt wurden.

---

## 📋 Mixin-Zielverzeichnis

| Mixin-Klasse | Minecraft-Zielklasse | Zielmethode & Injektionspunkt | Funktion |
| :--- | :--- | :--- | :--- |
| `EntityRendererMixin` | `net.minecraft.client.renderer.entity.EntityRenderer` | `shouldRender` `@At("HEAD")` | Mehrpunkt-Raycast-Okklusions-Culling & Mob-Dichteprüfungen |
| `BlockEntityRenderDispatcherMixin` | `net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher` | `tryExtractRenderState(BlockEntity, float, CrumblingOverlay)` `@At("HEAD")` | 6-seitige Umschließung & Raycast-Block-Entity-Culling (3 Argumente) |
| `BlockEntityRenderDispatcherMixin` | `net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher` | `tryExtractRenderState(BlockEntity, float, CrumblingOverlay)` `@At("RETURN")` | Zweiseitiges Schild-Rückseiten- & Leertext-Culling (3 Argumente) |
| `LivingEntityRendererMixin` | `net.minecraft.client.renderer.entity.LivingEntityRenderer` | `submit` `@At("HEAD")` & `@At("RETURN")` | Setzt & entfernt OpenGL Textur-LOD Mipmap-Bias (`GL_TEXTURE_LOD_BIAS`) |
| `SingleQuadParticleMixin` | `net.minecraft.client.particle.SingleQuadParticle` | `extract` `@At("HEAD")` | Okklusions-Culling für Quad-Partikel gegen solide Geometrie |
| `TextureAtlasMixin` | `net.minecraft.client.renderer.texture.TextureAtlas` | `cycleAnimationFrames` `@At("HEAD")` | Unterdrückt Uploads von animierten Texturen außerhalb des Bildschirms |

---

## 🌳 Paket-Hierarchie

```text
net.vanillaoutsider.culling
├── CameraCullingClient.java               (ClientModInitializer & Statistikzähler)
├── ModVersionGuard.java                   (Knot-Classloader Versions-Integritätsschutz)
│
├── command
│   └── CameraCullingCommand.java          (FabricClientCommandSource Brigadier-Syntaxbaum)
│
├── config
│   ├── CameraCullingConfig.java           (JSON-Serialisierung für Client- & Serverkonfiguration)
│   ├── CullingLevel.java                  (LOW, MEDIUM, HIGH, SUPER Intensitätsprofile)
│   ├── ModMenuIntegration.java            (ModMenu API-Einstiegspunkt mit verzögerter YACL-Factory)
│   └── YaclScreenHelper.java              (YetAnotherConfigLib v3 Screen Builder)
│
├── mixin
│   ├── BlockEntityRenderDispatcherMixin.java
│   ├── EntityRendererMixin.java
│   ├── LivingEntityRendererMixin.java
│   ├── SingleQuadParticleMixin.java
│   └── TextureAtlasMixin.java
│
└── util
    ├── AnimationCullingHelper.java        (Pausiert Atlas-Upload bei Pause oder Menüs)
    ├── BlacklistHelper.java               (Prüfung der Client- & Server-Entitätssperrliste)
    ├── BossDetectionHelper.java           (Dynamische HP-Schwellenwerte & Namensheuristiken)
    ├── CullingDiagnosticsHelper.java      (Echtzeit-Chat- & Log-Zustandsübergangsverfolgung)
    ├── CullingRaycastHelper.java          (Zero-Allocation primitive Raycasts & Hysterese)
    ├── ParticleCullingHelper.java         (4m-Näherungsblase & Sichtstrahl-Raycasting)
    ├── SignTextCullingHelper.java         (Vektornormalen-Skalarprodukte für 2-seitige Schilder)
    └── TextureLodHelper.java              (3-stufige OpenGL Mipmap-LOD-Bias-Berechnung)
```

---

## 🔗 Verwandte Seiten

- [[Entwickler-Setup & Build|de_de-26.1.2-Developer-Setup-and-Building]]
- [[API & Mod-Integration|de_de-26.1.2-API-and-Integration]]
- [[Zurück zur MC 26.1.2 Übersicht|de_de-26.1.2-Home]]
