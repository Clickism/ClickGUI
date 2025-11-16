package de.clickism.clickgui.menu;

import org.bukkit.entity.Player;

/**
 * Represents a click action/callback of a button.
 */
@FunctionalInterface
public interface ClickAction {
    /**
     * Called when the button is clicked.
     *
     * @param player the player who clicked the button
     * @param view the menu view
     * @param slot the slot of the button
     */
    void onClick(Player player, MenuView view, int slot);
}
