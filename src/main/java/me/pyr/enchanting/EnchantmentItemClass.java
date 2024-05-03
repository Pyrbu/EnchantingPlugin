package me.pyr.enchanting;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public enum EnchantmentItemClass {
    SWORD(Arrays.asList(Material.WOODEN_SWORD, Material.STONE_SWORD, Material.IRON_SWORD, Material.DIAMOND_SWORD, Material.NETHERITE_SWORD, Material.GOLDEN_SWORD),
            generateEnchantmentMap(
                    Arrays.asList(Enchantment.DAMAGE_ALL, Enchantment.FIRE_ASPECT, Enchantment.DURABILITY, Enchantment.KNOCKBACK),
                    Arrays.asList(10, 3, 5, 2)
            )),
    AXE(Arrays.asList(Material.WOODEN_AXE, Material.STONE_AXE, Material.IRON_AXE, Material.DIAMOND_AXE, Material.NETHERITE_AXE, Material.GOLDEN_AXE),
            generateEnchantmentMap(
                    Arrays.asList(Enchantment.DAMAGE_ALL, Enchantment.FIRE_ASPECT, Enchantment.DURABILITY, Enchantment.KNOCKBACK, Enchantment.MENDING, Enchantment.DIG_SPEED),
                    Arrays.asList(10, 3, 5, 2, 1, 10)
            )),
    BOW(Collections.singletonList(Material.BOW),
            generateEnchantmentMap(
                    Arrays.asList(Enchantment.ARROW_DAMAGE, Enchantment.ARROW_FIRE, Enchantment.ARROW_KNOCKBACK, Enchantment.ARROW_INFINITE, Enchantment.DURABILITY),
                    Arrays.asList(10, 1, 2, 1, 5)
            )),
    SHIELD(Collections.singletonList(Material.SHIELD),
            generateEnchantmentMap(
                    Collections.singletonList(Enchantment.DURABILITY),
                    Collections.singletonList(5))),
    FISHING_ROD(Collections.singletonList(Material.FISHING_ROD),
            generateEnchantmentMap(
                    Arrays.asList(Enchantment.DURABILITY, Enchantment.KNOCKBACK),
                    Arrays.asList(5, 3))),
    TOOL(Arrays.asList(Material.WOODEN_SHOVEL, Material.STONE_SHOVEL, Material.IRON_SHOVEL, Material.DIAMOND_SHOVEL, Material.NETHERITE_SHOVEL, Material.GOLDEN_SHOVEL,
            Material.WOODEN_PICKAXE, Material.STONE_PICKAXE, Material.IRON_PICKAXE, Material.DIAMOND_PICKAXE, Material.NETHERITE_PICKAXE, Material.GOLDEN_PICKAXE),
            generateEnchantmentMap(
                    Arrays.asList(Enchantment.DIG_SPEED, Enchantment.DURABILITY, Enchantment.LOOT_BONUS_BLOCKS, Enchantment.MENDING),
                    Arrays.asList(10, 5, 5, 1))),
    HELMET(Arrays.asList(Material.LEATHER_HELMET, Material.CHAINMAIL_HELMET, Material.IRON_HELMET, Material.DIAMOND_HELMET, Material.NETHERITE_HELMET, Material.GOLDEN_HELMET),
            generateEnchantmentMap(
                    Arrays.asList(Enchantment.PROTECTION_ENVIRONMENTAL, Enchantment.DURABILITY, Enchantment.MENDING),
                    Arrays.asList(5, 10, 1))),
    CHESTPLATE(Arrays.asList(Material.LEATHER_CHESTPLATE, Material.CHAINMAIL_CHESTPLATE, Material.IRON_CHESTPLATE, Material.DIAMOND_CHESTPLATE, Material.NETHERITE_CHESTPLATE, Material.GOLDEN_CHESTPLATE),
            generateEnchantmentMap(
                    Arrays.asList(Enchantment.PROTECTION_ENVIRONMENTAL, Enchantment.DURABILITY, Enchantment.MENDING),
                    Arrays.asList(5, 10, 1))),
    LEGGINGS(Arrays.asList(Material.LEATHER_LEGGINGS, Material.CHAINMAIL_LEGGINGS, Material.IRON_LEGGINGS, Material.DIAMOND_LEGGINGS, Material.NETHERITE_LEGGINGS, Material.GOLDEN_LEGGINGS),
            generateEnchantmentMap(
                    Arrays.asList(Enchantment.PROTECTION_ENVIRONMENTAL, Enchantment.DURABILITY, Enchantment.MENDING),
                    Arrays.asList(5, 10, 1))),
    BOOTS(Arrays.asList(Material.LEATHER_BOOTS, Material.CHAINMAIL_BOOTS, Material.IRON_BOOTS, Material.DIAMOND_BOOTS, Material.NETHERITE_BOOTS, Material.GOLDEN_BOOTS),
            generateEnchantmentMap(
                    Arrays.asList(Enchantment.PROTECTION_ENVIRONMENTAL, Enchantment.DURABILITY, Enchantment.MENDING),
                    Arrays.asList(5, 10, 1)));

    private final List<Material> materialList;
    private final Map<Enchantment, Integer> enchantmentMap;

    EnchantmentItemClass(List<Material> materials, Map<Enchantment, Integer> enchantmentMap) {
        this.materialList = materials;
        this.enchantmentMap = enchantmentMap;
    }

    public List<Material> getMaterialList() {
        return this.materialList;
    }

    public List<Enchantment> getEnchantmentList() {
        return new ArrayList<>(enchantmentMap.keySet());
    }

    public Integer getMaxEnchantLevel(Enchantment enchantment) {
        return this.enchantmentMap.get(enchantment);
    }

    private static Map<Enchantment, Integer> generateEnchantmentMap(List<Enchantment> enchantmentList, List<Integer> levelList) {
        Map<Enchantment, Integer> enchantmentMap = new HashMap<>();
        if(enchantmentList.size() != levelList.size()) return enchantmentMap;
        for(int i = 0; i < enchantmentList.size(); i++)
            enchantmentMap.put(enchantmentList.get(i), levelList.get(i));
        return enchantmentMap;
    }

    private static Map<Enchantment, String> generateEnchantmentNameMap(List<Enchantment> enchantmentList, List<String> nameList) {
        Map<Enchantment, String> enchantmentMap = new HashMap<>();
        if(enchantmentList.size() != nameList.size()) return enchantmentMap;
        for(int i = 0; i < enchantmentList.size(); i++)
            enchantmentMap.put(enchantmentList.get(i), nameList.get(i));
        return enchantmentMap;
    }

    private static final Map<Enchantment, String> enchantmentNameMap = generateEnchantmentNameMap(
            Arrays.asList(
                    Enchantment.DURABILITY, Enchantment.PROTECTION_ENVIRONMENTAL, Enchantment.DAMAGE_ALL,
                    Enchantment.KNOCKBACK, Enchantment.MENDING, Enchantment.FIRE_ASPECT,
                    Enchantment.DIG_SPEED, Enchantment.LOOT_BONUS_BLOCKS, Enchantment.ARROW_DAMAGE,
                    Enchantment.ARROW_INFINITE, Enchantment.ARROW_KNOCKBACK, Enchantment.ARROW_FIRE, Enchantment.PROTECTION_EXPLOSIONS),
            Arrays.asList(
                    "Unbreaking", "Protection", "Sharpness",
                    "Knockback", "Mending", "Fire Aspect",
                    "Efficiency", "Fortune", "Power",
                    "Infinity", "Punch", "Flame", "Blast Protection")
    );
    private static final Map<Enchantment, Integer> enchantmentCostMap = generateEnchantmentMap(
            Arrays.asList(
                    Enchantment.DURABILITY, Enchantment.PROTECTION_ENVIRONMENTAL, Enchantment.DAMAGE_ALL,
                    Enchantment.KNOCKBACK, Enchantment.MENDING, Enchantment.FIRE_ASPECT,
                    Enchantment.DIG_SPEED, Enchantment.LOOT_BONUS_BLOCKS, Enchantment.ARROW_DAMAGE,
                    Enchantment.ARROW_INFINITE, Enchantment.ARROW_KNOCKBACK, Enchantment.ARROW_FIRE, Enchantment.PROTECTION_EXPLOSIONS),
            Arrays.asList(
                    5, 25, 20,
                    50, 3000, 10,
                    10, 30, 15,
                    500, 30, 100, 25)
    );

    public static String getEnchantmentName(Enchantment enchantment) {
        String name = enchantmentNameMap.get(enchantment);
        if(name != null) return name;
        return enchantment.getKey().toString();
    }

    public static Integer getEnchantmentCost(Enchantment enchantment) {
        if(enchantmentCostMap.get(enchantment) != null) return enchantmentCostMap.get(enchantment);
        return Integer.MAX_VALUE;
    }

    public static EnchantmentItemClass getEnchantmentItemClass(ItemStack item) {
        if(item == null) return null;
        for(EnchantmentItemClass currentItemClass : EnchantmentItemClass.values())
            if(currentItemClass.getMaterialList().contains(item.getType()))
                return currentItemClass;
        return null;
    }

    public static Enchantment getEnchantmentByName(String name) {
        for(Enchantment enchantment : enchantmentNameMap.keySet())
            if(enchantmentNameMap.get(enchantment).equalsIgnoreCase(name)) return enchantment;
        return null;
    }

}
