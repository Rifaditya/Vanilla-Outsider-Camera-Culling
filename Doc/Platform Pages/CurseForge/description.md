<div align="center">

<img src="https://raw.githubusercontent.com/Rifaditya/Vanilla-Outsider-Camera-Culling/main/Doc/Media/icon.png" alt="Camera Culling Mod Icon" width="180">

</div>
<p align="center">
    <a href="https://www.curseforge.com/minecraft/mc-mods/fabric-api"><img src="https://img.shields.io/badge/Requires-Fabric_API-blue?style=for-the-badge&logo=fabric" alt="Requires Fabric API"></a>
    <img src="https://img.shields.io/badge/Language-Java_25-orange?style=for-the-badge&logo=java" alt="Java 25">
    <img src="https://img.shields.io/badge/License-GPLv3-green?style=for-the-badge" alt="License">
    <img src="https://img.shields.io/badge/Minecraft-26.1+-brightgreen?style=for-the-badge" alt="Minecraft 26.1+">
</p>

<h1>📷 Camera Culling</h1>

<p><strong>Active Version Policy:</strong> I build <strong>1 JAR for 1 Version</strong>. I only update and maintain the latest active Minecraft version (e.g. when 26.3 is released, 26.2 is retired). No backports or legacy version maintenance. Please do not ask.</p>

<blockquote><strong>Unrender the unseen. Lightweight, high-performance camera occlusion culling &amp; distance texture LOD.</strong></blockquote>

<p><strong>The Vanilla Problem:</strong> In vanilla Minecraft, the game's renderer extracts render states and draws thousands of entities and block entities even when they are completely hidden behind solid walls, buried in deep underground caves, or smothered behind dense walls of other mobs. Furthermore, rendering fully-resolved textures on distant mobs consumes precious GPU fillrate and VRAM bandwidth.</p>

<p><strong>Camera Culling</strong> solves this at the rendering root. It performs lightning-fast client-side raycasting and frustum occlusion checks against block collisions to dynamically unrender occluded entities and block entities. Combined with a smart mob crowd overdraw defense, distance-based texture LOD mipmap scaling, dynamic boss/mini-boss protections, and a two-tier immunity blacklist, Camera Culling drastically increases frame rates without altering world simulation or network packets.</p>

<p>Part of the <strong>Vanilla Outsider Collection</strong> — mods that refine the vanilla experience with modern standards.</p>

<hr>

<h2>✨ Features</h2>

<h3>🧱 Entity Occlusion Culling (Solid Block Sightlines)</h3>
<p>Never waste GPU cycles rendering mobs you cannot see. Fast multi-point raytracing against solid block collision geometry unrenders entities hidden behind walls, cave ceilings, and terrain.</p>
<ul>
  <li><strong>Multi-Point Precision:</strong> Evaluates key entity sightlines (center, head, feet, and bounding box corners) to guarantee mobs are never culled prematurely when partially visible around corners.</li>
  <li><strong>Safety Buffers:</strong> Incorporates a proximity safety margin so mobs near your crosshairs or close to your camera are always rendered smoothly.</li>
</ul>

<blockquote><strong>Zero Simulation Impact:</strong> Culling operates strictly on client-side render submission. Entity ticks, server synchronization, sound events, and physics remain 100% active.</blockquote>

<h3>👥 Entity-Behind-Entity Culling (Crowd Overdraw Defense)</h3>
<p>Dense mob farms and crowded pens are major FPS killers. Camera Culling detects when mobs are completely obscured behind closer, opaque mobs in front of them.</p>
<ul>
  <li><strong>Geometric Raycast AABB Clipping:</strong> Projects sightlines through candidate foreground mobs to cull entities entirely hidden in the crowd.</li>
  <li><strong>Cluster Density Cap:</strong> Automatically caps rendered entities in tight 1.5-block clusters (default: 8 mobs per cluster), preventing rendering lag in cramming farms.</li>
  <li><strong>Decorative &amp; Small Mob Bypass:</strong> Transparent or small entities (Slimes, Magma Cubes, Vexes, Armor Stands) never block sightlines or occlude other mobs.</li>
</ul>

<blockquote>💡 <strong>Profile Linking:</strong> Entity-behind-entity culling is active by default on <code>HIGH</code> and <code>SUPER</code> presets, and can be toggled independently via <code>/cameraculling entityculling &lt;true|false|auto&gt;</code>.</blockquote>

<h3>🎨 Distance-Based Mob Texture LOD (Texture Resolution Reduction)</h3>
<p>Distant mobs don't need 4K-level crisp textures. Camera Culling features a decoupled, 3-tier OpenGL Mipmap LOD biasing engine that dynamically reduces texture resolution on distant mobs:</p>
<ul>
  <li><strong>Near (&lt; 16 blocks):</strong> 100% Native Full-Resolution crisp textures (0.0 LOD bias).</li>
  <li><strong>Medium (16 – 32 blocks):</strong> Half-Resolution texture sampling (1.0 LOD bias).</li>
  <li><strong>Far (&gt; 32 blocks):</strong> Quarter-Resolution low-res mipmap sampling (2.5 LOD bias).</li>
  <li><strong>GPU Fillrate Optimization:</strong> Drastically reduces VRAM fillrate and memory bandwidth on large herds of distant mobs without degrading close-up visual fidelity.</li>
