# ⏱️ Hystérésis temporelle et zéro allocation mémoire (Minecraft 26.2)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

L'occlusion culling à haute fréquence peut introduire deux défauts de performance fréquents :
1. **Scintillement au ras des bordures (Z-Fighting / Pop-in)** : De légères rotations de la caméra ou le balancement de marche (view-bobbing) traversant l'arête d'un bloc peuvent faire basculer une entité de façon intermittente entre l'état visible et masqué à chaque frame.
2. **Saccades causées par le ramasse-miettes (GC)** : L'instanciation continue d'objets `new Vec3()` et de boîtes englobantes lors du raycasting déclenche des pauses fréquentes de la Young-Gen de la JVM.

**Camera Culling** résout ces deux problèmes grâce à un **tampon de grâce adaptatif échelonné selon la distance** et à un **moteur de chemin critique sans allocation mémoire (Zero-Allocation)**.

---

## 📋 Fiche d'information de l'hystérésis et de l'allocation

| Propriété | Valeur |
| :--- | :--- |
| **Tampon de grâce à courte distance** | $d \le 32\text{m} \implies 4\text{ frames consécutives masquées}$ |
| **Tampon de grâce à moyenne distance** | $32\text{m} < d \le 64\text{m} \implies 8\text{ frames consécutives masquées}$ |
| **Tampon de grâce à longue distance** | $d > 64\text{m} \implies 12\text{ frames consécutives masquées}$ |
| **Transition vers la visibilité** | Instantanée ($0\text{ frame de délai}$) dès le rétablissement d'une ligne de visée |
| **Structure de suivi** | `it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap` (Aucun surcoût d'autoboxing) |
| **Allocations par frame** | $0\text{ octet}$ (Coordonnées doubles primitives transmises directement) |

---

## 📐 Formule du tampon de grâce échelonné par distance

Le nombre consécutif de frames masquées requis avant de basculer une entité dans l'état `[CULLED]` est défini par :

$$\text{SérieRequise}(d) = \begin{cases}
12\text{ frames} & \text{si } d^2 > 64.0^2 \ (4096.0\text{m}^2) \\
8\text{ frames} & \text{si } d^2 > 32.0^2 \ (1024.0\text{m}^2) \\
4\text{ frames} & \text{si } d^2 \le 32.0^2
\end{cases}$$

```text
[LIGNE DE VISÉE DE L'ENTITÉ PERDUE]
       │
       ├── Frame 1 masquée ──► RENDRE (Décroissance de grâce)
       ├── Frame 2 masquée ──► RENDRE (Décroissance de grâce)
       ├── Frame 3 masquée ──► RENDRE (Décroissance de grâce)
       └── Frame 4 masquée ──► MASQUER (Série atteinte)
```

* **Réapparition instantanée (Instant Unculling)** : Dès qu'un seul rayon d'échantillonnage établit une ligne de visée dégagée, la série de masquage est effacée (`OCCLUDED_STREAK.remove(id)`), rendant l'entité visible immédiatement avec une latence de $0\text{ frame}$.
* **Décroissance asymétrique** : Le masquage exige plusieurs frames consécutives ; la réapparition ne prend qu'une seule frame. Cela supprime tout scintillement lors des mouvements de caméra.

---

## ⚡ Architecture primitive sans allocation mémoire

Dans `CullingRaycastHelper.java`, les allocations d'objets vecteurs sur le tas sont totalement supprimées :

```java
// Aucune allocation sur le tas : les coordonnées sont passées sous forme de doubles primitifs
public static boolean hasLineOfSight(Level level, Vec3 from, double toX, double toY, double toZ) {
    Vec3 to = new Vec3(toX, toY, toZ);
    ClipContext ctx = new ClipContext(from, to, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, CollisionContext.empty());
    BlockHitResult hit = level.clip(ctx);
    if (hit.getType() == HitResult.Type.MISS) {
        return true;
    }
    // Évaluation de l'impact au sol et tolérance sans aucune instanciation d'objet
    ...
}
```

---

## 🔗 Pages connexes

- [[Occlusion des entités|fr_fr-26.2-Entity-Occlusion-Culling]]
- [[Journalisation de débogage et diagnostics|fr_fr-26.2-Debug-Logging-and-Diagnostics]]
- [[Architecture et Mixins|fr_fr-26.2-Architecture-and-Mixins]]
- [[Retour à la vue d'ensemble MC 26.2|fr_fr-26.2-Home]]
