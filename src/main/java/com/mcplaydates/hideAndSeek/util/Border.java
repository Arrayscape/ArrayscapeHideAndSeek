package com.mcplaydates.hideAndSeek.util;

import com.mcplaydates.hideAndSeek.HideAndSeek;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

public class Border {
    HideAndSeek hs;
    FileConfiguration file;
    int minX;
    int maxX;
    int minZ;
    int maxZ;
    boolean isTrackingBorder = false;
    public Border(HideAndSeek hs){
        this.hs = hs;
        // Creates new config.yml from jar if it does not exist. Does not write over existing config.yml
        hs.saveDefaultConfig();
        file = hs.getConfig();
    }

    public void setLocation(int numCorner, Location location){
        if(numCorner == 1){
            file.set("corner1.x", location.getBlockX());
            file.set("corner1.z", location.getBlockZ());
        }else if(numCorner == 2){
            file.set("corner2.x", location.getBlockX());
            file.set("corner2.z", location.getBlockZ());
        }
        hs.saveConfig();
    }

    public void eraseBoarder(){
        file.set("corner1.x", null);
        file.set("corner1.z", null);
        file.set("corner2.x", null);
        file.set("corner2.z", null);
        isTrackingBorder = false;
        hs.saveConfig();
    }

    public void startBorder(){
        // Check if border exists.
        if(file.get("corner1.x") != null && file.get("corner1.z") != null &&
                file.get("corner2.x") != null & file.get("corner2.z") != null){
            isTrackingBorder = true;
            minX = Math.min((int) file.get("corner1.x"), (int) file.get("corner2.x"));
            maxX = Math.max((int) file.get("corner1.x"), (int) file.get("corner2.x"));
            minZ = Math.min((int) file.get("corner1.z"), (int) file.get("corner2.z"));
            maxZ = Math.max((int) file.get("corner1.z"), (int) file.get("corner2.z"));
        }
    }

    public boolean isInBoarder(Location playerLocation){
        return playerLocation.getBlockX() >= minX && playerLocation.getBlockX() <= maxX &&
                playerLocation.getBlockZ() >= minZ && playerLocation.getBlockZ() <= maxZ;

    }

    public boolean getTrackingBorder(){
        return isTrackingBorder;
    }
}
