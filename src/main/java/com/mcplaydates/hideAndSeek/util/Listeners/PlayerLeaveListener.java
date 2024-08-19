package com.mcplaydates.hideAndSeek.util.Listeners;

import com.mcplaydates.hideAndSeek.util.CoreGame.End;
import com.mcplaydates.hideAndSeek.util.CoreGame.Game;
import com.mcplaydates.hideAndSeek.util.CoreGame.Start;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeaveListener implements Listener {
    Start start;
    Game game;
    End end;
    public PlayerLeaveListener(Start start, Game game, End end){
        this.start = start;
        this.game = game;
        this.end = end;
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event){
        if(game.getGameRunning() || start.getIsHidingPhase()){
            start.removePlayer(event.getPlayer());
            end.checkGameOver();
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){
        start.removePlayer(event.getEntity());
        end.checkGameOver();
    }
}
