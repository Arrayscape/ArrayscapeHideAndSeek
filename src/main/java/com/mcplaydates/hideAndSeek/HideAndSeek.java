package com.mcplaydates.hideAndSeek;

import com.mcplaydates.hideAndSeek.util.*;
import com.mcplaydates.hideAndSeek.util.Listeners.EntityHitListener;
import com.mcplaydates.hideAndSeek.util.Listeners.MoveListener;
import com.mcplaydates.hideAndSeek.util.Listeners.PlayerLeaveListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class HideAndSeek extends JavaPlugin {

    @Override
    public void onEnable() {
        Config config = new Config(this);
        SpawnAndLobby spawnAndLobby = new SpawnAndLobby(this, config);
        HSScoreboard scoreboard = new HSScoreboard(this);
        Start start = new Start(this, scoreboard, spawnAndLobby);
        Game game = new Game(this, start);
        End end = new End(start, game, scoreboard, spawnAndLobby);
        start.setGame(game);

        Border border = new Border(this, config);


        this.getServer().getPluginManager().registerEvents(new MoveListener(this, border, game, start), this);
        this.getServer().getPluginManager().registerEvents(new EntityHitListener(this, start, game, end), this);
        this.getServer().getPluginManager().registerEvents(new PlayerLeaveListener(game, end), this);
        this.getCommand("hs").setTabCompleter(new TabComplete());
        this.getCommand("hs").setExecutor(new CommandHs(start, game, end, border, spawnAndLobby));
    }

    @Override
    public void onDisable() {
    }
}
