# 📷 Wiki do Camera Culling

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Bem-vindo ao portal oficial de documentação do **Camera Culling**. O Camera Culling é um mod de otimização de renderização do lado do cliente de alto desempenho para Minecraft **26.1.2**, **26.2** e **26.3** desenvolvido sob a filosofia **Vanilla Outsider**.

> 📌 **Aviso de Isenção de Responsabilidade do Código-Fonte**: A documentação nesta Wiki reflete o **estado atual do código-fonte no repositório**, o qual pode incluir commits recentes ainda não lançados ou recursos de desenvolvimento anteriores às compilações de lançamento público no CurseForge e Modrinth.

---

## 🧭 Portal de Comutação Multi-Versão

O Camera Culling é desenvolvido sob a estrita lei de **1 JAR 1 Versão**. Selecione a sua versão alvo do Minecraft abaixo para entrar em sua árvore de documentação dedicada e isolada:

| Versão Alvo do Minecraft | Versão de Lançamento do Mod | Runtime Java | Ferramentas de Compilação | Portal Wiki Dedicado |
| :--- | :--- | :--- | :--- | :--- |
| **Minecraft 26.1.2** | `1.10.2+26.1.2` | Java 25+ | Fabric Loom 1.15.5 | [[👉 Entrar na Wiki do MC 26.1.2|pt_br-26.1.2-Home]] |
| **Minecraft 26.2** | `1.10.1+26.2` | Java 25+ | Fabric Loom 1.15.5 | [[👉 Entrar na Wiki do MC 26.2|pt_br-26.2-Home]] |
| **Minecraft 26.3** | `1.10.1+26.3` | Java 25+ | Fabric Loom 1.15.5 | [[👉 Entrar na Wiki do MC 26.3|pt_br-26.3-Home]] |

---

## ⚡ Matriz de Otimizações Principais

| Sistema de Otimização | Mecanismo Primário | Benefício de Desempenho |
| :--- | :--- | :--- |
| **Motor de Raycast sem Alocação** | Verificação de linha de visão com coordenadas primitivas | Elimina picos de pausa de GC Young-Gen da JVM durante rotações de câmera |
| **Histerese Temporal Anti-Cintilação** | Buffer de tolerância adaptativo de 4/8/12 quadros por distância | Elimina cintilações em bordas de blocos e oscilação de caminhada (view-bobbing) |
| **Oclusão de Texto em Placas de Dois Lados** | Produtos escalares de vetores normais das faces ($\vec{N} \cdot \vec{V}$) | Redução de 50% a 100% nas chamadas de desenho (draw calls) de texto de placas |
| **Oclusão de Partículas** | Bolha de segurança de 4m + raycasts de recorte visual | Deixa de renderizar quads de partículas subterrâneas e ocluídas |
| **Oclusão de Animações** | Supressão de upload de atlas de texturas | Congela animações de blocos 3D e uploads de texturas fora de tela |
| **Defesa de Sobredesenho de Multidões de Mobs** | Falha rápida em 16m + limite de densidade de 8 mobs / 1,5m | Elimina picos de lag em cercados de procriação e farms hiperdensas |
| **LOD de Textura por Distância** | Viés de mipmap OpenGL de 3 níveis ($0.0 \to 1.0 \to 2.5$) | Reduz drasticamente a taxa de preenchimento de VRAM em manadas distantes |
| **Imunidade de Chefes e Mini-Chefes** | Limiares de vida dinâmicos e heurística de nomes | Impede o desaparecimento visual de chefes e ameaças críticas |
| **Lista Negra de Imunidade de Dois Níveis** | JSON do cliente local + Sincronização do administrador do servidor | Lista de permissões personalizada para mobs companheiros |
| **Oclusão de Entidades de Bloco** | Detecção de encapsulamento sólido de 6 lados | Ignora a extração de renderização para baús e blocos embutidos |

---

## 📚 Navegação Global

- [[Matriz de Compatibilidade e Ciclo de Vida de Versões|pt_br-Version-Compatibility]]
- [[Árvore de Documentação do Minecraft 26.1.2|pt_br-26.1.2-Home]]
- [[Árvore de Documentação do Minecraft 26.2|pt_br-26.2-Home]]
- [[Árvore de Documentação do Minecraft 26.3|pt_br-26.3-Home]]

---

<p align="center">
  <em>Desenvolvido por <strong>Dasik (Rifaditya)</strong> | Licenciado sob <strong>GNU General Public License v3.0 (GPLv3)</strong></em>
</p>
