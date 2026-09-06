# 🟣 Camera Culling (Minecraft 26.1.2)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Добро пожаловать в центр документации **Minecraft 26.1.2** для мода **Camera Culling** (`v1.10.2+26.1.2`).

> 📌 **Отказ от ответственности об источнике репозитория**: Документация в этой вики отражает **текущее состояние исходного кода в репозитории**, которое может включать недавние невыпущенные коммиты или функции в разработке до их публичного релиза на CurseForge и Modrinth.

---

## 📋 Информация о Minecraft 26.1.2

| Свойство | Значение |
| :--- | :--- |
| **Целевая версия Minecraft** | `26.1.2` |
| **Версия релиза** | `1.10.2+26.1.2` |
| **Требования к Java** | Java 25 (`release = 25`) |
| **Fabric Loader** | `>=0.18.4` |
| **Fabric API** | `0.145.4+26.1.2` |
| **Лицензия** | GNU General Public License v3.0 (GPLv3) |
| **Путь к подпроекту** | `Camera Culling v26.1/Camera Culling 26.1` |

---

## ⚡ Матрица функций

```text
Camera Culling 26.1.2 Pipeline
├── Entity Occlusion (Zero-Allocation Raycast Engine)
├── Block Entity Occlusion (6-Sided Enclosure & Sightlines)
├── 2-Sided Sign Text Culling (Normal Vector Dot Product)
├── Particle Occlusion (4m Proximity Safety + Visual Clip)
├── Animation Culling (TextureAtlas Upload Suppression)
├── Distance Texture LOD (3-Tier OpenGL Mipmap Bias)
├── Boss & Mini-Boss Immunity (Configurable HP Thresholds)
├── Anti-Flicker Temporal Hysteresis (Adaptive 4/8/12-Frame Buffer)
└── Graphical GUI (YACL v3 & ModMenu) + In-Game Commands
```

---

## 📚 Индекс документации 26.1.2

1. [[Окклюзионное отсечение сущностей|ru_ru-26.1.2-Entity-Occlusion-Culling]] — Многоточечная трассировка лучей и фильтрация пола.
2. [[Отсечение блочных сущностей|ru_ru-26.1.2-Block-Entity-Culling]] — Проверка окружения сундуков и блочных сущностей.
3. [[Отсечение текста табличек|ru_ru-26.1.2-Sign-and-Hanging-Sign-Culling]] — Математика скалярного произведения normal dot product для 2-сторонних табличек.
4. [[Отсечение частиц и анимаций|ru_ru-26.1.2-Particle-and-Animation-Culling]] — Отсечение подземных частиц и заморозка анимаций атласа.
5. [[Защита от перерисовки скоплений мобов|ru_ru-26.1.2-Mob-Crowd-Overdraw-Defense]] — Ограничение дистанции 16м и лимит плотности скоплений 1.5м.
6. [[Дистанционные LOD-текстуры|ru_ru-26.1.2-Distance-Texture-LOD]] — Смещение LOD мипмапов OpenGL на удаленных мобах.
7. [[Иммунитет боссов и черный список|ru_ru-26.1.2-Boss-and-Blacklist-Immunity]] — Защита боссов и двухуровневый черный список.
8. [[Временной гистерезис и нулевое выделение памяти|ru_ru-26.1.2-Temporal-Hysteresis-and-Zero-Allocation]] — Буфер задержки и движок без выделения памяти.
9. [[Команды и конфигурация|ru_ru-26.1.2-Commands-and-Configuration]] — Полный синтаксис команд Brigadier.
10. [[Графический интерфейс настроек (YACL)|ru_ru-26.1.2-GUI-Configuration]] — Руководство по графическому меню.
11. [[Отладка и диагностика|ru_ru-26.1.2-Debug-Logging-and-Diagnostics]] — Диагностический чат и трассировка переходов состояний в реальном времени.
12. [[Архитектура и миксины|ru_ru-26.1.2-Architecture-and-Mixins]] — Иерархия пакетов и таблица целей Mixin.
13. [[Настройка разработчика и сборка|ru_ru-26.1.2-Developer-Setup-and-Building]] — Настройка JDK 25 и инструкции по сборке Loom Gradle.
14. [[API и интеграция модов|ru_ru-26.1.2-API-and-Integration]] — Программные хуки для интеграции.

---

[[Вернуться на портал версий|ru_ru-Home]] &bull; [[Матрица совместимости версий|ru_ru-Version-Compatibility]]
