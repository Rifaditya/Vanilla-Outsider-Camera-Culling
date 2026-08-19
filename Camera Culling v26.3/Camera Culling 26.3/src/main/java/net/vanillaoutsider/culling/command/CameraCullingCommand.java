// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.vanillaoutsider.culling.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.fabric.api.client.command.v2.ClientCommands;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.network.chat.Component;
import net.vanillaoutsider.culling.CameraCullingClient;
import net.vanillaoutsider.culling.config.CameraCullingConfig;
import net.vanillaoutsider.culling.config.CullingLevel;

import java.util.Set;

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
                        src.sendFeedback(Component.literal("§7Entity-Behind-Entity Culling: " + (CameraCullingConfig.isCullEntitiesBehindEntities() ? "§aActive" : "§7Inactive")));
                        src.sendFeedback(Component.literal("§7Cluster Density Cap: §e" + CameraCullingConfig.getMaxEntitiesPerCluster() + " mobs / 1.5 blocks"));
                        src.sendFeedback(Component.literal("§7Distance Texture LOD: " + (CameraCullingConfig.isDistanceTextureLod()
                            ? "§aEnabled §7(" + CameraCullingConfig.getDistanceTextureLodStart() + "m - " + CameraCullingConfig.getDistanceTextureLodFar() + "m)"
                            : "§cDisabled")));
                        double bossHp = CameraCullingConfig.getBossHealthThreshold();
                        double miniHp = CameraCullingConfig.getMiniBossHealthThreshold();
                        src.sendFeedback(Component.literal("§7Boss & Mini-Boss Immunity: " + (CameraCullingConfig.isBossImmunity()
                            ? "§aActive §7(Boss: §e" + bossHp + " HP§7/§c" + (bossHp / 2.0) + "♥§7 | Mini-Boss: §e" + miniHp + " HP§7/§c" + (miniHp / 2.0) + "♥§7)"
                            : "§cDisabled")));
                        src.sendFeedback(Component.literal("§7Particle Culling: " + (CameraCullingConfig.isCullParticles() ? "§aEnabled" : "§cDisabled")));
                        src.sendFeedback(Component.literal("§7Animation Culling: " + (CameraCullingConfig.isCullAnimations() ? "§aEnabled" : "§cDisabled")));
                        src.sendFeedback(Component.literal("§7Debug Mode: " + (CameraCullingConfig.isDebugMode() ? "§aEnabled (Real-Time Tracing)" : "§cDisabled")));
                        src.sendFeedback(Component.literal("§7Client Blacklist: §e" + CameraCullingConfig.getClientBlacklist().size() + " entities§7 | Server Blacklist: §e" + CameraCullingConfig.getServerBlacklist().size() + " entities"));
                        src.sendFeedback(Component.literal("§7Culled Entities: §b" + CameraCullingClient.getCulledEntitiesCount() + "§7 | Rendered: §b" + CameraCullingClient.getRenderedEntitiesCount()));
                        src.sendFeedback(Component.literal("§7Culled Block Entities: §b" + CameraCullingClient.getCulledBlockEntitiesCount() + "§7 | Rendered: §b" + CameraCullingClient.getRenderedBlockEntitiesCount()));
                        src.sendFeedback(Component.literal("§7Culled Particles: §b" + CameraCullingClient.getCulledParticlesCount() + "§7 | Rendered: §b" + CameraCullingClient.getRenderedParticlesCount()));
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
                .then(ClientCommands.literal("debug")
                    .executes(ctx -> {
                        boolean newState = !CameraCullingConfig.isDebugMode();
                        CameraCullingConfig.setDebugMode(newState);
                        ctx.getSource().sendFeedback(Component.literal("§6[Camera Culling]§r Real-Time Debug Logging is now " + (newState ? "§aEnabled" : "§cDisabled")));
                        return 1;
                    })
                    .then(ClientCommands.argument("enabled", BoolArgumentType.bool())
                        .executes(ctx -> {
                            boolean enabled = BoolArgumentType.getBool(ctx, "enabled");
                            CameraCullingConfig.setDebugMode(enabled);
                            ctx.getSource().sendFeedback(Component.literal("§6[Camera Culling]§r Real-Time Debug Logging set to: " + (enabled ? "§aEnabled" : "§cDisabled")));
                            return 1;
                        })
                    )
                )
                .then(ClientCommands.literal("particles")
                    .executes(ctx -> {
                        boolean newState = !CameraCullingConfig.isCullParticles();
                        CameraCullingConfig.setCullParticles(newState);
                        ctx.getSource().sendFeedback(Component.literal("§6[Camera Culling]§r Particle Culling is now " + (newState ? "§aEnabled" : "§cDisabled")));
                        return 1;
                    })
                    .then(ClientCommands.argument("enabled", BoolArgumentType.bool())
                        .executes(ctx -> {
                            boolean enabled = BoolArgumentType.getBool(ctx, "enabled");
                            CameraCullingConfig.setCullParticles(enabled);
                            ctx.getSource().sendFeedback(Component.literal("§6[Camera Culling]§r Particle Culling set to: " + (enabled ? "§aEnabled" : "§cDisabled")));
                            return 1;
                        })
                    )
                )
                .then(ClientCommands.literal("animations")
                    .executes(ctx -> {
                        boolean newState = !CameraCullingConfig.isCullAnimations();
                        CameraCullingConfig.setCullAnimations(newState);
                        ctx.getSource().sendFeedback(Component.literal("§6[Camera Culling]§r Block & Texture Animation Culling is now " + (newState ? "§aEnabled" : "§cDisabled")));
                        return 1;
                    })
                    .then(ClientCommands.argument("enabled", BoolArgumentType.bool())
                        .executes(ctx -> {
                            boolean enabled = BoolArgumentType.getBool(ctx, "enabled");
                            CameraCullingConfig.setCullAnimations(enabled);
                            ctx.getSource().sendFeedback(Component.literal("§6[Camera Culling]§r Block & Texture Animation Culling set to: " + (enabled ? "§aEnabled" : "§cDisabled")));
                            return 1;
                        })
                    )
                )
                .then(ClientCommands.literal("blacklist")
                    .then(ClientCommands.literal("add")
                        .then(ClientCommands.argument("entity_id", StringArgumentType.string())
                            .executes(ctx -> {
                                String id = StringArgumentType.getString(ctx, "entity_id");
                                boolean added = CameraCullingConfig.addClientBlacklist(id);
                                if (added) {
                                    ctx.getSource().sendFeedback(Component.literal("§6[Camera Culling]§r Added §e" + id + "§r to personal immunity blacklist (never culled)."));
                                } else {
                                    ctx.getSource().sendFeedback(Component.literal("§6[Camera Culling]§c " + id + " is already on your personal blacklist."));
                                }
                                return 1;
                            })
                        )
                    )
                    .then(ClientCommands.literal("remove")
                        .then(ClientCommands.argument("entity_id", StringArgumentType.string())
                            .executes(ctx -> {
                                String id = StringArgumentType.getString(ctx, "entity_id");
                                boolean removed = CameraCullingConfig.removeClientBlacklist(id);
                                if (removed) {
                                    ctx.getSource().sendFeedback(Component.literal("§6[Camera Culling]§r Removed §e" + id + "§r from personal immunity blacklist."));
                                } else {
                                    ctx.getSource().sendFeedback(Component.literal("§6[Camera Culling]§c " + id + " was not found on your personal blacklist."));
                                }
                                return 1;
                            })
                        )
                    )
                    .then(ClientCommands.literal("list")
                        .executes(ctx -> {
                            Set<String> list = CameraCullingConfig.getClientBlacklist();
                            if (list.isEmpty()) {
                                ctx.getSource().sendFeedback(Component.literal("§6[Camera Culling]§7 Your personal immunity blacklist is currently empty."));
                            } else {
                                ctx.getSource().sendFeedback(Component.literal("§6[Camera Culling]§e Personal Blacklisted Entities (" + list.size() + "):§r " + String.join(", ", list)));
                            }
                            return 1;
                        })
                    )
                    .then(ClientCommands.literal("clear")
                        .executes(ctx -> {
                            CameraCullingConfig.clearClientBlacklist();
                            ctx.getSource().sendFeedback(Component.literal("§6[Camera Culling]§a Personal immunity blacklist cleared."));
                            return 1;
                        })
                    )
                )
                .then(ClientCommands.literal("serverblacklist")
                    .then(ClientCommands.literal("add")
                        .then(ClientCommands.argument("entity_id", StringArgumentType.string())
                            .executes(ctx -> {
                                String id = StringArgumentType.getString(ctx, "entity_id");
                                boolean added = CameraCullingConfig.addServerBlacklist(id);
                                if (added) {
                                    ctx.getSource().sendFeedback(Component.literal("§6[Camera Culling]§r Added §e" + id + "§r to server-enforced immunity blacklist."));
                                } else {
                                    ctx.getSource().sendFeedback(Component.literal("§6[Camera Culling]§c " + id + " is already on the server blacklist."));
                                }
                                return 1;
                            })
                        )
                    )
                    .then(ClientCommands.literal("remove")
                        .then(ClientCommands.argument("entity_id", StringArgumentType.string())
                            .executes(ctx -> {
                                String id = StringArgumentType.getString(ctx, "entity_id");
                                boolean removed = CameraCullingConfig.removeServerBlacklist(id);
                                if (removed) {
                                    ctx.getSource().sendFeedback(Component.literal("§6[Camera Culling]§r Removed §e" + id + "§r from server-enforced immunity blacklist."));
                                } else {
                                    ctx.getSource().sendFeedback(Component.literal("§6[Camera Culling]§c " + id + " was not found on the server blacklist."));
                                }
                                return 1;
                            })
                        )
                    )
                    .then(ClientCommands.literal("list")
                        .executes(ctx -> {
                            Set<String> list = CameraCullingConfig.getServerBlacklist();
                            if (list.isEmpty()) {
                                ctx.getSource().sendFeedback(Component.literal("§6[Camera Culling]§7 The server-enforced immunity blacklist is currently empty."));
                            } else {
                                ctx.getSource().sendFeedback(Component.literal("§6[Camera Culling]§e Server-Enforced Blacklisted Entities (" + list.size() + "):§r " + String.join(", ", list)));
                            }
                            return 1;
                        })
                    )
                    .then(ClientCommands.literal("clear")
                        .executes(ctx -> {
                            CameraCullingConfig.clearServerBlacklist();
                            ctx.getSource().sendFeedback(Component.literal("§6[Camera Culling]§a Server-enforced immunity blacklist cleared."));
                            return 1;
                        })
                    )
                )
                .then(ClientCommands.literal("cluster")
                    .then(ClientCommands.argument("max_entities", IntegerArgumentType.integer(1, 128))
                        .executes(ctx -> {
                            int limit = IntegerArgumentType.getInteger(ctx, "max_entities");
                            CameraCullingConfig.setMaxEntitiesPerCluster(limit);
                            ctx.getSource().sendFeedback(Component.literal("§6[Camera Culling]§r Cluster density cap set to: §e" + limit + " mobs / 1.5 blocks§r"));
                            return 1;
                        })
                    )
                )
                .then(ClientCommands.literal("crowdculling")
                    .then(ClientCommands.argument("enabled", BoolArgumentType.bool())
                        .executes(ctx -> {
                            boolean enabled = BoolArgumentType.getBool(ctx, "enabled");
                            CameraCullingConfig.setCullEntitiesBehindEntities(enabled);
                            ctx.getSource().sendFeedback(Component.literal("§6[Camera Culling]§r Entity-Behind-Entity culling override set to: " + (enabled ? "§aEnabled" : "§cDisabled")));
                            return 1;
                        })
                    )
                )
                .then(ClientCommands.literal("texturelod")
                    .then(ClientCommands.argument("enabled", BoolArgumentType.bool())
                        .executes(ctx -> {
                            boolean enabled = BoolArgumentType.getBool(ctx, "enabled");
                            CameraCullingConfig.setDistanceTextureLod(enabled);
                            ctx.getSource().sendFeedback(Component.literal("§6[Camera Culling]§r Distance Texture LOD set to: " + (enabled ? "§aEnabled" : "§cDisabled")));
                            return 1;
                        })
                    )
                    .then(ClientCommands.literal("range")
                        .then(ClientCommands.argument("start_dist", DoubleArgumentType.doubleArg(1.0, 256.0))
                            .then(ClientCommands.argument("far_dist", DoubleArgumentType.doubleArg(2.0, 512.0))
                                .executes(ctx -> {
                                    double start = DoubleArgumentType.getDouble(ctx, "start_dist");
                                    double far = DoubleArgumentType.getDouble(ctx, "far_dist");
                                    if (start >= far) {
                                        ctx.getSource().sendFeedback(Component.literal("§6[Camera Culling]§c Error: Start distance must be strictly less than Far distance."));
                                        return 0;
                                    }
                                    CameraCullingConfig.setDistanceTextureLodRange(start, far);
                                    ctx.getSource().sendFeedback(Component.literal("§6[Camera Culling]§r Distance Texture LOD range set to: §e" + CameraCullingConfig.getDistanceTextureLodStart() + "m (Half) / " + CameraCullingConfig.getDistanceTextureLodFar() + "m (Quarter)§r"));
                                    return 1;
                                })
                            )
                        )
                    )
                )
                .then(ClientCommands.literal("bossimmunity")
                    .then(ClientCommands.argument("enabled", BoolArgumentType.bool())
                        .executes(ctx -> {
                            boolean enabled = BoolArgumentType.getBool(ctx, "enabled");
                            CameraCullingConfig.setBossImmunity(enabled);
                            ctx.getSource().sendFeedback(Component.literal("§6[Camera Culling]§r Boss & Mini-Boss immunity set to: " + (enabled ? "§aEnabled" : "§cDisabled")));
                            return 1;
                        })
                    )
                )
                .then(ClientCommands.literal("bosshealth")
                    .then(ClientCommands.argument("boss_hp", DoubleArgumentType.doubleArg(1.0, 10000.0))
                        .executes(ctx -> {
                            double hp = DoubleArgumentType.getDouble(ctx, "boss_hp");
                            CameraCullingConfig.setBossHealthThreshold(hp);
                            ctx.getSource().sendFeedback(Component.literal("§6[Camera Culling]§r Major Boss health threshold set to: §e" + hp + " HP §7(§c" + (hp / 2.0) + " hearts§7)"));
                            return 1;
                        })
                        .then(ClientCommands.argument("miniboss_hp", DoubleArgumentType.doubleArg(1.0, 10000.0))
                            .executes(ctx -> {
                                double bossHp = DoubleArgumentType.getDouble(ctx, "boss_hp");
                                double miniHp = DoubleArgumentType.getDouble(ctx, "miniboss_hp");
                                CameraCullingConfig.setBossHealthThreshold(bossHp);
                                CameraCullingConfig.setMiniBossHealthThreshold(miniHp);
                                ctx.getSource().sendFeedback(Component.literal("§6[Camera Culling]§r Health thresholds updated: Boss §e" + bossHp + " HP §7(§c" + (bossHp / 2.0) + "♥§7) | Mini-Boss §e" + miniHp + " HP §7(§c" + (miniHp / 2.0) + "♥§7)"));
                                return 1;
                            })
                        )
                    )
                )
                .then(ClientCommands.literal("minibosshealth")
                    .then(ClientCommands.argument("miniboss_hp", DoubleArgumentType.doubleArg(1.0, 10000.0))
                        .executes(ctx -> {
                            double hp = DoubleArgumentType.getDouble(ctx, "miniboss_hp");
                            CameraCullingConfig.setMiniBossHealthThreshold(hp);
                            ctx.getSource().sendFeedback(Component.literal("§6[Camera Culling]§r Mini-Boss health threshold set to: §e" + hp + " HP §7(§c" + (hp / 2.0) + " hearts§7)"));
                            return 1;
                        })
                    )
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
