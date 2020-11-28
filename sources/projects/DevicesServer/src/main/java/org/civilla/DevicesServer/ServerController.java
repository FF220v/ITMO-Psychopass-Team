package org.civilla.DevicesServer;

import org.civilla.analysis.AnalysisConnector;
import org.civilla.common.Logging;
import org.civilla.dataclasses.communication.devicesserver.DeviceRequest;
import org.civilla.dataclasses.communication.devicesserver.DominatorResponse;
import org.civilla.dataclasses.database.Camera;
import org.civilla.dataclasses.database.Dominator;
import org.civilla.storage.DatabaseConnectorCameras;
import org.civilla.storage.DatabaseConnectorDominators;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ServerController {
    @RequestMapping(value = "/dominator", method = RequestMethod.POST,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> dominatorHandler(@RequestBody String payload,
                                                 @RequestHeader(value = "X-request-id",
                                                         required = false) String requestId){
        long startTime = System.currentTimeMillis();
        Logging.log.info(String.join(" ", "Received request", requestId, payload));

        DominatorResponse response = new DominatorResponse();
        DeviceRequest request = DeviceRequest.fromJson(payload);
        try {
            Dominator device = new DatabaseConnectorDominators().get(request.deviceId, requestId);
            if(device == null) {
                response.status = "error";
                response.reason = "device unauthorized";
            } else if (!device.ownerId.equals(request.userId)) {
                response.status = "error";
                response.reason = "insufficient permissions";
            } else {
                Double psychopassValue = new AnalysisConnector().analyse(request.targetId, "Dominator", requestId);
                response.status = "ok";
                response.reason = "";
                response.usageAllowed = psychopassValue > 0.5;
            }
        } catch (Exception e){
            e.printStackTrace();
            response.status = "exception";
            response.reason = e.getMessage();
        }

        String stringResponse = response.toJson();
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("X-request-id", requestId);
        Logging.log.info(String.join(" ", "Sending response", requestId, stringResponse, "time taken:", Long.toString(System.currentTimeMillis() - startTime), "ms"));
        return new ResponseEntity<>(stringResponse, responseHeaders, HttpStatus.OK);
    }

    @RequestMapping(value = "/camera", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> camerasHandler(@RequestBody String payload,
                                                   @RequestHeader(value = "X-request-id",
                                                           required = false) String requestId){
        long startTime = System.currentTimeMillis();
        Logging.log.info(String.join(" ", "Received request", requestId, payload));

        DominatorResponse response = new DominatorResponse();
        DeviceRequest request = DeviceRequest.fromJson(payload);
        try {
            Camera device = new DatabaseConnectorCameras().get(request.deviceId, requestId);
            if (device == null) {
                response.status = "error";
                response.reason = "unauthorized camera";
            } else {
                response.status = "ok";
                response.reason = "";
                Double psychopassValue = new AnalysisConnector().analyse(request.userId, "Dominator", requestId);
            }
        } catch (Exception e){
            e.printStackTrace();
            response.status = "exception";
            response.reason = e.getMessage();
        }

        String stringResponse = response.toJson();
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("X-request-id", requestId);
        Logging.log.info(String.join(" ", "Sending response", requestId, stringResponse, "time taken:", Long.toString(System.currentTimeMillis() - startTime), "ms"));
        return new ResponseEntity<>(stringResponse, responseHeaders, HttpStatus.OK);
    }
}