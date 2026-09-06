# 🏛️ 아키텍처 및 믹스인 기술 참조 (Minecraft 26.3)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Camera Culling은 최신 비난독화 **Minecraft 26.3** 렌더링 엔진을 위해 특별히 설계된 오버헤드 없는 Mixin 훅과 엄격한 **"1 파일 1 목적"** 아키텍처로 엔지니어링되었습니다.

---

## 📋 Mixin 대상 원장

| Mixin 클래스 | 대상 Minecraft 클래스 | 대상 메서드 및 인젝션 지점 | 기능 설명 |
| :--- | :--- | :--- | :--- |
| `EntityRendererMixin` | `net.minecraft.client.renderer.entity.EntityRenderer` | `shouldRender` `@At("HEAD")` | 다중 지점 레이캐스트 차폐 컬링 및 몹 밀집도 검사 |
| `BlockEntityRenderDispatcherMixin` | `net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher` | `tryExtractRenderState` `@At("HEAD")` | 6방향 밀폐 및 레이캐스트 블록 엔티티 컬링 |
| `BlockEntityRenderDispatcherMixin` | `net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher` | `tryExtractRenderState` `@At("RETURN")` | 양면 표지판 후면 및 빈 텍스트 컬링 |
| `LivingEntityRendererMixin` | `net.minecraft.client.renderer.entity.LivingEntityRenderer` | `submit` `@At("HEAD")` & `@At("RETURN")` | OpenGL 텍스처 LOD 밉맵 바이어스 적용 및 재설정 (`GL_TEXTURE_LOD_BIAS`) |
| `SingleQuadParticleMixin` | `net.minecraft.client.particle.SingleQuadParticle` | `extract` `@At("HEAD")` | 솔리드 지오메트리에 대한 QuadParticle 차폐 컬링 |
| `TextureAtlasMixin` | `net.minecraft.client.renderer.texture.TextureAtlas` | `cycleAnimationFrames` `@At("HEAD")` | 화면 밖 애니메이션 텍스처 업로드 억제 |

---

## 🌳 패키지 계층 구조

```text
net.vanillaoutsider.culling
├── CameraCullingClient.java               (ClientModInitializer 및 통계 카운터)
├── ModVersionGuard.java                   (Knot 클래스로더 버전 무결성 보호)
│
├── command
│   └── CameraCullingCommand.java          (FabricClientCommandSource Brigadier 구문 트리)
│
├── config
│   ├── CameraCullingConfig.java           (클라이언트 및 서버 구성을 위한 JSON 직렬화)
│   ├── CullingLevel.java                  (LOW, MEDIUM, HIGH, SUPER 강도 프로필)
│   ├── ModMenuIntegration.java            (지연된 YACL 팩토리가 포함된 ModMenu API 진입점)
│   └── YaclScreenHelper.java              (YetAnotherConfigLib v3 화면 빌더)
│
├── mixin
│   ├── BlockEntityRenderDispatcherMixin.java
│   ├── EntityRendererMixin.java
│   ├── LivingEntityRendererMixin.java
│   ├── SingleQuadParticleMixin.java
│   └── TextureAtlasMixin.java
│
└── util
    ├── AnimationCullingHelper.java        (게임 일시 중지/메뉴 상태 시 아틀라스 업로드 중지)
    ├── BlacklistHelper.java               (클라이언트 및 서버 엔티티 블랙리스트 평가)
    ├── BossDetectionHelper.java           (동적 HP 임계값 및 이름 휴리스틱)
    ├── CullingDiagnosticsHelper.java      (실시간 채팅 및 로그 상태 전환 추적)
    ├── CullingRaycastHelper.java          (제로 할당 원시 레이캐스팅 및 히스테리시스)
    ├── ParticleCullingHelper.java         (4m 근접 버블 및 시각 클립 레이캐스팅)
    ├── SignTextCullingHelper.java         (양면 표지판용 벡터 법선 내적)
    └── TextureLodHelper.java              (3단계 OpenGL 밉맵 LOD 바이어스 계산)
```

---

## 🔗 관련 페이지

- [[개발자 환경 설정 및 빌드|ko_kr-26.3-Developer-Setup-and-Building]]
- [[API 및 모드 통합|ko_kr-26.3-API-and-Integration]]
- [[MC 26.3 개요로 돌아가기|ko_kr-26.3-Home]]
