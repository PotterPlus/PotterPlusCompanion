package io.github.grisstyl.potterplus.companion.file;

import io.github.grisstyl.potterplus.companion.PotterPlusController;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * Copyright (c) 2013-2020 Tyler Grissom
 */
@RequiredArgsConstructor
public class ConfigFile {

    @Getter @NonNull
    private final PotterPlusController controller;

    public FileConfiguration getFile() {
        return controller.getPlugin().getConfig();
    }

    public void save() {
        controller.getPlugin().saveConfig();
    }

    public boolean isFirstInstallation() {
        return getFile().getBoolean("first_installation", true);
    }

    public boolean isProgressMenuClickEnabled() {
        return getFile().getBoolean("features.progress_menu_click", true);
    }

    public String getProgressMenuSpell() {
        return getFile().getString("progress_menu_spell", "progressmenu");
    }
}
