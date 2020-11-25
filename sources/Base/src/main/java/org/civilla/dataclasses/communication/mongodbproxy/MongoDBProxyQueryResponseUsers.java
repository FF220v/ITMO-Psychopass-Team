package org.civilla.dataclasses.communication.mongodbproxy;
import com.google.gson.Gson;
import org.civilla.dataclasses.database.User;

import java.util.ArrayList;

public class MongoDBProxyQueryResponseUsers extends BasicMongoDBProxyObject {
    public String dataType = null;
    public ArrayList<User> values = new ArrayList<>();

    public static MongoDBProxyQueryResponseUsers fromJson(String json){
        return new Gson().fromJson(json, MongoDBProxyQueryResponseUsers.class);
    }
}
