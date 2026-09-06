# 🟣 Camera Culling (Minecraft 26.1.2)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Bem-vindo ao hub de documentação do **Minecraft 26.1.2** para o **Camera Culling** (`v1.10.1+26.1.2`).

> 📌 **Aviso de Isenção de Responsabilidade do Código-Fonte**: A documentação nesta Wiki reflete o **estado atual do código-fonte no repositório**, o qual pode incluir commits recentes ainda não lançados ou recursos de desenvolvimento anteriores às compilações de lançamento público no CurseForge e Modrinth.

---

## 📋 Informações Rápidas do Minecraft 26.1.2

| Propriedade | Valor |
| :--- | :--- |
| **Versão Alvo do Minecraft** | `26.1.2` |
| **Versão de Lançamento** | `1.10.2+26.1.2` |
| **Requisito de Java** | Java 25 (`release = 25`) |
| **Fabric Loader** | `>=0.18.4` |
| **Fabric API** | `0.145.4+26.1.2` |
| **Licença** | GNU General Public License v3.0 (GPLv3) |
| **Caminho do Subprojeto** | `Camera Culling v26.1/Camera Culling 26.1` |

---

## ⚡ Matriz de Recursos Principais

```text
Camera Culling 26.1.2 Pipeline
├── Entity Occlusion (Zero-Allocation Raycast Engine)
├── Block Entity Occlusion (3-Arg tryExtractRenderState Hook)
├── 2-Sided Sign Text Culling (Normal Vector Dot Product Math)
├── Particle Occlusion (4m Proximity Safety + Visual Clip)
├── Animation Culling (TextureAtlas Upload Suppression)
├── Distance Texture LOD (3-Tier OpenGL Mipmap Bias)
├── Boss & Mini-Boss Immunity (Configurable HP Thresholds)
├── Anti-Flicker Temporal Hysteresis (Adaptive 4/8/12-Frame Buffer)
└── Graphical GUI (YACL v3 & ModMenu) + In-Game Commands
```

---

## 📚 Índice de Documentação do 26.1.2

1. [[Oclusão de Entidades|pt_br-26.1.2-Entity-Occlusion-Culling]] — Raycasting multiponto e filtragem de chão.
2. [[Oclusão de Entidades de Bloco|pt_br-26.1.2-Block-Entity-Culling]] — Verificações de encapsulamento para baús e entidades de bloco.
3. [[Oclusão de Texto de Placas|pt_br-26.1.2-Sign-and-Hanging-Sign-Culling]] — Matemática de produto escalar normal de 2 lados e passagem rápida de lado vazio.
4. [[Oclusão de Partículas e Animações|pt_br-26.1.2-Particle-and-Animation-Culling]] — Oclusão de partículas subterrâneas e congelamento de animações de atlas.
5. [[Defesa de Sobredesenho de Multidões|pt_br-26.1.2-Mob-Crowd-Overdraw-Defense]] — Limite de distância de 16m e limite de densidade de cluster de 1,5m.
6. [[LOD de Textura por Distância|pt_br-26.1.2-Distance-Texture-LOD]] — Viés de LOD de mipmap OpenGL em manadas distantes.
7. [[Imunidade de Chefes e Lista Negra|pt_br-26.1.2-Boss-and-Blacklist-Immunity]] — Proteção de chefes e lista negra de dois níveis.
8. [[Histerese Temporal e Alocação Zero|pt_br-26.1.2-Temporal-Hysteresis-and-Zero-Allocation]] — Buffer de tolerância e motor de alocação zero.
9. [[Comandos e Configuração|pt_br-26.1.2-Commands-and-Configuration]] — Referência completa da sintaxe de comandos Brigadier.
10. [[Configuração Gráfica GUI (YACL)|pt_br-26.1.2-GUI-Configuration]] — Guia do menu gráfico.
11. [[Logs de Depuração e Diagnósticos|pt_br-26.1.2-Debug-Logging-and-Diagnostics]] — Rastreamento de transições de estado no chat e nos logs em tempo real.
12. [[Arquitetura e Mixins|pt_br-26.1.2-Architecture-and-Mixins]] — Hierarquia de pacotes e tabela de alvos de Mixin de 3 argumentos.
13. [[Configuração de Desenvolvedor e Compilação|pt_br-26.1.2-Developer-Setup-and-Building]] — Configuração do JDK 25 e instruções de compilação Gradle Loom.
14. [[API e Integração de Mods|pt_br-26.1.2-API-and-Integration]] — Pontos de integração programática (hooks).

---

[[Voltar ao Portal de Versões|pt_br-Home]] &bull; [[Matriz de Compatibilidade e Ciclo de Vida|pt_br-Version-Compatibility]]
