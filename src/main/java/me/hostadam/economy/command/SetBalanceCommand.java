package me.hostadam.economy.command;

import com.google.common.primitives.Doubles;
import me.hostadam.economy.EconomyPlugin;
import me.hostadam.economy.data.EconomyPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collections;

public class SetBalanceCommand extends Command {

    private final EconomyPlugin plugin;
    public SetBalanceCommand(EconomyPlugin economyPlugin) {
        super("setbalance", "Set a player's balance", "setbal <player> <amount>", Arrays.asList("setbal"));
        this.plugin = economyPlugin;
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        if(!sender.isOp()) {
            sender.sendMessage("§cNo permission.");
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
            if(amount < 0) {
                sender.sendMessage("§cYou cannot set the balance to less than $0.");
                return true;
            }

            economyPlayer.setBalance(amount);
            sender.sendMessage("§eYou set the balance of §f'" + args[0] + "' §eto §2$§f" + amount + "§e.");
        } else {
            sender.sendMessage("§cUsage: /" + this.getUsage());
        }

        return true;
    }
}
