package me.hostadam.economy.data;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import com.mongodb.client.model.UpdateOptions;
import lombok.Getter;
import me.hostadam.economy.EconomyPlugin;
import org.bson.Document;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@Getter
public class EconomyPlayer {

    private final transient EconomyPlugin plugin;
    private final UUID uniqueId;
    private long lastEarnUsed = 0;
    private double balance = 0.0;

    public EconomyPlayer(EconomyPlugin plugin, UUID uniqueId) {
        this.plugin = plugin;
        this.uniqueId = uniqueId;
        this.load();
    }

    public int earn() {
        this.lastEarnUsed = System.currentTimeMillis();
        int balance = ThreadLocalRandom.current().nextInt(1, 6);
        this.addBalance(balance);
        return balance;
    }

    public boolean canEarn() {
        return this.lastEarnUsed == 0 || System.currentTimeMillis() - this.lastEarnUsed > TimeUnit.MINUTES.toMinutes(1);
    }

    public void addBalance(double balance) {
        this.setBalance(this.balance + balance);
    }

    public void deductBalance(double balance) {
        this.setBalance(this.balance - balance);
    }

    public void setBalance(double newBalance) {
        this.balance = Math.max(0, newBalance);
        this.save();
    }

    public void save() {
        Document document = new Document("uniqueId", this.uniqueId.toString())
                .append("balance", this.balance);
        this.plugin.getDatabase().getPlayers().updateOne(Filters.eq("uniqueId", this.uniqueId.toString()), document, new UpdateOptions().upsert(true));
    }

    public void load() {
        Document document = this.plugin.getDatabase().getPlayers().find(Filters.eq("uniqueId", this.uniqueId.toString())).first();
        if(document != null) {
            this.balance = document.getDouble("balance");
        }
    }
}
