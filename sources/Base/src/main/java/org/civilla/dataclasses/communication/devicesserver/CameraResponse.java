package org.civilla.dataclasses.communication.devicesserver;

import com.google.gson.Gson;

public class CameraResponse extends DeviceBase {
    public String status = null;
    public String reason = null;
    public static CameraResponse fromJson(String json){
        return new Gson().fromJson(json, CameraResponse.class);
    }
}
