# ✨ Oclusão de Partículas e Animações (Minecraft 26.3)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

No Minecraft vanilla, gotas de lava subterrâneas, poeira de caverna, partículas de portais e tochas geram centenas de quads de partículas atrás de paredes sólidas de pedra que são submetidos à GPU. Simultaneamente, atlas de texturas de blocos animados enviam continuamente dados de quadros para a GPU, mesmo quando o jogador pausa no modo um jogador ou está em menus modais.

O **Camera Culling** introduz verificações de linha de visão de alta velocidade para partículas e supressão de upload de atlas de texturas.

---

## 📋 Informações Rápidas de Oclusão de Partículas e Animações

| Propriedade | Valor |
| :--- | :--- |
| **Alvo de Partículas** | `SingleQuadParticle.extract(QuadParticleRenderState, Camera, float)` |
| **Alvo de Animação** | `TextureAtlas.cycleAnimationFrames()` |
| **Segurança por Proximidade** | 4,0 metros ($16.0\text{m}^2$) ao redor da câmera |
| **Distância Máxima de Partículas** | 64,0 metros ($4096.0\text{m}^2$) |
| **Gatilho de Pausa do Atlas** | Jogo pausado no singleplayer OU sem nível/jogador ativo |

---

## 🌪️ Pipeline de Oclusão de Partículas

```text
Partícula Gerada em (X, Y, Z)
        │
        ▼
[1] Distância <= 4m? ──────────► RENDERIZAR (Bolha de Proximidade)
        │ Não
        ▼
[2] Distância > 64m? ──────────► OCLUIR (Corte Distante)
        │ Não
        ▼
[3] Dentro de Bloco Sólido? ───► OCLUIR (Encapsulada)
        │ Não
        ▼
[4] Raycast de Recorte Visual Bloqueado?
        ├── Sim ──────────────► OCLUIR (Ocluída)
        └── Não ──────────────► RENDERIZAR (Visível)
```

1. **Bolha de Segurança por Proximidade (4,0m)**: Partículas emitidas a menos de 4 metros da câmera (ex.: espirais de poções, poeira de corrida, golpes de ataque) são renderizadas incondicionalmente em $< 0.0001\mu\text{s}$.
2. **Corte por Distância Longa (64,0m)**: Partículas geradas além de 64 metros são ocluídas para proteger a taxa de preenchimento (fillrate) de quads da GPU.
3. **Encapsulamento em Bloco Sólido**: Se a coordenada do bloco `BlockPos.containing(x, y, z)` tiver `isSolidRender() == true`, a partícula é descartada imediatamente.
4. **Raycast de Recorte Visual**: Projeta um raio `ClipContext.Block.VISUAL` da posição da câmera até o vetor da partícula. Se um bloco sólido opaco interceptar a linha de visão com margem $> 0.35\text{m}$, a renderização é ignorada.

---

## 🎬 Congelamento de Animações de Blocos e Atlas de Texturas

Animações no atlas de texturas (lanternas do mar animadas, água/lava fluindo, prismarino, fogo, bússola, relógio) consomem largura de banda da GPU para alternar índices de quadros.

O Camera Culling verifica `AnimationCullingHelper.shouldPauseAtlasAnimation()` em `TextureAtlasMixin`:
* Se o jogo no singleplayer estiver pausado (`mc.isPaused() == true`), os uploads do atlas são congelados.
* Se estiver na tela inicial, em menus modais ou desconectado do mundo, a ciclagem de texturas em segundo plano é suspensa.

---

## 🔗 Páginas Relacionadas

- [[Oclusão de Entidades|pt_br-26.3-Entity-Occlusion-Culling]]
- [[Comandos e Configuração|pt_br-26.3-Commands-and-Configuration]]
- [[Voltar à Visão Geral do MC 26.3|pt_br-26.3-Home]]
