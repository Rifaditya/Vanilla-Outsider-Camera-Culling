# 🧱 Entitäts-Okklusions-Culling (Minecraft 26.2)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

In Minecraft 26.2 extrahiert die clientseitige Entitäts-Rendering-Pipeline Render-Zustände (`EntityRenderState`) für alle Entitäten innerhalb des Kamera-Sichtkegels (Frustum) – selbst wenn diese hinter Bergen, dicken Steinmauern oder in unterirdischen Höhlen verborgen sind. **Camera Culling** fängt diese Prüfung ab, um zu verhindern, dass verdeckte Mobs unnötige CPU-Geometrieberechnungen und GPU-Draw-Calls verursachen.

---

## 📋 Entitäts-Culling-Kurzübersicht

| Eigenschaft | Wert |
| :--- | :--- |
| **Ziel-Pipeline** | `EntityRenderer.shouldRender(T, Frustum, double, double, double)` |
| **Standardprofil** | `SUPER` (Extrem) |
| **Clip-Kontext** | `ClipContext.Block.COLLIDER`, `ClipContext.Fluid.NONE` |
| **Bodenfilterung** | Ignoriert `Direction.UP`-Treffer, wenn Trefferhöhe $\le Y + 0.15\text{m}$ |
| **Immunitätsblase** | Distanzquadrat $< \text{minDistanceSq}$ |

---

## 🔬 Anatomisches Mehrpunkt-Sichtlinien-Sampling

Anstelle eines einfachen Ein-Punkt-Raycasts, der dazu führen kann, dass Mobs an Blockkanten plötzlich aufploppen oder verschwinden, führt Camera Culling ein anatomisches Mehrpunkt-Sampling basierend auf dem gewählten [[Culling-Profil|de_de-26.2-Commands-and-Configuration]] durch:

```text
       [1] Scheitelpunkt des Kopfes (maxY - 0.05)
          \
           [2] Augenposition (entity.getEyeY())
            \
             [3] Oberkörper / Brust (minY + height * 0.70)
              \
               [4] Massenschwerpunkt (centerY)
                \
        [5-8] Erhöhte Außenflanken (Breiten-/Tiefenprüfungen)
```

1. **Abtastpunkt 1: Scheitelpunkt des Kopfes (`maxY - 0.05`)**
   - Prüfung mit hoher Priorität. Erkennt hohe Entitäten, die über niedrige Barrikaden oder Zäune blicken.
2. **Abtastpunkt 2: Anatomische Augenposition (`getEyeY()`)**
   - Direkte Sichtlinie von der Kamera zu den Augen des Mobs.
3. **Abtastpunkt 3: Oberkörper / Brustkorb (`minY + height * 0.70`)**
   - Bewertet Sichtlinien auf den Oberkörper sicher über Bodenhöhe.
4. **Abtastpunkt 4: Massenschwerpunkt (`(minY + maxY) * 0.5`)**
   - Allgemeiner geometrischer Mittelpunkttest.
5. **Abtastpunkte 5–8: Erhöhte Außenflanken**
   - Bewertet $(X_{\min} + 0.15, Z_{\min} + 0.15)$, $(X_{\max} - 0.15, Z_{\min} + 0.15)$ usw. Stellt sicher, dass breite Bosse (z. B. Verwüster, Warden, Spinnen) sichtbar sind, sobald ihre Schultern hinter Kanten hervortreten.

---

## 🛡️ Direktionales Boden- & Hang-Filtering

Wenn ein Spieler schräg nach unten auf einen Mob blickt, der auf unebenem Gelände steht, kann ein standardmäßiger Raycast auf die Oberseite eines Blocks nahe den Füßen des Mobs treffen und den Boden fälschlicherweise als verdeckende Wand einstufen.

Camera Culling integriert daher ein **direktionales Boden-Treffer-Filtering**:
$$\text{Wenn } \text{hit.getDirection()} == \text{Direction.UP} \quad \text{und} \quad Y_{\text{Treffer}} \le Y_{\text{Ziel}} + 0.15\text{m} \implies \text{Gültige Sichtlinie (Nicht blockiert)}$$

Dies stellt sicher, dass Mobs auf Hügeln, Treppen und unebenem Terrain niemals fälschlicherweise ausgeblendet werden.

---

## ⚡ Zero-Allocation-Raycast-Engine

In früheren Implementierungen erzeugte die Auswertung von 8 Abtastpunkten bei 100 Entitäten über 180.000 `new Vec3()`-Heap-Allokationen pro Sekunde, was zu regelmäßigen JVM Young-Gen Garbage-Collection-Pausen führte.

In 26.2 übergibt `CullingRaycastHelper` direkt primitive Koordinaten:
```java
public static boolean hasLineOfSight(Level level, Vec3 from, double toX, double toY, double toZ)
```
Diese Architektur eliminiert Zwischenallokationen auf dem Heap in jedem Frame vollständig.

---

## 🔗 Verwandte Seiten

- [[Mob-Mengen-Overdraw-Schutz|de_de-26.2-Mob-Crowd-Overdraw-Defense]]
- [[Zeitliche Hysterese & Zero-Allocation|de_de-26.2-Temporal-Hysteresis-and-Zero-Allocation]]
- [[Boss- & Blacklist-Immunität|de_de-26.2-Boss-and-Blacklist-Immunity]]
- [[Zurück zur MC 26.2 Übersicht|de_de-26.2-Home]]
