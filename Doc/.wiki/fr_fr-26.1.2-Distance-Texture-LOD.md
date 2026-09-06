# 🎨 LOD de textures de mobs par distance (Minecraft 26.1.2)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Rendre des textures haute résolution complètes en 1024x1024 sur des créatures éloignées qui n'occupent que 4x4 pixels à l'écran gaspille inutilement la bande passante VRAM et pollue les lignes de cache des échantillonneurs de texture du GPU.

**Camera Culling** intègre un **moteur de biais de LOD de texture OpenGL** à 3 niveaux totalement découplé, qui ajuste dynamiquement l'échantillonnage des mipmaps selon la distance de l'entité.

---

## 📋 Fiche d'information du LOD de texture par distance

| Propriété | Valeur |
| :--- | :--- |
| **Pipeline cible** | `LivingEntityRenderer.submit(LivingEntityRenderState, ...)` |
| **Mécanisme** | Biais d'échantillonnage mipmap OpenGL (`GL_TEXTURE_LOD_BIAS`) |
| **Seuil proche** | $< 16.0$ blocs $\implies 0.0\text{f}$ de biais (Pleine résolution) |
| **Seuil moyen** | $16.0 - 32.0$ blocs $\implies 1.0\text{f}$ de biais (Résolution réduite de moitié) |
| **Seuil éloigné** | $> 32.0$ blocs $\implies 2.5\text{f}$ de biais (Résolution au quart / Mipmap) |
| **Exemptions** | Entités en surbrillance, joueur local, Boss et Mini-Boss, entités sur liste noire |

---

## 🔬 Biais mathématique du LOD de texture par distance

```text
Caméra
  │
  ├── [ 0m à 16m ] ────► Biais 0.0f  (100 % Texture native pleine résolution)
  │
  ├── [ 16m à 32m ] ───► Biais 1.0f  (50 % Échantillonnage mipmap demi-résolution)
  │
  └── [ > 32m ] ───────► Biais 2.5f  (25 % Échantillonnage mipmap basse résolution)
```

Le biais de LOD $B$ est calculé purement d'après le carré de la distance :
$$B(d) = \begin{cases} 
0.0\text{f} & \text{si } d^2 < \text{startDist}^2 \ (16.0^2) \\
1.0\text{f} & \text{si } \text{startDist}^2 \le d^2 < \text{farDist}^2 \ (32.0^2) \\
2.5\text{f} & \text{si } d^2 \ge \text{farDist}^2
\end{cases}$$

### Isolation d'état OpenGL
Lors du rendu d'une créature vivante, `LivingEntityRendererMixin` injecte au point `submit:HEAD` pour appliquer le biais calculé, et le réinitialise immédiatement à `0.0f` au point `submit:RETURN`. Cela garantit que les modèles de blocs, les objets tenus en main et l'interface utilisateur ne sont jamais affectés.

---

## 🔗 Pages connexes

- [[Immunité des boss et liste noire|fr_fr-26.1.2-Boss-and-Blacklist-Immunity]]
- [[Configuration graphique GUI (YACL)|fr_fr-26.1.2-GUI-Configuration]]
- [[Retour à la vue d'ensemble MC 26.1.2|fr_fr-26.1.2-Home]]
