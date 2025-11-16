package de.clickism.clickgui.menu.handler;

import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * A click handler that does not allow changing the inventory of a menu in any way.
 */
public class StaticClickHandler implements ClickHandler {
    /**
     * Creates a new static click handler.
     */
    public StaticClickHandler() {
    }

    @Override
    public boolean handleClick(InventoryClickEvent event) {
        return false;
    }

    @Override
    public void handleDrag(InventoryDragEvent event) {
        int size = event.getView().getTopInventory().getSize();
        for (Integer slot : event.getRawSlots()) {
            if (slot >= size) continue;
            event.setCancelled(true);
            return;
        }
    }

    @Override
    public boolean isValidClick(InventoryClickEvent event) {
        if (isIllegalShiftClick(event)) return false;
        if (isIllegalDoubleClick(event)) return false;
        Inventory clickedInventory = event.getClickedInventory();
        Inventory topInventory = event.getView().getTopInventory();
        if (clickedInventory != null && !clickedInventory.equals(topInventory)) return true;
        return event.getCurrentItem() != null;
    }

    /**
     * Checks if the given click is an illegal shift click, resulting in items being taken away from the menu.
     *
     * @param event event to check
     * @return true if the click is an illegal shift click, false otherwise
     */
    protected boolean isIllegalShiftClick(InventoryClickEvent event) {
        if (!event.getClick().isShiftClick()) return false;
        return event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY;
    }

    /**
     * Checks if the given click is an illegal double click, resulting in items being taken away from the menu.
     *
     * @param event event to check
     * @return true if the click is an illegal double click, false otherwise
     */
    protected boolean isIllegalDoubleClick(InventoryClickEvent event) {
        if (event.getClick() != ClickType.DOUBLE_CLICK) return false;
        if (event.getAction() != InventoryAction.COLLECT_TO_CURSOR) return false;
        ItemStack cursor = event.getCursor();
        if (cursor == null) return false;
        // Iterate through all items in the top inventory, if any of them is similar to the cursor, it is an illegal
        // double click. Since, the player would otherwise collect those items from the top inventory.
        for (ItemStack item : event.getView().getTopInventory()) {
            if (item != null && item.isSimilar(cursor)) return true;
        }
        return false;
    }
}
