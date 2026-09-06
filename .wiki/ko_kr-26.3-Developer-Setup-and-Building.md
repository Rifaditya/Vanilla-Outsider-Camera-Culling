# 🛠️ 개발자 환경 설정 및 빌드 (Minecraft 26.3)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

이 기술 가이드는 **Minecraft 26.3**에서 **Camera Culling**을 빌드하기 위한 환경 필수 구성 요소, Gradle Loom 도구 및 컴파일 지침을 다룹니다.

---

## 📋 환경 필수 조건

* **Java Development Kit (JDK)**: **JDK 25+** (예: Eclipse Adoptium Temurin 25).
* **Gradle Wrapper**: Loom 1.15.5 (`net.fabricmc.fabric-loom`)가 포함된 버전 9.3+.
* **런타임**: 비난독화 Mojang 런타임 (26.x에서는 `build.gradle`의 mappings 블록 사용이 엄격히 금지됨).

---

## 🏗️ Gradle 빌드 명령어

`Camera Culling v26.3/Camera Culling 26.3` 서브프로젝트 디렉터리에서 터미널을 엽니다:

```bash
# 단위 테스트 스위트 실행
./gradlew test --no-daemon

# 릴리스 JAR 컴파일 및 패키징
./gradlew build --no-daemon
```

### 자동 아카이빙 파이프라인
서브프로젝트 `build.gradle`에는 자동 아카이브 작업이 포함되어 있습니다:
* 출력 JAR: `build/libs/vanilla-outsider-camera-culling-1.10.0+26.3.jar`
* 자동 아카이브 경로: `Archive Jar of all versions/MC 26.3/`
* Modrinth 프로필 동기화: 로컬 런처 프로필(`Fabric 26.3ish/mods/`)에 자동으로 설치됩니다.

---

## 📄 `fabric.mod.json` 메타데이터

```json
{
  "schemaVersion": 1,
  "id": "camera-culling",
  "version": "${version}",
  "name": "Camera Culling",
  "description": "High-performance camera occlusion culling, 2-sided sign text culling & distance texture LOD.",
  "authors": [
    "Dasik (Rifaditya)"
  ],
  "license": "GPL-3.0-or-later",
  "environment": "client",
  "entrypoints": {
    "client": [
      "net.vanillaoutsider.culling.CameraCullingClient"
    ],
    "modmenu": [
      "net.vanillaoutsider.culling.config.ModMenuIntegration"
    ]
  },
  "mixins": [
    "camera-culling.mixins.json"
  ],
  "depends": {
    "fabricloader": ">=0.18.4",
    "minecraft": ">=26.3-",
    "fabric-api": "*"
  }
}
```

---

## 🔗 관련 페이지

- [[아키텍처 및 믹스인|ko_kr-26.3-Architecture-and-Mixins]]
- [[API 및 모드 통합|ko_kr-26.3-API-and-Integration]]
- [[MC 26.3 개요로 돌아가기|ko_kr-26.3-Home]]
