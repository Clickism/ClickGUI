# ClickGUI
ClickGUI is a GUI library for spigot plugins. It is designed to be easy to use and easy to implement.

### Usage
```java
public class ExamplePlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        MenuManager menuManager = new MenuManager(this);
        
        new Menu(player, MenuType.MENU_9X3)
                .setTitle("&b&lDiamonds Menu")
                .setBackground(i -> Button.withIcon(Material.BLACK_STAINED_GLASS_PANE))
                .addButton(0, Button.withIcon(Material.DIAMOND)
                        .setName("&b&lClick for Diamonds!")
                        .setLore("&7Get free diamonds!", "&7Click to use.")
                        .addEnchantmentGlint()
                        .hideAllAttributes()
                        .setOnClick((viewer, view, slot) -> {
                            viewer.sendMessage("You clicked the button!");
                            viewer.getInventory().addItem(new ItemStack(Material.DIAMOND));
                        }))
                .open(menuManager);
        
    }
}
```