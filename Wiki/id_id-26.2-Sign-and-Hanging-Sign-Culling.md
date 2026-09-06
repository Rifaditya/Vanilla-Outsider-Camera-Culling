# 🪧 Culling Teks Papan Tanda & Tanda Gantung 2 Sisi (Minecraft 26.2)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Dalam Minecraft 26.2, papan tanda memiliki rendering teks dua sisi. Mesin render font game menggambar quad glif, warna, dan garis luar bercahaya pada kedua sisi secara bersamaan.

**Camera Culling** menghilangkan proses render teks yang tidak perlu melalui perkalian titik vektor normal ($\vec{N} \cdot \vec{V}$) dan fast-pass teks kosong.

---

## 📋 Informasi Singkat Culling Papan Tanda

| Properti | Nilai |
| :--- | :--- |
| **Target Pipeline** | `BlockEntityRenderDispatcher.tryExtractRenderState` `@At("RETURN")` |
| **Hook API** | `signState.frontText = null;` / `signState.backText = null;` |
| **Tipe yang Didukung** | Wall Signs, Standing Signs, Wall Hanging Signs, Ceiling Hanging Signs |
| **Fast-Pass Teks Kosong** | Melewati permukaan kosong secara otomatis dengan nol karakter teks non-spasi |
| **Margin Perkalian Titik** | Toleransi $\pm 0.05$ mencegah terjadinya pop-in di sudut tepi |

---

## 📐 Matematika Perkalian Titik Vektor Normal

Untuk menentukan apakah sisi depan atau belakang papan tanda menghadap ke arah kamera, Camera Culling menghitung perkalian titik antara vektor normal permukaan papan tanda $\vec{N} = (N_x, N_z)$ dan vektor dari pusat papan tanda ke kamera $\vec{V} = (V_x, V_z)$:

$$V_x = X_{\text{cam}} - (X_{\text{sign}} + 0.5), \quad V_z = Z_{\text{cam}} - (Z_{\text{sign}} + 0.5)$$

### 1. Wall Signs (`WallSignBlock.FACING`) & Wall Hanging Signs (`WallHangingSignBlock.FACING`)
Vektor normal diturunkan langsung dari offset langkah `Direction` blok:
$$N_x = \text{facing.getStepX()}, \quad N_z = \text{facing.getStepZ()}$$

### 2. Standing Signs (`StandingSignBlock.ROTATION`) & Ceiling Hanging Signs (`CeilingHangingSignBlock.ROTATION`)
Rotasi direpresentasikan sebagai bilangan bulat $0 \dots 15$. Sudut $\theta$ dalam radian dihitung sebagai berikut:
$$\theta = \left(\frac{\text{rotation} \times 360^\circ}{16}\right) \times \frac{\pi}{180}$$
$$N_x = -\sin\theta, \quad N_z = \cos\theta$$

### 3. Penentuan Visibilitas
Perkalian titik $D$ mengevaluasi sudut pengamatan:
$$D = N_x V_x + N_z V_z$$

* **Evaluasi Teks Depan**: Di-cull saat $D < -0.05$ (Kamera berada di belakang permukaan papan tanda).
* **Evaluasi Teks Belakang**: Di-cull saat $D > 0.05$ (Kamera berada di depan permukaan papan tanda).

---

## ⚡ Fast-Pass Sisi Kosong

Permukaan kosong langsung diabaikan seketika tanpa menghitung perkalian titik:
```java
public static boolean isTextEmpty(SignText text) {
    if (text == null) return true;
    for (int i = 0; i < 4; i++) {
        Component msg = text.getMessage(i, false);
        if (msg != null && !msg.getString().trim().isEmpty()) {
            return false;
        }
    }
    return true;
}
```
Permukaan kosong diatur ke null seketika tanpa menjalankan trigonometri atau perkalian titik vektor.

---

## 🔗 Halaman Terkait

- [[Culling Entitas Blok|id_id-26.2-Block-Entity-Culling]]
- [[Perintah & Konfigurasi|id_id-26.2-Commands-and-Configuration]]
- [[Arsitektur & Mixin|id_id-26.2-Architecture-and-Mixins]]
- [[Kembali ke Ikhtisar MC 26.2|id_id-26.2-Home]]
