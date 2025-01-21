package me.clickism.clickgui.menu;

import me.clickism.clickgui.menu.handler.ClickHandler;
import me.clickism.clickgui.menu.handler.StaticClickHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

/**
 * Represents a menu.
 */
public class Menu {
    /**
     * The player viewing the menu.
     */
    protected final Player player;

    /**
     * The size of the menu.
     */
    protected final int size;
    /**
     * The buttons in the menu.
     */
    protected final Button[] buttons;
    /**
     * The title of the menu.
     */
    protected String title = "";
    /**
     * The background of the menu.
     */
    protected MenuBackground background = (slot) -> null;

    /**
     * The supplier for the inventory.
     */
    protected final InventorySupplier inventorySupplier;
    /**
     * The click handler for the menu.
     */
    protected ClickHandler clickHandler = new StaticClickHandler();
    /**
     * The action to perform when the menu is open.
     */
    protected Consumer<MenuView> onOpen = view -> {};
    /**
     * The action to perform when the menu is closed.
     */
    protected Consumer<MenuView> onClose = view -> {};

    /**
     * Creates a new menu.
     *
     * @param player the player viewing the menu
     * @param type   the type of menu
     */
    public Menu(Player player, MenuType type) {
        this(player, type.getSupplier(), type.getSize());
    }

    /**
     * Creates a new menu.
     *
     * @param player        the player viewing the menu
     * @param inventoryType the type of inventory
     */
    public Menu(Player player, InventoryType inventoryType) {
        this(player, title -> Bukkit.createInventory(null, inventoryType, title), inventoryType.getDefaultSize());
    }

    /**
     * Creates a new menu.
     *
     * @param player            the player viewing the menu
     * @param inventorySupplier the supplier for the inventory
     * @param size              the size of the menu
     */
    protected Menu(Player player, InventorySupplier inventorySupplier, int size) {
        this.player = player;
        this.inventorySupplier = inventorySupplier;
        this.size = size;
        this.buttons = new Button[size];
    }

    /**
     * Adds a button to the menu.
     *
     * @param slot   the slot to add the button to
     * @param button the button to add
     * @return this menu
     */
    public Menu addButton(int slot, Button button) {
        if (slot >= size) {
            throw new IllegalArgumentException("Slot " + slot + " out of bounds for menu with size " + size);
        }
        buttons[slot] = button;
        return this;
    }

    /**
     * Sets the title of the menu.
     *
     * @param title the title
     * @return this menu
     */
    public Menu setTitle(@Colorized String title) {
        this.title = Utils.colorize(title);
        return this;
    }

    /**
     * Sets the background of the menu.
     *
     * @param background the background
     * @return this menu
     */
    public Menu setBackground(MenuBackground background) {
        this.background = background;
        return this;
    }

    /**
     * Sets the click handler of the menu.
     *
     * @param clickHandler the click handler
     * @return this menu
     */
    public Menu setClickHandler(ClickHandler clickHandler) {
        this.clickHandler = clickHandler;
        return this;
    }

    /**
     * Sets the action to perform when the menu is opened.
     *
     * @param onOpen the action to perform
     * @return this menu
     */
    public Menu setOnOpen(Consumer<MenuView> onOpen) {
        this.onOpen = onOpen;
        return this;
    }

    /**
     * Sets the action to perform when the menu is closed.
     *
     * @param onClose the action to perform
     * @return this menu
     */
    public Menu setOnClose(Consumer<MenuView> onClose) {
        this.onClose = onClose;
        return this;
    }

    private void placeButtons(Inventory inventory) {
        for (int i = 0; i < buttons.length; i++) {
            Button button = getButton(i);
            if (button == null) continue;
            buttons[i] = button; // Update in case of a background button
            inventory.setItem(i, button.icon.get());
        }
    }

    /**
     * Gets the button at a slot.
     *
     * @param slot the slot
     * @return the button
     */
    @Nullable
    public Button getButton(int slot) {
        Button button = buttons[slot];
        if (button != null) {
            return button;
        }
        return background.getButton(slot);
    }

    /**
     * Opens the menu.
     *
     * @param menuManager the menu manager
     * @return the view of the menu
     */
    public MenuView open(MenuManager menuManager) {
        Inventory inventory = inventorySupplier.create(title);
        placeButtons(inventory);
        player.openInventory(inventory);
        MenuView view = new MenuView(this, inventory, menuManager);
        onOpen.accept(view);
        return menuManager.registerActiveView(view);
    }

    /**
     * Opens the menu using the instance of the menu manager
     * set by {@link MenuManager#setInstance(MenuManager)}.
     *
     * @throws IllegalStateException if no instance was set
     * @return the view of the menu
     */
    public MenuView open() {
        MenuManager menuManager = MenuManager.getInstance();
        if (menuManager == null) {
            throw new IllegalStateException("MenuManager instance not set");
        }
        return open(menuManager);
    }

    /**
     * Gets the player viewing the menu.
     *
     * @return the player
     */
    public Player getPlayer() {
        return player;
    }
}
