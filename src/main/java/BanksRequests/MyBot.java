package BanksRequests;

import BotUtils.UserStorage;
import BotUtils.Users;
import Buttons.AllButtons;

import Jsons.GettingExchangeRates;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

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

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            long chatId = update.getMessage().getChatId();
            String messageText = update.getMessage().getText();
            if ("/start".equals(messageText)) {
                if (!UserStorage.containsUser(chatId)){
                    Users newUser = new Users(chatId);
                    UserStorage.saveUser(newUser);
                }
                setDefaultChoices(chatId);
                allButtons.sendWelcomeMessage(chatId, this);
            }
        } else if (update.hasCallbackQuery()) {
            handleCallbackQuery(update.getCallbackQuery());
        }
    }

    private void setDefaultChoices(long chatId) {
        decimalChoices.putIfAbsent(chatId, "decimal_2");
        bankChoices.putIfAbsent(chatId, "bank_nbu");
        currenciesChoices.putIfAbsent(chatId, new HashSet<>(Set.of("currency_usd")));
    }

    private void handleCallbackQuery(CallbackQuery callbackQuery) {

        String callbackData = callbackQuery.getData();
        long chatId = callbackQuery.getMessage().getChatId();
        int messageId = callbackQuery.getMessage().getMessageId();

        if (callbackData.startsWith("decimal_")) {
            decimalChoices.put(chatId, callbackData);
            allButtons.sendNumberOfDecimalPlaces(chatId, messageId, this);
        } else if (callbackData.startsWith("bank_")) {
            bankChoices.put(chatId, callbackData);
            allButtons.sendBank(chatId, messageId, this);
        }  else if (callbackData.startsWith("currency_")) {
            handleCurrencySelection(chatId, callbackData);
            allButtons.sendCurrencies(chatId, messageId, this);
        } else {
            switch (callbackData) {
                case "info":
                    allButtons.sendInformation(chatId, this);
                    break;
                case "settings_menu":
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
                    deleteMessage(chatId, messageId);
                    allButtons.sendNotificationTime(chatId, this);
                    break;
                case "backToWelcome":
                    deleteMessage(chatId, messageId);
                    break;
                case "backToSettings":
                    deleteMessage(chatId, messageId);
                    allButtons.sendSettingsMenu(chatId, this);
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

    @Override
    public String getBotUsername() {
        return "FX_RateBot";
    }

    @Override
    public String getBotToken() {
        return "6792941997:AAGEJjcgCzy-C-X6hA56ORT74LAvQGNMkiI";
    }
}
