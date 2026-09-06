# 🧱 Oclusión de entidades (Minecraft 26.1.2)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

En Minecraft 26.1.2, el renderizado de entidades del cliente extrae estados de renderizado para todas las entidades dentro del frustum de la cámara, incluso cuando están ocultas tras cuevas, acantilados o construcciones. **Camera Culling** intercepta esta comprobación para evitar que los mobs ocluidos consuman procesamiento de geometría en la CPU y llamadas de dibujo en la GPU.

---

## 📋 Información de oclusión de entidades

| Propiedad | Valor |
| :--- | :--- |
| **Canal de destino** | `EntityRenderer.shouldRender` `@At("HEAD")` |
| **Algoritmo de oclusión** | Trazado de rayos multipunto + comprobación de multitudes |
| **Búfer de gracia (Grace)** | Histéresis temporal adaptativa de 4/8/12 fotogramas |
| **Puntos anatómicos** | 8 puntos de muestreo (cabeza, ojos, torso superior, centro, 4 flancos) |
| **Filtro direccional** | Suelo/escaleras: `hit.getDirection() == Direction.UP` + delta $Y \le 0.15\text{m}$ |

---

## 🔬 Muestreo anatómico multipunto de línea de visión

En lugar de un trazado de rayos simplista de un solo punto que causa parpadeos en las esquinas, Camera Culling realiza un muestreo anatómico multipunto según el [[perfil de oclusión|es_es-26.1.2-Commands-and-Configuration]] seleccionado:

```text
       [1] Head Top (maxY - 0.05)
            │
       [2] Eye Level (getEyeY)
            │
       [3] Upper Torso (minY + height * 0.70)
            │
       [4] Center of Mass ((minY + maxY) * 0.5)
            │
        [5-8] Elevated Perimeter Flanks (width/depth checks)
```

1. **Muestra 1: Parte superior de la cabeza (`maxY - 0.05`)** — Comprobación de alta prioridad. Detecta entidades altas que asoman sobre barricadas bajas o vallas.
2. **Muestra 2: Nivel anatómico de los ojos (`getEyeY()`)** — Línea directa de visión desde la cámara hasta los ojos del mob.
3. **Muestra 3: Torso superior / Pecho (`minY + height * 0.70`)** — Evalúa líneas de visión del cuerpo superior de forma segura por encima del suelo.
4. **Muestra 4: Centro de masa (`(minY + maxY) * 0.5`)** — Prueba de punto medio geométrico general.
5. **Muestras 5–8: Flancos perimetrales elevados** — Verifica entidades anchas (Devastadores, Wardens, Arañas) que asoman por las esquinas.

---

## 🛡️ Filtrado direccional de suelo y pendientes

Cuando un jugador mira hacia abajo a un mob situado en terreno irregular, un trazado de rayos estándar puede chocar contra la cara superior de un bloque junto a los pies del mob, tratando falsamente el suelo como un muro oclusor.

Camera Culling incorpora el **filtrado direccional de impacto en suelo**:
$$\text{Si } \text{hit.getDirection()} == \text{Direction.UP} \quad \text{y} \quad Y_{\text{hit}} \le Y_{\text{target}} + 0.15\text{m} \implies \text{Línea de visión válida (no bloqueada)}$$

Esto garantiza que los mobs que se desplazan por pendientes, escaleras y terrenos irregulares nunca sean ocluidos erróneamente.

---

## ⚡ Motor de raycasting sin asignación de memoria

En 26.1.2, `CullingRaycastHelper` pasa coordenadas primitivas directas:
```java
public static boolean hasLineOfSight(Level level, Vec3 from, double toX, double toY, double toZ)
```
Al pasar las coordenadas como tipos primitivos `double`, el bucle crítico de renderizado elimina por completo la creación de objetos temporales `Vec3` en el heap durante el trazado.

---

## 🔗 Páginas relacionadas

- [[Defensa de sobregiro en multitudes|es_es-26.1.2-Mob-Crowd-Overdraw-Defense]]
- [[Histéresis temporal y cero asignación|es_es-26.1.2-Temporal-Hysteresis-and-Zero-Allocation]]
- [[Inmunidad de jefes y lista negra|es_es-26.1.2-Boss-and-Blacklist-Immunity]]
- [[Volver al resumen de MC 26.1.2|es_es-26.1.2-Home]]
