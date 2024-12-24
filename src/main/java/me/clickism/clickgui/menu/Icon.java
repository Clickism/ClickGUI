package me.clickism.clickgui.menu;

import me.clickism.clickgui.annotations.Colorized;
import me.clickism.clickgui.util.Utils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.function.Consumer;

/**
 * Represents the icon of a button.
 */
@FunctionalInterface
public interface Icon {
    /**
     * Gets the item stack of this icon.
     *
     * @return the item stack
     */
    ItemStack get();

    /**
     * Sets the name of this icon.
     *
     * @param name the name
     * @return this icon
     */
    default Icon setName(@Colorized String name) {
        return applyToMeta(meta -> meta.setDisplayName(Utils.colorize(name)));
    }

    /**
     * Sets the material of this icon.
     *
     * @param material the material
     * @return this icon
     */
    default Icon setMaterial(Material material) {
        get().setType(material);
        return this;
    }

    /**
     * Hides the attributes of this icon.
     *
     * @return this icon
     */
    default Icon hideAttributes() {
        return applyToMeta(meta -> meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES));
    }

    /**
     * Adds the enchantment glint to this icon.
     *
     * @return this icon
     */
    default Icon addEnchantmentGlint() {
        return applyToMeta(meta -> {
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            meta.addEnchant(Enchantment.DURABILITY, 1, true);
        });
    }

    /**
     * Sets the lore of this icon.
     *
     * @param lore the lore
     * @return this icon
     */
    default Icon setLore(@Colorized String... lore) {
        return setLore(List.of(lore));
    }

    /**
     * Sets the lore of this icon.
     *
     * @param lore the lore
     * @return this icon
     */
    default Icon setLore(@Colorized List<String> lore) {
        return applyToMeta(meta -> {
            meta.setLore(lore.stream()
                    .map(Utils::colorize)
                    .toList());
        });
    }

    /**
     * Applies the consumer to the meta of this icon.
     *
     * @param consumer the consumer
     * @return this icon
     */
    default Icon applyToMeta(Consumer<ItemMeta> consumer) {
        ItemMeta meta = get().getItemMeta();
        if (meta == null) return this;
        consumer.accept(meta);
        get().setItemMeta(meta);
        return this;
    }

    /**
     * Creates an icon from the given item stack.
     *
     * @param item the item stack
     * @return the icon
     */
    static Icon of(ItemStack item) {
        final ItemStack copy = item.clone();
        return () -> copy;
    }

    /**
     * Creates an icon from the given material.
     *
     * @param material the material
     * @return the icon
     */
    static Icon of(Material material) {
        final ItemStack item = new ItemStack(material);
        return () -> item;
    }
}