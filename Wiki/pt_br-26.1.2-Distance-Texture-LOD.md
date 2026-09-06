# 🎨 LOD de Textura por Distância (Minecraft 26.1.2)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Renderizar texturas completas de 1024x1024 ou de alta resolução em mobs distantes que ocupam apenas 4x4 pixels na tela do jogador desperdiça largura de banda substancial de VRAM da GPU e linhas de cache do amostrador de texturas.

O **Camera Culling** inclui um **Motor de Viés de LOD de Textura OpenGL** desacoplado de 3 níveis que ajusta dinamicamente a amostragem de mipmap de texturas com base na distância da entidade.

---

## 📋 Informações Rápidas de LOD de Textura

| Propriedade | Valor |
| :--- | :--- |
| **Hook do Pipeline** | `LivingEntityRenderer.submit(...)` `@At("HEAD")` & `@At("RETURN")` |
| **Parâmetro OpenGL** | `GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, bias)` |
| **Limiar Próximo** | $< 16.0$ blocos $\implies 0.0\text{f}$ de Viés (Resolução Total) |
| **Limiar Médio** | $16.0 - 32.0$ blocos $\implies 1.0\text{f}$ de Viés (Meia Resolução) |
| **Limiar Longe** | $> 32.0$ blocos $\implies 2.5\text{f}$ de Viés (Quarta Resolução / Mipmap) |
| **Isenções** | Entidades brilhantes, jogador local, Chefes e Mini-Chefes, Mobs na Lista Negra |

---

## 🔬 Viés Matemático de LOD de Distância

```text
Câmera
  │
  ├── [ 0m a 16m ] ──► Viés 0.0f  (Textura Nativa com Resolução Total 100%)
  │
  ├── [ 16m a 32m ] ──► Viés 1.0f  (Amostragem de Mipmap com Meia Resolução 50%)
  │
  └── [ > 32m ] ──────► Viés 2.5f  (Amostragem de Mipmap com Baixa Resolução 25%)
```

O viés de LOD $B$ é calculado puramente a partir da distância ao quadrado:
$$B(d) = \begin{cases} 
0.0\text{f} & \text{se } d^2 < \text{startDist}^2 \ (16.0^2) \\
1.0\text{f} & \text{se } \text{startDist}^2 \le d^2 < \text{farDist}^2 \ (32.0^2) \\
2.5\text{f} & \text{se } d^2 \ge \text{farDist}^2
\end{cases}$$

### Isolamento de Estado OpenGL
Ao renderizar uma entidade viva, o `LivingEntityRendererMixin` injeta em `submit:HEAD` para aplicar o viés calculado, e o redefine imediatamente para `0.0f` em `submit:RETURN`. Isso garante que modelos de blocos, itens e elementos de interface nunca sejam afetados.

---

## 🔗 Páginas Relacionadas

- [[Imunidade de Chefes e Lista Negra|pt_br-26.1.2-Boss-and-Blacklist-Immunity]]
- [[Configuração Gráfica GUI (YACL)|pt_br-26.1.2-GUI-Configuration]]
- [[Voltar à Visão Geral do MC 26.1.2|pt_br-26.1.2-Home]]
