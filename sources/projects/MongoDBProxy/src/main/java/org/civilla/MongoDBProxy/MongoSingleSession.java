package org.civilla.MongoDBProxy;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.civilla.kubernetes.KubeConfigLoader;

public class MongoSingleSession {
    protected static MongoDatabase database = null;
    public static MongoDatabase getDatabase(){
        if(database == null) {
            ConnectionString connString = new ConnectionString(
                    String.join("", "mongodb://",
                            KubeConfigLoader.servicesUrls().get("mongodb").toString(), "/")
            );
            MongoClient mongoClient = MongoClients.create(connString);
            database = mongoClient.getDatabase("CivillaDatabase");
            return mongoClient.getDatabase("CivillaDatabase");
        }
        return database;
    }
}
