# 🪧 看板と吊り看板のテキスト間引き (Minecraft 26.3)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Minecraft 26.3 では、看板および吊り看板が両面テキスト機能（`SignTextSlot.FRONT` および `SignTextSlot.BACK`）を備えています。デフォルトでは、ゲームのフォントレンダリングエンジンは、プレイヤーが壁掛け看板の正面を直接見ている場合であっても、テキストクアッド、染料、発光輪郭を**両面**同時にパース、カーニング、レンダリングします。

**Camera Culling** は、面の法線ベクトルの内積（$\vec{N} \cdot \vec{V}$）および空テキスト高速判定を用いて、視線に入っていない面や空白の看板テキスト面のレンダリングをスキップすることでこの問題を解決します。

---

## 📋 看板カリング基本情報

| 項目 | 設定値 |
| :--- | :--- |
| **対象パイプライン** | `BlockEntityRenderDispatcher.tryExtractRenderState` `@At("RETURN")` |
| **APIフック** | `signState.frontText = null;` / `signState.backText = null;` |
| **対応タイプ** | 壁掛け看板、自立看板、壁掛け吊り看板、天井吊り看板 |
| **空面高速パス** | 空白文字以外のテキストが存在しない空白面を自動的にスキップ |
| **内積マージン** | 許容誤差 $\pm 0.05$ により斜め視点での不自然なチラつきを防止 |

---

## 📐 法線ベクトルの内積計算

看板の前面または背面がカメラに向いているかを判定するため、Camera Culling は看板面の法線ベクトル $\vec{N} = (N_x, N_z)$ と看板中心からカメラへのベクトル $\vec{V} = (V_x, V_z)$ の内積を計算します:

$$V_x = X_{\text{cam}} - (X_{\text{sign}} + 0.5), \quad V_z = Z_{\text{cam}} - (Z_{\text{sign}} + 0.5)$$

### 1. 壁掛け看板および壁掛け吊り看板
法線ベクトルはブロックの `Direction` ステップオフセットから直接算出されます:
$$N_x = \text{facing.getStepX()}, \quad N_z = \text{facing.getStepZ()}$$

### 2. 自立看板および天井吊り看板
回転値は $0 \dots 15$ の整数値で表されます。ラジアン角 $\theta$ は以下のように計算されます:
$$\theta = \left(\frac{\text{rotation} \times 360^\circ}{16}\right) \times \frac{\pi}{180}$$
$$N_x = -\sin\theta, \quad N_z = \cos\theta$$

### 3. 可視性の判定
内積 $D$ により観測角度を評価します:
$$D = N_x V_x + N_z V_z$$

* **前面の評価**: $D < -0.05$ の場合にカリング（カメラが看板面の背後に位置）。
* **背面の評価**: $D > 0.05$ の場合にカリング（カメラが看板面の正面に位置）。

---

## ⚡ 空面ファストパス

片面にしか文字が書かれていない看板や、装飾・仕切り目的で文字なしで設置された看板の場合、Camera Culling は4行のテキストを検査します:
```java
public static boolean isTextEmpty(SignText text) {
    if (text == null) return true;
    for (Component msg : text.getMessages(false)) {
        if (msg != null && !msg.getString().trim().isEmpty()) {
            return false;
        }
    }
    return true;
}
```
空白面は、三角関数やベクトルの内積計算を行うことなく即座に null 化されます。

---

## 🔗 関連ページ

- [[ブロックエンティティの間引き|ja_jp-26.3-Block-Entity-Culling]]
- [[コマンドと設定|ja_jp-26.3-Commands-and-Configuration]]
- [[アーキテクチャとMixin|ja_jp-26.3-Architecture-and-Mixins]]
- [[MC 26.3 概要に戻る|ja_jp-26.3-Home]]
