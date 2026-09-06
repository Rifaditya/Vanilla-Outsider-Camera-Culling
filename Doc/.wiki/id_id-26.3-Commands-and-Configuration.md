# 🎮 Perintah & Konfigurasi (Minecraft 26.3)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Camera Culling menyediakan rangkaian perintah in-game Brigadier lengkap (`/cameraculling`) dan persistensi konfigurasi JSON yang rapi (`config/camera-culling.json`).

---

## 📋 Tabel Referensi Perintah

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

## ⚙️ Rincian Perintah Terperinci

| Sintaksis Perintah | Parameter | Fungsi |
| :--- | :--- | :--- |
| `/cameraculling status` | Tidak ada | Menampilkan statistik langsung, penghitung entitas ter-render vs di-cull, profil aktif, dan jumlah daftar hitam. |
| `/cameraculling toggle` | Tidak ada | Membalik status aktif culling utama. |
| `/cameraculling set <profile>` | `low`, `medium`, `high`, `super` | Mengganti profil intensitas culling yang aktif. |
| `/cameraculling particles [bool]` | `true`, `false` (opsional) | Mengaktifkan/menonaktifkan culling oklusi partikel di balik blok solid. |
| `/cameraculling animations [bool]` | `true`, `false` (opsional) | Mengaktifkan/menonaktifkan pembekuan animasi blok dan atlas tekstur. |
| `/cameraculling crowdculling <bool>` | `true`, `false` | Mengaktifkan/menonaktifkan culling entitas di balik entitas / overdraw kerumunan. |
| `/cameraculling cluster <int>` | `1` hingga `128` | Mengonfigurasi jumlah maksimum mob yang di-render per kluster 1.5 blok (bawaan: `8`). |
| `/cameraculling texturlod <bool>` | `true`, `false` | Mengaktifkan atau menonaktifkan penskalaan mipmap LOD tekstur berbasis jarak. |
| `/cameraculling texturlod range <start> <far>` | `1.0..256.0`, `2.0..512.0` | Menetapkan ambang batas jarak dekat dan jauh untuk penskalaan LOD tekstur (misalnya `16.0 32.0`). |
| `/cameraculling bossimmunity <bool>` | `true`, `false` | Mengaktifkan atau menonaktifkan perlindungan garis pandang untuk bos dan mini-bos. |
| `/cameraculling bosshealth <hp>` | `1.0` hingga `10000.0` | Menetapkan ambang batas HP bos utama (misalnya `150.0`). |
| `/cameraculling minibosshealth <hp>` | `1.0` hingga `10000.0` | Menetapkan ambang batas HP mini-bos (misalnya `50.0`). |
| `/cameraculling blacklist add <id>` | String ID entitas | Menambahkan entitas (misalnya `minecraft:wolf`) ke daftar imunitas klien pribadi. |
| `/cameraculling blacklist remove <id>` | String ID entitas | Menghapus entitas dari daftar imunitas klien pribadi. |
| `/cameraculling blacklist list` | Tidak ada | Menampilkan daftar semua entitas yang saat ini berada di daftar hitam imunitas pribadi. |
| `/cameraculling blacklist clear` | Tidak ada | Menghapus semua entri dari daftar hitam imunitas pribadi. |
| `/cameraculling serverblacklist ...` | Subperintah + ID | Mengonfigurasi daftar hitam imunitas yang diberlakukan server (Memerlukan OP). |
| `/cameraculling debug [bool]` | `true`, `false` (opsional) | Mengaktifkan/menonaktifkan pelacakan diagnostik transisi status real-time ke obrolan dan log. |
| `/cameraculling reload` | Tidak ada | Memuat ulang berkas konfigurasi dari disk. |

---

## 📄 Format Konfigurasi JSON

Berkas konfigurasi terletak di direktori `.minecraft/config/`:

### Konfigurasi Klien (`config/camera-culling.json`)
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

### Konfigurasi Server (`config/camera-culling-server.json`)
```json
{
  "serverBlacklist": []
}
```

---

## 🔗 Halaman Terkait

- [[Konfigurasi GUI Grafis (YACL)|id_id-26.3-GUI-Configuration]]
- [[Pencatatan Debug & Diagnostik|id_id-26.3-Debug-Logging-and-Diagnostics]]
- [[Kembali ke Ikhtisar MC 26.3|id_id-26.3-Home]]
