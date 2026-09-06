# 🌐 Matriks Kompatibilitas Versi dan Siklus Hidup

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Dokumen ini menguraikan matriks rilis multi-era aktif, batasan dependensi, spesifikasi runtime Java, dan variasi API bytecode untuk **Camera Culling**.

> 📌 **Penafian Sumber Repositori**: Dokumentasi dalam Wiki ini mencerminkan **status kode sumber saat ini di repositori**, yang mungkin mencakup komit terbaru yang belum dirilis atau fitur dalam tahap pengembangan mendahului build rilis publik di CurseForge dan Modrinth.

---

## 📋 Matriks Siklus Hidup Multi-Versi

| Era Jangkar Minecraft | Versi MC Target | Versi Rilis Saat Ini | Kebutuhan Java | Batasan Fabric Loader | Batasan Fabric API | Status Rilis |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| **Modern Sovereign (26.3)** | `26.3` | `1.10.1+26.3` | Java 25 (`release = 25`) | `>=0.18.4` | `0.156.1+26.3` | 🟢 Rilis Aktif |
| **Modern Sovereign (26.2)** | `26.2` | `1.10.1+26.2` | Java 25 (`release = 25`) | `>=0.18.4` | `0.145.4+26.2` | 🟢 Rilis Aktif |
| **Modern Sovereign (26.1.2)** | `26.1.2` | `1.10.2+26.1.2` | Java 25 (`release = 25`) | `>=0.18.4` | `0.145.4+26.1.2` | 🟢 Rilis Aktif |

---

## 🛠️ Perbedaan API Bytecode Antar Versi

### 1. Alur Ekstraksi RenderState Entitas Blok
* **Minecraft 26.1.2**:
  - `BlockEntityRenderDispatcher.tryExtractRenderState(BlockEntity, float, ModelFeatureRenderer$CrumblingOverlay)` — menerima **3 argumen**.
* **Minecraft 26.2 & 26.3**:
  - `BlockEntityRenderDispatcher.tryExtractRenderState(BlockEntity, float, ModelFeatureRenderer$CrumblingOverlay, boolean isGloballyRendered)` — menerima **4 argumen**.

### 2. API Pengambilan Data Teks Papan Tanda
* **Minecraft 26.1.2 & 26.2**:
  - `SignBlockEntity.getFrontText()` dan `SignBlockEntity.getBackText()` mengambil `SignText`.
  - `SignText.getMessage(int index, boolean filtered)` mengambil baris `Component`.
* **Minecraft 26.3**:
  - `SignBlockEntity.getText(SignTextSlot.FRONT)` dan `SignBlockEntity.getText(SignTextSlot.BACK)` mengambil `SignText`.
  - `SignText.getMessages(boolean filtered)` mengambil larik baris `Component`.

---

## 📦 Lokasi Arsip Artefak Build

Semua build rilis dikompilasi secara otomatis dan disimpan dalam struktur arsip terpusat repositori induk:

```text
Archive Jar of all versions/
├── MC 26.1.2/
│   ├── vanilla-outsider-camera-culling-1.10.1+26.1.2.jar
│   └── vanilla-outsider-camera-culling-1.10.1+26.1.2-sources.jar
├── MC 26.2/
│   ├── vanilla-outsider-camera-culling-1.10.0+26.2.jar
│   └── vanilla-outsider-camera-culling-1.10.0+26.2-sources.jar
└── MC 26.3/
    ├── vanilla-outsider-camera-culling-1.10.0+26.3.jar
    └── vanilla-outsider-camera-culling-1.10.0+26.3-sources.jar
```

---

## 🔗 Tautan Cepat

- [[👉 Masuk Wiki Minecraft 26.3|id_id-26.3-Home]]
- [[👉 Masuk Wiki Minecraft 26.2|id_id-26.2-Home]]
- [[👉 Masuk Wiki Minecraft 26.1.2|id_id-26.1.2-Home]]
- [[Kembali ke Portal|id_id-Home]]
