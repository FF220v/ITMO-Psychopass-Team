package org.civilla.PoliceControlProxy;

import org.civilla.dataclasses.communication.policecontrolserver.BotServerMessageRequest;
import org.civilla.dataclasses.communication.policecontrolserver.BotServerMessageRequestItem;
import org.civilla.kubernetes.KubeConfigLoader;
import org.civilla.requests.AsyncHttpRequestsWithRetries;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.civilla.common.Logging;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class LongPollHandler extends TelegramLongPollingBot {

    public String processUpdates(List<Update> updates){
        BotServerMessageRequest request = new BotServerMessageRequest();
        for (Update update : updates) {
            if (!update.hasMessage() || !update.getMessage().hasText())
                continue;
            BotServerMessageRequestItem requestItem = new BotServerMessageRequestItem();
            String message_text = update.getMessage().getText();
            long objectId = update.getMessage().getChatId();

            boolean restricted = false;
            if (message_text.length() > getMaxMessageSize()){
                message_text = message_text.substring(0, getMaxMessageSize());
                restricted = true;
            }

            requestItem.restricted = restricted;
            requestItem.objectId = Long.toString(objectId);
            requestItem.message = message_text;
            request.values.add(requestItem);

            Logging.log.info(String.join(" ", "Incoming message: [", message_text,
                    "] objectId:", Long.toString(objectId), restricted ? "Message was restricted!" : ""));
        }
        return request.toJson();
    }

    @Override
    public void onUpdateReceived(Update update) {
        // Implementation of library allows to use onUpdatesReceived only
    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {
        String body = processUpdates(updates);
        HashMap<String, String> headers = new HashMap<>();
        headers.put("X-request-id", UUID.randomUUID().toString());
        AsyncHttpRequestsWithRetries.post(getBotServerUrl(), headers, body);
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