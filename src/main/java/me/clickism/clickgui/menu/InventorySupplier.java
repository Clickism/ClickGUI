package me.clickism.clickgui.menu;

import org.bukkit.inventory.Inventory;

/**
 * Represents a supplier of an {@link Inventory}.
 */
@FunctionalInterface
public interface InventorySupplier {
    /**
     * Creates a new {@link Inventory} with the given title.
     *
     * @param title the title of the inventory
     * @return a new inventory
     */
    Inventory create(String title);
}
