# 👑 Иммунитет боссов и черный список (Minecraft 26.3)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Для обеспечения честности игрового процесса и контроля боевой обстановки критически важные угрозы и питомцы-компаньоны никогда не должны исчезать за стенами или отсекаться агрессивными алгоритмами.

**Camera Culling** включает динамическую идентификацию боссов и двухуровневый черный список иммунитета.

---

## 📋 Инфобокс иммунитета

| Свойство | Значение |
| :--- | :--- |
| **Порог здоровья главного босса** | `bossHealthThreshold` (По умолчанию: `150.0 HP` / 75 сердец) |
| **Порог здоровья мини-босса** | `miniBossHealthThreshold` (По умолчанию: `50.0 HP` / 25 сердец) |
| **Путь к клиентскому списку** | `config/camera-culling.json` (массив `clientBlacklist`) |
| **Путь к серверному списку** | `config/camera-culling-server.json` (массив `serverBlacklist`) |
| **Область действия** | Освобождение от окклюзии блоками, отсечения толпы и LOD текстур |

---

## 🐲 Динамическое обнаружение боссов и мини-боссов

Camera Culling определяет иммунитет боссов с помощью двух независимых механизмов:

### 1. Динамические пороги здоровья
Любая `LivingEntity`, чье максимальное здоровье `getMaxHealth()` достигает настроенных порогов, получает безусловный иммунитет:
* `maxHealth >= 150.0` $\implies$ Главный босс (Дракон Края, Иссушитель, Хранитель).
* `maxHealth >= 50.0` $\implies$ Мини-босс (Древний страж, Разоритель, Железный голем, Брутальный пиглин, Бриз, модовые чемпионы).

### 2. Эвристика ключевых слов реестра и идентификаторов
Сущности с именами, содержащими любую из следующих подстрок, автоматически признаются боссами:
```text
dragon, wither, warden, elder_guardian, ravager, evoker, iron_golem, 
piglin_brute, breeze, boss, miniboss, mini_boss, titan, leviathan, 
harbinger, monarch, behemoth, champion, elite, brute
```

---

## 🛡️ Двухуровневая система черного списка иммунитета

```text
Immunity Resolution
├── Local Player / Vehicle / Mount ──► 100% Rendered
├── Glowing Effect Active ──────────► 100% Rendered
├── Boss or Mini-Boss Detected ─────► 100% Rendered
├── Client Blacklist Match ─────────► 100% Rendered
└── Server Admin Blacklist Match ───► 100% Rendered
```

### 1. Персональный клиентский черный список
Игроки могут локально добавить сущностей-компаньонов в белый список с помощью внутриигровых команд:
```sql
/cameraculling blacklist add minecraft:wolf
/cameraculling blacklist add minecraft:allay
/cameraculling blacklist add minecraft:cat
```

### 2. Серверный административный черный список
Операторы сервера могут задавать глобальный иммунитет в файле `config/camera-culling-server.json` или через `/cameraculling serverblacklist add <id>`. Все подключенные клиенты автоматически применяют серверный список иммунитета.

---

## 🔗 Связанные страницы

- [[Окклюзионное отсечение сущностей|ru_ru-26.3-Entity-Occlusion-Culling]]
- [[Дистанционные LOD-текстуры|ru_ru-26.3-Distance-Texture-LOD]]
- [[Команды и конфигурация|ru_ru-26.3-Commands-and-Configuration]]
- [[Вернуться к обзору MC 26.3|ru_ru-26.3-Home]]
