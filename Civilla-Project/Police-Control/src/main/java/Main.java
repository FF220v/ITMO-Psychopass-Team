import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

public class Main {

    public static void main(String[] args) throws TelegramApiRequestException {
        ApiContextInitializer.init();
        TelegramBotsApi botsApi = new TelegramBotsApi();

        Logging.log.info("Police Control bot initialized");

        // Register our bot
        try {
            botsApi.registerBot(new PoliceControlBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
