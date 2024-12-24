package me.clickism.clickgui.menu;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;

/**
 * Represents a menu view.
 */
public class MenuView {
    private final Menu menu;
    private final Inventory inventory;
    private final MenuManager menuManager;

    /**
     * Creates a new menu view.
     *
     * @param menu        the menu
     * @param inventory   the inventory
     * @param menuManager the menu manager
     */
    public MenuView(Menu menu, Inventory inventory, MenuManager menuManager) {
        this.menu = menu;
        this.inventory = inventory;
        this.menuManager = menuManager;
    }

    /**
     * Closes the menu.
     */
    public void close() {
        menu.getPlayer().closeInventory();
    }

    /**
     * Opens the given menu.
     *
     * @param menu the menu to open
     */
    public void open(Menu menu) {
        menu.open(menuManager);
    }

    /**
     * Refreshes the button at the given slot.
     *
     * @param slot the slot to refresh
     */
    public void refresh(int slot) {
        Button button = menu.getButton(slot);
        if (button == null) {
            inventory.setItem(slot, null);
            return;
        }
        inventory.setItem(slot, button.icon.get());
    }

    /**
     * Refreshes all buttons in the menu.
     */
    public void refresh() {
        for (int i = 0; i < menu.buttons.length; i++) {
            refresh(i);
        }
    }

    /**
     * Handles a click event on the menu view.
     *
     * @param event the event to handle
     */
    protected void onClick(InventoryClickEvent event) {
        if (!menu.clickHandler.isValidClick(event)) return;
        if (menu.clickHandler.handleClick(event)) return;
        Button button = menu.buttons[event.getRawSlot()];
        if (button != null) {
            button.handleClick(event, this);
        }
    }

    /**
     * Handles a drag event on the menu view.
     *
     * @param event the event to handle
     */
    protected void onDrag(InventoryDragEvent event) {
        menu.clickHandler.handleDrag(event);
    }

    /**
     * Handles the closing of the menu view.
     */
    protected void onClose() {
        menu.onClose.accept(this);
    }

    /**
     * Gets the player viewing the menu.
     *
     * @return the player
     */
    public Player getPlayer() {
        return menu.getPlayer();
    }

    /**
     * Gets the menu.
     *
     * @return the menu
     */
    public Menu getMenu() {
        return menu;
    }

    /**
     * Gets the inventory.
     *
     * @return the inventory
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Gets the menu manager.
     *
     * @return the menu manager
     */
    public MenuManager getMenuManager() {
        return menuManager;
    }
}
