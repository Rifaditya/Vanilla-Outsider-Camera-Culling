<p align="center">
  <img src="https://raw.githubusercontent.com/Rifaditya/Vanilla-Outsider-Camera-Culling/main/Doc/Media/icon.png" alt="Camera Culling Mod Icon" width="180">
</p>
<p align="center">
  <a href="https://www.curseforge.com/minecraft/mc-mods/fabric-api"><img src="https://img.shields.io/badge/Requires-Fabric_API-blue?style=for-the-badge&amp;logo=fabric" alt="Requires Fabric API"></a>
  <img src="https://img.shields.io/badge/Language-Java_25-orange?style=for-the-badge&amp;logo=java" alt="Java 25">
  <img src="https://img.shields.io/badge/License-GPLv3-green?style=for-the-badge" alt="License">
  <img src="https://img.shields.io/badge/Minecraft-26.1+-brightgreen?style=for-the-badge" alt="Minecraft 26.1+">
</p>

<h1>📷 Camera Culling</h1>

<p><strong>Active Version Policy:</strong> I build <strong>1 JAR for 1 Version</strong>. I support active Minecraft versions (<strong>26.1.2</strong>, <strong>26.2</strong>, and <strong>26.3</strong>). Each version is compiled natively with zero compromises.</p>

<blockquote><strong>Unrender the unseen. Lightweight, zero-allocation camera occlusion culling, 2-sided sign text culling &amp; distance texture LOD.</strong></blockquote>

<h2>The Vanilla Problem</h2>
<p>In vanilla Minecraft, the game's renderer extracts render states and draws thousands of entities, block entities, particle quads, and sign text glyphs even when they are hidden behind solid walls, underground in caves, or obscured from your field of view. Furthermore, rendering fully-resolved textures on distant mobs consumes precious GPU fillrate and memory bandwidth.</p>

<h2>What Camera Culling Does</h2>
<p><strong>Camera Culling</strong> solves this at the rendering root. It performs lightning-fast client-side raycasting and frustum occlusion checks against block collisions to dynamically unrender occluded entities, block entities, particles, and sign text faces. Combined with a smart crowd overdraw defense, distance-based texture LOD mipmap scaling, dynamic boss/mini-boss protections, zero per-frame heap allocations, and an anti-flicker temporal hysteresis buffer, Camera Culling drastically increases frame rates without altering world simulation or network packets.</p>

<p>Part of the <strong>Vanilla Outsider Collection</strong> &mdash; mods that refine the vanilla experience with modern standards.</p>

<p>🔗 <strong>Official GitHub Wiki</strong>: <a href="https://github.com/Rifaditya/Vanilla-Outsider-Camera-Culling/wiki" target="_blank" rel="noopener">https://github.com/Rifaditya/Vanilla-Outsider-Camera-Culling/wiki</a></p>

<hr>

<h2>✨ Key Optimization Features</h2>

<h3>🧱 Entity Occlusion Culling (Zero-Allocation Raycast Engine)</h3>
<p>Never waste GPU cycles rendering mobs you cannot see. Fast multi-point raytracing against solid block collision geometry unrenders entities hidden behind walls, cave ceilings, and terrain.</p>
<ul>
  <li><strong>Zero-Allocation Hot-Path Engine:</strong> Passing primitive coordinates directly eliminates intermediate <code>Vec3</code> heap allocations in continuous frame rendering, removing JVM Young-Gen garbage collection stutter spikes when panning the camera across dense entity herds.</li>
  <li><strong>Multi-Point Precision:</strong> Evaluates key entity sightlines (head top, anatomical eye height, upper torso, center, and elevated flanks) to guarantee mobs are never culled prematurely when partially visible around corners.</li>
  <li><strong>Directional Floor Filtering:</strong> Ignores upward block normal collisions (<code>Direction.UP</code>) at entity base level, preventing ground-grazing false culling on slopes and hills.</li>
  <li><strong>Proximity Safety Margin:</strong> Mobs within your close proximity safety bubble are always rendered smoothly without delay.</li>
