package me.pyr.enchanting;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EnchantmentMenu implements Listener {

    public static final int ITEM_SLOT = 10;
    public static final Material BACKGROUND_ITEM = Material.GRAY_STAINED_GLASS_PANE;
    public static final Material ENCHANTMENT_ITEM = Material.ENCHANTED_BOOK;

    private final Inventory inventory;
    private final Player player;

    EnchantmentMenu(Player player) {
        this.player = player;
        this.inventory = Bukkit.createInventory(null, 54,
                ChatColor.translateAlternateColorCodes('&', "&8[&5Enchantment Table&8]"));

        for(int i = 0; i < 54; i++) {
            this.inventory.setItem(i, createGuiItem(BACKGROUND_ITEM, " "));
        }

        for(int i = 12; i < 44; i++) {
            this.inventory.setItem(i, createGuiItem(Material.LIGHT_GRAY_STAINED_GLASS_PANE, " "));
            if (i % 9 == 7) i += 4;
        }

        this.inventory.setItem(ITEM_SLOT, createGuiItem(Material.AIR));

        player.openInventory(this.inventory);
    }

    public void close() {
        if(player.getInventory() == this.inventory)
            player.closeInventory();
    }

    public void onClose() {
        ItemStack item = this.inventory.getItem(ITEM_SLOT);
        if(item != null)
            if(this.player.getInventory().firstEmpty() == -1) {
                this.player.getWorld().dropItem(this.player.getLocation(), item);
            } else {
                this.player.getInventory().addItem(item);
            }
    }

    @SuppressWarnings("DataFlowIssue")
    public void onClick(InventoryClickEvent event) {
        if(event.getClickedInventory() == this.inventory && event.getSlot() != ITEM_SLOT) event.setCancelled(true);
        if(event.getCurrentItem() != null && event.getCurrentItem().getType() == ENCHANTMENT_ITEM) {
            ItemStack item = this.inventory.getItem(ITEM_SLOT);
            if(item == null) return;
            EnchantmentItemClass itemClass = EnchantmentItemClass.getEnchantmentItemClass(item);

            if(itemClass == null || event.getCurrentItem() == null || event.getCurrentItem().getItemMeta() == null) return;
            String name = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
            name = name.substring(1, name.length() - 1);

            Enchantment enchantment = EnchantmentItemClass.getEnchantmentByName(name);
            if(enchantment == null) return;

            if (item.hasItemMeta()) {
                if (enchantment == Enchantment.PROTECTION_ENVIRONMENTAL && item.getItemMeta().hasEnchant(Enchantment.PROTECTION_EXPLOSIONS)) return;
                if (enchantment == Enchantment.PROTECTION_EXPLOSIONS && item.getItemMeta().hasEnchant(Enchantment.PROTECTION_ENVIRONMENTAL)) return;
            }

            Player player = (Player) event.getWhoClicked();
            int nextEnchCost = (item.getEnchantmentLevel(enchantment) + 1) * EnchantmentItemClass.getEnchantmentCost(enchantment);
            if(player.getLevel() < nextEnchCost) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou don't have enough levels to do this"));
                return;
            }
            int enchLevel = item.getEnchantmentLevel(enchantment);
            if(enchLevel >= itemClass.getMaxEnchantLevel(enchantment)) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cThis item already has the max level of this enchantment"));
                return;
            }

            item.removeEnchantment(enchantment);
            item.addUnsafeEnchantment(enchantment, enchLevel + 1);
            player.setLevel(player.getLevel() - nextEnchCost);

            Bukkit.getScheduler().scheduleSyncDelayedTask(EnchantmentPlugin.getPlugin(), this::updateInventory, 1);
        } else {
            Bukkit.getScheduler().scheduleSyncDelayedTask(EnchantmentPlugin.getPlugin(), this::updateInventory, 1);
        }
    }

    private void updateInventory() {
        ItemStack item = this.inventory.getItem(ITEM_SLOT);

        for(int i = 12; i < 44; i++) {
            this.inventory.setItem(i, createGuiItem(Material.LIGHT_GRAY_STAINED_GLASS_PANE, " "));
            if (i % 9 == 7) i += 4;
        }

        EnchantmentItemClass itemClass = EnchantmentItemClass.getEnchantmentItemClass(item);
        if(itemClass == null) return;

        int i = 12;
        for(Enchantment enchantment : itemClass.getEnchantmentList()) {
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.translateAlternateColorCodes('&',
                            "&7Enchantment Level &8(&a" + item.getEnchantmentLevel(enchantment) + "&7/&a"
                                    + itemClass.getMaxEnchantLevel(enchantment)) + "&8)");
            if(item.getEnchantmentLevel(enchantment) < itemClass.getMaxEnchantLevel(enchantment))
                lore.add(ChatColor.translateAlternateColorCodes('&',
                        "&7Next level cost &8| &a" + (item.getEnchantmentLevel(enchantment) + 1) * EnchantmentItemClass.getEnchantmentCost(enchantment)));
            this.inventory.setItem(i,
                    createGuiItem(ENCHANTMENT_ITEM,
                            ChatColor.translateAlternateColorCodes('&', "&8[&5" +
                                    EnchantmentItemClass.getEnchantmentName(enchantment) + "&8]"), lore));
            if (i % 9 == 7) i += 4;
            i++;
        }
    }


    private static ItemStack createGuiItem(Material material) {
        return createGuiItem(material, null, null);
    }

    private static ItemStack createGuiItem(Material material, String name) {
        return createGuiItem(material, name, null);
    }

    private static ItemStack createGuiItem(Material material, String name, List<String> lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if(meta == null) return item;
        if(name != null) meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        if(lore != null) meta.setLore(lore.stream()
                .map(loreline -> ChatColor.translateAlternateColorCodes('&', loreline))
                .collect(Collectors.toList()));
        item.setItemMeta(meta);
        return item;
    }

}
