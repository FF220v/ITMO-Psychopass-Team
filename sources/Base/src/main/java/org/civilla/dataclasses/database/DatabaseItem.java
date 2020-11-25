package org.civilla.dataclasses.database;

import com.google.gson.Gson;

public abstract class DatabaseItem {
    public String objectId = null;
    public String toJson() {
        return new Gson().toJson(this);
    }
}
