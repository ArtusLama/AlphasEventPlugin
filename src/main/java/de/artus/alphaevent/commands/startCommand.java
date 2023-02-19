package de.artus.alphaevent.commands;

import de.artus.alphaevent.logic.Game;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class startCommand implements CommandExecutor {


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

            if (sender instanceof Player player) {
                if (player.isOp()) {
                    Game.startCountdown();
                }
            }

        return false;
    }
}
