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

    /**
     * Saves the inventory of the player before the game starts.
     * @param player
     *  The player in whose inventory is being saved.
     */
    public void getInventory(Player player){
        playerInventories.put(player.getName(), player.getInventory().getContents());
    }

    /**
     * Saves the inventory of the player to ItemConfig.yml for seekers
     * @param player
     *  The player whose inventory is being saved.
     */
    public void setSeekerInventory(Player player){
        config.setItemConfig("seekeritems", player.getInventory().getContents());
    }

    /**
     * Saves the inventory of the player to ItemConfig.yml for Hiders
     * @param player
     *  The player whose inventory is being saved.
     */
    public void setHiderInventory(Player player){
        config.setItemConfig("hideritems", player.getInventory().getContents());
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
            seekerItems = (ArrayList<ItemStack>) config.getCustomConfigList(("seekeritems"));
            System.out.println("Seek"+seekerItems);
        }catch (Exception e){
            System.out.println(e);
        }
        if(seekerItems == null)
            return;

        for(int i = 0; i < seekerItems.size(); i++){
            player.getInventory().setItem(i, seekerItems.get(i));
        }
    }

    public void giveHiderInventory(Player player){
        ArrayList<ItemStack> hiderItems = null;
        try{
            hiderItems = (ArrayList<ItemStack>) config.getCustomConfigList("hideritems");
            System.out.println("hide"+hiderItems);
        }catch (Exception e){
            System.out.println(e);
        }
        if(hiderItems == null)
            return;

        for(int i = 0; i < hiderItems.size(); i++){
            player.getInventory().setItem(i, hiderItems.get(i));
        }
    }
}
