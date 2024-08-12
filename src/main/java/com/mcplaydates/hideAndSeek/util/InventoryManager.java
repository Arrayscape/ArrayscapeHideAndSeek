package com.mcplaydates.hideAndSeek.util;

import com.mcplaydates.hideAndSeek.HideAndSeek;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

public class InventoryManager {
    HideAndSeek hs;
    HashMap<String, ItemStack[]> playerInventories = new HashMap<>(); // links player name to inventory
    Config config;
    public InventoryManager(HideAndSeek hs, Config config){
        this.hs = hs;
        this.config = config;
    }

    public void getInventory(Player player){
        playerInventories.put(player.getName(), player.getInventory().getContents());
    }

    public void giveBackInventory(String playerName){
        if(!playerInventories.containsKey(playerName)) // They do not have an inventory saved.
           return;

        Bukkit.getPlayer(playerName).getInventory().setContents(playerInventories.get(playerName));
        playerInventories.remove(playerName);
    }

    public void giveSeekerInventory(Player player){
        ArrayList<ItemStack> seekerItems = null;
        try{
            seekerItems = (ArrayList<ItemStack>) config.getConfigList("seekeritems");
        }catch (Exception e){

        }
        if(seekerItems == null)
            return;

        for(int i = 0; i < seekerItems.size(); i++){
            player.getInventory().setItem(i, seekerItems.get(i));
        }
    }
}
