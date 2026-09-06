# 👑 Inmunidad de jefes y lista negra (Minecraft 26.3)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Para garantizar la equidad del juego y la conciencia táctica en combate, las amenazas críticas y los animales compañeros nunca deben desaparecer detrás de las paredes ni verse afectados por algoritmos de oclusión agresivos.

**Camera Culling** incorpora identificación dinámica de jefes y una lista negra de inmunidad de dos niveles.

---

## 📋 Información de inmunidad

| Propiedad | Valor |
| :--- | :--- |
| **Umbral de salud de jefe mayor** | `bossHealthThreshold` (Predeterminado: `150.0 HP` / 75 corazones) |
| **Umbral de salud de minijefe** | `miniBossHealthThreshold` (Predeterminado: `50.0 HP` / 25 corazones) |
| **Ruta de lista negra del cliente** | `config/camera-culling.json` (matriz `clientBlacklist`) |
| **Ruta de lista negra del servidor** | `config/camera-culling-server.json` (matriz `serverBlacklist`) |
| **Alcance de la inmunidad** | Exento de oclusión de bloques, sobregiro de multitudes y LOD de texturas |

---

## 🐲 Detección dinámica de jefes y minijefes

Camera Culling evalúa la inmunidad de jefes mediante dos mecanismos independientes:

### 1. Umbrales dinámicos de salud
Cualquier `LivingEntity` cuya salud máxima `getMaxHealth()` alcance o supere los umbrales configurados recibe inmunidad incondicional:
* `maxHealth >= 150.0` $\implies$ Jefe mayor (Dragón del End, Wither, Warden).
* `maxHealth >= 50.0` $\implies$ Minijefe (Guardián anciano, Devastador, Gólem de hierro, Piglin bruto, Breeze, campeones de mods).

### 2. Heurística de palabras clave de registro e identificadores
Las entidades con identificadores que contengan cualquiera de las siguientes subcadenas se identifican automáticamente como jefes:
```text
dragon, wither, warden, elder_guardian, ravager, evoker, iron_golem, 
piglin_brute, breeze, boss, miniboss, mini_boss, titan, leviathan, 
harbinger, monarch, behemoth, champion, elite, brute
```

---

## 🛡️ Sistema de lista negra de inmunidad de dos niveles

```text
Immunity Resolution
├── Local Player / Vehicle / Mount ──► 100% Rendered
├── Glowing Effect Active ──────────► 100% Rendered
├── Boss or Mini-Boss Detected ─────► 100% Rendered
├── Client Blacklist Match ─────────► 100% Rendered
└── Server Admin Blacklist Match ───► 100% Rendered
```

### 1. Lista negra personal del cliente
Los jugadores pueden autorizar entidades compañeras específicas de forma local mediante comandos en el juego:
```sql
/cameraculling blacklist add minecraft:wolf
/cameraculling blacklist add minecraft:allay
/cameraculling blacklist add minecraft:cat
```

### 2. Lista negra del administrador del servidor
Los operadores del servidor pueden especificar inmunidades globales en `config/camera-culling-server.json` o mediante `/cameraculling serverblacklist add <id>`. Todos los clientes conectados respetarán automáticamente la lista de inmunidad impuesta por el servidor.

---

## 🔗 Páginas relacionadas

- [[Oclusión de entidades|es_es-26.3-Entity-Occlusion-Culling]]
- [[LOD de texturas por distancia|es_es-26.3-Distance-Texture-LOD]]
- [[Comandos y configuración|es_es-26.3-Commands-and-Configuration]]
- [[Volver al resumen de MC 26.3|es_es-26.3-Home]]
