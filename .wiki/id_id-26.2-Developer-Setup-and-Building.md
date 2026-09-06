# 🛠️ Pengaturan Pengembang & Kompilasi (Minecraft 26.2)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Panduan teknis ini mencakup prasyarat lingkungan, perkakas Gradle Loom, dan petunjuk kompilasi untuk membangun **Camera Culling** pada **Minecraft 26.2**.

---

## 📋 Prasyarat Lingkungan

* **Java Development Kit (JDK)**: **JDK 25+** (misalnya Eclipse Adoptium Temurin 25).
* **Gradle Wrapper**: Versi 9.3+ dengan Loom 1.15.5 (`net.fabricmc.fabric-loom`).
* **Runtime**: Runtime Mojang non-obfuscated (blok mappings di `build.gradle` dilarang ketat di era 26.x).

---

## 🏗️ Perintah Build Gradle

Buka terminal di direktori subproyek `Camera Culling v26.2/Camera Culling 26.2`:

```bash
# Jalankan rangkaian unit test
./gradlew test --no-daemon

# Kompilasi dan bungkus JAR rilis
./gradlew build --no-daemon
```

### Alur Kerja Pengarsipan Otomatis
File `build.gradle` subproyek mencakup tugas arsip otomatis:
* Output JAR: `build/libs/vanilla-outsider-camera-culling-1.10.0+26.2.jar`
* Lokasi Pengarsipan Otomatis: `Archive Jar of all versions/MC 26.2/`
* Sinkronisasi Profil Modrinth: Terpasang otomatis ke profil launcher lokal Anda (`Fabric 26.2/mods/`).

---

## 📄 Metadata `fabric.mod.json`

```json
{
  "schemaVersion": 1,
  "id": "camera-culling",
  "version": "${version}",
  "name": "Camera Culling",
  "description": "High-performance camera occlusion culling, 2-sided sign text culling & distance texture LOD.",
  "authors": [
    "Dasik (Rifaditya)"
  ],
  "license": "GPL-3.0-or-later",
  "environment": "client",
  "entrypoints": {
    "client": [
      "net.vanillaoutsider.culling.CameraCullingClient"
    ],
    "modmenu": [
      "net.vanillaoutsider.culling.config.ModMenuIntegration"
    ]
  },
  "mixins": [
    "camera-culling.mixins.json"
  ],
  "depends": {
    "fabricloader": ">=0.18.4",
    "minecraft": ">=26.2-",
    "fabric-api": "*"
  }
}
```

---

## 🔗 Halaman Terkait

- [[Arsitektur & Mixin|id_id-26.2-Architecture-and-Mixins]]
- [[API & Integrasi Mod|id_id-26.2-API-and-Integration]]
- [[Kembali ke Ikhtisar MC 26.2|id_id-26.2-Home]]
