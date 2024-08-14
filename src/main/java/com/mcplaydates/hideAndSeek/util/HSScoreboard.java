package com.mcplaydates.hideAndSeek.util;

import com.mcplaydates.hideAndSeek.HideAndSeek;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.*;

public class HSScoreboard {
    HideAndSeek hs;
    Score seekerCount;
    Score hiderCount;
    Score timer;
    Objective obj;
    public HSScoreboard(HideAndSeek hs){
        this.hs = hs;
    }

    public void makeScoreBoard(Scoreboard board){
        obj = board.registerNewObjective("Hide and Seek","Hide and Seek");

        seekerCount = obj.getScore(ChatColor.RED + "» Seekers");
        hiderCount = obj.getScore(ChatColor.BLUE + "» Hiders");
        seekerCount.setScore(0);
        hiderCount.setScore(0);

        timer = obj.getScore(ChatColor.WHITE + "Time Remaining");
        timer.setScore(0);

        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
    }

    public void updateScoreboard(int numHiders, int numSeekers){
        seekerCount.setScore(numSeekers);
        hiderCount.setScore(numHiders);
    }

    public void updateScoreboardTimer(int seconds){
        timer.setScore(seconds);
    }
    public void disableScoreboard(){
        obj.unregister();
    }

}
