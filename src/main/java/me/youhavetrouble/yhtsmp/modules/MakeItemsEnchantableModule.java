package me.youhavetrouble.yhtsmp.modules;

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.Enchantable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("UnstableApiUsage")
public class MakeItemsEnchantableModule implements Listener {

    private final Set<ItemType> itemTypes = new HashSet<>();

    public MakeItemsEnchantableModule(Collection<ItemType> itemTypes) {
        this.itemTypes.addAll(itemTypes);
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onMendingItemPickup(EntityPickupItemEvent event) {
        makeItemEnchantable(event.getItem().getItemStack());
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onLogin(PlayerJoinEvent event) {
        for (ItemStack itemStack : event.getPlayer().getInventory()) {
            makeItemEnchantable(itemStack);
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onInvClick(InventoryClickEvent event) {
        makeItemEnchantable(event.getCurrentItem());
        makeItemEnchantable(event.getCursor());
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onMakeItemEnchantableInContainer(InventoryOpenEvent event) {
        for (ItemStack itemStack : event.getInventory().getContents()) {
            makeItemEnchantable(itemStack);
        }
    }

    private void makeItemEnchantable(ItemStack item) {
        if (item == null || item.isEmpty()) return;
        if (item.hasData(DataComponentTypes.ENCHANTABLE)) return;
        if (!itemTypes.contains(item.getType().asItemType())) return;
        item.setData(DataComponentTypes.ENCHANTABLE, Enchantable.enchantable(1));
    }

}
