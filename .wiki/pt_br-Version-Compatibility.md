# 🌐 Matriz de Compatibilidade e Ciclo de Vida de Versões

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Este documento descreve a matriz de lançamentos multi-era ativa, limites de dependências, especificações de runtime Java e variações da API de bytecode para o **Camera Culling**.

> 📌 **Aviso de Isenção de Responsabilidade do Código-Fonte**: A documentação nesta Wiki reflete o **estado atual do código-fonte no repositório**, o qual pode incluir commits recentes ainda não lançados ou recursos de desenvolvimento anteriores às compilações de lançamento público no CurseForge e Modrinth.

---

## 📋 Matriz de Ciclo de Vida Multi-Versão

| Era Âncora do Minecraft | Versão Alvo do MC | Versão de Lançamento Atual | Requisito de Java | Limite do Fabric Loader | Limite da Fabric API | Status de Lançamento |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| **Modern Sovereign (26.3)** | `26.3` | `1.10.1+26.3` | Java 25 (`release = 25`) | `>=0.18.4` | `0.156.1+26.3` | 🟢 Lançamento Ativo |
| **Modern Sovereign (26.2)** | `26.2` | `1.10.1+26.2` | Java 25 (`release = 25`) | `>=0.18.4` | `0.145.4+26.2` | 🟢 Lançamento Ativo |
| **Modern Sovereign (26.1.2)** | `26.1.2` | `1.10.2+26.1.2` | Java 25 (`release = 25`) | `>=0.18.4` | `0.145.4+26.1.2` | 🟢 Lançamento Ativo |

---

## 🛠️ Diferenças da API de Bytecode Entre as Âncoras

### 1. Pipeline de Extração do RenderState de Entidades de Bloco
* **Minecraft 26.1.2**:
  - `BlockEntityRenderDispatcher.tryExtractRenderState(BlockEntity, float, ModelFeatureRenderer$CrumblingOverlay)` — aceita **3 argumentos**.
* **Minecraft 26.2 & 26.3**:
  - `BlockEntityRenderDispatcher.tryExtractRenderState(BlockEntity, float, ModelFeatureRenderer$CrumblingOverlay, boolean isGloballyRendered)` — aceita **4 argumentos**.

### 2. API de Recuperação de Dados de Texto em Placas
* **Minecraft 26.1.2 & 26.2**:
  - `SignBlockEntity.getFrontText()` e `SignBlockEntity.getBackText()` recuperam `SignText`.
  - `SignText.getMessage(int index, boolean filtered)` recupera o `Component` da linha.
* **Minecraft 26.3**:
  - `SignBlockEntity.getText(SignTextSlot.FRONT)` e `SignBlockEntity.getText(SignTextSlot.BACK)` recuperam `SignText`.
  - `SignText.getMessages(boolean filtered)` recupera a matriz de `Component` das linhas.

---

## 📦 Locais de Arquivamento de Artefatos de Compilação

Todas as compilações de lançamento são automaticamente compiladas e preservadas na estrutura de arquivos centralizada do repositório pai:

```text
Archive Jar of all versions/
├── MC 26.1.2/
│   ├── vanilla-outsider-camera-culling-1.10.1+26.1.2.jar
│   └── vanilla-outsider-camera-culling-1.10.1+26.1.2-sources.jar
├── MC 26.2/
│   ├── vanilla-outsider-camera-culling-1.10.0+26.2.jar
│   └── vanilla-outsider-camera-culling-1.10.0+26.2-sources.jar
└── MC 26.3/
    ├── vanilla-outsider-camera-culling-1.10.0+26.3.jar
    └── vanilla-outsider-camera-culling-1.10.0+26.3-sources.jar
```

---

## 🔗 Links Rápidos

- [[👉 Entrar na Wiki do Minecraft 26.3|pt_br-26.3-Home]]
- [[👉 Entrar na Wiki do Minecraft 26.2|pt_br-26.2-Home]]
- [[👉 Entrar na Wiki do Minecraft 26.1.2|pt_br-26.1.2-Home]]
- [[Voltar ao Portal|pt_br-Home]]
