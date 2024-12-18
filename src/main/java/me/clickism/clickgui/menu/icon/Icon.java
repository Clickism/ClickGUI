package me.clickism.clickgui.menu.icon;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public abstract class Icon {
    public abstract ItemStack getItem();

    public static StaticIcon of(Material material) {
        return new StaticIcon(new ItemStack(material));
    }

    public static StaticIcon of(ItemStack item) {
        return new StaticIcon(item);
    }

    public static StaticIcon of(String displayName, Material material) {
        return StaticIcon.of(material)
                .setDisplayName(displayName);
    }
}

