# 📌 Camera Culling Backlog

This file tracks planned features, technical refinements, performance optimizations, and deferred improvements for **Camera Culling**.

---

## 📊 Backlog Summary

| ID | Category | Title | Priority | Target Version | Status |
| :--- | :--- | :--- | :--- | :--- | :--- |
| `[BL-CC-001]` | `[FEATURE]` | Multi-Era Anchor Porting: Camera Culling | `[HIGH]` | `Multi-Era` | `📌 DEFERRED` |

---

## 🏷 Legend & Status Tags
- **Categories**: `[FEATURE]`, `[REFINEMENT]`, `[BUGFIX]`, `[PERF]`, `[TECH_DEBT]`
- **Priorities**: `[HIGH]` (Critical logic/porting task), `[MEDIUM]` (Quality of life), `[LOW]` (Minor polish)
- **Statuses**: `📌 DEFERRED` (Queued for future work), `🚧 IN_PROGRESS` (Active development), `✅ RESOLVED` (Implemented and verified)

---

## 📝 Detailed Backlog Entries

### [BL-CC-001] Multi-Era Anchor Porting: Camera Culling
- **Category**: `[FEATURE]`
- **Priority**: `[HIGH]`
- **Status**: `📌 DEFERRED`
- **Target Component(s)**: Multi-subproject directories, `FrustumMixin.java`, `EntityRendererMixin.java`, `BlockEntityRendererMixin.java`, `CameraCullingConfig.java`, `build.gradle`, `fabric.mod.json`, `RELEASE_QUEUE.md`
- **Date Added**: 2026-09-25

#### ❓ Problem / Context
Existing versions: `26.1`, `26.2`, `26.3` (Modern sovereign anchors complete). Missing anchors: Older `1.21.11`, `1.21.1`, `1.20.1`.
Per Multi-Era Version Matrix and 1 Jar 1 Version Policy, port mod across all missing anchors (Modern first, Older second).

#### 💡 Architectural Specifications & Toolchain Anchors
- **Phase 1: Modern Sovereign Anchors (Java 25+, Loom 1.15+, No Mappings Block)**:
  - `MC 26.1 / 26.1.2`: Java 25, Fabric Loom 1.15+, `Identifier.fromNamespaceAndPath`, `EntityTypes`, `dasik-library` 26.1 (already active baseline).
  - `MC 26.2`: Java 25, Fabric Loom 1.15+, `Identifier.fromNamespaceAndPath`, `DynamicGameRuleManager` (already active baseline).
  - `MC 26.3`: Java 25, Fabric Loom 1.15+, `minecraft_version=26.3-snapshot-6`, `fabric_version=0.156.1+26.3` (already active baseline).
- **Phase 2: Older Anchors (Mojang Mappings, Java 21 / 17)**:
  - `MC 1.21.11`: Java 21, Loom 1.15-SNAPSHOT (`fabric-loom-remap`), Mojang mappings, `Identifier.of`, `Optional<T>` CompoundTag, relocated entity packages.
  - `MC 1.21.1`: Java 21, Loom 1.10+, Mojang mappings, `Identifier.of`, `DataComponents`, native `Attributes.SCALE`.
  - `MC 1.20.1`: Java 17, Loom 1.4–1.10, Mojang mappings, `new Identifier`, primitive NBT CompoundTag, `FabricItemSettings`, `GameRules.Category` enum.

#### 🧪 Verification & Acceptance Criteria

##### Phase 1: Modern Sovereign Anchors (Priority 1)
- [x] **Anchor: MC 26.1 / 26.1.2 - Already established baseline** (Subproject: `Camera Culling v26.1`)
- [x] **Anchor: MC 26.2 - Already established baseline** (Subproject: `Camera Culling v26.2`)
- [x] **Anchor: MC 26.3 - Already established baseline** (Subproject: `Camera Culling v26.3`)

##### Phase 2: Older Anchors (Priority 2)
- [ ] **Anchor: MC 1.21.11 (Java 21, Loom 1.15-SNAPSHOT `fabric-loom-remap`, Mojang mappings, `Identifier.of`, `Optional<T>` CompoundTag, relocated entity packages)**
  - [ ] Subproject directory & build script scaffolding (`build.gradle`, `gradle.properties`, `settings.gradle`)
  - [ ] Source adaptation, API/mixin relocation, and dasik-library wiring for target version
  - [ ] Headless unit & integration test suite pass (`./gradlew test --no-daemon`)
  - [ ] Clean binary compilation (`./gradlew build --no-daemon`)
  - [ ] Mandatory Universal 4-Point Distribution (Local Archive, Hub Archive, External Vault `D:\`, Launcher Test Profile)
  - [ ] Release queue registration in `RELEASE_QUEUE.md` (`- [ ]`) and `CHANGELOG.md` entry
- [ ] **Anchor: MC 1.21.1 (Java 21, Loom 1.10+, Mojang mappings, `Identifier.of`, `DataComponents`, native `Attributes.SCALE`)**
  - [ ] Subproject directory & build script scaffolding (`build.gradle`, `gradle.properties`, `settings.gradle`)
  - [ ] Source adaptation, API/mixin relocation, and dasik-library wiring for target version
  - [ ] Headless unit & integration test suite pass (`./gradlew test --no-daemon`)
  - [ ] Clean binary compilation (`./gradlew build --no-daemon`)
  - [ ] Mandatory Universal 4-Point Distribution (Local Archive, Hub Archive, External Vault `D:\`, Launcher Test Profile)
  - [ ] Release queue registration in `RELEASE_QUEUE.md` (`- [ ]`) and `CHANGELOG.md` entry
- [ ] **Anchor: MC 1.20.1 (Java 17, Loom 1.4-1.10, Mojang mappings, `new Identifier`, primitive NBT CompoundTag, `FabricItemSettings`, `GameRules.Category` enum)**
  - [ ] Subproject directory & build script scaffolding (`build.gradle`, `gradle.properties`, `settings.gradle`)
  - [ ] Source adaptation, API/mixin relocation, and dasik-library wiring for target version
  - [ ] Headless unit & integration test suite pass (`./gradlew test --no-daemon`)
  - [ ] Clean binary compilation (`./gradlew build --no-daemon`)
  - [ ] Mandatory Universal 4-Point Distribution (Local Archive, Hub Archive, External Vault `D:\`, Launcher Test Profile)
  - [ ] Release queue registration in `RELEASE_QUEUE.md` (`- [ ]`) and `CHANGELOG.md` entry
