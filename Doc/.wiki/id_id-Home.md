# 📷 Wiki Camera Culling

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Selamat datang di portal dokumentasi resmi **Camera Culling**. Camera Culling adalah mod optimasi rendering sisi klien berperforma tinggi untuk Minecraft **26.1.2**, **26.2**, dan **26.3** yang dikembangkan di bawah filosofi **Vanilla Outsider**.

> 📌 **Penafian Sumber Repositori**: Dokumentasi dalam Wiki ini mencerminkan **status kode sumber saat ini di repositori**, yang mungkin mencakup komit terbaru yang belum dirilis atau fitur dalam tahap pengembangan mendahului build rilis publik di CurseForge dan Modrinth.

---

## 🧭 Portal Peralihan Multi-Versi

Camera Culling dikembangkan di bawah aturan ketat **1 JAR 1 Versi**. Pilih versi Minecraft target Anda di bawah ini untuk memasuki pohon dokumentasi khusus yang terisolasi:

| Versi Minecraft Target | Versi Rilis Mod | Lingkungan Runtime Java | Alat Build | Portal Wiki Khusus |
| :--- | :--- | :--- | :--- | :--- |
| **Minecraft 26.1.2** | `1.10.2+26.1.2` | Java 25+ | Fabric Loom 1.15.5 | [[👉 Masuk Wiki MC 26.1.2|id_id-26.1.2-Home]] |
| **Minecraft 26.2** | `1.10.1+26.2` | Java 25+ | Fabric Loom 1.15.5 | [[👉 Masuk Wiki MC 26.2|id_id-26.2-Home]] |
| **Minecraft 26.3** | `1.10.1+26.3` | Java 25+ | Fabric Loom 1.15.5 | [[👉 Masuk Wiki MC 26.3|id_id-26.3-Home]] |

---

## ⚡ Matriks Optimasi Inti

| Sistem Optimasi | Mekanisme Utama | Manfaat Performa |
| :--- | :--- | :--- |
| **Mesin Raycast Tanpa Alokasi** | Pemeriksaan garis pandang koordinat primitif | Menghilangkan lonjakan jeda GC Young-Gen JVM saat kamera berputar |
| **Histeresis Temporal Anti-Kedipan** | Buffer toleransi adaptif 4/8/12-frame berbasis jarak | Menghilangkan kedipan di tepi blok dan akibat view-bobbing |
| **Culling Teks Papan Tanda Dua Sisi** | Perkalian titik vektor normal permukaan ($\vec{N} \cdot \vec{V}$) | Pengurangan 50%–100% pada draw call teks papan tanda |
| **Culling Oklusi Partikel** | Gelembung pengaman 4m + raycast klip visual | Mencegah rendering quad partikel bawah tanah & terhalang |
| **Culling Animasi** | Penekanan unggahan atlas tekstur | Membekukan animasi blok 3D dan unggahan tekstur di luar layar |
| **Pertahanan Overdraw Kerumunan Mob** | Gagal-cepat 16m + batas kepadatan kluster 1.5m | Menghilangkan lonjakan lag pada kandang mob padat & peternakan |
| **LOD Tekstur Berbasis Jarak** | Bias mipmap OpenGL 3 tingkat ($0.0 \to 1.0 \to 2.5$) | Mengurangi secara drastis fillrate VRAM pada kawanan mob yang jauh |
| **Imunitas Bos & Mini-Bos** | Ambang batas HP dinamis & heuristik nama | Mencegah culling bos yang merusak alur permainan |
| **Daftar Hitam Imunitas Dua Tingkat** | JSON klien lokal + Sinkronisasi admin server | Whitelist kustom untuk mob pendamping/peliharaan |
| **Culling Entitas Blok** | Deteksi penutupan solid 6 sisi | Melewati ekstraksi render untuk peti dan blok tertutup |

---

## 📚 Navigasi Global

- [[Matriks Kompatibilitas Versi dan Siklus Hidup|id_id-Version-Compatibility]]
- [[Pohon Dokumentasi Minecraft 26.1.2|id_id-26.1.2-Home]]
- [[Pohon Dokumentasi Minecraft 26.2|id_id-26.2-Home]]
- [[Pohon Dokumentasi Minecraft 26.3|id_id-26.3-Home]]

---

<p align="center">
  <em>Dikembangkan oleh <strong>Dasik (Rifaditya)</strong> | Dilisensikan di bawah <strong>GNU General Public License v3.0 (GPLv3)</strong></em>
</p>
