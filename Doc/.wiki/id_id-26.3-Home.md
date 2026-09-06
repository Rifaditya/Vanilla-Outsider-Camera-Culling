# 🟢 Camera Culling (Minecraft 26.3)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Selamat datang di pusat dokumentasi **Minecraft 26.3** untuk **Camera Culling** (`v1.10.1+26.3`).

> 📌 **Penafian Sumber Repositori**: Dokumentasi dalam Wiki ini mencerminkan **status kode sumber saat ini di repositori**, yang mungkin mencakup komit terbaru yang belum dirilis atau fitur dalam tahap pengembangan mendahului build rilis publik di CurseForge dan Modrinth.

---

## 📋 Informasi Singkat Minecraft 26.3

| Properti | Nilai |
| :--- | :--- |
| **Target Versi Minecraft** | `26.3` |
| **Versi Rilis** | `1.10.1+26.3` |
| **Kebutuhan Java** | Java 25 (`release = 25`) |
| **Fabric Loader** | `>=0.18.4` |
| **Fabric API** | `0.156.1+26.3` |
| **Lisensi** | GNU General Public License v3.0 (GPLv3) |
| **Jalur Subproyek** | `Camera Culling v26.3/Camera Culling 26.3` |

---

## ⚡ Matriks Fitur Inti

```text
Camera Culling 26.3 Pipeline
├── Entity Occlusion (Zero-Allocation Raycast Engine)
├── Block Entity Occlusion (6-Sided Enclosure & Sightlines)
├── 2-Sided Sign Text Culling (SignTextSlot API + Perkalian Titik Vektor Normal)
├── Particle Occlusion (4m Proximity Safety + Visual Clip)
├── Animation Culling (TextureAtlas Upload Suppression)
├── Distance Texture LOD (3-Tier OpenGL Mipmap Bias)
├── Boss & Mini-Boss Immunity (Configurable HP Thresholds)
├── Anti-Flicker Temporal Hysteresis (Adaptive 4/8/12-Frame Buffer)
└── Graphical GUI (YACL v3 & ModMenu) + In-Game Commands
```

---

## 📚 Indeks Dokumentasi 26.3

1. [[Culling Oklusi Entitas|id_id-26.3-Entity-Occlusion-Culling]] — Raycasting multi-titik & pemfilteran lantai.
2. [[Culling Entitas Blok|id_id-26.3-Block-Entity-Culling]] — Pemeriksaan pengurungan peti dan entitas blok.
3. [[Culling Teks Papan Tanda|id_id-26.3-Sign-and-Hanging-Sign-Culling]] — Matematika perkalian titik normal 2 sisi dan penanganan teks.
4. [[Culling Partikel & Animasi|id_id-26.3-Particle-and-Animation-Culling]] — Culling partikel bawah tanah & pembekuan animasi atlas.
5. [[Pertahanan Overdraw Kerumunan Mob|id_id-26.3-Mob-Crowd-Overdraw-Defense]] — Pembatasan jarak 16m & batas kepadatan kluster 1.5m.
6. [[LOD Tekstur Berbasis Jarak|id_id-26.3-Distance-Texture-LOD]] — Pembiasan LOD mipmap OpenGL pada kawanan mob yang jauh.
7. [[Imunitas Bos & Daftar Hitam|id_id-26.3-Boss-and-Blacklist-Immunity]] — Perlindungan bos & daftar hitam dua tingkat.
8. [[Histeresis Temporal & Matematika Nol-Alokasi|id_id-26.3-Temporal-Hysteresis-and-Zero-Allocation]] — Buffer toleransi & mesin bebas alokasi heap.
9. [[Perintah & Konfigurasi|id_id-26.3-Commands-and-Configuration]] — Referensi sintaksis perintah Brigadier lengkap.
10. [[Konfigurasi GUI Grafis (YACL)|id_id-26.3-GUI-Configuration]] — Panduan menu grafis.
11. [[Pencatatan Debug & Diagnostik|id_id-26.3-Debug-Logging-and-Diagnostics]] — Pelacakan diagnostik obrolan & log transisi status real-time.
12. [[Arsitektur & Mixin|id_id-26.3-Architecture-and-Mixins]] — Hierarki paket dan tabel target Mixin.
13. [[Pengaturan Pengembang & Kompilasi|id_id-26.3-Developer-Setup-and-Building]] — Pengaturan JDK 25 dan instruksi kompilasi Gradle Loom.
14. [[API & Integrasi Mod|id_id-26.3-API-and-Integration]] — Hook integrasi terprogram.

---

[[Kembali ke Portal Versi|id_id-Home]] &bull; [[Matriks Kompatibilitas Versi|id_id-Version-Compatibility]]
