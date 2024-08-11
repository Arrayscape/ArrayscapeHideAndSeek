package com.mcplaydates.hideAndSeek.util;

import com.mcplaydates.hideAndSeek.HideAndSeek;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Game {
    HideAndSeek hs;
    Start start;
    final int HIDINGTIME = 60; // in seconds
    boolean gameRunning = false;
    public Game(HideAndSeek hs, Start start){
        this.hs = hs;
        this.start = start;
    }

    public void hidingPhaseStart(){
        start.setIsHidingPhase(true);
        for(Player player : start.getSeekers()){
            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, HIDINGTIME*20, 5));
            player.sendTitle("You are a Seeker!", "Close your eyes for 60 seconds", 10, HIDINGTIME*20, 20);
        }
        for(Player player : start.getHiders()){
            player.sendTitle("Quick, Hide!", " You have " + HIDINGTIME + " seconds", 10, 100, 20);
        }
        // Starts the eventListener to cancel movement for seekers
        gameRunning = true;

        // Calls hidingPhaseEnd after 60 seconds when HidingPhase should end.
        Bukkit.getScheduler().runTaskLater(hs, new Runnable() {
            @Override
            public void run() {
                hidingPhaseEnd();
            }
        }, HIDINGTIME*20);
    }

    public void hidingPhaseEnd(){
        start.setIsHidingPhase(false);

        for(Player player : start.getSeekers()){
            this.clearAllPotionEffects(player);
            player.sendTitle("Seekers Go!", "Look for people and tag them", 10, 100, 20);
        }
        for(Player player : start.getHiders()){
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, PotionEffect.INFINITE_DURATION, 3));
            player.sendTitle("Ready or Not!", "Make sure you're in a good spot", 10, 100, 20);
        }
    }

    public boolean getGameRunning(){
        return gameRunning;
    }
    public void setGameRunning(boolean gameRunning) {
        this.gameRunning = gameRunning;
    }

    public void clearAllPotionEffects(Player player){
        for(PotionEffect effect:player.getActivePotionEffects()){
            player.removePotionEffect(effect.getType());
        }
    }
}
