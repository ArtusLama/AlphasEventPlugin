package de.artus.alphaevent.utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.FallingBlock;

public class BarrierLoader {



    public static void setBarrier(Location loc) {

        Location barrierLocation = new Location(loc.getWorld(), 18, -60, 255);

        for (int x = 0; x >= -3; x--) {
            for (int z = -4; z <= 4; z++) {
                for (int y = 0; y <= 4; y++) {
                    Location blockLocation = barrierLocation.clone().add(x, y, z);
                    if (blockLocation.getBlock().getType() != Material.AIR)
                        loc.clone().add(x, y, z).getBlock().setBlockData(blockLocation.getBlock().getBlockData());

                }
            }
        }
    }

    public static void removeBarrier(Location loc) {

        Location barrierLocation = new Location(loc.getWorld(), 18, -60, 255);

        for (int x = 0; x >= -3; x--) {
            for (int z = -4; z <= 4; z++) {
                for (int y = 0; y <= 4; y++) {
                    Location blockLocation = barrierLocation.clone().add(x, y, z);
                    if (blockLocation.getBlock().getType() == loc.clone().add(x, y, z).getBlock().getType()) {
                        if (loc.clone().add(x, y, z).getBlock().getType() != Material.AIR) {
                            //blockLocation.getWorld().spawnParticle(Particle.GLOW, loc.clone().add(x + 0.5, y + 0.5, z + 0.5), 10);
                            FallingBlock fallingBlock = blockLocation.getWorld().spawnFallingBlock(loc.clone().add(x + 0.5, y + 0.5, z + 0.5), loc.clone().add(x, y, z).getBlock().getBlockData());
                            fallingBlock.setDropItem(false);
                            fallingBlock.setHurtEntities(false);
                            fallingBlock.setInvulnerable(true);
                            fallingBlock.setTicksLived(20 * 5);
                        }
                        loc.clone().add(x, y, z).getBlock().setType(Material.AIR);
                    }
                }
            }
        }

    }
}
