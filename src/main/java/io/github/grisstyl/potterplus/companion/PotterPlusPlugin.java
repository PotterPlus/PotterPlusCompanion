package io.github.grisstyl.potterplus.companion;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.annotation.dependency.Dependency;
import org.bukkit.plugin.java.annotation.dependency.SoftDependency;
import org.bukkit.plugin.java.annotation.plugin.Description;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.plugin.java.annotation.plugin.author.Author;

/**
 * Copyright (c) 2020 Tyler Grissom
 */
@Plugin(name = "PotterPlusCompanion", version = "1.0.0")
@Description("A companion plugin for use with the PotterPlus configuration for the Magic plugin.")
@Author("T0xicTyler")
@Dependency("Magic")
@SoftDependency("MagicScan")
public class PotterPlusPlugin extends JavaPlugin {

    @Getter
    private PotterPlusPlugin plugin;

    @Getter
    private PotterPlusController controller;

    @Override
    public void onEnable() {
        plugin = this;

        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        this.controller = new PotterPlusController(this);
    }
}
