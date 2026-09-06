# ✨ Oclusión de partículas y animaciones (Minecraft 26.2)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

En Minecraft vanilla, el goteo de lava subterránea, el polvo de cuevas, las partículas de portales y las antorchas generan cientos de polígonos de partículas detrás de muros de piedra sólida que se envían a la GPU. Al mismo tiempo, los atlas de texturas de bloques animados suben continuamente datos de fotogramas a la GPU incluso cuando el jugador tiene el juego en pausa o en menús modales.

**Camera Culling** resuelve ambos problemas optimizando el rendimiento del cliente.

---

## 📋 Información de oclusión de partículas

| Propiedad | Valor |
| :--- | :--- |
| **Burbuja de seguridad cercana** | Renderizado incondicional si distancia $\le 4.0\text{m}$ |
| **Distancia máxima de corte** | Las partículas a $> 64.0\text{m}$ se descartan |
| **Canal de trazado de rayos** | `SingleQuadParticle.extract(...)` `@At("HEAD")` |
| **Gancho de animación de atlas** | `TextureAtlasMixin.cycleAnimationFrames` |

---

## 🫧 Canal de verificación de línea de visión de partículas

```text
Particle Generated
  │
  ├── Distance <= 4.0m? ──────► RENDER (Safety Bubble)
  │
  ├── Distance > 64.0m? ──────► CULL (Too Far)
  │
  ├── Block is SolidRender? ──► CULL (Inside Solid Geometry)
  │
  └── Visual Raycast Blocked?
        ├── Yes ──────────────► CULL (Behind Walls)
        └── No ───────────────► RENDER (Visible)
```

1. **Burbuja de seguridad cercana (4.0m)**: Las partículas emitidas a menos de 4 metros de la cámara se renderizan incondicionalmente en $< 0.0001\mu\text{s}$.
2. **Distancia máxima de corte (64.0m)**: Las partículas generadas a más de 64 metros se ocluyen para proteger la tasa de relleno de la GPU.
3. **Cerramiento en bloque sólido**: Si las coordenadas `BlockPos.containing(x, y, z)` corresponden a `isSolidRender() == true`, la partícula se descarta de inmediato.
4. **Trazado de rayos de corte visual**: Proyecta un rayo `ClipContext.Block.VISUAL` desde la cámara hasta la partícula. Si un bloque sólido opaco intercepta la visión con $> 0.35\text{m}$ de margen, se omite el renderizado.

---

## 🎬 Congelación de animaciones de bloques y atlas de texturas

Las animaciones de atlas de texturas consumen ancho de banda de GPU para ciclar los fotogramas.

Camera Culling comprueba `AnimationCullingHelper.shouldPauseAtlasAnimation()` en `TextureAtlasMixin`:
* Si la partida individual está en pausa (`mc.isPaused() == true`), las subidas al atlas se congelan.
* Si hay menús modales abiertos a pantalla completa (inventario, ajustes), las animaciones se detienen, ahorrando ciclos de GPU.

---

## 🔗 Páginas relacionadas

- [[Oclusión de entidades|es_es-26.2-Entity-Occlusion-Culling]]
- [[Comandos y configuración|es_es-26.2-Commands-and-Configuration]]
- [[Volver al resumen de MC 26.2|es_es-26.2-Home]]
