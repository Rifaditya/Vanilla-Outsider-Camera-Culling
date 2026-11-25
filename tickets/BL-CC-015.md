# 🎫 [BL-CC-015] Adopt Per-Mod Advancement-Triggered Early Access Support Prompt (`DasikPromptRegistry`)

- **Ticket ID**: `[BL-CC-015]`
- **Category**: `[TECH_DEBT]` / `[GOVERNANCE]`
- **Priority**: `[MEDIUM]`
- **Target Version**: `Opportunistic Per-Mod (Next Release Cycle per Single-Ticket Release Policy)`
- **Scope**: `CameraCullingClient.java`, `CameraCullingClientConfig.java`, `ModMenu / YACL Config Screen`, `gradle.properties`, `fabric.mod.json`, `CameraCullingSidednessContractTest.java`
- **Status**: `📌 DEFERRED`
- **Grilling Status**: `✅ PASSED`
- **Target Repository / Root**: `Vanilla Outsider Collections/Camera Culling`
- **Parent Umbrella**: [BL-WS-047](file:///E:/Minecraft%20Project/tickets/BL-WS-047.md)
- **Upstream Engine**: [BL-DASIK-022](file:///E:/Minecraft%20Project/MC-DasikLibrary/tickets/BL-DASIK-022.md) (`✅ RESOLVED` in `mavenLocal()`)
- **Governance Mandate**: [Monetization & Creator Support Policy](file:///E:/Minecraft%20Project/.agents/rules/Monetization%20&%20Creator%20Support%20Policy.md) (§6–§7), [Single-Ticket Release Policy](file:///E:/Minecraft%20Project/.agents/rules/Single-Ticket%20Release%20Policy.md)
- **Date Added**: `2026-11-23`

---

## 🎯 1. Goal & Definition of Done (The 4-Pillar Objective Architecture)

1. **North Star Objective**: Register **Camera Culling** (`MOD_ID`: `camera-culling`) with `DasikLibrary`'s centralized `DasikPromptRegistry` using the Director-approved trigger advancement `minecraft:story/mine_stone` and a pure-Java per-mod client opt-out toggle (`CameraCullingClientConfig.showEarlyAccessPrompt = true`), exposed cleanly in YACL / ModMenu without duplicating prompt logic or server `GameRules`.
2. **In-Game Experience (Before vs. After)**:
   - *Before*: **Camera Culling** has no milestone-triggered Early Access prompt wired into `DasikPromptRegistry`, relying only on generic world-start greetings that players easily tune out on login, and lacks a per-mod opt-out toggle in its client settings.
   - *After*: When a player earns `minecraft:story/mine_stone` while playing with **Camera Culling**, `DasikLibrary`'s central 5-minute FIFO pacing queue gently delivers a single Early Access & Discord chat note 3 seconds later—unless the player toggled `showEarlyAccessPrompt` off for **Camera Culling** (or silenced support prompts globally in `DasikLibrary`).
3. **🚫 Anti-Goals (Off-Limits / Do Not Touch)**:
   - ❌ **Zero Custom Advancement Listeners, Polling, or Pacing Queues**: Do NOT write custom `ClientAdvancements` mixins, chat prompt builders, tick counters, or cooldown timers inside **Camera Culling**. All advancement polling, 3-second initial delay, and 5-minute global FIFO pacing live strictly in `DasikLibrary`.
   - ❌ **Zero Server `GameRules` or Server-to-Vanilla Chat Broadcasts**: `showEarlyAccessPrompt` MUST remain strictly a client-side config boolean (`syncGameRule = false`). Never register a `GameRule` or send support messages from server ticks.
   - ❌ **Zero Multi-Ticket Bundling**: Per [Single-Ticket Release Policy](file:///E:/Minecraft%20Project/.agents/rules/Single-Ticket%20Release%20Policy.md), do NOT bundle this governance ticket into an unrelated bugfix or gameplay release (`1 Ticket = 1 Version Bump`).
   - ❌ **Zero Client Class Leakage in Common Code**: `CameraCullingClientConfig` MUST remain pure Java with zero `net.minecraft.client.*`, `com.mojang.blaze3d.*`, YACL, or ModMenu imports so dedicated servers load common classes without crashing.
4. **✅ Definition of Done (Binary Verification Checklist)**:
   - [ ] `dasik_library_version` in `gradle.properties` and `dasik-library` dependency bounds in `fabric.mod.json` (and `neoforge.mods.toml`/`mods.toml` if multi-loader) meet `>= 1.13.2` (`26.2`/`26.3`), `>= 1.13.2+26.1.2` (`26.1.2`), `>= 1.4.4+1.21.11` (`1.21.11`), `>= 1.4.5+1.21.1` (`1.21.1`), and `>= 1.4.7+1.20.1` (`1.20.1`) across all compiled anchors.
   - [ ] `CameraCullingClientConfig.showEarlyAccessPrompt` (default `true`) exists as pure Java and is toggleable cleanly inside the mod's YACL / ModMenu screen.
   - [ ] `net.vanillaoutsider.culling.CameraCullingClient` calls `DasikPromptRegistry.register(MOD_ID, MOD_NAME, TRIGGER_ADVANCEMENT_ID, () -> CameraCullingClientConfig.showEarlyAccessPrompt)` with `TRIGGER_ADVANCEMENT_ID = "minecraft:story/mine_stone"`.
   - [ ] `CameraCullingSidednessContractTest` passes dedicated-server classloading, dynamic supplier toggle, GPLv3 header, and zero-`GameRule` assertions across all anchors (`./gradlew test build --no-daemon --console=plain`).

---

## ❓ 2. Problem Statement & Player Impact

1. **Timing Disconnect at World Login**: Under legacy `BL-WS-030` behavior, creator support prompts fired only on world login, when players are busy loading chunks and dismiss chat passively. Under [Monetization & Creator Support Policy](file:///E:/Minecraft%20Project/.agents/rules/Monetization%20&%20Creator%20Support%20Policy.md) §7 and [`BL-DASIK-022`](file:///E:/Minecraft%20Project/MC-DasikLibrary/tickets/BL-DASIK-022.md), each mod triggers at a meaningful gameplay milestone (`minecraft:story/mine_stone`).
2. **Stale `dasik-library` Version Pins**: Current anchor manifests pin older `dasik_library_version` releases that predate `net.dasik.social.api.prompt.DasikPromptRegistry` (`1.13.2` / `1.13.2+26.1.2` / `1.4.4+1.21.11` / `1.4.5+1.21.1` / `1.4.7+1.20.1`), which must be bumped before compilation.

---

## 🔬 3. Architectural Blueprint & Target Classes

- **Detected Ground-Truth Targets**:
  - Mod ID(s): `camera-culling` | Display Name: `"Camera Culling"` | `fabric.mod.json` Environment: `client`
  - Primary Entrypoint: `net.vanillaoutsider.culling.CameraCullingClient` (`ClientModInitializer`)
  - Existing Config / GUI Files: `CameraCullingConfig.java`, `ModMenuIntegration.java`, `YaclScreenHelper.java`
  - Sidedness Test Target: Existing test detected: `Camera Culling v1.20.1\Camera Culling 1.20.1\src\test\java\net\vanillaoutsider\culling\test\CameraCullingSidednessContractTest.java`
- **Approved Trigger Constant**:
  ```java
  public static final String MOD_ID = "camera-culling";
  public static final String MOD_NAME = "Camera Culling";
  public static final String TRIGGER_ADVANCEMENT_ID = "minecraft:story/mine_stone";
  ```
- **1-Line Central Registration (`net.vanillaoutsider.culling.CameraCullingClient#onInitializeClient()`)**:
  ```java
  DasikPromptRegistry.register(
          MOD_ID,
          MOD_NAME,
          TRIGGER_ADVANCEMENT_ID,
          () -> CameraCullingClientConfig.showEarlyAccessPrompt
  );
  ```
- **Pure-Java Client Opt-Out Holder (`net.vanillaoutsider.culling.config.CameraCullingClientConfig`)**:
  ```java
  // Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
  package net.vanillaoutsider.culling.config;

  /**
   * Pure-Java client configuration holder for Camera Culling.
   * Strictly a client-side opt-out toggle (never a server GameRule) with zero
   * net.minecraft.client.* or com.mojang.blaze3d.* linkage.
   */
  public final class CameraCullingClientConfig {
      public static boolean showEarlyAccessPrompt = true;

      private CameraCullingClientConfig() {}
  }
  ```
- **YACL / ModMenu Exposure**:
- **Existing GUI Architecture**: Uses custom `YaclScreenHelper` / `ModMenuIntegration` (`net.vanillaoutsider.culling.config.ModMenuIntegration`).
- **Pure-Java Opt-Out Holder**: Create `net.vanillaoutsider.culling.config.CameraCullingClientConfig` containing `public static boolean showEarlyAccessPrompt = true;` (with zero `net.minecraft.client.*` imports) and add a dedicated boolean toggle option for `showEarlyAccessPrompt` in `YaclScreenHelper` / ModMenu config screen (strictly client-side persistence, never a server `GameRule`).

---

## ⚓ 4. Multi-Era Anchor Parity Specifications

- **Required `dasik_library_version` Floor Across Studio Anchors** (all published in `mavenLocal()` under [`BL-DASIK-022`](file:///E:/Minecraft%20Project/MC-DasikLibrary/tickets/BL-DASIK-022.md)):
  - **MC `26.2` / `26.3` (`26.x`)**: `>= 1.13.2` (`fabric.mod.json`: `"dasik-library": ">=1.13.2"`)
  - **MC `26.1.2`**: `>= 1.13.2+26.1.2` (`fabric.mod.json`: `"dasik-library": ">=1.13.2+26.1.2"`)
  - **MC `1.21.11`**: `>= 1.4.4+1.21.11` (`fabric.mod.json`: `"dasik-library": ">=1.4.4+1.21.11"`)
  - **MC `1.21.1`**: `>= 1.4.5+1.21.1` (`fabric.mod.json`: `"dasik-library": ">=1.4.5+1.21.1"`)
  - **MC `1.20.1`**: `>= 1.4.7+1.20.1` (`fabric.mod.json`: `"dasik-library": ">=1.4.7+1.20.1"`)
- **Discovered On-Disk Anchor Pins in This Repository**:
  - `Camera Culling v1.20.1\Camera Culling 1.20.1\gradle.properties` (MC `1.20.1`): current `dasik_library_version = 1.1.0+1.20.1` -> bump to **`>= 1.4.7+1.20.1`**.
  - `Camera Culling v1.21.1\Camera Culling 1.21.1\gradle.properties` (MC `1.21.1`): current `dasik_library_version = 1.1.0+1.21.1` -> bump to **`>= 1.4.5+1.21.1`**.
  - `Camera Culling v1.21.11\Camera Culling 1.21.11\gradle.properties` (MC `1.21.11`): current `dasik_library_version = 1.1.0+1.21.11` -> bump to **`>= 1.4.4+1.21.11`**.
  - `Camera Culling v26.1\Camera Culling 26.1\gradle.properties` (MC `26.1.2`): current `dasik_library_version = 1.9.2` -> bump to **`>= 1.13.2+26.1.2`**.
  - `Camera Culling v26.2\Camera Culling 26.2\gradle.properties` (MC `26.2`): current `dasik_library_version = 1.9.2` -> bump to **`>= 1.13.2`**.
  - `Camera Culling v26.3\Camera Culling 26.3\gradle.properties` (MC `26.3`): current `dasik_library_version = 1.9.2` -> bump to **`>= 1.13.2`**.
- **Client-Only Environment (`"environment": "client"`)**: All wiring executes inside client initialization (`ClientModInitializer`) with zero server-side dependencies or `GameRules`; Pillar 3/4/5 checks adapt to client-only verification (`ClientConfig` loading, ModMenu/YACL screen AST, and manifest/license hygiene).

---

## 🛡️ 5. Edge Cases, Collisions & Failure Modes

| Dimension / Risk | Intended Impact | Forbidden Collateral Impact | Safeguard |
| :--- | :--- | :--- | :--- |
| **Dedicated Server Sidedness (Knot)** | `DasikPromptRegistry.register(...)` executes safely on server & client | `ClassNotFoundException` / `NoClassDefFoundError` on `net.minecraft.client.*` | `CameraCullingClientConfig` holds zero client imports; verified by `CameraCullingSidednessContractTest` |
| **Optional GUI Mods Absent** | Mod runs normally if ModMenu or YACL is not installed | Crash during config screen construction or initialization | Keep `modmenu` & `yet-another-config-lib` strictly in `"suggests"` with safe fallback |
| **Vanilla Clients on Modded Server** | Vanilla players connect seamlessly without downloading the mod | Server broadcasting prompt packets or chat spam to vanilla clients | Prompt polling & rendering stay 100% client-side inside `DasikLibrary` |
| **Shared Vanilla Trigger Collision** | Shared milestone `minecraft:story/mine_stone` across multiple Dasik mods stays non-intrusive | Stacked chat spam when multiple mods trigger back-to-back | `DasikLibrary`'s 5-minute (6000-tick) global FIFO pacing queue spaces prompts cleanly |
| **Server `GameRule` Isolation** | Opt-out toggle remains local to player's client preferences | Polluting world saves or `/gamerule` tab-completion with prompt flags | Never register any `GameRule` for Early Access prompts (`syncGameRule = false`) |
| **Single-Ticket Release Cadence** | Ships cleanly as its own dedicated `1 Ticket = 1 Version Bump` release | Bundling with unrelated bugfixes or breaking SemVer continuity | Execute opportunistically per [Single-Ticket Release Policy](file:///E:/Minecraft%20Project/.agents/rules/Single-Ticket%20Release%20Policy.md) |

---

## 🚀 6. Implementation Steps (Code-Level Plan)

1. **Lifecycle & SemVer Pre-Bump**: Confirm target release slot per [Single-Ticket Release Policy](file:///E:/Minecraft%20Project/.agents/rules/Single-Ticket%20Release%20Policy.md) and pre-bump `mod_version` in `gradle.properties` across each targeted anchor.
2. **Bump `dasik-library` Pins**: Update `dasik_library_version` in `gradle.properties` and `fabric.mod.json` (plus `neoforge.mods.toml` / `mods.toml` if present) to `>= 1.13.2` (`26.2`/`26.3`), `>= 1.13.2+26.1.2` (`26.1.2`), `>= 1.4.4+1.21.11` (`1.21.11`), `>= 1.4.5+1.21.1` (`1.21.1`), and `>= 1.4.7+1.20.1` (`1.20.1`).
3. **Add Pure-Java `CameraCullingClientConfig` & YACL Toggle**: Create `net.vanillaoutsider.culling.config.CameraCullingClientConfig` with `public static boolean showEarlyAccessPrompt = true;`, persist client-side state if applicable, and wire the toggle into the YACL / ModMenu screen (strictly `syncGameRule = false`).
4. **Register Trigger in Entrypoint**: Add `TRIGGER_ADVANCEMENT_ID = "minecraft:story/mine_stone"` and the 1-line `DasikPromptRegistry.register(MOD_ID, MOD_NAME, TRIGGER_ADVANCEMENT_ID, () -> CameraCullingClientConfig.showEarlyAccessPrompt)` call inside `net.vanillaoutsider.culling.CameraCullingClient#onInitializeClient()`.
5. **Update / Add `CameraCullingSidednessContractTest`**: Assert `Class.forName(CameraCullingClientConfig)` loads in a headless server JVM, `DasikPromptRegistry.isRegistered("minecraft:story/mine_stone")` passes post-init, toggling `showEarlyAccessPrompt` dynamically flips `PromptRegistration.isEnabled()`, and zero `GameRules` match `early_access|support_prompt`.
6. **Multi-Anchor Verify & Release**: Run `./gradlew test build --no-daemon --console=plain` across all compiled anchors, update `CHANGELOG.md` & `History.md`, and complete the 5-Link Release Chain.

---

## 🏁 7. 5-Pillar Verification Checklist

- [ ] **Pillar 1 (Chaos Property Fuzzing)**: Config boolean persistence & default fallback (`true`) survive malformed/missing JSON states without exceptions.
- [ ] **Pillar 2 (Concurrency Load)**: `DasikPromptRegistry` concurrent registration reads and dynamic `BooleanSupplier` evaluations pass thread-safe access checks without races.
- [ ] **Pillar 3 (GUI & Translation AST)**: Client config toggle (`showEarlyAccessPrompt`) and ModMenu/YACL screen labels validate cleanly headlessly with zero missing keys.
- [ ] **Pillar 4 (Client State Simulation)**: Headless registration & toggle-flip verification confirms `DasikPromptRegistry.get("minecraft:story/mine_stone")` dynamically tracks `CameraCullingClientConfig.showEarlyAccessPrompt` (`true` -> `false` -> `true`) with zero `GameRule` creation.
- [ ] **Pillar 5 (`CameraCullingSidednessContractTest` Dedicated-Server Safety & License Hygiene)**: Dedicated-server simulation confirms zero `net.minecraft.client.*` / `com.mojang.blaze3d.*` leakage in common config/init paths, zero `GameRule` prompt keys, and valid single-line `// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3` headers.
- [ ] **Multi-Anchor Compilation**: `./gradlew test build --no-daemon --console=plain` exits `0` across all active anchors for this mod.

---

## 🛡️ 8. Autonomous Goal Dependability & Self-Healing Contract

- **Pre-Flight Disk Truth**: Verify `net.dasik.social.api.prompt.DasikPromptRegistry` artifacts in `mavenLocal()` (`~/.m2/repository/net/dasik/social/dasik-library/`) before running Gradle builds across anchors (`26.3`, `26.2`, `26.1.2`, `1.21.11`, `1.21.1`, `1.20.1`).
- **Self-Healing Budget**: Run Gradle strictly with `--no-daemon --console=plain`; allow a 1-attempt self-healing retry if an older anchor uses a distinct `MOD_ID` constant or subproject `settings.gradle` layout, and never mark this ticket `✅ RESOLVED` until all compiled anchors pass `CameraCullingSidednessContractTest` and clean build checks.

---

## 🔗 9. Cross-References & Governance

- Parent Umbrella Ticket: [BL-WS-047](file:///E:/Minecraft%20Project/tickets/BL-WS-047.md)
- Upstream Engine Ticket: [BL-DASIK-022](file:///E:/Minecraft%20Project/MC-DasikLibrary/tickets/BL-DASIK-022.md)
- Governing Policies:
  - [Monetization & Creator Support Policy](file:///E:/Minecraft%20Project/.agents/rules/Monetization%20&%20Creator%20Support%20Policy.md) (§6–§7)
  - [Single-Ticket Release Policy](file:///E:/Minecraft%20Project/.agents/rules/Single-Ticket%20Release%20Policy.md)
  - [Bytecode Testing & ClassLoader Safety Policy](file:///E:/Minecraft%20Project/.agents/rules/Bytecode%20Testing%20&%20ClassLoader%20Safety%20Policy.md)
  - [Optional GUI Dependencies](file:///E:/Minecraft%20Project/.agents/rules/Optional%20GUI%20Dependencies.md)
