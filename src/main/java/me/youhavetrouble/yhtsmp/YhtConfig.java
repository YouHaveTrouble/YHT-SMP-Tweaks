package me.youhavetrouble.yhtsmp;

import net.kyori.adventure.key.Key;
import org.bukkit.Registry;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemType;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("UnstableApiUsage")
public class YhtConfig {

    public static YhtConfig instance = null;

    private final File configFile;
    private final FileConfiguration configuration;

    public YhtConfig(File configFile) {
        if (instance != null) {
            throw new IllegalStateException("YhtConfig instance already exists!");
        }
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
            } catch (Exception e) {
                throw new RuntimeException("Failed to create config file: " + configFile.getAbsolutePath(), e);
            }
        }
        this.configFile = configFile;
        instance = this;
        configuration = YamlConfiguration.loadConfiguration(configFile);
    }

    public void save() {
        try {
            configuration.save(configFile);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save config file: " + configFile.getAbsolutePath(), e);
        }
    }

    public ConfigurationSection getConfigurationSection(@NotNull String path) {
        if (!configuration.isConfigurationSection(path)) {
            configuration.createSection(path);
        }
        return configuration.getConfigurationSection(path);
    }

    public double getDouble(@NotNull String path, double def) {
        if (!configuration.isSet(path)) configuration.set(path, def);
        return configuration.getDouble(path, def);
    }

    public List<String> getStringList(@NotNull String path, List<String> def) {
        if (!configuration.isSet(path)) configuration.set(path, def);
        return configuration.getStringList(path);
    }

    public List<ItemType> getItemTypeList(@NotNull String path, List<ItemType> def) {
        List<String> defaultMaterialNames = def.stream()
                .map(itemType -> itemType.getKey().toString())
                .toList();
        List<String> stringList = getStringList(path, defaultMaterialNames);
        List<ItemType> itemTypes = new ArrayList<>();
        stringList.forEach(itemTypeKey -> {
            Key key = Key.key(itemTypeKey);
            ItemType itemType = Registry.ITEM.get(key);
            if (itemType == null) return;
            itemTypes.add(itemType);
        });
        return itemTypes;
    }

}
