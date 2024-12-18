package me.clickism.clickgui.menu;

import org.bukkit.entity.Player;

public class MenuView {

    private final Menu menu;
    private final Player player;

    public MenuView(Menu menu, Player player) {
        this.menu = menu;
        this.player = player;
    }

    public Menu getMenu() {
        return menu;
    }

    public Player getPlayer() {
        return player;
    }
}
