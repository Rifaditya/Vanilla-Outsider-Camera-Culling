# 👥 Defesa de Sobredesenho de Multidões de Mobs (Minecraft 26.3)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Farms densas de mobs, salas de trocas de aldeões e cercados de reprodução animal podem causar quedas extremas na taxa de quadros quando centenas de entidades ficam empilhadas em poucos blocos. Embora a rasterização Early-Z da GPU realize testes básicos de profundidade, extrair e enviar centenas de hierarquias esqueléticas de mobs sobrecarrega os despachantes de renderização da CPU.

O **Camera Culling** fornece um sistema opcional e seguro de defesa contra sobreposição em multidões.

---

## 📋 Informações Rápidas de Defesa de Multidões

| Propriedade | Valor |
| :--- | :--- |
| **Chave de Configuração** | `cullEntitiesBehindEntities` (Padrão: `false`) |
| **Limite de Densidade de Cluster** | `maxEntitiesPerCluster` (Padrão: `8` mobs / 1,5 blocos) |
| **Falha Rápida por Distância** | $> 16.0$ metros ($256.0\text{m}^2$) |
| **Raio de Busca de Cluster** | `targetBox.inflate(1.5)` |
| **Entidades Isentas** | Mobs transparentes / decorativos (`Vex`, `ArmorStand`, `ItemFrame`, `Slime`, `MagmaCube`) |

---

## 🛑 Arquitetura de Falha Rápida em 16 Metros de Distância

Em grandes campos abertos com manadas de vacas dispersas, executar consultas espaciais em todos os mobs geraria sobrecarga desnecessária na CPU. Nas versões modernas do Camera Culling, a oclusão por sobreposição de multidões aplica uma **falha rápida imediata a partir de 16 metros**:

```java
public static boolean isEntityOccludedByCloserEntities(Entity target, Level level, Vec3 camPos, AABB targetBox, double targetDistSq) {
    if (target == null || level == null || camPos == null) {
        return false;
    }

    // Falha rápida: aplica culling de multidão apenas dentro de 16 metros
    if (targetDistSq > 256.0) {
        return false;
    }

    // 1. Limite de densidade de cluster em uma esfera justa de 1,5 bloco
    int maxCluster = CameraCullingConfig.getMaxEntitiesPerCluster();
    AABB clusterBox = targetBox.inflate(1.5);
    List<Entity> clusterEntities = level.getEntities(target, clusterBox, 
        e -> e instanceof LivingEntity && !isTransparentOrDecorative(e));
    
    if (clusterEntities.size() < maxCluster) {
        return false;
    }

    int closerInCluster = 0;
    for (Entity e : clusterEntities) {
        double distSq = camPos.distanceToSqr(e.getX(), e.getY(), e.getZ());
        if (distSq < targetDistSq) {
            closerInCluster++;
            if (closerInCluster >= maxCluster) {
                return true; // Ocluído devido ao limite de densidade de cluster
            }
        }
    }
    return false;
}
```

### Benefícios:
1. **Zero Sobrecarga em Campo Aberto**: Mobs pastando a mais de 16 metros ignoram completamente as varreduras de busca de entidades.
2. **Proteção em Cercados Densos**: Em caixas de farms 1x1 ou 2x2 com mais de 50 vacas ou zumbis amontoados, a renderização é limitada às 8 entidades mais à frente, eliminando picos de travamento.

---

## 🔗 Páginas Relacionadas

- [[Oclusão de Entidades|pt_br-26.3-Entity-Occlusion-Culling]]
- [[Imunidade de Chefes e Lista Negra|pt_br-26.3-Boss-and-Blacklist-Immunity]]
- [[Voltar à Visão Geral do MC 26.3|pt_br-26.3-Home]]
