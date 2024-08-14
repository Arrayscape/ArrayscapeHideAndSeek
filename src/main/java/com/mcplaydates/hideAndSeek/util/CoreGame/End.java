package com.mcplaydates.hideAndSeek.util.CoreGame;

import com.mcplaydates.hideAndSeek.util.HSScoreboard;
import com.mcplaydates.hideAndSeek.util.InventoryManager;
import com.mcplaydates.hideAndSeek.util.SpawnAndLobby;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class End {

    Start start;
    Game game;
    HSScoreboard hsScoreboard;
    SpawnAndLobby spawnAndLobby;
    InventoryManager inventoryManager;
    public End(Start start, Game game, HSScoreboard hsScoreboard, SpawnAndLobby spawnAndLobby, InventoryManager inventoryManager){
        this.start = start;
        this.game = game;
        this.hsScoreboard = hsScoreboard;
        this.spawnAndLobby = spawnAndLobby;
        this.inventoryManager = inventoryManager;
    }
    public void checkGameOver(){
        if(start.getHiders().isEmpty()){
            endGame("Seekers won!");
        }
    }

    public void endGame(String message){
        if(!start.isHidingPhase && !game.getGameRunning()){
            return;
        }
        game.cancelScheduledTasks();
        start.removeTeams();
        hsScoreboard.disableScoreboard();
        game.setGameRunning(false);
        start.setIsHidingPhase(false);

        Bukkit.getWorlds().get(0).setPVP(start.getPVP()); // returns pvp to original setting.

        Location lobbyLocation = spawnAndLobby.checkForLobby();
        if(lobbyLocation == null)
            lobbyLocation = start.getStartLocation();

        for(Player player : Bukkit.getOnlinePlayers()){
            game.clearAllPotionEffects(player);
            player.sendTitle("Game Over!", message, 10, 100, 20);
            player.teleport(lobbyLocation);
            inventoryManager.giveBackInventory(player.getName());
        }
    }
}
