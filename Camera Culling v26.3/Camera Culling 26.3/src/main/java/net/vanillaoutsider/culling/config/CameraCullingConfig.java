// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.vanillaoutsider.culling.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public final class CameraCullingConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(CameraCullingConfig.class);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private static boolean enabled = true;
    private static CullingLevel level = CullingLevel.MEDIUM;
    private static Boolean cullEntitiesBehindEntities = null; // null = use level default
    private static int maxEntitiesPerCluster = 8;
    private static boolean distanceTextureLod = true;
    private static double distanceTextureLodStart = 16.0;
    private static double distanceTextureLodFar = 32.0;
    private static boolean bossImmunity = true;
    private static double bossHealthThreshold = 150.0;
    private static double miniBossHealthThreshold = 50.0;
    private static boolean debugMode = false;

    private CameraCullingConfig() {}

    private static File getConfigFile() {
        try {
            if (FabricLoader.getInstance() != null && FabricLoader.getInstance().getConfigDir() != null) {
                return FabricLoader.getInstance().getConfigDir().resolve("camera-culling.json").toFile();
            }
        } catch (Throwable ignored) {
        }
        return new File("build/config/camera-culling.json");
    }

    public static void load() {
        File file = getConfigFile();
        if (!file.exists()) {
            save();
            return;
        }

        try (FileReader reader = new FileReader(file)) {
            JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
            if (json.has("enabled")) {
                enabled = json.get("enabled").getAsBoolean();
            }
            if (json.has("level")) {
                level = CullingLevel.fromString(json.get("level").getAsString());
            }
            if (json.has("cullEntitiesBehindEntities")) {
                if (json.get("cullEntitiesBehindEntities").isJsonNull()) {
                    cullEntitiesBehindEntities = null;
                } else {
                    cullEntitiesBehindEntities = json.get("cullEntitiesBehindEntities").getAsBoolean();
                }
            }
            if (json.has("maxEntitiesPerCluster")) {
                maxEntitiesPerCluster = Math.max(1, Math.min(128, json.get("maxEntitiesPerCluster").getAsInt()));
            }
            if (json.has("distanceTextureLod")) {
                distanceTextureLod = json.get("distanceTextureLod").getAsBoolean();
            }
            if (json.has("distanceTextureLodStart")) {
                distanceTextureLodStart = Math.max(1.0, json.get("distanceTextureLodStart").getAsDouble());
            }
            if (json.has("distanceTextureLodFar")) {
                distanceTextureLodFar = Math.max(distanceTextureLodStart + 1.0, json.get("distanceTextureLodFar").getAsDouble());
            }
            if (json.has("bossImmunity")) {
                bossImmunity = json.get("bossImmunity").getAsBoolean();
            }
            if (json.has("bossHealthThreshold")) {
                bossHealthThreshold = Math.max(1.0, Math.min(10000.0, json.get("bossHealthThreshold").getAsDouble()));
            }
            if (json.has("miniBossHealthThreshold")) {
                miniBossHealthThreshold = Math.max(1.0, Math.min(10000.0, json.get("miniBossHealthThreshold").getAsDouble()));
            }
            if (json.has("debugMode")) {
                debugMode = json.get("debugMode").getAsBoolean();
            }
            LOGGER.info("[Camera Culling] Configuration loaded. Active Level: {}, Entity Culling: {}, Texture LOD: {}, Boss Immunity: {} (Boss HP: {}, Mini-Boss HP: {})",
                    level.getDisplayName(), isCullEntitiesBehindEntities(), distanceTextureLod, bossImmunity, bossHealthThreshold, miniBossHealthThreshold);
        } catch (Exception e) {
            LOGGER.error("[Camera Culling] Failed to load config, restoring defaults: {}", e.getMessage());
            save();
        }
    }

    public static void save() {
        File file = getConfigFile();
        try {
            if (file.getParentFile() != null && !file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            JsonObject json = new JsonObject();
            json.addProperty("enabled", enabled);
            json.addProperty("level", level.name());
            if (cullEntitiesBehindEntities == null) {
                json.add("cullEntitiesBehindEntities", null);
            } else {
                json.addProperty("cullEntitiesBehindEntities", cullEntitiesBehindEntities);
            }
            json.addProperty("maxEntitiesPerCluster", maxEntitiesPerCluster);
            json.addProperty("distanceTextureLod", distanceTextureLod);
            json.addProperty("distanceTextureLodStart", distanceTextureLodStart);
            json.addProperty("distanceTextureLodFar", distanceTextureLodFar);
            json.addProperty("bossImmunity", bossImmunity);
            json.addProperty("bossHealthThreshold", bossHealthThreshold);
            json.addProperty("miniBossHealthThreshold", miniBossHealthThreshold);
            json.addProperty("debugMode", debugMode);

            try (FileWriter writer = new FileWriter(file)) {
                GSON.toJson(json, writer);
            }
        } catch (IOException e) {
            LOGGER.error("[Camera Culling] Failed to save config: {}", e.getMessage());
        }
    }

    public static boolean isEnabled() {
        return enabled;
    }

    public static void setEnabled(boolean value) {
        enabled = value;
        save();
    }

    public static CullingLevel getLevel() {
        return level;
    }

    public static void setLevel(CullingLevel newLevel) {
        if (newLevel != null) {
            level = newLevel;
            save();
        }
    }

    public static boolean isCullEntitiesBehindEntities() {
        if (cullEntitiesBehindEntities != null) {
            return cullEntitiesBehindEntities;
        }
        return level.isDefaultCullEntitiesBehindEntities();
    }

    public static void setCullEntitiesBehindEntities(Boolean value) {
        cullEntitiesBehindEntities = value;
        save();
    }

    public static int getMaxEntitiesPerCluster() {
        return maxEntitiesPerCluster;
    }

    public static void setMaxEntitiesPerCluster(int limit) {
        maxEntitiesPerCluster = Math.max(1, Math.min(128, limit));
        save();
    }

    public static boolean isDistanceTextureLod() {
        return distanceTextureLod;
    }

    public static void setDistanceTextureLod(boolean enabled) {
        distanceTextureLod = enabled;
        save();
    }

    public static double getDistanceTextureLodStart() {
        return distanceTextureLodStart;
    }

    public static double getDistanceTextureLodFar() {
        return distanceTextureLodFar;
    }

    public static void setDistanceTextureLodRange(double start, double far) {
        distanceTextureLodStart = Math.max(1.0, Math.min(start, far - 1.0));
        distanceTextureLodFar = Math.max(distanceTextureLodStart + 1.0, far);
        save();
    }

    public static boolean isBossImmunity() {
        return bossImmunity;
    }

    public static void setBossImmunity(boolean enabled) {
        bossImmunity = enabled;
        save();
    }

    public static double getBossHealthThreshold() {
        return bossHealthThreshold;
    }

    public static void setBossHealthThreshold(double threshold) {
        bossHealthThreshold = Math.max(1.0, Math.min(10000.0, threshold));
        save();
    }

    public static double getMiniBossHealthThreshold() {
        return miniBossHealthThreshold;
    }

    public static void setMiniBossHealthThreshold(double threshold) {
        miniBossHealthThreshold = Math.max(1.0, Math.min(10000.0, threshold));
        save();
    }

    public static boolean isDebugMode() {
        return debugMode;
    }

    public static void setDebugMode(boolean value) {
        debugMode = value;
        save();
    }
}
