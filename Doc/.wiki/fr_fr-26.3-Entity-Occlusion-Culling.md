# 🧱 Occlusion des entités (Minecraft 26.3)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Dans Minecraft 26.3, le pipeline de rendu client des entités extrait les états de rendu (`EntityRenderState`) pour toutes les entités situées à l'intérieur du cône de vision de la caméra (frustum) — même si elles sont masquées derrière des montagnes, d'épais murs de pierre ou dans des donjons souterrains. **Camera Culling** intercepte cette vérification pour empêcher les entités cachées de surcharger le processeur avec des calculs géométriques superflus et la carte graphique avec des appels de rendu (draw calls) inutiles.

---

## 📋 Fiche d'information de l'occlusion des entités

| Propriété | Valeur |
| :--- | :--- |
| **Pipeline cible** | `EntityRenderer.shouldRender(T, Frustum, double, double, double)` |
| **Profil par défaut** | `SUPER` (Extrême) |
| **Contexte de découpe** | `ClipContext.Block.COLLIDER`, `ClipContext.Fluid.NONE` |
| **Filtrage du sol** | Ignore les impacts `Direction.UP` lorsque la hauteur d'impact $\le Y + 0.15\text{m}$ |
| **Occlusion du feuillage** | Le rendu solide et les blocs `BlockTags.LEAVES` bloquent les lignes de visée |
| **Bulle d'immunité** | Distance au carré $< \text{minDistanceSq}$ |

---

## 🔬 Échantillonnage anatomique multipoint de la ligne de visée

Au lieu d'un lancer de rayon (raycast) simpliste à point unique qui provoque l'apparition ou la disparition brutale des créatures aux angles des murs, Camera Culling effectue un échantillonnage anatomique multipoint basé sur le [[Profil d'occlusion|fr_fr-26.3-Commands-and-Configuration]] sélectionné :

```text
       [1] Sommet de la tête (maxY - 0.05)
          \
           [2] Position des yeux (entity.getEyeY())
            \
             [3] Haut du torse / Poitrine (minY + height * 0.70)
              \
               [4] Centre géométrique (centerY)
                \
        [5-8] Flancs périmétriques surélevés (vérifications largeur/profondeur)
```

1. **Échantillon 1 : Sommet de la tête de l'entité (`maxY - 0.05`)**
   - Test prioritaire élevé. Détecte les entités de grande taille qui dépassent au-dessus de barricades ou de clôtures basses.
2. **Échantillon 2 : Position anatomique des yeux (`getEyeY()`)**
   - Ligne de visée directe entre la caméra et les yeux de la créature.
3. **Échantillon 3 : Haut du torse / Poitrine (`minY + height * 0.70`)**
   - Évalue la visibilité du buste nettement au-dessus du niveau du sol.
4. **Échantillon 4 : Centre géométrique (`(minY + maxY) * 0.5`)**
   - Test général du point médian géométrique.
5. **Échantillons 5 à 8 : Flancs périmétriques surélevés**
   - Évalue $(X_{\min} + 0.15, Z_{\min} + 0.15)$, $(X_{\max} - 0.15, Z_{\min} + 0.15)$, etc. Garantit que les boss imposants (par exemple Ravageurs, Warden, Araignées) restent visibles dès qu'une de leurs épaules dépasse d'un angle.

---

## 🛡️ Filtrage directionnel du sol et des pentes

Lorsqu'un joueur regarde vers le bas en direction d'une créature sur un terrain accidenté, un raycast standard peut heurter la surface supérieure d'un bloc situé aux pieds de la créature et considérer à tort le sol comme un obstacle bloquant la vue.

Camera Culling intègre un **filtrage directionnel des impacts au sol** :
$$\text{Si } \text{hit.getDirection()} == \text{Direction.UP} \quad \text{et} \quad Y_{\text{impact}} \le Y_{\text{cible}} + 0.15\text{m} \implies \text{Ligne de visée valide (Non bloquée)}$$

Cette logique garantit que les créatures arpentant des collines, des escaliers et des terrains irréguliers ne sont jamais masquées par erreur.

---

## ⚡ Moteur de raycasting sans allocation mémoire

Dans les versions antérieures d'autres mods, l'évaluation de 8 points d'échantillonnage sur 100 entités générait plus de 180 000 allocations d'objets `new Vec3()` par seconde sur le tas (heap), provoquant des micro-saccades dues aux pauses du ramasse-miettes (GC Young-Gen) de la JVM.

Dans la version 26.3, `CullingRaycastHelper` transmet directement des coordonnées primitives brutes :
```java
public static boolean hasLineOfSight(Level level, Vec3 from, double toX, double toY, double toZ)
```
Cette architecture supprime intégralement les allocations mémoire intermédiaires à chaque frame.

---

## 🔗 Pages connexes

- [[Défense contre le surdessin des foules|fr_fr-26.3-Mob-Crowd-Overdraw-Defense]]
- [[Hystérésis temporelle et zéro allocation|fr_fr-26.3-Temporal-Hysteresis-and-Zero-Allocation]]
- [[Immunité des boss et liste noire|fr_fr-26.3-Boss-and-Blacklist-Immunity]]
- [[Retour à la vue d'ensemble MC 26.3|fr_fr-26.3-Home]]
