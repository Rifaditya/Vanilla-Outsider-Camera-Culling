<div align="center">
  <img src="https://raw.githubusercontent.com/Rifaditya/Vanilla-Outsider-Camera-Culling/main/Doc/Media/icon.png" alt="Camera Culling Mod Icon" width="180">
  <h1>📷 Camera Culling</h1>
  <p><strong>Unrender the unseen. Lightweight, zero-allocation camera occlusion culling, 2-sided sign text culling & distance texture LOD.</strong></p>
  <p>
    <img src="https://img.shields.io/badge/Requires-Fabric_API-blue?style=for-the-badge&logo=fabric" alt="Requires Fabric API">
    <img src="https://img.shields.io/badge/Language-Java_25-orange?style=for-the-badge&logo=java" alt="Java 25">
    <img src="https://img.shields.io/badge/License-GPLv3-green?style=for-the-badge" alt="License">
    <img src="https://img.shields.io/badge/Minecraft-26.1+-brightgreen?style=for-the-badge" alt="Minecraft 26.1+">
  </p>
</div>

<hr>

<h2>The Vanilla Problem</h2>
<p>In vanilla Minecraft, the game's renderer extracts render states and draws thousands of entities, block entities, particle quads, and sign text glyphs even when they are hidden behind solid walls, underground in caves, or obscured from your field of view. Furthermore, rendering fully-resolved textures on distant mobs consumes precious GPU fillrate and memory bandwidth.</p>

<h2>What Camera Culling Does</h2>
<p><strong>Camera Culling</strong> solves this at the rendering root. It performs lightning-fast client-side raycasting and frustum occlusion checks against block collisions to dynamically unrender occluded entities, block entities, particles, and sign text faces. Combined with a smart crowd overdraw defense, distance-based texture LOD mipmap scaling, dynamic boss/mini-boss protections, zero per-frame heap allocations, and an anti-flicker temporal hysteresis buffer, Camera Culling drastically increases frame rates without altering world simulation or network packets.</p>

<hr>

<h2>✨ Key Optimization Features</h2>

<h3>🧱 Entity Occlusion Culling (Zero-Allocation Raycast Engine)</h3>
<ul>
  <li><strong>Zero-Allocation Hot-Path Engine:</strong> Passes primitive coordinates directly to eliminate intermediate <code>Vec3</code> heap allocations, removing JVM garbage collection stutter spikes when panning camera across dense entity herds.</li>
  <li><strong>Multi-Point Precision:</strong> Evaluates key entity sightlines (head top, anatomical eye height, upper torso, center, and elevated flanks) so mobs are never culled prematurely when partially visible around corners.</li>
  <li><strong>Directional Floor Filtering:</strong> Filters out upward block collisions (<code>Direction.UP</code>) at entity base level, preventing ground-grazing false culling on slopes and hills.</li>
</ul>

<h3>🛡️ Anti-Flicker Temporal Hysteresis</h3>
<ul>
  <li><strong>Distance-Scaled Grace Buffer:</strong> Adaptive frame debounce decay (4 frames near &le; 32m, 8 frames medium 32-64m, 12 frames far &gt; 64m) absorbs camera turning and walking view-bobbing jitter without sudden pop-in.</li>
</ul>

<h3>🪧 2-Sided Sign &amp; Hanging Sign Back-Face Text Culling</h3>
<ul>
  <li><strong>Normal Vector Dot-Product Math:</strong> Calculates the sign face normal vector to automatically skip font rendering passes for whichever side faces away from the camera.</li>
  <li><strong>Empty-Side Fast-Pass:</strong> Immediately skips blank sign faces with zero math overhead.</li>
  <li><strong>50% to 100% Draw Call Reduction</strong> on signs in storage warehouses and multiplayer towns.</li>
</ul>

<h3>✨ Particle &amp; Animation Occlusion Culling</h3>
<ul>
  <li><strong>Particle Occlusion:</strong> Culls particles behind solid blocks with a 4.0-meter proximity safety bubble around the player's camera.</li>
  <li><strong>TextureAtlas Animation Freezing:</strong> Freezes 3D block animations and skips off-screen texture atlas frame uploads when the game is paused or modal menus are open.</li>
