# 🟣 Camera Culling (Minecraft 26.1.2)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Bienvenido al centro de documentación de **Minecraft 26.1.2** para **Camera Culling** (`v1.10.2+26.1.2`).

> 📌 **Aviso legal del código fuente del repositorio**: La documentación en esta Wiki refleja el **estado actual del código en el repositorio**, que puede incluir confirmaciones recientes no publicadas o características en desarrollo previas a las versiones públicas en CurseForge y Modrinth.

---

## 📋 Información rápida de Minecraft 26.1.2

| Propiedad | Valor |
| :--- | :--- |
| **Versión objetivo de Minecraft** | `26.1.2` |
| **Versión de lanzamiento** | `1.10.2+26.1.2` |
| **Requisito de Java** | Java 25 (`release = 25`) |
| **Fabric Loader** | `>=0.18.4` |
| **Fabric API** | `0.145.4+26.1.2` |
| **Licencia** | GNU General Public License v3.0 (GPLv3) |
| **Ruta del subproyecto** | `Camera Culling v26.1/Camera Culling 26.1` |

---

## ⚡ Matriz de funciones principales

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

## 📚 Índice de documentación de 26.1.2

1. [[Oclusión de entidades|es_es-26.1.2-Entity-Occlusion-Culling]] — Muestreo anatómico multipunto y filtrado de suelo.
2. [[Oclusión de entidades de bloque|es_es-26.1.2-Block-Entity-Culling]] — Comprobaciones de cerramiento de cofres y bloques.
3. [[Oclusión de texto en carteles|es_es-26.1.2-Sign-and-Hanging-Sign-Culling]] — Matemáticas de producto escalar normal y carteles de 2 caras.
4. [[Oclusión de partículas y animaciones|es_es-26.1.2-Particle-and-Animation-Culling]] — Oclusión de partículas subterráneas y congelación de atlas de texturas.
5. [[Defensa de sobregiro en multitudes|es_es-26.1.2-Mob-Crowd-Overdraw-Defense]] — Control de distancia a 16m y límite de densidad de 8 entidades en 1.5m.
6. [[LOD de texturas por distancia|es_es-26.1.2-Distance-Texture-LOD]] — Sesgo de mipmaps de OpenGL para manadas distantes.
7. [[Inmunidad de jefes y lista negra|es_es-26.1.2-Boss-and-Blacklist-Immunity]] — Protección de jefes y lista negra de dos niveles.
8. [[Histéresis temporal y cero asignación|es_es-26.1.2-Temporal-Hysteresis-and-Zero-Allocation]] — Búfer de gracia antiparpadeo y motor sin asignación de memoria.
9. [[Comandos y configuración|es_es-26.1.2-Commands-and-Configuration]] — Sintaxis completa de comandos de Brigadier.
10. [[Configuración gráfica GUI (YACL)|es_es-26.1.2-GUI-Configuration]] — Guía de interfaz gráfica de configuración.
11. [[Registro de depuración y diagnósticos|es_es-26.1.2-Debug-Logging-and-Diagnostics]] — Registro en chat y trazas de transición de estado en tiempo real.
12. [[Arquitectura y Mixins|es_es-26.1.2-Architecture-and-Mixins]] — Jerarquía de paquetes y tabla de puntos de inyección Mixin.
13. [[Configuración de desarrollador y compilación|es_es-26.1.2-Developer-Setup-and-Building]] — Configuración de JDK 25 e instrucciones de compilación con Loom Gradle.
14. [[API e integración de mods|es_es-26.1.2-API-and-Integration]] — Puntos de integración programática.

---

[[Volver al portal de versiones|es_es-Home]] &bull; [[Matriz de compatibilidad y ciclo de vida|es_es-Version-Compatibility]]
