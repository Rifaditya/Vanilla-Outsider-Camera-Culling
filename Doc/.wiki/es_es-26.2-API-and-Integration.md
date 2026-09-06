# 🔌 API e integración de mods (Minecraft 26.2)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Camera Culling está diseñado para operar a la perfección junto con mods de renderizado del cliente (como Sodium, Iris, Canvas) y mods de contenido que añaden entidades o entidades de bloque personalizadas.

---

## 🤝 Compatibilidad con renderizadores de terceros

### 1. Sodium y Embeddium
* **Chunks de terreno estático**: Sodium optimiza la creación de mallas de chunks y la geometría de caras de bloques.
* **Entidades dinámicas**: Camera Culling optimiza entidades dinámicas, cofres, carteles y partículas.
* **Compatibilidad**: 100% compatible sin colisiones de Mixin ni conflictos de estado.

### 2. Iris y Shaders
* Al usar paquetes de shaders, las sombras de las entidades se sincronizan limpiamente con la oclusión.
* Las comprobaciones de Camera Culling evitan llamadas de dibujo innecesarias en los búferes de mapas de sombras.

---

## 🛠️ Puntos de integración programática en Java

Otros mods pueden consultar el estado o interactuar con Camera Culling mediante fachadas de utilidades estáticas:

### 1. Consulta del estado del motor de oclusión
```java
import net.vanillaoutsider.culling.CameraCullingClient;

// Comprobar si la oclusión maestra está activa
boolean isCullingActive = CameraCullingClient.isMasterCullingEnabled();
```

### 2. Acceso a las opciones de configuración
```java
import net.vanillaoutsider.culling.config.CameraCullingConfig;
import net.vanillaoutsider.culling.config.CullingProfile;

// Obtener el nivel de perfil de oclusión actual
CullingProfile currentProfile = CameraCullingConfig.getCullingLevel();

// Comprobar si la oclusión de partículas está habilitada
boolean cullingParticles = CameraCullingConfig.isCullParticles();
```

### 3. Utilidad de trazado de rayos sin asignación de memoria
```java
import net.vanillaoutsider.culling.util.CullingRaycastHelper;

// Comprobación rápida de línea de visión sin instanciar objetos Vec3
boolean visible = CullingRaycastHelper.hasLineOfSight(level, cameraPos, targetX, targetY, targetZ);
```

---

## 🔗 Páginas relacionadas

- [[Arquitectura y Mixins|es_es-26.2-Architecture-and-Mixins]]
- [[Configuración de desarrollador y compilación|es_es-26.2-Developer-Setup-and-Building]]
- [[Volver al resumen de MC 26.2|es_es-26.2-Home]]
