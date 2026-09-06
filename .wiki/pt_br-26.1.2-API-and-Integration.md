# 🔌 API e Integração de Mods (Minecraft 26.1.2)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

O Camera Culling foi projetado para operar perfeitamente ao lado de mods de renderização do cliente (como Sodium, Iris, Canvas) e mods de conteúdo que adicionam entidades personalizadas ou entidades de bloco.

---

## 🤝 Compatibilidade com Motores de Renderização de Terceiros

### 1. Sodium & Embeddium
* **Chunks Estáticos de Terreno**: O Sodium otimiza a malha de chunks 16x16 e o pipeline de renderização de faces de blocos estáticos.
* **Entidades Dinâmicas**: O Camera Culling otimiza entidades dinâmicas, baús, placas e partículas.
* **Compatibilidade**: 100% Compatível, com zero sobreposição de Mixins ou conflitos de estado.

### 2. Iris & Shaders
* **Uniforms de Shaders**: Shaders executam pós-processamento no frame buffer extraído.
* **Economia por Oclusão**: Como as entidades ocluídas são impedidas de enviar geometria para o G-Buffer, os shaders operam com taxas de quadros significativamente superiores em áreas densas.

---

## 🛠️ Hooks da API Java Programática

Outros mods podem consultar ou integrar-se ao Camera Culling através de fachadas estáticas de utilitários:

### 1. Consultando o Estado do Motor de Culling
```java
import net.vanillaoutsider.culling.CameraCullingClient;

boolean isEnabled = CameraCullingClient.isCullingEnabled();
long culledMobs = CameraCullingClient.getCulledEntitiesCount();
long renderedMobs = CameraCullingClient.getRenderedEntitiesCount();
```

### 2. Imunidade Programática via Lista Negra
```java
import net.vanillaoutsider.culling.config.CameraCullingConfig;

// Adiciona o ID de uma entidade personalizada à lista de permissões do culling
CameraCullingConfig.addClientBlacklist("mymod:custom_companion");
```

### 3. Verificação Direta de Linha de Visão por Raycast
```java
import net.vanillaoutsider.culling.util.CullingRaycastHelper;

// Executa um teste de linha de visão sem alocações
boolean visible = CullingRaycastHelper.hasLineOfSight(level, camPos, targetX, targetY, targetZ);
```

---

## 🔗 Páginas Relacionadas

- [[Arquitetura e Mixins|pt_br-26.1.2-Architecture-and-Mixins]]
- [[Configuração de Desenvolvedor e Compilação|pt_br-26.1.2-Developer-Setup-and-Building]]
- [[Voltar à Visão Geral do MC 26.1.2|pt_br-26.1.2-Home]]
