package com.dojo.plugin.listeners;

import com.dojo.plugin.gui.DojoCraftGUI;
import com.dojo.plugin.items.DojoItems;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class CraftListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!event.getView().getTitle().equals(DojoCraftGUI.TITLE)) return;
        event.setCancelled(true);

        ItemStack clicked = event.getCurrentItem();
        if (clicked == null || !clicked.hasItemMeta()) return;

        String itemId = DojoItems.getItemId(clicked);
        if (itemId == null) return;

        int cost = DojoCraftGUI.costFor(itemId);
        if (cost <= 0) return;

        Player player = (Player) event.getWhoClicked();
        int owned = DojoCraftGUI.countEssence(player);

        if (owned < cost) {
            player.sendMessage("§6§lDOJO §7» §cYou need " + cost + "x Dojo Essence to craft this. You have " + owned + ".");
            return;
        }

        DojoCraftGUI.removeEssence(player, cost);
        ItemStack result = DojoCraftGUI.resultFor(itemId);
        player.getInventory().addItem(result);
        player.sendMessage("§6§lDOJO §7» §aYou crafted " + result.getItemMeta().getDisplayName() + "§a!");
    }
}
