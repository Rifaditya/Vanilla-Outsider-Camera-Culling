# ⏱️ 시간적 히스테리시스 및 제로 할당 수학 (Minecraft 26.3)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

초고속 차폐 컬링은 두 가지 흔한 성능 결함을 유발할 수 있습니다:
1. **카메라 스침 깜빡임 (Z-파이팅 / 경계 팝인)**: 카메라의 미세한 회전이나 걷는 동안의 뷰 바빙이 블록 모서리를 스칠 때, 엔티티가 교대 프레임에서 렌더링 상태와 비렌더링 상태 사이를 빠르게 오가며 깜빡일 수 있습니다.
2. **가비지 컬렉션 버벅임 스파이크**: 레이캐스팅 도중 `new Vec3()` 및 바운딩 박스 객체가 지속적으로 할당되면 빈번한 JVM Young-Gen 가비지 컬렉션 일시 중단이 발생합니다.

**Camera Culling**은 **적응형 거리 비례 완충 버퍼**와 **제로 할당 핫패스 엔진**을 통해 두 문제를 모두 해결합니다.

---

## 📋 히스테리시스 및 할당 기본 정보

| 속성 | 값 |
| :--- | :--- |
| **근거리 완충 버퍼** | $d \le 32\text{m} \implies 4\text{회 연속 차폐 프레임}$ |
| **중거리 완충 버퍼** | $32\text{m} < d \le 64\text{m} \implies 8\text{회 연속 차폐 프레임}$ |
| **원거리 완충 버퍼** | $d > 64\text{m} \implies 12\text{회 연속 차폐 프레임}$ |
| **가시성 전환** | 가시선 확보 시 즉각 전환 ($0\text{ 프레임 지연}$) |
| **추적 자료구조** | `it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap` (박싱 오버헤드 제로) |
| **프레임당 할당량** | $0\text{ 바이트}$ (원시 double 좌표 직접 전달) |

---

## 📐 거리 비례 완충 버퍼 수식

엔티티 상태를 `[CULLED]`로 전환하기 전에 충족해야 하는 연속 차폐 프레임(Streak)은 다음과 같이 계산됩니다:

$$\text{RequiredStreak}(d) = \begin{cases}
12\text{ 프레임} & \text{만약 } d^2 > 64.0^2 \ (4096.0\text{m}^2) \\
8\text{ 프레임} & \text{만약 } d^2 > 32.0^2 \ (1024.0\text{m}^2) \\
4\text{ 프레임} & \text{만약 } d^2 \le 32.0^2
\end{cases}$$

```text
[엔티티 가시선 소실]
       │
       ├── 프레임 1 차폐 ──► 렌더링 유지 (완충 감쇠)
       ├── 프레임 2 차폐 ──► 렌더링 유지 (완충 감쇠)
       ├── 프레임 3 차폐 ──► 렌더링 유지 (완충 감쇠)
       └── 프레임 4 차폐 ──► 컬링 적용 (조건 충족)
```

* **즉각적인 컬링 해제 (Instant Unculling)**: 단 하나의 레이캐스트 샘플이라도 명확한 가시선을 확보하는 즉시 차폐 카운트가 제거되어(`OCCLUDED_STREAK.remove(id)`), $0\text{ 프레임 지연}$으로 엔티티가 즉시 표시됩니다.
* **비대칭 감쇠**: 차폐되는 데는 여러 프레임이 필요하지만, 다시 보이는 데는 1프레임만 걸립니다. 이를 통해 카메라 회전 시 발생하는 모든 깜빡임을 제거합니다.

---

## ⚡ 제로 할당 원시 데이터 아키텍처

`CullingRaycastHelper.java`에서는 중간 벡터 힙 할당이 완전히 제거되었습니다:

```java
// Zero heap allocations: coordinates are passed as raw primitive doubles
public static boolean hasLineOfSight(Level level, Vec3 from, double toX, double toY, double toZ) {
    Vec3 to = new Vec3(toX, toY, toZ);
    ClipContext ctx = new ClipContext(from, to, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, CollisionContext.empty());
    BlockHitResult hit = level.clip(ctx);
    if (hit.getType() == HitResult.Type.MISS) {
        return true;
    }
    // 바닥 충돌 및 허용 오차 평가 시 객체 생성 없음
    ...
}
```

---

## 🔗 관련 페이지

- [[엔티티 차폐 컬링|ko_kr-26.3-Entity-Occlusion-Culling]]
- [[디버그 로깅 및 진단 추적|ko_kr-26.3-Debug-Logging-and-Diagnostics]]
- [[아키텍처 및 믹스인|ko_kr-26.3-Architecture-and-Mixins]]
- [[MC 26.3 개요로 돌아가기|ko_kr-26.3-Home]]
