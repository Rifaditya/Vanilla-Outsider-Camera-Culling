# 📦 Occlusion des entités de bloc (Minecraft 26.2)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Les entités de bloc (coffres, coffres de l'Ender, panneaux, bannières, crânes, vases décorés, cloches et balises) sont des éléments de rendu dynamiques. Parce qu'elles contournent la compilation du maillage statique des chunks de Minecraft Vanilla, elles émettent des appels de rendu (draw calls) individuels à chaque frame. Dans les salles de stockage ou les systèmes de tri automatisés contenant des centaines de coffres, cela engendre d'importants goulots d'étranglement GPU.

---

## 📋 Fiche d'information de l'occlusion des entités de bloc

| Propriété | Valeur |
| :--- | :--- |
| **Pipeline cible** | `BlockEntityRenderDispatcher.tryExtractRenderState(BlockEntity, float, CrumblingOverlay, boolean)` |
| **Détection d'enclosure** | Vérifie les 6 faces voisines : `up`, `down`, `north`, `south`, `east`, `west` |
| **Mode conservateur** | Vérification d'enclosure uniquement sous le profil `LOW` |
| **Mode agressif** | Vérification complète par raycast sous les profils `MEDIUM`, `HIGH` et `SUPER` |
| **Résultat** | Renvoie un RenderState `null` pour ignorer l'étape de rendu |

---

## 🔍 Architecture de vérification de l'enclosure et de la visibilité

```text
                [HAUT]
                  │
   [OUEST] ─── [COFFRE] ─── [EST]
                  │
                [BAS]
```

### 1. Détection rapide de confinement solide sur 6 faces
Avant d'effectuer tout calcul trigonométrique ou raycast, Camera Culling inspecte l'état des blocs adjacents :
```java
BlockState up = level.getBlockState(pos.above());
BlockState down = level.getBlockState(pos.below());
BlockState north = level.getBlockState(pos.north());
BlockState south = level.getBlockState(pos.south());
BlockState east = level.getBlockState(pos.east());
BlockState west = level.getBlockState(pos.west());

if (up.isSolidRender() && down.isSolidRender() && north.isSolidRender()
    && south.isSolidRender() && east.isSolidRender() && west.isSolidRender()) {
    return true; // 100 % Masqué — rendu ignoré
}
```
Les coffres encastrés derrière des murs ou enfermés dans les fondations d'un bâtiment sont ainsi masqués avec une charge CPU quasi nulle ($< 0.0001\mu\text{s}$).

### 2. Vérification de la ligne de visée par raycast
Dans les profils `MEDIUM`, `HIGH` et `SUPER` (`cullAllBlockEntities = true`), Camera Culling projette un rayon depuis la caméra du joueur jusqu'au centre de l'entité de bloc $(X + 0.5, Y + 0.5, Z + 0.5)$ :
* Si le raycast rencontre un bloc opaque solide avant d'atteindre l'entité de bloc, l'état de rendu est immédiatement annulé.
* Si la ligne de visée est dégagée, l'entité de bloc est rendue avec une fidélité graphique totale.

---

## 🔗 Pages connexes

- [[Occlusion de texte des panneaux|fr_fr-26.2-Sign-and-Hanging-Sign-Culling]]
- [[Occlusion des entités|fr_fr-26.2-Entity-Occlusion-Culling]]
- [[Architecture et Mixins|fr_fr-26.2-Architecture-and-Mixins]]
- [[Retour à la vue d'ensemble MC 26.2|fr_fr-26.2-Home]]
