package com.dojo.plugin.items;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DojoItems {

    public static final String KATANA_ID = "dojo_katana";
    public static final String YARI_ID = "dojo_yari";
    public static final String NUNCHAKU_ID = "dojo_nunchaku";
    public static final String DOJO_ESSENCE_ID = "dojo_essence";
    public static final String DOJO_CORE_ID = "dojo_core";

    private static NamespacedKey idKey;

    public static void init(org.bukkit.plugin.Plugin plugin) {
        idKey = new NamespacedKey(plugin, "dojo_item_id");
    }

    public static NamespacedKey getIdKey() {
        return idKey;
    }

    public static ItemStack createKatana() {
        ItemStack item = new ItemStack(Material.IRON_SWORD);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§6Dojo Katana");
        List<String> lore = new ArrayList<>();
        lore.add("§7A finely honed blade");
        lore.add("§7forged in the dojo.");
        lore.add("");
        lore.add("§eFast, balanced strikes");
        meta.setLore(lore);
        meta.setCustomModelData(101);
        addAttribute(meta, Attribute.GENERIC_ATTACK_DAMAGE, 7.0);
        addAttribute(meta, Attribute.GENERIC_ATTACK_SPEED, 1.8);
        meta.getPersistentDataContainer().set(idKey, PersistentDataType.STRING, KATANA_ID);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack createYari() {
        ItemStack item = new ItemStack(Material.TRIDENT);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§6Dojo Yari");
        List<String> lore = new ArrayList<>();
        lore.add("§7A long spear favored");
        lore.add("§7for keeping enemies at bay.");
        lore.add("");
        lore.add("§eLong reach, high damage");
        meta.setLore(lore);
        meta.setCustomModelData(102);
        addAttribute(meta, Attribute.GENERIC_ATTACK_DAMAGE, 9.0);
        addAttribute(meta, Attribute.GENERIC_ATTACK_SPEED, 1.0);
        meta.getPersistentDataContainer().set(idKey, PersistentDataType.STRING, YARI_ID);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack createNunchaku() {
        ItemStack item = new ItemStack(Material.GOLDEN_HOE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§6Dojo Nunchaku");
        List<String> lore = new ArrayList<>();
        lore.add("§7Rapid strikes,");
        lore.add("§7hard to master.");
        lore.add("");
        lore.add("§eVery fast, low damage per hit");
        meta.setLore(lore);
        meta.setCustomModelData(103);
        addAttribute(meta, Attribute.GENERIC_ATTACK_DAMAGE, 3.0);
        addAttribute(meta, Attribute.GENERIC_ATTACK_SPEED, 3.2);
        meta.getPersistentDataContainer().set(idKey, PersistentDataType.STRING, NUNCHAKU_ID);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack createDojoEssence(int tier) {
        ItemStack item = new ItemStack(Material.PRISMARINE_CRYSTALS);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§dDojo Essence §7(Tier " + tier + ")");
        List<String> lore = new ArrayList<>();
        lore.add("§7Crafting material dropped by");
        lore.add("§7the Dojo Guardian.");
        meta.setLore(lore);
        meta.setCustomModelData(200 + tier);
        meta.getPersistentDataContainer().set(idKey, PersistentDataType.STRING, DOJO_ESSENCE_ID);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack createDojoCore() {
        ItemStack item = new ItemStack(Material.NETHER_STAR);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§5§lDojo Core");
        List<String> lore = new ArrayList<>();
        lore.add("§7A rare core dropped only by");
        lore.add("§7high-tier Dojo Guardians.");
        lore.add("§7Used to craft legendary weapons.");
        meta.setLore(lore);
        meta.setCustomModelData(210);
        meta.getPersistentDataContainer().set(idKey, PersistentDataType.STRING, DOJO_CORE_ID);
        item.setItemMeta(meta);
        return item;
    }

    public static String getItemId(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return null;
        ItemMeta meta = item.getItemMeta();
        if (idKey == null) return null;
        return meta.getPersistentDataContainer().get(idKey, PersistentDataType.STRING);
    }

    private static void addAttribute(ItemMeta meta, Attribute attribute, double amount) {
        AttributeModifier modifier = new AttributeModifier(
                UUID.randomUUID(),
                "dojo_weapon_modifier",
                amount,
                AttributeModifier.Operation.ADD_NUMBER,
                org.bukkit.inventory.EquipmentSlot.HAND
        );
        meta.addAttributeModifier(attribute, modifier);
    }
}
