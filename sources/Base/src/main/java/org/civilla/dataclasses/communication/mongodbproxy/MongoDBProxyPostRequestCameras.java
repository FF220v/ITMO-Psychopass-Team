package org.civilla.dataclasses.communication.mongodbproxy;
import com.google.gson.Gson;
import org.civilla.dataclasses.database.Camera;
import org.civilla.dataclasses.database.User;

import java.util.ArrayList;

public class MongoDBProxyPostRequestCameras extends BasicMongoDBProxyObject {
    public String dataType = null;
    public ArrayList<Camera> values = new ArrayList<>();

    public static MongoDBProxyPostRequestCameras fromJson(String json){
        return new Gson().fromJson(json, MongoDBProxyPostRequestCameras.class);
    }
}
