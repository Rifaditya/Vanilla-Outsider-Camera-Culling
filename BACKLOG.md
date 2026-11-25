# 📌 Camera Culling Backlog

<!-- Highest Historical ID: BL-CC-016 (Next Ticket Must Start at BL-CC-017) -->
> **Next Available Ticket ID**: `[BL-CC-017]`

This file tracks planned features, technical refinements, performance optimizations, and deferred improvements for **Camera Culling**.

---

## 📊 Backlog Summary

| ID | Category | Title | Priority | Target Version | Status | Specification |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| [`BL-CC-014`](tickets/BL-CC-014.md) | `[BUGFIX]` | Fix ParticleCullingHelper ClipContext Null Entity NPE in Anchor 1.20.1 | `[HIGH]` | `1.0.4+1.20.1` | `✅ RESOLVED` | [View Ticket](tickets/BL-CC-014.md) |
| [`BL-CC-013`](tickets/BL-CC-013.md) | `[BUGFIX]` | Fix ClipContext Null Entity NPE in Anchor 1.20.1 | `[HIGH]` | `1.0.3+1.20.1` | `✅ RESOLVED` | [View Ticket](tickets/BL-CC-013.md) |
| [`BL-CC-011`](tickets/BL-CC-011.md) | `[TECH_DEBT]` | Scaffold Anchor 7: Camera Culling v26.x (rolling 26.3+) | `[MEDIUM]` | Multi-Version Parity | `📌 DEFERRED` | [View Ticket](tickets/BL-CC-011.md) |
| [`BL-CC-006`](tickets/BL-CC-006.md) | `[REFINEMENT]` | Clarify Centralized Platform Release Distribution in Repository Documentation (GitHub Issue #1) | `[LOW]` | `All Anchors` | `📌 DEFERRED` | [View Ticket](tickets/BL-CC-006.md) |
| [`BL-CC-007`](tickets/BL-CC-007.md) | `[TECH_DEBT]` | Adopt 5-Pillar Testing & Sidedness Verification Harness | `[MEDIUM]` | All Anchors | `✅ RESOLVED` | [View Ticket](tickets/BL-CC-007.md) |
| [`BL-CC-008`](tickets/BL-CC-008.md) | `[TECH_DEBT]` | Migrate Command Suite to Universal Brigadier Command Framework | `[MEDIUM]` | All Anchors | `📌 DEFERRED` | [View Ticket](tickets/BL-CC-008.md) |
| [`BL-CC-012`](tickets/BL-CC-012.md) | `[TECH_DEBT]` | Wiki Audit & Update to Latest Version: Camera Culling | `[MEDIUM]` | `All Anchors` | `📌 DEFERRED` | [View Ticket](tickets/BL-CC-012.md) |
| [`BL-CC-009`](tickets/BL-CC-009.md) | `[TECH_DEBT]` | Migrate Client Configuration GUI to UniversalConfigScreen | `[MEDIUM]` | All Anchors | `📌 DEFERRED` | [View Ticket](tickets/BL-CC-009.md) |
| [`BL-CC-010`](tickets/BL-CC-010.md) | `[GOVERNANCE]` | Storefront Reconciliation: Align Mod Pages with Current Public Release | `[HIGH]` | `Platform Pages` | `✅ RESOLVED` | [View Ticket](tickets/BL-CC-010.md) |
| [`BL-CC-015`](tickets/BL-CC-015.md) | `[TECH_DEBT]` | Adopt Per-Mod Advancement-Triggered Early Access Support Prompt (`DasikPromptRegistry`) | `[MEDIUM]` | `Opportunistic Per-Mod` | `📌 DEFERRED` | [View Ticket](tickets/BL-CC-015.md) |
| [`BL-CC-016`](tickets/BL-CC-016.md) | `[BUGFIX]` | Fix `ConfirmLinkScreen.confirmLinkNow(screen, String)` Overload Regression on `v26.1` & `v26.2` | `[HIGH]` | `v26.1`, `v26.2` | `✅ RESOLVED` | [View Ticket](tickets/BL-CC-016.md) |

---

## 🏷 Legend & Status Tags
- **Categories**: `[FEATURE]`, `[REFINEMENT]`, `[BUGFIX]`, `[PERF]`, `[TECH_DEBT]`
- **Priorities**: `[HIGH]` (Critical logic/porting task), `[MEDIUM]` (Quality of life), `[LOW]` (Minor polish)
- **Statuses**: `📌 DEFERRED` (Queued for future work), `🚧 IN_PROGRESS` (Active development), `✅ RESOLVED` (Implemented and verified)

---

## 📝 Detailed Backlog Entries (Modular Storage)

> [!NOTE]
> **Modular Backlog Architecture**: In accordance with Item 11 of the Studio Constitution (*The Modular Backlog Standard*), detailed specifications, implementation blueprints, and verification checklists are maintained in dedicated atomic ticket files located in [`tickets/`](tickets/). Click any **View Ticket** link in the summary table above or prompt worker agents directly with `tickets/BL-CC-XXX.md`.
