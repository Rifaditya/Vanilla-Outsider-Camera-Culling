# 🔌 API & Integrasi Mod (Minecraft 26.2)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Camera Culling dirancang untuk beroperasi secara mulus bersama mod rendering klien (seperti Sodium, Iris, Canvas) dan mod konten yang menambahkan entitas atau entitas blok kustom.

---

## 🤝 Kompatibilitas Renderer Pihak Ketiga

### 1. Sodium & Embeddium
* **Chunk Medan Statis**: Sodium mengoptimalkan meshing chunk 16x16 dan pipeline rendering permukaan blok statis.
* **Entitas Dinamis**: Camera Culling mengoptimalkan entitas dinamis, peti, papan tanda, dan partikel.
* **Kompatibilitas**: 100% Kompatibel dengan nol bentrokan Mixin atau tabrakan status.

### 2. Iris & Shaders
* **Uniform Shader**: Shader melakukan pemrosesan pasca (post-processing) pada buffer frame yang diekstrak.
* **Penghematan Oklusi**: Karena entitas yang di-cull dicegah mengirimkan geometri ke G-Buffer, shader dapat berjalan dengan framerate yang jauh lebih tinggi di area yang padat.

---

## 🛠️ Hook API Java Terprogram

Mod lain dapat menanyakan status atau berintegrasi dengan Camera Culling melalui fasad utilitas statis:

### 1. Menanyakan Status Mesin Culling
```java
import net.vanillaoutsider.culling.CameraCullingClient;

boolean isEnabled = CameraCullingClient.isCullingEnabled();
long culledMobs = CameraCullingClient.getCulledEntitiesCount();
long renderedMobs = CameraCullingClient.getRenderedEntitiesCount();
```

### 2. Memasukkan ke Daftar Hitam Imunitas Secara Terprogram
```java
import net.vanillaoutsider.culling.config.CameraCullingConfig;

// Menambahkan ID entitas kustom ke whitelist culling
CameraCullingConfig.addClientBlacklist("mymod:custom_companion");
```

### 3. Verifikasi Garis Pandang Raycast Langsung
```java
import net.vanillaoutsider.culling.util.CullingRaycastHelper;

// Menjalankan pengujian garis pandang tanpa alokasi heap
boolean visible = CullingRaycastHelper.hasLineOfSight(level, camPos, targetX, targetY, targetZ);
```

---

## 🔗 Halaman Terkait

- [[Arsitektur & Mixin|id_id-26.2-Architecture-and-Mixins]]
- [[Pengaturan Pengembang & Kompilasi|id_id-26.2-Developer-Setup-and-Building]]
- [[Kembali ke Ikhtisar MC 26.2|id_id-26.2-Home]]
