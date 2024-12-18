package me.clickism.clickgui.menu;

import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Listens for menu clicks and passes them to their active menus.
 */
public class MenuManager implements Listener {

    /**
     * Creates a new menu manager and registers it as a listener.
     *
     * @param plugin the plugin to register the listener with
     */
    public MenuManager(JavaPlugin plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    private final Map<Inventory, Menu> activeMenus = new HashMap<>();

    public void openMenu(Menu menu, Player player) {
        activeMenus.put(menu.open(player, this), menu);
    }

    public void closeActiveMenus() {
        activeMenus.forEach((inventory, menu) -> {
            List<HumanEntity> viewers = inventory.getViewers();
            new ArrayList<>(viewers).forEach(HumanEntity::closeInventory);
        });
        activeMenus.clear();
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        runOnActiveMenu(event.getInventory(), menu -> menu.onClick(event));
    }

    @EventHandler
    public void onDrag(InventoryDragEvent event) {
        runOnActiveMenu(event.getInventory(), menu -> menu.onDrag(event));
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        Inventory inventory = event.getInventory();
        runOnActiveMenu(inventory, menu -> {
            activeMenus.remove(inventory);
            menu.onClose(event);
        });
    }

    private void runOnActiveMenu(Inventory inventory, Consumer<Menu> consumer) {
        Menu menu = activeMenus.get(inventory);
        if (menu == null) return;
        consumer.accept(menu);
    }
}