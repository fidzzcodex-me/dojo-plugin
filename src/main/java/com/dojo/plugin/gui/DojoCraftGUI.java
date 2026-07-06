package com.dojo.plugin.gui;

import com.dojo.plugin.items.DojoItems;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class DojoCraftGUI {

    public static final String TITLE = "§6§lDojo Craftsman";

    public static Inventory build() {
        Inventory inv = Bukkit.createInventory(null, 27, TITLE);

        inv.setItem(10, craftEntry(DojoItems.createKatana(), Material.PRISMARINE_CRYSTALS, 8));
        inv.setItem(13, craftEntry(DojoItems.createYari(), Material.PRISMARINE_CRYSTALS, 12));
        inv.setItem(16, craftEntry(DojoItems.createNunchaku(), Material.PRISMARINE_CRYSTALS, 6));

        return inv;
    }

    private static ItemStack craftEntry(ItemStack displayItem, Material costMaterial, int costAmount) {
        ItemStack item = displayItem.clone();
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>(meta.hasLore() ? meta.getLore() : new ArrayList<>());
        lore.add("");
        lore.add("§7Cost: §d" + costAmount + "x Dojo Essence");
        lore.add("§eClick to craft");
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    public static int costFor(String itemId) {
        return switch (itemId) {
            case DojoItems.KATANA_ID -> 8;
            case DojoItems.YARI_ID -> 12;
            case DojoItems.NUNCHAKU_ID -> 6;
            default -> -1;
        };
    }

    public static ItemStack resultFor(String itemId) {
        return switch (itemId) {
            case DojoItems.KATANA_ID -> DojoItems.createKatana();
            case DojoItems.YARI_ID -> DojoItems.createYari();
            case DojoItems.NUNCHAKU_ID -> DojoItems.createNunchaku();
            default -> null;
        };
    }

    public static int countEssence(Player player) {
        int count = 0;
        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null && DojoItems.DOJO_ESSENCE_ID.equals(DojoItems.getItemId(item))) {
                count += item.getAmount();
            }
        }
        return count;
    }

    public static void removeEssence(Player player, int amount) {
        int remaining = amount;
        ItemStack[] contents = player.getInventory().getContents();
        for (int i = 0; i < contents.length; i++) {
            ItemStack item = contents[i];
            if (item != null && DojoItems.DOJO_ESSENCE_ID.equals(DojoItems.getItemId(item))) {
                int toRemove = Math.min(remaining, item.getAmount());
                item.setAmount(item.getAmount() - toRemove);
                remaining -= toRemove;
                if (remaining <= 0) break;
            }
        }
    }
}
