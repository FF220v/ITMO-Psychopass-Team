package org.civilla.storage;

import org.civilla.dataclasses.database.BotSession;

import java.util.HashMap;

public class DummyBotSessionConnector implements IStorageConnector<BotSession> {
    protected static HashMap<String, BotSession> storage = new HashMap<>();

    @Override
    public BotSession get(String objectId) {
        return storage.get(objectId);
    }

    @Override
    public String update(BotSession entity) {
        storage.put(entity.objectId, entity);
        return "ok";
    }

    @Override
    public String delete(String objectId) {
        return "ok";
    }
}
