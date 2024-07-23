import BanksRequests.MyBot;
import WelcomeMessage.WelcomeMessage;
import org.springframework.boot.SpringApplication;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class AppLauncher {
    public static void main(String[] args) {
        SpringApplication.run(AppLauncher.class, args);
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            MyBot telegramBot = new MyBot();
            botsApi.registerBot(telegramBot);
            botsApi.registerBot(new WelcomeMessage());
        } catch (TelegramApiException e) {
            System.out.println(e.getMessage());
        }
    }
}
