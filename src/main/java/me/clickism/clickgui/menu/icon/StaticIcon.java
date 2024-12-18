package me.clickism.clickgui.menu.icon;

import me.clickism.clickgui.util.Utils;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.List;
import java.util.function.Consumer;

public class StaticIcon extends Icon {

    private final ItemStack item;

    public StaticIcon(ItemStack item) {
        this.item = item;
    }

    public ItemStack getItem() {
        return item;
    }
    
    public StaticIcon setDisplayName(String displayName) {
        applyToMeta(meta -> meta.setDisplayName(Utils.colorize(displayName)));
        return this;
    }
    
    public StaticIcon addEnchantmentGlint() {
        applyToMeta(meta -> {
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            meta.addEnchant(Enchantment.DURABILITY, 1, true);
        });
        return this;
    }

    public StaticIcon addLore(String... lore) {
        addLore(List.of(lore));
        return this;
    }
    
    public StaticIcon addLore(List<String> lore) {
        List<String> colorized = lore.stream()
                .map(Utils::colorize)
                .toList();
        applyToMeta(meta -> meta.setLore(colorized));
        return this;
    }

    public StaticIcon applyToMeta(Consumer<ItemMeta> consumer) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return this;
        consumer.accept(meta);
        item.setItemMeta(meta);
        return this;
    }
}
