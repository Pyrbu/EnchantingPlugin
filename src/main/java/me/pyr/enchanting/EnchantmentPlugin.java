package me.pyr.enchanting;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class EnchantmentPlugin extends JavaPlugin {

    private static EnchantmentPlugin plugin;
    private static EnchantmentMenuManager menuManager;

    @Override
    public void onEnable() {
        plugin = this;
        menuManager = new EnchantmentMenuManager();

        registerListeners();
    }

    @Override
    public void onDisable() {
        menuManager.closeAllMenus();
    }

    public void registerListeners() {
        Bukkit.getPluginManager().registerEvents(menuManager, this);
    }

    public static EnchantmentPlugin getPlugin() {
        return plugin;
    }
}
