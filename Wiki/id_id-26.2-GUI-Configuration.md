# 🖥️ Konfigurasi GUI Grafis (Minecraft 26.2)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Camera Culling menyediakan dukungan layar konfigurasi grafis modern opsional yang didukung oleh **YetAnotherConfigLib (YACL v3)** dan **ModMenu**.

---

## 📋 Informasi Singkat Integrasi GUI

| Properti | Nilai |
| :--- | :--- |
| **Mesin GUI yang Didukung** | YetAnotherConfigLib v3 (YACL) + ModMenu |
| **Pola Integrasi** | Pemuatan kelas tertunda (`ConfigScreenFactory`) |
| **Keamanan Crash Server** | 100% Aman — tanpa referensi kelas GUI klien pada entrypoint server |
| **Kategori Menu** | 3 Kategori Bertab Khusus |

---

## 🗂️ Rincian Kategori GUI

```text
Layar Pengaturan Camera Culling
├── 1. Engine & Diagnostics
│   ├── Master Enable (Kotak Centang)
│   ├── Culling Level (Menu Tarik-Turun: LOW, MEDIUM, HIGH, SUPER)
│   └── Real-Time Debug Logging (Kotak Centang)
│
├── 2. Entity & Crowd Occlusion
│   ├── Crowd Overdraw Culling (Kotak Centang)
│   ├── Max Cluster Entities Cap (Slider: 1 hingga 32)
│   ├── Boss & Mini-Boss Immunity (Kotak Centang)
│   ├── Major Boss Health Threshold (Kolom Numerik, bawaan: 150.0 HP)
│   └── Mini-Boss Health Threshold (Kolom Numerik, bawaan: 50.0 HP)
│
└── 3. Blocks, Particles & Animations
    ├── Particle Culling (Kotak Centang)
    ├── Block & Texture Animation Culling (Kotak Centang)
    ├── 2-Sided Sign Text Culling (Kotak Centang)
    ├── Distance Texture LOD (Kotak Centang)
    ├── Distance Texture LOD Start Distance (Slider: 8m hingga 64m)
    └── Distance Texture LOD Far Distance (Slider: 16m hingga 128m)
```

---

## 🛡️ Pemuatan Kelas Tertunda & Keamanan Crash



```java
public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        if (FabricLoader.getInstance().isModLoaded("yet_another_config_lib_v3")) {
            return YaclScreenHelper.createFactory();
        }
        return parent -> null;
    }
}
```

Jika YACL tidak terpasang, semua pengaturan dapat disesuaikan melalui [[Perintah & Konfigurasi|id_id-26.2-Commands-and-Configuration]] atau dengan mengedit berkas `config/camera-culling.json`.

---

## 🔗 Halaman Terkait

- [[Perintah & Konfigurasi|id_id-26.2-Commands-and-Configuration]]
- [[Pencatatan Debug & Diagnostik|id_id-26.2-Debug-Logging-and-Diagnostics]]
- [[Kembali ke Ikhtisar MC 26.2|id_id-26.2-Home]]
