# 🌐 Versionskompatibilitäts-Matrix

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Dieses Dokument beschreibt die aktive Versionsmatrix, Abhängigkeitsgrenzen, Java-Laufzeitspezifikationen und Bytecode-API-Unterschiede für **Camera Culling**.

> 📌 **Quellcode-Haftungsausschluss**: Die Dokumentation in diesem Wiki spiegelt den **aktuellen Quellcode-Stand im Repository** wider, welcher noch unveröffentlichte Commits oder Entwicklungsfunktionen vor den offiziellen Release-Builds auf CurseForge und Modrinth enthalten kann.

---

## 📋 Multi-Versions-Lebenszyklus-Matrix

| Minecraft-Anker-Ära | Ziel-MC-Version | Aktuelle Release-Version | Java-Anforderung | Fabric-Loader-Grenze | Fabric-API-Grenze | Release-Status |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| **Modern Sovereign (26.3)** | `26.3` | `1.10.1+26.3` | Java 25 (`release = 25`) | `>=0.18.4` | `0.156.1+26.3` | 🟢 Aktiver Release |
| **Modern Sovereign (26.2)** | `26.2` | `1.10.1+26.2` | Java 25 (`release = 25`) | `>=0.18.4` | `0.145.4+26.2` | 🟢 Aktiver Release |
| **Modern Sovereign (26.1.2)** | `26.1.2` | `1.10.2+26.1.2` | Java 25 (`release = 25`) | `>=0.18.4` | `0.145.4+26.1.2` | 🟢 Aktiver Release |

---

## 🛠️ Bytecode-API-Unterschiede zwischen den Versionen

### 1. Block-Entity-RenderState-Extraktionspipeline
* **Minecraft 26.1.2**:
  - `BlockEntityRenderDispatcher.tryExtractRenderState(BlockEntity, float, ModelFeatureRenderer$CrumblingOverlay)` — benötigt **3 Argumente**.
* **Minecraft 26.2 & 26.3**:
  - `BlockEntityRenderDispatcher.tryExtractRenderState(BlockEntity, float, ModelFeatureRenderer$CrumblingOverlay, boolean isGloballyRendered)` — benötigt **4 Argumente**.

### 2. API zum Abrufen von Schildtexten
* **Minecraft 26.1.2 & 26.2**:
  - `SignBlockEntity.getFrontText()` und `SignBlockEntity.getBackText()` rufen `SignText` ab.
  - `SignText.getMessage(int index, boolean filtered)` ruft das Zeilen-`Component` ab.
* **Minecraft 26.3**:
  - `SignBlockEntity.getText(SignTextSlot.FRONT)` und `SignBlockEntity.getText(SignTextSlot.BACK)` rufen `SignText` ab.
  - `SignText.getMessages(boolean filtered)` ruft das Zeilen-`Component`-Array ab.

---

## 📦 Speicherorte des Build-Artefakt-Archivs

Alle Release-Builds werden automatisch kompiliert und in der zentralen Archivstruktur des übergeordneten Repositorys aufbewahrt:

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

## 🔗 Schnellzugriff

- [[👉 MC 26.3 Wiki öffnen|de_de-26.3-Home]]
- [[👉 MC 26.2 Wiki öffnen|de_de-26.2-Home]]
- [[👉 MC 26.1.2 Wiki öffnen|de_de-26.1.2-Home]]
- [[Zurück zum Portal|de_de-Home]]
