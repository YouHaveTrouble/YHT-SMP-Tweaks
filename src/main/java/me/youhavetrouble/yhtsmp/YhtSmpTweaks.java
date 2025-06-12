package me.youhavetrouble.yhtsmp;

import me.youhavetrouble.yhtsmp.modules.EndermenSpawnWithShulkerModule;
import me.youhavetrouble.yhtsmp.modules.MakeItemsEnchantableModule;
import me.youhavetrouble.yhtsmp.modules.MakeItemsRepairableModule;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

@SuppressWarnings("UnstableApiUsage")
public final class YhtSmpTweaks extends JavaPlugin {

    @Override
    public void onEnable() {
        double endermenWithShulkerShellChance = YhtConfig.instance.getDouble("endermen-spawn-with-shulker-chance", 0);
        if (endermenWithShulkerShellChance > 0) {
            getServer().getPluginManager().registerEvents(new EndermenSpawnWithShulkerModule(endermenWithShulkerShellChance), this);
        }

        List<ItemType> enchantableItemTypes = YhtConfig.instance.getItemTypeList("enchantable-items", List.of());
        if (!enchantableItemTypes.isEmpty()) {
            getServer().getPluginManager().registerEvents(new MakeItemsEnchantableModule(enchantableItemTypes), this);
        }

        ConfigurationSection repairableItemsSection = YhtConfig.instance.getConfigurationSection("repairable-items");
        if (repairableItemsSection != null && !repairableItemsSection.getKeys(false).isEmpty()) {
            getServer().getPluginManager().registerEvents(new MakeItemsRepairableModule(repairableItemsSection), this);
        }

        YhtConfig.instance.save();
    }

}
