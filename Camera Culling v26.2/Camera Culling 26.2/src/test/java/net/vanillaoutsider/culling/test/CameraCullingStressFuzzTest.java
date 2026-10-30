// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.vanillaoutsider.culling.test;

import net.vanillaoutsider.culling.config.CameraCullingConfig;
import net.vanillaoutsider.culling.config.CullingLevel;
import net.vanillaoutsider.culling.util.EntityLodHelper;
import net.vanillaoutsider.culling.util.SignTextCullingHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class CameraCullingStressFuzzTest {

    @BeforeEach
    void setUp() {
        CameraCullingConfig.setEnabled(true);
        CameraCullingConfig.setLevel(CullingLevel.MEDIUM);
        CameraCullingConfig.setMaxEntitiesPerCluster(8);
        CameraCullingConfig.setEntityDetailLod(true);
        CameraCullingConfig.setEntityLodDistance(32.0);
        CameraCullingConfig.setBossImmunity(true);
        CameraCullingConfig.setBossHealthThreshold(150.0);
        CameraCullingConfig.setMiniBossHealthThreshold(50.0);
        CameraCullingConfig.setCullParticles(true);
        CameraCullingConfig.setCullAnimations(true);
        CameraCullingConfig.setCullSignText(true);
    }

    @AfterEach
    void tearDown() {
        CameraCullingConfig.setEnabled(true);
        CameraCullingConfig.setLevel(CullingLevel.MEDIUM);
        CameraCullingConfig.setMaxEntitiesPerCluster(8);
        CameraCullingConfig.setEntityDetailLod(true);
        CameraCullingConfig.setEntityLodDistance(32.0);
        CameraCullingConfig.setBossImmunity(true);
        CameraCullingConfig.setBossHealthThreshold(150.0);
        CameraCullingConfig.setMiniBossHealthThreshold(50.0);
        CameraCullingConfig.setCullParticles(true);
        CameraCullingConfig.setCullAnimations(true);
        CameraCullingConfig.setCullSignText(true);
    }

    @Test
    @DisplayName("Pillar 1: 25,000 Iterations Chaos Fuzzing on EntityLodHelper.calculateShadowScale")
    void testMathematicalBoundaryChaosFuzzing() {
        Random rng = new Random(0xDA51C);
        double[] edgeCases = {
                Double.NaN,
                Double.POSITIVE_INFINITY,
                Double.NEGATIVE_INFINITY,
                Double.MIN_VALUE,
                -Double.MIN_VALUE,
                Double.MAX_VALUE,
                -Double.MAX_VALUE,
                0.0,
                -0.0,
                -1.0,
                -1000.0,
                1.0,
                16.0,
                32.0,
                48.0,
                100.0,
                1024.0,
                1600.0,
                2500.0,
                1e-12,
                1e12,
                1e154,
                30000000.0
        };

        for (int i = 0; i < 25_000; i++) {
            double distSq;
            double startDist;
            double fadeRange;

            int mode = rng.nextInt(5);
            if (mode == 0) {
                // Pure edge-case injection
                distSq = edgeCases[rng.nextInt(edgeCases.length)];
                startDist = edgeCases[rng.nextInt(edgeCases.length)];
                fadeRange = edgeCases[rng.nextInt(edgeCases.length)];
            } else if (mode == 1) {
                // Wide scale pseudo-random values
                distSq = (rng.nextDouble() - 0.5) * 200_000.0;
                startDist = (rng.nextDouble() - 0.5) * 500.0;
                fadeRange = (rng.nextDouble() - 0.5) * 100.0;
            } else if (mode == 2) {
                // Extreme scale double values
                distSq = rng.nextDouble() * Double.MAX_VALUE;
                startDist = rng.nextDouble() * 1000.0;
                fadeRange = rng.nextDouble() * 100.0;
            } else if (mode == 3) {
                // Valid operational domain values
                distSq = rng.nextDouble() * 10000.0;
                startDist = 1.0 + rng.nextDouble() * 128.0;
                fadeRange = 1.0 + rng.nextDouble() * 64.0;
            } else {
                // Boundary around transition point
                startDist = 32.0;
                fadeRange = 16.0;
                double dist = 20.0 + rng.nextDouble() * 40.0;
                distSq = dist * dist;
            }

            float scale = EntityLodHelper.calculateShadowScale(distSq, startDist, fadeRange);

            assertFalse(Float.isNaN(scale),
                    "Shadow scale produced NaN for distSq=" + distSq + ", startDist=" + startDist + ", fadeRange=" + fadeRange);
            assertFalse(Float.isInfinite(scale),
                    "Shadow scale produced Infinite for distSq=" + distSq + ", startDist=" + startDist + ", fadeRange=" + fadeRange);
            assertTrue(scale >= 0.0f && scale <= 1.0f,
                    "Shadow scale out of [0.0, 1.0] (" + scale + ") for distSq=" + distSq + ", startDist=" + startDist + ", fadeRange=" + fadeRange);
        }
    }

    @Test
    @DisplayName("Pillar 1: Sign Text Normal Dot Product Null-Safety & Chaos Fuzzing")
    void testSignTextNormalDotProductChaos() {
        Random rng = new Random(0xDA51C);

        // Core null safety invariants
        assertFalse(SignTextCullingHelper.shouldCullSignTextSide(null, null, true));
        assertFalse(SignTextCullingHelper.shouldCullSignTextSide(null, null, false));
        assertFalse(SignTextCullingHelper.shouldCullFace(null, null, null, true));
        assertFalse(SignTextCullingHelper.shouldCullFace(null, null, null, false));
        assertTrue(SignTextCullingHelper.isTextEmpty(null));

        // Permutation fuzzing
        for (int i = 0; i < 1_000; i++) {
            boolean isFront = rng.nextBoolean();
            boolean configEnabled = rng.nextBoolean();
            boolean cullSignEnabled = rng.nextBoolean();

            CameraCullingConfig.setEnabled(configEnabled);
            CameraCullingConfig.setCullSignText(cullSignEnabled);

            assertFalse(SignTextCullingHelper.shouldCullSignTextSide(null, null, isFront));
            assertFalse(SignTextCullingHelper.shouldCullFace(null, null, null, isFront));
            assertTrue(SignTextCullingHelper.isTextEmpty(null));
        }
    }

    @Test
    @DisplayName("Pillar 1: 5,000 Iterations Config Clamping Chaos Fuzzing")
    void testConfigClampingChaosFuzzing() {
        Random rng = new Random(0xDA51C);

        int[] intEdgeCases = {
                Integer.MIN_VALUE, -100000, -100, -1, 0, 1, 2, 8, 16, 64, 127, 128, 129, 1000, 100000, Integer.MAX_VALUE
        };

        for (int i = 0; i < 5_000; i++) {
            // 1. Max Entities Per Cluster Clamping [1, 128]
            int inputInt = (i < intEdgeCases.length) ? intEdgeCases[i] : rng.nextInt();
            CameraCullingConfig.setMaxEntitiesPerCluster(inputInt);
            int clusterCap = CameraCullingConfig.getMaxEntitiesPerCluster();
            assertTrue(clusterCap >= 1 && clusterCap <= 128,
                    "maxEntitiesPerCluster out of bounds [1, 128]: " + clusterCap + " for input: " + inputInt);

            // 2. Boss Health Threshold Clamping [1.0, 10000.0]
            double bossInput = (rng.nextDouble() - 0.5) * 200_000.0;
            CameraCullingConfig.setBossHealthThreshold(bossInput);
            double bossThresh = CameraCullingConfig.getBossHealthThreshold();
            assertTrue(bossThresh >= 1.0 && bossThresh <= 10000.0,
                    "bossHealthThreshold out of bounds [1.0, 10000.0]: " + bossThresh + " for input: " + bossInput);

            // 3. MiniBoss Health Threshold Clamping [1.0, 10000.0]
            double miniBossInput = (rng.nextDouble() - 0.5) * 200_000.0;
            CameraCullingConfig.setMiniBossHealthThreshold(miniBossInput);
            double miniBossThresh = CameraCullingConfig.getMiniBossHealthThreshold();
            assertTrue(miniBossThresh >= 1.0 && miniBossThresh <= 10000.0,
                    "miniBossHealthThreshold out of bounds [1.0, 10000.0]: " + miniBossThresh + " for input: " + miniBossInput);

            // 4. Entity LOD Distance Clamping [1.0, +Inf]
            double lodDistInput = (rng.nextDouble() - 0.5) * 200_000.0;
            CameraCullingConfig.setEntityLodDistance(lodDistInput);
            double lodDist = CameraCullingConfig.getEntityLodDistance();
            assertTrue(lodDist >= 1.0,
                    "entityLodDistance below 1.0: " + lodDist + " for input: " + lodDistInput);
        }
    }
}
