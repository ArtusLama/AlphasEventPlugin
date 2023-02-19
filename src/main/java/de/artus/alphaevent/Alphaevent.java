package de.artus.alphaevent;

import de.artus.alphaevent.commands.startCommand;
import de.artus.alphaevent.commands.stopCommand;
import de.artus.alphaevent.events.*;
import de.artus.alphaevent.logic.Checkpoints;
import de.artus.alphaevent.logic.Game;
import de.artus.alphaevent.logic.PlayerTimer;
import de.artus.alphaevent.scoreboard.PlayerScoreboard;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;



public final class Alphaevent extends JavaPlugin {

    @Override
    public void onEnable() {

        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new JoinQuitHandler(), this);
        pluginManager.registerEvents(new DieEvent(), this);
        pluginManager.registerEvents(new CheckpointListener(), this);
        pluginManager.registerEvents(new DisableDamage(), this);
        pluginManager.registerEvents(new DisableFood(), this);

        getCommand("startGame").setExecutor(new startCommand());
        getCommand("stopGame").setExecutor(new stopCommand());

        Game.setup();
        Checkpoints.setup();


        Bukkit.getOnlinePlayers().forEach(PlayerScoreboard::setScoreboard);
        new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.getOnlinePlayers().forEach(PlayerScoreboard::updateScoreBoard);
            }
        }.runTaskTimer(this, 10, 10);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
