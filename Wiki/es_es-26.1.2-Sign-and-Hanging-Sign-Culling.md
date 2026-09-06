# 🪧 Oclusión de texto en carteles de dos caras (Minecraft 26.1.2)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

En Minecraft 26.1.2, los carteles admiten renderizado de texto en ambas caras. El motor de renderizado dibuja glifos, colores y contornos brillantes en ambos lados simultáneamente.

**Camera Culling** elimina los pases de dibujo innecesarios mediante el cálculo del producto escalar de vectores normales ($\vec{N} \cdot \vec{V}$) y descartes rápidos para texto vacío.

---

## 📋 Información de oclusión de texto en carteles

| Propiedad | Valor |
| :--- | :--- |
| **Canal de destino** | `BlockEntityRenderDispatcher.tryExtractRenderState` `@At("RETURN")` |
| **Gancho de API** | `signState.frontText = null;` / `signState.backText = null;` |
| **Tipos compatibles** | Carteles de pared, carteles de pie, carteles colgantes de pared y de techo |
| **Paso rápido para texto vacío** | Omite automáticamente las caras vacías sin caracteres visibles |
| **Tolerancia de producto escalar** | Margen de $\pm 0.05$ que previene parpadeos en ángulos oblicuos |

---

## 📐 Matemáticas del producto escalar de vectores normales

Para determinar si la cara frontal o posterior de un cartel apunta hacia la cámara, Camera Culling calcula el producto escalar entre el vector normal de la cara $\vec{N} = (N_x, N_z)$ y el vector desde el centro del cartel hacia la cámara $\vec{V} = (V_x, V_z)$:

$$V_x = X_{\text{cam}} - (X_{\text{sign}} + 0.5), \quad V_z = Z_{\text{cam}} - (Z_{\text{sign}} + 0.5)$$

### 1. Carteles de pared y carteles colgantes de pared
El vector normal se obtiene directamente de los desplazamientos de paso de la dirección del bloque:
$$N_x = \text{facing.getStepX()}, \quad N_z = \text{facing.getStepZ()}$$

### 2. Carteles de pie y carteles colgantes de techo
La rotación se representa como un número entero de $0 \dots 15$. El ángulo $\theta$ en radianes se calcula:
$$\theta = \left(\frac{\text{rotation} \times 360^\circ}{16}\right) \times \frac{\pi}{180}$$
$$N_x = -\sin\theta, \quad N_z = \cos\theta$$

### 3. Determinación de visibilidad
El producto escalar $D$ evalúa el ángulo de observación:
$$D = N_x V_x + N_z V_z$$

* **Evaluación de cara frontal**: Se ocluye cuando $D < -0.05$ (la cámara está detrás del plano del cartel).
* **Evaluación de cara posterior**: Se ocluye cuando $D > 0.05$ (la cámara está delante del plano del cartel).

---

## ⚡ Descarte rápido para caras vacías

Si un jugador solo ha escrito texto en un lado del cartel (o lo ha colocado vacío como barrera decorativa), Camera Culling inspecciona las 4 líneas de texto:
```java
public static boolean isTextEmpty(SignText text) {
    if (text == null) return true;
    for (int i = 0; i < 4; i++) {
        Component msg = text.getMessage(i, false);
        if (msg != null && !msg.getString().trim().isEmpty()) {
            return false;
        }
    }
    return true;
}
```
Las caras en blanco se anulan inmediatamente sin realizar cálculos trigonométricos ni productos escalares.

---

## 🔗 Páginas relacionadas

- [[Oclusión de entidades de bloque|es_es-26.1.2-Block-Entity-Culling]]
- [[Comandos y configuración|es_es-26.1.2-Commands-and-Configuration]]
- [[Arquitectura y Mixins|es_es-26.1.2-Architecture-and-Mixins]]
- [[Volver al resumen de MC 26.1.2|es_es-26.1.2-Home]]
