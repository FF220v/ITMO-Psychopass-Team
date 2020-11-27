package org.civilla.dataclasses.communication.notificationserver;

import com.google.gson.Gson;

public class NotificationResponse extends NotificationBase {
    public String status;
    public String reason;

    public static NotificationResponse fromJson(String json){
        return new Gson().fromJson(json, NotificationResponse.class);
    }
}
