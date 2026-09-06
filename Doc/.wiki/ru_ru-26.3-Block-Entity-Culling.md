# 📦 Отсечение блочных сущностей (Minecraft 26.3)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Блочные сущности (сундуки, сундуки Края, таблички, флаги, черепа, декорированные горшки, колокола и маяки) являются динамическими элементами рендеринга. Поскольку они обходят компиляцию ванильных мешей чанков, они отправляют индивидуальные вызовы отрисовки каждый кадр. В хранилищах или автоматических сортировках с сотнями сундуков это создает значительные узкие места GPU.

---

## 📋 Инфобокс отсечения блочных сущностей

| Свойство | Значение |
| :--- | :--- |
| **Целевой конвейер** | `BlockEntityRenderDispatcher.tryExtractRenderState(...)` |
| **Обнаружение окружения** | Проверяет все 6 соседних граней: `up`, `down`, `north`, `south`, `east`, `west` |
| **Консервативный режим** | Проверка только полного окружения на профиле `LOW` |
| **Агрессивный режим** | Полная проверка линии взгляда трассировкой лучей на `MEDIUM`, `HIGH`, `SUPER` |

---

## 🧱 Проверка окружения и линии взгляда

```text
Camera
  │
  ├── [1] 6-Sided Solid Enclosure Check ──► CULL (Skip Draw Call)
  │
  └── [2] Line-of-Sight Raycast ──────────► RENDER or CULL
```

### 1. Быстрый проход 6-стороннего твердого окружения
Перед выполнением любых расчетов трассировки Camera Culling опрашивает состояния соседних блоков:
```java
BlockState up = level.getBlockState(pos.above());
BlockState down = level.getBlockState(pos.below());
BlockState north = level.getBlockState(pos.north());
BlockState south = level.getBlockState(pos.south());
BlockState east = level.getBlockState(pos.east());
BlockState west = level.getBlockState(pos.west());

if (isSolid(up) && isSolid(down) && isSolid(north) && isSolid(south) && isSolid(east) && isSolid(west)) {
    return true; // Полностью перекрыто — пропустить рендеринг
}
```
Сундуки, замурованные в стенах или окруженные твердым фундаментом подвала, отсекаются практически без нагрузки на процессор ($< 0.0001\mu\text{s}$).

### 2. Проверка линии взгляда трассировкой луча
На профилях `MEDIUM`, `HIGH` и `SUPER` (`cullAllBlockEntities = true`) Camera Culling проецирует луч из позиции камеры игрока к центру блочной сущности $(X + 0.5, Y + 0.5, Z + 0.5)$:
* Если луч сталкивается с непрозрачным твердым блоком до достижения целевой блочной сущности, состояние рендеринга сбрасывается.
* Если существует прямая линия взгляда, блочная сущность отрисовывается с полной точностью.

---

## 🔗 Связанные страницы

- [[Отсечение текста табличек|ru_ru-26.3-Sign-and-Hanging-Sign-Culling]]
- [[Окклюзионное отсечение сущностей|ru_ru-26.3-Entity-Occlusion-Culling]]
- [[Архитектура и миксины|ru_ru-26.3-Architecture-and-Mixins]]
- [[Вернуться к обзору MC 26.3|ru_ru-26.3-Home]]
