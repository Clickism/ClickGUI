package me.clickism.clickgui.menu;

import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Consumer;

/**
 * Listens for menu clicks and passes them to their active menus.
 */
public class MenuManager implements Listener {

    /**
     * The instance of the menu manager.
     */
    protected static MenuManager instance;

    private final JavaPlugin plugin;
    private final Map<Inventory, MenuView> activeMenus = new HashMap<>();

    /**
     * Creates a new menu manager and registers it as a listener.
     *
     * @param plugin the plugin to register the listener with
     */
    public MenuManager(JavaPlugin plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    /**
     * Opens a menu.
     *
     * @param menu the menu to open
     * @return the menu view
     */
    public MenuView openMenu(Menu menu) {
        return menu.open(this);
    }

    /**
     * Registers a menu view.
     *
     * @param view the view to register
     * @return the view
     */
    public MenuView registerActiveView(MenuView view) {
        activeMenus.put(view.getInventory(), view);
        return view;
    }

    /**
     * Closes all active menus.
     */
    public void closeActiveMenus() {
        Iterator<Map.Entry<Inventory, MenuView>> iterator = activeMenus.entrySet().iterator();
        while (iterator.hasNext()) {
            List<HumanEntity> viewers = iterator.next().getKey().getViewers();
            iterator.remove();
            new ArrayList<>(viewers).forEach(HumanEntity::closeInventory);
        }
    }

    /**
     * Closes all active menus when the plugin is disabled.
     *
     * @param event the event
     */
    @EventHandler
    public void onDisable(PluginDisableEvent event) {
        if (!event.getPlugin().equals(plugin)) return;
        closeActiveMenus();
    }

    /**
     * Passes the click event to the active menu.
     *
     * @param event the event
     */
    @EventHandler
    public void onClick(InventoryClickEvent event) {
        runOnActiveView(event.getInventory(), view -> {
            int slot = event.getRawSlot();
            if (slot < 0 || slot >= view.getInventory().getSize()) return;
            view.onClick(event);
        });
    }

    /**
     * Passes the drag event to the active menu.
     *
     * @param event the event
     */
    @EventHandler
    public void onDrag(InventoryDragEvent event) {
        runOnActiveView(event.getInventory(), view -> view.onDrag(event));
    }

    /**
     * Closes the active menu when the inventory is closed.
     *
     * @param event the event
     */
    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        Inventory inventory = event.getInventory();
        runOnActiveView(inventory, view -> {
            activeMenus.remove(inventory);
            view.onClose();
        });
    }

    private void runOnActiveView(Inventory inventory, Consumer<MenuView> consumer) {
        MenuView view = activeMenus.get(inventory);
        if (view == null) return;
        consumer.accept(view);
    }

    /**
     * Gets the instance of the menu manager, or
     * null if no instance was set.
     *
     * @return the instance
     */
    @Nullable
    public static MenuManager getInstance() {
        return instance;
    }

    /**
     * Sets the instance of the menu manager to be used
     * by the shorthand method {@link Menu#open()}.
     */
    public static void setInstance(MenuManager instance) {
        if (MenuManager.instance != null) {
            throw new IllegalStateException("Instance already set");
        }
        MenuManager.instance = instance;
    }
}