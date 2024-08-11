package com.mcplaydates.hideAndSeek.util.Listeners;

import com.mcplaydates.hideAndSeek.HideAndSeek;
import com.mcplaydates.hideAndSeek.util.CoreGame.End;
import com.mcplaydates.hideAndSeek.util.CoreGame.Game;
import com.mcplaydates.hideAndSeek.util.CoreGame.Start;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityHitListener implements Listener {
    HideAndSeek hs;
    Start start;
    Game game;
    End end;
    public EntityHitListener(HideAndSeek hs, Start start, Game game, End end){
        this.hs = hs;
        this.start = start;
        this.game = game;
        this.end = end;
    }
    @EventHandler
    public void onHit(EntityDamageByEntityEvent event){
        if(start.getIsHidingPhase()){
            event.setCancelled(true);
            return;
        }
        if(game.getGameRunning()) {
            if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
                Player attacker = (Player) event.getDamager();
                Player defender = (Player) event.getEntity();

                if (start.isSeeker(attacker) && start.isHider(defender)) {
                    start.setHidertoSeeker(defender);
                    end.checkGameOver();
                }
            }
            event.setCancelled(true);
        }
    }
}
