// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.vanillaoutsider.culling.command;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.client.command.v2.ClientCommands;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.network.chat.Component;
import net.vanillaoutsider.culling.CameraCullingClient;
import net.vanillaoutsider.culling.config.CameraCullingConfig;
import net.vanillaoutsider.culling.config.CullingLevel;

public final class CameraCullingCommand {
    private CameraCullingCommand() {}

    public static void register() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            registerCommands(dispatcher);
        });
    }

    public static void registerCommands(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        dispatcher.register(
            ClientCommands.literal("cameraculling")
                .then(ClientCommands.literal("status")
                    .executes(ctx -> {
                        FabricClientCommandSource src = ctx.getSource();
                        src.sendFeedback(Component.literal("§6[Camera Culling Status]§r"));
                        src.sendFeedback(Component.literal("§7Status: " + (CameraCullingConfig.isEnabled() ? "§aEnabled" : "§cDisabled")));
                        src.sendFeedback(Component.literal("§7Current Level: §e" + CameraCullingConfig.getLevel().getDisplayName()));
                        src.sendFeedback(Component.literal("§7Culled Entities: §b" + CameraCullingClient.getCulledEntitiesCount() + "§7 | Rendered: §b" + CameraCullingClient.getRenderedEntitiesCount()));
                        src.sendFeedback(Component.literal("§7Culled Block Entities: §b" + CameraCullingClient.getCulledBlockEntitiesCount() + "§7 | Rendered: §b" + CameraCullingClient.getRenderedBlockEntitiesCount()));
                        return 1;
                    })
                )
                .then(ClientCommands.literal("toggle")
                    .executes(ctx -> {
                        boolean newState = !CameraCullingConfig.isEnabled();
                        CameraCullingConfig.setEnabled(newState);
                        ctx.getSource().sendFeedback(Component.literal("§6[Camera Culling]§r Culling is now " + (newState ? "§aEnabled" : "§cDisabled")));
                        return 1;
                    })
                )
                .then(ClientCommands.literal("set")
                    .then(ClientCommands.literal("low")
                        .executes(ctx -> setLevel(ctx.getSource(), CullingLevel.LOW))
                    )
                    .then(ClientCommands.literal("medium")
                        .executes(ctx -> setLevel(ctx.getSource(), CullingLevel.MEDIUM))
                    )
                    .then(ClientCommands.literal("high")
                        .executes(ctx -> setLevel(ctx.getSource(), CullingLevel.HIGH))
                    )
                    .then(ClientCommands.literal("super")
                        .executes(ctx -> setLevel(ctx.getSource(), CullingLevel.SUPER))
                    )
                )
                .then(ClientCommands.literal("reload")
                    .executes(ctx -> {
                        CameraCullingConfig.load();
                        ctx.getSource().sendFeedback(Component.literal("§6[Camera Culling]§a Configuration reloaded. Active level: §e" + CameraCullingConfig.getLevel().getDisplayName()));
                        return 1;
                    })
                )
        );
    }

    private static int setLevel(FabricClientCommandSource src, CullingLevel level) {
        CameraCullingConfig.setLevel(level);
        src.sendFeedback(Component.literal("§6[Camera Culling]§r Culling level set to: §e" + level.getDisplayName()));
        return 1;
    }
}
