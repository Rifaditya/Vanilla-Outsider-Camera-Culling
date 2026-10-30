# 🎫 [BL-CC-008] Migrate Command Suite to Universal Brigadier Command Framework

- **Ticket ID**: `[BL-CC-008]`
- **Category**: `[TECH_DEBT]`
- **Priority**: `[MEDIUM]`
- **Target Version**: `All Anchors`
- **Scope**: `In-Game Commands & Brigadier Architecture`
- **Status**: `📌 DEFERRED`
- **Master Workspace Reference**: [`BL-WS-016`](file:///e:/Minecraft%20Project/tickets/BL-WS-016.md)
- **Governance Mandate**: Item 1.7 of the [Studio Constitution](file:///e:/Minecraft%20Project/.agents/rules/Studio%20Constitution.md) (*Universal Downstream Brigadier Command Suite Standard / ModCommandDispatcher & UniversalCommandRegistry Invariant*)

---

## 🎯 1. Problem Statement & Player Impact
Currently, **Camera Culling** maintains custom Brigadier command registration, argument parsers, feedback formatters, and GameRule reflection/dispatch logic. This creates repetitive boilerplate, maintenance friction across Minecraft version updates, and visual inconsistencies with studio chat formatting standards.

Following the release of `Dasik Library` v1.10.0 (Modern Lead) and v1.2.0 (Multi-Era Anchors), Camera Culling should adopt the centralized `net.dasik.social.api.command.*` framework.

## 🔬 2. Architectural Blueprint & Target Classes
1. **Dispatcher Registration**: Wire `ModCommandDispatcher.registerSuite(MOD_ID, builder -> ...)` in the mod's initializers across all active version anchors.
2. **Universal Tree Builder**: Construct standard subcommands (`help`, `status`, `get`, `set`, `reset`, `reload`) via `builder.withRootLiteral("cameraculling")` and `builder.withModName("Camera Culling")`.
3. **Reactive GameRule Bridging & Status**: Wire `builder.withBridge(new GameRuleCommandBridge(...))` or `builder.withStatusSupplier(source -> ...)` for two-way synchronization and saturated arithmetic bounds.
4. **Lifecycle & Reload Support**: Attach `builder.withReloadAction(source -> ...)` to re-parse sparse delta configs or invalidate caches without server restart.
5. **Standard Feedback & Footers**: Leverage `CommandFeedbackFormatter` for unified gold prefixes (`§6[Camera Culling]§r `), colored feedback levels, and clickable community footers (`DasikSupportHelper`).
6. **Boilerplate Purge**: Purge custom command boilerplate classes and redundant formatting helpers, simplifying the codebase.

### Downstream Registration Pattern
```java
ModCommandDispatcher.registerSuite(MOD_ID, builder -> builder
    .withRootLiteral("cameraculling")
    .withModName("Camera Culling")
    .withBridge(new GameRuleCommandBridge(rules -> {
        // Bind mod GameRules and sparse delta config values with saturated bounds
    }))
    .withReloadAction(source -> {
        // Reload configs and refresh in-memory caches
    })
);
```

## ⚓ 3. Prerequisites & Multi-Era Anchor Parity Specifications
- **Library Prerequisites (`gradle.properties` / `fabric.mod.json`)**:
  - Modern Lead (`26.x` / Sovereign): `dasik_library_version >= 1.10.0`
  - Multi-Era Anchors (`1.21.11`, `1.21.1`, `1.20.1`): `dasik_library_version >= 1.2.0` (or `1.3.0`)
- **Multi-Era Anchor Parity Scope**: Applicable across all active version anchors of Camera Culling in lockstep.
- **Backward Compatibility**: Preserves all existing command aliases (`/cameraculling`, `/cc`), argument names, and permission levels (`source.hasPermission(2)` for admin mutations).

## 📊 4. Execution Cadence & Migration Tier
- **Migration Tier**: `Tier 2 Medium Multi-Anchor`
- **Cadence Rationale**: Client-focused rendering mod with multi-anchor presence. Command suite handles entity/particle LOD toggles, boss immunity blacklists, and distance culling thresholds.

## 🛡️ 5. Edge Cases & Sidedness Safety
- Null source entity safety for console / RCON / command block execution.
- Strict sidedness: Zero `net.minecraft.client.*` imports in common code; Knot ClassLoader runtime safety.
- Freedom Over Anti-Crash: Full saturated numerical bounds with non-blocking warnings.

## 🚀 6. Implementation Steps
1. Pre-bump `dasik_library_version` in `gradle.properties` across targeted anchor(s).
2. Wire `ModCommandDispatcher.registerSuite` in mod initializer.
3. Configure `withRootLiteral`, `withModName`, `withBridge` / `withStatusSupplier`, and `withReloadAction`.
4. Replace custom command classes with `UniversalCommandRegistry` builder and purge obsolete boilerplate.
5. Verify via 5-Pillar Testing Harness (`*CommandTest` / Brigadier AST test).
6. Compile and deploy via the 5-Link Release Chain.

## 🏁 7. 5-Pillar Verification Checklist
- [ ] **Pillar 1 (Chaos Fuzzing)**: Fuzz command arguments with saturated inputs.
- [ ] **Pillar 2 (Concurrency Load)**: Multi-threaded command execution / bridge read test.
- [ ] **Pillar 3 (Brigadier AST)**: Complete parse tree and suggestion provider validation.
- [ ] **Pillar 4 (GameTest / Headless)**: Command execution assertions.
- [ ] **Pillar 5 (Sidedness & Hygiene Contract)**: 0 client imports in common command code; single-line GPLv3 headers.
