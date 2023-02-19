package de.artus.alphaevent.events;

import de.artus.alphaevent.logic.Checkpoints;

import de.artus.alphaevent.logic.Game;
import de.artus.alphaevent.logic.PlayerTimer;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class CheckpointListener implements Listener {


    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if (!Game.running) return;
        Player player = e.getPlayer();
        Location checkpointLoc = Checkpoints.getNextCheckpoint(player);
        if (checkpointLoc == null) {
            return;
        }
        checkpointLoc = checkpointLoc.clone();
        checkpointLoc.setYaw(0);
        checkpointLoc.setPitch(0);

        if (player.getLocation().getBlock().getLocation().equals(checkpointLoc)) {
            if (Game.playerTimers.containsKey(player.getUniqueId())) {
                Game.playerTimers.get(player.getUniqueId()).stopCurrentTimer();
            } else {
                Game.playerTimers.put(player.getUniqueId(), new PlayerTimer(player));
                Game.playerTimers.get(player.getUniqueId()).startNewTimer();
            }
            Game.playerTimers.get(player.getUniqueId()).startNewTimer();
            Checkpoints.gotCheckpoint(player);
        }
    }
}
