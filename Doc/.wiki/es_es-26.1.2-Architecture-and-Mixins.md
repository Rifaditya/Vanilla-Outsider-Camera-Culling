# 🏛️ Arquitectura y Mixins (Minecraft 26.1.2)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Camera Culling está diseñado bajo una estricta arquitectura de **«1 archivo, 1 propósito»** con ganchos Mixin de sobrecarga cero diseñados específicamente para el motor de renderizado de **Minecraft 26.1.2**.

---

## 📋 Puntos principales de intercepción de Mixin

| Clase Mixin | Clase objetivo de Minecraft | Método objetivo y punto de inyección | Función |
| :--- | :--- | :--- | :--- |
| `EntityRendererMixin` | `net.minecraft.client.renderer.entity.EntityRenderer` | `shouldRender` `@At("HEAD")` | Oclusión de entidades por muestreo multipunto y límites de densidad de multitudes |
| `BlockEntityRenderDispatcherMixin` | `net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher` | `tryExtractRenderState(BlockEntity, float, CrumblingOverlay)` `@At("HEAD")` | Cerramiento de 6 caras y oclusión de entidades de bloque por raycasting (3 argumentos) |
| `BlockEntityRenderDispatcherMixin` | `net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher` | `tryExtractRenderState(BlockEntity, float, CrumblingOverlay)` `@At("RETURN")` | Oclusión de cara posterior y texto en blanco en carteles (3 argumentos) |
| `LivingEntityRendererMixin` | `net.minecraft.client.renderer.entity.LivingEntityRenderer` | `submit` `@At("HEAD")` y `@At("RETURN")` | Aplica y restablece el sesgo de LOD de mipmaps de OpenGL (`GL_TEXTURE_LOD_BIAS`) |
| `SingleQuadParticleMixin` | `net.minecraft.client.particle.SingleQuadParticle` | `extract` `@At("HEAD")` | Oclusión de partículas frente a geometría sólida |
| `TextureAtlasMixin` | `net.minecraft.client.renderer.texture.TextureAtlas` | `cycleAnimationFrames` `@At("HEAD")` | Suprime la subida de texturas animadas al pausar o en menús modales |

---

## 📁 Estructura de paquetes y directorios

```text
net.vanillaoutsider.culling/
├── CameraCullingClient.java          # Punto de entrada ClientModInitializer de Fabric
├── config/
│   ├── CameraCullingConfig.java      # Modelo de datos y deserialización de JSON
│   ├── CullingProfile.java           # Enumeración LOW, MEDIUM, HIGH, SUPER
│   └── YaclScreenHelper.java         # Fachada de construcción de pantalla YACL v3
├── integration/
│   └── ModMenuIntegration.java       # Implementación de ModMenuApi con carga diferida
├── mixin/
│   ├── BlockEntityRenderDispatcherMixin.java
│   ├── EntityRendererMixin.java
│   ├── LivingEntityRendererMixin.java
│   ├── SingleQuadParticleMixin.java
│   └── TextureAtlasMixin.java
└── util/
    ├── AnimationCullingHelper.java    # Lógica de pausa y omisión de animaciones de atlas
    ├── BlockEntityCullingHelper.java  # Comprobación de cerramiento sólido en 6 caras
    ├── CullingDiagnosticsHelper.java  # Registro de diagnóstico y formato de chat
    ├── CullingRaycastHelper.java      # Motor de raycasting sin asignación de memoria
    └── SignCullingHelper.java         # Matemáticas de vectores de carteles y texto vacío
```

---

## 🔗 Páginas relacionadas

- [[Configuración de desarrollador y compilación|es_es-26.1.2-Developer-Setup-and-Building]]
- [[API e integración de mods|es_es-26.1.2-API-and-Integration]]
- [[Volver al resumen de MC 26.1.2|es_es-26.1.2-Home]]
