package de.artus.alphaevent.scoreboard;

import de.artus.alphaevent.logic.Checkpoints;
import de.artus.alphaevent.logic.Game;
import de.artus.alphaevent.logic.PlayerTimer;
import de.artus.alphaevent.logic.Statistics;
import de.artus.alphaevent.utils.Chat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Map;

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



        Team rank1 = board.registerNewTeam("rank1");
        rank1.addEntry(ChatColor.GRAY + "» Rank 1: " + ChatColor.BLACK + "" + ChatColor.YELLOW);
        rank1.setSuffix(ChatColor.RED + "✘");

        obj.getScore(ChatColor.GRAY + "» Rank 1: " + ChatColor.BLACK + "" + ChatColor.YELLOW).setScore(13);

        Team rank2 = board.registerNewTeam("rank2");
        rank2.addEntry(ChatColor.GRAY + "» Rank 2: " + ChatColor.BLACK + "" + ChatColor.GREEN);
        rank2.setSuffix(ChatColor.RED + "✘");

        obj.getScore(ChatColor.GRAY + "» Rank 2: " + ChatColor.BLACK + "" + ChatColor.GREEN).setScore(12);

        Team rank3 = board.registerNewTeam("rank3");
        rank3.addEntry(ChatColor.GRAY + "» Rank 3: " + ChatColor.BLACK + "" + ChatColor.GOLD);
        rank3.setSuffix(ChatColor.RED + "✘");

        obj.getScore(ChatColor.GRAY + "» Rank 3: " + ChatColor.BLACK + "" + ChatColor.GOLD).setScore(11);



        obj.getScore(" ").setScore(10);



        Team userRank = board.registerNewTeam("userRank");
        userRank.addEntry(ChatColor.GRAY + "» YourRank: " + ChatColor.BLACK + "" + ChatColor.DARK_GRAY);
        userRank.setSuffix(ChatColor.RED + "✘");

        obj.getScore(ChatColor.GRAY + "» YourRank: " + ChatColor.BLACK + "" + ChatColor.DARK_GRAY).setScore(9);




        Team timer = board.registerNewTeam("timer");
        timer.addEntry(ChatColor.GRAY + "» Time: " + ChatColor.BLACK + "" + ChatColor.RED);
        timer.setSuffix(ChatColor.RED + "✘");

        obj.getScore(ChatColor.GRAY + "» Time: " + ChatColor.BLACK + "" + ChatColor.RED).setScore(8);

        Team totalTime = board.registerNewTeam("totalTime");
        totalTime.addEntry(ChatColor.GRAY + "» TotalTime: " + ChatColor.BLACK + "" + ChatColor.AQUA);
        totalTime.setSuffix(ChatColor.RED + "✘");

        obj.getScore(ChatColor.GRAY + "» TotalTime: " + ChatColor.BLACK + "" + ChatColor.AQUA).setScore(7);


        Team checkpoint = board.registerNewTeam("checkpoint");
        checkpoint.addEntry(ChatColor.GRAY + "» Checkpoint: " + ChatColor.BLACK + "" + ChatColor.BLUE);
        checkpoint.setSuffix(ChatColor.RED + "✘");

        obj.getScore(ChatColor.GRAY + "» Checkpoint: " + ChatColor.BLACK + "" + ChatColor.BLUE).setScore(6);


        Team deaths = board.registerNewTeam("deaths");
        deaths.addEntry(ChatColor.GRAY + "» Deaths: " + ChatColor.BLACK + "" + ChatColor.DARK_PURPLE);
        deaths.setSuffix(ChatColor.RED + "✘");

        obj.getScore(ChatColor.GRAY + "» Deaths: " + ChatColor.BLACK + "" + ChatColor.DARK_PURPLE).setScore(5);




        player.setScoreboard(board);

    }

    public static void updateScoreBoard(Player player) {

        Scoreboard board = player.getScoreboard();


        if (board.getTeam("pushing") != null)
            board.getTeam("pushing").unregister();

        Team disablePushing = board.registerNewTeam("pushing");
        Bukkit.getOnlinePlayers().forEach(disablePushing::addPlayer);
        disablePushing.setOption(Team.Option.COLLISION_RULE, Team.OptionStatus.NEVER);



        Team onlineCounter = board.getTeam("onlineCounter");
        if (onlineCounter != null)
            onlineCounter.setSuffix("" + ChatColor.GREEN + Bukkit.getOnlinePlayers().size() + ChatColor.GRAY + "/" + ChatColor.GREEN + Bukkit.getMaxPlayers());

        Team timer = board.getTeam("timer");
        if (timer != null) {
            if (Game.running && !Game.playerTimers.get(player.getUniqueId()).finished) {
            PlayerTimer playerTimer = Game.playerTimers.get(player.getUniqueId());
            long playerTime = 0;
            if (playerTimer != null && Game.running) playerTime = playerTimer.getCurrentTime();
            timer.setSuffix(ChatColor.AQUA + "" + LocalTime.MIN.plusSeconds(playerTime).toString().substring(3));

            } else {
                timer.setSuffix(ChatColor.RED + "✘");
            }
        }
        Team totalTime = board.getTeam("totalTime");
        if (totalTime != null) {
            if (Game.running) {
                PlayerTimer playerTimer = Game.playerTimers.get(player.getUniqueId());
                long playerTime = 0;
                if (playerTimer != null && Game.running) playerTime = playerTimer.getTotalTime();
                totalTime.setSuffix(ChatColor.AQUA + "" + LocalTime.MIN.plusSeconds(playerTime).toString().substring(3));
            } else {
                totalTime.setSuffix(ChatColor.RED + "✘");
            }
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






        Team rank1 = board.getTeam("rank1");
        if (rank1 != null)
            try {
                Map.Entry<PlayerTimer, Integer> rank = Statistics.getRanks().get(0);
                rank1.setSuffix(ChatColor.GREEN + "" + Bukkit.getPlayer(rank.getKey().player).getName() + ChatColor.GRAY + " - " + ChatColor.GOLD + rank.getValue());
            } catch (Exception e) {
                rank1.setSuffix(ChatColor.RED + "✘");
            }


        Team rank2 = board.getTeam("rank2");
        if (rank2 != null)
            try {
                Map.Entry<PlayerTimer, Integer> rank = Statistics.getRanks().get(1);
                rank2.setSuffix(ChatColor.GREEN + "" + Bukkit.getPlayer(rank.getKey().player).getName() + ChatColor.GRAY + " - " + ChatColor.GOLD + rank.getValue());
            } catch (Exception e) {
                rank2.setSuffix(ChatColor.RED + "✘");
            }


        Team rank3 = board.getTeam("rank3");
        if (rank3 != null)
            try {
                Map.Entry<PlayerTimer, Integer> rank = Statistics.getRanks().get(2);
                rank3.setSuffix(ChatColor.GREEN + "" + Bukkit.getPlayer(rank.getKey().player).getName() + ChatColor.GRAY + " - " + ChatColor.GOLD + rank.getValue());
            } catch (Exception e) {
                rank3.setSuffix(ChatColor.RED + "✘");
            }


        Team userRank = board.getTeam("userRank");
        if (userRank != null) {
            boolean wasFound = false;
            for (Map.Entry<PlayerTimer, Integer> rank : Statistics.getRanks()) {
                if (rank.getKey().player == player.getUniqueId()) {
                    userRank.setSuffix(ChatColor.GREEN.toString() + (Statistics.getRanks().indexOf(rank) + 1));
                    wasFound = true;
                    break;
                }
            }
            if (!wasFound) userRank.setSuffix(ChatColor.RED + "✘");
        }








    }




}
