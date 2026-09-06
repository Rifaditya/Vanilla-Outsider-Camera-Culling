# ⏱️ Histerese Temporal e Alocação Zero (Minecraft 26.1.2)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

O culling de oclusão de alta velocidade pode introduzir duas falhas comuns de desempenho:
1. **Cintilação em Borda Rasante (Z-Fighting / Pop-in em Limites)**: Pequenas rotações de câmera ou a oscilação de passos ao caminhar (view-bobbing) cruzando a quina de um bloco podem fazer entidades piscarem rapidamente entre os estados renderizado e não renderizado em quadros alternados.
2. **Picos de Congelamento por Coleta de Lixo**: A alocação contínua de objetos `new Vec3()` durante o raycasting desencadeia frequentes pausas de coleta de lixo Young-Gen da JVM.

O **Camera Culling** resolve ambos os problemas com um **Buffer de Tolerância Adaptativo por Distância** e um **Motor de Caminho Crítico sem Alocação**.

---

## 📋 Informações Rápidas de Histerese e Alocação

| Propriedade | Valor |
| :--- | :--- |
| **Buffer de Tolerância de Distância Próxima** | $d \le 32\text{m} \implies 4\text{ quadros ocluídos consecutivos}$ |
| **Buffer de Tolerância de Distância Média** | $32\text{m} < d \le 64\text{m} \implies 8\text{ quadros ocluídos consecutivos}$ |
| **Buffer de Tolerância de Distância Longe** | $d > 64\text{m} \implies 12\text{ quadros ocluídos consecutivos}$ |
| **Transição de Visibilidade** | Instantânea ($0\text{ quadros de atraso}$) ao restabelecer a linha de visão |
| **Estrutura de Rastreamento** | `it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap` (Zero sobrecarga de autoboxing) |
| **Alocações por Quadro** | $0\text{ bytes}$ (Coordenadas primitivas double passadas diretamente) |

---

## 📐 Fórmula do Buffer de Tolerância por Distância

A sequência contínua de oclusão necessária antes de transicionar uma entidade para o estado `[CULLED]` é calculada por:

$$\text{RequiredStreak}(d) = \begin{cases}
12\text{ quadros} & \text{se } d^2 > 64.0^2 \ (4096.0\text{m}^2) \\
8\text{ quadros} & \text{se } d^2 > 32.0^2 \ (1024.0\text{m}^2) \\
4\text{ quadros} & \text{se } d^2 \le 32.0^2
\end{cases}$$

```text
[LINHA DE VISÃO DA ENTIDADE PERDIDA]
       │
       ├── Quadro 1 ocluído ──► RENDERIZAR (Decaimento da Tolerância)
       ├── Quadro 2 ocluído ──► RENDERIZAR (Decaimento da Tolerância)
       ├── Quadro 3 ocluído ──► RENDERIZAR (Decaimento da Tolerância)
       └── Quadro 4 ocluído ──► OCLUIR (Sequência Atingida)
```

* **Restauração Instantânea (Instant Unculling)**: Assim que uma única amostra de raycast restabelece uma linha de visão desobstruída, a sequência de oclusão é removida (`OCCLUDED_STREAK.remove(id)`), tornando a entidade visível imediatamente com $0\text{ quadros de latência}$.
* **Decaimento Assimétrico**: Ficar ocluído requer múltiplos quadros consecutivos; tornar-se visível leva apenas 1 quadro. Isso elimina todas as cintilações causadas pela rotação da câmera.

---

## ⚡ Arquitetura Primitiva sem Alocação

Em `CullingRaycastHelper.java`, as alocações intermediárias de vetores na heap foram completamente eliminadas:

```java
// Zero alocações na heap: coordenadas são passadas como doubles primitivos brutos
public static boolean hasLineOfSight(Level level, Vec3 from, double toX, double toY, double toZ) {
    Vec3 to = new Vec3(toX, toY, toZ);
    ClipContext ctx = new ClipContext(from, to, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, CollisionContext.empty());
    BlockHitResult hit = level.clip(ctx);
    if (hit.getType() == HitResult.Type.MISS) {
        return true;
    }
    // Avaliação de impacto no chão e tolerâncias com zero instanciação de objetos
    ...
}
```

---

## 🔗 Páginas Relacionadas

- [[Oclusão de Entidades|pt_br-26.1.2-Entity-Occlusion-Culling]]
- [[Logs de Depuração e Diagnósticos|pt_br-26.1.2-Debug-Logging-and-Diagnostics]]
- [[Arquitetura e Mixins|pt_br-26.1.2-Architecture-and-Mixins]]
- [[Voltar à Visão Geral do MC 26.1.2|pt_br-26.1.2-Home]]
