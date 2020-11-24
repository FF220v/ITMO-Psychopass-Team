package org.civilla.storage;

import java.util.HashMap;

public class DummyBotSessionConnector implements IStorageConnector<BotSession> {
    protected static HashMap<String, BotSession> storage = new HashMap<>();

    @Override
    public BotSession get(String id) {
        return storage.get(id);
    }

    @Override
    public String update(BotSession entity) {
        storage.put(entity.chat_id, entity);
        return "ok";
    }

    @Override
    public String delete(String id) {
        return "ok";
    }
}
