package com.mcplaydates.hideAndSeek.util.Listeners;

import com.mcplaydates.hideAndSeek.util.CoreGame.End;
import com.mcplaydates.hideAndSeek.util.CoreGame.Game;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeaveListener implements Listener {
    Game game;
    End end;
    public PlayerLeaveListener(Game game, End end){
        this.game = game;
        this.end = end;
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event){
        if(game.getGameRunning()){
            end.checkGameOver();
        }
    }
}
