# 👑 Immunité des boss et liste noire (Minecraft 26.2)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Afin de préserver l'équité du gameplay et la lisibilité des combats, les menaces majeures et les animaux de compagnie ne doivent jamais disparaître derrière des obstacles ni être affectés par des algorithmes d'occlusion agressifs.

**Camera Culling** intègre une identification dynamique des boss ainsi qu'un système de liste noire d'immunité à deux niveaux.

---

## 📋 Fiche d'information des immunités

| Propriété | Valeur |
| :--- | :--- |
| **Seuil de vie des boss majeurs** | `bossHealthThreshold` (Par défaut : `150.0 HP` / 75 cœurs) |
| **Seuil de vie des mini-boss** | `miniBossHealthThreshold` (Par défaut : `50.0 HP` / 25 cœurs) |
| **Fichier liste noire client** | `config/camera-culling.json` (Tableau `clientBlacklist`) |
| **Fichier liste noire serveur** | `config/camera-culling-server.json` (Tableau `serverBlacklist`) |
| **Périmètre de l'immunité** | Exempté de l'occlusion par blocs, du surdessin de foule et du LOD de texture |

---

## 🐲 Détection dynamique des boss et mini-boss

Camera Culling évalue l'immunité des boss par le biais de deux mécanismes distincts :

### 1. Seuils dynamiques de points de vie
Toute entité vivante (`LivingEntity`) dont le `getMaxHealth()` atteint ou dépasse les seuils définis bénéficie d'une immunité inconditionnelle :
* `maxHealth >= 150.0` $\implies$ Boss majeur (Ender Dragon, Wither, Warden).
* `maxHealth >= 50.0` $\implies$ Mini-boss (Grand Gardien, Ravageur, Golem de fer, Piglin barbare, Breeze, champions moddés).

### 2. Heuristiques par mots-clés de registre et d'identifiants
Les entités dont le nom ou l'identifiant contient l'une des sous-chaînes suivantes sont automatiquement traitées comme des boss :
```text
dragon, wither, warden, elder_guardian, ravager, evoker, iron_golem, 
piglin_brute, breeze, boss, miniboss, mini_boss, titan, leviathan, 
harbinger, monarch, behemoth, champion, elite, brute
```

---

## 🛡️ Système de liste noire d'immunité à deux niveaux

```text
Résolution d'immunité
├── Joueur local / Véhicule / Monture ──► 100 % Rendu
├── Effet de surbrillance (Glowing) ───► 100 % Rendu
├── Boss ou Mini-Boss détecté ──────────► 100 % Rendu
├── Présent sur la liste noire client ──► 100 % Rendu
└── Présent sur la liste noire serveur ─► 100 % Rendu
```

### 1. Liste noire personnelle côté client
Les joueurs peuvent inscrire localement des compagnons spécifiques en utilisant les commandes en jeu :
```sql
/cameraculling blacklist add minecraft:wolf
/cameraculling blacklist add minecraft:allay
/cameraculling blacklist add minecraft:cat
```

### 2. Liste noire imposée par le serveur
Les administrateurs de serveur peuvent imposer des immunités globales dans `config/camera-culling-server.json` ou via la commande `/cameraculling serverblacklist add <id>`. Tous les clients connectés respecteront automatiquement cette liste imposée par le serveur.

---

## 🔗 Pages connexes

- [[Occlusion des entités|fr_fr-26.2-Entity-Occlusion-Culling]]
- [[LOD de textures par distance|fr_fr-26.2-Distance-Texture-LOD]]
- [[Commandes et configuration|fr_fr-26.2-Commands-and-Configuration]]
- [[Retour à la vue d'ensemble MC 26.2|fr_fr-26.2-Home]]
