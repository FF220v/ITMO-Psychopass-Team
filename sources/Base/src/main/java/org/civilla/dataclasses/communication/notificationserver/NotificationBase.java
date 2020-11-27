package org.civilla.dataclasses.communication.notificationserver;

import com.google.gson.Gson;

public class NotificationBase {
    public String toJson() {
        return new Gson().toJson(this);
    }
}
