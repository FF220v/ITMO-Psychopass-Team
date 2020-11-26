package org.civilla.dataclasses.communication.policecontrolserver;

import com.google.gson.Gson;

public class BotServerMessageResponseItem extends BasicMessageItem {
    public String objectId = null;
    public String status = null;
    public String reason = null;

    public static BotServerMessageResponseItem fromJson(String json){
        return new Gson().fromJson(json, BotServerMessageResponseItem.class);
    }
}
