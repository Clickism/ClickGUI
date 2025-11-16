package de.clickism.clickgui.menu.handler;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

/**
 * Handles clicks before passing them onto a menu.
 * <p>This allows for custom click handling logic outside of buttons to be implemented.</p>
 */
public interface ClickHandler {

    /**
     * Handles a click on a menu. If the given click was processed by the handler and should
     * not be passed onto the menu, it should return true. Otherwise, it should return false.
     *
     * @param event event to handle
     * @return true if the click was handled, false otherwise
     */
    boolean handleClick(InventoryClickEvent event);

    /**
     * Handles the drag on the menu.
     *
     * @param event event to handle
     */
    void handleDrag(InventoryDragEvent event);

    /**
     * Checks if a given click is valid.
     *
     * @param event event to validate
     * @return true if click is valid, false otherwise
     */
    boolean isValidClick(InventoryClickEvent event);
}
