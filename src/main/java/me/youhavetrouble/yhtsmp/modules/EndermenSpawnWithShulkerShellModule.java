package me.youhavetrouble.yhtsmp.modules;

import java.util.concurrent.ThreadLocalRandom;
import org.bukkit.Material;
import org.bukkit.entity.Enderman;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.inventory.ItemStack;

public class EndermenSpawnWithShulkerShellModule implements Listener {
    private final double chance;

    public EndermenSpawnWithShulkerShellModule(double chance) {
        this.chance = chance;
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void addShulkerShellToEnderman(EntitySpawnEvent event) {
        if (!(event.getEntity() instanceof Enderman enderman)) return;
        if (ThreadLocalRandom.current().nextDouble() < chance) return;
        enderman.setCarriedBlock(null);
        enderman.clearActiveItem();
        enderman.setCanPickupItems(false);
        enderman.getEquipment().setItemInMainHand(new ItemStack(Material.SHULKER_SHELL));
    }

}