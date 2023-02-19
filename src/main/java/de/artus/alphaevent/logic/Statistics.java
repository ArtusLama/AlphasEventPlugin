package de.artus.alphaevent.logic;

import de.artus.alphaevent.utils.Chat;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Statistics {

    public static Map<UUID, Integer> deaths = new HashMap<>();

    public static void addDeath(Player player) {
        deaths.put(player.getUniqueId(), deaths.getOrDefault(player.getUniqueId(), 0) + 1);
        onStatisticChange("Deaths:" + deaths.get(player.getUniqueId()), player);
    }

    public static void onStatisticChange(String msg, Player player) {
        //Chat.sendMessageToAll(player + " -> " + msg, true);
    }


    public static void reset() {
        deaths.clear();
    }
}
