# 👑 Boss 與黑名單免疫 (Minecraft 26.3)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

為了保障戰鬥的公平性、危險感知以及玩家對重要隨從寵物的關注，關鍵首領怪物與馴服夥伴絕不能因激進的剔除演算法而在牆後突然消失。

**Camera Culling** 引入了動態 Boss 識別機制與雙層免疫黑名單系統。

---

## 📋 免疫系統資訊概覽

| 屬性 | 設定值 |
| :--- | :--- |
| **主要 Boss 生命值閾值** | `bossHealthThreshold` (預設: `150.0 HP` / 75 顆心) |
| **Mini-Boss 生命值閾值** | `miniBossHealthThreshold` (預設: `50.0 HP` / 25 顆心) |
| **客戶端黑名單設定路徑** | `config/camera-culling.json` (`clientBlacklist` 陣列) |
| **伺服器端黑名單設定路徑** | `config/camera-culling-server.json` (`serverBlacklist` 陣列) |
| **免疫生效範圍** | 豁免方塊遮擋剔除、生物密集過度繪製剔除與紋理 LOD 降級 |

---

## 🐲 動態 Boss 與 Mini-Boss 識別

Camera Culling 透過兩套相互配合的機制自動識別 Boss 級生物：

### 1. 動態生命值閾值判定
任何 `LivingEntity`，只要其最大生命值 `getMaxHealth()` 達到或超過設定的數值，即可自動獲得無條件免疫特權：
* `maxHealth >= 150.0` $\implies$ 主要 Boss（終界龍、凋零怪、伏守者等）。
* `maxHealth >= 50.0` $\implies$ Mini-Boss（遠古守衛者、劫掠獸、鐵魔像、豬靈蠻兵、旋風人以及模組中的精英怪）。

### 2. 註冊名與識別碼關鍵字啟發式匹配
名稱中包含下列任意關鍵字子串的實體，均會被自動賦予 Boss 豁免特權：
```text
dragon, wither, warden, elder_guardian, ravager, evoker, iron_golem, 
piglin_brute, breeze, boss, miniboss, mini_boss, titan, leviathan, 
harbinger, monarch, behemoth, champion, elite, brute
```

---

## 🛡️ 雙層免疫黑名單系統

```text
免疫裁定流程
├── 玩家自身 / 載具 / 騎乘坐騎 ──► 100% 正常渲染
├── 處於發光效果 (Glowing) 狀態 ──► 100% 正常渲染
├── 判定為 Boss 或 Mini-Boss ─────► 100% 正常渲染
├── 命中客戶端個人黑名單 ────────► 100% 正常渲染
└── 命中伺服器端管理員同步黑名單 ──► 100% 正常渲染
```

### 1. 客戶端個人黑名單
玩家可以在遊戲內隨時使用指令將心儀的寵物或特定生物加入本地白名單：
```sql
/cameraculling blacklist add minecraft:wolf
/cameraculling blacklist add minecraft:allay
/cameraculling blacklist add minecraft:cat
```

### 2. 伺服器端管理員黑名單
伺服器管理員可以在伺服器端的 `config/camera-culling-server.json` 中配置全域豁免實體，或者使用 `/cameraculling serverblacklist add <id>` 進行即時修改。所有連接到該伺服器的客戶端模組均會自動同步並遵循該名單。

---

## 🔗 相關頁面

- [[實體遮擋剔除|zh_tw-26.3-Entity-Occlusion-Culling]]
- [[基於距離的紋理 LOD|zh_tw-26.3-Distance-Texture-LOD]]
- [[指令與設定系統|zh_tw-26.3-Commands-and-Configuration]]
- [[返回 MC 26.3 概覽|zh_tw-26.3-Home]]
