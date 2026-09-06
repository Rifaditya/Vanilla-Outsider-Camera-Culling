# 🎨 LOD Tekstur Mob Berbasis Jarak (Minecraft 26.2)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Merender tekstur beresolusi penuh 1024x1024 pada mob yang jauh dan hanya menempati 4x4 piksel di layar pemain menghabiskan bandwidth VRAM GPU dan jalur cache sampler tekstur yang signifikan.

**Camera Culling** menyertakan **Mesin Pembiasan LOD Tekstur OpenGL** 3 tingkat terpisah yang secara dinamis menyesuaikan pengambilan sampel mipmap tekstur berdasarkan jarak entitas.

---

## 📋 Informasi Singkat LOD Tekstur Jarak

| Properti | Nilai |
| :--- | :--- |
| **Hook Pipeline** | `LivingEntityRenderer.submit(...)` `@At("HEAD")` & `@At("RETURN")` |
| **Parameter OpenGL** | `GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, bias)` |
| **Ambang Batas Dekat** | $< 16.0$ blok $\implies 0.0\text{f}$ Bias (Resolusi Penuh Asli) |
| **Ambang Batas Sedang** | $16.0 - 32.0$ blok $\implies 1.0\text{f}$ Bias (Setengah Resolusi) |
| **Ambang Batas Jauh** | $> 32.0$ blok $\implies 2.5\text{f}$ Bias (Seperempat Resolusi / Mipmap) |
| **Pengecualian** | Entitas bersinar, pemain lokal, Bos & Mini-Bos, mob dalam daftar hitam |

---

## 🔬 Pembiasan LOD Tekstur Berjarak Matematis

```text
Kamera
  │
  ├── [ 0m hingga 16m ] ──► Bias 0.0f  (Tekstur Asli 100% Resolusi Penuh)
  │
  ├── [ 16m hingga 32m ] ─► Bias 1.0f  (Pengambilan Sampel Mipmap Resolusi 50%)
  │
  └── [ > 32m ] ──────────► Bias 2.5f  (Pengambilan Sampel Mipmap Rendah 25%)
```

Bias LOD $B$ dihitung murni dari kuadrat jarak:
$$B(d) = \begin{cases} 
0.0\text{f} & \text{jika } d^2 < \text{startDist}^2 \ (16.0^2) \\
1.0\text{f} & \text{jika } \text{startDist}^2 \le d^2 < \text{farDist}^2 \ (32.0^2) \\
2.5\text{f} & \text{jika } d^2 \ge \text{farDist}^2
\end{cases}$$

### Isolasi Status OpenGL
Saat merender entitas hidup, `LivingEntityRendererMixin` menginjeksi pada `submit:HEAD` untuk menerapkan bias yang dihitung, dan segera meresetnya kembali ke `0.0f` pada `submit:RETURN`. Ini menjamin bahwa model blok, item, dan elemen antarmuka (UI) tidak pernah terpengaruh.

---

## 🔗 Halaman Terkait

- [[Imunitas Bos & Daftar Hitam|id_id-26.2-Boss-and-Blacklist-Immunity]]
- [[Konfigurasi GUI Grafis (YACL)|id_id-26.2-GUI-Configuration]]
- [[Kembali ke Ikhtisar MC 26.2|id_id-26.2-Home]]
