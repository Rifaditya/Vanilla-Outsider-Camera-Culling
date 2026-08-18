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
    private static final String CONFIG_FILE_NAME = "camera-culling.json";

    private static boolean enabled = true;
    private static CullingLevel level = CullingLevel.MEDIUM;
    private static boolean debugMode = false;

    private CameraCullingConfig() {}

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

    public static boolean isDebugMode() {
        return debugMode;
    }

    public static void setDebugMode(boolean value) {
        debugMode = value;
        save();
    }

    public static void load() {
        File configFile = getConfigFile();
        if (!configFile.exists()) {
            save();
            return;
        }

        try (FileReader reader = new FileReader(configFile)) {
            JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
            if (json.has("enabled")) {
                enabled = json.get("enabled").getAsBoolean();
            }
            if (json.has("cullingLevel")) {
                level = CullingLevel.fromString(json.get("cullingLevel").getAsString());
            }
            if (json.has("debugMode")) {
                debugMode = json.get("debugMode").getAsBoolean();
            }
            LOGGER.info("[Camera Culling] Loaded config: enabled={}, level={}, debugMode={}", enabled, level, debugMode);
        } catch (Exception e) {
            LOGGER.error("[Camera Culling] Failed to load config from {}: {}", configFile.getAbsolutePath(), e.getMessage());
            save();
        }
    }

    public static void save() {
        File configFile = getConfigFile();
        try {
            File parent = configFile.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            JsonObject json = new JsonObject();
            json.addProperty("enabled", enabled);
            json.addProperty("cullingLevel", level.name());
            json.addProperty("debugMode", debugMode);

            try (FileWriter writer = new FileWriter(configFile)) {
                GSON.toJson(json, writer);
            }
        } catch (IOException e) {
            LOGGER.error("[Camera Culling] Failed to save config: {}", e.getMessage());
        }
    }

    private static File getConfigFile() {
        try {
            if (FabricLoader.getInstance() != null && FabricLoader.getInstance().getConfigDir() != null) {
                return FabricLoader.getInstance().getConfigDir().resolve(CONFIG_FILE_NAME).toFile();
            }
        } catch (Throwable ignored) {
            // FabricLoader not initialized in unit tests
        }
        return new File("build/config/" + CONFIG_FILE_NAME);
    }
}
