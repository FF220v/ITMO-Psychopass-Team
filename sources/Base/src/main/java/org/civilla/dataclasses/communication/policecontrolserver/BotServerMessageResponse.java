package org.civilla.dataclasses.communication.policecontrolserver;

import com.google.gson.Gson;

public class BotServerMessageResponse extends BasicMessage<BotServerMessageResponseItem> {
    public String dataType = "messageResponses";
    public static BotServerMessageResponse fromJson(String json){
        return new Gson().fromJson(json, BotServerMessageResponse.class);
    }
}
