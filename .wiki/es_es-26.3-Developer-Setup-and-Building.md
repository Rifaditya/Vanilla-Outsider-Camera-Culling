# 🛠️ Configuración de desarrollador y compilación (Minecraft 26.3)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Esta guía técnica cubre los requisitos previos del entorno, las herramientas de Gradle Loom y las instrucciones de compilación para **Camera Culling** en **Minecraft 26.3**.

---

## 📋 Especificaciones del entorno

| Componente | Versión requerida |
| :--- | :--- |
| **Kit de desarrollo Java (JDK)** | JDK 25+ (Eclipse Temurin u Oracle OpenJDK 25) |
| **Gradle** | 9.3+ |
| **Fabric Loom** | 1.15.5 |
| **Fabric Loader** | `>=0.18.4` |

---

## 🏗️ Comandos de compilación de Gradle

Abre una terminal en el directorio del subproyecto `Camera Culling v26.3/Camera Culling 26.3`:

```bash
# Ejecutar la suite de pruebas unitarias
./gradlew test

# Compilar el archivo JAR de lanzamiento listo para producción
./gradlew build
```

### Canal de archivo automatizado
El archivo `build.gradle` del subproyecto incluye una tarea de archivado automático:
* JAR generado: `build/libs/vanilla-outsider-camera-culling-1.10.0+26.3.jar`
* Ubicación de archivado automático: `Archive Jar of all versions/MC 26.3/`
* Sincronización con perfil de Modrinth: Se instala automáticamente en tu perfil local de launcher (`Fabric 26.3ish/mods/`).

---

## 📜 Metadatos de `fabric.mod.json`

```json
{
  "schemaVersion": 1,
  "id": "camera-culling",
  "version": "1.10.0+26.3",
  "name": "Camera Culling",
  "description": "High-performance client-side rendering optimization mod.",
  "authors": ["Dasik (Rifaditya)"],
  "license": "GPL-3.0",
  "environment": "client",
  "entrypoints": {
    "client": [
      "net.vanillaoutsider.culling.CameraCullingClient"
    ],
    "modmenu": [
      "net.vanillaoutsider.culling.integration.ModMenuIntegration"
    ]
  },
  "mixins": [
    "camera-culling.mixins.json"
  ],
  "depends": {
    "fabricloader": ">=0.18.4",
    "minecraft": ">=26.3-",
    "fabric-api": "*"
  }
}
```

---

## 🔗 Páginas relacionadas

- [[Arquitectura y Mixins|es_es-26.3-Architecture-and-Mixins]]
- [[API e integración de mods|es_es-26.3-API-and-Integration]]
- [[Volver al resumen de MC 26.3|es_es-26.3-Home]]
