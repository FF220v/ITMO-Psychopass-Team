package org.civilla.dataclasses.database;

import com.google.gson.Gson;

public class BotSession extends DatabaseItem {

    public String msgId = null;
    public String firstNameBuf = null;
    public String lastNameBuf = null;
    public Boolean likesBeerBuf = null;
    public Boolean isPersonalDataFilled = false;

    public static BotSession fromJson(String json){
        return new Gson().fromJson(json, BotSession.class);
    }
}