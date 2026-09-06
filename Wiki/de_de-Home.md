# 📷 Camera Culling Wiki

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Willkommen im offiziellen Dokumentationsportal von **Camera Culling**. Camera Culling ist ein hochleistungsfähiger, clientseitiger Rendering-Optimierungs-Mod für Minecraft **26.1.2**, **26.2** und **26.3**, entwickelt unter der **Vanilla Outsider**-Philosophie.

> 📌 **Quellcode-Haftungsausschluss**: Die Dokumentation in diesem Wiki spiegelt den **aktuellen Quellcode-Stand im Repository** wider, welcher noch unveröffentlichte Commits oder Entwicklungsfunktionen vor den offiziellen Release-Builds auf CurseForge und Modrinth enthalten kann.

---

## 🧭 Multi-Versions-Wechselportal

Camera Culling wird unter dem strikten Prinzip **1 JAR 1 Version** entwickelt. Wähle unten deine gewünschte Minecraft-Version, um ihren dedizierten, isolierten Dokumentationsbaum aufzurufen:

| Ziel-Minecraft-Version | Mod-Release-Version | Java-Laufzeitumgebung | Build-Tooling | Dediziertes Wiki-Portal |
| :--- | :--- | :--- | :--- | :--- |
| **Minecraft 26.1.2** | `1.10.2+26.1.2` | Java 25+ | Fabric Loom 1.15.5 | [[👉 MC 26.1.2 Wiki öffnen|de_de-26.1.2-Home]] |
| **Minecraft 26.2** | `1.10.1+26.2` | Java 25+ | Fabric Loom 1.15.5 | [[👉 MC 26.2 Wiki öffnen|de_de-26.2-Home]] |
| **Minecraft 26.3** | `1.10.1+26.3` | Java 25+ | Fabric Loom 1.15.5 | [[👉 MC 26.3 Wiki öffnen|de_de-26.3-Home]] |

---

## ⚡ Kern-Optimierungsmatrix

| Optimierungssystem | Primärer Mechanismus | Leistungsvorteil |
| :--- | :--- | :--- |
| **Zero-Allocation-Raycast-Engine** | Sichtlinienprüfung mit primitiven Koordinaten | Beseitigt Young-Gen-GC-Pausenspitzen der JVM bei Kameradrehungen |
| **Zeitliche Hysterese gegen Flackern** | Adaptiver 4/8/12-Frame-Distanz-Kulanzpuffer | Beseitigt Randflackern und View-Bobbing-Flackern |
| **Schild- & Hängeschild-Text-Culling** | Skalarprodukt von Flächennormalen ($\vec{N} \cdot \vec{V}$) | 50%–100% Reduzierung der Draw-Calls für Schildtexte |
| **Partikel-Okklusions-Culling** | 4m-Sicherheitsblase + Sichtstrahl-Raycasts | Deaktiviert das Rendern von unterirdischen und verdeckten Partikel-Quads |
| **Animations-Culling** | Unterdrückung von Texturatlas-Uploads | Friert 3D-Blockanimationen und Offscreen-Textur-Uploads ein |
| **Mob-Mengen-Overdraw-Schutz** | 16m-Fast-Fail + 1,5m-Cluster-Dichteobergrenze | Beseitigt Lag-Spitzen in überfüllten Tiergehegen & Mob-Farmen |
| **Abstands-Textur-LOD** | 3-stufiger OpenGL-Mipmap-Bias ($0.0 	o 1.0 	o 2.5$) | Reduziert drastisch die VRAM-Füllrate bei entfernten Tierherden |
| **Boss- & Blacklist-Immunität** | Dynamische Gesundheitsschwellen & Namensheuristiken | Verhindert spielbeeinträchtigendes Culling von Bossen |
| **Zweistufige Immunitätssperrliste** | Lokales Client-JSON + Server-Admin-Synchronisation | Benutzerdefinierte Whitelist für Begleit- und Haustiere |
| **Block-Entity-Culling** | 6-seitige Erkennung solider Umschließung | Überspringt die Render-Extraktion für vollständig verdeckte Truhen und Blöcke |

---

## 📚 Globale Navigation

- [[Versionskompatibilitäts-Matrix|de_de-Version-Compatibility]]
- [[Minecraft 26.1.2 Dokumentationsbaum|de_de-26.1.2-Home]]
- [[Minecraft 26.2 Dokumentationsbaum|de_de-26.2-Home]]
- [[Minecraft 26.3 Dokumentationsbaum|de_de-26.3-Home]]

---

<p align="center">
  <em>Entwickelt von <strong>Dasik (Rifaditya)</strong> | Lizenziert unter <strong>GNU General Public License v3.0 (GPLv3)</strong></em>
</p>
