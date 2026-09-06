# 👥 生物密集过度绘制防御 (Minecraft 26.3)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

在密集的刷怪农场、村民交易大厅以及密集牲畜繁育围栏中，当数十上百只实体堆叠在几个方块的狭小空间内时，极易造成客户端帧率的急剧暴跌。虽然 GPU 的 Early-Z 早期深度测试可以剔除部分被遮挡的像素，但是为数百只生物提取并提交骨骼层级动画模型依然会严重压垮 CPU 的渲染调度管线。

**Camera Culling** 提供了一套可选的、具备严密安全边界的生物密集过度绘制防御机制。

---

## 📋 生物密集防御信息概览

| 属性 | 设定值 |
| :--- | :--- |
| **配置项键名** | `cullEntitiesBehindEntities` (默认: `false`) |
| **集群密度上限** | `maxEntitiesPerCluster` (默认: `8` 只生物 / 1.5 方块半径) |
| **距离快速失败** | $> 16.0$ 米 ($256.0\text{m}^2$) |
| **集群搜索半径** | `targetBox.inflate(1.5)` |
| **豁免实体类型** | 半透明与装饰性实体 (`Vex`, `ArmorStand`, `ItemFrame`, `Slime`, `MagmaCube`) |

---

## 🛑 16米距离快速失败架构

在广阔的自然旷野中，对分散游荡的牛羊群执行空间范围碰撞盒搜索会产生不必要的 CPU 额外开销。在 Camera Culling 中，生物密集防御机制严格执行 **16米距离快速失败**：

```java
public static boolean isEntityOccludedByCloserEntities(Entity target, Level level, Vec3 camPos, AABB targetBox, double targetDistSq) {
    if (target == null || level == null || camPos == null) {
        return false;
    }

    // 快速失败：仅对 16 米以内的生物应用密集剔除
    if (targetDistSq > 256.0) {
        return false;
    }

    // 1. 在 1.5 格紧凑空间内统计生物集群密度
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
                return true; // 超过集群密度上限，予以剔除
            }
        }
    }
    return false;
}
```

### 核心增益：
1. **零旷野额外开销**：在 16 米以外悠闲吃草的牲畜完全跳过空间范围查询。
2. **密集生物栏稳定护航**：在 1x1 或 2x2 挤压了 50+ 头牛或僵尸的极端刷怪坑中，强制仅渲染最前方的 8 只生物，瞬间抹平帧率骤降。

---

## 🔗 相关页面

- [[实体遮挡剔除|zh_cn-26.3-Entity-Occlusion-Culling]]
- [[Boss 与黑名单免疫|zh_cn-26.3-Boss-and-Blacklist-Immunity]]
- [[返回 MC 26.3 概览|zh_cn-26.3-Home]]
