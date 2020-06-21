import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Hashtable;
import java.util.List;

public class PoliceControlBot extends TelegramLongPollingBot {

    Hashtable<String, BotSession> sessionsTable;

    PoliceControlBot(){
        this.sessionsTable = new Hashtable<>();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {

            String message_text = update.getMessage().getText();
            long chat_id = update.getMessage().getChatId();

            Logging.log.info(String.join(" ", "Incoming message:", message_text,
                    "From chat_id:", Long.toString(chat_id)));

            BotSession session = getBotSession(chat_id);

            SendMessage message = new SendMessage().setChatId(chat_id);
            String response = "";

            boolean canGoBack = message_text.equals("back") && (session.currentCommand.options != null && Helpers.inArrayLower(message_text, session.currentCommand.options));
            if (canGoBack)
                session.setCurrentCommand(session.currentCommand.prevCmd);
            else if (session.currentCommand.action != null)
                response = session.currentCommand.action.execute(session, message_text);

                message.setText(String.join("\n", response, session.currentCommand.outputText));
                message.setReplyMarkup(session.currentCommand.commandKeyboard);

                Logging.log.info(String.join(" ", "Sending response:", message.getText()));

                try {
                    execute(message); // Sending our message object to user
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }

    public BotSession getBotSession(long chat_id){
        BotSession session = sessionsTable.getOrDefault(Long.toString(chat_id), null);
        if (session == null){
            session = new BotSession(Long.toString(chat_id), CommandTrees.basicTree);
            sessionsTable.put(Long.toString(chat_id), session);
        }
        return session;
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