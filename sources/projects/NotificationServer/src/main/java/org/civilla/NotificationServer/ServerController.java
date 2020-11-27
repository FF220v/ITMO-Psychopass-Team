package org.civilla.NotificationServer;

import org.civilla.common.Logging;
import org.civilla.dataclasses.communication.notificationserver.NotificationRequest;
import org.civilla.dataclasses.communication.notificationserver.NotificationResponse;
import org.civilla.dataclasses.communication.policecontrolserver.BotServerMessageRequest;
import org.civilla.dataclasses.communication.policecontrolserver.BotServerMessageRequestItem;
import org.civilla.dataclasses.communication.policecontrolserver.BotServerMessageResponse;
import org.civilla.dataclasses.communication.policecontrolserver.BotServerMessageResponseItem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.Notification;

@RestController
public class ServerController {
    @RequestMapping(value = "/notify", method = RequestMethod.POST,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> messageHandler(@RequestBody String payload,
                                                 @RequestHeader(value = "X-request-id", required = false) String requestId){
        long startTime = System.currentTimeMillis();
        Logging.log.info(String.join(" ", "Received message", requestId, payload));

        NotificationRequest request = NotificationRequest.fromJson(payload);
        NotificationResponse response = new NotificationResponse();

        NotificationMsgProc proc = new NotificationMsgProc();
        NotificationResponse result = proc.processMessage(request.message, requestId);

        String stringResponse = response.toJson();
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("X-request-id", requestId);
        Logging.log.info(String.join(" ", "Sending response", requestId, stringResponse, "time taken:", Long.toString(System.currentTimeMillis() - startTime), "ms"));
        return new ResponseEntity<>(stringResponse, responseHeaders, HttpStatus.OK);
    }
}
