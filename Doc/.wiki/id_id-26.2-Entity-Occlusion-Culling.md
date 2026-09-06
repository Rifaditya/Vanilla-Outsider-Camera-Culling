# 🧱 Culling Oklusi Entitas (Minecraft 26.2)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Dalam Minecraft 26.2, rendering entitas klien mengekstrak render state untuk semua entitas di dalam frustum kamera—bahkan saat tersembunyi di balik gua, tebing, atau bangunan. **Camera Culling** mencegat pemeriksaan ini untuk mencegah mob yang terhalang mengonsumsi pemrosesan geometri CPU dan draw call GPU.

---

## 📋 Informasi Singkat Culling Entitas

| Properti | Nilai |
| :--- | :--- |
| **Target Pipeline** | `EntityRenderer.shouldRender(T, Frustum, double, double, double)` |
| **Profil Bawaan** | `SUPER` (Ekstrem) |
| **Konteks Klip** | `ClipContext.Block.COLLIDER`, `ClipContext.Fluid.NONE` |
| **Filter Lantai** | Mengabaikan tabrakan `Direction.UP` ketika tinggi tabrakan $\le Y + 0.15\text{m}$ |
| **Oklusi Daun** | Render solid dan blok `BlockTags.LEAVES` menghalangi garis pandang |
| **Gelembung Imunitas** | Jarak kuadrat $< \text{minDistanceSq}$ |

---

## 🔬 Pengambilan Sampel Garis Pandang Anatomis Multi-Titik

Camera Culling melakukan pengambilan sampel anatomis multi-titik berdasarkan [[Profil Culling|id_id-26.2-Commands-and-Configuration]] yang dipilih:

```text
       [1] Puncak Kepala (maxY - 0.05)
          \
           [2] Posisi Mata (entity.getEyeY())
            \
             [3] Tubuh Bagian Atas (minY + height * 0.70)
              \
               [4] Pusat Massa (centerY)
                \
        [5-8] Sisi Flank Perimeter Terangkat (pemeriksaan lebar/kedalaman)
```

1. **Sampel 1: Bagian Atas Kepala Entitas (`maxY - 0.05`)**
   - Pemeriksaan prioritas tinggi. Mendeteksi entitas tinggi yang mengintip di balik barikade rendah atau pagar.
2. **Sampel 2: Posisi Mata Anatomis (`getEyeY()`)**
   - Garis pandang langsung dari kamera ke mata mob.
3. **Sampel 3: Tubuh Bagian Atas / Dada (`minY + height * 0.70`)**
   - Mengevaluasi garis pandang tubuh bagian atas secara aman di atas permukaan tanah.
4. **Sampel 4: Pusat Massa (`(minY + maxY) * 0.5`)**
   - Uji titik tengah geometris umum.
5. **Sampel 5–8: Sisi Flank Perimeter Terangkat**
   - Mengevaluasi $(X_{\min} + 0.15, Z_{\min} + 0.15)$, $(X_{\max} - 0.15, Z_{\min} + 0.15)$, dll. Memastikan bos bertubuh lebar (misalnya Ravager, Warden, Laba-laba) tetap terlihat saat pundak mereka muncul dari balik sudut dinding.

---

## 🛡️ Pemfilteran Lantai & Lereng Berarah

Saat pemain melihat ke bawah ke arah mob yang berdiri di medan tidak rata, raycast standar dapat menabrak permukaan atas blok di dekat kaki mob, dan secara keliru menganggap lantai sebagai dinding penghalang.

Camera Culling menerapkan **Pemfilteran Tabrakan Lantai Berarah**:
$$\text{Jika } \text{hit.getDirection()} == \text{Direction.UP} \quad \text{dan} \quad Y_{\text{hit}} \le Y_{\text{target}} + 0.15\text{m} \implies \text{Garis Pandang Valid (Tidak Terhalang)}$$

Ini menjamin bahwa mob yang melintasi bukit, tangga, dan medan tidak rata tidak akan pernah di-cull secara keliru.

---

## ⚡ Mesin Raycast Tanpa Alokasi

Pada implementasi terdahulu, mengevaluasi 8 titik sampel pada 100 entitas menghasilkan lebih dari 180.000 alokasi heap `new Vec3()` per detik, yang memicu jeda garbage collection Young-Gen JVM.

Dalam 26.2, `CullingRaycastHelper` meneruskan koordinat primitif mentah:
```java
public static boolean hasLineOfSight(Level level, Vec3 from, double toX, double toY, double toZ)
```
Arsitektur ini sepenuhnya menghilangkan alokasi heap perantara pada setiap frame.

---

## 🔗 Halaman Terkait

- [[Pertahanan Overdraw Kerumunan Mob|id_id-26.2-Mob-Crowd-Overdraw-Defense]]
- [[Histeresis Temporal & Matematika|id_id-26.2-Temporal-Hysteresis-and-Zero-Allocation]]
- [[Imunitas Bos & Daftar Hitam|id_id-26.2-Boss-and-Blacklist-Immunity]]
- [[Kembali ke Ikhtisar MC 26.2|id_id-26.2-Home]]
