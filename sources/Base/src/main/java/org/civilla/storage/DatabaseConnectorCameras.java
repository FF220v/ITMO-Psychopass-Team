package org.civilla.storage;

import org.civilla.dataclasses.communication.mongodbproxy.*;
import org.civilla.dataclasses.database.Camera;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class DatabaseConnectorCameras {
    DatabaseConnectorBase connector = new DatabaseConnectorBase();

    public Camera get(String objectId, String requestId) throws ExecutionException, InterruptedException {
        MongoDBProxyQueryResponseCameras resp = MongoDBProxyQueryResponseCameras.fromJson(connector.get(objectId, requestId, "cameras"));
        if(resp.values.size() > 0)
            return resp.values.get(0);
        return null;
    }
    public ArrayList<Camera> getByField(String field, String value, String requestId) throws ExecutionException, InterruptedException {
        MongoDBProxyQueryResponseCameras resp = MongoDBProxyQueryResponseCameras.fromJson(connector.getByField(field, value, requestId, "cameras"));
        return resp.values;
    }

    public MongoDBProxyPostResponse update(Camera item, String requestId) throws ExecutionException, InterruptedException {
        MongoDBProxyPostRequestCameras req = new MongoDBProxyPostRequestCameras();
        req.values.add(item);
        return connector.update(req.toJson(), requestId, "cameras");
    }
}
