# 👥 Pertahanan Overdraw Kerumunan Mob (Minecraft 26.1.2)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Peternakan mob padat, ruang perdagangan villager, dan kandang pengembangbiakan hewan dapat menyebabkan penurunan framerate ekstrem saat ratusan entitas menumpuk hanya dalam beberapa blok. Meskipun rasterisasi Early-Z GPU menangani pengujian kedalaman dasar, proses mengekstrak dan mengirimkan ratusan hierarki kerangka mob membebani dispatcher render CPU.

**Camera Culling** menyediakan sistem pertahanan overdraw kerumunan mob yang aman dan opsional.

---

## 📋 Informasi Singkat Pertahanan Kerumunan Mob

| Properti | Nilai |
| :--- | :--- |
| **Kunci Konfigurasi** | `cullEntitiesBehindEntities` (Bawaan: `false`) |
| **Batas Kepadatan Kluster** | `maxEntitiesPerCluster` (Bawaan: `8` mob / 1.5 blok) |
| **Gagal-Cepat Jarak** | $> 16.0$ meter ($256.0\text{m}^2$) |
| **Radius Pencarian Kluster** | `targetBox.inflate(1.5)` |
| **Entitas Dikecualikan** | Mob transparan / Dekoratif (`Vex`, `ArmorStand`, `ItemFrame`, `Slime`, `MagmaCube`) |

---

## 🛑 Arsitektur Gagal-Cepat Jarak 16 Meter

Di lapangan terbuka luas dengan kawanan sapi yang tersebar, menjalankan query spasial di antara semua mob menyebabkan beban CPU yang tidak perlu. Dalam versi modern Camera Culling, culling overdraw kerumunan menerapkan **gagal-cepat jarak 16 meter** seketika:

```java
public static boolean isEntityOccludedByCloserEntities(Entity target, Level level, Vec3 camPos, AABB targetBox, double targetDistSq) {
    if (target == null || level == null || camPos == null) {
        return false;
    }

    // Fast-fail: only apply crowd overdraw culling within 16 meters
    if (targetDistSq > 256.0) {
        return false;
    }

    // 1. Cluster Density Cap in tight 1.5-block sphere
    int maxCluster = CameraCullingConfig.getMaxEntitiesPerCluster();
    AABB clusterBox = targetBox.inflate(1.5);
    List<Entity> clusterEntities = level.getEntities(target, clusterBox, 
        e -> e instanceof LivingEntity && !isTransparentOrDecorative(e));
    
    if (clusterEntities.size() < maxCluster) {
        return false;
    }

    int closerInCluster = 0;
    for (Entity e : clusterEntities) {
        double distSq = camPos.distanceToSqr(e.getX(), e.getY(), e.getZ());
        if (distSq < targetDistSq) {
            closerInCluster++;
            if (closerInCluster >= maxCluster) {
                return true; // Culled due to cluster density cap
            }
        }
    }
    return false;
}
```

### Manfaat:
1. **Bebas Overhead di Lapangan Terbuka**: Mob yang merumput di luar jarak 16 meter melewati pemindaian pencarian entitas sepenuhnya.
2. **Perlindungan Kandang Padat**: Dalam kandang grinder mob ukuran 1x1 atau 2x2 yang dipadati lebih dari 50 sapi/zombie, rendering dibatasi hanya untuk 8 entitas terdepan, menghilangkan lonjakan lag seketika.

---

## 🔗 Halaman Terkait

- [[Culling Oklusi Entitas|id_id-26.1.2-Entity-Occlusion-Culling]]
- [[Imunitas Bos & Daftar Hitam|id_id-26.1.2-Boss-and-Blacklist-Immunity]]
- [[Kembali ke Ikhtisar MC 26.1.2|id_id-26.1.2-Home]]
