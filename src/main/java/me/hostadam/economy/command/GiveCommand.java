package me.hostadam.economy.command;

import com.google.common.primitives.Doubles;
import me.hostadam.economy.EconomyPlugin;
import me.hostadam.economy.data.EconomyPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collections;

public class GiveCommand extends Command {

    private final EconomyPlugin plugin;
    public GiveCommand(EconomyPlugin economyPlugin) {
        super("give", "Give a player money", "give <player> <amount>", Collections.emptyList());
        this.plugin = economyPlugin;
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        if(!(sender instanceof Player player)) {
            sender.sendMessage("§cThis command can only be ran by players.");
            return true;
        }

        if(args.length > 1) {
            EconomyPlayer economyPlayer = this.plugin.getPlayerHandler().byName(args[0]);
            if(economyPlayer == null) {
                sender.sendMessage("§cThis player has never played before.");
                return true;
            }

            if(Doubles.tryParse(args[1]) == null) {
                sender.sendMessage("§cInvalid amount.");
                return true;
            }

            double amount = Double.parseDouble(args[1]);
            if(amount <= 0) {
                sender.sendMessage("§cYou cannot give $0 or less.");
                return true;
            }

            EconomyPlayer senderEconomy = this.plugin.getPlayerHandler().byUniqueId(player.getUniqueId());
            if(senderEconomy.getBalance() < amount) {
                sender.sendMessage("§cInsufficient funds.");
                return true;
            }

            economyPlayer.addBalance(amount);
            senderEconomy.deductBalance(amount);
            sender.sendMessage("§eYou gave away §f" + amount + "§e.");
        } else {
            sender.sendMessage("§cUsage: " + this.getUsage());
        }

        return true;
    }
}
