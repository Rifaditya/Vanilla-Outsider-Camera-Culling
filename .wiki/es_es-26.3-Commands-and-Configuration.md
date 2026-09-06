# 🎮 Comandos y configuración (Minecraft 26.3)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Camera Culling proporciona una suite completa de comandos Brigadier en el juego (`/cameraculling`) y persistencia limpia de configuración en JSON (`config/camera-culling.json`).

---

## 📋 Tabla de referencia de comandos

```sql
/cameraculling status
/cameraculling toggle
/cameraculling set <low|medium|high|super>
/cameraculling particles [true|false]
/cameraculling animations [true|false]
/cameraculling crowdculling <true|false>
/cameraculling cluster <1-128>
/cameraculling texturlod <true|false>
/cameraculling texturlod range <start_dist> <far_dist>
/cameraculling bossimmunity <true|false>
/cameraculling bosshealth <boss_hp>
/cameraculling bosshealth <boss_hp> <miniboss_hp>
/cameraculling minibosshealth <miniboss_hp>
/cameraculling blacklist <add|remove|list|clear> <entity_id>
/cameraculling serverblacklist <add|remove|list|clear> <entity_id>
/cameraculling debug [true|false]
/cameraculling reload
```

---

## ⚙️ Desglose detallado de comandos

| Sintaxis del comando | Parámetros | Función |
| :--- | :--- | :--- |
| `/cameraculling status` | Ninguno | Muestra estadísticas en vivo, contadores de renderizados vs ocluidos, perfil activo y conteo de lista negra. |
| `/cameraculling toggle` | Ninguno | Invierte el estado maestro de activación de la oclusión. |
| `/cameraculling set <profile>` | `low`, `medium`, `high`, `super` | Cambia el perfil de intensidad de oclusión activo. |
| `/cameraculling particles [bool]` | `true`, `false` (opcional) | Activa/desactiva la oclusión de partículas tras bloques sólidos. |
| `/cameraculling animations [bool]` | `true`, `false` (opcional) | Activa/desactiva la congelación de animaciones de bloques y atlas. |
| `/cameraculling crowdculling <bool>` | `true`, `false` | Activa/desactiva la oclusión de mobs tapados por otras entidades. |
| `/cameraculling cluster <int>` | De `1` a `128` | Configura el máximo de mobs permitidos por grupo de 1.5 bloques (por defecto: `8`). |
| `/cameraculling texturlod <bool>` | `true`, `false` | Activa o desactiva la escala LOD de mipmaps de texturas por distancia. |
| `/cameraculling texturlod range <start> <far>` | `1.0..256.0`, `2.0..512.0` | Define umbrales de distancia cercana y lejana para LOD (ej. `16.0 32.0`). |
| `/cameraculling bossimmunity <bool>` | `true`, `false` | Activa o desactiva la protección de línea de visión de jefes y minijefes. |
| `/cameraculling bosshealth <hp>` | De `1.0` a `10000.0` | Establece el umbral de vida para jefes mayores (ej. `150.0`). |
| `/cameraculling minibosshealth <hp>` | De `1.0` a `10000.0` | Establece el umbral de vida para minijefes (ej. `50.0`). |
| `/cameraculling blacklist add <id>` | ID de entidad | Añade una entidad (ej. `minecraft:wolf`) a la inmunidad personal del cliente. |
| `/cameraculling blacklist remove <id>` | ID de entidad | Elimina una entidad de la inmunidad personal del cliente. |
| `/cameraculling blacklist list` | Ninguno | Lista todas las entidades en la lista negra personal de inmunidad. |
| `/cameraculling blacklist clear` | Ninguno | Limpia todas las entradas de la lista negra personal. |
| `/cameraculling serverblacklist ...` | Subcomando + ID | Configura la lista negra de inmunidad del servidor (Requiere OP). |
| `/cameraculling debug [bool]` | `true`, `false` (opcional) | Activa/desactiva el registro de diagnóstico de transiciones en chat y logs en tiempo real. |
| `/cameraculling reload` | Ninguno | Recarga los archivos de configuración desde el disco. |

---

## 📄 Formato de configuración JSON

Los archivos de configuración se encuentran en el directorio `.minecraft/config/`:

### Configuración del cliente (`config/camera-culling.json`)
```json
{
  "enabled": true,
  "level": "SUPER",
  "cullEntitiesBehindEntities": false,
  "maxEntitiesPerCluster": 8,
  "distanceTextureLod": true,
  "distanceTextureLodStart": 16.0,
  "distanceTextureLodFar": 32.0,
  "bossImmunity": true,
  "bossHealthThreshold": 150.0,
  "miniBossHealthThreshold": 50.0,
  "cullParticles": true,
  "cullAnimations": true,
  "cullSignText": true,
  "clientBlacklist": [
    "minecraft:wolf",
    "minecraft:allay"
  ],
  "debugMode": false
}
```

### Configuración del servidor (`config/camera-culling-server.json`)
```json
{
  "serverBlacklist": []
}
```

---

## 🔗 Páginas relacionadas

- [[Configuración gráfica GUI (YACL)|es_es-26.3-GUI-Configuration]]
- [[Registro de depuración y diagnósticos|es_es-26.3-Debug-Logging-and-Diagnostics]]
- [[Volver al resumen de MC 26.3|es_es-26.3-Home]]
