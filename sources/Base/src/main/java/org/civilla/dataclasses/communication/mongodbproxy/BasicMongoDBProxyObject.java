package org.civilla.dataclasses.communication.mongodbproxy;

import com.google.gson.Gson;

public abstract class BasicMongoDBProxyObject {
    public String toJson() {
        return new Gson().toJson(this);
    }
}