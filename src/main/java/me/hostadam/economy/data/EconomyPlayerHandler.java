package me.hostadam.economy.data;

import lombok.AllArgsConstructor;
import me.hostadam.economy.EconomyPlugin;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
public class EconomyPlayerHandler {

    private final EconomyPlugin plugin;
    private final Map<UUID, EconomyPlayer> playerMap = new HashMap<>();

    public EconomyPlayer byUniqueId(UUID uniqueId) {
        if(!this.playerMap.containsKey(uniqueId)) {
            EconomyPlayer economyPlayer = new EconomyPlayer(this.plugin, uniqueId);
            this.playerMap.put(uniqueId, economyPlayer);
            return economyPlayer;
        }

        return this.playerMap.get(uniqueId);
    }

    public EconomyPlayer byName(String name) {
        OfflinePlayer player = Bukkit.getOfflinePlayer(name);
        if(!player.hasPlayedBefore() && !player.isOnline()) {
            return null;
        }

        return this.byUniqueId(player.getUniqueId());
    }
}
