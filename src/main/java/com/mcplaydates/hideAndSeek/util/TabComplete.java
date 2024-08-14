package com.mcplaydates.hideAndSeek.util;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TabComplete implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender player, Command command, String s, String[] args) {
        ArrayList<String> options = new ArrayList<>();
        if(!player.isOp())
            options = new ArrayList<>(Arrays.asList("exit"));
        else if(args.length == 1)
            options = new ArrayList<>(Arrays.asList("start", "exit", "endgame"));

        else if(args.length == 2 && args[0].equals("admin"))
            options = new ArrayList<>(Arrays.asList("setcorner1", "setcorner2",
                    "eraseborder", "setspawn", "setlobby", "erasespawn", "eraselobby",
                    "sethiderinventory", "setseekerinventory", "setseektime", "sethidetime"));
        return options;
    }
}
