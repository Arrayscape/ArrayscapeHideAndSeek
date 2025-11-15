package com.mcplaydates.hideAndSeek.util;

import com.mcplaydates.hideAndSeek.util.CoreGame.End;
import com.mcplaydates.hideAndSeek.util.CoreGame.Game;
import com.mcplaydates.hideAndSeek.util.CoreGame.Start;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
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
        Player player = null;
        Boolean isOp = false;

        if(sender instanceof Player){
            player = (Player) sender;
            isOp = player.isOp();
        } else if(sender instanceof ConsoleCommandSender){
            isOp = true;
        }

        if (args.length <= 0) {
            return true;
        }

        if(args[0].equalsIgnoreCase("start") && isOp){
            if(start.getOnlinePlayers() <= 1) {
                sender.sendMessage("Cannot start the game. Too Few Players. (Minimum is 2)");
                return true;
            }
            // If game is already running, must end game first or else scoreboard breaks.
            if(start.getIsHidingPhase() || game.getGameRunning()) {
                end.endGame("Game Over");
            }
            if(spawnAndLobby.hasSpawn() && border.hasBorder()) {
                sender.sendMessage("Game Started");
                border.startBorder();
                start.startGame(sender);
            } else {
                sender.sendMessage("cannot start game: missing spawn or border");
            }
            return true;
        }

        if(args[0].equalsIgnoreCase("endgame") && isOp) {
            sender.sendMessage("Ending Game");
            end.endGame("Game Over!");
            return true;
        }

        if(args[0].equalsIgnoreCase("exit")) {
            if (player == null) {
                sender.sendMessage("you're not a player!");
                return true;
            }

            if(!(start.getIsHidingPhase() || game.getGameRunning())){
                player.sendMessage("You are not in a game!");
                return true;
            }

            start.removePlayer(player);
            end.checkGameOver();
            player.sendMessage("You have exited the game");

            return true;
        }

        if(args[0].equalsIgnoreCase("endhidetime") && isOp) {
            if(!start.getIsHidingPhase()) {
                sender.sendMessage("It is not hiding phase!");
                return true;
            }

            game.endHideTime();
            sender.sendMessage("Ended hide time.");

            return true;
        }

        if(args[0].equalsIgnoreCase("addhidetime")) {
            if(!start.getIsHidingPhase()) {
                sender.sendMessage("It is not hiding phase!");
                return true;
            }

            game.addHideTime();
            sender.sendMessage("Added 10 seconds!");

            return true;
        }

        // all admin commends must be executed by a player with op
        if (player == null || !isOp || !args[0].equalsIgnoreCase("admin") ) {
            return true;
        }

        if(args.length > 1 && args[1].equalsIgnoreCase("setcorner1")) {
            border.setLocation(1, player.getLocation());
            player.sendMessage("Corner 1 Set!");

            return true;
        }

        if(args.length > 1 && args[1].equalsIgnoreCase("setcorner2")) {
            border.setLocation(2, player.getLocation());
            player.sendMessage("Corner 2 Set!");

            return true;

        }

        if(args.length > 1 && args[1].equalsIgnoreCase("eraseborder")) {
            border.eraseBorder();
            player.sendMessage("Border has been erased!");

            return true;
        }

        if(args.length > 1 && args[1].equalsIgnoreCase("setspawn")) {
            border.startBorder();

            if(border.hasBorder() && !border.isInBorder(player.getLocation())) {
                player.sendMessage("Cannot set spawn here. You are not in the border.");
                return true;
            }

            spawnAndLobby.setSpawnLocation(player.getLocation());

            player.sendMessage("Spawn Set!");
            return true;
        }

        if(args.length > 1 && args[1].equalsIgnoreCase("erasespawn")) {
            spawnAndLobby.eraseSpawn();
            player.sendMessage("Spawn has been erased!");

            return true;
        }

        if(args.length > 1 && args[1].equalsIgnoreCase("setlobby")) {
            spawnAndLobby.setLobbyLocation(player.getLocation());
            player.sendMessage("Lobby Set!");

            return true;
        }

        if(args.length > 1 && args[1].equalsIgnoreCase("eraselobby")) {
            spawnAndLobby.eraseLobby();
            player.sendMessage("Lobby has been erased!");

            return true;
        }

        if(args.length > 1 && args[1].equalsIgnoreCase("sethiderinventory")) {
            inventoryManager.setHiderInventory(player);
            player.sendMessage("You have set hider's inventory!");
            return true;
        }

        if(args.length > 1 && args[1].equalsIgnoreCase("setseekerinventory")) {
            inventoryManager.setSeekerInventory(player);
            player.sendMessage("You have set seeker's inventory!");
            return true;
        }

        if(args.length > 1 && args[1].equalsIgnoreCase("setseektime")) {
            try {
                int time = Integer.parseInt(args[2]);
                config.setConfig("seekingtime", time);
                player.sendMessage("You have set the seeking time to " + time + " seconds!");
            } catch (Exception e){
                player.sendMessage("Not an integer!");
            }

            return true;
        }

        if(args.length > 1 && args[1].equalsIgnoreCase("sethidetime")) {
            try {
                int time = Integer.parseInt(args[2]);
                config.setConfig("hidingtime", time);
                player.sendMessage("You have set the hiding time to " + time + " seconds!");
            } catch (Exception e){
                player.sendMessage("Not an integer!");
            }

            return true;
        }

        return true;
    }
}
