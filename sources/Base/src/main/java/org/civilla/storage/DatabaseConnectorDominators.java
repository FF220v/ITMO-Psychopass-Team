package org.civilla.storage;

import org.civilla.dataclasses.communication.mongodbproxy.MongoDBProxyPostRequestDominators;
import org.civilla.dataclasses.communication.mongodbproxy.MongoDBProxyPostResponse;
import org.civilla.dataclasses.communication.mongodbproxy.MongoDBProxyQueryResponseDominators;
import org.civilla.dataclasses.database.Dominator;
import java.util.concurrent.ExecutionException;

public class DatabaseConnectorDominators {
    DatabaseConnectorBase connector = new DatabaseConnectorBase();

    public Dominator get(String objectId, String requestId) throws ExecutionException, InterruptedException {
        MongoDBProxyQueryResponseDominators resp = MongoDBProxyQueryResponseDominators.fromJson(connector.get(objectId, requestId, "dominators"));
        if(resp.values.size() > 0)
            return resp.values.get(0);
        return null;
    }

    public MongoDBProxyPostResponse update(Dominator item, String requestId) throws ExecutionException, InterruptedException {
        MongoDBProxyPostRequestDominators req = new MongoDBProxyPostRequestDominators();
        req.values.add(item);
        return connector.update(req.toJson(), requestId, "dominators");
    }
}
