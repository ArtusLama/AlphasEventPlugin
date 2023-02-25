package de.artus.alphaevent.logic;

import de.artus.alphaevent.utils.Chat;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Checkpoints {

    public static Map<UUID, Integer> checkpoints = new HashMap<>();
    public static Map<Integer, Location> checkpointLocations = new HashMap<>();


    public static void setup() {
        checkpointLocations.put(1, new Location(Game.world, 8, -60, 8, 90, 0));
        checkpointLocations.put(2, new Location(Game.world, -38, -50, 8, 90, 0));
        checkpointLocations.put(3, new Location(Game.world, -108, -49, 8, 90, 0));
        checkpointLocations.put(4, new Location(Game.world, -117, -29, 8, 90, 0));
        checkpointLocations.put(5, new Location(Game.world, -123, -55, 8, 90, 0));
        checkpointLocations.put(6, new Location(Game.world, -166, -52, 8, 90, 0));
        checkpointLocations.put(7, new Location(Game.world, -207, -41, 14, 90, 0));
        checkpointLocations.put(8, new Location(Game.world, -210, -29, 10, 90, 0));
        checkpointLocations.put(9, new Location(Game.world, -210, -25, 19, 90, 0));
        checkpointLocations.put(10, new Location(Game.world, -210, -25, 36, 90, 0));


    }
    public static void resetAll() {
        checkpoints = new HashMap<>();
    }

    public static Location getLastCheckpoint(Player player) {
        //Chat.sendMessageToAll("Checkpoint of " + player.getName() + " is " + checkpoints.getOrDefault(player.getUniqueId(), 0), true);
        return checkpointLocations.get(checkpoints.getOrDefault(player.getUniqueId(), 1));
    }
    public static Location getNextCheckpoint(Player player) {
        return checkpointLocations.get(checkpoints.getOrDefault(player.getUniqueId(), 0) + 1);
    }
    public static void gotCheckpoint(Player player) {
        checkpoints.put(player.getUniqueId(), checkpoints.getOrDefault(player.getUniqueId(), 0) + 1);
        Chat.sendMessage(player, "You got the checkpoint!", true);
    }
}
