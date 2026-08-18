// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.vanillaoutsider.culling;

import net.vanillaoutsider.culling.config.CameraCullingConfig;
import net.vanillaoutsider.culling.config.CullingLevel;
import net.vanillaoutsider.culling.util.CullingRaycastHelper;
import net.vanillaoutsider.culling.util.TextureLodHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CameraCullingTest {

    @BeforeEach
    public void setup() {
        CameraCullingConfig.setEnabled(true);
        CameraCullingConfig.setLevel(CullingLevel.MEDIUM);
        CameraCullingConfig.setCullEntitiesBehindEntities(null);
        CameraCullingConfig.setMaxEntitiesPerCluster(8);
        CameraCullingConfig.setDistanceTextureLod(true);
        CameraCullingConfig.setDistanceTextureLodRange(16.0, 32.0);
        CameraCullingConfig.setDebugMode(false);
    }

    @Test
    public void testToggleCulling() {
        assertTrue(CameraCullingConfig.isEnabled(), "Culling should be enabled by default");
        CameraCullingConfig.setEnabled(false);
        assertFalse(CameraCullingConfig.isEnabled(), "Culling should be disabled after toggle");
    }

    @Test
    public void testCullingLevels() {
        assertEquals(CullingLevel.MEDIUM, CameraCullingConfig.getLevel());

        CameraCullingConfig.setLevel(CullingLevel.LOW);
        assertEquals(CullingLevel.LOW, CameraCullingConfig.getLevel());
        assertEquals(16.0, CullingLevel.LOW.getMinDistanceSq());
        assertEquals(7, CullingLevel.LOW.getSamplePoints());
        assertFalse(CullingLevel.LOW.shouldCullAllBlockEntities());
        assertFalse(CullingLevel.LOW.isDefaultCullEntitiesBehindEntities());

        CameraCullingConfig.setLevel(CullingLevel.HIGH);
        assertEquals(CullingLevel.HIGH, CameraCullingConfig.getLevel());
        assertEquals(1.0, CullingLevel.HIGH.getMinDistanceSq());
        assertEquals(2, CullingLevel.HIGH.getSamplePoints());
        assertTrue(CullingLevel.HIGH.shouldCullDecorativeEntities());
        assertTrue(CullingLevel.HIGH.isDefaultCullEntitiesBehindEntities());

        CameraCullingConfig.setLevel(CullingLevel.SUPER);
        assertEquals(CullingLevel.SUPER, CameraCullingConfig.getLevel());
        assertEquals(0.25, CullingLevel.SUPER.getMinDistanceSq());
        assertEquals(1, CullingLevel.SUPER.getSamplePoints());
        assertTrue(CullingLevel.SUPER.shouldCullAllBlockEntities());
        assertTrue(CullingLevel.SUPER.isDefaultCullEntitiesBehindEntities());
    }

    @Test
    public void testEntityBehindEntityConfigAndOverrides() {
        CameraCullingConfig.setLevel(CullingLevel.MEDIUM);
        assertFalse(CameraCullingConfig.isCullEntitiesBehindEntities(), "MEDIUM should default entity culling to false");

        CameraCullingConfig.setLevel(CullingLevel.HIGH);
        assertTrue(CameraCullingConfig.isCullEntitiesBehindEntities(), "HIGH should default entity culling to true");

        CameraCullingConfig.setCullEntitiesBehindEntities(false);
        assertFalse(CameraCullingConfig.isCullEntitiesBehindEntities(), "Explicit false should override level default");

        CameraCullingConfig.setLevel(CullingLevel.LOW);
        CameraCullingConfig.setCullEntitiesBehindEntities(true);
        assertTrue(CameraCullingConfig.isCullEntitiesBehindEntities(), "Explicit true should override LOW default");

        CameraCullingConfig.setCullEntitiesBehindEntities(null);
        assertFalse(CameraCullingConfig.isCullEntitiesBehindEntities(), "Reset to auto should match LOW default (false)");
    }

    @Test
    public void testClusterLimitClamping() {
        assertEquals(8, CameraCullingConfig.getMaxEntitiesPerCluster());

        CameraCullingConfig.setMaxEntitiesPerCluster(16);
        assertEquals(16, CameraCullingConfig.getMaxEntitiesPerCluster());

        CameraCullingConfig.setMaxEntitiesPerCluster(0);
        assertEquals(1, CameraCullingConfig.getMaxEntitiesPerCluster(), "Should clamp lower bound to 1");

        CameraCullingConfig.setMaxEntitiesPerCluster(200);
        assertEquals(128, CameraCullingConfig.getMaxEntitiesPerCluster(), "Should clamp upper bound to 128");
    }

    @Test
    public void testDistanceTextureLodConfig() {
        assertTrue(CameraCullingConfig.isDistanceTextureLod());
        CameraCullingConfig.setDistanceTextureLod(false);
        assertFalse(CameraCullingConfig.isDistanceTextureLod());

        CameraCullingConfig.setDistanceTextureLodRange(24.0, 48.0);
        assertEquals(24.0, CameraCullingConfig.getDistanceTextureLodStart());
        assertEquals(48.0, CameraCullingConfig.getDistanceTextureLodFar());
    }

    @Test
    public void testTextureLodBiasMath() {
        double start = 16.0;
        double far = 32.0;

        // Distance = 10m (distSq = 100) -> Near -> 0.0f
        assertEquals(0.0f, TextureLodHelper.calculateLodBias(100.0, start, far));

        // Distance = 20m (distSq = 400) -> Medium -> 1.0f
        assertEquals(1.0f, TextureLodHelper.calculateLodBias(400.0, start, far));

        // Distance = 40m (distSq = 1600) -> Far -> 2.5f
        assertEquals(2.5f, TextureLodHelper.calculateLodBias(1600.0, start, far));

        // Boundary: exactly on start (16m -> distSq = 256) -> Medium -> 1.0f
        assertEquals(1.0f, TextureLodHelper.calculateLodBias(256.0, start, far));

        // Boundary: exactly on far (32m -> distSq = 1024) -> Far -> 2.5f
        assertEquals(2.5f, TextureLodHelper.calculateLodBias(1024.0, start, far));
    }

    @Test
    public void testCullingLevelFromString() {
        assertEquals(CullingLevel.LOW, CullingLevel.fromString("low"));
        assertEquals(CullingLevel.MEDIUM, CullingLevel.fromString("MEDIUM"));
        assertEquals(CullingLevel.HIGH, CullingLevel.fromString("High"));
        assertEquals(CullingLevel.SUPER, CullingLevel.fromString("super"));
        assertEquals(CullingLevel.MEDIUM, CullingLevel.fromString("invalid_name"));
        assertEquals(CullingLevel.MEDIUM, CullingLevel.fromString(null));
    }

    @Test
    public void testCounterIncrements() {
        long initialCulled = CameraCullingClient.getCulledEntitiesCount();
        CameraCullingClient.incrementCulledEntities();
        assertEquals(initialCulled + 1, CameraCullingClient.getCulledEntitiesCount());

        long initialRendered = CameraCullingClient.getRenderedEntitiesCount();
        CameraCullingClient.incrementRenderedEntities();
        assertEquals(initialRendered + 1, CameraCullingClient.getRenderedEntitiesCount());

        long initialCulledBlocks = CameraCullingClient.getCulledBlockEntitiesCount();
        CameraCullingClient.incrementCulledBlockEntities();
        assertEquals(initialCulledBlocks + 1, CameraCullingClient.getCulledBlockEntitiesCount());

        long initialRenderedBlocks = CameraCullingClient.getRenderedBlockEntitiesCount();
        CameraCullingClient.incrementRenderedBlockEntities();
        assertEquals(initialRenderedBlocks + 1, CameraCullingClient.getRenderedBlockEntitiesCount());
    }

    @Test
    public void testNullSafety() {
        assertDoesNotThrow(() -> {
            boolean occluded = CullingRaycastHelper.isEntityOccluded(null, 0, 0, 0);
            assertFalse(occluded, "Null entity should never be occluded");
        });

        assertDoesNotThrow(() -> {
            boolean blockOccluded = CullingRaycastHelper.isBlockEntityOccluded(null, null);
            assertFalse(blockOccluded, "Null block entity should never be occluded");
        });

        assertDoesNotThrow(() -> {
            boolean entityOccluded = CullingRaycastHelper.isEntityOccludedByCloserEntities(null, null, null, null, 0);
            assertFalse(entityOccluded, "Null entity/level should return false safely");
        });

        assertDoesNotThrow(() -> {
            float bias = TextureLodHelper.getLodBias(null);
            assertEquals(0.0f, bias, "Null render state should return 0.0f bias");
        });

        assertDoesNotThrow(() -> {
            TextureLodHelper.applyLodBias(1.0f);
            TextureLodHelper.resetLodBias();
        });
    }

    @Test
    public void testDisabledCullingFastPass() {
        CameraCullingConfig.setEnabled(false);
        assertFalse(CullingRaycastHelper.isEntityOccluded(null, 100, 100, 100));
        assertFalse(CullingRaycastHelper.isBlockEntityOccluded(null, null));
    }
}
