package me.youhavetrouble.yhtsmp;

import me.youhavetrouble.yhtsmp.modules.EndermenSpawnWithShulkerShellModule;
import me.youhavetrouble.yhtsmp.modules.YhtSmpCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public final class YhtSmpTweaks extends JavaPlugin {

    @Override
    public void onEnable() {
        reloadSmpConfig();
        getServer().getCommandMap().register("yhtsmptweaks", new YhtSmpCommand(this));
    }



    public void reloadSmpConfig() {
        HandlerList.unregisterAll(this);

        saveDefaultConfig();
        reloadConfig();
        FileConfiguration config = getConfig();

        double endermenWithShulkerShellChance = config.getDouble("endermen-spawn-with-shulker-shell-chance", 0);
        if (endermenWithShulkerShellChance != 0) {
            getServer().getPluginManager().registerEvents(new EndermenSpawnWithShulkerShellModule(endermenWithShulkerShellChance), this);
        }
    }
}
