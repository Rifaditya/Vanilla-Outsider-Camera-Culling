# 🔌 API & Mod-Integration (Minecraft 26.3)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Camera Culling ist so konzipiert, dass es reibungslos mit clientseitigen Rendering-Mods (wie Sodium, Iris, Canvas) und Inhalts-Mods zusammenarbeitet, die benutzerdefinierte Entitäten oder Block-Entities hinzufügen.

---

## 🤝 Kompatibilität mit Drittanbieter-Renderern

### 1. Sodium & Embeddium
* **Statische Geländechunks**: Sodium optimiert das 16x16-Chunk-Meshing und die Rendering-Pipeline statischer Blockflächen.
* **Dynamische Entitäten**: Camera Culling optimiert dynamische Entitäten, Truhen, Schilder und Partikel.
* **Kompatibilität**: Zu 100% kompatibel ohne überlappende Mixins oder Statuskollisionen.

### 2. Iris & Shader
* **Shader-Uniforms**: Shader führen Post-Processing auf dem extrahierten Frame-Buffer durch.
* **Okklusions-Einsparungen**: Da ausgeblendete Entitäten keine Geometrie an den G-Buffer übermitteln, laufen Shader in dicht bebauten Bereichen mit deutlich höheren Bildraten.

---

## 🛠️ Programmatische Java-API-Hooks

Andere Mods können den Zustand von Camera Culling über statische Hilfsfassaden abfragen oder sich dort einklinken:

### 1. Culling-Engine-Status abfragen
```java
import net.vanillaoutsider.culling.CameraCullingClient;

boolean isEnabled = CameraCullingClient.isCullingEnabled();
long culledMobs = CameraCullingClient.getCulledEntitiesCount();
long renderedMobs = CameraCullingClient.getRenderedEntitiesCount();
```

### 2. Programmatische Immunitäts-Sperrliste
```java
import net.vanillaoutsider.culling.config.CameraCullingConfig;

// Eine benutzerdefinierte Entitäts-ID vom Culling ausnehmen
CameraCullingConfig.addClientBlacklist("mymod:custom_companion");
```

### 3. Direkte Raycast-Sichtlinienüberprüfung
```java
import net.vanillaoutsider.culling.util.CullingRaycastHelper;

// Führt einen allokationsfreien Sichtlinientest durch
boolean visible = CullingRaycastHelper.hasLineOfSight(level, camPos, targetX, targetY, targetZ);
```

---

## 🔗 Verwandte Seiten

- [[Architektur & Mixins|de_de-26.3-Architecture-and-Mixins]]
- [[Entwickler-Setup & Build|de_de-26.3-Developer-Setup-and-Building]]
- [[Zurück zur MC 26.3 Übersicht|de_de-26.3-Home]]
