# 👑 Imunidade de Chefes e Lista Negra (Minecraft 26.3)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Para garantir a justiça do jogo e a consciência de combate, ameaças críticas e animais de estimação nunca devem desaparecer atrás de paredes ou ser afetados por algoritmos agressivos de culling.

O **Camera Culling** incorpora identificação dinâmica de chefes e uma lista negra de imunidade em dois níveis.

---

## 📋 Informações Rápidas de Imunidade

| Propriedade | Valor |
| :--- | :--- |
| **Limiar de Vida de Chefe Maior** | `bossHealthThreshold` (Padrão: `150.0 HP` / 75 corações) |
| **Limiar de Vida de Mini-Chefe** | `miniBossHealthThreshold` (Padrão: `50.0 HP` / 25 corações) |
| **Caminho da Lista Negra do Cliente** | `config/camera-culling.json` (array `clientBlacklist`) |
| **Caminho da Lista Negra do Servidor** | `config/camera-culling-server.json` (array `serverBlacklist`) |
| **Escopo da Imunidade** | Isento de Oclusão por Bloco, Sobredesenho de Multidão e LOD de Textura |

---

## 🐲 Detecção Dinâmica de Chefes e Mini-Chefes

O Camera Culling avalia a imunidade de chefes através de dois mecanismos distintos:

### 1. Limiares Dinâmicos de Vida
Qualquer `LivingEntity` cujo `getMaxHealth()` atinja ou exceda os limiares configurados recebe imunidade incondicional:
* `maxHealth >= 150.0` $\implies$ Chefe Principal (Dragão do Fim, Wither, Warden).
* `maxHealth >= 50.0` $\implies$ Mini-Chefe (Guardião-Mestre, Devastador, Golem de Ferro, Piglin Bruto, Breeze, campeões de mods).

### 2. Heurística de Palavras-Chave de Identificadores e Registro
Entidades com nomes correspondentes a qualquer uma das seguintes substrings são identificadas automaticamente como chefes:
```text
dragon, wither, warden, elder_guardian, ravager, evoker, iron_golem, 
piglin_brute, breeze, boss, miniboss, mini_boss, titan, leviathan, 
harbinger, monarch, behemoth, champion, elite, brute
```

---

## 🛡️ Sistema de Lista Negra de Imunidade em Dois Níveis

```text
Resolução de Imunidade
├── Jogador Local / Veículo / Montaria ──► 100% Renderizado
├── Efeito Brilhante (Glowing) Ativo ────► 100% Renderizado
├── Chefe ou Mini-Chefe Detectado ───────► 100% Renderizado
├── Correspondência na Lista do Cliente ─► 100% Renderizado
└── Correspondência na Lista do Servidor ─► 100% Renderizado
```

### 1. Lista Negra Pessoal do Cliente
Os jogadores podem adicionar entidades companheiras locais à lista de permissões usando comandos no jogo:
```sql
/cameraculling blacklist add minecraft:wolf
/cameraculling blacklist add minecraft:allay
/cameraculling blacklist add minecraft:cat
```

### 2. Lista Negra de Administrador do Servidor
Operadores de servidor podem especificar imunidades globais de entidades em `config/camera-culling-server.json` ou via `/cameraculling serverblacklist add <id>`. Todos os clientes conectados respeitarão automaticamente a lista de imunidade imposta pelo servidor.

---

## 🔗 Páginas Relacionadas

- [[Oclusão de Entidades|pt_br-26.3-Entity-Occlusion-Culling]]
- [[LOD de Textura por Distância|pt_br-26.3-Distance-Texture-LOD]]
- [[Comandos e Configuração|pt_br-26.3-Commands-and-Configuration]]
- [[Voltar à Visão Geral do MC 26.3|pt_br-26.3-Home]]
