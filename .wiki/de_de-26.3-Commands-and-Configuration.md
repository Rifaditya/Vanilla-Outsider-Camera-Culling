# 🎮 Befehle & Konfiguration (Minecraft 26.3)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Camera Culling bietet eine vollständige Brigadier-Befehlssuite im Spiel (`/cameraculling`) und eine saubere JSON-Konfigurationspersistenz (`config/camera-culling.json`).

---

## 📋 Befehlsreferenztabelle

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

| Befehlssyntax | Argumente & Werte | Beschreibung |
| :--- | :--- | :--- |
| `/cameraculling status` | Keine | Zeigt den aktuellen Culling-Status, die Intensitätsstufe und Echtzeit-Statistiken an. |
| `/cameraculling toggle` | Keine | Schaltet das gesamte Camera-Culling-System global um. |
| `/cameraculling set <level>` | `low`, `medium`, `high`, `super` | Legt das globale Culling-Aggressivitätsprofil fest. |
| `/cameraculling blockentities <bool>` | `true`, `false` | Aktiviert oder deaktiviert das Culling von verdeckten Block-Entities (Kisten usw.). |
| `/cameraculling crowd <bool>` | `true`, `false` | Aktiviert oder deaktiviert den Mob-Mengen-Overdraw-Schutz. |
| `/cameraculling maxcluster <int>` | `1` bis `32` | Legt die maximale Entitätsdichte pro 1,5-Block-Cluster fest (Standard: `8`). |
| `/cameraculling signs <bool>` | `true`, `false` | Aktiviert oder deaktiviert das zweiseitige Schild- und Hängeschild-Text-Culling. |
| `/cameraculling particles <bool>` | `true`, `false` | Aktiviert oder deaktiviert das Okklusions-Culling für Partikel. |
| `/cameraculling animations <bool>` | `true`, `false` | Friert Offscreen-Texturatlas-Animationen bei Pausen oder in Menüs ein. |
| `/cameraculling texturlod <bool>` | `true`, `false` | Aktiviert oder deaktiviert das distanzbasierte Textur-LOD-Mipmap-Scaling. |
| `/cameraculling texturlod range <start> <far>` | `1.0..256.0`, `2.0..512.0` | Legt Nah- und Fern-Schwellenwerte für das Textur-LOD fest (z. B. `16.0 32.0`). |
| `/cameraculling bossimmunity <bool>` | `true`, `false` | Aktiviert oder deaktiviert den Sichtlinienschutz für Bosse und Mini-Bosse. |
| `/cameraculling bosshealth <hp>` | `1.0` bis `10000.0` | Legt den HP-Schwellenwert für Hauptbosse fest (z. B. `150.0`). |
| `/cameraculling minibosshealth <hp>` | `1.0` bis `10000.0` | Legt den HP-Schwellenwert für Mini-Bosse fest (z. B. `50.0`). |
| `/cameraculling blacklist add <id>` | Entitäts-ID | Fügt eine Entität (z. B. `minecraft:wolf`) zur persönlichen Client-Immunität hinzu. |
| `/cameraculling blacklist remove <id>` | Entitäts-ID | Entfernt eine Entität aus der persönlichen Client-Immunität. |
| `/cameraculling blacklist list` | Keine | Listet alle Entitäten auf der persönlichen Immunitäts-Sperrliste auf. |
| `/cameraculling blacklist clear` | Keine | Löscht alle Einträge aus der persönlichen Immunitäts-Sperrliste. |
| `/cameraculling serverblacklist ...` | Unterbefehl + ID | Konfiguriert die vom Server vorgegebene Immunitäts-Sperrliste (erfordert OP). |
| `/cameraculling debug [bool]` | `true`, `false` (optional) | Schaltet die Echtzeit-Zustandsübergangs-Diagnose im Chat und Log um. |
| `/cameraculling reload` | Keine | Lädt die Konfigurationsdateien von der Festplatte neu. |

---

## 📄 JSON-Konfigurationsformat

Die Konfigurationsdateien befinden sich im Verzeichnis `.minecraft/config/`:

### Client-Konfiguration (`config/camera-culling.json`)
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

### Server-Konfiguration (`config/camera-culling-server.json`)
```json
{
  "serverBlacklist": []
}
```

---

## 🔗 Verwandte Seiten

- [[Grafische GUI-Konfiguration (YACL)|de_de-26.3-GUI-Configuration]]
- [[Debug-Logging & Diagnose|de_de-26.3-Debug-Logging-and-Diagnostics]]
- [[Zurück zur MC 26.3 Übersicht|de_de-26.3-Home]]
