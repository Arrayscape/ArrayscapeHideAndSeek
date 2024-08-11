package com.mcplaydates.hideAndSeek.util.Listeners;

import com.mcplaydates.hideAndSeek.HideAndSeek;
import com.mcplaydates.hideAndSeek.util.Border;
import com.mcplaydates.hideAndSeek.util.CoreGame.Game;
import com.mcplaydates.hideAndSeek.util.CoreGame.Start;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MoveListener implements Listener {
    HideAndSeek hs;
    Border border;
    Game game;
    Start start;
    boolean isTrackingBorder = false;
    public MoveListener(HideAndSeek hs, Border border, Game game, Start start){
        this.hs = hs;
        this.border = border;
        this.game = game;
        this.start = start;
    }


    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){
        if(start.getIsHidingPhase() && start.isSeeker(event.getPlayer())){
            event.setCancelled(true);
        }

        if(border.getTrackingBorder() && (game.getGameRunning() || start.getIsHidingPhase())){
            // triggers when the player moves out of border.
            if ((border.isInBoarder(event.getFrom()) && !border.isInBoarder(event.getTo()))) {
                event.setCancelled(true);
            }
        }
    }

}
