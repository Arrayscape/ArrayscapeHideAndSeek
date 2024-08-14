package com.mcplaydates.hideAndSeek.util;

import com.mcplaydates.hideAndSeek.HideAndSeek;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class Config {
    FileConfiguration file;
    HideAndSeek hs;
    private File itemConfigFile;
    private FileConfiguration itemConfig;

    public Config(HideAndSeek hs){
        this.hs = hs;

        // Creates new config.yml from jar if it does not exist. Does not write over existing config.yml
        hs.saveDefaultConfig();
        file = hs.getConfig();

        createCustomItemConfig();
    }

    public void setConfig(String path, Object value){
        file.set(path, value);
        hs.saveConfig();
    }

    public Object getConfig(String path){
        return file.get(path);
    }

    public Object getConfigList(String path){
        return file.getList(path);
    }

    public Object getCustomConfigList(String path){
        return itemConfig.getList(path);
    }



    private void createCustomItemConfig() {
        itemConfigFile = new File(hs.getDataFolder(), "ItemConfig.yml");
        if (!itemConfigFile.exists()) {
            itemConfigFile.getParentFile().mkdirs();
            hs.saveResource("ItemConfig.yml", false);
        }

        itemConfig = YamlConfiguration.loadConfiguration(itemConfigFile);
    }

    public void setItemConfig(String path, Object value){
        itemConfig.set(path, value);
        try{
            itemConfig.save(itemConfigFile);
            itemConfig = YamlConfiguration.loadConfiguration(itemConfigFile);
        }catch (Exception e){
            hs.getLogger().info("Error. Could not save item config file.");
        }
    }
}
