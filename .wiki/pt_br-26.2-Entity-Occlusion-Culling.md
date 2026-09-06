# 🧱 Oclusão de Entidades (Minecraft 26.2)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

No Minecraft 26.2, a renderização de entidades do cliente extrai estados de renderização para todas as entidades dentro do frustum da câmera — mesmo quando obscurecidas atrás de cavernas, penhascos ou construções. O **Camera Culling** intercepta essa verificação para evitar que mobs ocluídos consumam processamento de geometria na CPU e chamadas de desenho na GPU.

---

## 📋 Informações Rápidas de Oclusão de Entidades

| Propriedade | Valor |
| :--- | :--- |
| **Pipeline Alvo** | `EntityRenderer.shouldRender(T, Frustum, double, double, double)` |
| **Perfil Padrão** | `SUPER` (Extremo) |
| **Contexto de Recorte** | `ClipContext.Block.COLLIDER`, `ClipContext.Fluid.NONE` |
| **Filtragem de Chão** | Ignora impactos `Direction.UP` quando altura do impacto $\le Y + 0.15\text{m}$ |
| **Oclusão por Folhas** | Blocos de renderização sólida e blocos `BlockTags.LEAVES` ocluem as linhas de visão |
| **Bolha de Imunidade** | Distância ao quadrado $< \text{minDistanceSq}$ |

---

## 🔬 Amostragem Anatômica Multiponto de Linha de Visão

Em vez de um raycast simplista de ponto único que faz os mobs piscarem nas esquinas, o Camera Culling realiza amostragem anatômica multiponto com base no [[Perfil de Culling|pt_br-26.2-Commands-and-Configuration]] selecionado:

```text
       [1] Head Top (maxY - 0.05)
          \
           [2] Eye Position (entity.getEyeY())
            \
             [3] Upper Torso (minY + height * 0.70)
              \
               [4] Center of Mass (centerY)
                \
        [5-8] Elevated Perimeter Flanks (width/depth checks)
```

1. **Amostra 1: Topo da Cabeça da Entidade (`maxY - 0.05`)**
   - Verificação de alta prioridade. Detecta entidades altas espiando por cima de barricadas baixas ou cercas.
2. **Amostra 2: Posição Anatômica dos Olhos (`getEyeY()`)**
   - Linha de visão direta da câmera até os olhos do mob.
3. **Amostra 3: Tronco Superior / Peito (`minY + height * 0.70`)**
   - Avalia linhas de visão da parte superior do corpo com segurança acima do nível do solo.
4. **Amostra 4: Centro de Massa (`(minY + maxY) * 0.5`)**
   - Teste geométrico geral do ponto médio.
5. **Amostras 5–8: Flancos Elevados do Perímetro**
   - Avalia $(X_{\min} + 0.15, Z_{\min} + 0.15)$, $(X_{\max} - 0.15, Z_{\min} + 0.15)$, etc. Garante que chefes largos (ex.: Devastadores, Wardens, Aranhas) permaneçam visíveis quando seus ombros surgirem de trás de quinas.

---

## 🛡️ Filtragem Direcional de Chão e Encostas

Quando um jogador olha para baixo em direção a um mob situado em terreno irregular, um raycast padrão pode colidir com a superfície superior de um bloco próximo aos pés do mob, tratando incorretamente o chão como uma parede oclusora.

O Camera Culling incorpora a **Filtragem Direcional de Impacto no Chão**:
$$\text{Se } \text{hit.getDirection()} == \text{Direction.UP} \quad \text{e} \quad Y_{\text{hit}} \le Y_{\text{target}} + 0.15\text{m} \implies \text{Linha de Visão Válida (Não Bloqueada)}$$

Isso assegura que mobs atravessando colinas, escadas e terrenos acidentados nunca sejam ocluídos erroneamente.

---

## ⚡ Motor de Raycast sem Alocação

Em versões anteriores, a avaliação de 8 pontos de amostra em 100 entidades gerava mais de 180.000 alocações de `new Vec3()` na heap por segundo, resultando em pausas de coleta de lixo (GC) Young-Gen da JVM.

No 26.2, o `CullingRaycastHelper` passa diretamente as coordenadas primitivas:
```java
public static boolean hasLineOfSight(Level level, Vec3 from, double toX, double toY, double toZ)
```
Essa arquitetura elimina completamente alocações intermediárias na heap a cada quadro.

---

## 🔗 Páginas Relacionadas

- [[Defesa de Sobredesenho de Multidões|pt_br-26.2-Mob-Crowd-Overdraw-Defense]]
- [[Histerese Temporal e Alocação Zero|pt_br-26.2-Temporal-Hysteresis-and-Zero-Allocation]]
- [[Imunidade de Chefes e Lista Negra|pt_br-26.2-Boss-and-Blacklist-Immunity]]
- [[Voltar à Visão Geral do MC 26.2|pt_br-26.2-Home]]
