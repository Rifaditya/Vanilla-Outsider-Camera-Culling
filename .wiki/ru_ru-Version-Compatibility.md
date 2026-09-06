# 🌐 Матрица совместимости версий и жизненного цикла

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

В этом документе описана активная матрица релизов для нескольких эпох, границы зависимостей, спецификации среды выполнения Java и различия в байткод-API для **Camera Culling**.

> 📌 **Отказ от ответственности об источнике репозитория**: Документация в этой вики отражает **текущее состояние исходного кода в репозитории**, которое может включать недавние невыпущенные коммиты или функции в разработке до их публичного релиза на CurseForge и Modrinth.

---

## 📋 Матрица жизненного цикла версий

| Эпоха Minecraft | Целевая версия MC | Текущая версия релиза | Требования к Java | Ограничение Fabric Loader | Ограничение Fabric API | Статус релиза |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| **Modern Sovereign (26.3)** | `26.3` | `1.10.1+26.3` | Java 25 (`release = 25`) | `>=0.18.4` | `0.156.1+26.3` | 🟢 Активный релиз |
| **Modern Sovereign (26.2)** | `26.2` | `1.10.1+26.2` | Java 25 (`release = 25`) | `>=0.18.4` | `0.145.4+26.2` | 🟢 Активный релиз |
| **Modern Sovereign (26.1.2)** | `26.1.2` | `1.10.2+26.1.2` | Java 25 (`release = 25`) | `>=0.18.4` | `0.145.4+26.1.2` | 🟢 Активный релиз |

---

## 🛠️ Различия в байткод-API между версиями

### 1. Конвейер извлечения RenderState блочных сущностей
* **Minecraft 26.1.2**:
  - `BlockEntityRenderDispatcher.tryExtractRenderState(BlockEntity, float, ModelFeatureRenderer$CrumblingOverlay)` — принимает **3 аргумента**.
* **Minecraft 26.2 и 26.3**:
  - `BlockEntityRenderDispatcher.tryExtractRenderState(BlockEntity, float, ModelFeatureRenderer$CrumblingOverlay, boolean isGloballyRendered)` — принимает **4 аргумента**.

### 2. API получения данных текста табличек
* **Minecraft 26.1.2 и 26.2**:
  - `SignBlockEntity.getFrontText()` и `SignBlockEntity.getBackText()` возвращают `SignText`.
  - `SignText.getMessage(int index, boolean filtered)` возвращает компонент строки `Component`.
* **Minecraft 26.3**:
  - `SignBlockEntity.getText(SignTextSlot.FRONT)` и `SignBlockEntity.getText(SignTextSlot.BACK)` возвращают `SignText`.
  - `SignText.getMessages(boolean filtered)` возвращает массив компонентов строк `Component[]`.

---

## 📦 Расположение архивов артефактов сборки

Все релизные сборки автоматически компилируются и сохраняются в централизованной структуре архивов родительского репозитория:

```text
Archive Jar of all versions/
├── MC 26.1.2/
│   ├── vanilla-outsider-camera-culling-1.10.1+26.1.2.jar
│   └── vanilla-outsider-camera-culling-1.10.1+26.1.2-sources.jar
├── MC 26.2/
│   ├── vanilla-outsider-camera-culling-1.10.0+26.2.jar
│   └── vanilla-outsider-camera-culling-1.10.0+26.2-sources.jar
└── MC 26.3/
    ├── vanilla-outsider-camera-culling-1.10.0+26.3.jar
    └── vanilla-outsider-camera-culling-1.10.0+26.3-sources.jar
```

---

## 🔗 Быстрые ссылки

- [[👉 Войти в вики Minecraft 26.3|ru_ru-26.3-Home]]
- [[👉 Войти в вики Minecraft 26.2|ru_ru-26.2-Home]]
- [[👉 Войти в вики Minecraft 26.1.2|ru_ru-26.1.2-Home]]
- [[Вернуться на портал версий|ru_ru-Home]]
