package com.mcplaydates.hideAndSeek.util;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandHs implements CommandExecutor{
    Start start;
    Game game;
    End end;
    Border border;

    public CommandHs(Start start, Game game, End end, Border border){
        this.start = start;
        this.game = game;
        this.end = end;
        this.border = border;

    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;

            if(args.length > 0 && args[0].equalsIgnoreCase("start") && player.isOp()){
                player.sendMessage("Game Started");
                game.setStartLocation(player.getLocation());
                border.startBorder();

                if(!border.getTrackingBorder() || border.isInBoarder(player.getLocation())){
                    start.startGame();
                }else{
                    player.sendMessage("You're not in the border!");
                }
            }
            if(args.length > 0 && args[0].equalsIgnoreCase("endgame") && player.isOp()){
                if(start.getIsHidingPhase())
                    player.sendMessage("Cannot end the game in hiding phase. Please wait until after.");
                else{
                    player.sendMessage("Ending Game");
                    end.endGame();
                }
            }
            if(args.length > 0 && args[0].equalsIgnoreCase("exit")){
                if(start.getIsHidingPhase() || game.getGameRunning()){
                    start.removePlayer(player);
                    end.checkGameOver();
                    player.sendMessage("You have exited the game");
                }else{
                    player.sendMessage("You are not in a game!");
                }

            }
            if(args.length > 0 && args[0].equalsIgnoreCase("setcorner1") && player.isOp()){
                border.setLocation(1, player.getLocation());
                player.sendMessage("Corner 1 Set!");
            }
            if(args.length > 0 && args[0].equalsIgnoreCase("setcorner2") && player.isOp()){
                border.setLocation(2, player.getLocation());
                player.sendMessage("Corner 2 Set!");
            }
            if(args.length > 0 && args[0].equalsIgnoreCase("eraseborder") && player.isOp()){
                border.eraseBoarder();
                player.sendMessage("Border has been erased!");
            }
        }
        else{
            if(args.length > 0 && args[0].equalsIgnoreCase("start")){
                game.setStartLocation(Bukkit.getOnlinePlayers().iterator().next().getLocation());
                start.startGame();
            }
            if(args.length > 0 && args[0].equalsIgnoreCase("end")){
                end.endGame();

            }

        }


        return true;
    }


}
