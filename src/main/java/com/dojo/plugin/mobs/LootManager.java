package com.dojo.plugin.mobs;

import com.dojo.plugin.items.DojoItems;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class LootManager {

    private static final Random random = new Random();

    public static void dropLoot(Location location, int tier) {
        World world = location.getWorld();
        if (world == null) return;

        int essenceAmount = 1 + random.nextInt(tier);
        ItemStack essence = DojoItems.createDojoEssence(tier);
        essence.setAmount(Math.min(essenceAmount, essence.getMaxStackSize()));
        world.dropItemNaturally(location, essence);

        if (tier >= 8) {
            double coreChance = tier == 10 ? 0.75 : 0.25;
            if (random.nextDouble() < coreChance) {
                world.dropItemNaturally(location, DojoItems.createDojoCore());
            }
        }
    }
}
