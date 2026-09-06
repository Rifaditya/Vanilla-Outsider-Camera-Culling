# 🎨 LOD de texturas de mobs por distancia (Minecraft 26.1.2)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Renderizar texturas de resolución completa (1024x1024 o superior) en mobs distantes que apenas ocupan 4x4 píxeles en la pantalla del jugador desperdicia un ancho de banda considerable en la VRAM de la GPU y agota las líneas de caché de muestreo de texturas.

**Camera Culling** incluye un **motor de sesgo de LOD de texturas de OpenGL** de 3 niveles, desacoplado, que ajusta dinámicamente el muestreo de mipmaps según la distancia de la entidad.

---

## 📋 Información de LOD de texturas

| Propiedad | Valor |
| :--- | :--- |
| **Gancho del canal** | `LivingEntityRenderer.submit(...)` `@At("HEAD")` & `@At("RETURN")` |
| **Parámetro OpenGL** | `GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, bias)` |
| **Umbral cercano** | $< 16.0$ bloques $\implies 0.0\text{f}$ Bias (Resolución completa) |
| **Umbral medio** | $16.0 - 32.0$ bloques $\implies 1.0\text{f}$ Bias (Media resolución) |
| **Umbral lejano** | $> 32.0$ bloques $\implies 2.5\text{f}$ Bias (Cuarto de resolución / Mipmap) |
| **Exenciones** | Entidades brillantes, jugador local, jefes y minijefes, lista negra |

---

## 🔬 Sesgo matemático de LOD por distancia

```text
Camera
  │
  ├── [ 0m to 16m ] ──► Bias 0.0f  (100% Native Full-Res Texture)
  │
  ├── [ 16m to 32m ] ──► Bias 1.0f  (50% Half-Res Mipmap Sampling)
  │
  └── [ > 32m ] ──────► Bias 2.5f  (25% Low-Res Mipmap Sampling)
```

El sesgo de LOD $B$ se calcula puramente a partir del cuadrado de la distancia:
$$B(d) = \begin{cases} 
0.0\text{f} & \text{si } d^2 < \text{startDist}^2 \ (16.0^2) \\
1.0\text{f} & \text{si } \text{startDist}^2 \le d^2 < \text{farDist}^2 \ (32.0^2) \\
2.5\text{f} & \text{si } d^2 \ge \text{farDist}^2
\end{cases}$$

### Aislamiento de estado de OpenGL
Al renderizar una entidad viva, `LivingEntityRendererMixin` se inyecta en `submit:HEAD` para aplicar el sesgo calculado y lo restablece de inmediato a `0.0f` en `submit:RETURN`. Esto garantiza que los modelos de bloques, objetos y elementos de la interfaz nunca se vean afectados.

---

## 🔗 Páginas relacionadas

- [[Inmunidad de jefes y lista negra|es_es-26.1.2-Boss-and-Blacklist-Immunity]]
- [[Configuración gráfica GUI (YACL)|es_es-26.1.2-GUI-Configuration]]
- [[Volver al resumen de MC 26.1.2|es_es-26.1.2-Home]]
