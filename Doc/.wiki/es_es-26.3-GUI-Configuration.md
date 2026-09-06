# 🖥️ Configuración gráfica GUI (YACL) (Minecraft 26.3)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Camera Culling ofrece una pantalla de configuración gráfica moderna y opcional mediante **YetAnotherConfigLib (YACL v3)** y **ModMenu**.

---

## 🗂️ Desglose de categorías de la interfaz

La pantalla de configuración se divide limpiamente en tres categorías:

### 1. Motor y diagnósticos (Engine & Diagnostics)
* **Master Culling Switch**: Interruptor maestro para activar/desactivar la oclusión.
* **Culling Profile Preset**: Selector de perfil (`LOW`, `MEDIUM`, `HIGH`, `SUPER`).
* **Debug Mode**: Activa la traza de diagnósticos en chat y registros.

### 2. Oclusión de entidades y multitudes (Entity & Crowd Occlusion)
* **Cull Entities Behind Entities**: Oclusión de entidades tapadas por otros mobs.
* **Max Entities Per Cluster**: Límite de entidades visibles por grupo (de `1` a `128`).
* **Boss Sightline Immunity**: Protección de línea de visión para jefes.
* **Boss Health Thresholds**: Umbrales de vida de jefes mayores y minijefes.
* **Client Immunity Blacklist**: Gestión de la lista blanca de mobs.

### 3. Bloques, partículas y animaciones (Blocks, Particles & Animations)
* **Block Entity Enclosure Culling**: Oclusión de cofres encerrados en bloques sólidos.
* **2-Sided Sign Text Culling**: Oclusión del texto oculto en carteles.
* **Particle Occlusion Culling**: Oclusión de partículas tras muros sólidos.
* **Atlas Animation Culling**: Congelación de animaciones al pausar o en menús.
* **Distance Texture LOD**: Activación del escalado LOD de texturas por distancia.

---

## 🛡️ Carga diferida de clases y seguridad contra fallos

Para garantizar que Camera Culling nunca cause fallos en servidores dedicados o en instalaciones donde YACL no esté presente, `ModMenuIntegration` implementa carga diferida de clases:
```java
public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> {
            if (FabricLoader.getInstance().isModLoaded("yet_another_config_lib_v3")) {
                return YaclScreenHelper.createScreen(parent);
            }
            return null;
        };
    }
}
```

Si YACL no está instalado, el juego se inicia con normalidad y los jugadores pueden configurar todos los ajustes mediante [[comandos en el juego|es_es-26.3-Commands-and-Configuration]] o editando `config/camera-culling.json`.

---

## 🔗 Páginas relacionadas

- [[Comandos y configuración|es_es-26.3-Commands-and-Configuration]]
- [[Registro de depuración y diagnósticos|es_es-26.3-Debug-Logging-and-Diagnostics]]
- [[Volver al resumen de MC 26.3|es_es-26.3-Home]]
