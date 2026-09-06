# 🖥️ Configuração Gráfica GUI (YACL) (Minecraft 26.3)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

O Camera Culling oferece suporte opcional a uma tela moderna de configuração gráfica alimentada pelo **YetAnotherConfigLib (YACL v3)** e pelo **ModMenu**.

---

## 📋 Informações Rápidas de Integração da GUI

| Propriedade | Valor |
| :--- | :--- |
| **Motores de GUI Suportados** | YetAnotherConfigLib v3 (YACL) + ModMenu |
| **Padrão de Integração** | Carregamento diferido de classes (`ConfigScreenFactory`) |
| **Segurança contra Falhas no Servidor** | 100% Seguro — zero referências a classes de GUI de cliente nos entrypoints do servidor |
| **Categorias do Menu** | 3 Categorias Dedicadas com Guias (Tabs) |

---

## 🗂️ Estrutura das Categorias da GUI

```text
Camera Culling Settings Screen
├── 1. Engine & Diagnostics
│   ├── Master Enable (Caixa de Seleção)
│   ├── Culling Level (Menu Suspenso: LOW, MEDIUM, HIGH, SUPER)
│   └── Real-Time Debug Logging (Caixa de Seleção)
│
├── 2. Entity & Crowd Occlusion
│   ├── Crowd Overdraw Culling (Caixa de Seleção)
│   ├── Max Cluster Entities Cap (Controle Deslizante: 1 a 32)
│   ├── Boss & Mini-Boss Immunity (Caixa de Seleção)
│   ├── Major Boss Health Threshold (Campo Numérico, padrão: 150.0 HP)
│   └── Mini-Boss Health Threshold (Campo Numérico, padrão: 50.0 HP)
│
└── 3. Blocks, Particles & Animations
    ├── Particle Culling (Caixa de Seleção)
    ├── Block & Texture Animation Culling (Caixa de Seleção)
    ├── 2-Sided Sign Text Culling (Caixa de Seleção)
    ├── Distance Texture LOD (Caixa de Seleção)
    ├── Distance Texture LOD Start Distance (Controle Deslizante: 8m a 64m)
    └── Distance Texture LOD Far Distance (Controle Deslizante: 16m a 128m)
```

---

## 🛡️ Carregamento Diferido de Classes e Segurança contra Falhas

Para garantir que o Camera Culling nunca cause erros em servidores dedicados ou em instalações sem o YACL presente, a classe `ModMenuIntegration` implementa o carregamento diferido:

```java
public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        if (FabricLoader.getInstance().isModLoaded("yet_another_config_lib_v3")) {
            return YaclScreenHelper.createFactory();
        }
        return parent -> null;
    }
}
```

Se o YACL não estiver instalado, o jogo inicializa perfeitamente e os jogadores podem ajustar todas as opções através dos [[Comandos e Configuração|pt_br-26.3-Commands-and-Configuration]] ou editando `config/camera-culling.json`.

---

## 🔗 Páginas Relacionadas

- [[Comandos e Configuração|pt_br-26.3-Commands-and-Configuration]]
- [[Logs de Depuração e Diagnósticos|pt_br-26.3-Debug-Logging-and-Diagnostics]]
- [[Voltar à Visão Geral do MC 26.3|pt_br-26.3-Home]]
