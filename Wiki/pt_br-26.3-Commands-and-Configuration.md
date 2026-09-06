# 🎮 Comandos e Configuração (Minecraft 26.3)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

O Camera Culling fornece um conjunto completo de comandos Brigadier no jogo (`/cameraculling`) e persistência limpa de configuração em JSON (`config/camera-culling.json`).

---

## 📋 Tabela de Referência de Comandos

```sql
/cameraculling status
/cameraculling toggle
/cameraculling set <low|medium|high|super>
/cameraculling particles [true|false]
/cameraculling animations [true|false]
/cameraculling crowdculling <true|false>
/cameraculling cluster <1-128>
/cameraculling texturlod <true|false>
/cameraculling texturlod range <start_dist> <far_dist>
/cameraculling bossimmunity <true|false>
/cameraculling bosshealth <boss_hp>
/cameraculling bosshealth <boss_hp> <miniboss_hp>
/cameraculling minibosshealth <miniboss_hp>
/cameraculling blacklist <add|remove|list|clear> <entity_id>
/cameraculling serverblacklist <add|remove|list|clear> <entity_id>
/cameraculling debug [true|false]
/cameraculling reload
```

---

## ⚙️ Detalhamento dos Comandos

| Sintaxe do Comando | Parâmetros | Função |
| :--- | :--- | :--- |
| `/cameraculling status` | Nenhum | Exibe estatísticas ao vivo, contadores de renderizados vs. ocluídos, perfil ativo e contagem de itens na lista negra. |
| `/cameraculling toggle` | Nenhum | Inverte o estado mestre de ativação do culling. |
| `/cameraculling set <perfil>` | `low`, `medium`, `high`, `super` | Altera o perfil de intensidade de culling ativo. |
| `/cameraculling particles [bool]` | `true`, `false` (opcional) | Alterna o culling de oclusão de partículas atrás de blocos sólidos. |
| `/cameraculling animations [bool]` | `true`, `false` (opcional) | Alterna o congelamento de animações de blocos e atlas de texturas. |
| `/cameraculling crowdculling <bool>` | `true`, `false` | Alterna o culling de sobreposição em multidões / entidades atrás de entidades. |
| `/cameraculling cluster <int>` | `1` a `128` | Define o número máximo de mobs renderizados por cluster de 1,5 bloco (padrão: `8`). |
| `/cameraculling texturlod <bool>` | `true`, `false` | Ativa ou desativa a escala de mipmap LOD de textura por distância. |
| `/cameraculling texturlod range <start> <far>` | `1.0..256.0`, `2.0..512.0` | Define os limiares de distância inicial e distante para o escalonamento de LOD (ex.: `16.0 32.0`). |
| `/cameraculling bossimmunity <bool>` | `true`, `false` | Ativa ou desativa a proteção de linha de visão de chefes e mini-chefes. |
| `/cameraculling bosshealth <hp>` | `1.0` a `10000.0` | Define o limiar de HP de chefes principais (ex.: `150.0`). |
| `/cameraculling minibosshealth <hp>` | `1.0` a `10000.0` | Define o limiar de HP de mini-chefes (ex.: `50.0`). |
| `/cameraculling blacklist add <id>` | ID da entidade | Adiciona uma entidade (ex.: `minecraft:wolf`) à imunidade pessoal do cliente. |
| `/cameraculling blacklist remove <id>` | ID da entidade | Remove uma entidade da imunidade pessoal do cliente. |
| `/cameraculling blacklist list` | Nenhum | Lista todas as entidades presentes na lista negra pessoal de imunidade. |
| `/cameraculling blacklist clear` | Nenhum | Limpa todas as entradas da lista negra pessoal de imunidade. |
| `/cameraculling serverblacklist ...` | Subcomando + ID | Configura a lista negra de imunidade imposta pelo servidor (Requer OP). |
| `/cameraculling debug [bool]` | `true`, `false` (opcional) | Alterna o rastreamento diagnóstico de transições de estado no chat e nos logs em tempo real. |
| `/cameraculling reload` | Nenhum | Recarrega os arquivos de configuração do disco. |

---

## 📄 Formato de Configuração JSON

Os arquivos de configuração ficam localizados na pasta `.minecraft/config/`:

### Configuração do Cliente (`config/camera-culling.json`)
```json
{
  "enabled": true,
  "level": "SUPER",
  "cullEntitiesBehindEntities": false,
  "maxEntitiesPerCluster": 8,
  "distanceTextureLod": true,
  "distanceTextureLodStart": 16.0,
  "distanceTextureLodFar": 32.0,
  "bossImmunity": true,
  "bossHealthThreshold": 150.0,
  "miniBossHealthThreshold": 50.0,
  "cullParticles": true,
  "cullAnimations": true,
  "cullSignText": true,
  "clientBlacklist": [
    "minecraft:wolf",
    "minecraft:allay"
  ],
  "debugMode": false
}
```

### Configuração do Servidor (`config/camera-culling-server.json`)
```json
{
  "serverBlacklist": []
}
```

---

## 🔗 Páginas Relacionadas

- [[Configuração Gráfica GUI (YACL)|pt_br-26.3-GUI-Configuration]]
- [[Logs de Depuração e Diagnósticos|pt_br-26.3-Debug-Logging-and-Diagnostics]]
- [[Voltar à Visão Geral do MC 26.3|pt_br-26.3-Home]]
