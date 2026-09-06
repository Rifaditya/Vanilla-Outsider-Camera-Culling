# ✨ Culling Oklusi Partikel & Animasi (Minecraft 26.2)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Dalam vanilla Minecraft, tetesan lava bawah tanah, debu gua, partikel portal, dan obor menghasilkan ratusan quad partikel di balik dinding batu solid yang tetap dikirimkan ke GPU. Bersamaan dengan itu, atlas tekstur blok beranimasi terus mengunggah data frame ke GPU bahkan ketika pemain menjeda game dalam mode singleplayer atau membuka menu modal.

**Camera Culling** memperkenalkan pemeriksaan garis pandang partikel berkecepatan tinggi dan penekanan unggahan atlas tekstur.

---

## 📋 Informasi Singkat Culling Partikel & Animasi

| Properti | Nilai |
| :--- | :--- |
| **Target Partikel** | `SingleQuadParticle.extract(QuadParticleRenderState, Camera, float)` |
| **Target Animasi** | `TextureAtlas.cycleAnimationFrames()` |
| **Keamanan Proksimitas** | 4.0 meter ($16.0\text{m}^2$) di sekitar kamera |
| **Jarak Maks Partikel** | 64.0 meter ($4096.0\text{m}^2$) |
| **Pemicu Jeda Atlas** | Game singleplayer dijeda ATAU tidak ada level/pemain aktif |

---

## 🌪️ Pipeline Oklusi Partikel

```text
Partikel Dihasilkan di (X, Y, Z)
        │
        ▼
[1] Jarak <= 4m? ─────────────► RENDER (Gelembung Proksimitas)
        │ Tidak
        ▼
[2] Jarak > 64m? ─────────────► CULL (Batas Jarak Jauh)
        │ Tidak
        ▼
[3] Di Dalam Blok Solid? ─────► CULL (Terkurung)
        │ Tidak
        ▼
[4] Raycast Klip Visual Terhalang?
        ├── Ya ───────────────► CULL (Terhalang)
        └── Tidak ────────────► RENDER (Terlihat)
```

1. **Gelembung Keamanan Proksimitas (4.0m)**: Partikel yang dipancarkan dalam jarak 4 meter dari kamera di-render tanpa syarat dalam waktu $< 0.0001\mu\text{s}$.
2. **Batas Jarak Jauh (64.0m)**: Partikel yang dihasilkan di luar radius 64 meter di-cull untuk melindungi fillrate quad GPU.
3. **Pengurungan Blok Solid**: Jika koordinat blok `BlockPos.containing(x, y, z)` memiliki `isSolidRender() == true`, partikel langsung diabaikan.
4. **Raycast Klip Visual**: Memproyeksikan sinar `ClipContext.Block.VISUAL` dari posisi kamera ke vektor partikel. Jika ada blok solid kedap cahaya yang memotong garis pandang dengan margin $> 0.35\text{m}$, proses render dilewati.

---

## 🎬 Pembekuan Animasi Blok & Atlas Tekstur

Animasi atlas tekstur (lentera laut beranimasi, air/lava mengalir, prismarine, api, kompas, jam) mengonsumsi bandwidth GPU untuk menggilir indeks frame.

Camera Culling memeriksa `AnimationCullingHelper.shouldPauseAtlasAnimation()` dalam `TextureAtlasMixin`:
* Jika singleplayer dijeda (`mc.isPaused() == true`), unggahan data atlas akan dibekukan.
* Saat berada di layar judul, di menu modal, atau terputus dari dunia, pergantian tekstur latar belakang dihentikan.

---

## 🔗 Halaman Terkait

- [[Culling Oklusi Entitas|id_id-26.2-Entity-Occlusion-Culling]]
- [[Perintah & Konfigurasi|id_id-26.2-Commands-and-Configuration]]
- [[Kembali ke Ikhtisar MC 26.2|id_id-26.2-Home]]
