package me.clickism.clickgui.menu;

import me.clickism.clickgui.menu.icon.Icon;
import org.bukkit.entity.Player;

/**
 * A silent button that does not play a click sound when clicked.
 */
public abstract class SilentButton extends Button {
    /**
     * Creates a new silent button with the specified slot.
     *
     * @param slot the slot of the button
     * @param icon the icon of the button
     */
    public SilentButton(int slot, Icon icon) {
        super(slot, icon);
    }

    @Override
    public void playClickSound(Player player) {
        // Do nothing
    }
}
