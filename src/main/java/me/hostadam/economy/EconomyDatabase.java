package me.hostadam.economy;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import org.bson.Document;

import java.util.Objects;

public class EconomyDatabase {

    private MongoClient client;
    private MongoDatabase database;
    @Getter
    private MongoCollection<Document> players;

    public EconomyDatabase(EconomyPlugin plugin) {
        this.enable(plugin);
    }

    public void enable(EconomyPlugin plugin) {
        ConnectionString string = new ConnectionString(plugin.getConfig().getString("mongo-connection-string"));
        MongoClientSettings.Builder settings = MongoClientSettings.builder().applyConnectionString(string);

        try {
            this.client = MongoClients.create(settings.build());
            this.database = this.client.getDatabase(Objects.requireNonNull(string.getDatabase()));
            this.players = this.database.getCollection("players");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void disable() {
        this.client.close();
    }
}
