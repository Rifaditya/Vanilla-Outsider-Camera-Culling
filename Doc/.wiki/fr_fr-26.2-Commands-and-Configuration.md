# 🎮 Commandes et configuration (Minecraft 26.2)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Camera Culling propose une suite complète de commandes en jeu via Brigadier (`/cameraculling`) et une persistance propre de la configuration en JSON (`config/camera-culling.json`).

---

## 📋 Tableau de référence des commandes

```sql
/cameraculling status
/cameraculling toggle
/cameraculling set <low|medium|high|super>
/cameraculling blockentities <bool>
/cameraculling crowd <bool>
/cameraculling maxcluster <1..32>
/cameraculling signs <bool>
/cameraculling particles <bool>
/cameraculling animations <bool>
/cameraculling texturlod <bool>
/cameraculling texturlod range <start> <far>
/cameraculling bossimmunity <bool>
/cameraculling bosshealth <hp>
/cameraculling minibosshealth <hp>
/cameraculling blacklist add <id>
/cameraculling blacklist remove <id>
/cameraculling blacklist list
/cameraculling blacklist clear
/cameraculling serverblacklist add <id>
/cameraculling serverblacklist remove <id>
/cameraculling serverblacklist list
/cameraculling serverblacklist clear
/cameraculling debug [bool]
/cameraculling reload
```

| Syntaxe de commande | Arguments & Valeurs | Description |
| :--- | :--- | :--- |
| `/cameraculling status` | Aucun | Affiche l'état actuel de l'occlusion, le profil d'intensité et les statistiques en temps réel. |
| `/cameraculling toggle` | Aucun | Active ou désactive globalement l'ensemble du système Camera Culling. |
| `/cameraculling set <level>` | `low`, `medium`, `high`, `super` | Définit le profil d'agressivité globale de l'occlusion. |
| `/cameraculling blockentities <bool>` | `true`, `false` | Active ou désactive l'occlusion des entités de bloc (coffres, etc.). |
| `/cameraculling crowd <bool>` | `true`, `false` | Active ou désactive la défense contre le surdessin des foules de créatures. |
| `/cameraculling maxcluster <int>` | `1` à `32` | Définit la densité maximale d'entités par cluster de 1,5 bloc (Défaut : `8`). |
| `/cameraculling signs <bool>` | `true`, `false` | Active ou désactive l'occlusion de texte des panneaux et panneaux suspendus. |
| `/cameraculling particles <bool>` | `true`, `false` | Active ou désactive l'occlusion des particules masquées. |
| `/cameraculling animations <bool>` | `true`, `false` | Fige les animations d'atlas de textures hors écran lors des pauses ou dans les menus. |
| `/cameraculling texturlod <bool>` | `true`, `false` | Active ou désactive l'ajustement du LOD de texture par distance (mipmaps). |
| `/cameraculling texturlod range <start> <far>` | `1.0..256.0`, `2.0..512.0` | Définit les seuils de distance proche et éloignée du LOD de texture (ex : `16.0 32.0`). |
| `/cameraculling bossimmunity <bool>` | `true`, `false` | Active ou désactive la protection de ligne de visée des boss et mini-boss. |
| `/cameraculling bosshealth <hp>` | `1.0` à `10000.0` | Définit le seuil de points de vie des boss majeurs (ex : `150.0`). |
| `/cameraculling minibosshealth <hp>` | `1.0` à `10000.0` | Définit le seuil de points de vie des mini-boss (ex : `50.0`). |
| `/cameraculling blacklist add <id>` | ID de l'entité | Ajoute une créature (ex : `minecraft:wolf`) à la liste d'immunité client personnelle. |
| `/cameraculling blacklist remove <id>` | ID de l'entité | Retire une créature de la liste d'immunité client personnelle. |
| `/cameraculling blacklist list` | Aucun | Liste toutes les entités actuellement inscrites sur la liste noire personnelle. |
| `/cameraculling blacklist clear` | Aucun | Efface toutes les entrées de la liste d'immunité personnelle. |
| `/cameraculling serverblacklist ...` | Sous-commande + ID | Configure la liste d'immunité imposée par le serveur (Requiert les droits OP). |
| `/cameraculling debug [bool]` | `true`, `false` (optionnel) | Bascule le traçage diagnostique des transitions d'état dans le chat et les logs. |
| `/cameraculling reload` | Aucun | Recharge les fichiers de configuration depuis le disque. |

---

## 📄 Format de configuration JSON

Les fichiers de configuration sont situés dans le dossier `.minecraft/config/` :

### Configuration client (`config/camera-culling.json`)
```json
{
  "enabled": true,
  "level": "SUPER",
  "cullEntitiesBehindEntities": false,
  "maxEntitiesPerCluster": 8,
  "distanceTextureLod": true,
  "distanceTextureLodStart": 16.0,
  "distanceTextureLodFar": 32.0,
  "bossImmunity": true,
  "bossHealthThreshold": 150.0,
  "miniBossHealthThreshold": 50.0,
  "cullParticles": true,
  "cullAnimations": true,
  "cullSignText": true,
  "clientBlacklist": [
    "minecraft:wolf",
    "minecraft:allay"
  ],
  "debugMode": false
}
```

### Configuration serveur (`config/camera-culling-server.json`)
```json
{
  "serverBlacklist": []
}
```

---

## 🔗 Pages connexes

- [[Configuration graphique GUI (YACL)|fr_fr-26.2-GUI-Configuration]]
- [[Journalisation de débogage et diagnostics|fr_fr-26.2-Debug-Logging-and-Diagnostics]]
- [[Retour à la vue d'ensemble MC 26.2|fr_fr-26.2-Home]]
