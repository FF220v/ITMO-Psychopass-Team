package org.civilla.dataclasses.communication.mongodbproxy;
import com.google.gson.Gson;
import org.civilla.dataclasses.database.BotSession;
import org.civilla.dataclasses.database.User;

import java.util.ArrayList;

public class MongoDBProxyPostRequestBotSessions extends BasicMongoDBProxyObject {
    public String dataType = null;
    public ArrayList<BotSession> values = new ArrayList<>();

    public static MongoDBProxyPostRequestBotSessions fromJson(String json){
        return new Gson().fromJson(json, MongoDBProxyPostRequestBotSessions.class);
    }
}
