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

import java.util.ArrayList;
import java.util.List;


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
//            long chatId = update.getMessage().getChatId();
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
            case "settings menu":
                settingsButton.sendSettingsMenu(chatId, this);
                break;
            case "setting_1":
                // Обработка для настройки 1
                handleSetting1(chatId);
                break;
            case "setting_2":
                // Обработка для настройки 2
                handleSetting2(chatId);
                break;
            case "setting_3":
                // Обработка для настройки 3
                handleSetting3(chatId);
                break;
            case "setting_4":
                // Обработка для настройки 4
                handleSetting4(chatId);
                break;
        }
    }

    private void handleSetting1(long chatId) {
        // Логика для настройки 1
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Setting 1 is now configured.");
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void handleSetting2(long chatId) {
        // Логика для настройки 2
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Setting 2 is now configured.");
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void handleSetting3(long chatId) {
        // Логика для настройки 3
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Setting 3 is now configured.");
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void handleSetting4(long chatId) {
        // Логика для настройки 4
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Setting 4 is now configured.");
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
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
