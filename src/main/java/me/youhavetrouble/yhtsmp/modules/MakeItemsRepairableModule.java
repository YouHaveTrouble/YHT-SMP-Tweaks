package me.youhavetrouble.yhtsmp.modules;

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.Repairable;
import io.papermc.paper.registry.RegistryKey;
import io.papermc.paper.registry.TypedKey;
import io.papermc.paper.registry.set.RegistryKeySet;
import io.papermc.paper.registry.set.RegistrySet;
import me.youhavetrouble.yhtsmp.YhtConfig;
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
import org.jetbrains.annotations.NotNull;

import java.util.*;

@SuppressWarnings("UnstableApiUsage")
public class MakeItemsRepairableModule implements Listener {

    private final Map<ItemType, RegistryKeySet<@NotNull ItemType>> repairItems = new HashMap<>();

    public MakeItemsRepairableModule(ConfigurationSection mapSection) {
        for (String key : mapSection.getKeys(false)) {
            ItemType itemType = Registry.ITEM.get(Key.key(key));
            if (itemType == null) continue;
            Set<TypedKey<ItemType>> repairingItemTypeKeys = new HashSet<>();
            YhtConfig.instance.getItemTypeList("repairable-items." + key, List.of())
                    .forEach(repairingItemType -> repairingItemTypeKeys.add(TypedKey.create(RegistryKey.ITEM, repairingItemType.key())));
            repairItems.put(itemType, RegistrySet.keySet(RegistryKey.ITEM, repairingItemTypeKeys.stream().toList()));
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
        RegistryKeySet<@NotNull ItemType> repairingItemType = repairItems.get(item.getType().asItemType());
        if (repairingItemType == null) return;
        item.setData(DataComponentTypes.REPAIRABLE, Repairable.repairable(repairingItemType));
    }

}
