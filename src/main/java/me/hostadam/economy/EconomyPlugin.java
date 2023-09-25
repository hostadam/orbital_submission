package me.hostadam.economy;

import lombok.Getter;
import me.hostadam.economy.command.BalanceCommand;
import me.hostadam.economy.command.EarnCommand;
import me.hostadam.economy.command.GiveCommand;
import me.hostadam.economy.command.SetBalanceCommand;
import me.hostadam.economy.data.EconomyPlayerHandler;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;

@Getter
public class EconomyPlugin extends JavaPlugin {

    private EconomyDatabase database;
    private EconomyPlayerHandler playerHandler;

    @Override
    public void onEnable() {
        if(!getDataFolder().exists()) {
            getDataFolder().mkdirs();
        }

        this.saveDefaultConfig();
        this.database = new EconomyDatabase(this);
        this.playerHandler = new EconomyPlayerHandler(this);

        CommandMap map;
        try {
            Field field = this.getServer().getClass().getDeclaredField("commandMap");
            field.setAccessible(true);
            map = (CommandMap) field.get(this.getServer());
        } catch (Exception exception) {
            exception.printStackTrace();
            return;
        }

        map.register("balance", new BalanceCommand(this));
        map.register("earn", new EarnCommand(this));
        map.register("give", new GiveCommand(this));
        map.register("setbalance", new SetBalanceCommand(this));
    }

    @Override
    public void onDisable() {
        this.database.disable();
    }
}
