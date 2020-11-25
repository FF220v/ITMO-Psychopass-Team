package org.civilla.dataclasses.communication.mongodbproxy;
import com.google.gson.Gson;
import org.civilla.dataclasses.database.Camera;

import java.util.ArrayList;

public class MongoDBProxyQueryResponseCameras extends BasicMongoDBProxyObject {
    public String dataType = null;
    public ArrayList<Camera> values = new ArrayList<>();

    public static MongoDBProxyQueryResponseCameras fromJson(String json){
        return new Gson().fromJson(json, MongoDBProxyQueryResponseCameras.class);
    }
}
