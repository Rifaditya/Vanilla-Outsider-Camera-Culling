# 🎨 Abstands-Textur-LOD (Minecraft 26.3)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Das Rendern vollständiger 1024x1024- oder hochauflösender Texturen auf entfernten Mobs, die auf dem Bildschirm des Spielers nur 4x4 Pixel einnehmen, verschwendet erhebliche GPU-VRAM-Bandbreite und Textur-Sampler-Cachezeilen.

**Camera Culling** enthält eine entkoppelte, 3-stufige **OpenGL-Textur-LOD-Biasing-Engine**, die das Mipmap-Sampling der Texturen dynamisch an die Entfernung der Entität anpasst.

---

## 📋 Abstands-Textur-LOD-Kurzübersicht

| Eigenschaft | Wert |
| :--- | :--- |
| **Ziel-Pipeline** | `LivingEntityRenderer.submit(LivingEntityRenderState, ...)` |
| **Mechanismus** | OpenGL Textur-Sampler Mipmap Bias (`GL_TEXTURE_LOD_BIAS`) |
| **Nah-Schwellenwert** | $< 16.0$ Blöcke $\implies 0.0\text{f}$ Bias (Volle Auflösung) |
| **Mittel-Schwellenwert** | $16.0 - 32.0$ Blöcke $\implies 1.0\text{f}$ Bias (Halbe Auflösung) |
| **Fern-Schwellenwert** | $> 32.0$ Blöcke $\implies 2.5\text{f}$ Bias (Viertel-Auflösung / Mipmap) |
| **Ausnahmen** | Leuchtende Entitäten, lokaler Spieler, Bosse & Mini-Bosse, Entitäten auf der Sperrliste |

---

## 🔬 Mathematisches Distanz-LOD-Biasing

```text
Kamera
  │
  ├── [ 0m bis 16m ] ──► Bias 0.0f  (100% native Textur in voller Auflösung)
  │
  ├── [ 16m bis 32m ] ─► Bias 1.0f  (50% Mipmap-Sampling mit halber Auflösung)
  │
  └── [ > 32m ] ───────► Bias 2.5f  (25% Mipmap-Sampling mit reduzierter Auflösung)
```

Der LOD-Bias $B$ wird rein aus dem Distanzquadrat berechnet:
$$B(d) = \begin{cases} 
0.0\text{f} & \text{wenn } d^2 < \text{startDist}^2 \ (16.0^2) \\
1.0\text{f} & \text{wenn } \text{startDist}^2 \le d^2 < \text{farDist}^2 \ (32.0^2) \\
2.5\text{f} & \text{wenn } d^2 \ge \text{farDist}^2
\end{cases}$$

### OpenGL-Statusisolation
Beim Rendern einer lebenden Entität injiziert `LivingEntityRendererMixin` bei `submit:HEAD`, um den berechneten Bias anzuwenden, und setzt ihn bei `submit:RETURN` sofort wieder auf `0.0f` zurück. Dies garantiert, dass Blockmodelle, Gegenstände und GUI-Elemente niemals beeinträchtigt werden.

---

## 🔗 Verwandte Seiten

- [[Boss- & Blacklist-Immunität|de_de-26.3-Boss-and-Blacklist-Immunity]]
- [[Grafische GUI-Konfiguration (YACL)|de_de-26.3-GUI-Configuration]]
- [[Zurück zur MC 26.3 Übersicht|de_de-26.3-Home]]
