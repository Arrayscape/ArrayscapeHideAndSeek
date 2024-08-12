package com.mcplaydates.hideAndSeek.util;

import com.mcplaydates.hideAndSeek.HideAndSeek;
import org.bukkit.configuration.file.FileConfiguration;

public class Config {
    FileConfiguration file;
    HideAndSeek hs;

    public Config(HideAndSeek hs){
        this.hs = hs;

        // Creates new config.yml from jar if it does not exist. Does not write over existing config.yml
        hs.saveDefaultConfig();
        file = hs.getConfig();
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
}
