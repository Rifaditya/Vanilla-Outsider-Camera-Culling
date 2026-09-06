# ⏱️ Histéresis temporal y cero asignación de memoria (Minecraft 26.1.2)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

La oclusión de alta velocidad puede presentar dos defectos habituales de rendimiento:
1. **Parpadeo por roce de cámara (Z-Fighting / Boundary Pop-in)**: Pequeñas rotaciones de la cámara que crucen el borde de un bloque pueden hacer que las entidades parpadeen rápidamente entre estados visibles y ocluidos en fotogramas alternos.
2. **Tirones por recolección de basura**: La asignación continua de objetos `new Vec3()` durante el trazado de rayos provoca pausas del recolector JVM Young-Gen.

**Camera Culling** resuelve ambos problemas con un **búfer de gracia adaptativo escalado por distancia** y un **motor de ruta crítica sin asignación de memoria**.

---

## 📋 Información del motor de histéresis

| Parámetro | Valor |
| :--- | :--- |
| **Búfer de gracia cercano** | $d \le 32\text{m} \implies 4\text{ fotogramas}$ de persistencia |
| **Búfer de gracia medio** | $32\text{m} < d \le 64\text{m} \implies 8\text{ fotogramas}$ de persistencia |
| **Búfer de gracia lejano** | $d > 64\text{m} \implies 12\text{ fotogramas}$ de persistencia |
| **Latencia de desoclusión** | Instantánea ($0\text{ fotogramas}$ al detectar visión) |
| **Tasa de asignación en heap** | $0\text{ bytes/fotograma}$ (coordenadas `double` primitivas) |

---

## 📐 Fórmula del búfer de gracia escalado por distancia

La racha de fotogramas ocluidos necesaria antes de pasar una entidad al estado `[CULLED]` se calcula mediante:

$$\text{RequiredStreak}(d) = \begin{cases}
12\text{ fotogramas} & \text{si } d^2 > 64.0^2 \ (4096.0\text{m}^2) \\
8\text{ fotogramas} & \text{si } d^2 > 32.0^2 \ (1024.0\text{m}^2) \\
4\text{ fotogramas} & \text{de lo contrario}
\end{cases}$$

```text
Entity Sightline Lost
  ├── Frame 1 occluded ──► Grace State (Still Rendered)
  ├── Frame 2 occluded ──► Grace State (Still Rendered)
  ├── Frame 3 occluded ──► Grace State (Still Rendered)
  └── Frame 4 occluded ──► CULL (Streak Met)
```

* **Desoclusión instantánea**: Tan pronto como una sola muestra de rayo confirma línea de visión despejada, la racha de oclusión se elimina (`OCCLUDED_STREAK.remove(id)`), haciendo visible la entidad inmediatamente.
* **Decaimiento asimétrico**: Ocluir a una entidad requiere múltiples fotogramas consecutivos; volverla visible toma 1 fotograma. Esto elimina por completo el parpadeo al girar la vista.

---

## ⚡ Motor de ruta crítica sin asignación de memoria

En `CullingRaycastHelper.java`, se eliminan por completo las asignaciones de vectores intermedios en el heap:

```java
// Cero asignaciones en heap: las coordenadas se pasan como primitivos double
public static boolean hasLineOfSight(Level level, Vec3 from, double toX, double toY, double toZ) {
    Vec3 to = new Vec3(toX, toY, toZ);
    ClipContext ctx = new ClipContext(from, to, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, CollisionContext.empty());
    BlockHitResult hit = level.clip(ctx);
    if (hit.getType() == HitResult.Type.MISS) {
        return true;
    }
    // Evaluación de suelo y tolerancias sin construir nuevos objetos
    ...
}
```

---

## 🔗 Páginas relacionadas

- [[Oclusión de entidades|es_es-26.1.2-Entity-Occlusion-Culling]]
- [[Registro de depuración y diagnósticos|es_es-26.1.2-Debug-Logging-and-Diagnostics]]
- [[Arquitectura y Mixins|es_es-26.1.2-Architecture-and-Mixins]]
- [[Volver al resumen de MC 26.1.2|es_es-26.1.2-Home]]