</ul>

<blockquote><strong>Zero Simulation Impact:</strong> Culling operates strictly on client-side render submission. Entity ticks, server synchronization, sound events, and physics remain 100% active.</blockquote>

<h3>🛡️ Anti-Flicker Temporal Hysteresis</h3>
<ul>
  <li><strong>Distance-Scaled Grace Buffer:</strong> Implements an adaptive frame debounce decay (4 frames near &le; 32m, 8 frames medium 32&ndash;64m, 12 frames far &gt; 64m) that absorbs camera rotation and walking view-bobbing jitter without sudden pop-in.</li>
  <li><strong>Instant Unculling:</strong> Mobs becoming visible are rendered immediately with 0-frame latency as soon as a single sightline sample connects.</li>
</ul>

<h3>🪧 2-Sided Sign &amp; Hanging Sign Back-Face Text Culling</h3>
<p>Signs with text on both sides render font glyphs, kerning tables, and glow outlines on both faces simultaneously &mdash; even when looking at only one side.</p>
<ul>
  <li><strong>Normal Vector Dot-Product Math:</strong> Calculates the sign face normal vector across Wall Signs, Standing Signs, and Hanging Signs to automatically skip font rendering passes for whichever side faces away from the camera.</li>
  <li><strong>Empty-Side Fast-Pass:</strong> Automatically detects blank/unwritten sign faces and skips text layout immediately with zero math overhead.</li>
  <li><strong>50% to 100% Draw Call Reduction</strong> on signs in storage warehouses and multiplayer towns.</li>
</ul>

<h3>✨ Particle &amp; Animation Occlusion Culling</h3>
<ul>
  <li><strong>Particle Occlusion:</strong> Raycasts particle sightlines against visual collision shapes with a 4.0-meter proximity safety bubble around the player's camera.</li>
  <li><strong>TextureAtlas Animation Freezing:</strong> Freezes 3D block animations and skips off-screen texture atlas frame cycling when the game is paused or modal menus are open.</li>
</ul>

<h3>👥 Entity-Behind-Entity Culling (Crowd Overdraw Defense)</h3>
<p>Dense mob farms and crowded pens are major FPS killers. Camera Culling detects when mobs are completely obscured behind closer, opaque mobs in front of them.</p>
<ul>
  <li><strong>Cluster Density Cap:</strong> Automatically caps rendered entities in tight 1.5-block clusters (default: 8 mobs per cluster), preventing rendering lag in cramming farms.</li>
  <li><strong>16-Meter Distance Fast-Fail:</strong> Completely bypasses crowd queries beyond 16 meters, ensuring zero CPU overhead in large open-field herds.</li>
  <li><strong>Decorative &amp; Small Mob Bypass:</strong> Transparent or small entities (Slimes, Magma Cubes, Vexes, Armor Stands) never block sightlines or occlude other mobs.</li>
</ul>

<h3>🎨 Distance-Based Mob Texture LOD</h3>
<p>Distant mobs don't need 4K-level crisp textures. Camera Culling features a decoupled, 3-tier OpenGL Mipmap LOD biasing engine that dynamically reduces texture resolution on distant mobs:</p>
<ul>
  <li><strong>Near (&lt; 16 blocks):</strong> 100% Native Full-Resolution crisp textures (0.0 LOD bias).</li>
  <li><strong>Medium (16 &ndash; 32 blocks):</strong> Half-Resolution texture sampling (1.0 LOD bias).</li>
  <li><strong>Far (&gt; 32 blocks):</strong> Quarter-Resolution low-res mipmap sampling (2.5 LOD bias).</li>
  <li><strong>GPU Fillrate Optimization:</strong> Drastically reduces VRAM fillrate and memory bandwidth on large herds of distant mobs without degrading close-up visual fidelity.</li>
