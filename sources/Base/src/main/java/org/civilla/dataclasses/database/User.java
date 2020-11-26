package org.civilla.dataclasses.database;

import com.google.gson.Gson;

public class User extends DatabaseItem {
    public String firstName = null;
    public String lastName = null;
    public Boolean likesBeer = null;
    public Boolean isPoliceman = null;
    public String isPolicemanStr = null; //Kostili
    public Double psychopassValue = null;

    public static User fromJson(String json){
        return new Gson().fromJson(json, User.class);
    }
}
