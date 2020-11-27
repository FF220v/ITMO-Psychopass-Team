package org.civilla.AnalysisServer;

import org.civilla.common.Logging;
import org.civilla.dataclasses.communication.analysisserver.AnalysisRequest;
import org.civilla.dataclasses.communication.analysisserver.AnalysisResponse;
import org.civilla.dataclasses.database.User;
import org.civilla.notification.NotificationConnector;
import org.civilla.storage.DatabaseConnectorUsers;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ServerController {

    protected static PsychopassDataProcessor psychopassDataProcessor = new PsychopassDataProcessor();

    @RequestMapping(value = "/analyse", method = RequestMethod.POST,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> analysisHandler(@RequestBody String payload,
                                                 @RequestHeader(value = "X-request-id",
                                                         required = false) String requestId){
        long startTime = System.currentTimeMillis();
        Logging.log.info(String.join(" ", "Received request", requestId, payload));
        AnalysisResponse response = new AnalysisResponse();
        AnalysisRequest request = AnalysisRequest.fromJson(payload);
        boolean sendNotification = false;
        String notificationMsg;
        try {
            Double psychopassValue = psychopassDataProcessor.analyse();
            DatabaseConnectorUsers conn = new DatabaseConnectorUsers();
            User user = conn.get(request.userId, requestId);

            if(user == null){
                sendNotification = true;
                notificationMsg = String.format("[Analysis] [%s] Attention! Unknown user found.", request.serviceName);
            } else {
                notificationMsg = String.format(String.join(" ", "[Analysis] [%s]",
                        user.isPoliceman ? "Policeman" : "Citizen",
                        user.objectId, user.firstName, user.lastName), request.serviceName) + ".";
                user.psychopassValue = psychopassValue;
                conn.update(user, requestId);
                response.userId = user.objectId;
            }
            if(psychopassValue > 0.5) {
                sendNotification = true;
                notificationMsg = String.join(" ", notificationMsg,
                        "User psychopass value is higher then average -", psychopassValue.toString(),
                        "Police action is required.");
            }
            if(sendNotification)
                new NotificationConnector().notify(notificationMsg, requestId);

            response.status = "ok";
            response.reason = "";
            response.psychoPassValue = psychopassValue;
        } catch (Exception e){
            e.printStackTrace();
            response.status = "exception";
            response.reason = e.getMessage();
            response.psychoPassValue = null;
            response.userId = null;
        }
        String stringResponse = response.toJson();
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("X-request-id", requestId);
        Logging.log.info(String.join(" ", "Sending response", requestId, stringResponse, "time taken:", Long.toString(System.currentTimeMillis() - startTime), "ms"));
        return new ResponseEntity<>(stringResponse, responseHeaders, HttpStatus.OK);
    }
}