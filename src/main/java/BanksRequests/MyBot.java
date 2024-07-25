package BanksRequests;


import Buttons.GetInformation;
import Buttons.SettingsButton;
import Buttons.WelcomeMessage;

import Jsons.GettingExchangeRates;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


@Component
public class MyBot extends TelegramLongPollingBot {
    private final WelcomeMessage welcomeMessage = new WelcomeMessage();
    private final GetInformation getInformation = new GetInformation();
    private final SettingsButton settingsButton = new SettingsButton();

    @Override
    public void onUpdateReceived(Update update) {
        long chatId = update.getMessage().getChatId();

        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            if ("/start".equals(messageText)) {
                welcomeMessage.sendWelcomeMessage(chatId, this);
            }
        } else if (update.hasCallbackQuery()) {
            handleCallbackQuery(update.getCallbackQuery());
        }

        GettingExchangeRates bankRates = new GettingExchangeRates();
        String bank = update.getMessage().getText();
        try {
            String message = bankRates.getExchangeRates(bank);
            sendMessage(chatId, message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleCallbackQuery(CallbackQuery callbackQuery) {
        String callbackData = callbackQuery.getData();
        long chatId = callbackQuery.getMessage().getChatId();

        switch (callbackData) {
            case "info":
                getInformation.sendInformation(chatId, this);
                break;
            case "settings_menu":
                settingsButton.sendSettingsMenu(chatId, this);
                break;
            case "setting1":
                settingsButton.sendNumberOfDecimalPlaces(chatId, this);
                break;
            case "setting2":
                settingsButton.sendBank(chatId, this);
                break;
            case "setting3":
                settingsButton.sendCurrencies(chatId, this);
                break;
            case "setting4":
                settingsButton.sendNotificationTime(chatId, this);
                break;
            case "setting5":
                welcomeMessage.sendWelcomeMessage(chatId, this);
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
