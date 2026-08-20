# 🌐 Version Compatibility & Lifecycle Matrix

This document outlines the active multi-era release matrix, dependency bounds, Java runtime specifications, and bytecode API variations for **Camera Culling**.

> 📌 **Repository Source Disclaimer**: The documentation in this Wiki reflects the **current source code state in the repository**, which may include recent unreleased commits or developmental features ahead of public release builds on CurseForge and Modrinth.

---

## 📋 Multi-Version Lifecycle Matrix

| Minecraft Anchor Era | Target MC Version | Current Release Version | Java Requirement | Fabric Loader Bound | Fabric API Bound | Release Status |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| **Modern Sovereign (26.3)** | `26.3` | `1.10.0+26.3` | Java 25 (`release = 25`) | `>=0.18.4` | `0.145.4+26.3` | 🟢 Active Release |
| **Modern Sovereign (26.2)** | `26.2` | `1.10.0+26.2` | Java 25 (`release = 25`) | `>=0.18.4` | `0.145.4+26.2` | 🟢 Active Release |
| **Modern Sovereign (26.1.2)** | `26.1.2` | `1.10.1+26.1.2` | Java 25 (`release = 25`) | `>=0.18.4` | `0.145.4+26.1.2` | 🟢 Active Release |

---

## 🛠️ Bytecode API Differences Across Anchors

### 1. Block Entity RenderState Extraction Pipeline
* **Minecraft 26.1.2**:
  - `BlockEntityRenderDispatcher.tryExtractRenderState(BlockEntity, float, ModelFeatureRenderer$CrumblingOverlay)` — takes **3 arguments**.
* **Minecraft 26.2 & 26.3**:
  - `BlockEntityRenderDispatcher.tryExtractRenderState(BlockEntity, float, ModelFeatureRenderer$CrumblingOverlay, boolean isGloballyRendered)` — takes **4 arguments**.

### 2. Sign Text Data Retrieval API
* **Minecraft 26.1.2 & 26.2**:
  - `SignBlockEntity.getFrontText()` and `SignBlockEntity.getBackText()` retrieve `SignText`.
  - `SignText.getMessage(int index, boolean filtered)` retrieves line `Component`.
* **Minecraft 26.3**:
  - `SignBlockEntity.getText(SignTextSlot.FRONT)` and `SignBlockEntity.getText(SignTextSlot.BACK)` retrieve `SignText`.
  - `SignText.getMessages(boolean filtered)` retrieves line `Component` array.

---

## 📦 Build Artifact Archive Locations

All release builds are automatically compiled and preserved in the parent repository's centralized archive structure:

```text
Archive Jar of all versions/
├── MC 26.1.2/
│   ├── vanilla-outsider-camera-culling-1.10.1+26.1.2.jar
│   └── vanilla-outsider-camera-culling-1.10.1+26.1.2-sources.jar
├── MC 26.2/
│   ├── vanilla-outsider-camera-culling-1.10.0+26.2.jar
│   └── vanilla-outsider-camera-culling-1.10.0+26.2-sources.jar
└── MC 26.3/
    ├── vanilla-outsider-camera-culling-1.10.0+26.3.jar
    └── vanilla-outsider-camera-culling-1.10.0+26.3-sources.jar
```

---

## 🔗 Quick Links

- [[👉 Enter Minecraft 26.3 Wiki|26.3-Home]]
- [[👉 Enter Minecraft 26.2 Wiki|26.2-Home]]
- [[👉 Enter Minecraft 26.1.2 Wiki|26.1.2-Home]]
- [[Return to Portal|Home]]
