package io.github.grisstyl.potterplus.companion.integration;

import io.github.grisstyl.potterplus.companion.PotterPlusController;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import io.github.grisstyl.potterplus.api.misc.PluginLogger;

/**
 * Copyright (c) 2013-2020 Tyler Grissom
 */
public class MagicIntegration {

    @Getter @NonNull
    private final PotterPlusController controller;

    public MagicIntegration(PotterPlusController controller) {
        this.controller = controller;

        PluginManager pm = Bukkit.getPluginManager();

        if (pm.isPluginEnabled("Magic")) {
            PluginLogger.atInfo()
                    .with("Magic found. The integration will be set up.")
                    .print();
        }
    }
}
