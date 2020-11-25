package org.civilla.dataclasses.communication;
import com.google.gson.Gson;
import java.util.ArrayList;

public abstract class BasicMessage<T> {
    public ArrayList<T> values = new ArrayList<>();
    public String toJson() {
        return new Gson().toJson(this);
    }
}
