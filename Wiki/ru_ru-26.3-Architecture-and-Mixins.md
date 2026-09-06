# 🏛️ Архитектура и миксины (Minecraft 26.3)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Camera Culling спроектирован в строгом соответствии с архитектурным принципом **«1 файл, 1 назначение»** с легковесными хуками Mixin, разработанными специально для движка рендеринга **Minecraft 26.3**.

---

## 📋 Основные точки внедрения Mixin

| Класс Mixin | Целевой класс Minecraft | Целевой метод и точка внедрения | Назначение |
| :--- | :--- | :--- | :--- |
| `EntityRendererMixin` | `net.minecraft.client.renderer.entity.EntityRenderer` | `shouldRender` `@At("HEAD")` | Многоточечное окклюзионное отсечение и проверка плотности толпы |
| `BlockEntityRenderDispatcherMixin` | `net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher` | `tryExtractRenderState` `@At("HEAD")` | 6-стороннее окружение и отсечение блочных сущностей трассировкой |
| `BlockEntityRenderDispatcherMixin` | `net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher` | `tryExtractRenderState` `@At("RETURN")` | Отсечение задней грани и пустого текста табличек |
| `LivingEntityRendererMixin` | `net.minecraft.client.renderer.entity.LivingEntityRenderer` | `submit` `@At("HEAD")` и `@At("RETURN")` | Применение и сброс смещения LOD мипмапов текстур OpenGL (`GL_TEXTURE_LOD_BIAS`) |
| `SingleQuadParticleMixin` | `net.minecraft.client.particle.SingleQuadParticle` | `extract` `@At("HEAD")` | Окклюзионное отсечение полигонов частиц за сплошной геометрией |
| `TextureAtlasMixin` | `net.minecraft.client.renderer.texture.TextureAtlas` | `cycleAnimationFrames` `@At("HEAD")` | Подавление загрузки анимированных текстур при паузе и в меню |

---

## 📁 Структура пакетов и классов

```text
net.vanillaoutsider.culling/
├── CameraCullingClient.java          # Точка входа Fabric ClientModInitializer
├── config/
│   ├── CameraCullingConfig.java      # Модель данных конфигурации и десериализация JSON
│   ├── CullingProfile.java           # Перечисление LOW, MEDIUM, HIGH, SUPER
│   └── YaclScreenHelper.java         # Фасад построения графического интерфейса YACL v3
├── integration/
│   └── ModMenuIntegration.java       # Реализация ModMenuApi с отложенной загрузкой
├── mixin/
│   ├── BlockEntityRenderDispatcherMixin.java
│   ├── EntityRendererMixin.java
│   ├── LivingEntityRendererMixin.java
│   ├── SingleQuadParticleMixin.java
│   └── TextureAtlasMixin.java
└── util/
    ├── AnimationCullingHelper.java    # Логика паузы и пропуска анимаций атласов
    ├── BlockEntityCullingHelper.java  # Проверка 6-стороннего твердого окружения
    ├── CullingDiagnosticsHelper.java  # Диагностика и форматирование чата
    ├── CullingRaycastHelper.java      # Трассировка лучей с нулевым выделением памяти
    └── SignCullingHelper.java         # Математика нормалей табличек и проверка пустого текста
```

---

## 🔗 Связанные страницы

- [[Настройка разработчика и сборка|ru_ru-26.3-Developer-Setup-and-Building]]
- [[API и интеграция модов|ru_ru-26.3-API-and-Integration]]
- [[Вернуться к обзору MC 26.3|ru_ru-26.3-Home]]
