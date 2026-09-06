# 🎮 명령어 및 구성 (Minecraft 26.2)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Camera Culling은 완전한 인게임 Brigadier 명령어 스위트(`/cameraculling`) 및 깔끔한 JSON 구성 지속성(`config/camera-culling.json`)을 제공합니다.

---

## 📋 명령어 참조 표

```sql
/cameraculling status
/cameraculling toggle
/cameraculling set <low|medium|high|super>
/cameraculling particles [true|false]
/cameraculling animations [true|false]
/cameraculling crowdculling <true|false>
/cameraculling cluster <1-128>
/cameraculling texturlod <true|false>
/cameraculling texturlod range <start_dist> <far_dist>
/cameraculling bossimmunity <true|false>
/cameraculling bosshealth <boss_hp>
/cameraculling bosshealth <boss_hp> <miniboss_hp>
/cameraculling minibosshealth <miniboss_hp>
/cameraculling blacklist <add|remove|list|clear> <entity_id>
/cameraculling serverblacklist <add|remove|list|clear> <entity_id>
/cameraculling debug [true|false]
/cameraculling reload
```

---

## ⚙️ 세부 명령어 분석

| 명령어 구문 | 매개변수 | 기능 설명 |
| :--- | :--- | :--- |
| `/cameraculling status` | 없음 | 실시간 통계, 렌더링 vs 컬링된 카운터, 활성 프로필 및 블랙리스트 개수를 표시합니다. |
| `/cameraculling toggle` | 없음 | 마스터 컬링 활성화 상태를 반전합니다. |
| `/cameraculling set <profile>` | `low`, `medium`, `high`, `super` | 활성 컬링 강도 프로필을 전환합니다. |
| `/cameraculling particles [bool]` | `true`, `false` (선택 사항) | 솔리드 블록 뒤의 파티클 차폐 컬링을 켜거나 끕니다. |
| `/cameraculling animations [bool]` | `true`, `false` (선택 사항) | 블록 및 텍스처 아틀라스 애니메이션 동결을 켜거나 끕니다. |
| `/cameraculling crowdculling <bool>` | `true`, `false` | 엔티티 뒤 엔티티 / 군집 오버드로 컬링을 켜거나 끕니다. |
| `/cameraculling cluster <int>` | `1` ~ `128` | 1.5블록 클러스터당 렌더링할 최대 허용 몹 수를 구성합니다 (기본값: `8`). |
| `/cameraculling texturlod <bool>` | `true`, `false` | 거리 텍스처 LOD 밉맵 스케일링을 활성화하거나 비활성화합니다. |
| `/cameraculling texturlod range <start> <far>` | `1.0..256.0`, `2.0..512.0` | 텍스처 LOD 스케일링을 위한 근거리 및 원거리 임계값을 설정합니다 (예: `16.0 32.0`). |
| `/cameraculling bossimmunity <bool>` | `true`, `false` | 보스 및 미니 보스 시선 보호 기능을 켜거나 끕니다. |
| `/cameraculling bosshealth <hp>` | `1.0` ~ `10000.0` | 메이저 보스 HP 임계값을 설정합니다 (예: `150.0`). |
| `/cameraculling minibosshealth <hp>` | `1.0` ~ `10000.0` | 미니 보스 HP 임계값을 설정합니다 (예: `50.0`). |
| `/cameraculling blacklist add <id>` | 엔티티 ID 문자열 | 개인 클라이언트 면제 목록에 엔티티(예: `minecraft:wolf`)를 추가합니다. |
| `/cameraculling blacklist remove <id>` | 엔티티 ID 문자열 | 개인 클라이언트 면제 목록에서 엔티티를 제거합니다. |
| `/cameraculling blacklist list` | 없음 | 현재 개인 면제 블랙리스트에 있는 모든 엔티티를 나열합니다. |
| `/cameraculling blacklist clear` | 없음 | 개인 면제 블랙리스트의 모든 항목을 지웁니다. |
| `/cameraculling serverblacklist ...` | 하위 명령어 + ID | 서버 강제 면제 블랙리스트를 구성합니다 (관리자 권한 필요). |
| `/cameraculling debug [bool]` | `true`, `false` (선택 사항) | 채팅 및 로그로 실시간 상태 전환 진단 추적을 켜거나 끕니다. |
| `/cameraculling reload` | 없음 | 디스크에서 구성 파일을 다시 로드합니다. |

---

## 📄 JSON 구성 형식

구성 파일은 `.minecraft/config/` 디렉터리에 있습니다:

### 클라이언트 구성 (`config/camera-culling.json`)
```json
{
  "enabled": true,
  "level": "SUPER",
  "cullEntitiesBehindEntities": false,
  "maxEntitiesPerCluster": 8,
  "distanceTextureLod": true,
  "distanceTextureLodStart": 16.0,
  "distanceTextureLodFar": 32.0,
  "bossImmunity": true,
  "bossHealthThreshold": 150.0,
  "miniBossHealthThreshold": 50.0,
  "cullParticles": true,
  "cullAnimations": true,
  "cullSignText": true,
  "clientBlacklist": [
    "minecraft:wolf",
    "minecraft:allay"
  ],
  "debugMode": false
}
```

### 서버 구성 (`config/camera-culling-server.json`)
```json
{
  "serverBlacklist": []
}
```

---

## 🔗 관련 페이지

- [[그래픽 GUI 구성 (YACL)|ko_kr-26.2-GUI-Configuration]]
- [[디버그 로깅 및 진단 추적|ko_kr-26.2-Debug-Logging-and-Diagnostics]]
- [[MC 26.2 개요로 돌아가기|ko_kr-26.2-Home]]
