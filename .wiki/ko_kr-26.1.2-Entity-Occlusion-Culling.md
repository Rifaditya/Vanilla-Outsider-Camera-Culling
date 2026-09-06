# 🧱 엔티티 차폐 컬링 (Minecraft 26.1.2)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Minecraft 26.1.2에서 클라이언트 엔티티 렌더링은 동굴, 절벽 또는 건축물 뒤에 가려져 있더라도 카메라 프러스텀 내의 모든 엔티티에 대해 렌더 상태를 추출합니다. **Camera Culling**은 이 검사를 가로채어 가려진 몹이 CPU 지오메트리 처리와 GPU 드로우 콜을 소모하지 않도록 차단합니다.

---

## 📋 엔티티 컬링 기본 정보

| 속성 | 값 |
| :--- | :--- |
| **대상 파이프라인** | `EntityRenderer.shouldRender(T, Frustum, double, double, double)` |
| **기본 프로필** | `SUPER` (극한) |
| **클립 컨텍스트** | `ClipContext.Block.COLLIDER`, `ClipContext.Fluid.NONE` |
| **바닥 필터링** | 충돌 높이가 $\le Y + 0.15\text{m}$일 때 `Direction.UP` 충돌 무시 |
| **나뭇잎 차폐** | 솔리드 렌더 및 `BlockTags.LEAVES` 블록이 시선을 차폐함 |
| **면제 버블** | 거리 제곱 $< \text{minDistanceSq}$ |

---

## 🔬 다중 지점 해부학적 시선 샘플링

Camera Culling은 선택된 [[컬링 프로필|ko_kr-26.1.2-Commands-and-Configuration]]에 따라 다중 지점 해부학적 샘플링을 수행합니다:

```text
       [1] 머리 상단 (maxY - 0.05)
          \
           [2] 안구 위치 (entity.getEyeY())
            \
             [3] 상체 / 가슴 (minY + height * 0.70)
              \
               [4] 기하학적 중심 (centerY)
                \
        [5-8] 외곽 측면 샘플링 (너비/깊이 검사)
```

1. **샘플 1: 엔티티 머리 상단 (`maxY - 0.05`)**
   - 높은 우선순위 검사. 낮은 방벽이나 울타리 너머로 엿보는 키 큰 엔티티를 감지합니다.
2. **샘플 2: 해부학적 눈 위치 (`getEyeY()`)**
   - 카메라에서 몹의 눈으로 이어지는 직접적인 가시선입니다.
3. **샘플 3: 상체 / 가슴 (`minY + height * 0.70`)**
   - 지면보다 안전하게 높은 상체 시선을 평가합니다.
4. **샘플 4: 질량 중심 (`(minY + maxY) * 0.5`)**
   - 일반적인 기하학적 중심점 테스트입니다.
5. **샘플 5–8: 외곽 측면 샘플링**
   - $(X_{\min} + 0.15, Z_{\min} + 0.15)$, $(X_{\max} - 0.15, Z_{\min} + 0.15)$ 등을 평가합니다. 덩치가 큰 보스(예: 파괴수, 워든, 거미)가 모퉁이 뒤에서 어깨를 드러낼 때 항상 보이도록 보장합니다.

---

## 🛡️ 방향성 바닥 및 경사면 필터링

플레이어가 울퉁불퉁한 지형에 서 있는 몹을 내려다볼 때, 일반적인 레이캐스트는 몹 발 근처 블록의 윗면에 부딪혀 바닥을 차폐벽으로 잘못 인식할 수 있습니다.

Camera Culling은 **방향성 바닥 충돌 필터링**을 적용합니다:
$$\text{만약 } \text{hit.getDirection()} == \text{Direction.UP} \quad \text{이고} \quad Y_{\text{hit}} \le Y_{\text{target}} + 0.15\text{m} \implies \text{유효한 시선 (차폐되지 않음)}$$

이를 통해 언덕, 계단 및 불규칙한 지형을 이동하는 몹이 잘못 컬링되는 현상을 완전히 방지합니다.

---

## ⚡ 제로 할당 레이캐스트 엔진

이전 구현에서는 100개의 엔티티에 대해 8개의 샘플 지점을 평가할 때 초당 180,000개 이상의 `new Vec3()` 힙 할당이 발생하여 JVM Young-Gen 가비지 컬렉션 일시 중단을 유발했습니다.

26.1.2에서는 `CullingRaycastHelper`가 원시 기본형 좌표를 직접 전달합니다:
```java
public static boolean hasLineOfSight(Level level, Vec3 from, double toX, double toY, double toZ)
```
이 아키텍처는 매 프레임 발생하는 중간 힙 할당을 완전히 제거합니다.

---

## 🔗 관련 페이지

- [[몹 밀집 오버드로 방어|ko_kr-26.1.2-Mob-Crowd-Overdraw-Defense]]
- [[시간적 히스테리시스 및 제로 할당 수학|ko_kr-26.1.2-Temporal-Hysteresis-and-Zero-Allocation]]
- [[보스 및 블랙리스트 면제|ko_kr-26.1.2-Boss-and-Blacklist-Immunity]]
- [[MC 26.1.2 개요로 돌아가기|ko_kr-26.1.2-Home]]
