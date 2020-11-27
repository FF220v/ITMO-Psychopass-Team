package org.civilla.dataclasses.communication.analysisserver;

import com.google.gson.Gson;

public class AnalysisBase {
    public String toJson() {
        return new Gson().toJson(this);
    }
}
