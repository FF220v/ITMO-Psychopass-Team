package org.civilla.dataclasses.communication.policecontrolserver;

import com.google.gson.Gson;
import org.civilla.dataclasses.communication.BasicMessageItem;

public class BotServerMessageRequestItem extends BasicMessageItem {
    public String objectId = null;
    public String message = null;
    public Boolean restricted = null;

    public static BotServerMessageRequestItem fromJson(String json){
        return new Gson().fromJson(json, BotServerMessageRequestItem.class);
    }
}
