package org.civilla.PoliceControlProxy;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.civilla.common.Logging;

public class PoliceControlProxy {

    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi botsApi = new TelegramBotsApi();

        Logging.log.info("Police Control bot initialized");

        try {
            botsApi.registerBot(new LongPollHandler());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
