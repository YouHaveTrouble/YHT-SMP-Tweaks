package me.youhavetrouble.yhtsmp;

import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.bootstrap.PluginBootstrap;
import org.jetbrains.annotations.NotNull;

import java.io.File;

@SuppressWarnings("UnstableApiUsage")
public class YhtBootstrapper implements PluginBootstrap {

    @Override
    public void bootstrap(@NotNull BootstrapContext context) {
        File file = context.getDataDirectory().toFile();
        if (!file.exists()) file.mkdirs();
        File configFile = new File(file, "config.yml");
        new YhtConfig(configFile);
    }
}
