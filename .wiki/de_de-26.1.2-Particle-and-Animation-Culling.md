# ✨ Partikel- & Animations-Culling (Minecraft 26.1.2)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

In Vanilla-Minecraft erzeugen unterirdische Lavatropfen, Höhlenstaub, Portalpartikel und Fackeln Hunderte von Partikel-Quads hinter massiven Steinmauern, die an die GPU gesendet werden. Gleichzeitig laden animierte Blocktextur-Atlanten kontinuierlich Frame-Daten auf die GPU hoch – selbst wenn das Einzelspieler-Spiel pausiert ist oder sich der Spieler in Menüs befindet.

**Camera Culling** führt hochgradig optimierte Partikel-Sichtlinienprüfungen und die Unterdrückung von Texturatlas-Uploads ein.

---

## 📋 Partikel- & Animations-Culling-Kurzübersicht

| Eigenschaft | Wert |
| :--- | :--- |
| **Partikel-Ziel** | `SingleQuadParticle.extract(QuadParticleRenderState, Camera, float)` |
| **Animations-Ziel** | `TextureAtlas.cycleAnimationFrames()` |
| **Näherungssicherheit** | 4,0 Meter ($16.0\text{m}^2$) um die Kamera |
| **Maximale Partikeldistanz** | 64,0 Meter ($4096.0\text{m}^2$) |
| **Atlas-Pause-Bedingung** | Einzelspieler pausiert ODER keine aktive Welt / kein Spieler |

---

## 🌪️ Partikel-Okklusions-Pipeline

```text
Partikel erzeugt bei (X, Y, Z)
        │
        ▼
[1] Distanz <= 4m? ──────────► RENDERN (Näherungsblase)
        │ Nein
        ▼
[2] Distanz > 64m? ──────────► AUSBLENDEN (Weit-Abschaltung)
        │ Nein
        ▼
[3] Im soliden Block? ───────► AUSBLENDEN (Umschlossen)
        │ Nein
        ▼
[4] Sichtstrahl blockiert?
        ├── Ja ──────────────► AUSBLENDEN (Verdeckt)
        └── Nein ─────────────► RENDERN (Sichtbar)
```

1. **Näherungs-Sicherheitsblase (4,0m)**: Partikel, die innerhalb von 4 Metern um die Kamera ausgestoßen werden (z. B. Trankwirbel, Sprintstaub, Angriffsschwünge), werden bedingungslos in $< 0.0001\mu\text{s}$ gerendert.
2. **Weitdistanz-Abschaltung (64,0m)**: Partikel jenseits von 64 Metern werden verworfen, um die Quad-Füllrate der GPU zu schonen.
3. **Solide Blockumschließung**: Hat die Blockposition `BlockPos.containing(x, y, z)` die Eigenschaft `isSolidRender() == true`, wird das Partikel sofort verworfen.
4. **Sichtstrahl-Raycast**: Projiziert einen `ClipContext.Block.VISUAL`-Strahl von der Kameraposition zum Partikelvektor. Blockiert ein undurchsichtiger solider Block die Sichtlinie mit einem Spielraum von $> 0.35\text{m}$, wird das Rendern übersprungen.

---

## 🎬 Einfrieren von Block- & Texturatlas-Animationen

Animationen im Texturatlas (animierte Seelaternen, fließendes Wasser/Lava, Prismarin, Feuer, Kompass, Uhr) verbrauchen GPU-Bandbreite beim Durchlaufen der Frame-Indizes.

Camera Culling prüft `AnimationCullingHelper.shouldPauseAtlasAnimation()` in `TextureAtlasMixin`:
* Ist das Einzelspielerspiel pausiert (`mc.isPaused() == true`), werden Atlas-Uploads angehalten.
* Befindet sich der Spieler im Hauptmenü, in Konfigurationsbildschirmen oder ist von der Welt getrennt, wird der Texturzyklus im Hintergrund gestoppt.

---

## 🔗 Verwandte Seiten

- [[Entitäts-Okklusions-Culling|de_de-26.1.2-Entity-Occlusion-Culling]]
- [[Befehle & Konfiguration|de_de-26.1.2-Commands-and-Configuration]]
- [[Zurück zur MC 26.1.2 Übersicht|de_de-26.1.2-Home]]