</ul>

<h3>🛡️ Two-Tier Entity Immunity Blacklist</h3>
<p>Want specific entities to never be culled under any circumstances? Camera Culling provides a robust two-tier blacklist system:</p>
<ul>
  <li><strong>Client Personal Blacklist:</strong> Stored locally in <code>config/camera-culling.json</code>. Players can blacklist their favorite companions (e.g. <code>minecraft:wolf</code>, <code>minecraft:allay</code>, <code>minecraft:cat</code>).</li>
  <li><strong>Server Admin Blacklist:</strong> Configured in <code>config/camera-culling-server.json</code>. Server operators can enforce server-wide entity immunity across all connected clients.</li>
  <li><strong>Full Immunity:</strong> Blacklisted entities are 100% exempt from block occlusion culling, crowd overdraw culling, and texture LOD downscaling.</li>
</ul>

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

<h3>📦 Block Entity Occlusion Culling</h3>
<ul>
  <li><strong>Enclosure Check:</strong> Automatically unrenders chests, signs, banners, skulls, and decorated pots that are fully surrounded on all 6 faces by solid opaque blocks.</li>
  <li><strong>Line-of-Sight Check:</strong> Skips rendering when blocked from camera view on aggressive culling profiles.</li>
</ul>

<h3>⚙️ 4 Culling Intensity Profiles</h3>
<ul>
  <li><strong>LOW (Conservative):</strong> 4.0-block safety buffer, 7-point sampling, padded hitboxes.</li>
  <li><strong>MEDIUM (Balanced):</strong> 2.0-block safety buffer, 5-point sampling.</li>
  <li><strong>HIGH (Aggressive):</strong> 1.0-block safety buffer, fast 3-point sampling, aggressive block entity culling.</li>
  <li><strong>SUPER (Extreme &mdash; Default):</strong> 0.25-block safety buffer, ultra-fast 2-point check for maximum FPS on modern systems.</li>
</ul>

<hr>

<h2>📋 Quick Command Reference</h2>
<p>All settings can be inspected and adjusted in-game via the <code>/cameraculling</code> command suite:</p>

<pre>
/cameraculling status                               &rarr; View live statistics, active level, and thresholds
/cameraculling toggle                               &rarr; Toggle culling on or off
/cameraculling set &lt;low|medium|high|super&gt;          &rarr; Change culling intensity preset
/cameraculling particles [true|false]               &rarr; Toggle particle occlusion culling
/cameraculling animations [true|false]              &rarr; Toggle block &amp; texture atlas animation culling
/cameraculling crowdculling &lt;true|false&gt;            &rarr; Toggle crowd overdraw / entity-behind-entity culling
/cameraculling cluster &lt;1-128&gt;                      &rarr; Set cluster density cap for packed mob pens
/cameraculling texturlod &lt;true|false&gt;               &rarr; Toggle distance texture LOD independently
/cameraculling texturlod range &lt;near&gt; &lt;far&gt;         &rarr; Set custom texture LOD distances (e.g. 16.0 32.0)
/cameraculling bossimmunity &lt;true|false&gt;            &rarr; Toggle boss &amp; mini-boss immunity
/cameraculling bosshealth &lt;hp&gt;                      &rarr; Set major boss health threshold (e.g. 150)
/cameraculling minibosshealth &lt;hp&gt;                  &rarr; Set mini-boss health threshold (e.g. 50)
/cameraculling blacklist add &lt;entity_id&gt;            &rarr; Add mob to personal client immunity list (e.g. minecraft:wolf)
/cameraculling blacklist remove &lt;entity_id&gt;         &rarr; Remove mob from personal immunity list
/cameraculling blacklist list                       &rarr; List all personal blacklisted entities
/cameraculling blacklist clear                      &rarr; Clear personal immunity blacklist
/cameraculling serverblacklist add &lt;entity_id&gt;      &rarr; Add mob to server-wide admin immunity list (OP)
/cameraculling serverblacklist remove &lt;entity_id&gt;   &rarr; Remove mob from server admin immunity list
/cameraculling serverblacklist list                 &rarr; List all server blacklisted entities
/cameraculling serverblacklist clear                &rarr; Clear server admin blacklist
/cameraculling debug [true|false]                   &rarr; Toggle real-time diagnostic tracing in chat &amp; logs
/cameraculling reload                               &rarr; Reload configuration from disk
</pre>

