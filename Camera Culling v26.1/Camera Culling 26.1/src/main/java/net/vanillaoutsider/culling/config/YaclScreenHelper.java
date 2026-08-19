// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.vanillaoutsider.culling.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.api.controller.DoubleFieldControllerBuilder;
import dev.isxander.yacl3.api.controller.DoubleSliderControllerBuilder;
import dev.isxander.yacl3.api.controller.EnumDropdownControllerBuilder;
import dev.isxander.yacl3.api.controller.IntegerSliderControllerBuilder;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public final class YaclScreenHelper {

    private YaclScreenHelper() {}

    public static ConfigScreenFactory<?> createFactory() {
        return YaclScreenHelper::createScreen;
    }

    public static Screen createScreen(Screen parent) {
        return YetAnotherConfigLib.createBuilder()
            .title(Component.translatable("config.cameraculling.title"))
            .save(CameraCullingConfig::save)

            // === 1. ENGINE & DIAGNOSTICS ===
            .category(ConfigCategory.createBuilder()
                .name(Component.translatable("config.cameraculling.category.general"))
                .tooltip(Component.translatable("config.cameraculling.category.general.tooltip"))
                
                // Master Enable
                .option(Option.<Boolean>createBuilder()
                    .name(Component.translatable("config.cameraculling.enabled"))
                    .description(OptionDescription.of(Component.translatable("config.cameraculling.enabled.desc")))
                    .binding(true, CameraCullingConfig::isEnabled, CameraCullingConfig::setEnabled)
                    .controller(TickBoxControllerBuilder::create)
                    .build())
                
                // Culling Level
                .option(Option.<CullingLevel>createBuilder()
                    .name(Component.translatable("config.cameraculling.level"))
                    .description(OptionDescription.of(Component.translatable("config.cameraculling.level.desc")))
                    .binding(CullingLevel.SUPER, CameraCullingConfig::getLevel, CameraCullingConfig::setLevel)
                    .controller(EnumDropdownControllerBuilder::create)
                    .build())
                
                // Real-Time Debug Logging
                .option(Option.<Boolean>createBuilder()
                    .name(Component.translatable("config.cameraculling.debugMode"))
                    .description(OptionDescription.of(Component.translatable("config.cameraculling.debugMode.desc")))
                    .binding(false, CameraCullingConfig::isDebugMode, CameraCullingConfig::setDebugMode)
                    .controller(TickBoxControllerBuilder::create)
                    .build())
                .build())

            // === 2. ENTITY & CROWD OCCLUSION ===
            .category(ConfigCategory.createBuilder()
                .name(Component.translatable("config.cameraculling.category.entities"))
                .tooltip(Component.translatable("config.cameraculling.category.entities.tooltip"))

                // Crowd Overdraw Culling (Behind Entities)
                .option(Option.<Boolean>createBuilder()
                    .name(Component.translatable("config.cameraculling.cullEntitiesBehindEntities"))
                    .description(OptionDescription.of(Component.translatable("config.cameraculling.cullEntitiesBehindEntities.desc")))
                    .binding(false, CameraCullingConfig::isCullEntitiesBehindEntities, CameraCullingConfig::setCullEntitiesBehindEntities)
                    .controller(TickBoxControllerBuilder::create)
                    .build())

                // Max Cluster Entities Cap
                .option(Option.<Integer>createBuilder()
                    .name(Component.translatable("config.cameraculling.maxEntitiesPerCluster"))
                    .description(OptionDescription.of(Component.translatable("config.cameraculling.maxEntitiesPerCluster.desc")))
                    .binding(8, CameraCullingConfig::getMaxEntitiesPerCluster, CameraCullingConfig::setMaxEntitiesPerCluster)
                    .controller(opt -> IntegerSliderControllerBuilder.create(opt).range(1, 32).step(1))
                    .build())

                // Boss Immunity
                .option(Option.<Boolean>createBuilder()
                    .name(Component.translatable("config.cameraculling.bossImmunity"))
                    .description(OptionDescription.of(Component.translatable("config.cameraculling.bossImmunity.desc")))
                    .binding(true, CameraCullingConfig::isBossImmunity, CameraCullingConfig::setBossImmunity)
                    .controller(TickBoxControllerBuilder::create)
                    .build())

                // Boss Health Threshold
                .option(Option.<Double>createBuilder()
                    .name(Component.translatable("config.cameraculling.bossHealthThreshold"))
                    .description(OptionDescription.of(Component.translatable("config.cameraculling.bossHealthThreshold.desc")))
                    .binding(150.0, CameraCullingConfig::getBossHealthThreshold, CameraCullingConfig::setBossHealthThreshold)
                    .controller(DoubleFieldControllerBuilder::create)
                    .build())

                // Mini-Boss Health Threshold
                .option(Option.<Double>createBuilder()
                    .name(Component.translatable("config.cameraculling.miniBossHealthThreshold"))
                    .description(OptionDescription.of(Component.translatable("config.cameraculling.miniBossHealthThreshold.desc")))
                    .binding(50.0, CameraCullingConfig::getMiniBossHealthThreshold, CameraCullingConfig::setMiniBossHealthThreshold)
                    .controller(DoubleFieldControllerBuilder::create)
                    .build())
                .build())

            // === 3. BLOCKS, PARTICLES & ANIMATIONS ===
            .category(ConfigCategory.createBuilder()
                .name(Component.translatable("config.cameraculling.category.rendering"))
                .tooltip(Component.translatable("config.cameraculling.category.rendering.tooltip"))

                // Particle Culling
                .option(Option.<Boolean>createBuilder()
                    .name(Component.translatable("config.cameraculling.cullParticles"))
                    .description(OptionDescription.of(Component.translatable("config.cameraculling.cullParticles.desc")))
                    .binding(true, CameraCullingConfig::isCullParticles, CameraCullingConfig::setCullParticles)
                    .controller(TickBoxControllerBuilder::create)
                    .build())

                // Block & Texture Animation Culling
                .option(Option.<Boolean>createBuilder()
                    .name(Component.translatable("config.cameraculling.cullAnimations"))
                    .description(OptionDescription.of(Component.translatable("config.cameraculling.cullAnimations.desc")))
                    .binding(true, CameraCullingConfig::isCullAnimations, CameraCullingConfig::setCullAnimations)
                    .controller(TickBoxControllerBuilder::create)
                    .build())

                // Distance Texture LOD
                .option(Option.<Boolean>createBuilder()
                    .name(Component.translatable("config.cameraculling.distanceTextureLod"))
                    .description(OptionDescription.of(Component.translatable("config.cameraculling.distanceTextureLod.desc")))
                    .binding(true, CameraCullingConfig::isDistanceTextureLod, CameraCullingConfig::setDistanceTextureLod)
                    .controller(TickBoxControllerBuilder::create)
                    .build())

                // Distance Texture LOD Start
                .option(Option.<Double>createBuilder()
                    .name(Component.translatable("config.cameraculling.distanceTextureLodStart"))
                    .description(OptionDescription.of(Component.translatable("config.cameraculling.distanceTextureLodStart.desc")))
                    .binding(16.0, CameraCullingConfig::getDistanceTextureLodStart, CameraCullingConfig::setDistanceTextureLodStart)
                    .controller(opt -> DoubleSliderControllerBuilder.create(opt).range(8.0, 64.0).step(1.0))
                    .build())

                // Distance Texture LOD Far
                .option(Option.<Double>createBuilder()
                    .name(Component.translatable("config.cameraculling.distanceTextureLodFar"))
                    .description(OptionDescription.of(Component.translatable("config.cameraculling.distanceTextureLodFar.desc")))
                    .binding(32.0, CameraCullingConfig::getDistanceTextureLodFar, CameraCullingConfig::setDistanceTextureLodFar)
                    .controller(opt -> DoubleSliderControllerBuilder.create(opt).range(16.0, 128.0).step(1.0))
                    .build())
                .build())
            .build()
            .generateScreen(parent);
    }
}
