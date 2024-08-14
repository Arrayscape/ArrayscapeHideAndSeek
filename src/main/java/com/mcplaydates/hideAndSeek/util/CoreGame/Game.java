package com.mcplaydates.hideAndSeek.util.CoreGame;

import com.mcplaydates.hideAndSeek.HideAndSeek;
import com.mcplaydates.hideAndSeek.util.Config;
import com.mcplaydates.hideAndSeek.util.HSScoreboard;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Game {
    Config config;
    HideAndSeek hs;
    Start start;
    End end;
    HSScoreboard hsScoreboard;
    int hidingTime;
    int seekingTime;
    int seekingTimeLeft;
    int hidingTimeLeft;
    boolean gameRunning = false;
    int hidingTaskID;
    int seekingTaskID;
    public Game(HideAndSeek hs, Start start, Config config, HSScoreboard hsScoreboard){
        this.hs = hs;
        this.start = start;
        this.config = config;
        this.hsScoreboard = hsScoreboard;
    }

    public void setEnd(End end){
        this.end = end;
    }

    public void hidingPhaseStart(){
        getTimes();
        hidingTimeLeft = hidingTime;
        seekingTimeLeft = seekingTime;
        start.setIsHidingPhase(true);
        for(Player player : start.getSeekers()){
            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, hidingTime *20, 5));
            player.sendTitle("You are a Seeker!", "Close your eyes for " + hidingTime + " seconds", 10, hidingTime *20, 20);
        }
        for(Player player : start.getHiders()){
            player.sendTitle("Quick, Hide!", " You have " + hidingTime + " seconds", 10, 100, 20);
        }
        // Starts the eventListener to cancel movement for seekers
        gameRunning = true;

        hsScoreboard.updateScoreboardTimer(seekingTime);
        // Schedules repeating timer
        hidingTaskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(hs,
                this::hidingPhaseTimer, 20L, 20);
    }

    private void getTimes(){
        try {
            hidingTime = (int) config.getConfig("hidingtime");
            seekingTime = (int) config.getConfig("seekingtime");
        }catch (Exception e){
            hs.getLogger().info("Error loading times. Setting defaults.");
            hidingTime = 60;
            seekingTime = 300;
        }
    }
    private void hidingPhaseTimer(){
        for(Player player : Bukkit.getOnlinePlayers()){
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("Game starts in " + hidingTimeLeft + " seconds!"));
        }
        hidingTimeLeft--;
        if(hidingTimeLeft <= 0){
            Bukkit.getScheduler().cancelTask(hidingTaskID);
            hidingPhaseEnd();
        }
    }

    private void hidingPhaseEnd(){
        start.setIsHidingPhase(false);

        for(Player player : start.getSeekers()){
            this.clearAllPotionEffects(player);
            player.sendTitle("Seekers Go!", "Look for people and tag them", 10, 100, 20);
        }
        for(Player player : start.getHiders()){
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, PotionEffect.INFINITE_DURATION, 3));
            player.sendTitle("Ready or Not!", "Make sure you're in a good spot", 10, 100, 20);
        }
        seekingTaskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(hs,
                this::seekingPhaseTimer, 20L, 20);
    }

    private void seekingPhaseTimer(){
        seekingTimeLeft--;
        hsScoreboard.updateScoreboardTimer(seekingTimeLeft);

        if(seekingTimeLeft <= 0){
            Bukkit.getScheduler().cancelTask(seekingTaskID);
            end.endGame("Hiders won!");
        }
    }

    public void cancelScheduledTasks(){
        Bukkit.getScheduler().cancelTask(seekingTaskID);
        Bukkit.getScheduler().cancelTask(hidingTaskID);
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
