package de.artus.alphaevent.logic;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class PlayerTimer {

    public UUID player;
    Map<Integer, Long> times = new HashMap<>();
    Long startTime = 0L;

    public Long finishedTime = 0L;
    public boolean finished = false;


    public PlayerTimer(Player player) {
        this.player = player.getUniqueId();
    }

    public void startNewTimer() {
        startTime = System.currentTimeMillis();
    }
    public long getCurrentTime() {
        return (System.currentTimeMillis() - startTime) / 1000;
    }
    public void stopCurrentTimer() {
        times.put(times.size(), getCurrentTime());
        startTime = 0L;
    }

    public long getTotalTime() {
        if (finished) return finishedTime;
        long totalTime = getCurrentTime();
        for (Map.Entry<Integer, Long> time : times.entrySet()) {
            totalTime += time.getValue();
        }
        return totalTime;
    }
}
