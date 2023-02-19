package de.artus.alphaevent.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class DisableFood implements Listener {

    @EventHandler
    public void onHungerChange(FoodLevelChangeEvent e) {
        e.setCancelled(true);
    }
}
