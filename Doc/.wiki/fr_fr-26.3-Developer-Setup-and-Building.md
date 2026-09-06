# 🛠️ Configuration développeur et compilation (Minecraft 26.3)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Ce guide technique détaille les prérequis d'environnement, les outils Gradle Loom et les instructions de compilation pour compiler **Camera Culling** sur **Minecraft 26.3**.

---

## 📋 Prérequis d'environnement

* **Kit de développement Java (JDK)** : **JDK 25+** (par exemple Eclipse Adoptium Temurin 25).
* **Wrapper Gradle** : Version 9.3+ avec Loom 1.15.5 (`net.fabricmc.fabric-loom`).
* **Runtime** : Environnement d'exécution Mojang non obscurci (le bloc de mappages dans `build.gradle` est strictement proscrit en 26.x).

---

## 🏗️ Commandes de compilation Gradle

Ouvrez un terminal dans le répertoire du sous-projet `Camera Culling v26.3/Camera Culling 26.3` :

```bash
# Exécuter la suite de tests unitaires
./gradlew test --no-daemon

# Compiler et assembler le fichier JAR de publication
./gradlew build --no-daemon
```

### Pipeline d'archivage automatique
Le fichier `build.gradle` du sous-projet inclut une tâche d'archivage automatisée :
* Fichier JAR produit : `build/libs/vanilla-outsider-camera-culling-1.10.0+26.3.jar`
* Emplacement archivé : `Archive Jar of all versions/MC 26.3/`
* Synchronisation du profil Modrinth : Automatiquement installé dans votre profil de lanceur local (`Fabric 26.3ish/mods/`).

---

## 📄 Métadonnées fabric.mod.json

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

## 🔗 Pages connexes

- [[Architecture et Mixins|fr_fr-26.3-Architecture-and-Mixins]]
- [[API et intégration de mods|fr_fr-26.3-API-and-Integration]]
- [[Retour à la vue d'ensemble MC 26.3|fr_fr-26.3-Home]]
