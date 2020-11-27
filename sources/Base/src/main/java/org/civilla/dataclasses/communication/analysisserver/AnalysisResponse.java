package org.civilla.dataclasses.communication.analysisserver;

import com.google.gson.Gson;

public class AnalysisResponse extends AnalysisBase {
    public String status;
    public String reason;
    public String userId;
    public Double psychoPassValue;

    public static AnalysisResponse fromJson(String json){
        return new Gson().fromJson(json, AnalysisResponse.class);
    }
}
