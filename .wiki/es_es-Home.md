# 📷 Wiki de Camera Culling

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Bienvenido al portal oficial de documentación de **Camera Culling**. Camera Culling es un mod de optimización de renderizado del cliente de alto rendimiento para Minecraft **26.1.2**, **26.2** y **26.3** desarrollado bajo la filosofía **Vanilla Outsider**.

> 📌 **Aviso legal del código fuente del repositorio**: La documentación en esta Wiki refleja el **estado actual del código en el repositorio**, que puede incluir confirmaciones recientes no publicadas o características en desarrollo previas a las versiones públicas en CurseForge y Modrinth.

---

## 🧭 Portal de conmutación multiversión

Camera Culling se desarrolla bajo la estricta ley de **1 JAR 1 Versión**. Selecciona tu versión de Minecraft a continuación para acceder a su árbol de documentación dedicado y aislado:

| Versión de Minecraft objetivo | Versión de lanzamiento del mod | Entorno de ejecución Java | Herramientas de compilación | Portal wiki dedicado |
| :--- | :--- | :--- | :--- | :--- |
| **Minecraft 26.1.2** | `1.10.2+26.1.2` | Java 25+ | Fabric Loom 1.15.5 | [[👉 Entrar a la Wiki de MC 26.1.2|es_es-26.1.2-Home]] |
| **Minecraft 26.2** | `1.10.1+26.2` | Java 25+ | Fabric Loom 1.15.5 | [[👉 Entrar a la Wiki de MC 26.2|es_es-26.2-Home]] |
| **Minecraft 26.3** | `1.10.1+26.3` | Java 25+ | Fabric Loom 1.15.5 | [[👉 Entrar a la Wiki de MC 26.3|es_es-26.3-Home]] |

---

## ⚡ Matriz de optimizaciones principales

| Sistema de optimización | Mecanismo principal | Beneficio de rendimiento |
| :--- | :--- | :--- |
| **Motor de raycasting sin asignación de memoria** | Comprobación de línea de visión con coordenadas primitivas | Elimina picos de pausa del recolector de basura (JVM Young-Gen GC) al girar la cámara |
| **Histéresis temporal antiparpadeo** | Búfer adaptativo de tolerancia de 4/8/12 fotogramas según distancia | Elimina el parpadeo en bordes de bloques y al caminar |
| **Oclusión de texto en carteles de dos caras** | Producto escalar del vector normal de cara ($\vec{N} \cdot \vec{V}$) | Reducción del 50% al 100% en llamadas de dibujo (draw calls) de texto de carteles |
| **Oclusión de partículas** | Burbuja de seguridad de 4m + trazado de rayos de corte visual | Omite el renderizado de partículas subterráneas y ocluidas |
| **Oclusión de animaciones** | Supresión de carga de atlas de texturas | Congela animaciones 3D de bloques y subidas de texturas fuera de pantalla |
| **Defensa de sobredibujado de multitudes de mobs** | Descarte rápido a 16m + límite de densidad de 8 entidades en 1.5m | Elimina caídas severas de FPS en granjas de mobs y acumulaciones densas |
| **LOD de texturas por distancia** | Sesgo de mipmap de OpenGL en 3 niveles ($0.0 \to 1.0 \to 2.5$) | Reduce drásticamente el ancho de banda de VRAM en manadas distantes |
| **Inmunidad de jefes y minijefes** | Umbrales de salud dinámicos y heurística de nombres | Evita que los jefes desaparezcan indebidamente afectando la jugabilidad |
| **Lista negra de inmunidad de dos niveles** | JSON de cliente local + sincronización de administrador del servidor | Lista blanca personalizada para mascotas y compañeros |
| **Oclusión de entidades de bloque** | Detección de cerramiento sólido en las 6 caras adyacentes | Omite la extracción de renderizado para cofres y bloques encerrados |

---

## 📚 Navegación global

- [[Matriz de compatibilidad y ciclo de vida de versiones|es_es-Version-Compatibility]]
- [[Árbol de documentación de Minecraft 26.1.2|es_es-26.1.2-Home]]
- [[Árbol de documentación de Minecraft 26.2|es_es-26.2-Home]]
- [[Árbol de documentación de Minecraft 26.3|es_es-26.3-Home]]

---

<p align="center">
  <em>Desarrollado por <strong>Dasik (Rifaditya)</strong> | Licenciado bajo <strong>GNU General Public License v3.0 (GPLv3)</strong></em>
</p>
