package org.civilla.dataclasses.database;

import com.google.gson.Gson;

public class Camera extends OwnedItem {
    public Double coordinateX = null;
    public Double coordinateY = null;
    public String cameraModel = null;

    public static Camera fromJson(String json){
        return new Gson().fromJson(json, Camera.class);
    }
}