</ul>

<blockquote>💡 <strong>Standalone Setting:</strong> Texture LOD operates independently of culling presets. Configure custom thresholds anytime via <code>/cameraculling texturlod range &lt;near&gt; &lt;far&gt;</code>.</blockquote>

<h3>🛡️ Two-Tier Entity Immunity Blacklist</h3>
<p>Want specific entities to never be culled under any circumstances? Camera Culling provides a robust two-tier blacklist system:</p>
<ul>
  <li><strong>Client Personal Blacklist:</strong> Stored locally in <code>config/camera-culling.json</code>. Players can blacklist their favorite companions (e.g. <code>minecraft:wolf</code>, <code>minecraft:allay</code>, <code>minecraft:cat</code>).</li>
  <li><strong>Server Admin Blacklist:</strong> Configured in <code>config/camera-culling-server.json</code>. Server operators can enforce server-wide entity immunity across all connected clients.</li>
  <li><strong>Full Immunity:</strong> Blacklisted entities are 100% exempt from block occlusion culling, crowd overdraw culling, and texture LOD downscaling.</li>
</ul>

<blockquote>💡 <strong>Command Management:</strong> Add or remove entities in-game using <code>/cameraculling blacklist &lt;add|remove|list|clear&gt; &lt;id&gt;</code> or <code>/cameraculling serverblacklist &lt;add|remove|list|clear&gt; &lt;id&gt;</code>.</blockquote>

<h3>👑 Dynamic Boss &amp; Mini-Boss Immunity</h3>
<p>Never lose sight of dangerous foes. Camera Culling automatically identifies both vanilla and modded bosses &amp; mini-bosses:</p>
<ul>
  <li><strong>Automatic Recognition:</strong> Protects Ender Dragons, Withers, Wardens, Elder Guardians, Ravagers, Iron Golems, Piglin Brutes, Evokers, Breezes, and modded champions/titans.</li>
  <li><strong>Configurable Health Thresholds:</strong>
    <ul>
      <li><strong>Major Boss Threshold:</strong> <code>bossHealthThreshold</code> (Default: <code>150.0 HP</code> / 75 hearts).</li>
      <li><strong>Mini-Boss Threshold:</strong> <code>miniBossHealthThreshold</code> (Default: <code>50.0 HP</code> / 25 hearts).</li>
    </ul>
  </li>
  <li><strong>Absolute Sightline Protection:</strong> Bosses and mini-bosses are never culled behind solid blocks, never culled by crowd caps, and never downscaled by texture LOD.</li>
</ul>

<blockquote>💡 <strong>Heart Conversion Display:</strong> Adjust thresholds in-game with instant heart feedback via <code>/cameraculling bosshealth &lt;hp&gt;</code> and <code>/cameraculling minibosshealth &lt;hp&gt;</code>.</blockquote>

<h3>📦 Block Entity Occlusion Culling</h3>
<p>Skips render extraction for block entities that are completely encased or occluded:</p>
<ul>
  <li><strong>Enclosure Check:</strong> Automatically unrenders chests, signs, banners, skulls, and decorated pots that are fully surrounded on all 6 faces by solid opaque blocks.</li>
  <li><strong>Line-of-Sight Check:</strong> Skips rendering when blocked from camera view on aggressive culling profiles.</li>
</ul>

<h3>⚙️ 4 Culling Intensity Profiles</h3>
<p>Choose the ideal balance of performance and visual fidelity:</p>
<ul>
  <li><strong>LOW (Conservative):</strong> 4.0-block safety buffer, 7-point sampling, padded hitboxes.</li>
  <li><strong>MEDIUM (Balanced - Default):</strong> 2.0-block safety buffer, 3-point sampling + corner checks.</li>
  <li><strong>HIGH (Aggressive):</strong> 1.0-block safety buffer, fast 2-point sampling, entity-behind-entity culling active, aggressive block entity culling.</li>
  <li><strong>SUPER (Extreme / Potato PC):</strong> 0.25-block safety buffer, ultra-fast 1-point center check for maximum FPS on low-end hardware.</li>
</ul>

<h3>🎮 Zero Multiplayer Desync (100% Client-Side)</h3>
<p>Camera Culling hooks strictly into the client render pipeline (<code>EntityRenderer</code>, <code>BlockEntityRenderDispatcher</code>, <code>LivingEntityRenderer</code>). It sends zero network packets, requires no server-side installation, and never interferes with mob AI, spawning, or physics.</p>

<hr>

<h2>📋 Quick Command Reference</h2>
<p>All settings can be inspected and adjusted in-game via the <code>/cameraculling</code> command suite:</p>

