package com.mcplaydates.hideAndSeek.util;

import com.mcplaydates.hideAndSeek.HideAndSeek;
import org.bukkit.Location;

public class Border {
    HideAndSeek hs;
    int minX;
    int maxX;
    int minZ;
    int maxZ;
    boolean isTrackingBorder = false;
    Config config;
    public Border(HideAndSeek hs, Config config){
        this.hs = hs;
        this.config = config;
    }

    public void setLocation(int numCorner, Location location){
        if(numCorner == 1){
            config.setConfig("corner1.x", location.getBlockX());
            config.setConfig("corner1.z", location.getBlockZ());
        }else if(numCorner == 2){
            config.setConfig("corner2.x", location.getBlockX());
            config.setConfig("corner2.z", location.getBlockZ());
        }
    }

    public void eraseBorder(){
        config.setConfig("corner1.x", null);
        config.setConfig("corner1.z", null);
        config.setConfig("corner2.x", null);
        config.setConfig("corner2.z", null);
        isTrackingBorder = false;
    }

    public void startBorder(){
        // Check if border exists.
        if(config.getConfig("corner1.x") != null && config.getConfig("corner1.z") != null &&
                config.getConfig("corner2.x") != null & config.getConfig("corner2.z") != null){
            isTrackingBorder = true;
            minX = Math.min((int) config.getConfig("corner1.x"), (int) config.getConfig("corner2.x"));
            maxX = Math.max((int) config.getConfig("corner1.x"), (int) config.getConfig("corner2.x"));
            minZ = Math.min((int) config.getConfig("corner1.z"), (int) config.getConfig("corner2.z"));
            maxZ = Math.max((int) config.getConfig("corner1.z"), (int) config.getConfig("corner2.z"));
        }
    }

    public boolean isInBorder(Location playerLocation){
        return playerLocation.getBlockX() >= minX && playerLocation.getBlockX() <= maxX &&
                playerLocation.getBlockZ() >= minZ && playerLocation.getBlockZ() <= maxZ;

    }

    public boolean getTrackingBorder(){
        return isTrackingBorder;
    }
}
