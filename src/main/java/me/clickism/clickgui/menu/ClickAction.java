package me.clickism.clickgui.menu;

/**
 * Represents a click action/callback of a button.
 */
@FunctionalInterface
public interface ClickAction {
    /**
     * Called when the button is clicked.
     *
     * @param view the menu view
     * @param slot the slot of the button
     */
    void onClick(MenuView view, int slot);
}
