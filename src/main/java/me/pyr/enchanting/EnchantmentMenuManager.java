package me.pyr.enchanting;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;
import java.util.Map;

public class EnchantmentMenuManager implements Listener {

    private final Map<Player, EnchantmentMenu> menuMap = new HashMap<>();

    public boolean isMenuOpen(Player player) {
        return getMenu(player) != null;
    }

    public EnchantmentMenu getMenu(Player player) {
        return menuMap.get(player);
    }

    public EnchantmentMenu openMenu(Player player) {
        EnchantmentMenu menu = new EnchantmentMenu(player);
        menuMap.put(player, menu);
        return menu;
    }

    public void closeAllMenus() {
        for (Player player : menuMap.keySet()) {
            if(isMenuOpen(player)) getMenu(player).close();
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        if(isMenuOpen(player)) {
            getMenu(player).onClose();
            menuMap.remove(player);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if(isMenuOpen((Player) event.getWhoClicked()))
            getMenu((Player) event.getWhoClicked()).onClick(event);
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        Block clickedBlock = event.getClickedBlock();
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && clickedBlock != null && clickedBlock.getType() == Material.ENCHANTING_TABLE) {
            event.setCancelled(true);
            menuMap.put(event.getPlayer(), openMenu(event.getPlayer()));
        }
    }
}
