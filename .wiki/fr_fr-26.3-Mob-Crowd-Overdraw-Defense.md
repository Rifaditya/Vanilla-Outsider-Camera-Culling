# 👥 Défense contre le surdessin des foules de mobs (Minecraft 26.3)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Les fermes à créatures très denses, les salles d'échange de villageois et les enclos de reproduction d'animaux peuvent provoquer des chutes de framerate catastrophiques lorsque des centaines d'entités s'entassent sur quelques blocs. Bien que la rastérisation Early-Z du processeur graphique prenne en charge les tests de profondeur de base, l'extraction et l'envoi de centaines de hiérarchies squelettiques surchargent les dispatchers de rendu du CPU.

**Camera Culling** propose un système optionnel et sécurisé de défense contre le surdessin des foules.

---

## 📋 Fiche d'information de la défense contre le surdessin

| Propriété | Valeur |
| :--- | :--- |
| **Clé de configuration** | `cullEntitiesBehindEntities` (Par défaut : `false`) |
| **Plafond de densité de cluster** | `maxEntitiesPerCluster` (Par défaut : `8` créatures / 1,5 bloc) |
| **Échec rapide de distance** | $> 16.0$ mètres ($256.0\text{m}^2$) |
| **Rayon de recherche de cluster** | `targetBox.inflate(1.5)` |
| **Entités exemptées** | Créatures transparentes ou décoratives (`Vex`, `ArmorStand`, `ItemFrame`, `Slime`, `MagmaCube`) |

---

## 🛑 Architecture d'échec rapide à 16 mètres

Dans les grands espaces ouverts où paissent des vaches dispersées, exécuter des requêtes spatiales sur toutes les créatures entraîne une charge CPU superflue. Dans les versions modernes de Camera Culling, la défense de surdessin applique un **échec rapide immédiat au-delà de 16 mètres** :

```java
public static boolean isEntityOccludedByCloserEntities(Entity target, Level level, Vec3 camPos, AABB targetBox, double targetDistSq) {
    if (target == null || level == null || camPos == null) {
        return false;
    }

    // Échec rapide : n'applique le culling de foule qu'à moins de 16 mètres
    if (targetDistSq > 256.0) {
        return false;
    }

    // 1. Plafonnement de densité dans un rayon restreint de 1,5 bloc
    int maxCluster = CameraCullingConfig.getMaxEntitiesPerCluster();
    AABB clusterBox = targetBox.inflate(1.5);
    List<Entity> clusterEntities = level.getEntities(target, clusterBox, 
        e -> e instanceof LivingEntity && !isTransparentOrDecorative(e));
    
    if (clusterEntities.size() < maxCluster) {
        return false;
    }

    int closerInCluster = 0;
    for (Entity e : clusterEntities) {
        double distSq = camPos.distanceToSqr(e.getX(), e.getY(), e.getZ());
        if (distSq < targetDistSq) {
            closerInCluster++;
            if (closerInCluster >= maxCluster) {
                return true; // Masqué en raison du dépassement du plafond de cluster
            }
        }
    }
    return false;
}
```

### Avantages :
1. **Aucun impact en terrain découvert** : Les animaux paissant au-delà de 16 mètres ignorent totalement les balayages de recherche d'entités.
2. **Protection dans les enclos surpeuplés** : Dans les broyeurs 1x1 ou 2x2 où s'entassent plus de 50 vaches ou zombies, le rendu est plafonné aux 8 entités les plus proches de la caméra, éliminant ainsi les pics de lag.

---

## 🔗 Pages connexes

- [[Occlusion des entités|fr_fr-26.3-Entity-Occlusion-Culling]]
- [[Immunité des boss et liste noire|fr_fr-26.3-Boss-and-Blacklist-Immunity]]
- [[Retour à la vue d'ensemble MC 26.3|fr_fr-26.3-Home]]
