# 👑 Boss 与黑名单免疫 (Minecraft 26.1.2)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

为了保障战斗的公平性、危险感知以及玩家对重要随从宠物的关注，关键首领怪物与驯服伙伴绝不能因激进的剔除算法而在墙后突然消失。

**Camera Culling** 引入了动态 Boss 识别机制与双层免疫黑名单系统。

---

## 📋 免疫系统信息概览

| 属性 | 设定值 |
| :--- | :--- |
| **主要 Boss 生命值阈值** | `bossHealthThreshold` (默认: `150.0 HP` / 75 颗心) |
| **Mini-Boss 生命值阈值** | `miniBossHealthThreshold` (默认: `50.0 HP` / 25 颗心) |
| **客户端黑名单配置路径** | `config/camera-culling.json` (`clientBlacklist` 数组) |
| **服务端黑名单配置路径** | `config/camera-culling-server.json` (`serverBlacklist` 数组) |
| **免疫生效范围** | 豁免方块遮挡剔除、生物密集过度绘制剔除与纹理 LOD 降级 |

---

## 🐲 动态 Boss 与 Mini-Boss 识别

Camera Culling 通过两套相互配合的机制自动识别 Boss 级生物：

### 1. 动态生命值阈值判定
任何 `LivingEntity`，只要其最大生命值 `getMaxHealth()` 达到或超过设定的数值，即可自动获得无条件免疫特权：
* `maxHealth >= 150.0` $\implies$ 主要 Boss（末影龙、凋灵、监守者等）。
* `maxHealth >= 50.0` $\implies$ Mini-Boss（远古守卫者、劫掠兽、铁傀儡、猪灵蛮兵、旋风人以及模组中的精英怪）。

### 2. 注册名与标识符关键词启发式匹配
名称中包含下列任意关键词子串的实体，均会被自动赋予 Boss 豁免特权：
```text
dragon, wither, warden, elder_guardian, ravager, evoker, iron_golem, 
piglin_brute, breeze, boss, miniboss, mini_boss, titan, leviathan, 
harbinger, monarch, behemoth, champion, elite, brute
```

---

## 🛡️ 双层免疫黑名单系统

```text
免疫裁定流程
├── 玩家自身 / 载具 / 骑乘坐骑 ──► 100% 正常渲染
├── 处于发光效果 (Glowing) 状态 ──► 100% 正常渲染
├── 判定为 Boss 或 Mini-Boss ─────► 100% 正常渲染
├── 命中客户端个人黑名单 ────────► 100% 正常渲染
└── 命中服务端管理员同步黑名单 ──► 100% 正常渲染
```

### 1. 客户端个人黑名单
玩家可以在游戏内随时使用指令将心仪的宠物或特定生物加入本地白名单：
```sql
/cameraculling blacklist add minecraft:wolf
/cameraculling blacklist add minecraft:allay
/cameraculling blacklist add minecraft:cat
```

### 2. 服务端管理员黑名单
服务器管理员可以在服务端的 `config/camera-culling-server.json` 中配置全局豁免实体，或者使用 `/cameraculling serverblacklist add <id>` 进行实时修改。所有连接到该服务器的客户端模组均会自动同步并遵循该名单。

---

## 🔗 相关页面

- [[实体遮挡剔除|zh_cn-26.1.2-Entity-Occlusion-Culling]]
- [[基于距离的纹理 LOD|zh_cn-26.1.2-Distance-Texture-LOD]]
- [[指令与配置系统|zh_cn-26.1.2-Commands-and-Configuration]]
- [[返回 MC 26.1.2 概览|zh_cn-26.1.2-Home]]
