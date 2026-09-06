# 🖥️ Configuration graphique GUI (YACL) (Minecraft 26.1.2)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Camera Culling propose un écran de configuration graphique moderne et optionnel, propulsé par **YetAnotherConfigLib (YACL v3)** et **ModMenu**.

---

## 📋 Fiche d'information de l'intégration GUI

| Propriété | Valeur |
| :--- | :--- |
| **Moteurs GUI pris en charge** | YetAnotherConfigLib v3 (YACL) + ModMenu |
| **Modèle d'intégration** | Chargement de classe différé (`ConfigScreenFactory`) |
| **Sécurité anti-crash serveur** | 100 % Sûr — aucune référence de classe GUI client dans les points d'entrée serveur |
| **Catégories de menus** | 3 onglets thématiques dédiés |

---

## 🗂️ Répartition des catégories de l'interface GUI

```text
Écran de paramètres Camera Culling
├── 1. Moteur & Diagnostics
│   ├── Activation principale (Case à cocher)
│   ├── Niveau d'occlusion (Menu déroulant : LOW, MEDIUM, HIGH, SUPER)
│   └── Journalisation de débogage en temps réel (Case à cocher)
│
├── 2. Occlusion des entités & des foules
│   ├── Occlusion du surdessin de foule (Case à cocher)
│   ├── Plafond maximal d'entités par cluster (Curseur : 1 à 32)
│   ├── Immunité des boss et mini-boss (Case à cocher)
│   ├── Seuil de santé des boss majeurs (Champ numérique, défaut : 150.0 HP)
│   └── Seuil de santé des mini-boss (Champ numérique, défaut : 50.0 HP)
│
└── 3. Blocs, Particules & Animations
    ├── Occlusion des particules (Case à cocher)
    ├── Occlusion des animations de blocs et textures (Case à cocher)
    ├── Occlusion de texte des panneaux double face (Case à cocher)
    ├── LOD de texture par distance (Case à cocher)
    ├── Distance initiale du LOD de texture (Curseur : 8m à 64m)
    └── Distance éloignée du LOD de texture (Curseur : 16m à 128m)
```

---

## 🛡️ Chargement de classe différé et sécurité anti-crash

Pour s'assurer que Camera Culling ne provoque jamais d'erreur critique sur les serveurs dédiés ou sur les installations où YACL est absent, `ModMenuIntegration` applique un chargement de classes différé :

```java
public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        if (FabricLoader.getInstance().isModLoaded("yet_another_config_lib_v3")) {
            return YaclScreenHelper.createFactory();
        }
        return parent -> null;
    }
}
```

Si YACL n'est pas installé, le jeu démarre parfaitement et les joueurs peuvent configurer tous les paramètres via les [[Commandes en jeu|fr_fr-26.1.2-Commands-and-Configuration]] ou en éditant le fichier `config/camera-culling.json`.

---

## 🔗 Pages connexes

- [[Commandes et configuration|fr_fr-26.1.2-Commands-and-Configuration]]
- [[Journalisation de débogage et diagnostics|fr_fr-26.1.2-Debug-Logging-and-Diagnostics]]
- [[Retour à la vue d'ensemble MC 26.1.2|fr_fr-26.1.2-Home]]
