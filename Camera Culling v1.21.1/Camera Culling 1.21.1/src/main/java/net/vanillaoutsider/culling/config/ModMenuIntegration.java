// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.vanillaoutsider.culling.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.fabricmc.loader.api.FabricLoader;

public class ModMenuIntegration implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        if (FabricLoader.getInstance().isModLoaded("yet_another_config_lib_v3")) {
            try {
                Class<?> helperClass = Class.forName("net.vanillaoutsider.culling.config.YaclScreenHelper");
                java.lang.reflect.Method method = helperClass.getMethod("createFactory");
                return (ConfigScreenFactory<?>) method.invoke(null);
            } catch (Exception ignored) {
            }
        }
        return parent -> null;
    }
}
