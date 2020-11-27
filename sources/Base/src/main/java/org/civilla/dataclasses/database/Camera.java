package org.civilla.dataclasses.database;

import com.google.gson.Gson;

public class Camera extends OwnedItem {
    public String type = "camera";
    public String name = null;
    public Double xCoordinate = null;
    public Double yCoordinate = null;
    public static Camera fromJson(String json){
        return new Gson().fromJson(json, Camera.class);
    }
}
