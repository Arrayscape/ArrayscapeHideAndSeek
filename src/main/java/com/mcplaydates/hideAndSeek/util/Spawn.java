package com.mcplaydates.hideAndSeek.util;

import com.mcplaydates.hideAndSeek.HideAndSeek;
import org.bukkit.Location;

public class Spawn {
    HideAndSeek hs;
    Config config;

    public Spawn(HideAndSeek hs, Config config){
        this.hs = hs;
        this.config = config;
    }

    public void setLocation(Location location){
        config.setConfig("spawn", location);
    }

    public Location checkForSpawn(){
        return (Location) config.getConfig("spawn");
    }

    public void eraseSpawn(){
        config.setConfig("spawn", null);
    }
}
