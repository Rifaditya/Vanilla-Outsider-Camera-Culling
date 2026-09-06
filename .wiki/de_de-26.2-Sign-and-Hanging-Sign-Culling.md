# 🪧 Schild- & Hängeschild-Text-Culling (Minecraft 26.2)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

In Minecraft 26.2 verfügen Schilder über beidseitiges Text-Rendering. Die Schrift-Rendering-Engine zeichnet Glyphen-Quads, Farben und Leuchtkonturen auf beiden Seiten gleichzeitig.

**Camera Culling** eliminiert unnötige Text-Renderdurchläufe über Normalenvektor-Skalarprodukte ($\\vec{N} \\cdot \\vec{V}$) und Schnelldurchläufe für leere Texte.

---

## 📋 Schild-Culling-Kurzübersicht

| Eigenschaft | Wert |
| :--- | :--- |
| **Ziel-Pipeline** | `BlockEntityRenderDispatcher.tryExtractRenderState` |
| **Abschaltwinkel Vorder-/Rückseite** | Skalarprodukt-Schwellenwert $\pm 0.05$ |
| **Leerer Schnelldurchlauf** | Überspringt leere Flächen ohne Textzeichen automatisch |
| **Unterstützte Blöcke** | Stehende Schilder, Wandschilder, Deckenhängeschilder, Wandhängeschilder |

---

## 📐 Trigonometrisches Normalenvektor-Skalarprodukt

Um festzustellen, ob die Vorder- oder Rückseite eines Schildes zur Kamera zeigt, berechnet Camera Culling das Skalarprodukt zwischen dem Flächennormalenvektor $\vec{N} = (N_x, N_z)$ des Schildes und dem Vektor vom Schildmittelpunkt zur Kamera $\vec{V} = (V_x, V_z)$:

$$V_x = X_{\text{cam}} - (X_{\text{Schild}} + 0.5), \quad V_z = Z_{\text{cam}} - (Z_{\text{Schild}} + 0.5)$$

### 1. Wandschilder (`WallSignBlock.FACING`) & Wandhängeschilder (`WallHangingSignBlock.FACING`)
Der Normalenvektor wird direkt aus den Schrittweiten der Block-`Direction` abgeleitet:
$$N_x = \text{facing.getStepX()}, \quad N_z = \text{facing.getStepZ()}$$

### 2. Stehende Schilder (`StandingSignBlock.ROTATION`) & Deckenhängeschilder (`CeilingHangingSignBlock.ROTATION`)
Die Drehung wird als Ganzzahl von $0 \dots 15$ dargestellt. Der Winkel $\theta$ im Bogenmaß berechnet sich wie folgt:
$$\theta = \left(\frac{\text{rotation} \times 360^\circ}{16}\right) \times \frac{\pi}{180}$$
$$N_x = -\sin\theta, \quad N_z = \cos\theta$$

### 3. Sichtbarkeitsbestimmung
Das Skalarprodukt $D$ wertet den Beobachtungswinkel aus:
$$D = N_x V_x + N_z V_z$$

* **Auswertung der Vorderseite**: Wird ausgeblendet, wenn $D < -0.05$ (Kamera befindet sich hinter der Schildfläche).
* **Auswertung der Rückseite**: Wird ausgeblendet, wenn $D > 0.05$ (Kamera befindet sich vor der Schildfläche).

---

## ⚡ Schnelldurchlauf für leere Seiten (Fast-Pass)

Wenn ein Spieler nur Text auf eine Seite eines Schildes geschrieben hat (oder ein Schild ohne Text als Barriere oder Dekoration platziert hat), prüft Camera Culling die Textzeilen:
```java
public static boolean isTextEmpty(SignText text) {
    if (text == null) return true;
    for (int i = 0; i < 4; i++) {
        Component msg = text.getMessage(i, false);
        if (msg != null && !msg.getString().trim().isEmpty()) {
            return false;
        }
    }
    return true;
}
```
Leere Flächen werden sofort verworfen, ohne Trigonometrie oder Vektorskalarprodukte zu berechnen.

---

## 🔗 Verwandte Seiten

- [[Block-Entity-Culling|de_de-26.2-Block-Entity-Culling]]
- [[Befehle & Konfiguration|de_de-26.2-Commands-and-Configuration]]
- [[Architektur & Mixins|de_de-26.2-Architecture-and-Mixins]]
- [[Zurück zur MC 26.2 Übersicht|de_de-26.2-Home]]
