package com.mcplaydates.hideAndSeek.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class End {

    Start start;
    Game game;
    HSScoreboard hsScoreboard;
    public End(Start start, Game game, HSScoreboard hsScoreboard){
        this.start = start;
        this.game = game;
        this.hsScoreboard = hsScoreboard;
    }
    public void checkGameOver(){
        if(start.getHiders().isEmpty()){
            endGame();
        }
    }

    public void endGame(){
        if(!start.isHidingPhase && !game.getGameRunning()){
            return;
        }
        start.removeTeams();
        hsScoreboard.disableScoreboard();
        game.setGameRunning(false);
        start.setIsHidingPhase(false);

        Bukkit.getWorlds().get(0).setPVP(start.getPVP()); // returns pvp to original setting.
        for(Player player : Bukkit.getOnlinePlayers()){
            game.clearAllPotionEffects(player);
            player.sendTitle("Game Over!", "Everyone Was Found!", 10, 100, 20);
            player.teleport(start.getStartLocation());
        }
    }
}
