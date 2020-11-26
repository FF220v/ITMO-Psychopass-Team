package org.civilla.storage;

import org.civilla.dataclasses.communication.mongodbproxy.*;
import org.civilla.dataclasses.database.BotSession;
import java.util.concurrent.ExecutionException;

public class DatabaseConnectorBotSessions {
    DatabaseConnectorBase<BotSession> connector = new DatabaseConnectorBase<>();

    public BotSession get(String objectId, String requestId) throws ExecutionException, InterruptedException {
        MongoDBProxyQueryResponseBotSessions resp = MongoDBProxyQueryResponseBotSessions.fromJson(connector.get(objectId, requestId));
        if(resp.values.size() > 0)
            return resp.values.get(0);
        return null;
    }

    public MongoDBProxyPostResponse update(BotSession item, String requestId) throws ExecutionException, InterruptedException {
        MongoDBProxyPostRequestBotSessions req = new MongoDBProxyPostRequestBotSessions();
        req.values.add(item);
        return connector.update(req.toJson(), requestId);
    }
}
