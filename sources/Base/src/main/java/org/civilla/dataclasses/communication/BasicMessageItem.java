package org.civilla.dataclasses.communication;

import com.google.gson.Gson;

public abstract class BasicMessageItem {
    public String toJson() {
        return new Gson().toJson(this);
    }
}
