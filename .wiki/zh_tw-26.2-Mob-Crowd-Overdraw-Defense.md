# 👥 生物密集過度繪製防禦 (Minecraft 26.2)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

在密集的刷怪農場、村民交易大廳以及密集牲畜繁育圍欄中，當數十上百隻實體堆疊在幾個方塊的狹小空間內時，極易造成客戶端幀率的急劇暴跌。雖然 GPU 的 Early-Z 早期深度測試可以剔除部分被遮擋的像素，但是為數百隻生物提取並提交骨骼層級動畫模型依然會嚴重壓垮 CPU 的渲染調度管線。

**Camera Culling** 提供了一套可選的、具備嚴密安全邊界的生物密集過度繪製防禦機制。

---

## 📋 生物密集防禦資訊概覽

| 屬性 | 設定值 |
| :--- | :--- |
| **設定項鍵名** | `cullEntitiesBehindEntities` (預設: `false`) |
| **集群密度上限** | `maxEntitiesPerCluster` (預設: `8` 隻生物 / 1.5 方塊半徑) |
| **距離快速失敗** | $> 16.0$ 公尺 ($256.0\text{m}^2$) |
| **集群搜尋半徑** | `targetBox.inflate(1.5)` |
| **豁免實體類型** | 半透明與裝飾性實體 (`Vex`, `ArmorStand`, `ItemFrame`, `Slime`, `MagmaCube`) |

---

## 🛑 16公尺距離快速失敗架構

在廣闊的自然曠野中，對分散遊蕩的牛羊群執行空間範圍碰撞盒搜尋會產生不必要的 CPU 額外開銷。在 Camera Culling 中，生物密集防禦機制嚴格執行 **16公尺距離快速失敗**：

```java
public static boolean isEntityOccludedByCloserEntities(Entity target, Level level, Vec3 camPos, AABB targetBox, double targetDistSq) {
    if (target == null || level == null || camPos == null) {
        return false;
    }

    // 快速失敗：僅對 16 公尺以內的生物應用密集剔除
    if (targetDistSq > 256.0) {
        return false;
    }

    // 1. 在 1.5 格緊湊空間內統計生物集群密度
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
                return true; // 超過集群密度上限，予以剔除
            }
        }
    }
    return false;
}
```

### 核心增益：
1. **零曠野額外開銷**：在 16 公尺以外悠閒吃草的牲畜完全跳過空間範圍查詢。
2. **密集生物欄穩定護航**：在 1x1 或 2x2 擠壓了 50+ 頭牛或殭屍的極端刷怪坑中，強制僅渲染最前方的 8 隻生物，瞬間抹平幀率驟降。

---

## 🔗 相關頁面

- [[實體遮擋剔除|zh_tw-26.2-Entity-Occlusion-Culling]]
- [[Boss 與黑名單免疫|zh_tw-26.2-Boss-and-Blacklist-Immunity]]
- [[返回 MC 26.2 概覽|zh_tw-26.2-Home]]
