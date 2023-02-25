package de.artus.alphaevent.utils;

import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

public class PlayerParticle {




    public static void spawnPlayerParticles(Player player) {

        if (PlayerInventory.playerVisible.getOrDefault(player.getUniqueId(), true)) return;
        Bukkit.getOnlinePlayers().forEach(p -> {
            if (p == player) return;
            for (double angle = 0; angle <= Math.PI*2; angle += 0.1) {
                double x = Math.sin(angle) * 0.75;
                double z = Math.cos(angle) * 0.75;
                player.spawnParticle(Particle.CRIT_MAGIC, p.getLocation().add(x, 0.1, z), 0);
            }
        });


    }

}
