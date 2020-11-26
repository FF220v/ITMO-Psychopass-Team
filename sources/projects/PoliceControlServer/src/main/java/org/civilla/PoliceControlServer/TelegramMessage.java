package org.civilla.PoliceControlServer;

public class TelegramMessage {
    long objectId;
    String text;
    TelegramMessage(long objectId, String text){
        this.objectId = objectId;
        this.text = text;
    }
}
