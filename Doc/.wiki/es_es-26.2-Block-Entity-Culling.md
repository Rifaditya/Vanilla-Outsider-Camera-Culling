# 📦 Oclusión de entidades de bloque (Minecraft 26.2)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Las entidades de bloque (cofres, cofres de ender, carteles, estandartes, calaveras, macetas decoradas, campanas y faros) generan llamadas de dibujo individuales en cada fotograma. En salas de almacenamiento grandes o clasificadores automáticos, esto produce una saturación notable de llamadas de dibujo en la GPU.

---

## 📋 Información de oclusión de entidades de bloque

| Propiedad | Valor |
| :--- | :--- |
| **Canal de destino** | `BlockEntityRenderDispatcher.tryExtractRenderState(BlockEntity, float, CrumblingOverlay, boolean)` |
| **Detección de cerramiento** | Comprueba las 6 caras adyacentes: `up`, `down`, `north`, `south`, `east`, `west` |
| **Modo conservador** | Solo comprueba el cerramiento completo en el perfil `LOW` |
| **Modo agresivo** | Comprobación completa de línea de visión por raycasting en `MEDIUM`, `HIGH`, `SUPER` |

---

## 🧱 Comprobación de cerramiento y línea de visión

```text
Camera
  │
  ├── [1] 6-Sided Solid Enclosure Check ──► CULL (Skip Draw Call)
  │
  └── [2] Line-of-Sight Raycast ──────────► RENDER or CULL
```

### 1. Descarte rápido por cerramiento sólido en 6 caras
Antes de ejecutar cualquier cálculo de trazado de rayos, Camera Culling consulta los estados de los bloques vecinos:
```java
BlockState up = level.getBlockState(pos.above());
BlockState down = level.getBlockState(pos.below());
BlockState north = level.getBlockState(pos.north());
BlockState south = level.getBlockState(pos.south());
BlockState east = level.getBlockState(pos.east());
BlockState west = level.getBlockState(pos.west());

if (isSolid(up) && isSolid(down) && isSolid(north) && isSolid(south) && isSolid(east) && isSolid(west)) {
    return true; // 100% ocluido — omitir renderizado
}
```
Los cofres incrustados en paredes o rodeados de cimientos sólidos se descartan con un consumo de CPU casi nulo ($< 0.0001\mu\text{s}$).

### 2. Comprobación de línea de visión por trazado de rayos
En los perfiles `MEDIUM`, `HIGH` y `SUPER` (`cullAllBlockEntities = true`), Camera Culling proyecta un rayo desde la posición de la cámara del jugador hacia el centro de la entidad de bloque $(X + 0.5, Y + 0.5, Z + 0.5)$:
* Si el rayo impacta contra un bloque sólido opaco antes de llegar a la entidad, se omite el estado de renderizado.
* Si existe una línea de visión despejada, la entidad de bloque se renderiza con fidelidad visual completa.

---

## 🔗 Páginas relacionadas

- [[Oclusión de texto en carteles|es_es-26.2-Sign-and-Hanging-Sign-Culling]]
- [[Oclusión de entidades|es_es-26.2-Entity-Occlusion-Culling]]
- [[Arquitectura y Mixins|es_es-26.2-Architecture-and-Mixins]]
- [[Volver al resumen de MC 26.2|es_es-26.2-Home]]
