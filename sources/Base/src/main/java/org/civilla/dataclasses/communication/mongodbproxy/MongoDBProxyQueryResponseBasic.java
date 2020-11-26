package org.civilla.dataclasses.communication.mongodbproxy;
import com.google.gson.Gson;
import org.civilla.dataclasses.database.User;

import java.util.ArrayList;

public class MongoDBProxyQueryResponseBasic extends BasicMongoDBProxyObject {
    public String status = "";
    public String reason = "";

    public static MongoDBProxyQueryResponseBasic fromJson(String json){
        return new Gson().fromJson(json, MongoDBProxyQueryResponseBasic.class);
    }
}
