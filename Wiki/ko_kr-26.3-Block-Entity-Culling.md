# 📦 블록 엔티티 차폐 컬링 (Minecraft 26.3)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

블록 엔티티(상자, 엔더 상자, 표지판, 현수막, 해골, 장식된 도자기, 종 및 신호기)는 동적 렌더링 요소입니다. 바닐라 청크 메시 컴파일을 우회하기 때문에 매 프레임 개별 드로우 콜을 제출합니다. 수백 개의 상자가 있는 창고나 자동 분류 시설에서는 심각한 GPU 병목 현상이 발생합니다.

---

## 📋 블록 엔티티 컬링 기본 정보

| 속성 | 값 |
| :--- | :--- |
| **대상 파이프라인** | `BlockEntityRenderDispatcher.tryExtractRenderState(...)` |
| **밀폐 감지** | 6개 인접 면 모두 검사: `up`, `down`, `north`, `south`, `east`, `west` |
| **보수 모드** | `LOW` 프로필에서 밀폐된 경우만 검사 |
| **공격적 모드** | `MEDIUM`, `HIGH`, `SUPER`에서 완전한 레이캐스트 시선 검증 |
| **결과** | 렌더 제출을 생략하기 위해 `null` RenderState 반환 |

---

## 🔍 밀폐 및 시선 검증 아키텍처

```text
               [위 (UP)]
                  │
   [서 (WEST)] ─ [상자] ─ [동 (EAST)]
                  │
              [아래 (DOWN)]
```

### 1. 6방향 솔리드 완전 밀폐 패스트 패스
레이캐스트 연산을 수행하기 전에 Camera Culling은 인접 블록 상태를 먼저 조회합니다:
```java
BlockState up = level.getBlockState(pos.above());
BlockState down = level.getBlockState(pos.below());
BlockState north = level.getBlockState(pos.north());
BlockState south = level.getBlockState(pos.south());
BlockState east = level.getBlockState(pos.east());
BlockState west = level.getBlockState(pos.west());

if (up.isSolidRender() && down.isSolidRender() && north.isSolidRender()
    && south.isSolidRender() && east.isSolidRender() && west.isSolidRender()) {
    return true; // 100% 차폐됨 — 렌더링 생략
}
```
벽 뒤에 묻혀 있거나 단단한 지하실 기초 속에 밀폐된 상자는 CPU 연산을 거의 소모하지 않고($< 0.0001\mu\text{s}$) 렌더링에서 제외됩니다.

### 2. 가시선 레이캐스트 검사
`MEDIUM`, `HIGH`, `SUPER` 프로필(`cullAllBlockEntities = true`)에서 Camera Culling은 플레이어의 카메라 위치에서 블록 엔티티 중심 $(X + 0.5, Y + 0.5, Z + 0.5)$으로 광선을 투사합니다:
* 레이캐스트가 대상 블록 엔티티에 도달하기 전에 차폐 솔리드 블록에 부딪히면 렌더 상태를 폐기합니다.
* 명확한 가시선이 확보되면 블록 엔티티가 완전한 시각적 품질로 렌더링됩니다.

---

## 🔗 관련 페이지

- [[표지판 및 매다는 표지판 텍스트 컬링|ko_kr-26.3-Sign-and-Hanging-Sign-Culling]]
- [[엔티티 차폐 컬링|ko_kr-26.3-Entity-Occlusion-Culling]]
- [[아키텍처 및 믹스인|ko_kr-26.3-Architecture-and-Mixins]]
- [[MC 26.3 개요로 돌아가기|ko_kr-26.3-Home]]
