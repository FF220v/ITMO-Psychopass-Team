package org.civilla.dataclasses.communication.policecontrolserver;

import com.google.gson.Gson;
import org.civilla.dataclasses.communication.BasicMessage;

public class BotServerMessageRequest extends BasicMessage<BotServerMessageRequestItem> {
    public String dataType = "messageRequests";
    public static BotServerMessageRequest fromJson(String json){
        return new Gson().fromJson(json, BotServerMessageRequest.class);
    }
}
