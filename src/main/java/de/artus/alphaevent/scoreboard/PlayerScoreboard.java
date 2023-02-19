package de.artus.alphaevent.scoreboard;

import de.artus.alphaevent.logic.Checkpoints;
import de.artus.alphaevent.logic.Game;
import de.artus.alphaevent.logic.PlayerTimer;
import de.artus.alphaevent.logic.Statistics;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.time.Duration;
import java.time.LocalTime;

public class PlayerScoreboard {


    public static void setScoreboard(Player player) {

        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective obj = board.registerNewObjective("scoreboard", "dummy", "Event by Alpha");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);




        Team onlineCounter = board.registerNewTeam("onlineCounter");
        onlineCounter.addEntry(ChatColor.GRAY + "» Online: " + ChatColor.BLACK + "" + ChatColor.WHITE);
        onlineCounter.setSuffix("" + ChatColor.GREEN + Bukkit.getOnlinePlayers().size() + ChatColor.GRAY + "/" + ChatColor.GREEN + Bukkit.getMaxPlayers());

        obj.getScore(ChatColor.GRAY + "» Online: " + ChatColor.BLACK + "" + ChatColor.WHITE).setScore(15);

        obj.getScore("").setScore(14);

        Team timer = board.registerNewTeam("timer");
        timer.addEntry(ChatColor.GRAY + "» Time: " + ChatColor.BLACK + "" + ChatColor.RED);
        timer.setSuffix(ChatColor.RED + "✘");

        obj.getScore(ChatColor.GRAY + "» Time: " + ChatColor.BLACK + "" + ChatColor.RED).setScore(13);

        Team totalTime = board.registerNewTeam("totalTime");
        totalTime.addEntry(ChatColor.GRAY + "» TotalTime: " + ChatColor.BLACK + "" + ChatColor.AQUA);
        totalTime.setSuffix(ChatColor.RED + "✘");

        obj.getScore(ChatColor.GRAY + "» TotalTime: " + ChatColor.BLACK + "" + ChatColor.AQUA).setScore(12);


        Team checkpoint = board.registerNewTeam("checkpoint");
        checkpoint.addEntry(ChatColor.GRAY + "» Checkpoint: " + ChatColor.BLACK + "" + ChatColor.BLUE);
        checkpoint.setSuffix(ChatColor.RED + "✘");

        obj.getScore(ChatColor.GRAY + "» Checkpoint: " + ChatColor.BLACK + "" + ChatColor.BLUE).setScore(11);


        Team deaths = board.registerNewTeam("deaths");
        deaths.addEntry(ChatColor.GRAY + "» Deaths: " + ChatColor.BLACK + "" + ChatColor.DARK_PURPLE);
        deaths.setSuffix(ChatColor.RED + "✘");

        obj.getScore(ChatColor.GRAY + "» Deaths: " + ChatColor.BLACK + "" + ChatColor.DARK_PURPLE).setScore(10);


        player.setScoreboard(board);
    }

    public static void updateScoreBoard(Player player) {

        Scoreboard board = player.getScoreboard();

        Team onlineCounter = board.getTeam("onlineCounter");
        if (onlineCounter != null)
            onlineCounter.setSuffix("" + ChatColor.GREEN + Bukkit.getOnlinePlayers().size() + ChatColor.GRAY + "/" + ChatColor.GREEN + Bukkit.getMaxPlayers());

        Team timer = board.getTeam("timer");
        if (timer != null) {
            PlayerTimer playerTimer = Game.playerTimers.get(player.getUniqueId());
            long playerTime = 0;
            if (playerTimer != null) playerTime = playerTimer.getCurrentTime();
            timer.setSuffix(ChatColor.AQUA + "" + LocalTime.MIN.plusSeconds(playerTime));
        }
        Team totalTime = board.getTeam("totalTime");
        if (totalTime != null) {
            PlayerTimer playerTimer = Game.playerTimers.get(player.getUniqueId());
            long playerTime = 0;
            if (playerTimer != null) playerTime = playerTimer.getTotalTime();
            totalTime.setSuffix(ChatColor.AQUA + "" + LocalTime.MIN.plusSeconds(playerTime));
        }

        Team checkpoint = board.getTeam("checkpoint");
        if (checkpoint != null) {
            int playerCheckpoint = Checkpoints.checkpoints.getOrDefault(player.getUniqueId(), 0);
            if (playerCheckpoint == 0)
                checkpoint.setSuffix(ChatColor.RED + "✘");
            else {
                checkpoint.setSuffix(ChatColor.GOLD.toString() + playerCheckpoint);
            }
        }

        Team deaths = board.getTeam("deaths");
        if (deaths != null)
            deaths.setSuffix(ChatColor.RED + "" + Statistics.deaths.getOrDefault(player.getUniqueId(), 0));

    }




}
