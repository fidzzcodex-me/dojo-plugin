package com.dojo.plugin.mobs;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DojoGuardian {

    private static NamespacedKey tierKey;
    private static NamespacedKey markerKey;

    private static final Map<UUID, BossBar> activeBars = new HashMap<>();

    public static void init(Plugin plugin) {
        tierKey = new NamespacedKey(plugin, "dojo_guardian_tier");
        markerKey = new NamespacedKey(plugin, "dojo_guardian_marker");
    }

    public static LivingEntity spawn(Location location, int tier) {
        tier = Math.max(1, Math.min(10, tier));

        LivingEntity entity = (LivingEntity) location.getWorld().spawnEntity(location, EntityType.ZOMBIE);

        double baseHealth = 20.0 + (tier * 15.0);
        double baseDamage = 3.0 + (tier * 1.2);
        double baseSpeed = 0.23 + (tier * 0.01);

        entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(baseHealth);
        entity.setHealth(baseHealth);
        if (entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE) != null) {
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(baseDamage);
        }
        if (entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED) != null) {
            entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(baseSpeed);
        }

        entity.setCustomName(nameForTier(tier));
        entity.setCustomNameVisible(true);
        entity.getPersistentDataContainer().set(tierKey, PersistentDataType.INTEGER, tier);
        entity.getPersistentDataContainer().set(markerKey, PersistentDataType.STRING, "dojo_guardian");

        BossBar bar = Bukkit.createBossBar(nameForTier(tier), colorForTier(tier), BarStyle.SEGMENTED_10);
        bar.setProgress(1.0);
        activeBars.put(entity.getUniqueId(), bar);

        return entity;
    }

    public static boolean isGuardian(LivingEntity entity) {
        if (markerKey == null) return false;
        return entity.getPersistentDataContainer().has(markerKey, PersistentDataType.STRING);
    }

    public static int getTier(LivingEntity entity) {
        if (tierKey == null) return 1;
        Integer tier = entity.getPersistentDataContainer().get(tierKey, PersistentDataType.INTEGER);
        return tier != null ? tier : 1;
    }

    public static void updateBossBar(LivingEntity entity) {
        BossBar bar = activeBars.get(entity.getUniqueId());
        if (bar == null) return;

        double max = entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
        double current = Math.max(0, entity.getHealth());
        bar.setProgress(Math.min(1.0, current / max));
    }

    public static void addViewer(LivingEntity entity, Player player) {
        BossBar bar = activeBars.get(entity.getUniqueId());
        if (bar != null && !bar.getPlayers().contains(player)) {
            bar.addPlayer(player);
        }
    }

    public static void removeViewer(LivingEntity entity, Player player) {
        BossBar bar = activeBars.get(entity.getUniqueId());
        if (bar != null) {
            bar.removePlayer(player);
        }
    }

    public static void cleanup(LivingEntity entity) {
        BossBar bar = activeBars.remove(entity.getUniqueId());
        if (bar != null) {
            bar.removeAll();
        }
    }

    private static String nameForTier(int tier) {
        String color = colorCodeForTier(tier);
        return color + "Dojo Guardian §7[Tier " + tier + "]";
    }

    private static String colorCodeForTier(int tier) {
        if (tier <= 3) return "§a";
        if (tier <= 6) return "§e";
        if (tier <= 9) return "§c";
        return "§5§l";
    }

    private static BarColor colorForTier(int tier) {
        if (tier <= 3) return BarColor.GREEN;
        if (tier <= 6) return BarColor.YELLOW;
        if (tier <= 9) return BarColor.RED;
        return BarColor.PURPLE;
    }
}
