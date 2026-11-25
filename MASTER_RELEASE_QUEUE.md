# 🎛️ Master Release Queue: Vanilla Outsider — Camera Culling

> **Mod Project Master Ground-Truth Document**  
> *Last Synchronized: 2026-11-25*  
> **Modrinth ID**: `ATX2NaJR` (`vo-camera-culling`) | **CurseForge ID**: `1700558` (`vo-camera-culling`) | **Lead SemVer**: `1.10.8`

---

## 📊 Multi-Version Release Matrix & Queue Status

| Target MC | Generational Era | Live on Platforms | Next Queued Version | Status & Cadence Action | Feature Highlights / Notes |
| :--- | :--- | :--- | :--- | :--- | :--- |
| **MC 26.3** | Modern Lead | Published up to `1.3.0+26.3` | `1.4.0+26.3` (`19` queued up to `1.10.7+26.3`) | ⏳ **Cadence Cooldown** | Updated 2026-11-25; next eligible 2026-12-01 15:06 UTC. |
| **MC 26.2** | Modern Predecessor | Published up to `1.7.0+26.2` | `1.8.0+26.2` (`16` queued up to `1.10.8+26.2`) | ⏳ **Cadence Cooldown** | Updated 2026-11-25; `1.10.8+26.2` (`[BL-CC-016]`) queued. |
| **MC 26.1** | Modern Predecessor | Published up to `1.1.0+26.1.2` | `1.2.0+26.1.2` (`21` queued up to `1.10.8+26.1.2`) | ⏳ **Cadence Cooldown** | Updated 2026-11-25; `1.10.8+26.1.2` (`[BL-CC-016]`) queued. |
| **MC 1.21.11** | Modern Anchor | Published up to `1.0.2+1.21.11` | `—` | ✅ **Up to Date** | All 3 queue entries published (latest 2026-11-11). |
| **MC 1.21.1** | Modern Anchor | Published up to `1.0.2+1.21.1` | `—` | ✅ **Up to Date** | All 3 queue entries published (latest 2026-11-11). |
| **MC 1.20.1** | Legacy Anchor | Published up to `1.0.4+1.20.1` | `—` | ✅ **Up to Date** | All 5 queue entries published (2026-11-25; `1.0.2`/`1.0.3` quarantined). |

---

## 🏛️ Project Operating Rules & Architectural Invariants

1. **🔢 Universal Direct SemVer Inheritance**:
   - Modern subprojects share unified SemVer milestone lineage targeting `1.10.8`.
   - Each Minecraft version anchor manages its own organic progression to ensure 100% clean, verified parity.

2. **📅 Daily Update Guard**:
   - Strict maximum of 1 release per day per targeted Minecraft version anchor across Modrinth and CurseForge.

---

## 🛠️ CLI Publisher Commands for Camera Culling

```powershell
# 1. Check current status across all targeted Minecraft versions
python ".agents/skills/platform-publisher/scripts/platform_publisher.py" --mod "Camera Culling" --status

# 2. Publish next sequential batch across all active versions
python ".agents/skills/platform-publisher/scripts/platform_publisher.py" --mod "Camera Culling" --publish-next --yes

# 3. Publish for a specific version anchor only (e.g. MC 26.3)
python ".agents/skills/platform-publisher/scripts/platform_publisher.py" --mod "Camera Culling" --mc 26.3 --publish-next --yes
```
