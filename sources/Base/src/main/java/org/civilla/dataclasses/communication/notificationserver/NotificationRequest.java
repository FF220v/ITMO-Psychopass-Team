package org.civilla.dataclasses.communication.notificationserver;

import com.google.gson.Gson;

public class NotificationRequest extends NotificationBase{
    public String message;

    public static NotificationRequest fromJson(String json){
        return new Gson().fromJson(json, NotificationRequest.class);
    }
}
