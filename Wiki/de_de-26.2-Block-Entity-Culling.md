# 📦 Block-Entity-Culling (Minecraft 26.2)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Block-Entities (Truhen, Enderkisten, Schilder, Banner, Schädel, verzierte Töpfe, Glocken und Leuchtfeuer) sind dynamische Rendering-Elemente. Da sie die statische Chunk-Netz-Kompilierung von Vanilla umgehen, senden sie in jedem Frame individuelle Draw-Calls. In Lagerräumen oder automatischen Sortieranlagen mit Hunderten von Kisten erzeugt dies erhebliche GPU-Engpässe.

---

## 📋 Block-Entity-Culling-Kurzübersicht

| Eigenschaft | Wert |
| :--- | :--- |
| **Ziel-Pipeline** | `BlockEntityRenderDispatcher.tryExtractRenderState(BlockEntity, float, CrumblingOverlay, boolean)` |
| **Umschließungserkennung** | Prüft alle 6 Nachbarflächen: `up`, `down`, `north`, `south`, `east`, `west` |
| **Konservativer Modus** | Nur Umschließungsprüfung im Profil `LOW` |
| **Aggressiver Modus** | Vollständige Raycast-Sichtlinienüberprüfung in `MEDIUM`, `HIGH`, `SUPER` |
| **Ergebnis** | Gibt `null` RenderState zurück, um den Render-Aufruf zu überspringen |

---

## 🔍 Architektur der Umschließungs- & Sichtlinienüberprüfung

```text
               [OBEN]
                 │
   [WESTEN] ── [TRUHE] ── [OSTEN]
                 │
               [UNTEN]
```

### 1. 6-seitiger Schnelldurchlauf für solide Umschließung
Vor der Durchführung jeglicher Raycast-Berechnungen fragt Camera Culling die benachbarten Blockzustände ab:
```java
BlockState up = level.getBlockState(pos.above());
BlockState down = level.getBlockState(pos.below());
BlockState north = level.getBlockState(pos.north());
BlockState south = level.getBlockState(pos.south());
BlockState east = level.getBlockState(pos.east());
BlockState west = level.getBlockState(pos.west());

if (up.isSolidRender() && down.isSolidRender() && north.isSolidRender()
    && south.isSolidRender() && east.isSolidRender() && west.isSolidRender()) {
    return true; // Zu 100% verdeckt — Rendern überspringen
}
```
Truhen, die in Wänden eingelassen oder in soliden Fundamenten umschlossen sind, werden mit nahezu null CPU-Berechnungsaufwand ($< 0.0001\mu\text{s}$) ausgeblendet.

### 2. Sichtlinien-Raycast-Überprüfung
In den Profilen `MEDIUM`, `HIGH` und `SUPER` (`cullAllBlockEntities = true`) projiziert Camera Culling einen Strahl von der Kameraposition des Spielers zum Mittelpunkt der Block-Entity $(X + 0.5, Y + 0.5, Z + 0.5)$:
* Trifft der Raycast vor Erreichen der Ziel-Block-Entity auf einen verdeckenden soliden Block, wird der Renderzustand verworfen.
* Besteht eine freie Sichtlinie, wird die Block-Entity mit voller grafischer Wiedergabetreue gerendert.

---

## 🔗 Verwandte Seiten

- [[Schild- & Hängeschild-Text-Culling|de_de-26.2-Sign-and-Hanging-Sign-Culling]]
- [[Entitäts-Okklusions-Culling|de_de-26.2-Entity-Occlusion-Culling]]
- [[Architektur & Mixins|de_de-26.2-Architecture-and-Mixins]]
- [[Zurück zur MC 26.2 Übersicht|de_de-26.2-Home]]
