# 🪧 Oclusão de Texto em Placas de Dois Lados (Minecraft 26.1.2)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

No Minecraft 26.1.2, as placas apresentam renderização de texto em ambos os lados (`getFrontText()` e `getBackText()`). O motor de renderização de fontes do jogo desenha quads de glifos, cores e contornos brilhantes em ambos os lados simultaneamente.

O **Camera Culling** elimina passagens de renderização de texto desnecessárias por meio de produtos escalares de vetores normais ($\vec{N} \cdot \vec{V}$) e passagens rápidas de texto vazio.

---

## 📋 Informações Rápidas de Oclusão de Placas

| Propriedade | Valor |
| :--- | :--- |
| **Pipeline Alvo** | `BlockEntityRenderDispatcher.tryExtractRenderState` `@At("RETURN")` |
| **Hook da API** | `signState.frontText = null;` / `signState.backText = null;` |
| **Tipos Suportados** | Placas de parede, placas de chão, placas suspensas de parede, placas suspensas de teto |
| **Passagem Rápida Vazia** | Pula automaticamente faces em branco com zero caracteres de texto |
| **Margem do Produto Escalar** | Tolerância de $\pm 0.05$ evita pop-in em ângulos de visão rasantes |

---

## 📐 Matemática do Produto Escalar Normal do Vetor

Para determinar se a face frontal ou traseira de uma placa está apontada para a câmera, o Camera Culling calcula o produto escalar entre o vetor normal da face da placa $\vec{N} = (N_x, N_z)$ e o vetor do centro da placa até a câmera $\vec{V} = (V_x, V_z)$:

$$V_x = X_{\text{cam}} - (X_{\text{sign}} + 0.5), \quad V_z = Z_{\text{cam}} - (Z_{\text{sign}} + 0.5)$$

### 1. Placas de Parede e Placas Suspensas de Parede
O vetor normal é obtido diretamente dos deslocamentos de passo do `Direction` do bloco:
$$N_x = \text{facing.getStepX()}, \quad N_z = \text{facing.getStepZ()}$$

### 2. Placas de Chão e Placas Suspensas de Teto
A rotação é representada por um número inteiro $0 \dots 15$. O ângulo $\theta$ em radianos é calculado por:
$$\theta = \left(\frac{\text{rotation} \times 360^\circ}{16}\right) \times \frac{\pi}{180}$$
$$N_x = -\sin\theta, \quad N_z = \cos\theta$$

### 3. Determinação de Visibilidade
O produto escalar $D$ avalia o ângulo de observação:
$$D = N_x V_x + N_z V_z$$

* **Avaliação da Face Frontal**: Ocluída quando $D < -0.05$ (A câmera está atrás da face da placa).
* **Avaliação da Face Traseira**: Ocluída quando $D > 0.05$ (A câmera está à frente da face da placa).

---

## ⚡ Passagem Rápida de Lado Vazio

Se o jogador escreveu texto em apenas um dos lados da placa (ou colocou uma placa sem texto como decoração/barreira), o Camera Culling inspeciona as 4 linhas de texto:
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
Faces vazias são anuladas imediatamente sem a necessidade de calcular funções trigonométricas ou produtos escalares de vetores.

---

## 🔗 Páginas Relacionadas

- [[Oclusão de Entidades de Bloco|pt_br-26.1.2-Block-Entity-Culling]]
- [[Comandos e Configuração|pt_br-26.1.2-Commands-and-Configuration]]
- [[Arquitetura e Mixins|pt_br-26.1.2-Architecture-and-Mixins]]
- [[Voltar à Visão Geral do MC 26.1.2|pt_br-26.1.2-Home]]
