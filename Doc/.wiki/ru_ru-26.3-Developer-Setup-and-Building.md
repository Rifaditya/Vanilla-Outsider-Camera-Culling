# 🛠️ Настройка разработчика и сборка (Minecraft 26.3)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

В этом руководстве описаны предварительные требования к окружению, инструментарий Gradle Loom и инструкции по компиляции **Camera Culling** для **Minecraft 26.3**.

---

## 📋 Спецификации окружения

| Компонент | Требуемая версия |
| :--- | :--- |
| **Java Development Kit (JDK)** | JDK 25+ (Eclipse Temurin или Oracle OpenJDK 25) |
| **Gradle** | 9.3+ |
| **Fabric Loom** | 1.15.5 |
| **Fabric Loader** | `>=0.18.4` |

---

## 🏗️ Команды сборки Gradle

Откройте терминал в директории подпроекта `Camera Culling v26.3/Camera Culling 26.3`:

```bash
# Запуск набора модульных тестов
./gradlew test

# Компиляция готового релизного JAR-файла
./gradlew build
```

### Конвейер автоархивации
Файл `build.gradle` подпроекта включает автоматическую задачу архивации:
* Выходной JAR: `build/libs/vanilla-outsider-camera-culling-1.10.0+26.3.jar`
* Расположение автоархива: `Archive Jar of all versions/MC 26.3/`
* Синхронизация с профилем Modrinth: Автоматически копируется в локальный профиль лаунчера (`Fabric 26.3ish/mods/`).

---

## 📜 Метаданные `fabric.mod.json`

```json
{
  "schemaVersion": 1,
  "id": "camera-culling",
  "version": "1.10.0+26.3",
  "name": "Camera Culling",
  "description": "High-performance client-side rendering optimization mod.",
  "authors": ["Dasik (Rifaditya)"],
  "license": "GPL-3.0",
  "environment": "client",
  "entrypoints": {
    "client": [
      "net.vanillaoutsider.culling.CameraCullingClient"
    ],
    "modmenu": [
      "net.vanillaoutsider.culling.integration.ModMenuIntegration"
    ]
  },
  "mixins": [
    "camera-culling.mixins.json"
  ],
  "depends": {
    "fabricloader": ">=0.18.4",
    "minecraft": ">=26.3-",
    "fabric-api": "*"
  }
}
```

---

## 🔗 Связанные страницы

- [[Архитектура и миксины|ru_ru-26.3-Architecture-and-Mixins]]
- [[API и интеграция модов|ru_ru-26.3-API-and-Integration]]
- [[Вернуться к обзору MC 26.3|ru_ru-26.3-Home]]
