package BanksRequests;

import BotUtils.UserStorage;
import BotUtils.User;
import Buttons.AllButtons;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


@Component
public class MyBot extends TelegramLongPollingBot {
    private final AllButtons allButtons = new AllButtons();

    private final Map<Long, String> decimalChoices = new HashMap<>();
    private final Map<Long, String> bankChoices = new HashMap<>();
    private final Map<Long, Set<String>> currenciesChoices = new HashMap<>();
    private final Map<Long, String> timeChoices = new HashMap<>();

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            long chatId = update.getMessage().getChatId();
            String messageText = update.getMessage().getText();
            if ("/start".equals(messageText)) {
                if (!UserStorage.containsUser(chatId)) {
                    User newUser = new User(chatId);
                    UserStorage.saveUser(newUser);
                }

                setDefaultChoices(chatId); //added

                allButtons.sendWelcomeMessage(chatId, this);

                User user = UserStorage.getUser(chatId);
                User.NotificatorUser notificator = user.new NotificatorUser(this);
                notificator.startTimer();

            }

        } else if (update.hasCallbackQuery()) {
            handleCallbackQuery(update.getCallbackQuery());
        }
    }

    private void setDefaultChoices(long chatId) {
        decimalChoices.putIfAbsent(chatId, "decimal_2");
        bankChoices.putIfAbsent(chatId, "bank_nbu");
        currenciesChoices.putIfAbsent(chatId, new HashSet<>(Set.of("currency_usd")));
        timeChoices.putIfAbsent(chatId, "setNotification_9");
    }

    private void handleCallbackQuery(CallbackQuery callbackQuery) {

        String callbackData = callbackQuery.getData();
        long chatId = callbackQuery.getMessage().getChatId();
        int messageId = callbackQuery.getMessage().getMessageId();

        // Логирование для отладки
        System.out.println("Callback Data: " + callbackData);
        System.out.println("Chat ID: " + chatId);
        System.out.println("Message ID: " + messageId);


        if (callbackData.startsWith("decimal_")) {
            decimalChoices.put(chatId, callbackData);
            allButtons.sendNumberOfDecimalPlaces(chatId, messageId, this);
        } else if (callbackData.startsWith("bank_")) {
            bankChoices.put(chatId, callbackData);
            allButtons.sendBank(chatId, messageId, this);
        } else if (callbackData.startsWith("currency_")) {
            handleCurrencySelection(chatId, callbackData);
            allButtons.sendCurrencies(chatId, messageId, this);
        } else if (callbackData.startsWith("setNotification_")) {
            updateNotificationButtons(chatId, messageId, callbackData, this);
            timeChoices.put(chatId, callbackData);
            System.out.println("Time Choice Updated: " + callbackData);
            String[] str = callbackData.toString().split("_");
            LocalTime userNotificationTime = LocalTime.of(Integer.parseInt(str[1]), 00);
            UserStorage.getUser(chatId).setUserNotificationTime(userNotificationTime);
            System.out.println(UserStorage.getUser(chatId).getUserNotificationTime());
        }

        switch (callbackData) {
            case "info":
                allButtons.sendInformation(chatId, this);
                break;
            case "settings_menu":
                deleteMessage(chatId, messageId);
                allButtons.sendSettingsMenu(chatId, this);
                break;
            case "numberOfDecimalPlaces":
                allButtons.sendNumberOfDecimalPlaces(chatId, messageId, this);
                break;
            case "bank":
                allButtons.sendBank(chatId, messageId, this);
                break;
            case "currencies":
                allButtons.sendCurrencies(chatId, messageId, this);
                break;
            case "notificationTime":
//                    deleteMessage(chatId, messageId);
                allButtons.sendNotificationTimeButton(chatId, messageId, this);
                break;
            case "backToWelcome":
                deleteMessage(chatId, messageId);
                allButtons.sendWelcomeMessage(chatId, this);
                break;
            case "backToSettings":
                deleteMessage(chatId, messageId);
                allButtons.sendSettingsMenu(chatId, this);
                break;
            case "bank_nbu":
                UserStorage.getUser(chatId).setBank("nbu");
                System.out.println(UserStorage.getUser(chatId).getBank());
                break;
            case "bank_privat":
                UserStorage.getUser(chatId).setBank("privat24");
                System.out.println(UserStorage.getUser(chatId).getBank());
                break;
            case "bank_mono":
                UserStorage.getUser(chatId).setBank("mono");
                System.out.println(UserStorage.getUser(chatId).getBank());
                break;
            case "currency_eur":
                UserStorage.getUser(chatId).switchEur();
                System.out.println(UserStorage.getUser(chatId).isEurEnable());
                break;
            case "currency_usd":
                UserStorage.getUser(chatId).switchUsd();
                System.out.println(UserStorage.getUser(chatId).isUsdEnable());
                break;
            case "decimal_2":
                UserStorage.getUser(chatId).setDigitsAfterComs(2);
                break;
            case "decimal_3":
                UserStorage.getUser(chatId).setDigitsAfterComs(3);
                break;
            case "decimal_4":
                UserStorage.getUser(chatId).setDigitsAfterComs(4);
                break;

            case "switch_notifications":
                UserStorage.getUser(chatId).switchNotification();
                System.out.println("Notifications enabled "+UserStorage.getUser(chatId).isEnableNotifications());
        }
    }

    private void deleteMessage(long chatId, int messageId) {
        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setChatId(chatId);
        deleteMessage.setMessageId(messageId);

        try {
            execute(deleteMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public Map<Long, String> getDecimalChoices() {
        return decimalChoices;
    }

    public Map<Long, String> getBankChoices() {
        return bankChoices;
    }

    public Map<Long, String> getTimeChoices() {
        return timeChoices;
    }

    public Map<Long, Set<String>> getCurrenciesChoices() {
        return currenciesChoices;
    }

    private void handleCurrencySelection(long chatId, String callbackData) {
        Set<String> choices = currenciesChoices.getOrDefault(chatId, new HashSet<>());
        if (choices.contains(callbackData)) {
            choices.remove(callbackData);
        } else {
            choices.add(callbackData);
        }
        currenciesChoices.put(chatId, choices);
    }

    private void updateNotificationButtons(long chatId, int messageId, String selectedTime, MyBot bot) {
        timeChoices.put(chatId, selectedTime);
        allButtons.sendNotificationTimeButton(chatId, messageId, bot);
    }

    @Override
    public String getBotUsername() {
        return "FX_RateBot";
    }

    @Override
    public String getBotToken() {
        return "6792941997:AAGEJjcgCzy-C-X6hA56ORT74LAvQGNMkiI";
    }
}
