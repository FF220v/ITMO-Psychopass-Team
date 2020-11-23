package org.civilla.PoliceControlServer;

public class TelegramMessage {
    long chat_id;
    String text;
    TelegramMessage(long chat_id, String text){
        this.chat_id = chat_id;
        this.text = text;
    }
}
