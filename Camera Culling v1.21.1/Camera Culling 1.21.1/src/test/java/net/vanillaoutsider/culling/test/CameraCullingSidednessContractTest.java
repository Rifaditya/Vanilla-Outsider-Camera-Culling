// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.vanillaoutsider.culling.test;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class CameraCullingSidednessContractTest {

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

    @Test
    @DisplayName("Pillar 5: Fabric Mod JSON Client Environment Contract")
    void testFabricModJsonClientEnvironment() throws IOException {
        Path projectRoot = getProjectRoot();
        Path modJsonPath = projectRoot.resolve("src/main/resources/fabric.mod.json");
        assertTrue(Files.exists(modJsonPath), "fabric.mod.json must exist at " + modJsonPath);

        String content = Files.readString(modJsonPath);
        JsonObject json = JsonParser.parseString(content).getAsJsonObject();

        assertTrue(json.has("environment"), "fabric.mod.json must declare an 'environment' property");
        assertEquals("client", json.get("environment").getAsString(), "Environment must be strictly 'client'");

        assertTrue(json.has("entrypoints"), "fabric.mod.json must declare entrypoints");
        JsonObject entrypoints = json.getAsJsonObject("entrypoints");

        assertTrue(entrypoints.has("client"), "Must declare client entrypoints");
        assertFalse(entrypoints.has("server"), "Must declare ZERO server entrypoints for pure client mod");

        JsonArray clientEntrypoints = entrypoints.getAsJsonArray("client");
        assertNotNull(clientEntrypoints);
        assertTrue(clientEntrypoints.size() > 0, "Client entrypoints list must not be empty");
    }

    @Test
    @DisplayName("Pillar 5: GPLv3 License Header Contract Across All Java Sources")
    void testGplLicenseHeaders() throws IOException {
        Path projectRoot = getProjectRoot();
        Path mainJava = projectRoot.resolve("src/main/java");
        Path testJava = projectRoot.resolve("src/test/java");

        String expectedHeader = "// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3";

        try (Stream<Path> mainStream = Files.walk(mainJava);
             Stream<Path> testStream = Files.walk(testJava)) {
            List<Path> allJavaFiles = Stream.concat(mainStream, testStream)
                    .filter(p -> p.toString().endsWith(".java"))
                    .collect(Collectors.toList());

            assertFalse(allJavaFiles.isEmpty(), "Java source files must be discovered");

            for (Path javaFile : allJavaFiles) {
                String firstLine;
                try (Stream<String> lines = Files.lines(javaFile)) {
                    firstLine = lines.findFirst().orElse("");
                }
                if (firstLine.startsWith("\uFEFF")) {
                    firstLine = firstLine.substring(1);
                }
                assertEquals(expectedHeader, firstLine.trim(),
                        "Missing or malformed GPLv3 license header in: " + projectRoot.relativize(javaFile));
            }
        }
    }

    @Test
    @DisplayName("Pillar 5: Zero Banned Guard Reflection Tokens Contract")
    void testZeroBannedGuardReflection() throws IOException {
        Path projectRoot = getProjectRoot();
        Path mainJava = projectRoot.resolve("src/main/java");

        String bannedToken = "Mod" + "Version" + "Guard";

        try (Stream<Path> stream = Files.walk(mainJava)) {
            List<Path> javaFiles = stream
                    .filter(p -> p.toString().endsWith(".java"))
                    .collect(Collectors.toList());

            for (Path javaFile : javaFiles) {
                String content = Files.readString(javaFile);
                assertFalse(content.contains(bannedToken),
                        "Found banned reflection token (" + bannedToken + ") in: " + projectRoot.relativize(javaFile));
            }
        }
    }

    @Test
    @DisplayName("Pillar 5: Mixin RefMap Target Integrity Contract")
    void testMixinRefMapTargetIntegrity() throws IOException {
        Path projectRoot = getProjectRoot();
        Path mixinJsonPath = projectRoot.resolve("src/main/resources/camera-culling.mixins.json");
        assertTrue(Files.exists(mixinJsonPath), "camera-culling.mixins.json must exist at " + mixinJsonPath);

        JsonObject json = JsonParser.parseString(Files.readString(mixinJsonPath)).getAsJsonObject();
        assertTrue(json.has("package"), "Mixins config must declare package");
        String pkg = json.get("package").getAsString();
        String pkgPath = pkg.replace('.', '/');
        Path mixinSrcDir = projectRoot.resolve("src/main/java").resolve(pkgPath);

        assertTrue(json.has("client"), "Mixins config must declare client mixins");
        JsonArray clientMixins = json.getAsJsonArray("client");
        assertTrue(clientMixins.size() > 0, "Must declare at least one client mixin");

        for (JsonElement el : clientMixins) {
            String mixinName = el.getAsString();
            Path mixinSourceFile = mixinSrcDir.resolve(mixinName + ".java");
            assertTrue(Files.exists(mixinSourceFile),
                    "Declared client mixin does not exist on disk: " + projectRoot.relativize(mixinSourceFile));
        }
    }
}
