# 👥 密集モブのオーバードロー防御 (Minecraft 26.1.2)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

過密なモブトラップ、村人取引所、家畜繁殖用の囲いなどでは、わずか数ブロック内に何百ものエンティティが密集することで著しいフレームレート低下を引き起こします。GPUの Early-Z ラスタライズ処理が基本的な深度テストを行うものの、大量のモブ骨格階層を抽出・送信する処理自体がCPUレンダーディスパッチャを圧迫します。

**Camera Culling** は、安全策を備えた任意の密集オーバードロー防御システムを提供します。

---

## 📋 密集モブ防御基本情報

| 項目 | 設定値 |
| :--- | :--- |
| **設定キー** | `cullEntitiesBehindEntities`（デフォルト: `false`） |
| **クラスタ密度上限** | `maxEntitiesPerCluster`（デフォルト: 1.5ブロック内に `8` 体） |
| **高速早期判定距離** | $> 16.0$ メートル ($256.0\text{m}^2$) |
| **クラスタ探索半径** | `targetBox.inflate(1.5)` |
| **除外エンティティ** | 半透明・装飾モブ（`Vex`, `ArmorStand`, `ItemFrame`, `Slime`, `MagmaCube`） |

---

## 🛑 16メートル距離高速早期判定アーキテクチャ

開けた平原に散らばる牛の群れなどに対して空間検索を常時実行すると、無駄なCPUオーバーヘッドが生じます。近代バージョンの Camera Culling では、密集オーバードローカリングに**16メートルの高速早期判定**を導入しています:

```java
public static boolean isEntityOccludedByCloserEntities(Entity target, Level level, Vec3 camPos, AABB targetBox, double targetDistSq) {
    if (target == null || level == null || camPos == null) {
        return false;
    }

    // 高速判定: 密集カリングは16メートル以内でのみ適用
    if (targetDistSq > 256.0) {
        return false;
    }

    // 1. 狭い1.5ブロック球内でのクラスタ密度上限チェック
    int maxCluster = CameraCullingConfig.getMaxEntitiesPerCluster();
    AABB clusterBox = targetBox.inflate(1.5);
    List<Entity> clusterEntities = level.getEntities(target, clusterBox, 
        e -> e instanceof LivingEntity && !isTransparentOrDecorative(e));
    
    if (clusterEntities.size() < maxCluster) {
        return false;
    }

    int closerInCluster = 0;
    for (Entity e : clusterEntities) {
        double distSq = camPos.distanceToSqr(e.getX(), e.getY(), e.getZ());
        if (distSq < targetDistSq) {
            closerInCluster++;
            if (closerInCluster >= maxCluster) {
                return true; // クラスタ密度上限を超過したためカリング
            }
        }
    }
    return false;
}
```

### パフォーマンス上の利点:
1. **開けた地形での負荷ゼロ**: 16メートル以遠で放牧されているモブはエンティティ探索処理を完全にスキップします。
2. **過密ペンでの確実な保護**: 50頭以上の牛やゾンビが詰め込まれた1x1または2x2のトラップ枠内では、最前面の8体のみに描画を制限し、ラグスパイクを解消します。

---

## 🔗 関連ページ

- [[エンティティの遮蔽カリング|ja_jp-26.1.2-Entity-Occlusion-Culling]]
- [[ボスとブラックリスト免疫|ja_jp-26.1.2-Boss-and-Blacklist-Immunity]]
- [[MC 26.1.2 概要に戻る|ja_jp-26.1.2-Home]]
