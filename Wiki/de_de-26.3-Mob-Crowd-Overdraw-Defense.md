# 👥 Mob-Mengen-Overdraw-Schutz (Minecraft 26.3)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Dichte Mob-Farmen, Dorfbewohner-Handelshallen und Tierzuchtgehege können extreme Framerate-Einbrüche verursachen, wenn Hunderte von Entitäten auf wenigen Blöcken zusammengedrängt sind. Während die Early-Z-Rasterung der GPU einfache Tiefentests übernimmt, überlastet das Extrahieren und Übertragen Hunderter Skeletthierarchien den CPU-Render-Dispatcher.

**Camera Culling** bietet ein optionales, abgesichertes Schutzsystem gegen Overdraw bei dichten Mob-Gruppen.

---

## 📋 Mob-Mengen-Schutz-Kurzübersicht

| Eigenschaft | Wert |
| :--- | :--- |
| **Konfigurationsschlüssel** | `cullEntitiesBehindEntities` (Standard: `false`) |
| **Cluster-Dichteobergrenze** | `maxEntitiesPerCluster` (Standard: `8` Mobs / 1,5 Blöcke) |
| **Distanz-Fast-Fail** | $> 16.0$ Meter ($256.0\text{m}^2$) |
| **Cluster-Suchradius** | `targetBox.inflate(1.5)` |
| **Ausgenommene Entitäten** | Transparente / dekorative Mobs (`Vex`, `ArmorStand`, `ItemFrame`, `Slime`, `MagmaCube`) |

---

## 🛑 16-Meter-Distanz-Fast-Fail-Architektur

In weitläufigen offenen Landschaften mit verstreuten Kuhherden verursacht das Ausführen räumlicher Abfragen über alle Mobs unnötigen CPU-Overhead. In modernen Versionen von Camera Culling erzwingt das Mob-Overdraw-Culling einen sofortigen **16-Meter-Fast-Fail**:

```java
public static boolean isEntityOccludedByCloserEntities(Entity target, Level level, Vec3 camPos, AABB targetBox, double targetDistSq) {
    if (target == null || level == null || camPos == null) {
        return false;
    }

    // Fast-Fail: Overdraw-Culling nur innerhalb von 16 Metern anwenden
    if (targetDistSq > 256.0) {
        return false;
    }

    // 1. Cluster-Dichteobergrenze im engen 1,5-Block-Radius
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
                return true; // Wegen Cluster-Dichteobergrenze ausgeblendet
            }
        }
    }
    return false;
}
```

### Vorteile:
1. **Kein Overhead auf offenem Feld**: Weidende Mobs jenseits von 16 Metern überspringen Suchdurchläufe vollständig.
2. **Schutz in überfüllten Zuchtbecken**: In 1x1- oder 2x2-Schreddern mit mehr als 50 Kühen oder Zombies wird das Rendering auf die vordersten 8 Entitäten begrenzt, wodurch Lag-Spitzen vermieden werden.

---

## 🔗 Verwandte Seiten

- [[Entitäts-Okklusions-Culling|de_de-26.3-Entity-Occlusion-Culling]]
- [[Boss- & Blacklist-Immunität|de_de-26.3-Boss-and-Blacklist-Immunity]]
- [[Zurück zur MC 26.3 Übersicht|de_de-26.3-Home]]
