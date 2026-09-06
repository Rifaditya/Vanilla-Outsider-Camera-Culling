# 🏛️ Arsitektur & Referensi Teknis Mixin (Minecraft 26.1.2)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Camera Culling dirancang dengan arsitektur ketat **"1 Berkas, 1 Tujuan"** dan hook Mixin tanpa overhead yang dirancang khusus untuk mesin rendering **Minecraft 26.1.2** non-obfuscated.

---

## 📋 Buku Besar Target Mixin

| Kelas Mixin | Kelas Target Minecraft | Metode Target & Titik Injeksi | Fungsi |
| :--- | :--- | :--- | :--- |
| `EntityRendererMixin` | `net.minecraft.client.renderer.entity.EntityRenderer` | `shouldRender` `@At("HEAD")` | Culling oklusi raycast multi-titik & pemeriksaan kepadatan kerumunan |
| `BlockEntityRenderDispatcherMixin` | `net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher` | `tryExtractRenderState(BlockEntity, float, CrumblingOverlay)` `@At("HEAD")` | Culling entitas blok melalui pengurungan 6 sisi & raycast (3 argumen) |
| `BlockEntityRenderDispatcherMixin` | `net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher` | `tryExtractRenderState(BlockEntity, float, CrumblingOverlay)` `@At("RETURN")` | Culling teks sisi belakang & teks kosong papan tanda 2 sisi (3 argumen) |
| `LivingEntityRendererMixin` | `net.minecraft.client.renderer.entity.LivingEntityRenderer` | `submit` `@At("HEAD")` & `@At("RETURN")` | Menerapkan & mereset bias mipmap LOD Tekstur OpenGL (`GL_TEXTURE_LOD_BIAS`) |
| `SingleQuadParticleMixin` | `net.minecraft.client.particle.SingleQuadParticle` | `extract` `@At("HEAD")` | Culling oklusi QuadParticle terhadap geometri solid |
| `TextureAtlasMixin` | `net.minecraft.client.renderer.texture.TextureAtlas` | `cycleAnimationFrames` `@At("HEAD")` | Menekan unggahan tekstur beranimasi di luar layar |

---

## 🌳 Hirarki Paket

```text
net.vanillaoutsider.culling
├── CameraCullingClient.java               (ClientModInitializer & penghitung statistik)
├── ModVersionGuard.java                   (Penjaga integritas versi classloader Knot)
│
├── command
│   └── CameraCullingCommand.java          (Pohon sintaksis Brigadier FabricClientCommandSource)
│
├── config
│   ├── CameraCullingConfig.java           (Serialisasi JSON untuk konfigurasi klien & server)
│   ├── CullingLevel.java                  (Profil intensitas LOW, MEDIUM, HIGH, SUPER)
│   ├── ModMenuIntegration.java            (Entrypoint ModMenu API dengan pabrik YACL tertunda)
│   └── YaclScreenHelper.java              (Pembangun layar YetAnotherConfigLib v3)
│
├── mixin
│   ├── BlockEntityRenderDispatcherMixin.java
│   ├── EntityRendererMixin.java
│   ├── LivingEntityRendererMixin.java
│   ├── SingleQuadParticleMixin.java
│   └── TextureAtlasMixin.java
│
└── util
    ├── AnimationCullingHelper.java        (Menjeda unggahan atlas saat game dijeda/di menu)
    ├── BlacklistHelper.java               (Evaluasi daftar hitam entitas klien & server)
    ├── BossDetectionHelper.java           (Ambang batas HP dinamis & heuristik nama)
    ├── CullingDiagnosticsHelper.java      (Pelacakan transisi status obrolan & log real-time)
    ├── CullingRaycastHelper.java          (Raycasting primitif tanpa alokasi & histeresis)
    ├── ParticleCullingHelper.java         (Gelembung proksimitas 4m & raycast klip visual)
    ├── SignTextCullingHelper.java         (Perkalian titik vektor normal untuk tanda 2 sisi)
    └── TextureLodHelper.java              (Kalkulasi bias LOD mipmap OpenGL 3 tingkat)
```

---

## 🔗 Halaman Terkait

- [[Pengaturan Pengembang & Kompilasi|id_id-26.1.2-Developer-Setup-and-Building]]
- [[API & Integrasi Mod|id_id-26.1.2-API-and-Integration]]
- [[Kembali ke Ikhtisar MC 26.1.2|id_id-26.1.2-Home]]
