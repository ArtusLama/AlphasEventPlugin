package de.artus.alphaevent.events;

import de.artus.alphaevent.logic.Checkpoints;
import de.artus.alphaevent.logic.Game;
import de.artus.alphaevent.logic.Statistics;
import de.artus.alphaevent.utils.Chat;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class DieEvent implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if (e.getTo().getY() <= -75) {
            Player player = e.getPlayer();
            player.teleport(Checkpoints.getLastCheckpoint(player).toCenterLocation());

            if (Game.running)
                Statistics.addDeath(player);
        }
    }

    @EventHandler
    public void onDie(PlayerDeathEvent e) {
        e.setDeathMessage("");
    }


}
