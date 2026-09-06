# 👑 Boss- & Blacklist-Immunität (Minecraft 26.3)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Um Spielfairness und taktische Aufmerksamkeit im Kampf zu gewährleisten, dürfen kritische Bedrohungen und Haustiere niemals hinter Wänden verschwinden oder von aggressiven Culling-Algorithmen beeinflusst werden.

**Camera Culling** beinhaltet eine dynamische Boss-Erkennung sowie eine zweistufige Immunitäts-Sperrliste.

---

## 📋 Immunitäts-Kurzübersicht

| Eigenschaft | Wert |
| :--- | :--- |
| **Hauptboss-Gesundheitsschwelle** | `bossHealthThreshold` (Standard: `150.0 HP` / 75 Herzen) |
| **Mini-Boss-Gesundheitsschwelle** | `miniBossHealthThreshold` (Standard: `50.0 HP` / 25 Herzen) |
| **Client-Sperrlistenpfad** | `config/camera-culling.json` (`clientBlacklist`-Array) |
| **Server-Sperrlistenpfad** | `config/camera-culling-server.json` (`serverBlacklist`-Array) |
| **Umfang der Immunität** | Ausgenommen von Block-Okklusion, Mob-Overdraw und Textur-LOD |

---

## 🐲 Dynamische Boss- & Mini-Boss-Erkennung

Camera Culling ermittelt die Boss-Immunität über zwei Mechanismen:

### 1. Dynamische Gesundheitsschwellenwerte
Jede `LivingEntity`, deren `getMaxHealth()` die konfigurierten Schwellenwerte erreicht oder überschreitet, erhält bedingungslose Immunität:
* `maxHealth >= 150.0` $\implies$ Hauptboss (Enderdrache, Wither, Warden).
* `maxHealth >= 50.0` $\implies$ Mini-Boss (Großer Wächter, Verwüster, Eisengolem, Piglin-Brut, Breeze, gemoddete Champions).

### 2. Registry- & Namensheuristiken
Entitäten, deren Bezeichner eine der folgenden Teilzeichenfolgen enthält, werden automatisch als Bosse identifiziert:
```text
dragon, wither, warden, elder_guardian, ravager, evoker, iron_golem, 
piglin_brute, breeze, boss, miniboss, mini_boss, titan, leviathan, 
harbinger, monarch, behemoth, champion, elite, brute
```

---

## 🛡️ Zweistufiges Immunitäts-Sperrlistensystem

```text
Immunitätsauflösung
├── Lokaler Spieler / Fahrzeug / Reittier ──► 100% gerendert
├── Leuchteffekt aktiv ─────────────────────► 100% gerendert
├── Boss oder Mini-Boss erkannt ────────────► 100% gerendert
├── Treffer auf Client-Sperrliste ──────────► 100% gerendert
└── Treffer auf Server-Admin-Sperrliste ────► 100% gerendert
```

### 1. Persönliche Client-Sperrliste
Spieler können bestimmte Begleiter und Haustiere lokal über In-Game-Befehle auf die Whitelist setzen:
```sql
/cameraculling blacklist add minecraft:wolf
/cameraculling blacklist add minecraft:allay
/cameraculling blacklist add minecraft:cat
```

### 2. Server-Admin-Sperrliste
Serverbetreiber können globale Entitätsimmunitäten in `config/camera-culling-server.json` oder über `/cameraculling serverblacklist add <id>` festlegen. Alle verbundenen Clients übernehmen die vom Server vorgegebene Immunitätsliste automatisch.

---

## 🔗 Verwandte Seiten

- [[Entitäts-Okklusions-Culling|de_de-26.3-Entity-Occlusion-Culling]]
- [[Abstands-Textur-LOD|de_de-26.3-Distance-Texture-LOD]]
- [[Befehle & Konfiguration|de_de-26.3-Commands-and-Configuration]]
- [[Zurück zur MC 26.3 Übersicht|de_de-26.3-Home]]
