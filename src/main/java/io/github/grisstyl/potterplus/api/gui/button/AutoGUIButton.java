package io.github.grisstyl.potterplus.api.gui.button;

import org.bukkit.inventory.ItemStack;

import java.util.function.Supplier;

/**
 * A simple automatically canceled GUI button.
 */
public class AutoGUIButton extends GUIButton {

    public AutoGUIButton(ItemStack item) {
        super(item);

        this.setListener(event -> event.setCancelled(true));
    }

    public AutoGUIButton(Supplier<ItemStack> item) {
        this(item.get());
    }
}
