# 🟢 Camera Culling (Minecraft 26.3)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Bienvenue sur le portail de documentation **Minecraft 26.3** de **Camera Culling** (`v1.10.1+26.3`).

> 📌 **Avis de non-responsabilité sur le code source du dépôt** : La documentation de ce wiki reflète l'**état actuel du code source dans le dépôt**, qui peut inclure des commits récents non publiés ou des fonctionnalités en développement avant les versions publiques sur CurseForge et Modrinth.

---

## 📋 Fiche d'information de Minecraft 26.3

| Propriété | Valeur |
| :--- | :--- |
| **Version cible de Minecraft** | `26.3` |
| **Version de publication** | `1.10.1+26.3` |
| **Prérequis Java** | Java 25 (`release = 25`) |
| **Fabric Loader** | `>=0.18.4` |
| **Fabric API** | `0.156.1+26.3` |
| **Licence** | GNU General Public License v3.0 (GPLv3) |
| **Chemin du sous-projet** | `Camera Culling v26.3/Camera Culling 26.3` |

---

## ⚡ Matrice des fonctionnalités

```text
Pipeline Camera Culling 26.3
├── Occlusion des entités (Moteur de raycasting sans allocation mémoire)
├── Occlusion des entités de bloc (Enclosure sur 6 faces & lignes de visée)
├── Occlusion de texte des panneaux double face (Produit scalaire normal)
├── Occlusion des particules (Bulle de proximité 4m + vérification visuelle)
├── Occlusion des animations (Suppression des envois d'atlas de textures)
├── LOD de texture par distance (Biais de mipmap OpenGL à 3 niveaux)
├── Immunité des boss et mini-boss (Seuils de vie configurables)
├── Hystérésis temporelle anti-scintillement (Tampon adaptatif 4/8/12 frames)
└── Interface graphique GUI (YACL v3 & ModMenu) + Commandes en jeu
```

---

## 📚 Index de documentation pour 26.3

1. [[Occlusion des entités|fr_fr-26.3-Entity-Occlusion-Culling]] — Raycasting multipoint et filtrage du sol.
2. [[Occlusion des entités de bloc|fr_fr-26.3-Block-Entity-Culling]] — Détection d'enclosure pour coffres et entités de bloc.
3. [[Occlusion de texte des panneaux|fr_fr-26.3-Sign-and-Hanging-Sign-Culling]] — Mathématiques du produit scalaire normal et optimisation des panneaux.
4. [[Occlusion des particules et animations|fr_fr-26.3-Particle-and-Animation-Culling]] — Occlusion des particules souterraines et gel des animations d'atlas.
5. [[Défense contre le surdessin des foules|fr_fr-26.3-Mob-Crowd-Overdraw-Defense]] — Échec rapide à 16m et plafond de densité de cluster à 1,5m.
6. [[LOD de textures par distance|fr_fr-26.3-Distance-Texture-LOD]] — Biais de mipmap OpenGL sur les hordes de créatures éloignées.
7. [[Immunité des boss et liste noire|fr_fr-26.3-Boss-and-Blacklist-Immunity]] — Protection des boss et liste noire à deux niveaux.
8. [[Hystérésis temporelle et zéro allocation|fr_fr-26.3-Temporal-Hysteresis-and-Zero-Allocation]] — Tampon de grâce et moteur sans allocation.
9. [[Commandes et configuration|fr_fr-26.3-Commands-and-Configuration]] — Référence exhaustive de la syntaxe des commandes Brigadier.
10. [[Configuration graphique GUI (YACL)|fr_fr-26.3-GUI-Configuration]] — Guide des menus graphiques.
11. [[Journalisation de débogage et diagnostics|fr_fr-26.3-Debug-Logging-and-Diagnostics]] — Suivi en temps réel des transitions d'état dans le chat et les logs.
12. [[Architecture et Mixins|fr_fr-26.3-Architecture-and-Mixins]] — Hiérarchie des packages et registre des injections Mixin.
13. [[Configuration développeur et compilation|fr_fr-26.3-Developer-Setup-and-Building]] — Configuration JDK 25 et instructions de compilation Loom Gradle.
14. [[API et intégration de mods|fr_fr-26.3-API-and-Integration]] — Points d'ancrage d'intégration programmatique.

---

[[Retour au portail de versions|fr_fr-Home]] &bull; [[Matrice de compatibilité|fr_fr-Version-Compatibility]]