</ul>

<h3>👥 Entity-Behind-Entity Culling (Crowd Overdraw Defense)</h3>
<ul>
  <li><strong>Cluster Density Cap:</strong> Automatically caps rendered entities in tight 1.5-block clusters (default: 8 mobs per cluster), preventing rendering lag in cramming farms.</li>
  <li><strong>16-Meter Distance Fast-Fail:</strong> Completely bypasses crowd queries beyond 16 meters, ensuring zero CPU overhead in open fields.</li>
</ul>

<h3>🎨 Distance-Based Mob Texture LOD</h3>
<ul>
  <li><strong>Near (&lt; 16 blocks):</strong> 100% Native Full-Resolution crisp textures (0.0 LOD bias).</li>
  <li><strong>Medium (16 &ndash; 32 blocks):</strong> Half-Resolution texture sampling (1.0 LOD bias).</li>
  <li><strong>Far (&gt; 32 blocks):</strong> Quarter-Resolution low-res mipmap sampling (2.5 LOD bias).</li>
</ul>

<h3>👑 Dynamic Boss &amp; Mini-Boss Immunity</h3>
<ul>
  <li><strong>Automatic Recognition:</strong> Protects Ender Dragons, Withers, Wardens, Elder Guardians, Ravagers, Iron Golems, Piglin Brutes, Evokers, Breezes, and modded champions.</li>
  <li><strong>Configurable Thresholds:</strong> Major Boss (150 HP / 75 hearts), Mini-Boss (50 HP / 25 hearts).</li>
</ul>

<h3>⚙️ Graphical GUI &amp; In-Game Commands</h3>
<ul>
  <li>Optional <strong>YetAnotherConfigLib (YACL v3)</strong> &amp; <strong>ModMenu</strong> configuration screen with 3 organized categories.</li>
  <li>Complete <code>/cameraculling</code> Brigadier command suite with real-time chat diagnostic logging (<code>/cameraculling debug</code>).</li>
</ul>

<hr>

<h2>📋 Quick Command Reference</h2>
<pre>
/cameraculling status                               → View live statistics, active level, and thresholds
/cameraculling toggle                               → Toggle culling on or off
/cameraculling set &lt;low|medium|high|super&gt;          → Change culling intensity preset
/cameraculling particles [true|false]               → Toggle particle occlusion culling
/cameraculling animations [true|false]              → Toggle block &amp; texture atlas animation culling
/cameraculling crowdculling &lt;true|false&gt;            → Toggle crowd overdraw / entity-behind-entity culling
/cameraculling cluster &lt;1-128&gt;                      → Set cluster density cap for packed mob pens
/cameraculling texturlod &lt;true|false&gt;               → Toggle distance texture LOD independently
/cameraculling texturlod range &lt;near&gt; &lt;far&gt;         → Set custom texture LOD distances (e.g. 16.0 32.0)
/cameraculling bossimmunity &lt;true|false&gt;            → Toggle boss &amp; mini-boss immunity
/cameraculling bosshealth &lt;hp&gt;                      → Set major boss health threshold (e.g. 150)
/cameraculling minibosshealth &lt;hp&gt;                  → Set mini-boss health threshold (e.g. 50)
/cameraculling blacklist &lt;add|remove|list|clear&gt;    → Manage personal client immunity list
/cameraculling serverblacklist &lt;add|remove|list&gt;    → Manage server admin immunity list
/cameraculling debug [true|false]                   → Toggle real-time diagnostic tracing in chat &amp; logs
/cameraculling reload                               → Reload configuration from disk
</pre>

<hr>

<h2>📦 Installation</h2>
<ol>
  <li>Install <strong>Fabric API</strong>.</li>
  <li>(Optional) Install <strong>ModMenu</strong> and <strong>YetAnotherConfigLib (YACL)</strong> for graphical settings.</li>
  <li>Download the <strong>Camera Culling</strong> JAR for your Minecraft version and place it in your <code>.minecraft/mods</code> folder.</li>
</ol>

<hr>

<p align="center">
  <strong>Part of the Vanilla Outsider Collection</strong><br>
  <em>Created by Dasik (Rifaditya) | Licensed under GNU GPLv3</em>
</p>
