package org.civilla.dataclasses.communication.mongodbproxy;

import com.google.gson.Gson;

public class MongoDBProxyPostResponse extends BasicMongoDBProxyObject {
    public String status = null;
    public String reason = null;

    public static MongoDBProxyPostResponse fromJson(String json){
        return new Gson().fromJson(json, MongoDBProxyPostResponse.class);
    }
}
