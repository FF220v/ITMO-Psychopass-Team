package org.civilla.dataclasses.communication.mongodbproxy;

import com.google.gson.Gson;

public class MongoDBProxyQueryRequest extends BasicMongoDBProxyObject{
    public String dataType = "mongodbRequest";
    public String field = null;
    public String value = null;

    public static MongoDBProxyQueryRequest fromJson(String json){
        return new Gson().fromJson(json, MongoDBProxyQueryRequest.class);
    }
}
