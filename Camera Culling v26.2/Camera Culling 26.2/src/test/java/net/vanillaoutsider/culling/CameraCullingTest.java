// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.vanillaoutsider.culling;

import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.world.entity.Entity;
import net.vanillaoutsider.culling.config.CameraCullingConfig;
import net.vanillaoutsider.culling.config.CullingLevel;
import net.vanillaoutsider.culling.util.BlacklistHelper;
import net.vanillaoutsider.culling.util.BossDetectionHelper;
import net.vanillaoutsider.culling.util.EntityLodHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CameraCullingTest {

    @BeforeEach
    void setUp() {
        CameraCullingConfig.setEnabled(true);
        CameraCullingConfig.setLevel(CullingLevel.MEDIUM);
        CameraCullingConfig.setCullEntitiesBehindEntities(null);
        CameraCullingConfig.setMaxEntitiesPerCluster(8);
        CameraCullingConfig.setEntityDetailLod(true);
        CameraCullingConfig.setEntityLodDistance(32.0);
        CameraCullingConfig.setBossImmunity(true);
        CameraCullingConfig.setBossHealthThreshold(150.0);
        CameraCullingConfig.setMiniBossHealthThreshold(50.0);
        CameraCullingConfig.clearClientBlacklist();
        CameraCullingConfig.clearServerBlacklist();
    }

    @Test
    void testClientAndServerBlacklist() {
        assertFalse(CameraCullingConfig.isEntityBlacklisted("minecraft:wolf"));

        // Add client blacklist
        assertTrue(CameraCullingConfig.addClientBlacklist("minecraft:wolf"));
        assertTrue(CameraCullingConfig.isEntityBlacklisted("minecraft:wolf"));
        assertTrue(CameraCullingConfig.getClientBlacklist().contains("minecraft:wolf"));

        // Add duplicate
        assertFalse(CameraCullingConfig.addClientBlacklist("minecraft:wolf"));

        // Add server blacklist
        assertTrue(CameraCullingConfig.addServerBlacklist("minecraft:allay"));
        assertTrue(CameraCullingConfig.isEntityBlacklisted("minecraft:allay"));
        assertTrue(CameraCullingConfig.getServerBlacklist().contains("minecraft:allay"));

        // Non-blacklisted mob
        assertFalse(CameraCullingConfig.isEntityBlacklisted("minecraft:zombie"));

        // Remove from client blacklist
        assertTrue(CameraCullingConfig.removeClientBlacklist("minecraft:wolf"));
        assertFalse(CameraCullingConfig.isEntityBlacklisted("minecraft:wolf"));

        // Server blacklist is still active
        assertTrue(CameraCullingConfig.isEntityBlacklisted("minecraft:allay"));

        // Clear all
        CameraCullingConfig.clearServerBlacklist();
        assertFalse(CameraCullingConfig.isEntityBlacklisted("minecraft:allay"));
    }

    @Test
    void testBossAndMiniBossKeywordHeuristics() {
        // Major Bosses
        assertTrue(BossDetectionHelper.isBossOrMiniBossName("minecraft:ender_dragon"));
        assertTrue(BossDetectionHelper.isBossOrMiniBossName("minecraft:wither"));
        assertTrue(BossDetectionHelper.isBossOrMiniBossName("minecraft:warden"));
        assertTrue(BossDetectionHelper.isBossOrMiniBossName("cataclysm:netherite_monstrosity_boss"));
        assertTrue(BossDetectionHelper.isBossOrMiniBossName("twilightforest:hydra_titan"));
        assertTrue(BossDetectionHelper.isBossOrMiniBossName("mowziesmobs:ferrous_wroughtnaut_champion"));

        // Mini-Bosses
        assertTrue(BossDetectionHelper.isBossOrMiniBossName("minecraft:elder_guardian"));
        assertTrue(BossDetectionHelper.isBossOrMiniBossName("minecraft:ravager"));
        assertTrue(BossDetectionHelper.isBossOrMiniBossName("minecraft:iron_golem"));
        assertTrue(BossDetectionHelper.isBossOrMiniBossName("minecraft:piglin_brute"));
        assertTrue(BossDetectionHelper.isBossOrMiniBossName("minecraft:evoker"));
        assertTrue(BossDetectionHelper.isBossOrMiniBossName("mod:dungeon_elite_guard"));
        assertTrue(BossDetectionHelper.isBossOrMiniBossName("mod:goblin_miniboss"));

        // Normal Mobs (Must NOT be flagged as boss/mini-boss)
        assertFalse(BossDetectionHelper.isBossOrMiniBossName("minecraft:zombie"));
        assertFalse(BossDetectionHelper.isBossOrMiniBossName("minecraft:skeleton"));
        assertFalse(BossDetectionHelper.isBossOrMiniBossName("minecraft:creeper"));
        assertFalse(BossDetectionHelper.isBossOrMiniBossName("minecraft:cow"));
        assertFalse(BossDetectionHelper.isBossOrMiniBossName("minecraft:sheep"));
        assertFalse(BossDetectionHelper.isBossOrMiniBossName(null));
        assertFalse(BossDetectionHelper.isBossOrMiniBossName(""));
    }

    @Test
    void testBossHealthThresholdsAndClamping() {
        assertEquals(150.0, CameraCullingConfig.getBossHealthThreshold());
        assertEquals(50.0, CameraCullingConfig.getMiniBossHealthThreshold());

        // Test custom threshold settings
        CameraCullingConfig.setBossHealthThreshold(200.0);
        assertEquals(200.0, CameraCullingConfig.getBossHealthThreshold());

        CameraCullingConfig.setMiniBossHealthThreshold(75.0);
        assertEquals(75.0, CameraCullingConfig.getMiniBossHealthThreshold());

        // Bounds clamping [1.0, 10000.0]
        CameraCullingConfig.setBossHealthThreshold(0.5);
        assertEquals(1.0, CameraCullingConfig.getBossHealthThreshold());

        CameraCullingConfig.setBossHealthThreshold(50000.0);
        assertEquals(10000.0, CameraCullingConfig.getBossHealthThreshold());

        CameraCullingConfig.setMiniBossHealthThreshold(-10.0);
        assertEquals(1.0, CameraCullingConfig.getMiniBossHealthThreshold());

        CameraCullingConfig.setMiniBossHealthThreshold(20000.0);
        assertEquals(10000.0, CameraCullingConfig.getMiniBossHealthThreshold());
    }

    @Test
    void testBossImmunityToggle() {
        assertTrue(CameraCullingConfig.isBossImmunity());
        CameraCullingConfig.setBossImmunity(false);
        assertFalse(CameraCullingConfig.isBossImmunity());

        // When immunity disabled, isBossOrMiniBoss returns false
        assertFalse(BossDetectionHelper.isBossOrMiniBoss((Entity) null));
        assertFalse(BossDetectionHelper.isBossOrMiniBoss((EntityRenderState) null));
    }

    @Test
    void testEntityLodShadowScaleCalculation() {
        // Distance < 32m (e.g. 10m -> 100 sq dist) -> 1.0f (Full shadow)
        assertEquals(1.0f, EntityLodHelper.calculateShadowScale(100.0, 32.0, 16.0));

        // Distance = 32m -> 1.0f
        assertEquals(1.0f, EntityLodHelper.calculateShadowScale(1024.0, 32.0, 16.0));

        // Distance = 40m (halfway between 32m and 48m -> 1600 sq dist) -> 0.5f
        assertEquals(0.5f, EntityLodHelper.calculateShadowScale(1600.0, 32.0, 16.0), 0.001f);

        // Distance >= 48m (e.g. 50m -> 2500 sq dist) -> 0.0f (Completely culled shadow)
        assertEquals(0.0f, EntityLodHelper.calculateShadowScale(2500.0, 32.0, 16.0));

        // Null / Zero distance safety
        assertEquals(1.0f, EntityLodHelper.calculateShadowScale(0.0, 32.0, 16.0));
        assertEquals(1.0f, EntityLodHelper.calculateShadowScale(-10.0, 32.0, 16.0));
    }

    @Test
    void testEntityDetailLodConfigToggle() {
        assertTrue(CameraCullingConfig.isEntityDetailLod());
        CameraCullingConfig.setEntityDetailLod(false);
        assertFalse(CameraCullingConfig.isEntityDetailLod());
        CameraCullingConfig.setEntityDetailLod(true);

        assertEquals(32.0, CameraCullingConfig.getEntityLodDistance());
        CameraCullingConfig.setEntityLodDistance(48.0);
        assertEquals(48.0, CameraCullingConfig.getEntityLodDistance());
        CameraCullingConfig.setEntityLodDistance(32.0);
    }

    @Test
    void testCullingLevelProfiles() {
        assertEquals(CullingLevel.LOW, CullingLevel.fromString("low"));
        assertEquals(CullingLevel.MEDIUM, CullingLevel.fromString("medium"));
        assertEquals(CullingLevel.HIGH, CullingLevel.fromString("high"));
        assertEquals(CullingLevel.SUPER, CullingLevel.fromString("super"));
        assertEquals(CullingLevel.SUPER, CullingLevel.fromString("invalid"));

        assertEquals(25.0, CullingLevel.LOW.getMinDistanceSq());
        assertEquals(12.25, CullingLevel.MEDIUM.getMinDistanceSq());
        assertEquals(9.0, CullingLevel.HIGH.getMinDistanceSq());
        assertEquals(4.0, CullingLevel.SUPER.getMinDistanceSq());
    }

    @Test
    void testClusterDensityCap() {
        assertEquals(8, CameraCullingConfig.getMaxEntitiesPerCluster());
        CameraCullingConfig.setMaxEntitiesPerCluster(16);
        assertEquals(16, CameraCullingConfig.getMaxEntitiesPerCluster());

        // Clamp minimum
        CameraCullingConfig.setMaxEntitiesPerCluster(0);
        assertEquals(1, CameraCullingConfig.getMaxEntitiesPerCluster());

        // Clamp maximum
        CameraCullingConfig.setMaxEntitiesPerCluster(200);
        assertEquals(128, CameraCullingConfig.getMaxEntitiesPerCluster());
    }

    @Test
    void testParticleCullingConfigAndNullSafety() {
        assertTrue(CameraCullingConfig.isCullParticles());
        CameraCullingConfig.setCullParticles(false);
        assertFalse(CameraCullingConfig.isCullParticles());

        // When disabled, shouldCullParticle is always false
        assertFalse(net.vanillaoutsider.culling.util.ParticleCullingHelper.shouldCullParticle(0, 0, 0, null, null));

        CameraCullingConfig.setCullParticles(true);
        // Null camera/level safety
        assertFalse(net.vanillaoutsider.culling.util.ParticleCullingHelper.shouldCullParticle(0, 0, 0, null, null));
    }

    @Test
    void testAnimationCullingConfig() {
        assertTrue(CameraCullingConfig.isCullAnimations());
        CameraCullingConfig.setCullAnimations(false);
        assertFalse(CameraCullingConfig.isCullAnimations());

        // When disabled, shouldPauseAtlasAnimation is false
        assertFalse(net.vanillaoutsider.culling.util.AnimationCullingHelper.shouldPauseAtlasAnimation());

        CameraCullingConfig.setCullAnimations(true);
    }

    @Test
    void testSignTextCullingConfigAndNullSafety() {
        assertTrue(CameraCullingConfig.isCullSignText());
        CameraCullingConfig.setCullSignText(false);
        assertFalse(CameraCullingConfig.isCullSignText());

        // When disabled, returns false
        assertFalse(net.vanillaoutsider.culling.util.SignTextCullingHelper.shouldCullSignTextSide(null, null, true));
        assertFalse(net.vanillaoutsider.culling.util.SignTextCullingHelper.shouldCullFace(null, null, null, true));

        CameraCullingConfig.setCullSignText(true);
        // Null safety
        assertFalse(net.vanillaoutsider.culling.util.SignTextCullingHelper.shouldCullSignTextSide(null, null, true));
        assertFalse(net.vanillaoutsider.culling.util.SignTextCullingHelper.shouldCullFace(null, null, null, true));

        // Empty text detection
        assertTrue(net.vanillaoutsider.culling.util.SignTextCullingHelper.isTextEmpty(null));
    }
}
