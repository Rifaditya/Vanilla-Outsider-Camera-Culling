// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.vanillaoutsider.culling.test;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.CommandNode;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.vanillaoutsider.culling.command.CameraCullingCommand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

public class CameraCullingCommandTest {

    @Test
    @DisplayName("Pillar 3: Headless Brigadier Command Hierarchy AST Verification")
    void testClientCommandDispatcherHierarchy() {
        CommandDispatcher<FabricClientCommandSource> dispatcher = new CommandDispatcher<>();
        CameraCullingCommand.registerCommands(dispatcher);

        CommandNode<FabricClientCommandSource> rootNode = dispatcher.getRoot().getChild("cameraculling");
        assertNotNull(rootNode, "Root command '/cameraculling' must be registered");

        // Verify primary command branches
        assertNotNull(rootNode.getChild("status"), "Command '/cameraculling status' must exist");
        assertNotNull(rootNode.getChild("toggle"), "Command '/cameraculling toggle' must exist");
        assertNotNull(rootNode.getChild("debug"), "Command '/cameraculling debug' must exist");
        assertNotNull(rootNode.getChild("particles"), "Command '/cameraculling particles' must exist");
        assertNotNull(rootNode.getChild("animations"), "Command '/cameraculling animations' must exist");
        assertNotNull(rootNode.getChild("blacklist"), "Command '/cameraculling blacklist' must exist");
        assertNotNull(rootNode.getChild("serverblacklist"), "Command '/cameraculling serverblacklist' must exist");
        assertNotNull(rootNode.getChild("cluster"), "Command '/cameraculling cluster' must exist");
        assertNotNull(rootNode.getChild("crowdculling"), "Command '/cameraculling crowdculling' must exist");
        assertNotNull(rootNode.getChild("entitylod"), "Command '/cameraculling entitylod' must exist");
        assertNotNull(rootNode.getChild("texturlod"), "Command '/cameraculling texturlod' must exist");
        assertNotNull(rootNode.getChild("bossimmunity"), "Command '/cameraculling bossimmunity' must exist");
        assertNotNull(rootNode.getChild("bosshealth"), "Command '/cameraculling bosshealth' must exist");
        assertNotNull(rootNode.getChild("minibosshealth"), "Command '/cameraculling minibosshealth' must exist");
        assertNotNull(rootNode.getChild("set"), "Command '/cameraculling set' must exist");
        assertNotNull(rootNode.getChild("reload"), "Command '/cameraculling reload' must exist");

        // Verify 'set' sub-arguments
        CommandNode<FabricClientCommandSource> setNode = rootNode.getChild("set");
        assertNotNull(setNode.getChild("low"), "Command '/cameraculling set low' must exist");
        assertNotNull(setNode.getChild("medium"), "Command '/cameraculling set medium' must exist");
        assertNotNull(setNode.getChild("high"), "Command '/cameraculling set high' must exist");
        assertNotNull(setNode.getChild("super"), "Command '/cameraculling set super' must exist");

        // Verify 'blacklist' sub-arguments
        CommandNode<FabricClientCommandSource> blacklistNode = rootNode.getChild("blacklist");
        assertNotNull(blacklistNode.getChild("add"), "Command '/cameraculling blacklist add' must exist");
        assertNotNull(blacklistNode.getChild("remove"), "Command '/cameraculling blacklist remove' must exist");
        assertNotNull(blacklistNode.getChild("list"), "Command '/cameraculling blacklist list' must exist");
        assertNotNull(blacklistNode.getChild("clear"), "Command '/cameraculling blacklist clear' must exist");

        // Verify 'serverblacklist' sub-arguments
        CommandNode<FabricClientCommandSource> serverBlacklistNode = rootNode.getChild("serverblacklist");
        assertNotNull(serverBlacklistNode.getChild("add"), "Command '/cameraculling serverblacklist add' must exist");
        assertNotNull(serverBlacklistNode.getChild("remove"), "Command '/cameraculling serverblacklist remove' must exist");
        assertNotNull(serverBlacklistNode.getChild("list"), "Command '/cameraculling serverblacklist list' must exist");
        assertNotNull(serverBlacklistNode.getChild("clear"), "Command '/cameraculling serverblacklist clear' must exist");
    }

    @Test
    @DisplayName("Pillar 3: Translation Key Coverage and Integrity (en_us.json)")
    void testTranslationKeyCoverage() {
        InputStream stream = getClass().getClassLoader().getResourceAsStream("assets/camera-culling/lang/en_us.json");
        assertNotNull(stream, "assets/camera-culling/lang/en_us.json must be readable from classpath");

        try (InputStreamReader reader = new InputStreamReader(stream, StandardCharsets.UTF_8)) {
            JsonObject langJson = JsonParser.parseReader(reader).getAsJsonObject();
            assertNotNull(langJson);

            // Assert critical UI / Command / Config keys
            assertTrue(langJson.has("cameraculling.title") || langJson.has("config.cameraculling.title"),
                "Language file must declare cameraculling.title or config.cameraculling.title");
            assertTrue(langJson.has("config.cameraculling.level"),
                "Language file must declare config.cameraculling.level");
            assertTrue(langJson.has("config.cameraculling.level.low"),
                "Language file must declare config.cameraculling.level.low");
            assertTrue(langJson.has("config.cameraculling.level.medium"),
                "Language file must declare config.cameraculling.level.medium");
            assertTrue(langJson.has("config.cameraculling.level.high"),
                "Language file must declare config.cameraculling.level.high");
            assertTrue(langJson.has("config.cameraculling.level.super"),
                "Language file must declare config.cameraculling.level.super");
            assertTrue(langJson.has("config.cameraculling.enabled"),
                "Language file must declare config.cameraculling.enabled");
            assertTrue(langJson.has("config.cameraculling.bossImmunity"),
                "Language file must declare config.cameraculling.bossImmunity");
            assertTrue(langJson.has("config.cameraculling.cullParticles"),
                "Language file must declare config.cameraculling.cullParticles");
            assertTrue(langJson.has("config.cameraculling.cullAnimations"),
                "Language file must declare config.cameraculling.cullAnimations");
            assertTrue(langJson.has("config.cameraculling.cullSignText"),
                "Language file must declare config.cameraculling.cullSignText");
        } catch (Exception e) {
            fail("Failed reading en_us.json: " + e.getMessage());
        }
    }
}
