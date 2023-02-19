package de.artus.alphaevent.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class Chat {

    public static String prefix = ChatColor.GRAY + "[" + ChatColor.DARK_PURPLE + "Event" + ChatColor.GRAY + "] ";

    public static void sendMessage(Player player, String msg, boolean sound) {

        player.sendMessage(prefix + msg);
        if (sound) player.playSound(player, Sound.BLOCK_NOTE_BLOCK_PLING, 0.5f, 2);
    }
    public static void sendMessageToAll(String msg, boolean sound) {
        Bukkit.getOnlinePlayers().forEach(player -> sendMessage(player, msg, sound));
    }


}
