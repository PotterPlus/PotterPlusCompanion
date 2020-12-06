package io.github.grisstyl.potterplus.companion.listener;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import io.github.grisstyl.potterplus.companion.PotterPlusPlugin;
import io.github.grisstyl.potterplus.companion.file.ConfigFile;

/**
 * Copyright (c) 2013-2020 Tyler Grissom
 */
@RequiredArgsConstructor
public class InteractListener implements Listener {

    @Getter @NonNull
    private final PotterPlusPlugin plugin;

    @EventHandler
    public void onInteract(final PlayerInteractEvent event) {
        ConfigFile config = plugin.getController().getConfig();

        if (config.isProgressMenuClickEnabled()) {
            Player player = event.getPlayer();
            Action action = event.getAction();

            if (player.isSneaking()) {
                if (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) {
                    String commandStr = String.format("castp %s %s", player.getName(), config.getProgressMenuSpell());

                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), commandStr);
                }
            }
        }
    }
}
