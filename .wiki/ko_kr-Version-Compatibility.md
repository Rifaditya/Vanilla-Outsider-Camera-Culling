# 🌐 버전 호환성 및 수명 주기 매트릭스

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

본 문서는 **Camera Culling**의 활성 다중 시대 릴리스 매트릭스, 종속성 제약 조건, Java 런타임 사양 및 바이트코드 API 차이점을 설명합니다.

> 📌 **저장소 소스 코드 면책 조항**: 본 위키의 문서는 CurseForge 및 Modrinth의 공개 릴리스 빌드보다 앞선 최신 미출시 커밋 또는 개발 중인 기능을 포함할 수 있는 **저장소의 현재 소스 코드 상태**를 반영합니다.

---

## 📋 버전 호환성 및 수명 주기 매트릭스

| Minecraft 앵커 시대 | 대상 MC 버전 | 현재 릴리스 버전 | Java 요구 사항 | Fabric Loader 제약 | Fabric API 제약 | 릴리스 상태 |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| **Modern Sovereign (26.3)** | `26.3` | `1.10.1+26.3` | Java 25 (`release = 25`) | `>=0.18.4` | `0.156.1+26.3` | 🟢 활성 릴리스 |
| **Modern Sovereign (26.2)** | `26.2` | `1.10.1+26.2` | Java 25 (`release = 25`) | `>=0.18.4` | `0.145.4+26.2` | 🟢 활성 릴리스 |
| **Modern Sovereign (26.1.2)** | `26.1.2` | `1.10.2+26.1.2` | Java 25 (`release = 25`) | `>=0.18.4` | `0.145.4+26.1.2` | 🟢 활성 릴리스 |

---

## 🛠️ 버전별 바이트코드 API 차이점

### 1. 블록 엔티티 RenderState 추출 파이프라인
* **Minecraft 26.1.2**:
  - `BlockEntityRenderDispatcher.tryExtractRenderState(BlockEntity, float, ModelFeatureRenderer$CrumblingOverlay)` — **3개의 인자**를 취함.
* **Minecraft 26.2 및 26.3**:
  - `BlockEntityRenderDispatcher.tryExtractRenderState(BlockEntity, float, ModelFeatureRenderer$CrumblingOverlay, boolean isGloballyRendered)` — **4개의 인자**를 취함.

### 2. 표지판 텍스트 데이터 조회 API
* **Minecraft 26.1.2 및 26.2**:
  - `SignBlockEntity.getFrontText()` 및 `SignBlockEntity.getBackText()`를 통해 `SignText`를 가져옴.
  - `SignText.getMessage(int index, boolean filtered)`를 통해 줄 단위 `Component`를 가져옴.
* **Minecraft 26.3**:
  - `SignBlockEntity.getText(SignTextSlot.FRONT)` 및 `SignBlockEntity.getText(SignTextSlot.BACK)`를 통해 `SignText`를 가져옴.
  - `SignText.getMessages(boolean filtered)`를 통해 줄 단위 `Component` 배열을 가져옴.

---

## 📦 빌드 아티팩트 아카이브 경로

모든 릴리스 빌드는 부모 저장소의 중앙 아카이브 구조에 자동으로 컴파일 및 보존됩니다:

```text
Archive Jar of all versions/
├── MC 26.1.2/
│   ├── vanilla-outsider-camera-culling-1.10.1+26.1.2.jar
│   └── vanilla-outsider-camera-culling-1.10.1+26.1.2-sources.jar
├── MC 26.2/
│   ├── vanilla-outsider-camera-culling-1.10.0+26.2.jar
│   └── vanilla-outsider-camera-culling-1.10.0+26.2-sources.jar
└── MC 26.3/
    ├── vanilla-outsider-camera-culling-1.10.0+26.3.jar
    └── vanilla-outsider-camera-culling-1.10.0+26.3-sources.jar
```

---

## 🔗 빠른 링크

- [[👉 마인크래프트 26.3 위키 들어가기|ko_kr-26.3-Home]]
- [[👉 마인크래프트 26.2 위키 들어가기|ko_kr-26.2-Home]]
- [[👉 마인크래프트 26.1.2 위키 들어가기|ko_kr-26.1.2-Home]]
- [[포털로 돌아가기|ko_kr-Home]]
