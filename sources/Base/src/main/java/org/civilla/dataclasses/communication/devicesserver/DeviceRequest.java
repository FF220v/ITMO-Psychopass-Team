package org.civilla.dataclasses.communication.devicesserver;

import com.google.gson.Gson;

public class DeviceRequest extends DeviceBase {
    public String userId;
    public String targetId;
    public String deviceId;

    public static DeviceRequest fromJson(String json){
        return new Gson().fromJson(json, DeviceRequest.class);
    }
}
