package de.artus.alphaevent.events;

import de.artus.alphaevent.logic.Checkpoints;

import de.artus.alphaevent.logic.Game;
import de.artus.alphaevent.logic.PlayerTimer;
import de.artus.alphaevent.utils.Chat;
import de.artus.alphaevent.utils.PlayerInventory;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.time.LocalTime;

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



            Checkpoints.gotCheckpoint(player);

            if (Checkpoints.checkpoints.get(player.getUniqueId()) == 10) {
                PlayerTimer playerTimer = Game.playerTimers.get(player.getUniqueId());
                playerTimer.finishedTime = playerTimer.getTotalTime();
                playerTimer.finished = true;
                playerTimer.stopCurrentTimer();
                Chat.sendMessageToAll(ChatColor.GREEN + player.getName() + ChatColor.GRAY + " finished the JumpAndRun in " + ChatColor.GREEN + LocalTime.MIN.plusSeconds(playerTimer.finishedTime).toString().substring(3) + ChatColor.GRAY + "! GG", true);
                player.setGameMode(GameMode.SPECTATOR);
                PlayerInventory.showPlayers(player);

            } else {
                if (Game.playerTimers.containsKey(player.getUniqueId())) {
                    Game.playerTimers.get(player.getUniqueId()).stopCurrentTimer();
                } else {
                    Game.playerTimers.put(player.getUniqueId(), new PlayerTimer(player));
                    Game.playerTimers.get(player.getUniqueId()).startNewTimer();
                }

                Game.playerTimers.get(player.getUniqueId()).startNewTimer();
            }

        }
    }
}
