# 🎨 距離ベースのテクスチャLOD (Minecraft 26.2)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

画面上でわずか 4x4 ピクセル程度にしか表示されない遠方のモブに対して、完全な 1024x1024 や高解像度テクスチャを描画することは、GPU VRAM 帯域幅およびテクスチャサンプラーキャッシュラインの大幅な浪費となります。

**Camera Culling** は、エンティティの距離に応じてテクスチャのミップマップサンプリングを動的に調整する、疎結合な3段階の **OpenGL テクスチャ LOD バイアスエンジン** を搭載しています。

---

## 📋 距離テクスチャLOD基本情報

| 項目 | 設定値 |
| :--- | :--- |
| **パイプラインフック** | `LivingEntityRenderer.submit(...)` `@At("HEAD")` & `@At("RETURN")` |
| **OpenGL パラメータ** | `GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, bias)` |
| **近距離閾値** | $< 16.0$ ブロック $\implies 0.0\text{f}$ バイアス（完全なネイティブ解像度） |
| **中距離閾値** | $16.0 - 32.0$ ブロック $\implies 1.0\text{f}$ バイアス（半分解像度サンプリング） |
| **遠距離閾値** | $> 32.0$ ブロック $\implies 2.5\text{f}$ バイアス（1/4解像度ミップマップ） |
| **除外対象** | 発光エンティティ、ローカルプレイヤー、ボス・ミニボス、ブラックリスト指定モブ |

---

## 🔬 数学的な距離 LOD バイアス制御

```text
カメラ
  │
  ├── [ 0m 〜 16m ] ──► バイアス 0.0f  (100% ネイティブ解像度テクスチャ)
  │
  ├── [ 16m 〜 32m ] ──► バイアス 1.0f  (50% 半分解像度ミップマップサンプリング)
  │
  └── [ > 32m ] ──────► バイアス 2.5f  (25% 低解像度ミップマップサンプリング)
```

LOD バイアス $B$ は距離の2乗から純粋に計算されます:
$$B(d) = \begin{cases} 
0.0\text{f} & \text{もし } d^2 < \text{startDist}^2 \ (16.0^2) \\
1.0\text{f} & \text{もし } \text{startDist}^2 \le d^2 < \text{farDist}^2 \ (32.0^2) \\
2.5\text{f} & \text{もし } d^2 \ge \text{farDist}^2
\end{cases}$$

### OpenGL 状態の完全な分離
生物エンティティの描画時、`LivingEntityRendererMixin` が `submit:HEAD` で計算されたバイアスを適用し、`submit:RETURN` で直ちに `0.0f` にリセットします。これにより、ブロックモデル、手持ちアイテム、GUI画面には一切影響を与えません。

---

## 🔗 関連ページ

- [[ボスとブラックリスト免疫|ja_jp-26.2-Boss-and-Blacklist-Immunity]]
- [[グラフィカルGUI設定 (YACL)|ja_jp-26.2-GUI-Configuration]]
- [[MC 26.2 概要に戻る|ja_jp-26.2-Home]]
