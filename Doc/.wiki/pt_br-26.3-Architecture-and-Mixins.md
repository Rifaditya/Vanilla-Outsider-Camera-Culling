# 🏛️ Arquitetura e Mixins (Minecraft 26.3)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

O Camera Culling foi projetado com uma arquitetura estrita de **"1 Arquivo, 1 Propósito"** e ganchos Mixin de sobrecarga zero projetados especificamente para o motor de renderização desofuscado do **Minecraft 26.3**.

---

## 📋 Tabela de Alvos de Injeção Mixin

| Classe Mixin | Classe Alvo do Minecraft | Método Alvo e Ponto de Injeção | Função |
| :--- | :--- | :--- | :--- |
| `EntityRendererMixin` | `net.minecraft.client.renderer.entity.EntityRenderer` | `shouldRender` `@At("HEAD")` | Culling de oclusão multiponto por raycast e verificações de densidade de multidões |
| `BlockEntityRenderDispatcherMixin` | `net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher` | `tryExtractRenderState` `@At("HEAD")` | Oclusão de entidades de bloco por encapsulamento de 6 lados e raycast |
| `BlockEntityRenderDispatcherMixin` | `net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher` | `tryExtractRenderState` `@At("RETURN")` | Oclusão de face traseira e texto em branco em placas de 2 lados |
| `LivingEntityRendererMixin` | `net.minecraft.client.renderer.entity.LivingEntityRenderer` | `submit` `@At("HEAD")` & `@At("RETURN")` | Aplica e redefine o viés de mipmap de LOD de textura OpenGL (`GL_TEXTURE_LOD_BIAS`) |
| `SingleQuadParticleMixin` | `net.minecraft.client.particle.SingleQuadParticle` | `extract` `@At("HEAD")` | Culling de oclusão de QuadParticle contra geometria sólida |
| `TextureAtlasMixin` | `net.minecraft.client.renderer.texture.TextureAtlas` | `cycleAnimationFrames` `@At("HEAD")` | Suprime o upload de texturas animadas fora da tela |

---

## 🌳 Hierarquia de Pacotes

```text
net.vanillaoutsider.culling
├── CameraCullingClient.java               (ClientModInitializer & contadores de estatísticas)
├── ModVersionGuard.java                   (Guarda de integridade de versão no classloader Knot)
│
├── command
│   └── CameraCullingCommand.java          (Árvore de sintaxe Brigadier FabricClientCommandSource)
│
├── config
│   ├── CameraCullingConfig.java           (Serialização JSON para configs de cliente e servidor)
│   ├── CullingLevel.java                  (Perfis de intensidade LOW, MEDIUM, HIGH, SUPER)
│   ├── ModMenuIntegration.java            (Entrypoint da API ModMenu com fábrica YACL diferida)
│   └── YaclScreenHelper.java              (Construtor de telas YetAnotherConfigLib v3)
│
├── mixin
│   ├── BlockEntityRenderDispatcherMixin.java
│   ├── EntityRendererMixin.java
│   ├── LivingEntityRendererMixin.java
│   ├── SingleQuadParticleMixin.java
│   └── TextureAtlasMixin.java
│
└── util
    ├── AnimationCullingHelper.java        (Pausa o upload de atlas quando pausado/em menus)
    ├── BlacklistHelper.java               (Avaliação de lista negra de entidades no cliente e servidor)
    ├── BossDetectionHelper.java           (Limiares dinâmicos de HP e heurísticas de nomes)
    ├── CullingDiagnosticsHelper.java      (Rastreamento de transições de estado no chat e logs em tempo real)
    ├── CullingRaycastHelper.java          (Raycasting primitivo sem alocação e histerese)
    ├── ParticleCullingHelper.java         (Bolha de proximidade de 4m e raycast de recorte visual)
    ├── SignTextCullingHelper.java         (Produtos escalares de normais para placas de 2 lados)
    └── TextureLodHelper.java              (Cálculo de viés de LOD de mipmap OpenGL em 3 níveis)
```

---

## 🔗 Páginas Relacionadas

- [[Configuração de Desenvolvedor e Compilação|pt_br-26.3-Developer-Setup-and-Building]]
- [[API e Integração de Mods|pt_br-26.3-API-and-Integration]]
- [[Voltar à Visão Geral do MC 26.3|pt_br-26.3-Home]]
