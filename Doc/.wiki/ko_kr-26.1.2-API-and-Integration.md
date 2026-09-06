# 🔌 API 및 모드 통합 (Minecraft 26.1.2)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Camera Culling은 클라이언트 렌더링 모드(예: Sodium, Iris, Canvas) 및 커스텀 엔티티나 블록 엔티티를 추가하는 콘텐츠 모드와 원활하게 연동되도록 설계되었습니다.

---

## 🤝 서드파티 렌더러 호환성

### 1. Sodium & Embeddium
* **정적 지형 청크**: Sodium은 16x16 청크 메시 생성 및 정적 블록 면 렌더링 파이프라인을 최적화합니다.
* **동적 엔티티**: Camera Culling은 동적 엔티티, 상자, 표지판 및 파티클을 최적화합니다.
* **호환성**: 중복되는 Mixin이나 상태 충돌이 전혀 없으며 100% 호환됩니다.

### 2. Iris & Shaders
* **셰이더 유니폼**: 셰이더는 추출된 프레임 버퍼에서 후처리를 수행합니다.
* **차폐 절감 효과**: 컬링된 엔티티가 G-버퍼에 지오메트리를 제출하지 않으므로, 몹이 밀집된 영역에서 셰이더가 훨씬 높은 프레임레이트로 실행됩니다.

---

## 🛠️ 프로그래밍 방식의 Java API 훅

다른 모드는 정적 유틸리티 파사드를 통해 Camera Culling의 상태를 조회하거나 통합할 수 있습니다:

### 1. 컬링 엔진 상태 조회
```java
import net.vanillaoutsider.culling.CameraCullingClient;

boolean isEnabled = CameraCullingClient.isCullingEnabled();
long culledMobs = CameraCullingClient.getCulledEntitiesCount();
long renderedMobs = CameraCullingClient.getRenderedEntitiesCount();
```

### 2. 프로그래밍 방식의 면제 블랙리스트 등록
```java
import net.vanillaoutsider.culling.config.CameraCullingConfig;

// 커스텀 엔티티 ID를 컬링 화이트리스트에 등록
CameraCullingConfig.addClientBlacklist("mymod:custom_companion");
```

### 3. 직접적인 레이캐스트 가시선 검증
```java
import net.vanillaoutsider.culling.util.CullingRaycastHelper;

// 힙 할당 없는 시선 테스트 수행
boolean visible = CullingRaycastHelper.hasLineOfSight(level, camPos, targetX, targetY, targetZ);
```

---

## 🔗 관련 페이지

- [[아키텍처 및 믹스인|ko_kr-26.1.2-Architecture-and-Mixins]]
- [[개발자 환경 설정 및 빌드|ko_kr-26.1.2-Developer-Setup-and-Building]]
- [[MC 26.1.2 개요로 돌아가기|ko_kr-26.1.2-Home]]
