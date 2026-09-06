# 🛠️ Configuração de Desenvolvedor e Compilação (Minecraft 26.3)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Este guia técnico aborda os pré-requisitos de ambiente, ferramentas do Gradle Loom e instruções de compilação para construir o **Camera Culling** no **Minecraft 26.3**.

---

## 📋 Pré-requisitos de Ambiente

* **Kit de Desenvolvimento Java (JDK)**: **JDK 25+** (ex.: Eclipse Adoptium Temurin 25).
* **Gradle Wrapper**: Versão 9.3+ com Loom 1.15.5 (`net.fabricmc.fabric-loom`).
* **Runtime**: Ambiente de execução Mojang desofuscado (o bloco de mapeamentos no `build.gradle` é estritamente proibido nas versões 26.x).

---

## 🏗️ Comandos de Compilação Gradle

Abra um terminal no diretório do subprojeto `Camera Culling v26.3/Camera Culling 26.3`:

```bash
# Executar a suíte de testes unitários
./gradlew test --no-daemon

# Compilar e empacotar o JAR de lançamento
./gradlew build --no-daemon
```

### Pipeline de Arquivamento Automático
O `build.gradle` do subprojeto inclui uma tarefa automatizada de arquivamento:
* JAR de Saída: `build/libs/vanilla-outsider-camera-culling-1.10.0+26.3.jar`
* Local de Arquivamento Automático: `Archive Jar of all versions/MC 26.3/`
* Sincronização com o Perfil do Modrinth: Instalado automaticamente no seu perfil local do inicializador (`Fabric 26.3ish/mods/`).

---

## 📄 Metadados do `fabric.mod.json`

```json
{
  "schemaVersion": 1,
  "id": "camera-culling",
  "version": "${version}",
  "name": "Camera Culling",
  "description": "High-performance camera occlusion culling, 2-sided sign text culling & distance texture LOD.",
  "authors": [
    "Dasik (Rifaditya)"
  ],
  "license": "GPL-3.0-or-later",
  "environment": "client",
  "entrypoints": {
    "client": [
      "net.vanillaoutsider.culling.CameraCullingClient"
    ],
    "modmenu": [
      "net.vanillaoutsider.culling.config.ModMenuIntegration"
    ]
  },
  "mixins": [
    "camera-culling.mixins.json"
  ],
  "depends": {
    "fabricloader": ">=0.18.4",
    "minecraft": ">=26.3-",
    "fabric-api": "*"
  }
}
```

---

## 🔗 Páginas Relacionadas

- [[Arquitetura e Mixins|pt_br-26.3-Architecture-and-Mixins]]
- [[API e Integração de Mods|pt_br-26.3-API-and-Integration]]
- [[Voltar à Visão Geral do MC 26.3|pt_br-26.3-Home]]
