package org.civilla.dataclasses.communication.devicesserver;

import com.google.gson.Gson;

public class DominatorResponse extends DeviceBase {
    public String status = null;
    public String reason = null;
    public Boolean usageAllowed = null;

    public static DominatorResponse fromJson(String json){
        return new Gson().fromJson(json, DominatorResponse.class);
    }
}
