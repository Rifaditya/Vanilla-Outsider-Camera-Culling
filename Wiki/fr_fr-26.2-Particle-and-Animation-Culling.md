# ✨ Occlusion des particules et animations (Minecraft 26.2)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Dans Minecraft Vanilla, les gouttes de lave souterraines, la poussière des grottes, les particules de portail et les torches génèrent des centaines de quads de particules derrière d'épais murs de pierre qui sont envoyés au GPU. Parallèlement, les atlas de textures de blocs animés envoient continuellement des données de frames au processeur graphique même lorsque le joueur est en pause en solo ou consulte un menu.

**Camera Culling** introduit des vérifications de ligne de visée rapides pour les particules ainsi que la suspension des envois d'atlas de textures.

---

## 📋 Fiche d'information de l'occlusion des particules et animations

| Propriété | Valeur |
| :--- | :--- |
| **Cible particules** | `SingleQuadParticle.extract(QuadParticleRenderState, Camera, float)` |
| **Cible animations** | `TextureAtlas.cycleAnimationFrames()` |
| **Bulle de proximité sécurisée** | 4,0 mètres ($16.0\text{m}^2$) autour de la caméra |
| **Distance maximale particules** | 64,0 mètres ($4096.0\text{m}^2$) |
| **Déclencheur de pause atlas** | Jeu solo en pause OU aucun monde/joueur actif |

---

## 🌪️ Pipeline d'occlusion des particules

```text
Particule générée en (X, Y, Z)
        │
        ▼
[1] Distance <= 4m ? ────────► RENDRE (Bulle de proximité)
        │ Non
        ▼
[2] Distance > 64m ? ────────► MASQUER (Coupure éloignée)
        │ Non
        ▼
[3] Dans un bloc solide ? ───► MASQUER (Enfermée)
        │ Non
        ▼
[4] Raycast visuel bloqué ?
        ├── Oui ─────────────► MASQUER (Obstruée)
        └── Non ─────────────► RENDRE (Visible)
```

1. **Bulle de proximité sécurisée (4,0m)** : Les particules émises à moins de 4 mètres de la caméra (par exemple les tourbillons de potions, la poussière de course, les effets de balayage d'attaque) sont rendues inconditionnellement en $< 0.0001\mu\text{s}$.
2. **Coupure à longue distance (64,0m)** : Les particules générées au-delà de 64 mètres sont éliminées pour préserver le taux de remplissage des quads GPU.
3. **Enclosure dans un bloc solide** : Si la coordonnée du bloc `BlockPos.containing(x, y, z)` a `isSolidRender() == true`, la particule est abandonnée immédiatement.
4. **Raycast de découpe visuelle** : Projette un rayon `ClipContext.Block.VISUAL` de la caméra vers le vecteur de la particule. Si un bloc opaque solide intercepte la trajectoire avec une marge $> 0.35\text{m}$, le rendu est ignoré.

---

## 🎬 Gel des animations de blocs et de l'atlas de textures

Les animations de l'atlas de textures (lanternes aquatiques, lave/eau en mouvement, prismarine, feu, boussole, horloge) consomment de la bande passante GPU pour cycler les index de frames.

Camera Culling vérifie `AnimationCullingHelper.shouldPauseAtlasAnimation()` dans `TextureAtlasMixin` :
* Lorsque la partie solo est en pause (`mc.isPaused() == true`), les envois d'atlas sont suspendus.
* Sur l'écran titre, dans les menus modaux ou en cas de déconnexion, le cycle des textures en arrière-plan est arrêté.

---

## 🔗 Pages connexes

- [[Occlusion des entités|fr_fr-26.2-Entity-Occlusion-Culling]]
- [[Commandes et configuration|fr_fr-26.2-Commands-and-Configuration]]
- [[Retour à la vue d'ensemble MC 26.2|fr_fr-26.2-Home]]
