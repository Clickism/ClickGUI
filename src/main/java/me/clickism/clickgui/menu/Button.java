package me.clickism.clickgui.menu;

import me.clickism.clickgui.menu.icon.Icon;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * Represents a icon in a menu.
 */
public abstract class Button {

    private final int slot;
    private final Icon icon;

    /**
     * Creates a new icon with the specified slot.
     *
     * @param slot the slot of the icon
     */
    public Button(int slot, Icon icon) {
        this.slot = slot;
        this.icon = icon;
    }

    /**
     * Gets the icon of the icon.
     *
     * @return the icon of the icon
     */
    public Icon getIcon() {
        return icon;
    }

    /**
     * Handles the click event when the icon is clicked.
     *
     * @param event the click event
     */
    protected abstract void onClick(InventoryClickEvent event);

    /**
     * Plays the click sound for the player.
     *
     * @param player the player
     */
    protected abstract void playClickSound(Player player);

    /**
     * Gets the slot of the icon.
     *
     * @return the slot of the icon
     */
    public int getSlot() {
        return slot;
    }
}
