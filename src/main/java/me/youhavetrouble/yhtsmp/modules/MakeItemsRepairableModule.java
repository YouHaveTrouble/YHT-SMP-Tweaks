package me.youhavetrouble.yhtsmp.modules;

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.Enchantable;
import io.papermc.paper.datacomponent.item.Repairable;
import io.papermc.paper.registry.RegistryKey;
import io.papermc.paper.registry.TypedKey;
import io.papermc.paper.registry.set.RegistrySet;
import net.kyori.adventure.key.Key;
import org.bukkit.Registry;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("UnstableApiUsage")
public class MakeItemsRepairableModule implements Listener {

    private final Map<ItemType, ItemType> repairItems = new HashMap<>();

    public MakeItemsRepairableModule(ConfigurationSection mapSection) {
        for (String key : mapSection.getKeys(false)) {
            ItemType itemType = Registry.ITEM.get(Key.key(key));
            if (itemType == null) continue;
            String repairKey = mapSection.getString(key);
            ItemType repairItemType = Registry.ITEM.get(Key.key(repairKey));
            if (repairItemType == null) continue;
            repairItems.put(itemType, repairItemType);
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onMendingItemPickup(EntityPickupItemEvent event) {
        makeItemRepairable(event.getItem().getItemStack());
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onLogin(PlayerJoinEvent event) {
        for (ItemStack itemStack : event.getPlayer().getInventory()) {
            makeItemRepairable(itemStack);
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onInvClick(InventoryClickEvent event) {
        makeItemRepairable(event.getCurrentItem());
        makeItemRepairable(event.getCursor());
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onmakeItemRepairableInContainer(InventoryOpenEvent event) {
        for (ItemStack itemStack : event.getInventory().getContents()) {
            makeItemRepairable(itemStack);
        }
    }

    private void makeItemRepairable(ItemStack item) {
        if (item == null || item.isEmpty()) return;
        if (item.hasData(DataComponentTypes.REPAIRABLE)) return;
        ItemType repairingItemType = repairItems.get(item.getType().asItemType());
        if (repairingItemType == null) return;
        TypedKey<ItemType> repairingItemTypeKey = TypedKey.create(RegistryKey.ITEM, repairingItemType.key());
        item.setData(DataComponentTypes.REPAIRABLE, Repairable.repairable(RegistrySet.keySet(RegistryKey.ITEM, repairingItemTypeKey)));
    }

}
