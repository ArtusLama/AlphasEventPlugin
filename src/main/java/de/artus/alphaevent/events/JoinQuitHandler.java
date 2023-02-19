package de.artus.alphaevent.events;

import de.artus.alphaevent.logic.Checkpoints;
import de.artus.alphaevent.logic.Game;
import de.artus.alphaevent.logic.PlayerTimer;
import de.artus.alphaevent.scoreboard.PlayerScoreboard;
import de.artus.alphaevent.utils.Chat;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinQuitHandler implements Listener {


    @EventHandler
    public void join(PlayerJoinEvent e) {
        Chat.sendMessageToAll(ChatColor.GREEN + e.getPlayer().getName() + ChatColor.GRAY + " joined the server!", true);


        if (Game.running) {
            e.getPlayer().teleport(Checkpoints.getLastCheckpoint(e.getPlayer()));
        } else  {
            e.getPlayer().teleport(new Location(e.getPlayer().getWorld(), 90.5, -59.5, 7.5, 90, 0));
            Game.playerTimers.put(e.getPlayer().getUniqueId(), new PlayerTimer(e.getPlayer()));
        }


        PlayerScoreboard.setScoreboard(e.getPlayer());
        e.getPlayer().setFoodLevel(20);
        e.getPlayer().setHealth(20);
        e.setJoinMessage("");
    }

    @EventHandler
    public void quit(PlayerQuitEvent e) {
        Chat.sendMessageToAll(ChatColor.RED + e.getPlayer().getName() + ChatColor.GRAY + " left the server!", false);
        e.setQuitMessage("");
    }



}
