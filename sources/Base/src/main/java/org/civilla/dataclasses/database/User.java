package org.civilla.dataclasses.database;

import com.google.gson.Gson;

public class User extends DatabaseItem {
    public String firstNameBuf = null;
    public String lastNameBuf = null;
    public Boolean likesBeerBuf = null;
    public Double psychopassValue = null;

    public static User fromJson(String json){
        return new Gson().fromJson(json, User.class);
    }
}
