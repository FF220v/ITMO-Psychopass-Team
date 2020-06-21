import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

public class PoliceControlBot extends TelegramLongPollingBot {

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message_text = update.getMessage().getText();
            Logging.log.info(String.join(" ", "Incoming message:", message_text));

            long chat_id = update.getMessage().getChatId();
            String response = null;

            switch (message_text){
                case "/start":
                    response = "/start!";
                    break;
                case "/end":
                    response = "/end!";
                    break;
                default:
                    response = "default";
            }


            Logging.log.info(String.join(" ", "Sending response:", response));
            SendMessage message = new SendMessage() // Create a message object object
                    .setChatId(chat_id)
                    .setText(response);
            try {
                execute(message); // Sending our message object to user
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {
        for (Update update:updates){
            onUpdateReceived(update);
        }
    }

    @Override
    public String getBotUsername() {
        return "Civilla_Bot";
    }

    @Override
    public String getBotToken() {
        return "1018032526:AAGvccDnJcHYl-jxbW_H1RWKuQQpmxY3gzQ";
    }
}