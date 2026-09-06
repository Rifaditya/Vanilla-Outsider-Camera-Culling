# 👥 Defensa de sobredibujado de multitudes de mobs (Minecraft 26.3)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Las granjas densas de mobs, las salas de comercio de aldeanos y los corrales de reproducción pueden provocar caídas severas en los fotogramas cuando cientos de entidades se apilan en unos pocos bloques. Aunque la rasterización Early-Z de la GPU maneja las pruebas de profundidad básicas, extraer y enviar cientos de jerarquías esqueléticas de mobs sobrecarga los despachadores de renderizado de la CPU.

**Camera Culling** proporciona un sistema opcional y seguro de defensa contra el sobredibujado de multitudes.

---

## 📋 Información de defensa de multitudes

| Propiedad | Valor |
| :--- | :--- |
| **Clave de configuración** | `cullEntitiesBehindEntities` (Predeterminado: `false`) |
| **Límite de densidad por grupo** | `maxEntitiesPerCluster` (Predeterminado: `8` mobs / 1.5 bloques) |
| **Descarte rápido por distancia** | $> 16.0$ metros ($256.0\text{m}^2$) |
| **Radio de búsqueda de grupo** | `targetBox.inflate(1.5)` |
| **Entidades exentas** | Mobs transparentes o decorativos (`Vex`, `ArmorStand`, `ItemFrame`, `Slime`, `MagmaCube`) |

---

## 🛑 Arquitectura de descarte rápido a 16 metros

En campos abiertos con manadas dispersas de vacas, ejecutar consultas espaciales sobre todas las entidades genera una carga innecesaria en la CPU. En versiones modernas de Camera Culling, la oclusión de multitudes aplica un **descarte rápido inmediato a 16 metros**:

```java
public static boolean isEntityOccludedByCloserEntities(Entity target, Level level, Vec3 camPos, AABB targetBox, double targetDistSq) {
    if (target == null || level == null || camPos == null) {
        return false;
    }

    // Descarte rápido: solo aplicar oclusión de multitudes a menos de 16 metros
    if (targetDistSq > 256.0) {
        return false;
    }

    // 1. Límite de densidad en esfera estrecha de 1.5 bloques
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
                return true; // Ocluido por superar el límite de densidad
            }
        }
    }
    return false;
}
```

### Beneficios:
1. **Cero sobrecarga en campo abierto**: Los mobs que pastan a más de 16 metros omiten completamente el escaneo de entidades.
2. **Protección en acumulaciones densas**: En fosas de granjas 1x1 o 2x2 con más de 50 vacas o zombis apretados, el renderizado se limita a las 8 entidades frontales, eliminando por completo los tirones de lag.

---

## 🔗 Páginas relacionadas

- [[Oclusión de entidades|es_es-26.3-Entity-Occlusion-Culling]]
- [[Inmunidad de jefes y lista negra|es_es-26.3-Boss-and-Blacklist-Immunity]]
- [[Volver al resumen de MC 26.3|es_es-26.3-Home]]
