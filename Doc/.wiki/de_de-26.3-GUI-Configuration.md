# 🖥️ Grafische GUI-Konfiguration (YACL) (Minecraft 26.3)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Camera Culling bietet eine optionale, moderne grafische Konfigurationsanzeige auf Basis von **YetAnotherConfigLib (YACL v3)** und **ModMenu**.

---

## 📋 GUI-Integrations-Kurzübersicht

| Eigenschaft | Wert |
| :--- | :--- |
| **Unterstützte GUI-Engines** | YetAnotherConfigLib v3 (YACL) + ModMenu |
| **Integrationsmuster** | Verzögertes Classloading (`ConfigScreenFactory`) |
| **Server-Absturzsicherheit** | 100% Sicher — keine Client-GUI-Klassenreferenzen in Server-Entrypoints |
| **Menükategorien** | 3 dedizierte Reiter-Kategorien |

---

## 🗂️ Übersicht der GUI-Kategorien

```text
Camera Culling Einstellungsbildschirm
├── 1. Engine & Diagnose
│   ├── Hauptaktivierung (TickBox)
│   ├── Culling-Stufe (Dropdown: LOW, MEDIUM, HIGH, SUPER)
│   └── Echtzeit-Debug-Logging (TickBox)
│
├── 2. Entitäts- & Mengen-Okklusion
│   ├── Mengen-Overdraw-Culling (TickBox)
│   ├── Max Cluster-Entitäten-Obergrenze (Schieberegler: 1 bis 32)
│   ├── Boss- & Mini-Boss-Immunität (TickBox)
│   ├── Hauptboss-Gesundheitsschwelle (Numerisches Feld, Standard: 150.0 HP)
│   └── Mini-Boss-Gesundheitsschwelle (Numerisches Feld, Standard: 50.0 HP)
│
└── 3. Blöcke, Partikel & Animationen
    ├── Partikel-Culling (TickBox)
    ├── Block- & Textur-Animations-Culling (TickBox)
    ├── Zweiseitiges Schild-Text-Culling (TickBox)
    ├── Abstands-Textur-LOD (TickBox)
    ├── Textur-LOD Startdistanz (Schieberegler: 8m bis 64m)
    └── Textur-LOD Ferndistanz (Schieberegler: 16m bis 128m)
```

---

## 🛡️ Verzögertes Classloading & Absturzsicherheit

Um sicherzustellen, dass Camera Culling dedizierte Server oder Installationen ohne installiertes YACL niemals zum Absturz bringt, implementiert `ModMenuIntegration` verzögertes Classloading:

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

Wenn YACL nicht installiert ist, startet das Spiel reibungslos und die Spieler können alle Einstellungen über [[In-Game-Befehle|de_de-26.3-Commands-and-Configuration]] oder durch Bearbeiten der Datei `config/camera-culling.json` anpassen.

---

## 🔗 Verwandte Seiten

- [[Befehle & Konfiguration|de_de-26.3-Commands-and-Configuration]]
- [[Debug-Logging & Diagnose|de_de-26.3-Debug-Logging-and-Diagnostics]]
- [[Zurück zur MC 26.3 Übersicht|de_de-26.3-Home]]
