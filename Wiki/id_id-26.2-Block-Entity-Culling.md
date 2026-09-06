# 📦 Culling Oklusi Entitas Blok (Minecraft 26.2)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Entitas blok (peti, ender chest, papan tanda, spanduk, tengkorak, pot berdekorasi, lonceng, dan suar) mengirimkan draw call individual setiap frame. Di ruang penyimpanan besar atau fasilitas penyortiran otomatis, hal ini menghasilkan beban perebutan draw call GPU yang berat.

---

## 📋 Informasi Singkat Culling Entitas Blok

| Properti | Nilai |
| :--- | :--- |
| **Target Pipeline** | `BlockEntityRenderDispatcher.tryExtractRenderState(BlockEntity, float, CrumblingOverlay, boolean)` |
| **Deteksi Pengurungan** | Memeriksa ke-6 sisi tetangga: `up`, `down`, `north`, `south`, `east`, `west` |
| **Mode Konservatif** | Pemeriksaan hanya yang terkurung pada profil `LOW` |
| **Mode Agresif** | Verifikasi garis pandang raycast penuh pada `MEDIUM`, `HIGH`, `SUPER` |
| **Hasil** | Mengembalikan RenderState `null` untuk melewati pengiriman render |

---

## 🔍 Arsitektur Pengurungan & Verifikasi Garis Pandang

```text
               [ATAS]
                 │
   [BARAT] ─── [PETI] ─── [TIMUR]
                 │
               [BAWAH]
```

### 1. Fast-Pass Pengurungan 6 Sisi Solid
Sebelum menjalankan perhitungan raycast apa pun, Camera Culling meminta status blok tetangga:
```java
BlockState up = level.getBlockState(pos.above());
BlockState down = level.getBlockState(pos.below());
BlockState north = level.getBlockState(pos.north());
BlockState south = level.getBlockState(pos.south());
BlockState east = level.getBlockState(pos.east());
BlockState west = level.getBlockState(pos.west());

if (up.isSolidRender() && down.isSolidRender() && north.isSolidRender()
    && south.isSolidRender() && east.isSolidRender() && west.isSolidRender()) {
    return true; // 100% Terhalang — lewati rendering
}
```
Peti yang tertanam di balik dinding atau terkurung di dalam fondasi basement solid tidak akan di-render dengan kalkulasi CPU mendekati nol ($< 0.0001\mu\text{s}$).

### 2. Pemeriksaan Garis Pandang Raycast
Pada profil `MEDIUM`, `HIGH`, dan `SUPER` (`cullAllBlockEntities = true`), Camera Culling memproyeksikan sinar dari posisi kamera ke pusat entitas blok $(X + 0.5, Y + 0.5, Z + 0.5)$ untuk membatalkan render state yang terhalang.

---

## 🔗 Halaman Terkait

- [[Culling Teks Papan Tanda|id_id-26.2-Sign-and-Hanging-Sign-Culling]]
- [[Culling Oklusi Entitas|id_id-26.2-Entity-Occlusion-Culling]]
- [[Arsitektur & Mixin|id_id-26.2-Architecture-and-Mixins]]
- [[Kembali ke Ikhtisar MC 26.2|id_id-26.2-Home]]
