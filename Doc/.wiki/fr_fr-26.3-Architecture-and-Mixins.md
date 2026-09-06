# 🏛️ Architecture et Mixins (Minecraft 26.3)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Camera Culling est conçu selon une architecture stricte **« 1 Fichier, 1 Objectif »** et des points d'ancrage Mixin à latence nulle spécialement adaptés au moteur de rendu non obscurci de **Minecraft 26.3**.

---

## 📋 Registre des cibles Mixin

| Classe Mixin | Classe Minecraft ciblée | Méthode ciblée & Point d'injection | Fonction |
| :--- | :--- | :--- | :--- |
| `EntityRendererMixin` | `net.minecraft.client.renderer.entity.EntityRenderer` | `shouldRender` `@At("HEAD")` | Occlusion multipoint par raycast & vérification de densité de foule |
| `BlockEntityRenderDispatcherMixin` | `net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher` | `tryExtractRenderState` `@At("HEAD")` | Occlusion des entités de bloc par 6 faces et raycast |
| `BlockEntityRenderDispatcherMixin` | `net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher` | `tryExtractRenderState` `@At("RETURN")` | Occlusion de face arrière et de texte vierge des panneaux |
| `LivingEntityRendererMixin` | `net.minecraft.client.renderer.entity.LivingEntityRenderer` | `submit` `@At("HEAD")` & `@At("RETURN")` | Applique et réinitialise le biais de LOD de texture OpenGL (`GL_TEXTURE_LOD_BIAS`) |
| `SingleQuadParticleMixin` | `net.minecraft.client.particle.SingleQuadParticle` | `extract` `@At("HEAD")` | Occlusion des QuadParticle contre la géométrie solide |
| `TextureAtlasMixin` | `net.minecraft.client.renderer.texture.TextureAtlas` | `cycleAnimationFrames` `@At("HEAD")` | Supprime les envois de textures animées situées hors écran |

---

## 🌳 Hiérarchie des packages

```text
net.vanillaoutsider.culling
├── CameraCullingClient.java               (ClientModInitializer & compteurs statistiques)
├── ModVersionGuard.java                   (Garde d'intégrité de version du classloader Knot)
│
├── command
│   └── CameraCullingCommand.java          (Arbre de syntaxe Brigadier FabricClientCommandSource)
│
├── config
│   ├── CameraCullingConfig.java           (Sérialisation JSON des configurations client & serveur)
│   ├── CullingLevel.java                  (Profils d'intensité LOW, MEDIUM, HIGH, SUPER)
│   ├── ModMenuIntegration.java            (Point d'entrée ModMenu avec factory YACL différée)
│   └── YaclScreenHelper.java              (Générateur d'écran YetAnotherConfigLib v3)
│
├── mixin
│   ├── BlockEntityRenderDispatcherMixin.java
│   ├── EntityRendererMixin.java
│   ├── LivingEntityRendererMixin.java
│   ├── SingleQuadParticleMixin.java
│   └── TextureAtlasMixin.java
│
└── util
    ├── AnimationCullingHelper.java        (Suspend l'envoi d'atlas en pause ou dans les menus)
    ├── BlacklistHelper.java               (Évaluation de la liste noire d'entités client & serveur)
    ├── BossDetectionHelper.java           (Seuils de vie dynamiques & heuristiques de noms)
    ├── CullingDiagnosticsHelper.java      (Traçage des transitions d'état dans le chat & les logs)
    ├── CullingRaycastHelper.java          (Raycasting primitif sans allocation mémoire & hystérésis)
    ├── ParticleCullingHelper.java         (Bulle de proximité 4m & raycasting visuel)
    ├── SignTextCullingHelper.java         (Produits scalaires de vecteurs normaux pour panneaux)
    └── TextureLodHelper.java              (Calcul du biais de LOD de texture OpenGL à 3 niveaux)
```

---

## 🔗 Pages connexes

- [[Configuration développeur et compilation|fr_fr-26.3-Developer-Setup-and-Building]]
- [[API et intégration de mods|fr_fr-26.3-API-and-Integration]]
- [[Retour à la vue d'ensemble MC 26.3|fr_fr-26.3-Home]]
