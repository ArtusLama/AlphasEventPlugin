package de.artus.alphaevent.logic;

import de.artus.alphaevent.utils.Chat;
import de.artus.alphaevent.utils.PlayerInventory;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Statistics {

    public static Map<UUID, Integer> deaths = new HashMap<>();
    public static Map<UUID, Integer> ranks = new HashMap<>();
    private static List<Map.Entry<PlayerTimer, Integer>> calcRanks = new ArrayList<>();

    public static void addDeath(Player player) {
        deaths.put(player.getUniqueId(), deaths.getOrDefault(player.getUniqueId(), 0) + 1);
        onStatisticChange("Deaths:" + deaths.get(player.getUniqueId()), player);
    }

    public static void onStatisticChange(String msg, Player player) {
        //Chat.sendMessageToAll(player + " -> " + msg, true);
    }


    public static List<Map.Entry<PlayerTimer, Integer>> getRanks() {
        calculateRanks();
        return calcRanks;
    }
    public static void calculateRanks() {
        ranks.clear();
        calcRanks.clear();

        if (!Game.running) return;

        Map<PlayerTimer, Integer> rankMap = new HashMap<>();
        Bukkit.getOnlinePlayers().forEach(player -> {
            int checkpoint = Checkpoints.checkpoints.getOrDefault(player.getUniqueId(), 0);
            PlayerTimer time = Game.playerTimers.get(player.getUniqueId());
            if (time != null) rankMap.put(time, checkpoint);
        });

        calcRanks = new ArrayList<>(rankMap.entrySet());
        calcRanks = calcRanks.stream().sorted(
                        Map.Entry.<PlayerTimer, Integer>comparingByValue()
                                .thenComparingLong(e -> e.getKey().getCurrentTime()).reversed())
                .collect(Collectors.toList());


    }




    public static void reset() {
        deaths.clear();
    }
}
