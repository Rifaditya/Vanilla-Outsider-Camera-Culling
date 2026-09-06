# 👥 몹 밀집 오버드로 방어 (Minecraft 26.3)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

밀집된 몹 농장, 주민 거래소 및 동물 번식장은 수백 개의 엔티티가 불과 몇 블록 내에 겹쳐 있을 때 극심한 프레임 드랍을 일으킬 수 있습니다. GPU Early-Z 래스터화가 기본적인 깊이 테스트를 처리하지만, 수백 개의 몹 골격 계층 구조를 추출하고 제출하는 것은 CPU 렌더 디스패처에 큰 과부하를 줍니다.

**Camera Culling**은 안전 장치가 마련된 선택적 몹 군집 오버드로 방어 시스템을 제공합니다.

---

## 📋 몹 밀집 방어 기본 정보

| 속성 | 값 |
| :--- | :--- |
| **구성 키** | `cullEntitiesBehindEntities` (기본값: `false`) |
| **클러스터 밀도 제한** | `maxEntitiesPerCluster` (기본값: 1.5블록당 `8`마리) |
| **거리 빠른 실패** | $> 16.0$미터 ($256.0\text{m}^2$) |
| **클러스터 검색 반경** | `targetBox.inflate(1.5)` |
| **면제 엔티티** | 투명/장식용 몹 (`Vex`, `ArmorStand`, `ItemFrame`, `Slime`, `MagmaCube`) |

---

## 🛑 16미터 거리 빠른 실패(Fast-Fail) 아키텍처

소 무리가 흩어져 있는 넓은 개활지에서 모든 몹을 대상으로 공간 쿼리를 실행하는 것은 불필요한 CPU 오버헤드를 유발합니다. 최신 버전의 Camera Culling에서는 군집 오버드로 컬링에 즉각적인 **16미터 거리 빠른 실패**를 적용합니다:

```java
public static boolean isEntityOccludedByCloserEntities(Entity target, Level level, Vec3 camPos, AABB targetBox, double targetDistSq) {
    if (target == null || level == null || camPos == null) {
        return false;
    }

    // Fast-fail: only apply crowd overdraw culling within 16 meters
    if (targetDistSq > 256.0) {
        return false;
    }

    // 1. Cluster Density Cap in tight 1.5-block sphere
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
                return true; // Culled due to cluster density cap
            }
        }
    }
    return false;
}
```

### 이점:
1. **개활지 오버헤드 제로**: 16미터 너머에서 풀을 뜯는 몹은 엔티티 검색 스윕을 완전히 건너뜁니다.
2. **밀집 축사 보호**: 50마리 이상의 소/좀비가 갇혀 있는 1x1 또는 2x2 몹 그라인더 축사에서 렌더링이 가장 앞쪽 8마리의 엔티티로 제한되어 렉 스파이크가 제거됩니다.

---

## 🔗 관련 페이지

- [[엔티티 차폐 컬링|ko_kr-26.3-Entity-Occlusion-Culling]]
- [[보스 및 블랙리스트 면제|ko_kr-26.3-Boss-and-Blacklist-Immunity]]
- [[MC 26.3 개요로 돌아가기|ko_kr-26.3-Home]]
