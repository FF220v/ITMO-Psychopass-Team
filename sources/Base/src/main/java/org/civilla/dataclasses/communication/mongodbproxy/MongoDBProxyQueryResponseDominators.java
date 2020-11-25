package org.civilla.dataclasses.communication.mongodbproxy;
import com.google.gson.Gson;
import org.civilla.dataclasses.database.Dominator;

import java.util.ArrayList;

public class MongoDBProxyQueryResponseDominators extends BasicMongoDBProxyObject {
    public String dataType = null;
    public ArrayList<Dominator> values = new ArrayList<>();

    public static MongoDBProxyQueryResponseDominators fromJson(String json){
        return new Gson().fromJson(json, MongoDBProxyQueryResponseDominators.class);
    }
}
