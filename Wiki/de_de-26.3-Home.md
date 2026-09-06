# 🟢 Camera Culling (Minecraft 26.3)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Willkommen im **Minecraft 26.3**-Dokumentations-Hub für **Camera Culling** (`v1.10.1+26.3`).

> 📌 **Quellcode-Haftungsausschluss**: Die Dokumentation in diesem Wiki spiegelt den **aktuellen Quellcode-Stand im Repository** wider, welcher noch unveröffentlichte Commits oder Entwicklungsfunktionen vor den offiziellen Release-Builds auf CurseForge und Modrinth enthalten kann.

---

## 📋 Minecraft 26.3 Kurzübersicht

| Eigenschaft | Wert |
| :--- | :--- |
| **Ziel-Minecraft-Version** | `26.3` |
| **Release-Version** | `1.10.1+26.3` |
| **Java-Anforderung** | Java 25 (`release = 25`) |
| **Fabric Loader** | `>=0.18.4` |
| **Fabric API** | `0.156.1+26.3` |
| **Lizenz** | GNU General Public License v3.0 (GPLv3) |
| **Unterprojekt-Pfad** | `Camera Culling v26.3/Camera Culling 26.3` |

---

## ⚡ Funktionsmatrix

```text
Camera Culling 26.3 Pipeline
├── Entitäts-Okklusion (Zero-Allocation-Raycast-Engine)
├── Block-Entity-Okklusion (6-seitige Umschließung & Sichtlinien)
├── 2-seitiges Schild-Text-Culling (Normalenvektor-Skalarprodukt)
├── Partikel-Okklusion (4m-Näherungssicherheitsblase + Sichtprüfung)
├── Animations-Culling (Unterdrückung von Texturatlas-Uploads)
├── Abstands-Textur-LOD (3-stufiger OpenGL Mipmap-Bias)
├── Boss- & Mini-Boss-Immunität (Konfigurierbare HP-Schwellenwerte)
├── Zeitliche Hysterese gegen Flackern (Adaptiver 4/8/12-Frame-Puffer)
└── Grafische GUI (YACL v3 & ModMenu) + In-Game-Befehle
```

---

## 📚 Dokumentationsindex für 26.3

1. [[Entitäts-Okklusions-Culling|de_de-26.3-Entity-Occlusion-Culling]] — Mehrpunkt-Raycasting & Bodenfilterung.
2. [[Block-Entity-Culling|de_de-26.3-Block-Entity-Culling]] — Umschließungsprüfungen für Kisten und Block-Entities.
3. [[Schild- & Hängeschild-Text-Culling|de_de-26.3-Sign-and-Hanging-Sign-Culling]] — Zweiseitige Normalenvektor-Mathematik und Schildtext-Optimierung.
4. [[Partikel- & Animations-Culling|de_de-26.3-Particle-and-Animation-Culling]] — Unterirdisches Partikel-Culling & Einfrieren von Atlas-Animationen.
5. [[Mob-Mengen-Overdraw-Schutz|de_de-26.3-Mob-Crowd-Overdraw-Defense]] — 16m-Distanz-Gating & 1,5m-Cluster-Dichteobergrenze.
6. [[Abstands-Textur-LOD|de_de-26.3-Distance-Texture-LOD]] — OpenGL Mipmap-LOD-Biasing bei entfernten Mob-Gruppen.
7. [[Boss- & Blacklist-Immunität|de_de-26.3-Boss-and-Blacklist-Immunity]] — Boss-Schutz & zweistufige Sperrliste.
8. [[Zeitliche Hysterese & Zero-Allocation|de_de-26.3-Temporal-Hysteresis-and-Zero-Allocation]] — Kulanzpuffer & Allokationsfreie Engine.
9. [[Befehle & Konfiguration|de_de-26.3-Commands-and-Configuration]] — Vollständige Brigadier-Befehlssyntax-Referenz.
10. [[Grafische GUI-Konfiguration (YACL)|de_de-26.3-GUI-Configuration]] — Leitfaden für grafische Menüs.
11. [[Debug-Logging & Diagnose|de_de-26.3-Debug-Logging-and-Diagnostics]] — Echtzeit-Zustandsübergangsverfolgung im Chat & Log.
12. [[Architektur & Mixins|de_de-26.3-Architecture-and-Mixins]] — Pakethierarchie und Mixin-Zieltabelle.
13. [[Entwickler-Setup & Build|de_de-26.3-Developer-Setup-and-Building]] — JDK 25-Setup und Loom-Gradle-Buildanweisungen.
14. [[API & Mod-Integration|de_de-26.3-API-and-Integration]] — Programmatische Integrations-Hooks.

---

[[Zurück zum Versionsportal|de_de-Home]] &bull; [[Versionskompatibilität|de_de-Version-Compatibility]]
