package com.mcplaydates.hideAndSeek.util;

import com.mcplaydates.hideAndSeek.HideAndSeek;
import org.bukkit.Location;

public class SpawnAndLobby {
    HideAndSeek hs;
    Config config;

    public SpawnAndLobby(HideAndSeek hs, Config config){
        this.hs = hs;
        this.config = config;
    }

    public void setSpawnLocation(Location location){
        config.setConfig("spawn", location);
    }

    public Location checkForSpawn(){
        return (Location) config.getConfig("spawn");
    }

    public void eraseSpawn(){
        config.setConfig("spawn", null);
    }

    public void setLobbyLocation(Location location){
        config.setConfig("lobby", location);
    }

    public Location checkForLobby(){
        return (Location) config.getConfig("lobby");
    }

    public void eraseLobby(){
        config.setConfig("lobby", null);
    }
}
