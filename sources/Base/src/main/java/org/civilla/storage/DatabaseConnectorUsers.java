package org.civilla.storage;

import org.civilla.dataclasses.communication.mongodbproxy.MongoDBProxyPostRequestUsers;
import org.civilla.dataclasses.communication.mongodbproxy.MongoDBProxyPostResponse;
import org.civilla.dataclasses.communication.mongodbproxy.MongoDBProxyQueryResponseUsers;
import org.civilla.dataclasses.database.User;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class DatabaseConnectorUsers {
    DatabaseConnectorBase connector = new DatabaseConnectorBase();

    public User get(String objectId, String requestId) throws ExecutionException, InterruptedException {
        MongoDBProxyQueryResponseUsers resp = MongoDBProxyQueryResponseUsers.fromJson(connector.get(objectId, requestId, "users"));
        if(resp.values.size() > 0)
            return resp.values.get(0);
        return null;
    }
    public ArrayList<User> getByField(String field, String value, String requestId) throws ExecutionException, InterruptedException {
        MongoDBProxyQueryResponseUsers resp = MongoDBProxyQueryResponseUsers.fromJson(connector.getByField(field, value, requestId, "users"));
        return resp.values;
    }

    public MongoDBProxyPostResponse update(User item, String requestId) throws ExecutionException, InterruptedException {
        MongoDBProxyPostRequestUsers req = new MongoDBProxyPostRequestUsers();
        req.values.add(item);
        return connector.update(req.toJson(), requestId, "users");
    }
}
