package me.clickism.clickgui.menu;

import org.bukkit.Bukkit;

/**
 * Represents types/sizes of menus that can be created.
 */
public enum MenuType {
    /**
     * A 9x1 menu.
     */
    MENU_9X1,
    /**
     * A 9x2 menu.
     */
    MENU_9X2,
    /**
     * A 9x3 menu.
     */
    MENU_9X3,
    /**
     * A 9x4 menu.
     */
    MENU_9X4,
    /**
     * A 9x5 menu.
     */
    MENU_9X5;

    private final int size;

    /**
     * Creates a new menu type.
     */
    MenuType() {
        this.size = 9 * (ordinal() + 1);
    }

    /**
     * Gets the size of the menu.
     *
     * @return the size
     */
    public int getSize() {
        return size;
    }

    /**
     * Gets the inventory supplier for the menu.
     *
     * @return the supplier
     */
    public InventorySupplier getSupplier() {
        return title -> Bukkit.createInventory(null, size, title);
    }
}
