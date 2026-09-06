# 🔵 Camera Culling (Minecraft 26.2)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

**Camera Culling** (`v1.10.1+26.2`)의 **Minecraft 26.2** 전용 문서 허브에 오신 것을 환영합니다.

> 📌 **저장소 소스 코드 면책 조항**: 본 위키의 문서는 CurseForge 및 Modrinth의 공개 릴리스 빌드보다 앞선 최신 미출시 커밋 또는 개발 중인 기능을 포함할 수 있는 **저장소의 현재 소스 코드 상태**를 반영합니다.

---

## 📋 Minecraft 26.2 기본 정보

| 속성 | 값 |
| :--- | :--- |
| **대상 Minecraft 버전** | `26.2` |
| **릴리스 버전** | `1.10.1+26.2` |
| **Java 요구 사양** | Java 25 (`release = 25`) |
| **Fabric Loader** | `>=0.18.4` |
| **Fabric API** | `0.145.4+26.2` |
| **라이선스** | GNU General Public License v3.0 (GPLv3) |
| **서브프로젝트 경로** | `Camera Culling v26.2/Camera Culling 26.2` |

---

## ⚡ 핵심 기능 매트릭스

```text
Camera Culling 26.2 Pipeline
├── Entity Occlusion (Zero-Allocation Raycast Engine)
├── Block Entity Occlusion (6-Sided Enclosure & Sightlines)
├── 2-Sided Sign Text Culling (표면 법선 벡터 내적 + 빈 텍스트 패스트 패스)
├── Particle Occlusion (4m Proximity Safety + Visual Clip)
├── Animation Culling (TextureAtlas Upload Suppression)
├── Distance Texture LOD (3-Tier OpenGL Mipmap Bias)
├── Boss & Mini-Boss Immunity (Configurable HP Thresholds)
├── Anti-Flicker Temporal Hysteresis (Adaptive 4/8/12-Frame Buffer)
└── Graphical GUI (YACL v3 & ModMenu) + In-Game Commands
```

---

## 📚 26.2 문서 인덱스

1. [[엔티티 차폐 컬링|ko_kr-26.2-Entity-Occlusion-Culling]] — 다중 지점 레이캐스팅 및 바닥 필터링.
2. [[블록 엔티티 차폐 컬링|ko_kr-26.2-Block-Entity-Culling]] — 상자 및 블록 엔티티 밀폐 검사.
3. [[표지판 및 매다는 표지판 텍스트 컬링|ko_kr-26.2-Sign-and-Hanging-Sign-Culling]] — 양면 법선 내적 수학 및 텍스트 처리.
4. [[파티클 및 애니메이션 차폐 컬링|ko_kr-26.2-Particle-and-Animation-Culling]] — 지하 파티클 컬링 및 아틀라스 애니메이션 동결.
5. [[몹 밀집 오버드로 방어|ko_kr-26.2-Mob-Crowd-Overdraw-Defense]] — 16m 거리 게이팅 및 1.5m 클러스터 밀도 제한.
6. [[거리 기반 텍스처 LOD|ko_kr-26.2-Distance-Texture-LOD]] — 원거리 몹 무리에 대한 OpenGL 밉맵 LOD 바이어스.
7. [[보스 및 블랙리스트 면제|ko_kr-26.2-Boss-and-Blacklist-Immunity]] — 보스 보호 및 2단계 블랙리스트.
8. [[시간적 히스테리시스 및 제로 할당 수학|ko_kr-26.2-Temporal-Hysteresis-and-Zero-Allocation]] — 완충 버퍼 및 제로 할당 엔진.
9. [[명령어 및 구성|ko_kr-26.2-Commands-and-Configuration]] — Brigadier 명령어 구문 전체 참조.
10. [[그래픽 GUI 구성 (YACL)|ko_kr-26.2-GUI-Configuration]] — 그래픽 메뉴 가이드.
11. [[디버그 로깅 및 진단 추적|ko_kr-26.2-Debug-Logging-and-Diagnostics]] — 실시간 상태 전환 채팅 및 로그 추적.
12. [[아키텍처 및 믹스인|ko_kr-26.2-Architecture-and-Mixins]] — 패키지 계층 구조 및 Mixin 대상 표.
13. [[개발자 환경 설정 및 빌드|ko_kr-26.2-Developer-Setup-and-Building]] — JDK 25 환경 설정 및 Loom Gradle 빌드 지침.
14. [[API 및 모드 통합|ko_kr-26.2-API-and-Integration]] — 프로그래밍 방식의 통합 훅.

---

[[버전 포털로 돌아가기|ko_kr-Home]] &bull; [[버전 호환성 및 수명 주기 매트릭스|ko_kr-Version-Compatibility]]
