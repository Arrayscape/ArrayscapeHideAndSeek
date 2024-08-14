package com.mcplaydates.hideAndSeek.util;

import com.mcplaydates.hideAndSeek.util.CoreGame.End;
import com.mcplaydates.hideAndSeek.util.CoreGame.Game;
import com.mcplaydates.hideAndSeek.util.CoreGame.Start;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandHs implements CommandExecutor{
    Start start;
    Game game;
    End end;
    Border border;
    SpawnAndLobby spawnAndLobby;
    InventoryManager inventoryManager;
    Config config;

    public CommandHs(Start start, Game game, End end, Border border, SpawnAndLobby spawnAndLobby, InventoryManager inventoryManager, Config config){
        this.start = start;
        this.game = game;
        this.end = end;
        this.border = border;
        this.spawnAndLobby = spawnAndLobby;
        this.inventoryManager = inventoryManager;
        this.config = config;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;

            if(args.length > 0 && args[0].equalsIgnoreCase("start") && player.isOp()){
//                if(Bukkit.getOnlinePlayers().size() <= 1){
//                    player.sendMessage("Cannot start the game. Too Few Players. (Minimum is 2)");
//                    return true;
//                }
                player.sendMessage("Game Started");
                border.startBorder();

                if(spawnAndLobby.checkForSpawn() != null || !border.getTrackingBorder() || border.isInBoarder(player.getLocation())){
                    start.startGame(player);
                }else{
                    player.sendMessage("You're not in the border!");
                }
            }
            if(args.length > 0 && args[0].equalsIgnoreCase("endgame") && player.isOp()){
                player.sendMessage("Ending Game");
                end.endGame("Game Over!");
            }
            if(args.length > 0 && args[0].equalsIgnoreCase("exit")){
                if(!(start.getIsHidingPhase() || game.getGameRunning())){
                    player.sendMessage("You are not in a game!");
                    return true;
                }
                start.removePlayer(player);
                end.checkGameOver();
                player.sendMessage("You have exited the game");
            }
            if(args.length > 1 && args[1].equalsIgnoreCase("setcorner1") && player.isOp()){
                border.setLocation(1, player.getLocation());
                player.sendMessage("Corner 1 Set!");
            }
            if(args.length > 1 && args[1].equalsIgnoreCase("setcorner2") && player.isOp()){
                border.setLocation(2, player.getLocation());
                player.sendMessage("Corner 2 Set!");
            }
            if(args.length > 1 && args[1].equalsIgnoreCase("eraseborder") && player.isOp()){
                border.eraseBoarder();
                player.sendMessage("Border has been erased!");
            }
            if(args.length > 1 && args[1].equalsIgnoreCase("setspawn") && player.isOp()){
                if(!border.isInBoarder(player.getLocation())){
                    player.sendMessage("Cannot set spawn here. You are not in the border.");
                    return true;
                }
                spawnAndLobby.setSpawnLocation(player.getLocation());
                player.sendMessage("Spawn Set!");
            }
            if(args.length > 1 && args[1].equalsIgnoreCase("erasespawn") && player.isOp()){
                spawnAndLobby.eraseSpawn();
                player.sendMessage("Spawn has been erased!");
            }
            if(args.length > 1 && args[1].equalsIgnoreCase("setlobby") && player.isOp()){
                spawnAndLobby.setLobbyLocation(player.getLocation());
                player.sendMessage("Lobby Set!");
            }
            if(args.length > 1 && args[1].equalsIgnoreCase("eraselobby") && player.isOp()){
                spawnAndLobby.eraseLobby();
                player.sendMessage("Lobby has been erased!");
            }
            if(args.length > 1 && args[1].equalsIgnoreCase("sethiderinventory") && player.isOp()){
                inventoryManager.setHiderInventory(player);
                player.sendMessage("You have set hider's inventory!");
            }
            if(args.length > 1 && args[1].equalsIgnoreCase("setseekerinventory") && player.isOp()){
                inventoryManager.setSeekerInventory(player);
                player.sendMessage("You have set seeker's inventory!");

            }
            if(args.length > 1 && args[1].equalsIgnoreCase("setseektime") && player.isOp()){
                try{
                    int time = Integer.parseInt(args[2]);
                    config.setConfig("seekingtime", time);
                    player.sendMessage("You have set the seeking time to " + time + " seconds!");
                }catch (Exception e){
                    player.sendMessage("Not an integer!");
                }

            }
            if(args.length > 1 && args[1].equalsIgnoreCase("sethidetime") && player.isOp()){
                try{
                    int time = Integer.parseInt(args[2]);
                    config.setConfig("hidingtime", time);
                    player.sendMessage("You have set the hiding time to " + time + " seconds!");
                }catch (Exception e){
                    player.sendMessage("Not an integer!");
                }

            }
        }
        return true;
    }


}
