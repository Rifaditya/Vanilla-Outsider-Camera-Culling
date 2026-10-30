// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.vanillaoutsider.culling.test;

import net.vanillaoutsider.culling.config.CameraCullingConfig;
import net.vanillaoutsider.culling.config.CullingLevel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

public class CameraCullingConcurrencyLoadTest {

    @BeforeEach
    void setUp() {
        CameraCullingConfig.clearClientBlacklist();
        CameraCullingConfig.clearServerBlacklist();
        CameraCullingConfig.setEnabled(true);
        CameraCullingConfig.setLevel(CullingLevel.MEDIUM);
    }

    @AfterEach
    void tearDown() {
        CameraCullingConfig.clearClientBlacklist();
        CameraCullingConfig.clearServerBlacklist();
        CameraCullingConfig.setEnabled(true);
        CameraCullingConfig.setLevel(CullingLevel.SUPER);
    }

    @Test
    @DisplayName("Pillar 2: 50 Concurrent Worker Threads 500 Iterations Config Mutation & Read Stress")
    void testConcurrentConfigMutationsAndReads() throws InterruptedException {
        int threadCount = 50;
        int iterations = 500;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);
        AtomicInteger errorCount = new AtomicInteger(0);

        for (int t = 0; t < threadCount; t++) {
            final int threadId = t;
            executor.submit(() -> {
                try {
                    for (int i = 0; i < iterations; i++) {
                        String entityId = "minecraft:entity_t" + threadId + "_" + (i % 5);

                        // Concurrent mutations to shared blacklist
                        CameraCullingConfig.addClientBlacklist(entityId);
                        boolean blacklisted = CameraCullingConfig.isEntityBlacklisted(entityId);
                        assertTrue(blacklisted, "Entity should be blacklisted after adding");

                        // Concurrent reads and snapshot iteration across all threads
                        Set<String> clientList = CameraCullingConfig.getClientBlacklist();
                        assertNotNull(clientList);
                        for (String id : clientList) {
                            assertNotNull(id);
                        }

                        if (i % 10 == 0) {
                            CameraCullingConfig.removeClientBlacklist(entityId);
                        }

                        // Concurrent configuration mutations exercising FILE_LOCK disk sync
                        if (i % 20 == 0) {
                            CameraCullingConfig.setMaxEntitiesPerCluster(1 + (i % 64));
                            CameraCullingConfig.setLevel(CullingLevel.values()[i % CullingLevel.values().length]);
                            CameraCullingConfig.setEnabled(i % 2 == 0);
                        }
                    }
                } catch (Throwable e) {
                    errorCount.incrementAndGet();
                    e.printStackTrace();
                } finally {
                    latch.countDown();
                }
            });
        }

        boolean completedInTime = latch.await(15, TimeUnit.SECONDS);
        executor.shutdown();
        boolean terminated = executor.awaitTermination(5, TimeUnit.SECONDS);

        assertTrue(completedInTime, "Concurrent load test timed out after 15 seconds (potential deadlock!)");
        assertTrue(terminated, "Executor did not terminate cleanly");
        assertEquals(0, errorCount.get(), "Encountered exceptions during concurrent load test");
    }
}
