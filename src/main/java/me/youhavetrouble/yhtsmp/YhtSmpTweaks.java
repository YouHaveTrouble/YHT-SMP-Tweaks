package me.youhavetrouble.yhtsmp;

import me.youhavetrouble.yhtsmp.modules.EndermenSpawnWithShulkerShellModule;
import me.youhavetrouble.yhtsmp.modules.MakeItemsEnchantableModule;
import org.bukkit.inventory.ItemType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

@SuppressWarnings("UnstableApiUsage")
public final class YhtSmpTweaks extends JavaPlugin {

    @Override
    public void onEnable() {
        double endermenWithShulkerShellChance = YhtConfig.instance.getDouble("endermen-spawn-with-shulker-shell-chance", 0);
        if (endermenWithShulkerShellChance > 0) {
            getServer().getPluginManager().registerEvents(new EndermenSpawnWithShulkerShellModule(endermenWithShulkerShellChance), this);
        }

        List<ItemType> enchantableItemTypes = YhtConfig.instance.getItemTypeList("enchantable-items", List.of());
        if (!enchantableItemTypes.isEmpty()) {
            getServer().getPluginManager().registerEvents(new MakeItemsEnchantableModule(enchantableItemTypes), this);
        }

        YhtConfig.instance.save();
    }

}
