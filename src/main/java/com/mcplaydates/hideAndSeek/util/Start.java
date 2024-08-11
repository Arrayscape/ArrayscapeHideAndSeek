package com.mcplaydates.hideAndSeek.util;

import com.mcplaydates.hideAndSeek.HideAndSeek;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Start {
    HashMap<String, String> playerTeamMap;
    ArrayList<Player> hiders;
    ArrayList<Player> seekers;
    HideAndSeek hs;
    Game game;

    ScoreboardManager manager;
    Scoreboard board;
    HSScoreboard hsScoreboard;
    Team hiderTeam;
    Team seekerTeam;
    boolean isHidingPhase;
    boolean PVP;
    public Start(HideAndSeek hs, HSScoreboard hsScoreboard){
        this.hs = hs;
        this.hsScoreboard = hsScoreboard;
    }

    public void setGame(Game game){
        this.game = game;
    }
    public void startGame(){
        for(Player player : Bukkit.getOnlinePlayers()){
            game.clearAllPotionEffects(player);
            player.teleport(game.startLocation);
        }
        makeTeams();
        game.hidingPhaseStart();
        hsScoreboard.makeScoreBoard(board);
        hsScoreboard.updateScoreboard(hiders.size(), seekers.size());
    }

    private void makeTeams(){
        playerTeamMap = new HashMap<>();
        hiders = new ArrayList<>();
        seekers = new ArrayList<>();

        manager = Bukkit.getScoreboardManager();
        board = manager.getNewScoreboard();
        hiderTeam = board.registerNewTeam("hider");
        seekerTeam = board.registerNewTeam("seeker");


        seekerTeam.setPrefix(ChatColor.RED + "[Seeker] " + ChatColor.WHITE);
        hiderTeam.setPrefix(ChatColor.BLUE + "[Hider] " + ChatColor.WHITE);
        hiderTeam.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.FOR_OTHER_TEAMS);

        PVP = Bukkit.getWorlds().get(0).getPVP();
        Bukkit.getWorlds().get(0).setPVP(true);



        for(Player player : Bukkit.getOnlinePlayers()){
            hiders.add(player);
        }
        // Choose one hider to be a seeker
        makeRandomSeeker();

        // Put all hiders and seekers into a hashmap and into team.
        for(Player player : hiders){
            hiderTeam.addEntry(player.getName());
            player.setScoreboard(hiderTeam.getScoreboard());
            playerTeamMap.put(player.getName(), "hider");
        }
        for(Player player : seekers){
            seekerTeam.addEntry(player.getName());
            player.setScoreboard(seekerTeam.getScoreboard());
            playerTeamMap.put(player.getName(), "seeker");
        }

    }
    public Player makeRandomSeeker(){
        Random random = new Random();
        int randomPlayer = random.nextInt(hiders.size());
        Player seeker = hiders.get(randomPlayer);
        hiders.remove(seeker);
        seekers.add(seeker);
        seeker.sendMessage("You're the Seeker!");
        return seeker;
    }

    /**
     * Removes a player from the game.
     * @param player
     *  The player to be removed.
     */
    public void removePlayer(Player player){
        game.clearAllPotionEffects(player);
        player.resetTitle();
        hiders.remove(player);
        seekers.remove(player);
        playerTeamMap.remove(player.getName());
        hiderTeam.removeEntry(player.getName());
        seekerTeam.removeEntry(player.getName());
        hsScoreboard.updateScoreboard(hiders.size(), seekers.size());

        // Ensures there will be at least 1 seeker.
        if(seekers.isEmpty() && !hiders.isEmpty()){
            Player seeker = makeRandomSeeker();
            seekerTeam.addEntry(seeker.getName());
            seeker.setScoreboard(seekerTeam.getScoreboard());
            playerTeamMap.put(seeker.getName(), "seeker");
            seeker.teleport(game.startLocation);
            if(isHidingPhase){
                seeker.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 99999, 99));
            }
        }

    }

    public ArrayList<Player> getHiders() {
        return hiders;
    }

    public ArrayList<Player> getSeekers() {
        return seekers;
    }

    public void setIsHidingPhase(boolean isHidingPhase){
        this.isHidingPhase = isHidingPhase;
    }

    public boolean getIsHidingPhase(){
        return isHidingPhase;
    }

    public boolean isHider(Player player){
        return playerTeamMap.get(player.getName()) != null && playerTeamMap.get(player.getName()).equals("hider");
    }

    public boolean isSeeker(Player player){
        return playerTeamMap.get(player.getName()) != null && playerTeamMap.get(player.getName()).equals("seeker");
    }

    public boolean getPVP(){
        return PVP;
    }



    public void setHidertoSeeker(Player player){
        if(!isHidingPhase)
            game.clearAllPotionEffects(player);
       else{
           //tp them to oringal spot
            // blindness / movement (DONE)?
        }
        hiders.remove(player);
        seekers.add(player);
        playerTeamMap.put(player.getName(),"seeker");
        // switch teams
        hiderTeam.removeEntry(player.getName());
        seekerTeam.addEntry(player.getName());
        player.setScoreboard(seekerTeam.getScoreboard());
        hsScoreboard.updateScoreboard(hiders.size(), seekers.size());

        player.sendTitle("You're a Seeker!", "Search for people!", 10, 100, 20);
    }

    public void removeTeams(){
        for(String player : hiderTeam.getEntries()){
            hiderTeam.removeEntry(player);
        }
        for(String player : seekerTeam.getEntries()){
            seekerTeam.removeEntry(player);
        }
    }
}