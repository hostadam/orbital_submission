package me.hostadam.economy.command;

import com.google.common.primitives.Doubles;
import me.hostadam.economy.EconomyPlugin;
import me.hostadam.economy.data.EconomyPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

public class EarnCommand extends Command {

    private final EconomyPlugin plugin;
    public EarnCommand(EconomyPlugin economyPlugin) {
        super("earn", "Earn a random amount of money", "earn", Collections.emptyList());
        this.plugin = economyPlugin;
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        if(!(sender instanceof Player player)) {
            sender.sendMessage("§cThis command can only be ran by players.");
            return true;
        }

        EconomyPlayer economyPlayer = this.plugin.getPlayerHandler().byUniqueId(player.getUniqueId());
        if(!economyPlayer.canEarn()) {
            sender.sendMessage("§cYou are on cooldown.");
            return true;
        }

        int earned = economyPlayer.earn();
        sender.sendMessage("§eYou earned §2$§f" + earned + "§e.");
        return true;
    }
}
