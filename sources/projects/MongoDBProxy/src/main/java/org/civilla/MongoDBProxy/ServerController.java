package org.civilla.MongoDBProxy;

import com.google.gson.Gson;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import org.bson.BsonDocument;
import org.bson.Document;
import org.civilla.common.Logging;
import org.civilla.dataclasses.communication.mongodbproxy.*;
import org.civilla.dataclasses.database.DatabaseItem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class ServerController {
    @RequestMapping(value = "/users", method = RequestMethod.POST,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> usersPostHandler(@RequestBody String payload,
                                                   @RequestHeader(value = "X-request-id", required = false) String requestId){
        MongoDBProxyPostRequestUsers request = MongoDBProxyPostRequestUsers.fromJson(payload);
        return processPostRequest(requestId, payload, "Users", request.values);
    }

    @RequestMapping(value = "/botsessions", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> botSessionsPostHandler(@RequestBody String payload,
                                                         @RequestHeader(value = "X-request-id", required = false) String requestId){
        MongoDBProxyPostRequestBotSessions request = MongoDBProxyPostRequestBotSessions.fromJson(payload);
        return processPostRequest(requestId, payload, "BotSessions", request.values);
    }

    @RequestMapping(value = "/dominators", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> dominatorsPostHandler(@RequestBody String payload,
                                                        @RequestHeader(value = "X-request-id", required = false) String requestId){
        MongoDBProxyPostRequestDominators request = MongoDBProxyPostRequestDominators.fromJson(payload);
        return processPostRequest(requestId, payload, "Dominators", request.values);
    }

    @RequestMapping(value = "/cameras", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> camerasPostHandler(@RequestBody String payload,
                                                 @RequestHeader(value = "X-request-id", required = false) String requestId){
        MongoDBProxyPostRequestCameras request = MongoDBProxyPostRequestCameras.fromJson(payload);
        return processPostRequest(requestId, payload, "Cameras", request.values);
    }

    @RequestMapping(value = "/users", method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> usersPutHandler(@RequestBody String payload,
                                                   @RequestHeader(value = "X-request-id", required = false) String requestId){
        return processPutRequest(requestId, payload, "Users");
    }

    @RequestMapping(value = "/botsessions", method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> botSessionsPutHandler(@RequestBody String payload,
                                                         @RequestHeader(value = "X-request-id", required = false) String requestId){
        return processPutRequest(requestId, payload, "BotSessions");
    }

    @RequestMapping(value = "/dominators", method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> dominatorsPutHandler(@RequestBody String payload,
                                                        @RequestHeader(value = "X-request-id", required = false) String requestId){
        return processPutRequest(requestId, payload, "Dominators");
    }

    @RequestMapping(value = "/cameras", method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> camerasPutHandler(@RequestBody String payload,
                                                     @RequestHeader(value = "X-request-id", required = false) String requestId){
        return processPutRequest(requestId, payload, "Cameras");
    }


    public ResponseEntity<String> processPostRequest(String requestId, String payload, String collectionName, ArrayList<? extends DatabaseItem> items){
        MongoDBProxyPostResponse response = new MongoDBProxyPostResponse();
        response.status = "ok";
        response.reason = "";
        long startTime = System.currentTimeMillis();

        try {
            Logging.log.info(String.join(" ", "Received request POST", requestId, payload));

            MongoDatabase database = MongoSingleSession.getDatabase();
            MongoCollection<Document> collection = database.getCollection(collectionName);

            for (DatabaseItem object : items) {
                BsonDocument doc = new BsonDocument("$set", BsonDocument.parse(object.toJson()));
                collection.findOneAndUpdate(Filters.eq("objectId", object.objectId), doc, new FindOneAndUpdateOptions().upsert(true));
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.status = "exception";
            response.reason = e.getMessage();
        }
        Logging.log.info(String.join(" ", "Sending response", requestId, response.toJson(), "stringResponse", "time taken:",
                Long.toString(System.currentTimeMillis() - startTime), "ms"));

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("X-request-id", requestId);
        return new ResponseEntity<>(response.toJson(), responseHeaders, HttpStatus.OK);
    }

    public ResponseEntity<String> processPutRequest(String requestId, String payload, String collectionName){
        long startTime = System.currentTimeMillis();
        Logging.log.info(String.join(" ", "Received request PUT", requestId, payload));
        String jsonResult;
        try {
            MongoDBProxyQueryRequest request = MongoDBProxyQueryRequest.fromJson(payload);

            MongoDatabase database = MongoSingleSession.getDatabase();
            MongoCollection<Document> collection = database.getCollection(collectionName);

            ArrayList<String> docs = new ArrayList<>();
            for (Document doc : collection.find(Filters.eq(request.field, request.value))) {
                doc.remove("_id");
                docs.add(doc.toJson());
            }
            jsonResult = "{\"status\":\"ok\", \"reason\":\"\", \"dataType\": \"" +
                    collectionName + "\", \"values\": [" + String.join(", ", docs) + "]}";

            Logging.log.info(String.join(" ", "Sending response", requestId, jsonResult, "time taken:",
                    Long.toString(System.currentTimeMillis() - startTime), "ms"));
        } catch (Exception e){
            e.printStackTrace();
            MongoDBProxyQueryResponseBasic res = new MongoDBProxyQueryResponseBasic();
            res.status = "exception";
            res.reason = e.getMessage();
            jsonResult = res.toJson();
        }
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("X-request-id", requestId);
        return new ResponseEntity<>(jsonResult, responseHeaders, HttpStatus.OK);
    }
}