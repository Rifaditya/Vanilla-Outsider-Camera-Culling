# 🛠️ Entwickler-Setup & Build (Minecraft 26.3)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Dieser technische Leitfaden beschreibt die Umgebungsvoraussetzungen, das Gradle-Loom-Tooling und die Kompilierungsanweisungen zum Erstellen von **Camera Culling** für **Minecraft 26.3**.

---

## 📋 Umgebungsvoraussetzungen

* **Java Development Kit (JDK)**: **JDK 25+** (z. B. Eclipse Adoptium Temurin 25).
* **Gradle Wrapper**: Version 9.3+ mit Loom 1.15.5 (`net.fabricmc.fabric-loom`).
* **Laufzeitumgebung**: Unobfuszierte Mojang-Laufzeitumgebung (der Mappings-Block in `build.gradle` ist in 26.x strikt untersagt).

---

## 🏗️ Gradle-Build-Befehle

Öffne ein Terminal im Unterprojektverzeichnis `Camera Culling v26.3/Camera Culling 26.3`:

```bash
# Unit-Testsuite ausführen
./gradlew test --no-daemon

# Release-JAR kompilieren und verpacken
./gradlew build --no-daemon
```

### Automatische Archivierungs-Pipeline
Die Datei `build.gradle` des Unterprojekts enthält eine automatisierte Archivierungsaufgabe:
* Ausgabe-JAR: `build/libs/vanilla-outsider-camera-culling-1.10.0+26.3.jar`
* Archivierter Speicherort: `Archive Jar of all versions/MC 26.3/`
* Modrinth-Profilsynchronisation: Wird automatisch in dein lokales Launcher-Profil installiert (`Fabric 26.3ish/mods/`).

---

## 📄 fabric.mod.json-Metadaten

```json
{
  "schemaVersion": 1,
  "id": "camera-culling",
  "version": "${version}",
  "name": "Camera Culling",
  "description": "High-performance camera occlusion culling, 2-sided sign text culling & distance texture LOD.",
  "authors": [
    "Dasik (Rifaditya)"
  ],
  "license": "GPL-3.0-or-later",
  "environment": "client",
  "entrypoints": {
    "client": [
      "net.vanillaoutsider.culling.CameraCullingClient"
    ],
    "modmenu": [
      "net.vanillaoutsider.culling.config.ModMenuIntegration"
    ]
  },
  "mixins": [
    "camera-culling.mixins.json"
  ],
  "depends": {
    "fabricloader": ">=0.18.4",
    "minecraft": ">=26.3-",
    "fabric-api": "*"
  }
}
```

---

## 🔗 Verwandte Seiten

- [[Architektur & Mixins|de_de-26.3-Architecture-and-Mixins]]
- [[API & Mod-Integration|de_de-26.3-API-and-Integration]]
- [[Zurück zur MC 26.3 Übersicht|de_de-26.3-Home]]
