# 🎫 [BL-CC-016] Fix `ConfirmLinkScreen.confirmLinkNow(screen, String)` Overload Regression on `v26.1` & `v26.2`

- **Ticket ID**: `[BL-CC-016]`
- **Category**: `[BUGFIX]`
- **Priority**: `[HIGH]`
- **Target Version**: `v26.1` (`1.10.8+26.1.2`), `v26.2` (`1.10.8+26.2`)
- **Scope**: `Camera Culling v26.1/Camera Culling 26.1/src/main/java/net/vanillaoutsider/culling/config/YaclScreenHelper.java`, `Camera Culling v26.2/Camera Culling 26.2/src/main/java/net/vanillaoutsider/culling/config/YaclScreenHelper.java`, Client Sidedness/Bytecode Contract Tests
- **Status**: `📌 DEFERRED`
- **Grilling Status**: `✅ PASSED`
- **Target Repository / Root**: `Vanilla Outsider Collections/Camera Culling`
- **Discovered Via**: `[BL-HQS-007]` Stage 5 Workspace Blast-Radius Sweep (`Hyper Quality Screenshots`)
- **Date Added**: `2026-11-25`

---

## 🎯 1. Problem Statement & Player Impact

During the workspace-wide blast-radius sweep triggered by `[BL-HQS-007]`, `YaclScreenHelper.java:185` in both **Camera Culling v26.1** (`26.1.2`) and **Camera Culling v26.2** (`26.2`) was found still invoking the legacy `String` overload inside `createSupportButton()`'s fallback Ko-fi button:

```java
.action((screen, opt) -> ConfirmLinkScreen.confirmLinkNow(screen, "https://ko-fi.com/dasikigaijin"))
```

Meanwhile, `v1.21.1`, `v1.21.11`, and `v26.3` already pass `java.net.URI.create("https://ko-fi.com/dasikigaijin")`, and `v1.20.1` properly retains the 1.20.1 3-arg signature `ConfirmLinkScreen.confirmLinkNow("https://ko-fi.com/dasikigaijin", screen, true)`. On `26.1.2` and `26.2`, clicking or invoking the YACL fallback Ko-fi support button when `DasikSupportHelper` reflection falls through risks `NoSuchMethodError` / compile-time overload mismatch against `ConfirmLinkScreen.confirmLinkNow(Screen, URI)`.

---

## 🔬 2. Architectural Blueprint & Target Classes

- **Affected Anchors & Exact Files**:
  1. `Camera Culling v26.1/Camera Culling 26.1/src/main/java/net/vanillaoutsider/culling/config/YaclScreenHelper.java` (Line 185)
  2. `Camera Culling v26.2/Camera Culling 26.2/src/main/java/net/vanillaoutsider/culling/config/YaclScreenHelper.java` (Line 185)
- **Target Replacement (Matching `v1.21.1`, `v1.21.11`, `v26.3`)**:
  ```java
  .action((screen, opt) -> ConfirmLinkScreen.confirmLinkNow(screen, java.net.URI.create("https://ko-fi.com/dasikigaijin")))
  ```

---

## 🛡️ 3. Blast Radius & Multi-Era Parity Table

| Anchor | Current `YaclScreenHelper:185` Call | Status | Required Action |
| :--- | :--- | :--- | :--- |
| `v1.20.1` | `confirmLinkNow("https://ko-fi.com/dasikigaijin", screen, true)` | Valid on `1.20.1` | Keep unchanged |
| `v1.21.1` | `confirmLinkNow(screen, java.net.URI.create(...))` | Parity OK | Add bytecode contract guard if absent |
| `v1.21.11` | `confirmLinkNow(screen, java.net.URI.create(...))` | Parity OK | Add bytecode contract guard if absent |
| `v26.1` (`26.1.2`) | `confirmLinkNow(screen, "https://ko-fi.com/dasikigaijin")` | **DEFECTIVE** | Replace `String` with `URI.create(...)`; bump `1.10.8+26.1.2` |
| `v26.2` | `confirmLinkNow(screen, "https://ko-fi.com/dasikigaijin")` | **DEFECTIVE** | Replace `String` with `URI.create(...)`; bump `1.10.8+26.2` |
| `v26.3` | `confirmLinkNow(screen, java.net.URI.create(...))` | Parity OK | Keep unchanged |

---

## 🚫 4. Anti-Goals (Creep Shield)

1. **Zero Unrelated YACL / Config Refactors**: Do NOT migrate `YaclScreenHelper` to `UniversalConfigScreen` (`BL-CC-009` owns that) or alter slider bounds/defaults.
2. **Zero `1.20.1` Signature Breakage**: Do NOT change `v1.20.1`'s valid 3-arg `confirmLinkNow("...", screen, true)` call.
3. **Zero Multi-Ticket Bundling**: Do NOT bundle `BL-CC-015` (`DasikPromptRegistry`) or `BL-CC-008` command refactors into `1.10.8+26.1.2` / `1.10.8+26.2`.
4. **Zero Server-Side Entrypoint / `GameRule` Creep**: Keep `"environment": "client"` strictly intact with zero server classes or `GameRules`.

---

## 🏁 5. Client-Only 5-Pillar Verification Checklist

- [ ] **Pillar 1 (Chaos Property Fuzzing)**: Retain existing `EntityLodHelper` & config fuzz tests passing on `v26.1` and `v26.2`.
- [ ] **Pillar 2 (Concurrency Load)**: Retain 50-thread config persistence concurrency tests passing with zero regressions.
- [ ] **Pillar 3 (GUI & Translation AST)**: Verify `YaclScreenHelper` Ko-fi button translation keys (`dasiklibrary.support.kofi.button`, `dasiklibrary.support.kofi.tooltip`) and fallback `ModMenuIntegration` execution with YACL absent (`[PAT-20261125-001]`).
- [ ] **Pillar 4 (Client State Simulation)**: Add/verify `YaclScreenHelper.class` ASM bytecode contract test asserting `ConfirmLinkScreen.confirmLinkNow(Screen, URI)` is invoked on `1.21.1+` / `26.x` and `ConfirmLinkScreen.confirmLinkNow(Screen, String)` is strictly absent.
- [ ] **Pillar 5 (Sidedness & License Hygiene)**: Confirm `CameraCullingSidednessContractTest` passes, GPLv3 header intact, and `./gradlew test build --no-daemon --console=plain` succeeds on `v26.1` and `v26.2`.
