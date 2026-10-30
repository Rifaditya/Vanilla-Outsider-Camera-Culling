# 🎫 [BL-CC-006] Clarify Centralized Platform Release Distribution in Repository Documentation (GitHub Issue #1)

- **Ticket ID**: `[BL-CC-006]`
- **Category**: `[REFINEMENT]`
- **Priority**: `[LOW]`
- **Scope**: `Repository Documentation & Storefront Badges`
- **Status**: `📌 DEFERRED`
- **Target Repository / Root**: `Vanilla Outsider Collections/Camera Culling`

---

## 🎯 1. Problem Statement & Player Impact
In GitHub Issue #1, a community user asked: *"to be honest. where's the 26.3 mods that never been in Action tab nor release?"*. Because the studio compiles builds locally and publishes official releases directly to Modrinth and CurseForge via centralized release automation, visitors to the GitHub repository were confused by the empty GitHub Releases tab.

In-game & storefront behavior before vs. after:
- **Before**: Players visiting GitHub cannot locate binary downloads and assume the mod is unreleased.
- **After**: Repository `README.md` prominently displays Modrinth and CurseForge download badges and a dedicated "📥 Downloads & Releases" section pointing directly to official platform channels.

## 🔬 2. Architectural Blueprint & Target Classes
- Target Files:
  - `README.md` (root repository and subprojects)
  - `.github/ISSUE_TEMPLATE/` (issue templates guiding users looking for downloads)
- Documentation Additions:
  - Shields.io dynamic version badges for Modrinth and CurseForge.
  - Dedicated callout:
    > [!IMPORTANT]
    > **Official Releases**: Official binary JARs are compiled and distributed directly to **[Modrinth](https://modrinth.com/mod/vo-camera-culling)** and **[CurseForge](https://www.curseforge.com/minecraft/mc-mods/vo-camera-culling)**. GitHub Releases are reserved for development tags.

## ⚓ 3. Multi-Era Anchor Parity Specifications
- Documentation clarifies release availability across all 6 anchors (`1.20.1`, `1.21.1`, `1.21.11`, `26.1.2`, `26.2`, `26.3`).

## 🛡️ 4. Edge Cases, Collisions & Failure Modes
- Broken badge URLs: uses official Shields.io Modrinth and CurseForge SVG badges with fallback links.
- Subproject READMEs: ensures subproject readmes also link back to official platform pages.

## 🚀 5. Implementation Steps (Code-Level Plan)
1. Add Modrinth and CurseForge Shields.io badges to the top of `README.md`.
2. Add "📥 Downloads & Releases" section with clickable links.
3. Update GitHub issue templates with download guidance.
4. Push documentation changes to GitHub remote.

## 🏁 6. 5-Pillar Verification Checklist
- [ ] **Pillar 1 (Chaos Fuzzing)**: Markdown link checker verifies 0 dead URLs.
- [ ] **Pillar 2 (Concurrency Load)**: N/A (Repository documentation).
- [ ] **Pillar 3 (Brigadier AST)**: N/A.
- [ ] **Pillar 4 (GameTest)**: N/A.
- [ ] **Pillar 5 (Sidedness & Hygiene)**: 0 broken markdown links; valid Shields.io SVG badge syntax.
- [ ] **Anchor Compilation**: Markdown renders cleanly on GitHub web interface.
