# 👑 Imunitas Bos & Daftar Hitam (Minecraft 26.2)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Untuk menjaga keadilan gameplay dan kesadaran bertempur, ancaman kritis serta hewan peliharaan pendamping tidak boleh menghilang di balik dinding atau terpengaruh oleh algoritma culling yang agresif.

**Camera Culling** menyertakan identifikasi bos dinamis dan daftar hitam imunitas dua tingkat.

---

## 📋 Informasi Singkat Imunitas

| Properti | Nilai |
| :--- | :--- |
| **Ambang Batas Darah Bos Utama** | `bossHealthThreshold` (Bawaan: `150.0 HP` / 75 hati) |
| **Ambang Batas Darah Mini-Bos** | `miniBossHealthThreshold` (Bawaan: `50.0 HP` / 25 hati) |
| **Jalur Daftar Hitam Klien** | `config/camera-culling.json` (larik `clientBlacklist`) |
| **Jalur Daftar Hitam Server** | `config/camera-culling-server.json` (larik `serverBlacklist`) |
| **Cakupan Imunitas** | Dikecualikan dari Oklusi Blok, Overdraw Kerumunan, dan LOD Tekstur |

---

## 🐲 Deteksi Bos & Mini-Bos Dinamis

Camera Culling mengevaluasi imunitas bos melalui dua mekanisme berbeda:

### 1. Ambang Batas Darah Dinamis
Setiap `LivingEntity` yang memiliki `getMaxHealth()` memenuhi atau melampaui ambang batas yang dikonfigurasi akan diberikan imunitas tanpa syarat:
* `maxHealth >= 150.0` $\implies$ Bos Utama (Ender Dragon, Wither, Warden).
* `maxHealth >= 50.0` $\implies$ Mini-Bos (Elder Guardian, Ravager, Iron Golem, Piglin Brute, Breeze, juara modifikasi).

### 2. Heuristik Kata Kunci Registri & Pengenal
Entitas dengan nama yang cocok dengan substring berikut secara otomatis diidentifikasi sebagai bos:
```text
dragon, wither, warden, elder_guardian, ravager, evoker, iron_golem, 
piglin_brute, breeze, boss, miniboss, mini_boss, titan, leviathan, 
harbinger, monarch, behemoth, champion, elite, brute
```

---

## 🛡️ Sistem Daftar Hitam Imunitas Dua Tingkat

```text
Resolusi Imunitas
├── Pemain Lokal / Kendaraan / Tunggangan ──► 100% Di-render
├── Efek Bersinar (Glowing) Aktif ─────────► 100% Di-render
├── Terdeteksi Bos atau Mini-Bos ──────────► 100% Di-render
├── Cocok dengan Daftar Hitam Klien ───────► 100% Di-render
└── Cocok dengan Daftar Hitam Server ──────► 100% Di-render
```

### 1. Daftar Hitam Personal Klien
Pemain dapat memasukkan entitas pendamping tertentu ke dalam whitelist secara lokal menggunakan perintah dalam game:
```sql
/cameraculling blacklist add minecraft:wolf
/cameraculling blacklist add minecraft:allay
/cameraculling blacklist add minecraft:cat
```

### 2. Daftar Hitam Admin Server
Operator server dapat menentukan imunitas entitas global di `config/camera-culling-server.json` atau melalui `/cameraculling serverblacklist add <id>`. Semua klien yang terhubung akan secara otomatis mematuhi daftar imunitas yang diberlakukan server.

---

## 🔗 Halaman Terkait

- [[Culling Oklusi Entitas|id_id-26.2-Entity-Occlusion-Culling]]
- [[LOD Tekstur Berbasis Jarak|id_id-26.2-Distance-Texture-LOD]]
- [[Perintah & Konfigurasi|id_id-26.2-Commands-and-Configuration]]
- [[Kembali ke Ikhtisar MC 26.2|id_id-26.2-Home]]
