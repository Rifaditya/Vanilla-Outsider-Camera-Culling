# 🎮 指令与配置系统 (Minecraft 26.1.2)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Camera Culling 提供了完整的 Brigadier 游戏内指令系统（`/cameraculling`）以及易读易改的 JSON 配置文件持久化机制（`config/camera-culling.json`）。

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

## ⚙️ 详细指令解析

| 指令语法 | 接收参数 | 功能说明 |
| :--- | :--- | :--- |
| `/cameraculling status` | 无 | 显示实时统计数据：已渲染与已剔除计数、当前配置档及黑名单数量。 |
| `/cameraculling toggle` | 无 | 切换模组主剔除功能的启用/禁用状态。 |
| `/cameraculling set <profile>` | `low`, `medium`, `high`, `super` | 切换当前使用的剔除强度配置档。 |
| `/cameraculling particles [bool]` | `true`, `false` (可选) | 开启或关闭针对实心方块后方粒子的遮挡剔除。 |
| `/cameraculling animations [bool]` | `true`, `false` (可选) | 开启或关闭单人游戏暂停与菜单界面的图集动画冻结。 |
| `/cameraculling crowdculling <bool>` | `true`, `false` | 开启或关闭生物重叠/密集过度绘制防御系统。 |
| `/cameraculling cluster <int>` | `1` 到 `128` | 设置 1.5 方块集群内允许渲染的生物数量上限（默认: `8`）。 |
| `/cameraculling texturlod <bool>` | `true`, `false` | 开启或关闭基于距离的生物纹理 Mipmap LOD 缩放。 |
| `/cameraculling texturlod range <start> <far>` | `1.0..256.0`, `2.0..512.0` | 设置纹理 LOD 的近距离与远距离切换阈值（如 `16.0 32.0`）。 |
| `/cameraculling bossimmunity <bool>` | `true`, `false` | 开启或关闭 Boss 与 Mini-Boss 的视线剔除豁免。 |
| `/cameraculling bosshealth <hp>` | `1.0` 到 `10000.0` | 设置主要 Boss 的生命值判断阈值（默认: `150.0`）。 |
| `/cameraculling minibosshealth <hp>` | `1.0` 到 `10000.0` | 设置 Mini-Boss 的生命值判断阈值（默认: `50.0`）。 |
| `/cameraculling blacklist add <id>` | 实体标识符 (如 `minecraft:wolf`) | 将指定实体加入玩家本地客户端个人免疫白名单。 |
| `/cameraculling blacklist remove <id>` | 实体标识符 | 将指定实体从个人免疫名单中移除。 |
| `/cameraculling blacklist list` | 无 | 列出当前个人免疫名单中的所有实体。 |
| `/cameraculling blacklist clear` | 无 | 清空个人免疫名单中的全部条目。 |
| `/cameraculling serverblacklist ...` | 子指令 + 实体标识符 | 管理服务端强制同步的实体黑名单（需要 OP 权限）。 |
| `/cameraculling debug [bool]` | `true`, `false` (可选) | 开启或关闭向聊天栏和日志输出的实时状态转换诊断追踪。 |
| `/cameraculling reload` | 无 | 强制从磁盘重新加载配置文件。 |

---

## 📄 JSON 配置格式

配置文件存放于 `.minecraft/config/` 目录中：

### 客户端配置文件 (`config/camera-culling.json`)
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

### 服务端配置文件 (`config/camera-culling-server.json`)
```json
{
  "serverBlacklist": []
}
```

---

## 🔗 相关页面

- [[图形化界面配置 (YACL)|zh_cn-26.1.2-GUI-Configuration]]
- [[调试日志与诊断追踪|zh_cn-26.1.2-Debug-Logging-and-Diagnostics]]
- [[返回 MC 26.1.2 概览|zh_cn-26.1.2-Home]]
