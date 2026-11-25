// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.vanillaoutsider.culling.test;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.vanillaoutsider.culling.config.ModMenuIntegration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.DataInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class CameraCullingYaclContractTest {

    private static Path getProjectRoot() {
        Path current = Paths.get("").toAbsolutePath();
        if (Files.exists(current.resolve("src/main/resources/fabric.mod.json"))) {
            return current;
        }
        Path parent = current;
        while (parent != null) {
            if (Files.exists(parent.resolve("src/main/resources/fabric.mod.json"))) {
                return parent;
            }
            parent = parent.getParent();
        }
        throw new IllegalStateException("Could not resolve project root containing src/main/resources/fabric.mod.json from " + current);
    }

    private static Set<String> readConstantPoolUtf8Strings(Path classFile) throws IOException {
        Set<String> utf8Strings = new HashSet<>();
        try (DataInputStream in = new DataInputStream(Files.newInputStream(classFile))) {
            int magic = in.readInt();
            assertEquals(0xCAFEBABE, magic, "Invalid Java class magic header in " + classFile);
            in.readUnsignedShort(); // minor version
            in.readUnsignedShort(); // major version
            int cpCount = in.readUnsignedShort();
            for (int i = 1; i < cpCount; i++) {
                int tag = in.readUnsignedByte();
                switch (tag) {
                    case 1 -> utf8Strings.add(in.readUTF());
                    case 3, 4, 9, 10, 11, 12, 17, 18 -> in.readInt();
                    case 5, 6 -> {
                        in.readLong();
                        i++;
                    }
                    case 7, 8, 16, 19, 20 -> in.readUnsignedShort();
                    case 15 -> {
                        in.readUnsignedByte();
                        in.readUnsignedShort();
                    }
                    default -> fail("Unsupported constant pool tag " + tag + " at index " + i);
                }
            }
        }
        return utf8Strings;
    }

    @Test
    @DisplayName("Pillar 3: Ko-fi Lang Keys & Missing-YACL ModMenu Fallback Contract")
    void testKofiLangKeysAndMissingYaclFallback() throws IOException {
        Path projectRoot = getProjectRoot();
        Path langPath = projectRoot.resolve("src/main/resources/assets/camera-culling/lang/en_us.json");
        assertTrue(Files.exists(langPath), "en_us.json must exist at " + langPath);

        JsonObject langJson = JsonParser.parseString(Files.readString(langPath)).getAsJsonObject();
        assertTrue(langJson.has("dasiklibrary.support.kofi.button"),
                "Missing dasiklibrary.support.kofi.button in en_us.json");
        assertFalse(langJson.get("dasiklibrary.support.kofi.button").getAsString().isBlank(),
                "dasiklibrary.support.kofi.button must not be blank");

        assertTrue(langJson.has("dasiklibrary.support.kofi.tooltip"),
                "Missing dasiklibrary.support.kofi.tooltip in en_us.json");
        assertFalse(langJson.get("dasiklibrary.support.kofi.tooltip").getAsString().isBlank(),
                "dasiklibrary.support.kofi.tooltip must not be blank");

        ModMenuIntegration integration = new ModMenuIntegration();
        assertNull(integration.getModConfigScreenFactory().create(null),
                "ModMenuIntegration must safely return null when YACL is absent");
    }

    @Test
    @DisplayName("Pillar 4/5: YaclScreenHelper ConfirmLinkScreen URI Overload Bytecode Contract")
    void testConfirmLinkScreenUriBytecodeContract() throws IOException {
        Path projectRoot = getProjectRoot();
        Path classFile = projectRoot.resolve(
                "build/classes/java/main/net/vanillaoutsider/culling/config/YaclScreenHelper.class");
        assertTrue(Files.exists(classFile), "Compiled YaclScreenHelper.class must exist at " + classFile);

        Set<String> cpUtf8 = readConstantPoolUtf8Strings(classFile);
        assertTrue(cpUtf8.contains("net/minecraft/client/gui/screens/ConfirmLinkScreen"),
                "YaclScreenHelper.class must reference ConfirmLinkScreen");
        assertTrue(cpUtf8.contains("(Lnet/minecraft/client/gui/screens/Screen;Ljava/net/URI;)V"),
                "YaclScreenHelper.class must invoke ConfirmLinkScreen.confirmLinkNow(Screen, URI)");
        assertFalse(cpUtf8.contains("(Lnet/minecraft/client/gui/screens/Screen;Ljava/lang/String;)V"),
                "YaclScreenHelper.class must NOT invoke removed ConfirmLinkScreen.confirmLinkNow(Screen, String)");
    }
}
