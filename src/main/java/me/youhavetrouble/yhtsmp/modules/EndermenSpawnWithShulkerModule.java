package me.youhavetrouble.yhtsmp.modules;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Enderman;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.persistence.PersistentDataType;

public class EndermenSpawnWithShulkerModule implements Listener {
    private final double chance;

    private static final NamespacedKey shulkyKey = new NamespacedKey("yhtsmp", "shulky");

    private final Set<Material> shulkers = Set.of(
        Material.SHULKER_BOX,
        Material.WHITE_SHULKER_BOX,
        Material.ORANGE_SHULKER_BOX,
        Material.MAGENTA_SHULKER_BOX,
        Material.LIGHT_BLUE_SHULKER_BOX,
        Material.YELLOW_SHULKER_BOX,
        Material.LIME_SHULKER_BOX,
        Material.PINK_SHULKER_BOX,
        Material.GRAY_SHULKER_BOX,
        Material.LIGHT_GRAY_SHULKER_BOX,
        Material.CYAN_SHULKER_BOX,
        Material.PURPLE_SHULKER_BOX,
        Material.BLUE_SHULKER_BOX,
        Material.BROWN_SHULKER_BOX,
        Material.GREEN_SHULKER_BOX,
        Material.RED_SHULKER_BOX,
        Material.BLACK_SHULKER_BOX
    );

    public EndermenSpawnWithShulkerModule(double chance) {
        this.chance = chance;
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void addShulkerShellToEnderman(EntitySpawnEvent event) {
        if (!(event.getEntity() instanceof Enderman enderman)) return;
        if (ThreadLocalRandom.current().nextDouble() > chance) return;
        Material material = shulkers.stream()
            .skip(ThreadLocalRandom.current().nextInt(shulkers.size()))
            .findFirst()
            .orElse(Material.SHULKER_BOX);
        enderman.setCarriedBlock(material.createBlockData());
        enderman.setCanPickupItems(false);
        enderman.getPersistentDataContainer().set(shulkyKey, PersistentDataType.BOOLEAN, true);
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onEndermanPlaceBlock(EntityChangeBlockEvent event) {
        if (!(event.getEntity() instanceof Enderman enderman)) return;
        if (enderman.getCarriedBlock() == null || enderman.getCarriedBlock().getMaterial() == Material.AIR) return;
        if (!enderman.getPersistentDataContainer().has(shulkyKey, PersistentDataType.BOOLEAN)) return;
        if (!shulkers.contains(enderman.getCarriedBlock().getMaterial())) return;
        event.setCancelled(true);
    }

}
