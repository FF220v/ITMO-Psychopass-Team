package org.civilla.dataclasses.communication.analysisserver;

import com.google.gson.Gson;

public class AnalysisRequest extends AnalysisBase {
    public String userId;
    public String serviceName;
    public static AnalysisRequest fromJson(String json){
        return new Gson().fromJson(json, AnalysisRequest.class);
    }
}
