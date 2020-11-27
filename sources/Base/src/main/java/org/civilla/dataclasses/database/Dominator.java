package org.civilla.dataclasses.database;

import com.google.gson.Gson;

public class Dominator extends OwnedItem {
    public String type = "dominator";
    public String name = null;
    public static Dominator fromJson(String json){
        return new Gson().fromJson(json, Dominator.class);
    }
}
