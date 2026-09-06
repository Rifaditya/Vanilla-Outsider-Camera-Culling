# 🎮 Команды и конфигурация (Minecraft 26.1.2)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Camera Culling предоставляет полный набор внутриигровых команд Brigadier (`/cameraculling`) и прозрачное сохранение конфигурации в формате JSON (`config/camera-culling.json`).

---

## 📋 Таблица команд

```sql
/cameraculling status
/cameraculling toggle
/cameraculling set <low|medium|high|super>
/cameraculling particles [true|false]
/cameraculling animations [true|false]
/cameraculling crowdculling <true|false>
/cameraculling cluster <1-128>
/cameraculling texturlod <true|false>
/cameraculling texturlod range <start_dist> <far_dist>
/cameraculling bossimmunity <true|false>
/cameraculling bosshealth <boss_hp>
/cameraculling bosshealth <boss_hp> <miniboss_hp>
/cameraculling minibosshealth <miniboss_hp>
/cameraculling blacklist <add|remove|list|clear> <entity_id>
/cameraculling serverblacklist <add|remove|list|clear> <entity_id>
/cameraculling debug [true|false]
/cameraculling reload
```

---

## ⚙️ Подробный разбор команд

| Синтаксис команды | Параметры | Функция |
| :--- | :--- | :--- |
| `/cameraculling status` | Нет | Показывает текущую статистику, счетчики отрисованных/отсеченных сущностей, активный профиль и размер черного списка. |
| `/cameraculling toggle` | Нет | Инвертирует главное состояние включения мода. |
| `/cameraculling set <profile>` | `low`, `medium`, `high`, `super` | Переключает профиль интенсивности отсечения. |
| `/cameraculling particles [bool]` | `true`, `false` (опционально) | Переключает окклюзионное отсечение частиц за сплошными блоками. |
| `/cameraculling animations [bool]` | `true`, `false` (опционально) | Переключает заморозку анимаций блоков и текстурных атласов. |
| `/cameraculling crowdculling <bool>` | `true`, `false` | Переключает отсечение мобов, перекрытых другими сущностями. |
| `/cameraculling cluster <int>` | от `1` до `128` | Задает макс. число отображаемых мобов на кластер 1.5 блока (по умолчанию: `8`). |
| `/cameraculling texturlod <bool>` | `true`, `false` | Включает или отключает масштабирование LOD мипмапов текстур по расстоянию. |
| `/cameraculling texturlod range <start> <far>` | `1.0..256.0`, `2.0..512.0` | Задает ближний и дальний пороги для масштабирования LOD (например, `16.0 32.0`). |
| `/cameraculling bossimmunity <bool>` | `true`, `false` | Включает или отключает защиту линии взгляда боссов и мини-боссов. |
| `/cameraculling bosshealth <hp>` | от `1.0` до `10000.0` | Устанавливает порог здоровья для главных боссов (например, `150.0`). |
| `/cameraculling minibosshealth <hp>` | от `1.0` до `10000.0` | Устанавливает порог здоровья для мини-боссов (например, `50.0`). |
| `/cameraculling blacklist add <id>` | ID сущности | Добавляет сущность (например, `minecraft:wolf`) в персональный клиентский список иммунитета. |
| `/cameraculling blacklist remove <id>` | ID сущности | Удаляет сущность из персонального списка иммунитета. |
| `/cameraculling blacklist list` | Нет | Отображает список всех сущностей в персональном черном списке. |
| `/cameraculling blacklist clear` | Нет | Очищает все записи в персональном черном списке. |
| `/cameraculling serverblacklist ...` | Подкоманда + ID | Настраивает серверный список иммунитета (требуются права оператора). |
| `/cameraculling debug [bool]` | `true`, `false` (опционально) | Переключает диагностическую трассировку переходов состояний в чат и логи в реальном времени. |
| `/cameraculling reload` | Нет | Перезагружает файлы конфигурации с диска. |

---

## 📄 Формат конфигурации JSON

Файлы конфигурации располагаются в директории `.minecraft/config/`:

### Конфигурация клиента (`config/camera-culling.json`)
```json
{
  "enabled": true,
  "level": "SUPER",
  "cullEntitiesBehindEntities": false,
  "maxEntitiesPerCluster": 8,
  "distanceTextureLod": true,
  "distanceTextureLodStart": 16.0,
  "distanceTextureLodFar": 32.0,
  "bossImmunity": true,
  "bossHealthThreshold": 150.0,
  "miniBossHealthThreshold": 50.0,
  "cullParticles": true,
  "cullAnimations": true,
  "cullSignText": true,
  "clientBlacklist": [
    "minecraft:wolf",
    "minecraft:allay"
  ],
  "debugMode": false
}
```

### Конфигурация сервера (`config/camera-culling-server.json`)
```json
{
  "serverBlacklist": []
}
```

---

## 🔗 Связанные страницы

- [[Графический интерфейс настроек (YACL)|ru_ru-26.1.2-GUI-Configuration]]
- [[Отладка и диагностика|ru_ru-26.1.2-Debug-Logging-and-Diagnostics]]
- [[Вернуться к обзору MC 26.1.2|ru_ru-26.1.2-Home]]
