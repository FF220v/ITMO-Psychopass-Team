import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Main {

    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi botsApi = new TelegramBotsApi();

        Logging.log.info("Police Control bot initialized");

        try {
            botsApi.registerBot(new PoliceControlBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
