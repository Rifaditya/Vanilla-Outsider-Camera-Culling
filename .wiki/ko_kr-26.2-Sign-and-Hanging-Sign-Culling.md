# 🪧 양면 표지판 및 매다는 표지판 텍스트 컬링 (Minecraft 26.2)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Minecraft 26.2에서 표지판은 양면 텍스트 렌더링을 제공합니다. 게임의 폰트 렌더링 엔진은 양쪽 면의 글리프 쿼드, 색상 및 발광 윤곽선을 동시에 그립니다.

**Camera Culling**은 표면 법선 벡터 내적($\vec{N} \cdot \vec{V}$)과 빈 텍스트 패스트 패스를 통해 불필요한 텍스트 렌더링 패스를 제거합니다.

---

## 📋 표지판 컬링 기본 정보

| 속성 | 값 |
| :--- | :--- |
| **대상 파이프라인** | `BlockEntityRenderDispatcher.tryExtractRenderState` `@At("RETURN")` |
| **API 훅** | `signState.frontText = null;` / `signState.backText = null;` |
| **지원 유형** | 벽 표지판, 서 있는 표지판, 벽 매다는 표지판, 천장 매다는 표지판 |
| **빈 텍스트 패스트 패스** | 공백이 아닌 문자가 0개인 빈 표면을 자동으로 건너뜀 |
| **내적 마진** | $\pm 0.05$의 허용 오차로 모서리 각도 팝인 방지 |

---

## 📐 벡터 법선 내적 수학

표지판의 앞면 또는 뒷면이 카메라를 향하고 있는지 확인하기 위해, Camera Culling은 표지판 표면 법선 벡터 $\vec{N} = (N_x, N_z)$와 표지판 중심에서 카메라로 향하는 벡터 $\vec{V} = (V_x, V_z)$ 사이의 내적을 계산합니다:

$$V_x = X_{\text{cam}} - (X_{\text{sign}} + 0.5), \quad V_z = Z_{\text{cam}} - (Z_{\text{sign}} + 0.5)$$

### 1. 벽 표지판 (`WallSignBlock.FACING`) 및 벽 매다는 표지판 (`WallHangingSignBlock.FACING`)
법선 벡터는 블록의 `Direction` 스텝 오프셋에서 직접 파생됩니다:
$$N_x = \text{facing.getStepX()}, \quad N_z = \text{facing.getStepZ()}$$

### 2. 서 있는 표지판 (`StandingSignBlock.ROTATION`) 및 천장 매다는 표지판 (`CeilingHangingSignBlock.ROTATION`)
회전은 $0 \dots 15$의 정수로 표현됩니다. 라디안 각도 $\theta$는 다음과 같이 계산됩니다:
$$\theta = \left(\frac{\text{rotation} \times 360^\circ}{16}\right) \times \frac{\pi}{180}$$
$$N_x = -\sin\theta, \quad N_z = \cos\theta$$

### 3. 가시성 판정
내적 $D$는 관측 각도를 평가합니다:
$$D = N_x V_x + N_z V_z$$

* **전면 텍스트 평가**: $D < -0.05$일 때 컬링됨 (카메라가 표지판 뒷면에 위치).
* **후면 텍스트 평가**: $D > 0.05$일 때 컬링됨 (카메라가 표지판 앞면에 위치).

---

## ⚡ 빈 면 패스트 패스

빈 표면은 내적을 계산하지 않고 즉시 무효화(null) 처리됩니다:
```java
public static boolean isTextEmpty(SignText text) {
    if (text == null) return true;
    for (int i = 0; i < 4; i++) {
        Component msg = text.getMessage(i, false);
        if (msg != null && !msg.getString().trim().isEmpty()) {
            return false;
        }
    }
    return true;
}
```
빈 면은 삼각 함수나 벡터 내적을 수행하지 않고 즉시 null 처리됩니다.

---

## 🔗 관련 페이지

- [[블록 엔티티 차폐 컬링|ko_kr-26.2-Block-Entity-Culling]]
- [[명령어 및 구성|ko_kr-26.2-Commands-and-Configuration]]
- [[아키텍처 및 믹스인|ko_kr-26.2-Architecture-and-Mixins]]
- [[MC 26.2 개요로 돌아가기|ko_kr-26.2-Home]]
