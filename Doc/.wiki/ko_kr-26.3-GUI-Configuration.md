# 🖥️ 그래픽 GUI 구성 (Minecraft 26.3)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Camera Culling은 **YetAnotherConfigLib (YACL v3)** 및 **ModMenu**를 기반으로 하는 최신 그래픽 구성 화면을 지원합니다.

---

## 📋 GUI 통합 기본 정보

| 속성 | 값 |
| :--- | :--- |
| **지원 GUI 엔진** | YetAnotherConfigLib v3 (YACL) + ModMenu |
| **통합 패턴** | 지연 클래스 로딩 (`ConfigScreenFactory`) |
| **서버 충돌 안전성** | 100% 안전 — 서버 진입점에 클라이언트 GUI 클래스 참조 제로 |
| **메뉴 카테고리** | 3개의 전용 탭 카테고리 |

---

## 🗂️ GUI 카테고리 분석

```text
Camera Culling 설정 화면
├── 1. Engine & Diagnostics (엔진 및 진단)
│   ├── Master Enable (체크박스)
│   ├── Culling Level (드롭다운: LOW, MEDIUM, HIGH, SUPER)
│   └── Real-Time Debug Logging (체크박스)
│
├── 2. Entity & Crowd Occlusion (엔티티 및 군집 차폐)
│   ├── Crowd Overdraw Culling (체크박스)
│   ├── Max Cluster Entities Cap (슬라이더: 1 ~ 32)
│   ├── Boss & Mini-Boss Immunity (체크박스)
│   ├── Major Boss Health Threshold (숫자 입력, 기본값: 150.0 HP)
│   └── Mini-Boss Health Threshold (숫자 입력, 기본값: 50.0 HP)
│
└── 3. Blocks, Particles & Animations (블록, 파티클 및 애니메이션)
    ├── Particle Culling (체크박스)
    ├── Block & Texture Animation Culling (체크박스)
    ├── 2-Sided Sign Text Culling (체크박스)
    ├── Distance Texture LOD (체크박스)
    ├── Distance Texture LOD Start Distance (슬라이더: 8m ~ 64m)
    └── Distance Texture LOD Far Distance (슬라이더: 16m ~ 128m)
```

---

## 🛡️ 지연 클래스 로딩 및 충돌 안전성

Camera Culling이 전용 서버나 YACL이 없는 환경에서 충돌하지 않도록 보장하기 위해, `ModMenuIntegration`은 지연 클래스 로딩을 구현합니다:

```java
public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        if (FabricLoader.getInstance().isModLoaded("yet_another_config_lib_v3")) {
            return YaclScreenHelper.createFactory();
        }
        return parent -> null;
    }
}
```

YACL이 설치되지 않은 경우에도 게임은 정상적으로 실행되며, 플레이어는 [[인게임 명령어|ko_kr-26.3-Commands-and-Configuration]] 또는 `config/camera-culling.json` 편집을 통해 모든 설정을 구성할 수 있습니다.

---

## 🔗 관련 페이지

- [[명령어 및 구성|ko_kr-26.3-Commands-and-Configuration]]
- [[디버그 로깅 및 진단 추적|ko_kr-26.3-Debug-Logging-and-Diagnostics]]
- [[MC 26.3 개요로 돌아가기|ko_kr-26.3-Home]]
