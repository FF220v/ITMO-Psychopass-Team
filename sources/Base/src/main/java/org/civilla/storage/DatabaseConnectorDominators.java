package org.civilla.storage;

import org.civilla.dataclasses.communication.mongodbproxy.*;
import org.civilla.dataclasses.database.Camera;
import org.civilla.dataclasses.database.Dominator;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class DatabaseConnectorDominators {
    DatabaseConnectorBase connector = new DatabaseConnectorBase();

    public Dominator get(String objectId, String requestId) throws ExecutionException, InterruptedException {
        MongoDBProxyQueryResponseDominators resp = MongoDBProxyQueryResponseDominators.fromJson(connector.get(objectId, requestId, "dominators"));
        if(resp.values.size() > 0)
            return resp.values.get(0);
        return null;
    }
    public ArrayList<Dominator> getByField(String field, String value, String requestId) throws ExecutionException, InterruptedException {
        MongoDBProxyQueryResponseDominators resp = MongoDBProxyQueryResponseDominators.fromJson(connector.getByField(field, value, requestId, "dominators"));
        return resp.values;
    }

    public MongoDBProxyPostResponse update(Dominator item, String requestId) throws ExecutionException, InterruptedException {
        MongoDBProxyPostRequestDominators req = new MongoDBProxyPostRequestDominators();
        req.values.add(item);
        return connector.update(req.toJson(), requestId, "dominators");
    }
}
