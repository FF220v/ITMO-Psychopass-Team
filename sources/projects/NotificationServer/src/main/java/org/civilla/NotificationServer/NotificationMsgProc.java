package org.civilla.NotificationServer;
import org.civilla.dataclasses.communication.notificationserver.NotificationResponse;
import org.civilla.dataclasses.database.User;
import org.civilla.kubernetes.KubeConfigLoader;
import org.civilla.storage.DatabaseConnectorUsers;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.ArrayList;


public class NotificationMsgProc {

    protected DefaultAbsSender getSender(){
        DefaultBotOptions options = new DefaultBotOptions();
        options.setMaxThreads(100);
        return new DefaultAbsSender(options) {
            @Override
            public String getBotToken() {
                return KubeConfigLoader.getBotSecrets().get("token").toString();
            }
        };
    }

    public NotificationResponse processMessage(String message, String requestId){
        NotificationResponse response = new NotificationResponse();
        try {
            DatabaseConnectorUsers usersConnector = new DatabaseConnectorUsers();
            ArrayList<User> policemans = usersConnector.getByField("isPolicemanStr", "yes", requestId);

            for (User policeman : policemans) {
                try {
                    SendMessage telegramMessage = new SendMessage().setChatId(policeman.objectId);
                    telegramMessage.setText(String.join(" ","[Notification]", message));
                    DefaultAbsSender sender = getSender();
                    sender.execute(telegramMessage);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
            response.reason = "";
            response.status = "ok";
        } catch (Exception e){
            e.printStackTrace();
            response.reason = e.getMessage();
            response.status = "exception";
        }
        return response;
    }
}