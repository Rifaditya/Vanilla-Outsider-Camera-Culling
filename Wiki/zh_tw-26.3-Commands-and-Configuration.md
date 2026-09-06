# 🎮 指令與設定系統 (Minecraft 26.3)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Camera Culling 提供了完整的 Brigadier 遊戲內指令系統（`/cameraculling`）以及易讀易改的 JSON 設定檔持久化機制（`config/camera-culling.json`）。

---

## 📋 指令速查表

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

## ⚙️ 詳細指令解析

| 指令語法 | 接收參數 | 功能說明 |
| :--- | :--- | :--- |
| `/cameraculling status` | 無 | 顯示即時統計數據：已渲染與已剔除計數、當前設定檔及黑名單數量。 |
| `/cameraculling toggle` | 無 | 切換模組主剔除功能的啟用/禁用狀態。 |
| `/cameraculling set <profile>` | `low`, `medium`, `high`, `super` | 切換當前使用的剔除強度設定檔。 |
| `/cameraculling particles [bool]` | `true`, `false` (可選) | 開啟或關閉針對實心方塊後方粒子的遮擋剔除。 |
| `/cameraculling animations [bool]` | `true`, `false` (可選) | 開啟或關閉單人遊戲暫停與功能表介面的圖集動畫凍結。 |
| `/cameraculling crowdculling <bool>` | `true`, `false` | 開啟或關閉生物重疊/密集過度繪製防禦系統。 |
| `/cameraculling cluster <int>` | `1` 到 `128` | 設定 1.5 方塊集群內允許渲染的生物數量上限（預設: `8`）。 |
| `/cameraculling texturlod <bool>` | `true`, `false` | 開啟或關閉基於距離的生物紋理 Mipmap LOD 縮放。 |
| `/cameraculling texturlod range <start> <far>` | `1.0..256.0`, `2.0..512.0` | 設定紋理 LOD 的近距離與遠距離切換閾值（如 `16.0 32.0`）。 |
| `/cameraculling bossimmunity <bool>` | `true`, `false` | 開啟或關閉 Boss 與 Mini-Boss 的視線剔除豁免。 |
| `/cameraculling bosshealth <hp>` | `1.0` 到 `10000.0` | 設定主要 Boss 的生命值判斷閾值（預設: `150.0`）。 |
| `/cameraculling minibosshealth <hp>` | `1.0` 到 `10000.0` | 設定 Mini-Boss 的生命值判斷閾值（預設: `50.0`）。 |
| `/cameraculling blacklist add <id>` | 實體識別碼 (如 `minecraft:wolf`) | 將指定實體加入玩家本地客戶端個人免疫白名單。 |
| `/cameraculling blacklist remove <id>` | 實體識別碼 | 將指定實體從個人免疫名單中移除。 |
| `/cameraculling blacklist list` | 無 | 列出當前個人免疫名單中的所有實體。 |
| `/cameraculling blacklist clear` | 無 | 清空個人免疫名單中的全部條目。 |
| `/cameraculling serverblacklist ...` | 子指令 + 實體識別碼 | 管理伺服器端強制同步的實體黑名單（需要 OP 權限）。 |
| `/cameraculling debug [bool]` | `true`, `false` (可選) | 開啟或關閉向聊天欄和日誌輸出的即時狀態轉換診斷追蹤。 |
| `/cameraculling reload` | 無 | 強制從磁碟重新載入設定檔。 |

---

## 📄 JSON 設定格式

設定檔存放於 `.minecraft/config/` 目錄中：

### 客戶端設定檔 (`config/camera-culling.json`)
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

### 伺服器端設定檔 (`config/camera-culling-server.json`)
```json
{
  "serverBlacklist": []
}
```

---

## 🔗 相關頁面

- [[圖形化介面設定 (YACL)|zh_tw-26.3-GUI-Configuration]]
- [[調試日誌與診斷追蹤|zh_tw-26.3-Debug-Logging-and-Diagnostics]]
- [[返回 MC 26.3 概覽|zh_tw-26.3-Home]]
