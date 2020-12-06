package io.github.grisstyl.potterplus.api.gui;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.plugin.java.JavaPlugin;
import io.github.grisstyl.potterplus.api.gui.button.GUIButton;

import java.util.HashMap;
import java.util.Map;

/**
 * A basic custom inventory setup.
 */
public class GUI implements InventoryHolder {

    // TODO Document

    /**
     * Listens for GUIs to handle logic appropriately.
     */
    private static class GUIListener implements Listener {

        @EventHandler
        public void onInventoryClick(InventoryClickEvent event) {
            InventoryHolder holder = event.getInventory().getHolder();

            if (holder != null) {
                if (holder instanceof GUI) {
                    GUI gui = (GUI) holder;
                    GUIButton button = gui.getButton(event.getSlot());

                    if (button != null && button.getListener() != null) {
                        button.getListener().onClick(event);
                    }
                }
            }
        }

        @EventHandler
        public void onInventoryClose(InventoryCloseEvent event) {
            InventoryHolder holder = event.getInventory().getHolder();

            if (holder != null) {
                if (holder instanceof GUI) {
                    if (holder instanceof ConfirmPrompt && ((ConfirmPrompt) holder).isSafelyClosed()) {
                        return;
                    }

                    GUI gui = (GUI) holder;

                    gui.onClose(event);
                }
            }
        }
    }

    public static void prepare(JavaPlugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(new GUIListener(), plugin);
    }

    @Getter
    private String title;

    @Getter
    private int size;

    @Getter @Setter
    private Map<Integer, GUIButton> items;

    public void setTitle(String title) {
        this.title = ChatColor.translateAlternateColorCodes('&', title);
    }

    public GUI(String title, int size) {
        this.title = ChatColor.translateAlternateColorCodes('&', title);
        this.size = size;
        this.items = new HashMap<>();
    }

    public GUI(String name) {
        this(name, 9);
    }

    public void addButton(GUIButton button) {
        int slot = 0;

        if (!items.isEmpty()) {
            int highestSlot = -1;

            for (int itemSlot : items.keySet()) {
                if (itemSlot > highestSlot) {
                    highestSlot = itemSlot;
                }
            }

            slot = highestSlot + 1;
        }

        items.put(slot, button);
    }

    public void setButton(int slot, GUIButton button) {
        items.put(slot, button);
    }

    public void removeButton(int slot) {
        items.remove(slot);
    }

    public void clearButtons() {
        this.items.clear();
    }

    public GUIButton getButton(int slot) {
        if (slot < getSize()) {
            return items.get(slot);
        }

        return null;
    }

    public void refreshInventory(HumanEntity holder) {
        holder.closeInventory();
        holder.openInventory(getInventory());
    }

    public void activate(HumanEntity player) {
        player.openInventory(this.getInventory());
    }

    public Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, getSize(), getTitle());

        if (items != null && !items.isEmpty()) {
            for (Map.Entry<Integer, GUIButton> entry : getItems().entrySet()) {
                Integer slot = entry.getKey();
                GUIButton button = entry.getValue();

                if (slot == null || button == null) continue;

                inventory.setItem(slot, button.getItem());
            }
        }

        return inventory;
    }

    public void onClose(InventoryCloseEvent event) {

    }
}
