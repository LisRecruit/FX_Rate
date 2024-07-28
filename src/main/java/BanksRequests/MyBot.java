package BanksRequests;

import BotUtils.UserStorage;
import BotUtils.User;
import Buttons.AllButtons;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;




@Component
public class MyBot extends TelegramLongPollingBot {
    private final AllButtons allButtons = new AllButtons();

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            long chatId = update.getMessage().getChatId();
            String messageText = update.getMessage().getText();
            if ("/start".equals(messageText)) {
                if (!UserStorage.containsUser(chatId)){
                    User newUser = new User(chatId);
                    UserStorage.saveUser(newUser);
                }

                allButtons.sendWelcomeMessage(chatId, this);

                User user = UserStorage.getUser(chatId);
                User.NotificatorUser notificator = user.new NotificatorUser(this);
                notificator.startTimer();

            }

        } else if (update.hasCallbackQuery()) {
            handleCallbackQuery(update.getCallbackQuery());
        }
    }

    private void handleCallbackQuery(CallbackQuery callbackQuery) {
        String callbackData = callbackQuery.getData();
        long chatId = callbackQuery.getMessage().getChatId();

        switch (callbackData) {
            case "info":
                allButtons.sendInformation(chatId, this);
                break;
            case "settings_menu":
                allButtons.sendSettingsMenu(chatId, this);
                break;
            case "numberOfDecimalPlaces":
                allButtons.sendNumberOfDecimalPlaces(chatId, this);
                break;
            case "bank":
                allButtons.sendBank(chatId, this);
                break;
            case "currencies":
                allButtons.sendCurrencies(chatId, this);
                break;
            case "notificationTime":
                allButtons.sendNotificationTimeButton(chatId, this);
                break;
            case "back":
                allButtons.sendWelcomeMessage(chatId, this);
                break;
            case "setNbu":
                UserStorage.getUser(chatId).setBank("nbu");
                System.out.println(UserStorage.getUser(chatId).getBank());
                break;
            case "setPrivat24":
                UserStorage.getUser(chatId).setBank("privat24");
                System.out.println(UserStorage.getUser(chatId).getBank());
                break;
            case "setMono_bank":
                UserStorage.getUser(chatId).setBank("mono");
                System.out.println(UserStorage.getUser(chatId).getBank());
                break;
        }
    }

    @Override
    public String getBotUsername() {
        return "FX_RateBot";
    }

    @Override
    public String getBotToken() {
        return "6792941997:AAGEJjcgCzy-C-X6hA56ORT74LAvQGNMkiI";
    }

    public void sendMessage(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
