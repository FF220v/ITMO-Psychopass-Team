package org.civilla.PoliceControlProxy;

import org.civilla.kubernetes.KubeConfigLoader;
import org.civilla.requests.AsyncHttpRequestsWithRetries;
import org.json.JSONArray;
import org.json.JSONObject;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.civilla.common.Logging;
import java.util.HashMap;
import java.util.List;

public class LongPollHandler extends TelegramLongPollingBot {

    public JSONObject processUpdates(List<Update> updates){
        JSONArray updatesJson = new JSONArray();
        for (Update update : updates) {
            if (!update.hasMessage() || !update.getMessage().hasText())
                continue;
            JSONObject updateJson = new JSONObject();
            String message_text = update.getMessage().getText();
            long chat_id = update.getMessage().getChatId();

            boolean restricted = false;
            if (message_text.length() > getMaxMessageSize()){
                message_text = message_text.substring(0, getMaxMessageSize());
                restricted = true;
            }

            updateJson.put("restricted", restricted);
            updateJson.put("chat_id", chat_id);
            updateJson.put("message", message_text);
            updatesJson.put(updateJson);

            Logging.log.info(String.join(" ", "Incoming message: [", message_text,
                    "] chat_id:", Long.toString(chat_id), restricted ? "Message was restricted!" : ""));
        }
        JSONObject result = new JSONObject();
        result.put("values", updatesJson);

        return result;
    }

    @Override
    public void onUpdateReceived(Update update) {
        // Implementation of library allows to use onUpdatesReceived only
    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {
        JSONObject body = processUpdates(updates);
        AsyncHttpRequestsWithRetries.post(getBotServerUrl(), new HashMap<>(), body.toString());
    }

    @Override
    public String getBotUsername() {
        return KubeConfigLoader.getBotSecrets().get("name").toString();
    }

    @Override
    public String getBotToken() {
        return KubeConfigLoader.getBotSecrets().get("token").toString();
    }

    public String getBotServerUrl() {
        return String.join("", "http://",
                KubeConfigLoader.servicesUrls().get(KubeConfigLoader.POLICE_CONTROL_SERVER).toString(),
                "/message");
    }

    public int getMaxMessageSize() { return 50; }
}