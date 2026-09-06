# 🌐 Matriz de compatibilidad y ciclo de vida de versiones

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Este documento describe la matriz activa de lanzamientos multiera, límites de dependencias, especificaciones del entorno Java y variaciones de API de bytecode para **Camera Culling**.

> 📌 **Aviso legal del código fuente del repositorio**: La documentación en esta Wiki refleja el **estado actual del código en el repositorio**, que puede incluir confirmaciones recientes no publicadas o características en desarrollo previas a las versiones públicas en CurseForge y Modrinth.

---

## 📋 Matriz de ciclo de vida multiversión

| Era ancla de Minecraft | Versión objetivo de MC | Versión de lanzamiento actual | Requisito de Java | Límite de Fabric Loader | Límite de Fabric API | Estado de lanzamiento |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| **Modern Sovereign (26.3)** | `26.3` | `1.10.1+26.3` | Java 25 (`release = 25`) | `>=0.18.4` | `0.156.1+26.3` | 🟢 Lanzamiento activo |
| **Modern Sovereign (26.2)** | `26.2` | `1.10.1+26.2` | Java 25 (`release = 25`) | `>=0.18.4` | `0.145.4+26.2` | 🟢 Lanzamiento activo |
| **Modern Sovereign (26.1.2)** | `26.1.2` | `1.10.2+26.1.2` | Java 25 (`release = 25`) | `>=0.18.4` | `0.145.4+26.1.2` | 🟢 Lanzamiento activo |

---

## 🛠️ Diferencias de API de bytecode entre versiones

### 1. Canal de extracción de RenderState de entidades de bloque
* **Minecraft 26.1.2**:
  - `BlockEntityRenderDispatcher.tryExtractRenderState(BlockEntity, float, ModelFeatureRenderer$CrumblingOverlay)` — toma **3 argumentos**.
* **Minecraft 26.2 y 26.3**:
  - `BlockEntityRenderDispatcher.tryExtractRenderState(BlockEntity, float, ModelFeatureRenderer$CrumblingOverlay, boolean isGloballyRendered)` — toma **4 argumentos**.

### 2. API de recuperación de datos de texto en carteles
* **Minecraft 26.1.2 y 26.2**:
  - `SignBlockEntity.getFrontText()` y `SignBlockEntity.getBackText()` devuelven `SignText`.
  - `SignText.getMessage(int index, boolean filtered)` devuelve el componente de línea `Component`.
* **Minecraft 26.3**:
  - `SignBlockEntity.getText(SignTextSlot.FRONT)` y `SignBlockEntity.getText(SignTextSlot.BACK)` devuelven `SignText`.
  - `SignText.getMessages(boolean filtered)` devuelve una matriz de componentes `Component[]`.

---

## 📦 Ubicaciones de archivo de compilaciones

Todas las compilaciones de lanzamiento se generan automáticamente y se almacenan en la estructura centralizada del repositorio:

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

## 🔗 Enlaces rápidos

- [[👉 Entrar a la Wiki de Minecraft 26.3|es_es-26.3-Home]]
- [[👉 Entrar a la Wiki de Minecraft 26.2|es_es-26.2-Home]]
- [[👉 Entrar a la Wiki de Minecraft 26.1.2|es_es-26.1.2-Home]]
- [[Volver al portal de versiones|es_es-Home]]
