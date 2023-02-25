package de.artus.alphaevent.utils;

import de.artus.alphaevent.Alphaevent;
import de.artus.alphaevent.logic.Checkpoints;
import de.artus.alphaevent.logic.Game;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerInventory implements Listener {


    public static ItemStack lastCheckpoint = new ItemStack(Material.LIGHT_WEIGHTED_PRESSURE_PLATE, 1);
    public static ItemStack hidePlayers = new ItemStack(Material.GRAY_DYE, 1);
    public static ItemStack showPlayers = new ItemStack(Material.LIME_DYE, 1);

    public static Map<UUID, Boolean> playerVisible = new HashMap<>();




    static {
        ItemMeta meta = lastCheckpoint.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "Teleport to last Checkpoint (RightClick)");
        lastCheckpoint.setItemMeta(meta);

        ItemMeta meta2 = hidePlayers.getItemMeta();
        meta2.setDisplayName(ChatColor.GRAY + "Click to show other Players (RightClick)");
        hidePlayers.setItemMeta(meta2);

        ItemMeta meta3 = showPlayers.getItemMeta();
        meta3.setDisplayName(ChatColor.GREEN + "Click to hide other Players (RightClick)");
        showPlayers.setItemMeta(meta3);
    }


    public static void setupLobbyInv() {
        Bukkit.getOnlinePlayers().forEach(p -> {
            p.getInventory().clear();
        });
    }

    public static void setupGameInv() {
        Bukkit.getOnlinePlayers().forEach(p -> {
            p.getInventory().clear();
            p.getInventory().setItem(0, lastCheckpoint);
            p.getInventory().setItem(1, showPlayers);
            playerVisible.put(p.getUniqueId(), true);
        });
    }



    public static void showAllPlayers() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (player == p) continue;
                player.showPlayer(p);
            }
        }
    }

    public static void showPlayers(Player executor) {
        playerVisible.put(executor.getUniqueId(), true);
        Bukkit.getOnlinePlayers().forEach(p -> {
            if (p != executor) executor.showPlayer(p);
        });
    }

    public static void hidePlayers(Player executor) {
        Bukkit.getOnlinePlayers().forEach(p -> {
            if (p != executor) executor.hidePlayer(p);
        });
    }



    @EventHandler
    public void dontDrop(PlayerDropItemEvent e) {
        if (e.getPlayer().getGameMode() == GameMode.ADVENTURE) e.setCancelled(true);
    }

    @EventHandler
    public void onUseItem(PlayerInteractEvent e) {
        if (e.getPlayer().getGameMode() == GameMode.SPECTATOR) return;
        if (e.getItem() != null) {
            if (e.getAction().isRightClick()) {
                if (e.getItem().isSimilar(lastCheckpoint)) {
                    e.getPlayer().teleport(Checkpoints.getLastCheckpoint(e.getPlayer()));
                } else if (e.getItem().isSimilar(hidePlayers)) {
                    showPlayers(e.getPlayer());
                    e.getItem().setType(showPlayers.getType());
                    e.getItem().setItemMeta(showPlayers.getItemMeta());
                    playerVisible.put(e.getPlayer().getUniqueId(), true);
                } else if (e.getItem().isSimilar(showPlayers)) {
                    hidePlayers(e.getPlayer());
                    e.getItem().setType(hidePlayers.getType());
                    e.getItem().setItemMeta(hidePlayers.getItemMeta());
                    playerVisible.put(e.getPlayer().getUniqueId(), false);
                }
            }
        }
    }


}
