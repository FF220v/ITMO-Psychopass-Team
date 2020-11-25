package org.civilla.dataclasses.communication.mongodbproxy;
import com.google.gson.Gson;
import org.civilla.dataclasses.database.BotSession;

import java.util.ArrayList;

public class MongoDBProxyQueryResponseBotSessions extends BasicMongoDBProxyObject{
    public String dataType = null;
    public ArrayList<BotSession> values = new ArrayList<>();

    public static MongoDBProxyQueryResponseBotSessions fromJson(String json){
        return new Gson().fromJson(json, MongoDBProxyQueryResponseBotSessions.class);
    }
}
