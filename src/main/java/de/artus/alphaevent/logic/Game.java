package de.artus.alphaevent.logic;

import de.artus.alphaevent.Alphaevent;
import de.artus.alphaevent.scoreboard.PlayerScoreboard;
import de.artus.alphaevent.utils.BarrierLoader;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Game {


    public static boolean running = false;
    public static World world;

    public static Map<UUID, PlayerTimer> playerTimers = new HashMap<>();


    public static void setup() {
        world = Bukkit.getWorld("world");
    }


    public static void stop() {
        running = false;
        setup();
        Checkpoints.setup();
        playerTimers.clear();
        Statistics.reset();
        Bukkit.getOnlinePlayers().forEach(PlayerScoreboard::setScoreboard);
        Checkpoints.resetAll();

        Bukkit.getOnlinePlayers().forEach(p -> {
            p.teleport(new Location(p.getWorld(), 90.5, -59.5, 7.5, 90, 0));
        });

        BarrierLoader.setBarrier(Checkpoints.checkpointLocations.get(1));
    }

    public static void startGame() {
        Location startLocation = new Location(world, 8.5, -60, 8.5, 90, 0);
        Bukkit.getOnlinePlayers().forEach(p -> p.teleport(startLocation));
        BarrierLoader.removeBarrier(Checkpoints.checkpointLocations.get(1));
        running = true;
    }

    public static void startCountdown() {

        BarrierLoader.setBarrier(Checkpoints.checkpointLocations.get(1));
        Location startLocation = new Location(world, 8.5, -60, 8.5, 90, 0);
        Bukkit.getOnlinePlayers().forEach(p -> p.teleport(startLocation));

        new BukkitRunnable() {

            int count = 10;

            @Override
            public void run() {
                Title title = Title.title(Component.text(ChatColor.GREEN.toString() + count),
                        Component.text(""), Title.Times.times(Duration.ofMillis(100), Duration.ofMillis(700), Duration.ofMillis(100)));

                Bukkit.getOnlinePlayers().forEach(p -> p.showTitle(title));
                count--;
                if (count <= 0) {
                    startGame();
                    cancel();
                }
            }
        }.runTaskTimer(Alphaevent.getPlugin(Alphaevent.class), 20, 20);
    }


}
