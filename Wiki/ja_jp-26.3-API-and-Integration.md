# 🔌 APIとMod連携 (Minecraft 26.3)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Camera Culling は、クライアント側描画最適化Mod（Sodium、Iris、Canvasなど）や、独自のエンティティ・ブロックエンティティを追加するコンテンツModと競合することなくシームレスに動作するように設計されています。

---

## 🤝 サードパーティ製レンダラーとの互換性

### 1. Sodium & Embeddium
* **静的地形チャンク**: Sodium は 16x16 チャンクのメッシュ構築および静的ブロック面のレンダリングパイプラインを最適化します。
* **動的エンティティ**: Camera Culling は動的エンティティ、チェスト、看板、パーティクルを最適化します。
* **互換性**: Mixin の競合やステートの衝突は一切なく、100% の互換性を保持します。

### 2. Iris & シェーダー
* **シェーダーユニフォーム**: シェーダーは抽出されたフレームバッファに対してポストプロセス処理を実行します。
* **遮蔽による負荷軽減**: カリングされたエンティティは G-Buffer へのジオメトリ送信自体を行わないため、密集領域においてシェーダーのフレームレートが大幅に向上します。

---

## 🛠️ Java プログラムからの API フック

他の Mod から静的ユーティリティファサードを介して Camera Culling の状態取得や制御を行うことができます:

### 1. カリングエンジンの状態照会
```java
import net.vanillaoutsider.culling.CameraCullingClient;

boolean isEnabled = CameraCullingClient.isCullingEnabled();
long culledMobs = CameraCullingClient.getCulledEntitiesCount();
long renderedMobs = CameraCullingClient.getRenderedEntitiesCount();
```

### 2. プログラムによるブラックリスト免疫登録
```java
import net.vanillaoutsider.culling.config.CameraCullingConfig;

// 独自エンティティ ID をカリング対象から除外（ホワイトリスト登録）
CameraCullingConfig.addClientBlacklist("mymod:custom_companion");
```

### 3. 直接レイキャストによる視線検証
```java
import net.vanillaoutsider.culling.util.CullingRaycastHelper;

// ゼロアロケーションで視線判定を実行
boolean visible = CullingRaycastHelper.hasLineOfSight(level, camPos, targetX, targetY, targetZ);
```

---

## 🔗 関連ページ

- [[アーキテクチャとMixin|ja_jp-26.3-Architecture-and-Mixins]]
- [[開発環境のセットアップとビルド|ja_jp-26.3-Developer-Setup-and-Building]]
- [[MC 26.3 概要に戻る|ja_jp-26.3-Home]]
