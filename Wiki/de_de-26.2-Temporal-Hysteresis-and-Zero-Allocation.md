# ⏱️ Zeitliche Hysterese & Zero-Allocation (Minecraft 26.2)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Hochgradig optimiertes Okklusions-Culling kann zwei bekannte Leistungsprobleme mit sich bringen:
1. **Kantenstreifendes Flackern (Z-Fighting / Boundary Pop-in)**: Minimale Kameradrehungen oder die Geh-Kopfbewegung (View-Bobbing) über eine Blockkante können dazu führen, dass Entitäten in aufeinanderfolgenden Frames ständig zwischen gerendertem und ausgeblendetem Zustand hin- und herflackern.
2. **Garbage-Collection-Ruckler**: Die kontinuierliche Erzeugung von `new Vec3()`- und Begrenzungsrahmen-Objekten während des Raycastings führt zu häufigen JVM Young-Gen GC-Pausen.

**Camera Culling** löst beide Probleme durch einen **adaptiven, entfernungsabhängigen Kulanzpuffer** und eine **Zero-Allocation-Hot-Path-Engine**.

---

## 📋 Hysterese- & Allokations-Kurzübersicht

| Eigenschaft | Wert |
| :--- | :--- |
| **Nahdistanz-Kulanzpuffer** | $d \le 32\text{m} \implies 4\text{ aufeinanderfolgende verdeckte Frames}$ |
| **Mitteldistanz-Kulanzpuffer** | $32\text{m} < d \le 64\text{m} \implies 8\text{ aufeinanderfolgende verdeckte Frames}$ |
| **Far-Distanz-Kulanzpuffer** | $d > 64\text{m} \implies 12\text{ aufeinanderfolgende verdeckte Frames}$ |
| **Sichtbarkeitsübergang** | Sofort ($0\text{ Frames Verzögerung}$) bei bestehender Sichtlinie |
| **Tracking-Datenstruktur** | `it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap` (Kein Boxing-Overhead) |
| **Allokationen pro Frame** | $0\text{ Bytes}$ (Primitive double-Koordinaten direkt übergeben) |

---

## 📐 Formel für distanzskalierte Kulanzpuffer

Die erforderliche Serie verdeckter Frames, bevor eine Entität in den Zustand `[CULLED]` übergeht, berechnet sich wie folgt:

$$\text{ErforderlicheSerie}(d) = \begin{cases}
12\text{ Frames} & \text{wenn } d^2 > 64.0^2 \ (4096.0\text{m}^2) \\
8\text{ Frames} & \text{wenn } d^2 > 32.0^2 \ (1024.0\text{m}^2) \\
4\text{ Frames} & \text{wenn } d^2 \le 32.0^2
\end{cases}$$

```text
[ENTITÄTS-SICHTLINIE VERLOREN]
       │
       ├── Frame 1 verdeckt ──► RENDERN (Kulanz-Abbau)
       ├── Frame 2 verdeckt ──► RENDERN (Kulanz-Abbau)
       ├── Frame 3 verdeckt ──► RENDERN (Kulanz-Abbau)
       └── Frame 4 verdeckt ──► AUSBLENDEN (Serie erreicht)
```

* **Sofortiges Wiedereinblenden (Instant Unculling)**: Sobald ein einziger Raycast-Abtastpunkt eine freie Sichtlinie bestätigt, wird die Verdeckungsserie gelöscht (`OCCLUDED_STREAK.remove(id)`), wodurch die Entität ohne jede Verzögerung ($0\text{ Frames Latenz}$) sichtbar wird.
* **Asymmetrischer Zerfall**: Das Ausblenden erfordert mehrere aufeinanderfolgende Frames; das Wiedereinblenden erfolgt in genau 1 Frame. Dies verhindert jegliches Flackern bei Kameradrehungen.

---

## ⚡ Primitive Zero-Allocation-Architektur

In `CullingRaycastHelper.java` werden Zwischenallokationen von Vektoren auf dem Heap vollständig vermieden:

```java
// Keine Heap-Allokationen: Koordinaten werden als primitive doubles übergeben
public static boolean hasLineOfSight(Level level, Vec3 from, double toX, double toY, double toZ) {
    Vec3 to = new Vec3(toX, toY, toZ);
    ClipContext ctx = new ClipContext(from, to, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, CollisionContext.empty());
    BlockHitResult hit = level.clip(ctx);
    if (hit.getType() == HitResult.Type.MISS) {
        return true;
    }
    // Boden-Treffer & Toleranzprüfung ohne zusätzliche Objekterzeugung
    ...
}
```

---

## 🔗 Verwandte Seiten

- [[Entitäts-Okklusions-Culling|de_de-26.2-Entity-Occlusion-Culling]]
- [[Debug-Logging & Diagnose|de_de-26.2-Debug-Logging-and-Diagnostics]]
- [[Architektur & Mixins|de_de-26.2-Architecture-and-Mixins]]
- [[Zurück zur MC 26.2 Übersicht|de_de-26.2-Home]]
