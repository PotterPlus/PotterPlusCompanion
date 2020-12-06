package io.github.grisstyl.potterplus.companion;

import com.google.common.collect.ImmutableList;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import io.github.grisstyl.potterplus.api.misc.PluginLogger;
import io.github.grisstyl.potterplus.companion.file.ConfigFile;
import io.github.grisstyl.potterplus.companion.listener.InteractListener;

import java.util.List;

/**
 * Copyright (c) 2013-2020 Tyler Grissom
 */
public class PotterPlusController {

    @Getter @NonNull
    private final PotterPlusPlugin plugin;

    @Getter
    private final ConfigFile config;

    public PotterPlusController(PotterPlusPlugin plugin) {
        this.plugin = plugin;
        this.config = new ConfigFile(this);

        this.load();
    }

    public List<String> getFirstTimeInstructions() {
        return ImmutableList.of(
                "| Welcome to PotterPlus!",
                "------------------------------",
                " The PotterPlus companion plugin offers features which",
                " intertwine with the Magic plugin and more specifically",
                " the PotterPlus configuration. Do /ppc to get started.",
                " > To bring up these instructions again, perform /ppc instructions",
                " Requirements:",
                "  - Magic plugin (https://github.com/elBukkit/MagicPlugin)",
                "  - PotterPlus config (https://github.com/grisstyl/PotterPlus)",
                " Optional dependencies:",
                "  - MagicScan plugin (https://github.com/grisstyl/MagicScan)"
        );
    }

    public void load() {
        this.registerListeners();

        if (config.isFirstInstallation()) {
            for (String s : getFirstTimeInstructions()) {
                PluginLogger.atInfo()
                        .with(s)
                        .print();
            }

            FileConfiguration fc = config.getFile();

            fc.set("first_installation", false);

            config.save();
        }
    }

    private void registerListeners() {
        PluginManager pm = Bukkit.getPluginManager();

        if (getConfig().isProgressMenuClickEnabled()) {
            pm.registerEvents(new InteractListener(plugin), plugin);

            PluginLogger.atInfo()
                    .with("Progress menu click feature is enabled. Shift right click with a wand to quickly bring up the progress menu.")
                    .print();
        } else {
            PluginLogger.atInfo()
                    .with("Progress menu click feature is disabled. This can be enabled in the config.yml.");
        }
    }
}