<pre><code>/cameraculling status                               → View live statistics, active level, and thresholds
/cameraculling toggle                               → Toggle culling on or off
/cameraculling set &lt;low|medium|high|super&gt;          → Change culling intensity preset
/cameraculling blacklist add &lt;entity_id&gt;            → Add mob to personal client immunity list (e.g. minecraft:wolf)
/cameraculling blacklist remove &lt;entity_id&gt;         → Remove mob from personal immunity list
/cameraculling blacklist list                       → List all personal blacklisted entities
/cameraculling serverblacklist add &lt;entity_id&gt;      → Add mob to server-wide admin immunity list (OP)
/cameraculling texturlod &lt;true|false&gt;               → Toggle distance texture LOD independently
/cameraculling texturlod range &lt;near&gt; &lt;far&gt;         → Set custom texture LOD distances (e.g. 16.0 32.0)
/cameraculling bossimmunity &lt;true|false&gt;            → Toggle boss &amp; mini-boss immunity
/cameraculling bosshealth &lt;hp&gt;                      → Set major boss health threshold (e.g. 150)
/cameraculling minibosshealth &lt;hp&gt;                  → Set mini-boss health threshold (e.g. 50)
/cameraculling entityculling &lt;true|false|auto&gt;      → Toggle entity-behind-entity crowd culling
/cameraculling maxcluster &lt;1-128&gt;                   → Set cluster density cap for packed mob pens
/cameraculling reload                               → Reload configuration from disk</code></pre>

<hr>

<h2>📦 Installation &amp; Environment</h2>

<h3>⚛️ Environment Support</h3>
<ul>
  <li>✅ <strong>Client-side only:</strong> All functionality is done client-side and is compatible with vanilla servers.
    <ul>
      <li>✅ Works in singleplayer too</li>
      <li>✅ Compatible with any multiplayer server</li>
    </ul>
  </li>
  <li>❌ <strong>Server-side only:</strong> Server-only installation.</li>
  <li>❌ <strong>Client and server:</strong> Requires installation on both sides.</li>
</ul>

<h3>📥 Install Instructions</h3>
<ol>
  <li>Install <a href="https://www.curseforge.com/minecraft/mc-mods/fabric-api"><strong>Fabric API</strong></a>.</li>
  <li>Download the latest <strong>Camera Culling</strong> JAR for your Minecraft version and place it in your <code>.minecraft/mods</code> folder.</li>
  <li>Launch Minecraft and enjoy smoother frame rates!</li>
</ol>

<hr>

<h2>☕ Support</h2>

<p>If you enjoy <strong>Camera Culling</strong> and the <strong>Vanilla Outsider</strong> philosophy, consider fueling the next update!</p>

<p align="center">
  <a href="https://ko-fi.com/dasikigaijin/tip"><img src="https://img.shields.io/badge/Ko--fi-Support%20Me-FF5E5B?style=for-the-badge&logo=ko-fi&logoColor=white" alt="Ko-fi"></a>
  <a href="https://sociabuzz.com/dasikigaijin/tribe"><img src="https://img.shields.io/badge/SocioBuzz-Local_Support-7BB32E?style=for-the-badge" alt="SocioBuzz"></a>
  <a href="https://saweria.co/DasikIgaijinn"><img src="https://img.shields.io/badge/Saweria-Local_Support-FFA500?style=for-the-badge" alt="Saweria"></a>
</p>

<blockquote><strong>🇮🇩 Indonesian Users:</strong> SocioBuzz and Saweria support local payment methods (Gopay, OVO, Dana, etc.) if you want to support me without using PayPal/Ko-fi!</blockquote>

<hr>

<h2>📜 Credits</h2>

<table>
  <thead>
    <tr>
      <th align="left">Role</th>
      <th align="left">Author</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td><strong>Creator</strong></td>
      <td><strong>Dasik</strong> (Rifaditya)</td>
    </tr>
    <tr>
      <td><strong>Collection</strong></td>
      <td>Vanilla Outsider</td>
    </tr>
    <tr>
      <td><strong>License</strong></td>
      <td>GNU GPLv3</td>
    </tr>
  </tbody>
</table>

<hr>

<blockquote>
  <strong>📦 Modpack Permissions &amp; Distribution:</strong><br>
  You are free to include this mod in any modpack on any platform. However, the mod itself must be downloaded from its official distribution pages on <strong>Modrinth</strong> or <strong>CurseForge</strong>. Re-uploading or redistributing the mod jar file to third-party sites is strictly prohibited unless explicitly permitted by the creator.
  <br><br>
  <strong>License &amp; Forks:</strong><br>
  Since the source code is licensed under <strong>GNU GPLv3</strong>, you are fully permitted to fork the repository, make modifications, build your own versions, and distribute them under the terms of the GPLv3. The prohibition on third-party redistribution applies exclusively to the official compiled releases/jars published by the original creator (Dasik/Rifaditya). Forks must be published as distinct projects, not direct re-uploads of official builds.
</blockquote>

<hr>

<div align="center">
  <p><strong>Made with ❤️ for the Minecraft community</strong></p>
  <p><em>Part of the Vanilla Outsider Collection</em></p>
</div>
