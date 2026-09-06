# 🖥️ Графический интерфейс настроек (YACL) (Minecraft 26.1.2)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Camera Culling предоставляет опциональный современный экран графической конфигурации на базе библиотек **YetAnotherConfigLib (YACL v3)** и **ModMenu**.

---

## 🗂️ Категории графического интерфейса

Конфигурационный экран логически разделен на три категории настроек:

### 1. Движок и диагностика (Engine & Diagnostics)
* **Master Culling Switch**: Главный переключатель активации мода.
* **Culling Profile Preset**: Выбор профиля (`LOW`, `MEDIUM`, `HIGH`, `SUPER`).
* **Debug Mode**: Переключатель вывода трассировки в чат и лог.

### 2. Окклюзия сущностей и толпы (Entity & Crowd Occlusion)
* **Cull Entities Behind Entities**: Переключатель защиты от скоплений мобов.
* **Max Entities Per Cluster**: Ползунок лимита сущностей (от `1` до `128`).
* **Boss Sightline Immunity**: Переключатель защиты боссов.
* **Boss Health Thresholds**: Пороги здоровья главных боссов и мини-боссов.
* **Client Immunity Blacklist**: Управление белым списком мобов.

### 3. Блоки, частицы и анимации (Blocks, Particles & Animations)
* **Block Entity Enclosure Culling**: Переключатель отсечения замурованных сундуков.
* **2-Sided Sign Text Culling**: Переключатель отсечения невидимого текста табличек.
* **Particle Occlusion Culling**: Отсечение частиц за сплошными блоками.
* **Atlas Animation Culling**: Заморозка анимаций при паузе и в меню.
* **Distance Texture LOD**: Переключатель дистанционного смещения LOD мипмапов.

---

## 🛡️ Отложенная загрузка классов и защита от сбоев


```java
public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> {
            if (FabricLoader.getInstance().isModLoaded("yet_another_config_lib_v3")) {
                return YaclScreenHelper.createScreen(parent);
            }
            return null;
        };
    }
}
```

Если YACL не установлен, все настройки можно регулировать через [[команды и конфигурацию|ru_ru-26.1.2-Commands-and-Configuration]] или путем редактирования `config/camera-culling.json`.

---

## 🔗 Связанные страницы

- [[Команды и конфигурация|ru_ru-26.1.2-Commands-and-Configuration]]
- [[Отладка и диагностика|ru_ru-26.1.2-Debug-Logging-and-Diagnostics]]
- [[Вернуться к обзору MC 26.1.2|ru_ru-26.1.2-Home]]