<hr>

<h2>⚙️ Configuration File</h2>
<p>Settings are saved directly to <code>config/camera-culling.json</code>:</p>

<pre>
{
  "enabled": true,
  "level": "SUPER",
  "cullEntitiesBehindEntities": false,
  "maxEntitiesPerCluster": 8,
  "distanceTextureLod": true,
  "distanceTextureLodStart": 16.0,
  "distanceTextureLodFar": 32.0,
  "bossImmunity": true,
  "bossHealthThreshold": 150.0,
  "miniBossHealthThreshold": 50.0,
  "cullParticles": true,
  "cullAnimations": true,
  "cullSignText": true,
  "clientBlacklist": [],
  "debugMode": false
}
</pre>

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
  <li>Install <a href="https://www.curseforge.com/minecraft/mc-mods/fabric-api" target="_blank" rel="noopener"><strong>Fabric API</strong></a>.</li>
  <li>(Optional) Install <a href="https://www.curseforge.com/minecraft/mc-mods/modmenu" target="_blank" rel="noopener"><strong>ModMenu</strong></a> and <a href="https://www.curseforge.com/minecraft/mc-mods/yacl" target="_blank" rel="noopener"><strong>YetAnotherConfigLib (YACL)</strong></a> for graphical settings.</li>
  <li>Download the latest <strong>Camera Culling</strong> JAR for your Minecraft version and place it in your <code>.minecraft/mods</code> folder.</li>
  <li>Launch Minecraft and enjoy smooth, optimized frame rates!</li>
</ol>

<hr>

<h2>☕ Support</h2>
<p>If you enjoy <strong>Camera Culling</strong> and the <strong>Vanilla Outsider</strong> philosophy, consider fueling the next update!</p>
<p>
  <a href="https://ko-fi.com/dasikigaijin/tip" target="_blank" rel="noopener"><img src="https://img.shields.io/badge/Ko--fi-Support%20Me-FF5E5B?style=for-the-badge&amp;logo=ko-fi&amp;logoColor=white" alt="Ko-fi"></a>
  <a href="https://sociabuzz.com/dasikigaijin/tribe" target="_blank" rel="noopener"><img src="https://img.shields.io/badge/SocioBuzz-Local_Support-7BB32E?style=for-the-badge" alt="SocioBuzz"></a>
  <a href="https://saweria.co/DasikIgaijinn" target="_blank" rel="noopener"><img src="https://img.shields.io/badge/Saweria-Local_Support-FFA500?style=for-the-badge" alt="Saweria"></a>
</p>

<hr>

<h2>📜 Credits</h2>
<table border="1" cellpadding="6" cellspacing="0">
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
  <strong>📦 Modpack Permissions &amp; Distribution:</strong> You are free to include this mod in any modpack on any platform. However, the mod itself must be downloaded from its official distribution pages on <strong>Modrinth</strong> or <strong>CurseForge</strong>. Re-uploading or redistributing official JAR files to third-party sites is strictly prohibited.<br><br>
  <strong>License &amp; Forks:</strong> Since the source code is licensed under <strong>GNU GPLv3</strong>, you are fully permitted to fork the repository, make modifications, build your own versions, and distribute them under the terms of the GPLv3 as distinct projects.
</blockquote>

<hr>

<p align="center">
  <strong>Made with ❤️ for the Minecraft community</strong><br>
  <em>Part of the Vanilla Outsider Collection</em>
</p>
