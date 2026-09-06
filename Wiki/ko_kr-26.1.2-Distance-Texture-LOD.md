# 🎨 거리 기반 몹 텍스처 LOD (Minecraft 26.1.2)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

화면에서 겨우 4x4 픽셀만 차지하는 먼 거리의 몹에 대해 전체 1024x1024 또는 고해상도 텍스처를 렌더링하는 것은 GPU VRAM 대역폭과 텍스처 샘플러 캐시 라인을 크게 낭비합니다.

**Camera Culling**은 엔티티 거리에 따라 텍스처 밉맵 샘플링을 동적으로 조정하는 분리된 3단계 **OpenGL 텍스처 LOD 바이어싱 엔진**을 포함합니다.

---

## 📋 거리 텍스처 LOD 기본 정보

| 속성 | 값 |
| :--- | :--- |
| **파이프라인 훅** | `LivingEntityRenderer.submit(...)` `@At("HEAD")` & `@At("RETURN")` |
| **OpenGL 매개변수** | `GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, bias)` |
| **근거리 임계값** | $< 16.0$블록 $\implies 0.0\text{f}$ 바이어스 (기본 원본 해상도) |
| **중거리 임계값** | $16.0 - 32.0$블록 $\implies 1.0\text{f}$ 바이어스 (절반 해상도) |
| **원거리 임계값** | $> 32.0$블록 $\implies 2.5\text{f}$ 바이어스 (1/4 해상도 / 밉맵) |
| **면제 대상** | 발광 엔티티, 로컬 플레이어, 보스 및 미니 보스, 블랙리스트 몹 |

---

## 🔬 수학적 거리 LOD 바이어싱

```text
카메라
  │
  ├── [ 0m ~ 16m ] ───► 바이어스 0.0f  (100% 네이티브 풀 해상도 텍스처)
  │
  ├── [ 16m ~ 32m ] ──► 바이어스 1.0f  (50% 하프 해상도 밉맵 샘플링)
  │
  └── [ > 32m ] ──────► 바이어스 2.5f  (25% 로우 해상도 밉맵 샘플링)
```

LOD 바이어스 $B$는 거리 제곱을 기반으로 순수하게 계산됩니다:
$$B(d) = \begin{cases} 
0.0\text{f} & \text{만약 } d^2 < \text{startDist}^2 \ (16.0^2) \\
1.0\text{f} & \text{만약 } \text{startDist}^2 \le d^2 < \text{farDist}^2 \ (32.0^2) \\
2.5\text{f} & \text{만약 } d^2 \ge \text{farDist}^2
\end{cases}$$

### OpenGL 상태 격리
살아있는 엔티티를 렌더링할 때 `LivingEntityRendererMixin`은 `submit:HEAD`에 인젝션하여 계산된 바이어스를 적용하고, `submit:RETURN`에서 즉시 `0.0f`로 재설정합니다. 이를 통해 블록 모델, 아이템 및 UI 요소가 절대 영향을 받지 않도록 보장합니다.

---

## 🔗 관련 페이지

- [[보스 및 블랙리스트 면제|ko_kr-26.1.2-Boss-and-Blacklist-Immunity]]
- [[그래픽 GUI 구성 (YACL)|ko_kr-26.1.2-GUI-Configuration]]
- [[MC 26.1.2 개요로 돌아가기|ko_kr-26.1.2-Home]]
