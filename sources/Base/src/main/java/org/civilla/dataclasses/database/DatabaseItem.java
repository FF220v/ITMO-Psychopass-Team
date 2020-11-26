package org.civilla.dataclasses.database;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

public abstract class DatabaseItem{
    public String objectId = null;
    public String toJson() {
        return new Gson().toJson(this);
    }
}
