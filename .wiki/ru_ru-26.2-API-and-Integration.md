# 🔌 API и интеграция модов (Minecraft 26.2)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Camera Culling спроектирован для бесшовной совместной работы со сторонними модами рендеринга клиента (такими как Sodium, Iris, Canvas) и контентными модами, добавляющими пользовательские сущности или блочные сущности.

---

## 🤝 Совместимость со сторонними рендерерами

### 1. Sodium и Embeddium
* **Статические чанки мира**: Sodium оптимизирует построение мешей чанков и геометрию граней блоков.
* **Динамические сущности**: Camera Culling оптимизирует динамические сущности, сундуки, таблички и частицы.
* **Совместимость**: 100% совместимость без пересекающихся Mixin-внедрений или конфликтов состояний.

### 2. Iris и Shaders
* При использовании шейдерных пакетов тени сущностей корректно согласуются с отсечением.
* Окклюзионные проверки Camera Culling предотвращают лишние вызовы отрисовки в теневые буферы карт теней.

---

## 🛠️ Программные хуки Java API

Другие моды могут запрашивать состояние или интегрироваться с Camera Culling через статические служебные фасады:

### 1. Запрос состояния движка отсечения
```java
import net.vanillaoutsider.culling.CameraCullingClient;

// Проверка активности главного переключателя мода
boolean isCullingActive = CameraCullingClient.isMasterCullingEnabled();
```

### 2. Доступ к параметрам конфигурации
```java
import net.vanillaoutsider.culling.config.CameraCullingConfig;
import net.vanillaoutsider.culling.config.CullingProfile;

// Получение текущего уровня профиля отсечения
CullingProfile currentProfile = CameraCullingConfig.getCullingLevel();

// Проверка включения отсечения частиц
boolean cullingParticles = CameraCullingConfig.isCullParticles();
```

### 3. Служебная трассировка без выделения памяти
```java
import net.vanillaoutsider.culling.util.CullingRaycastHelper;

// Быстрая проверка прямой видимости без создания объектов Vec3
boolean visible = CullingRaycastHelper.hasLineOfSight(level, cameraPos, targetX, targetY, targetZ);
```

---

## 🔗 Связанные страницы

- [[Архитектура и миксины|ru_ru-26.2-Architecture-and-Mixins]]
- [[Настройка разработчика и сборка|ru_ru-26.2-Developer-Setup-and-Building]]
- [[Вернуться к обзору MC 26.2|ru_ru-26.2-Home]]
