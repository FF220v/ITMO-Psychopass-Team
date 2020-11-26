package org.civilla.PoliceControlServer;

public class PoliceControlMsgProcResponse {
    public String status;
    public String reason;
    PoliceControlMsgProcResponse(String status, String reason){
        this.status = status;
        this.reason = reason;
    }
}
