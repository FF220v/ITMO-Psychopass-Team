package org.civilla.dataclasses.communication.devicesserver;

import com.google.gson.Gson;

public class DeviceBase {
    public String toJson() {
        return new Gson().toJson(this);
    }
}
