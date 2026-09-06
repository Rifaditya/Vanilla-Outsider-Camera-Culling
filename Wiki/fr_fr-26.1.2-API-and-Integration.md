# 🔌 API et intégration de mods (Minecraft 26.1.2)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Camera Culling est conçu pour fonctionner en parfaite synergie avec les mods de rendu côté client (comme Sodium, Iris, Canvas) et les mods de contenu introduisant de nouvelles entités ou entités de bloc.

---

## 🤝 Compatibilité avec les moteurs de rendu tiers

### 1. Sodium & Embeddium
* **Chunks de terrain statiques** : Sodium optimise le maillage des chunks 16x16 et le pipeline de rendu des faces de blocs statiques.
* **Entités dynamiques** : Camera Culling optimise le traitement des entités mobiles, des coffres, des panneaux et des particules.
* **Compatibilité** : 100 % Compatible, sans aucun chevauchement de Mixin ni conflit d'état.

### 2. Iris & Shaders
* **Uniforms des Shaders** : Les shaders effectuent un post-traitement sur le tampon d'image extrait.
* **Gains d'occlusion** : Les entités masquées ne soumettent aucune géométrie au G-Buffer, ce qui permet aux shaders de s'exécuter avec des cadences d'images par seconde nettement supérieures dans les zones encombrées.

---

## 🛠️ Points d'ancrage de l'API Java

D'autres mods peuvent interroger l'état de Camera Culling ou s'y intégrer via des façades utilitaires statiques :

### 1. Consultation de l'état du moteur d'occlusion
```java
import net.vanillaoutsider.culling.CameraCullingClient;

boolean isEnabled = CameraCullingClient.isCullingEnabled();
long culledMobs = CameraCullingClient.getCulledEntitiesCount();
long renderedMobs = CameraCullingClient.getRenderedEntitiesCount();
```

### 2. Ajout programmatique à la liste noire d'immunité
```java
import net.vanillaoutsider.culling.config.CameraCullingConfig;

// Exempte une entité personnalisée de l'occlusion
CameraCullingConfig.addClientBlacklist("mymod:custom_companion");
```

### 3. Vérification directe de la ligne de visée par raycast
```java
import net.vanillaoutsider.culling.util.CullingRaycastHelper;

// Effectue un test de ligne de visée sans allocation mémoire
boolean visible = CullingRaycastHelper.hasLineOfSight(level, camPos, targetX, targetY, targetZ);
```

---

## 🔗 Pages connexes

- [[Architecture et Mixins|fr_fr-26.1.2-Architecture-and-Mixins]]
- [[Configuration développeur et compilation|fr_fr-26.1.2-Developer-Setup-and-Building]]
- [[Retour à la vue d'ensemble MC 26.1.2|fr_fr-26.1.2-Home]]
