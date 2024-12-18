package me.clickism.clickgui.menu;

import me.clickism.clickgui.menu.handler.ClickHandler;
import me.clickism.clickgui.menu.handler.StaticClickHandler;
import me.clickism.clickgui.menu.icon.Icon;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public abstract class Menu {

    private final String title;
    private final int size;

    @Nullable
    private final Location location;
    private final ClickHandler clickHandler;

    @Nullable
    private final MenuBackground background;

    private final Map<Integer, Button> buttonMap = new HashMap<>();

    public Menu(String title, int size, @Nullable Location location) {
        this(title, size, location, null, new StaticClickHandler());
    }

    public Menu(String title, int size, @Nullable Location location, @Nullable MenuBackground background,
                ClickHandler clickHandler) {
        this.location = location;
        this.size = size;
        this.title = title;
        this.background = background;
        this.clickHandler = clickHandler;
    }

    /**
     * Sets up the buttons for the menu based on the player viewing it.
     * See {@link #addButton(Button)}.
     *
     * @param viewer the player viewing the menu
     */
    public abstract void setupButtons(Player viewer, MenuManager menuManager);

    /**
     * Places the buttons of the menu in the given inventory.
     */
    private void placeButtons(Inventory inventory) {
        buttonMap.forEach((slot, button) -> inventory.setItem(slot, button.getIcon().getItem()));
    }

    /**
     * Decorates the given inventory with the background and buttons for the given player.
     *
     * @param viewer the player viewing the menu
     */
    public void decorate(Inventory inventory, Player viewer, MenuManager menuManager) {
        if (background != null) {
            for (int i = 0; i < size; i++) {
                addButton(background.getBackgroundButton(i));
            }
        }
        setupButtons(viewer, menuManager);
        placeButtons(inventory);
    }

    /**
     * Adds an icon to the menu.
     *
     * @param button the icon to add
     */
    protected void addButton(Button button) {
        buttonMap.put(button.getSlot(), button);
    }

    /**
     * Adds a silent button to the menu with the specified slot, icon and click event.
     *
     * @param slot    the slot of the button
     * @param icon    the icon of the button
     * @param onClick the click event of the button
     */
    protected void addButton(int slot, Icon icon, Consumer<InventoryClickEvent> onClick) {
        addButton(new SilentButton(slot, icon) {
            @Override
            protected void onClick(InventoryClickEvent event) {
                onClick.accept(event);
            }
        });
    }

    /**
     * Handles the click event when the menu is clicked.
     *
     * @param event the click event
     */
    protected void onClick(InventoryClickEvent event) {
        if (!clickHandler.isValidClick(event)) {
            event.setCancelled(true);
            return;
        }
        Button button = buttonMap.get(event.getRawSlot());
        if (button == null) return;
        if (clickHandler.handleClick(event)) {
            return; // Handled by the click handler
        }
        button.onClick(event);
    }

    /**
     * Handles the drag event when an item is dragged in the menu.
     */
    protected void onDrag(InventoryDragEvent event) {
        clickHandler.handleDrag(event);
    }

    /**
     * Handles the close event when the menu is closed.
     *
     * @param event the close event
     */
    protected void onClose(InventoryCloseEvent event) {
    }

    /**
     * Opens the menu for the specified player.
     *
     * @param viewer the player to open the menu for
     */
    Inventory open(Player viewer, MenuManager menuManager) {
        Inventory inventory = Bukkit.createInventory(null, size, title);
        decorate(inventory, viewer, menuManager);
        viewer.openInventory(inventory);
        return inventory;
    }

    /**
     * Gets the location of the menu.
     *
     * @return the location of the menu
     */
    @Nullable
    public Location getLocation() {
        return location;
    }

    /**
     * Gets the size of the menu.
     *
     * @return the size of the menu
     */
    public int getSize() {
        return size;
    }

    /**
     * Gets the title of the menu.
     *
     * @return the title of the menu
     */
    public String getTitle() {
        return title;
    }
}
