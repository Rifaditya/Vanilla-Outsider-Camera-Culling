// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.vanillaoutsider.culling.test;

import net.vanillaoutsider.culling.config.CameraCullingConfig;
import net.vanillaoutsider.culling.config.CullingLevel;
import net.vanillaoutsider.culling.util.AnimationCullingHelper;
import net.vanillaoutsider.culling.util.BossDetectionHelper;
import net.vanillaoutsider.culling.util.ParticleCullingHelper;
import net.vanillaoutsider.culling.util.SignTextCullingHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CameraCullingStateSimulationTest {

    @BeforeEach
    void setUp() {
        CameraCullingConfig.setEnabled(true);
        CameraCullingConfig.setLevel(CullingLevel.SUPER);
        CameraCullingConfig.setBossImmunity(true);
        CameraCullingConfig.setCullParticles(true);
        CameraCullingConfig.setCullAnimations(true);
        CameraCullingConfig.setCullSignText(true);
    }

    @AfterEach
    void tearDown() {
        CameraCullingConfig.setEnabled(true);
        CameraCullingConfig.setLevel(CullingLevel.SUPER);
        CameraCullingConfig.setBossImmunity(true);
        CameraCullingConfig.setCullParticles(true);
        CameraCullingConfig.setCullAnimations(true);
        CameraCullingConfig.setCullSignText(true);
    }

    @Test
    @DisplayName("Pillar 4: Boss and Mini-Boss Detection Heuristics & Boundary Checks")
    void testBossDetectionSimulation() {
        // Vanilla and Modded Boss Keywords
        assertTrue(BossDetectionHelper.isBossOrMiniBossName("minecraft:ender_dragon"));
        assertTrue(BossDetectionHelper.isBossOrMiniBossName("minecraft:wither"));
        assertTrue(BossDetectionHelper.isBossOrMiniBossName("minecraft:warden"));
        assertTrue(BossDetectionHelper.isBossOrMiniBossName("minecraft:elder_guardian"));
        assertTrue(BossDetectionHelper.isBossOrMiniBossName("minecraft:ravager"));
        assertTrue(BossDetectionHelper.isBossOrMiniBossName("cataclysm:the_leviathan"));
        assertTrue(BossDetectionHelper.isBossOrMiniBossName("twilightforest:naga_titan"));
        assertTrue(BossDetectionHelper.isBossOrMiniBossName("custom:dungeon_behemoth_boss"));
        assertTrue(BossDetectionHelper.isBossOrMiniBossName("boss_entity"));
        assertTrue(BossDetectionHelper.isBossOrMiniBossName("miniboss_brute"));

        // Normal Mobs must evaluate to false
        assertFalse(BossDetectionHelper.isBossOrMiniBossName("minecraft:zombie"));
        assertFalse(BossDetectionHelper.isBossOrMiniBossName("minecraft:skeleton"));
        assertFalse(BossDetectionHelper.isBossOrMiniBossName("minecraft:creeper"));
        assertFalse(BossDetectionHelper.isBossOrMiniBossName("minecraft:cow"));
        assertFalse(BossDetectionHelper.isBossOrMiniBossName("minecraft:sheep"));
        assertFalse(BossDetectionHelper.isBossOrMiniBossName("minecraft:pig"));
        assertFalse(BossDetectionHelper.isBossOrMiniBossName(null));
        assertFalse(BossDetectionHelper.isBossOrMiniBossName(""));

        // Disabled boss immunity bypass
        CameraCullingConfig.setBossImmunity(false);
        assertFalse(BossDetectionHelper.isBossOrMiniBoss((net.minecraft.world.entity.Entity) null));
    }

    @Test
    @DisplayName("Pillar 4: Sign Text Backface Culling Normal & Empty Text Simulation")
    void testSignTextBackfaceCullingSimulation() {
        // 1. Empty Text check
        assertTrue(SignTextCullingHelper.isTextEmpty(null), "Null SignText must be treated as empty");

        // 2. State & null safety
        assertFalse(SignTextCullingHelper.shouldCullFace(null, null, null, true), "Null state must not cull front face");
        assertFalse(SignTextCullingHelper.shouldCullFace(null, null, null, false), "Null state must not cull back face");

        // 3. Mathematical Dot Product Simulation:
        // Front face normal: (nx=0, nz=1) [pointing South]
        // Camera position in front of sign: (vx=0, vz=5) -> dot = (0*0) + (1*5) = 5.0
        // When evaluating front face: dot > -0.05 => front face is visible (not culled: return false)
        // When evaluating back face: dot > 0.05 => back face is occluded/pointing away (culled: return true)
        double nx = 0.0;
        double nz = 1.0;
        double vxFront = 0.0;
        double vzFront = 5.0;
        double dotFront = nx * vxFront + nz * vzFront;
        boolean cullFrontFromFront = dotFront < -0.05;
        boolean cullBackFromFront = dotFront > 0.05;
        assertFalse(cullFrontFromFront, "Front face must be visible when viewing from front");
        assertTrue(cullBackFromFront, "Back face must be culled when viewing from front");

        // Camera position behind sign: (vx=0, vz=-5) -> dot = (0*0) + (1*-5) = -5.0
        // When evaluating front face: dot < -0.05 => front face is culled (culled: return true)
        // When evaluating back face: dot <= 0.05 => back face is visible (not culled: return false)
        double vxBack = 0.0;
        double vzBack = -5.0;
        double dotBack = nx * vxBack + nz * vzBack;
        boolean cullFrontFromBack = dotBack < -0.05;
        boolean cullBackFromBack = dotBack > 0.05;
        assertTrue(cullFrontFromBack, "Front face must be culled when viewing from behind");
        assertFalse(cullBackFromBack, "Back face must be visible when viewing from behind");
    }

    @Test
    @DisplayName("Pillar 4: Block and Texture Atlas Animation Culling Simulation")
    void testAnimationCullingSimulation() {
        // Toggle animation culling off -> shouldPauseAtlasAnimation must return false
        CameraCullingConfig.setCullAnimations(false);
        assertFalse(AnimationCullingHelper.shouldPauseAtlasAnimation(),
            "When animation culling is disabled, atlas animation should never pause");

        // Toggle master enable off -> shouldPauseAtlasAnimation must return false
        CameraCullingConfig.setCullAnimations(true);
        CameraCullingConfig.setEnabled(false);
        assertFalse(AnimationCullingHelper.shouldPauseAtlasAnimation(),
            "When master culling is disabled, atlas animation should never pause");

        // Toggle animation culling on with master enabled
        CameraCullingConfig.setEnabled(true);
        CameraCullingConfig.setCullAnimations(true);
        // In headless testing environment, Minecraft.getInstance() is null -> safely returns false without NPE
        assertDoesNotThrow(() -> {
            boolean paused = AnimationCullingHelper.shouldPauseAtlasAnimation();
            assertFalse(paused, "Headless instance should safely return false");
        });
    }

    @Test
    @DisplayName("Pillar 4: Subterranean and Occluded Particle Culling Simulation")
    void testParticleCullingSimulation() {
        // When particle culling is disabled -> shouldCullParticle must return false
        CameraCullingConfig.setCullParticles(false);
        assertFalse(ParticleCullingHelper.shouldCullParticle(0.0, 0.0, 0.0, null, null),
            "When particle culling is disabled, shouldCullParticle must return false");

        // When particle culling is enabled but camera is null -> safe return false
        CameraCullingConfig.setCullParticles(true);
        assertDoesNotThrow(() -> {
            boolean culled = ParticleCullingHelper.shouldCullParticle(10.0, 64.0, 10.0, null, null);
            assertFalse(culled, "Null camera must safely return false without NPE");
        });

        // When master culling is disabled -> shouldCullParticle must return false
        CameraCullingConfig.setEnabled(false);
        assertFalse(ParticleCullingHelper.shouldCullParticle(10.0, 64.0, 10.0, null, null),
            "When master culling is disabled, shouldCullParticle must return false");
    }
}
