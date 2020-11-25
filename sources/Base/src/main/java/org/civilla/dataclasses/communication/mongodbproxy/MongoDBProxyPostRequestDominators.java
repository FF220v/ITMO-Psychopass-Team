package org.civilla.dataclasses.communication.mongodbproxy;
import com.google.gson.Gson;
import org.civilla.dataclasses.database.Dominator;
import org.civilla.dataclasses.database.User;

import java.util.ArrayList;

public class MongoDBProxyPostRequestDominators extends BasicMongoDBProxyObject {
    public String dataType = null;
    public ArrayList<Dominator> values = new ArrayList<>();

    public static MongoDBProxyPostRequestDominators fromJson(String json){
        return new Gson().fromJson(json, MongoDBProxyPostRequestDominators.class);
    }
}
