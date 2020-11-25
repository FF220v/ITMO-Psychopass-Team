package org.civilla.PoliceControlServer;

import org.civilla.common.Logging;
import org.civilla.dataclasses.communication.policecontrolserver.BotServerMessageRequest;
import org.civilla.dataclasses.communication.policecontrolserver.BotServerMessageRequestItem;
import org.civilla.dataclasses.communication.policecontrolserver.BotServerMessageResponse;
import org.civilla.dataclasses.communication.policecontrolserver.BotServerMessageResponseItem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ServerController {
    @RequestMapping(value = "/message", method = RequestMethod.POST,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> messageHandler(@RequestBody String payload,
                                                 @RequestHeader(value = "X-request-id", required = false) String requestId){
        long startTime = System.currentTimeMillis();
        Logging.log.info(String.join(" ", "Received message", requestId, payload));
        BotServerMessageResponse response = new BotServerMessageResponse();
        BotServerMessageRequest request = BotServerMessageRequest.fromJson(payload);
        for (BotServerMessageRequestItem requestItem: request.values) {
            TelegramMessage telegramMessage
                    = new TelegramMessage(Long.parseLong(requestItem.objectId), requestItem.message);

            PoliceControlMsgProc proc = new PoliceControlMsgProc(telegramMessage, requestId);
            PoliceControlMsgProcResponse result = proc.processMessage();

            BotServerMessageResponseItem responseItem = new BotServerMessageResponseItem();
            responseItem.objectId = Long.toString(telegramMessage.objectId);
            responseItem.status = result.status;
            responseItem.reason = result.reason;
            response.values.add(responseItem);
        }
        String stringResponse = response.toJson();
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("X-request-id", requestId);
        Logging.log.info(String.join(" ", "Sending response", requestId, stringResponse, "time taken:", Long.toString(System.currentTimeMillis() - startTime), "ms"));
        return new ResponseEntity<>(stringResponse, responseHeaders, HttpStatus.OK);
    }
}
