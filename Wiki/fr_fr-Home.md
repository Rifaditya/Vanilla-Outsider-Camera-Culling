# 📷 Wiki Camera Culling

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Bienvenue sur le portail officiel de documentation de **Camera Culling**. Camera Culling est un mod d'optimisation du rendu côté client hautes performances pour Minecraft **26.1.2**, **26.2** et **26.3**, développé selon la philosophie **Vanilla Outsider**.

> 📌 **Avis de non-responsabilité sur le code source du dépôt** : La documentation de ce wiki reflète l'**état actuel du code source dans le dépôt**, qui peut inclure des commits récents non publiés ou des fonctionnalités en développement avant les versions publiques sur CurseForge et Modrinth.

---

## 🧭 Portail de commutation multi-versions

Camera Culling est développé selon la règle stricte **1 JAR 1 Version**. Sélectionnez ci-dessous votre version ciblée de Minecraft pour accéder à son arborescence de documentation dédiée et isolée :

| Version cible de Minecraft | Version de publication du mod | Runtime Java | Outils de compilation | Portail wiki dédié |
| :--- | :--- | :--- | :--- | :--- |
| **Minecraft 26.1.2** | `1.10.2+26.1.2` | Java 25+ | Fabric Loom 1.15.5 | [[👉 Accéder au Wiki MC 26.1.2|fr_fr-26.1.2-Home]] |
| **Minecraft 26.2** | `1.10.1+26.2` | Java 25+ | Fabric Loom 1.15.5 | [[👉 Accéder au Wiki MC 26.2|fr_fr-26.2-Home]] |
| **Minecraft 26.3** | `1.10.1+26.3` | Java 25+ | Fabric Loom 1.15.5 | [[👉 Accéder au Wiki MC 26.3|fr_fr-26.3-Home]] |

---

## ⚡ Matrice d'optimisations principales

| Système d'optimisation | Mécanisme principal | Gain de performance |
| :--- | :--- | :--- |
| **Moteur de raycasting sans allocation mémoire** | Vérification de la ligne de visée via coordonnées primitives | Élimine les pics de pause GC Young-Gen de la JVM lors des rotations de caméra |
| **Hystérésis temporelle anti-scintillement** | Tampon de grâce adaptatif par distance de 4/8/12 frames | Élimine le scintillement aux bordures et lors du balancement de marche (view bobbing) |
| **Occlusion de texte des panneaux double face** | Produits scalaires des vecteurs normaux de face ($\\vec{N} \\cdot \\vec{V}$) | Réduction de 50 % à 100 % des appels de rendu (draw calls) du texte des panneaux |
| **Occlusion des particules** | Bulle de sécurité de 4m + raycasts de découpe visuelle | Ne rend pas les quads de particules souterrains et obstrués |
| **Occlusion des animations** | Suppression de l'envoi vers l'atlas de textures | Fige les animations de blocs 3D et les envois de textures hors écran |
| **Défense contre le surdessin des foules de mobs** | Échec rapide à 16m + plafonnement de densité de cluster à 1,5m | Élimine les chutes de FPS dans les enclos et fermes à mobs surpeuplés |
| **LOD de textures par distance** | Biais de mipmap OpenGL à 3 niveaux ($0.0 \\to 1.0 \\to 2.5$) | Réduit considérablement le taux de remplissage VRAM sur les hordes éloignées |
| **Immunité des boss et mini-boss** | Seuils de santé dynamiques et heuristiques de noms | Empêche la disparition des boss essentielle au gameplay |
| **Liste noire d'immunité à deux niveaux** | JSON client local + synchronisation administrateur serveur | Liste blanche personnalisée pour les animaux de compagnie |
| **Occlusion des entités de bloc** | Détection d'enclosure solide sur 6 faces | Ignore l'extraction de rendu pour les coffres et blocs totalement entourés |

---

## 📚 Navigation globale

- [[Matrice de compatibilité et cycle de vie des versions|fr_fr-Version-Compatibility]]
- [[Arborescence de documentation Minecraft 26.1.2|fr_fr-26.1.2-Home]]
- [[Arborescence de documentation Minecraft 26.2|fr_fr-26.2-Home]]
- [[Arborescence de documentation Minecraft 26.3|fr_fr-26.3-Home]]

---

<p align="center">
  <em>Développé par <strong>Dasik (Rifaditya)</strong> | Sous licence <strong>GNU General Public License v3.0 (GPLv3)</strong></em>
</p>
