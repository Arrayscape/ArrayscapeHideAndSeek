package com.mcplaydates.hideAndSeek.util;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class TabComplete implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        ArrayList<String> options = new ArrayList<>();
        options.add("start");
        options.add("exit");
        options.add("endgame");

        return options;
    }
}
