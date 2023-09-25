package me.hostadam.economy.command;

import me.hostadam.economy.EconomyPlugin;
import me.hostadam.economy.data.EconomyPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class BalanceCommand extends Command {

    private final EconomyPlugin plugin;
    public BalanceCommand(EconomyPlugin economyPlugin) {
        super("balance", "View a player's balance", "balance [player]", Arrays.asList("bal"));
        this.plugin = economyPlugin;
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        if(args.length > 0) {
            EconomyPlayer economyPlayer = this.plugin.getPlayerHandler().byName(args[0]);
            if(economyPlayer == null) {
                sender.sendMessage("§cThis player has never played before.");
                return true;
            }

            sender.sendMessage("§eBalance§7: §2$§f" + economyPlayer.getBalance());
        } else if(sender instanceof Player player) {
            EconomyPlayer economyPlayer = this.plugin.getPlayerHandler().byUniqueId(player.getUniqueId());
            sender.sendMessage("§eYour Balance§7: §2$§f" + economyPlayer.getBalance());
        } else {
            sender.sendMessage("§cThis command cannot be ran by console.");
        }

        return true;
    }
}
