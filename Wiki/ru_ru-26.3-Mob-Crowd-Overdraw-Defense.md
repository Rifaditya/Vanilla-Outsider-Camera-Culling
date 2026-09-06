# 👥 Защита от перерисовки скоплений мобов (Minecraft 26.3)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Плотные фермы мобов, залы торговли жителей и загоны для разведения животных могут вызывать резкое падение частоты кадров, когда сотни сущностей скапливаются в пределах нескольких блоков. Хотя растровая проверка глубины GPU Early-Z берет на себя базовый тест Z-буфера, извлечение и отправка сотен скелетных иерархий мобов перегружает диспетчер рендеринга CPU.

**Camera Culling** предоставляет опциональную, защищенную систему защиты от перерисовки скоплений.

---

## 📋 Инфобокс защиты от скоплений мобов

| Свойство | Значение |
| :--- | :--- |
| **Ключ конфигурации** | `cullEntitiesBehindEntities` (По умолчанию: `false`) |
| **Лимит плотности скопления** | `maxEntitiesPerCluster` (По умолчанию: `8` мобов / 1.5 блока) |
| **Быстрый сброс по дистанции** | $> 16.0$ метров ($256.0\text{m}^2$) |
| **Радиус поиска скопления** | `targetBox.inflate(1.5)` |
| **Исключенные сущности** | Прозрачные / Декоративные мобы (`Vex`, `ArmorStand`, `ItemFrame`, `Slime`, `MagmaCube`) |

---

## 🛑 Архитектура быстрого сброса на 16 метрах

На больших открытых полях с рассеянными стадами коров выполнение пространственных запросов ко всем мобам создает лишнюю нагрузку на процессор. В современных версиях Camera Culling отсечение перекрытия мобами мобов использует немедленный **быстрый сброс на дистанции 16 метров**:

```java
public static boolean isEntityOccludedByCloserEntities(Entity target, Level level, Vec3 camPos, AABB targetBox, double targetDistSq) {
    if (target == null || level == null || camPos == null) {
        return false;
    }

    // Быстрый сброс: применять отсечение скоплений только в пределах 16 метров
    if (targetDistSq > 256.0) {
        return false;
    }

    // 1. Лимит плотности в тесной сфере 1.5 блока
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
                return true; // Отсечено по лимиту плотности скопления
            }
        }
    }
    return false;
}
```

### Преимущества:
1. **Ноль накладных расходов в открытом поле**: Мобы, пасущиеся дальше 16 метров, полностью пропускают сканирование сущностей.
2. **Защита плотных загонов**: В накопителях мобоферм 1x1 или 2x2, где спрессовано 50+ коров или зомби, рендеринг ограничивается 8 передними сущностями, полностью устраняя лаги.

---

## 🔗 Связанные страницы

- [[Окклюзионное отсечение сущностей|ru_ru-26.3-Entity-Occlusion-Culling]]
- [[Иммунитет боссов и черный список|ru_ru-26.3-Boss-and-Blacklist-Immunity]]
- [[Вернуться к обзору MC 26.3|ru_ru-26.3-Home]]
