# 🌐 Matrice de compatibilité et cycle de vie des versions

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Ce document décrit la matrice de publication multi-ères active, les limites de dépendance, les spécifications du runtime Java et les variations d'API bytecode pour **Camera Culling**.

> 📌 **Avis de non-responsabilité sur le code source du dépôt** : La documentation de ce wiki reflète l'**état actuel du code source dans le dépôt**, qui peut inclure des commits récents non publiés ou des fonctionnalités en développement avant les versions publiques sur CurseForge et Modrinth.

---

## 📋 Matrice de cycle de vie multi-versions

| Ère d'ancrage Minecraft | Version MC ciblée | Version actuelle de publication | Prérequis Java | Limite Fabric Loader | Limite Fabric API | Statut de publication |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| **Modern Sovereign (26.3)** | `26.3` | `1.10.1+26.3` | Java 25 (`release = 25`) | `>=0.18.4` | `0.156.1+26.3` | 🟢 Version active |
| **Modern Sovereign (26.2)** | `26.2` | `1.10.1+26.2` | Java 25 (`release = 25`) | `>=0.18.4` | `0.145.4+26.2` | 🟢 Version active |
| **Modern Sovereign (26.1.2)** | `26.1.2` | `1.10.2+26.1.2` | Java 25 (`release = 25`) | `>=0.18.4` | `0.145.4+26.1.2` | 🟢 Version active |

---

## 🛠️ Différences d'API Bytecode selon les versions

### 1. Pipeline d'extraction du RenderState des entités de bloc
* **Minecraft 26.1.2**:
  - `BlockEntityRenderDispatcher.tryExtractRenderState(BlockEntity, float, ModelFeatureRenderer$CrumblingOverlay)` — prend **3 arguments**.
* **Minecraft 26.2 & 26.3**:
  - `BlockEntityRenderDispatcher.tryExtractRenderState(BlockEntity, float, ModelFeatureRenderer$CrumblingOverlay, boolean isGloballyRendered)` — prend **4 arguments**.

### 2. API de récupération des données de texte des panneaux
* **Minecraft 26.1.2 & 26.2**:
  - `SignBlockEntity.getFrontText()` et `SignBlockEntity.getBackText()` récupèrent `SignText`.
  - `SignText.getMessage(int index, boolean filtered)` récupère le `Component` de ligne.
* **Minecraft 26.3**:
  - `SignBlockEntity.getText(SignTextSlot.FRONT)` et `SignBlockEntity.getText(SignTextSlot.BACK)` récupèrent `SignText`.
  - `SignText.getMessages(boolean filtered)` récupère le tableau de `Component` de lignes.

---

## 📦 Emplacements d'archivage des artefacts de compilation

Toutes les versions de publication sont automatiquement compilées et conservées dans la structure d'archivage centralisée du dépôt parent :

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

## 🔗 Liens rapides

- [[👉 Accéder au Wiki MC 26.3|fr_fr-26.3-Home]]
- [[👉 Accéder au Wiki MC 26.2|fr_fr-26.2-Home]]
- [[👉 Accéder au Wiki MC 26.1.2|fr_fr-26.1.2-Home]]
- [[Retour au portail|fr_fr-Home]]
