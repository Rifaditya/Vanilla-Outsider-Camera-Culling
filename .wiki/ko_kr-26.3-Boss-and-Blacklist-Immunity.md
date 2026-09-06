# 👑 보스 및 블랙리스트 면제 (Minecraft 26.3)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

게임플레이 공정성과 전투 상황 인식을 보장하기 위해 치명적인 위협과 동반자 동물은 벽 뒤로 사라지거나 공격적인 컬링 알고리즘의 영향을 받아서는 안 됩니다.

**Camera Culling**은 동적 보스 식별 및 2단계 면제 블랙리스트를 갖추고 있습니다.

---

## 📋 면제 기본 정보

| 속성 | 값 |
| :--- | :--- |
| **메이저 보스 체력 임계값** | `bossHealthThreshold` (기본값: `150.0 HP` / 하트 75개) |
| **미니 보스 체력 임계값** | `miniBossHealthThreshold` (기본값: `50.0 HP` / 하트 25개) |
| **클라이언트 블랙리스트 경로** | `config/camera-culling.json` (`clientBlacklist` 배열) |
| **서버 블랙리스트 경로** | `config/camera-culling-server.json` (`serverBlacklist` 배열) |
| **면제 범위** | 블록 차폐, 군집 오버드로 및 텍스처 LOD에서 면제 |

---

## 🐲 동적 보스 및 미니 보스 감지

Camera Culling은 두 가지 메커니즘을 통해 보스 면제를 평가합니다:

### 1. 동적 체력 임계값 판정
`getMaxHealth()`가 설정된 임계값을 충족하거나 초과하는 모든 `LivingEntity`에는 무조건적인 면제가 부여됩니다:
* `maxHealth >= 150.0` $\implies$ 메이저 보스 (엔더 드래곤, 위더, 워든).
* `maxHealth >= 50.0` $\implies$ 미니 보스 (엘더 가디언, 파괴수, 철 골렘, 피글린 잔혹한 자, 브리즈, 모드 보스 몬스터).

### 2. 레지스트리 및 식별자 키워드 휴리스틱
이름이 다음 하위 문자열과 일치하는 엔티티는 자동으로 보스로 인식됩니다:
```text
dragon, wither, warden, elder_guardian, ravager, evoker, iron_golem, 
piglin_brute, breeze, boss, miniboss, mini_boss, titan, leviathan, 
harbinger, monarch, behemoth, champion, elite, brute
```

---

## 🛡️ 2단계 면제 블랙리스트 시스템

```text
면제 판정 트리
├── 로컬 플레이어 / 탑승물 / 탈것 ──► 100% 렌더링
├── 발광(Glowing) 효과 활성화 ────► 100% 렌더링
├── 보스 또는 미니 보스 감지 ─────► 100% 렌더링
├── 클라이언트 블랙리스트 일치 ───► 100% 렌더링
└── 서버 관리자 블랙리스트 일치 ──► 100% 렌더링
```

### 1. 클라이언트 개인 블랙리스트
플레이어는 인게임 명령어를 사용하여 특정 동반자 엔티티를 로컬 화이트리스트에 추가할 수 있습니다:
```sql
/cameraculling blacklist add minecraft:wolf
/cameraculling blacklist add minecraft:allay
/cameraculling blacklist add minecraft:cat
```

### 2. 서버 관리자 블랙리스트
서버 관리자는 `config/camera-culling-server.json` 또는 `/cameraculling serverblacklist add <id>`를 통해 전역 엔티티 면제를 지정할 수 있습니다. 연결된 모든 클라이언트는 서버가 지정한 면제 목록을 자동으로 준수합니다.

---

## 🔗 관련 페이지

- [[엔티티 차폐 컬링|ko_kr-26.3-Entity-Occlusion-Culling]]
- [[거리 기반 텍스처 LOD|ko_kr-26.3-Distance-Texture-LOD]]
- [[명령어 및 구성|ko_kr-26.3-Commands-and-Configuration]]
- [[MC 26.3 개요로 돌아가기|ko_kr-26.3-Home]]
