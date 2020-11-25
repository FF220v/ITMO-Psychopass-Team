package org.civilla.dataclasses.database;

import com.google.gson.Gson;

public class BotSession extends DatabaseItem {

    public String objectId = null;
    public String msgId = null;
    public String firstNameBuf = null;
    public String lastNameBuf = null;
    public Boolean likesBeerBuf = null;
    public Boolean isPersonalDataFilled = false;
    public Boolean isPoliceman = false;
    public Boolean isAdmin = false;

    // Remove these three, duplication with database (or not remove? hm...)
    public String firstName = null;
    public String lastName = null;
    public Boolean likesBeer = null;

    public static BotSession fromJson(String json){
        return new Gson().fromJson(json, BotSession.class);
    }
}