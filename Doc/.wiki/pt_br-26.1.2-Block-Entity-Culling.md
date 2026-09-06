# 📦 Oclusão de Entidades de Bloco (Minecraft 26.1.2)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Entidades de bloco (baús, baús de ender, placas, estandartes, crânios, potes decorados, sinos e sinalizadores) enviam chamadas de desenho individuais a cada quadro. Em grandes salas de armazenamento ou instalações de triagem automatizadas, isso produz forte contenção de draw calls na GPU.

---

## 📋 Informações Rápidas de Oclusão de Entidades de Bloco

| Propriedade | Valor |
| :--- | :--- |
| **Pipeline Alvo** | `BlockEntityRenderDispatcher.tryExtractRenderState(BlockEntity, float, CrumblingOverlay)` (3 argumentos) |
| **Detecção de Encapsulamento** | Verifica todas as 6 faces vizinhas: `up`, `down`, `north`, `south`, `east`, `west` |
| **Modo Conservador** | Verificação apenas de encapsulamento no perfil `LOW` |
| **Modo Agressivo** | Verificação completa de linha de visão por raycast nos perfis `MEDIUM`, `HIGH`, `SUPER` |
| **Resultado** | Retorna RenderState `null` para ignorar o envio da renderização |

---

## 🔍 Arquitetura de Verificação de Encapsulamento e Linha de Visão

```text
               [UP]
                │
   [WEST] ── [CHEST] ── [EAST]
                │
              [DOWN]
```

### 1. Passagem Rápida de Encapsulamento Sólido de 6 Lados
Antes de executar qualquer matemática de raycast, o Camera Culling consulta os estados dos blocos vizinhos:
```java
BlockState up = level.getBlockState(pos.above());
BlockState down = level.getBlockState(pos.below());
BlockState north = level.getBlockState(pos.north());
BlockState south = level.getBlockState(pos.south());
BlockState east = level.getBlockState(pos.east());
BlockState west = level.getBlockState(pos.west());

if (up.isSolidRender() && down.isSolidRender() && north.isSolidRender()
    && south.isSolidRender() && east.isSolidRender() && west.isSolidRender()) {
    return true; // 100% Ocluído — ignorar renderização
}
```
Baús embutidos atrás de paredes ou totalmente encapsulados em fundações sólidas de subsolos deixam de ser renderizados com processamento quase nulo de CPU ($< 0.0001\mu\text{s}$).

### 2. Verificação de Linha de Visão por Raycast
Nos perfis `MEDIUM`, `HIGH` e `SUPER` (`cullAllBlockEntities = true`), o Camera Culling projeta um raio da posição da câmera do jogador até o centro da entidade de bloco $(X + 0.5, Y + 0.5, Z + 0.5)$:
* Se o raycast colidir com um bloco sólido oclusor antes de alcançar a entidade de bloco alvo, o estado de renderização é descartado.
* Se existir uma linha de visão desobstruída, a entidade de bloco é renderizada com total fidelidade visual.

---

## 🔗 Páginas Relacionadas

- [[Oclusão de Texto de Placas|pt_br-26.1.2-Sign-and-Hanging-Sign-Culling]]
- [[Oclusão de Entidades|pt_br-26.1.2-Entity-Occlusion-Culling]]
- [[Arquitetura e Mixins|pt_br-26.1.2-Architecture-and-Mixins]]
- [[Voltar à Visão Geral do MC 26.1.2|pt_br-26.1.2-Home]]
