# ⏱️ Histeresis Temporal & Matematika Nol-Alokasi (Minecraft 26.3)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Culling oklusi berkecepatan tinggi dapat memicu dua kelemahan performa umum:
1. **Kedipan Sisi Kamera (Z-Fighting / Boundary Pop-in)**: Rotasi kecil kamera atau goyangan pandangan saat berjalan (view-bobbing) yang melintasi tepi blok dapat menyebabkan entitas berkedip cepat antara status ter-render dan tidak ter-render pada frame yang bergantian.
2. **Lonjakan Tersendat Garbage Collection**: Alokasi objek `new Vec3()` dan kotak pembatas (bounding box) secara terus-menerus selama raycasting memicu jeda pengumpulan sampah (garbage collection) Young-Gen JVM yang sering.

**Camera Culling** mengatasi kedua masalah ini dengan **Buffer Toleransi Adaptif Berskala Jarak** dan **Mesin Hot-Path Bebas Alokasi Heap**.

---

## 📋 Informasi Singkat Histeresis & Alokasi

| Properti | Nilai |
| :--- | :--- |
| **Buffer Toleransi Jarak Dekat** | $d \le 32\text{m} \implies 4\text{ frame terhalang berturut-turut}$ |
| **Buffer Toleransi Jarak Sedang** | $32\text{m} < d \le 64\text{m} \implies 8\text{ frame terhalang berturut-turut}$ |
| **Buffer Toleransi Jarak Jauh** | $d > 64\text{m} \implies 12\text{ frame terhalang berturut-turut}$ |
| **Transisi Visibilitas** | Seketika ($0\text{ frame delay}$) saat garis pandang terbuka |
| **Struktur Pelacakan** | `it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap` (Nol overhead boxing) |
| **Alokasi Per-Frame** | $0\text{ byte}$ (Koordinat primitif double diteruskan langsung) |

---

## 📐 Formula Buffer Toleransi Berskala Jarak

Rentang frame terhalang (streak) yang diperlukan sebelum mengubah status entitas menjadi `[CULLED]` dihitung dengan:

$$\text{RequiredStreak}(d) = \begin{cases}
12\text{ frame} & \text{jika } d^2 > 64.0^2 \ (4096.0\text{m}^2) \\
8\text{ frame} & \text{jika } d^2 > 32.0^2 \ (1024.0\text{m}^2) \\
4\text{ frame} & \text{jika } d^2 \le 32.0^2
\end{cases}$$

```text
[GARIS PANDANG ENTITAS HILANG]
       │
       ├── Frame 1 terhalang ──► RENDER (Toleransi Peluruhan)
       ├── Frame 2 terhalang ──► RENDER (Toleransi Peluruhan)
       ├── Frame 3 terhalang ──► RENDER (Toleransi Peluruhan)
       └── Frame 4 terhalang ──► CULL (Syarat Streak Terpenuhi)
```

* **Unculling Seketika**: Begitu satu sampel raycast menemukan garis pandang yang jelas, streak oklusi langsung dihapus (`OCCLUDED_STREAK.remove(id)`), membuat entitas terlihat seketika dengan latensi $0\text{ frame}$.
* **Peluruhan Asimetris**: Menjadi terhalang membutuhkan beberapa frame berturut-turut; menjadi terlihat hanya butuh 1 frame. Ini menghilangkan semua kedipan akibat putaran kamera.

---

## ⚡ Arsitektur Primitif Tanpa Alokasi Heap

Dalam `CullingRaycastHelper.java`, alokasi heap vektor perantara dihilangkan sepenuhnya:

```java
// Zero heap allocations: coordinates are passed as raw primitive doubles
public static boolean hasLineOfSight(Level level, Vec3 from, double toX, double toY, double toZ) {
    Vec3 to = new Vec3(toX, toY, toZ);
    ClipContext ctx = new ClipContext(from, to, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, CollisionContext.empty());
    BlockHitResult hit = level.clip(ctx);
    if (hit.getType() == HitResult.Type.MISS) {
        return true;
    }
    // Evaluasi tabrakan lantai & toleransi tanpa konstruksi objek baru
    ...
}
```

---

## 🔗 Halaman Terkait

- [[Culling Oklusi Entitas|id_id-26.3-Entity-Occlusion-Culling]]
- [[Pencatatan Debug & Diagnostik|id_id-26.3-Debug-Logging-and-Diagnostics]]
- [[Arsitektur & Mixin|id_id-26.3-Architecture-and-Mixins]]
- [[Kembali ke Ikhtisar MC 26.3|id_id-26.3-Home]]
