package com.mcplaydates.hideAndSeek.util.Listeners;

import com.mcplaydates.hideAndSeek.HideAndSeek;
import com.mcplaydates.hideAndSeek.util.Border;
import com.mcplaydates.hideAndSeek.util.CoreGame.End;
import com.mcplaydates.hideAndSeek.util.CoreGame.Game;
import com.mcplaydates.hideAndSeek.util.CoreGame.Start;

import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityHitListener implements Listener {
    HideAndSeek hs;
    Start start;
    Game game;
    End end;
    Border border;

    public EntityHitListener(HideAndSeek hs, Start start, Game game, End end, Border border) {
        this.hs = hs;
        this.start = start;
        this.game = game;
        this.end = end;
        this.border = border;
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event){
        if(start.getIsHidingPhase()){
            event.setCancelled(true);
            return;
        }
        if(game.getGameRunning()) {
            Player defender = null;
            Player attacker = null;
            if(event.getEntity() instanceof Player){
                defender = (Player) event.getEntity();
            }
            if(event.getDamager() instanceof Player){
                attacker = (Player) event.getDamager();
            } else if(event.getDamager() instanceof Arrow && (((Arrow) event.getDamager()).getShooter() instanceof Player)){
                attacker = (Player) (((Arrow) event.getDamager()).getShooter());
            }

            if(defender == null || attacker == null || !(start.isSeeker(attacker) && start.isHider(defender))){
                event.setCancelled(true);
                return;
            }

            attacker.sendTitle( "You Tagged", defender.getName() + "!", 10, 100, 20);
            defender.sendTitle("Tagged by",attacker.getName() + "!", 10, 100, 20);
            start.setHidertoSeeker(defender);
            end.checkGameOver();

            event.setCancelled(true);
            return;
        }

        if (border.hasBorder()) {
            // game isn't in progress, we disable hits inside the arena, but not elsewhere in the world
            Player defender = null;
            Player attacker = null;
            if(event.getEntity() instanceof Player){
                defender = (Player) event.getEntity();
            }
            if(event.getDamager() instanceof Player){
                attacker = (Player) event.getDamager();
            } else if(event.getDamager() instanceof Arrow && (((Arrow) event.getDamager()).getShooter() instanceof Player)){
                attacker = (Player) (((Arrow) event.getDamager()).getShooter());
            }

            if (defender != null && border.isInBorder(defender.getLocation())) {
                event.setCancelled(true);
                return;
            }
            if (attacker != null && border.isInBorder(attacker.getLocation())) {
                event.setCancelled(true);
                return;
            }
        }
    }
}
