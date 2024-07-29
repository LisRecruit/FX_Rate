package Buttons;

import BanksRequests.MyBot;
import BotUtils.UserStorage;
import Jsons.GettingExchangeRates;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.User;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class AllButtons {
    public void sendWelcomeMessage(long chatId, MyBot bot) {

        InlineKeyboardButton button1 = new InlineKeyboardButton();
        button1.setText("Отримати інфо");
        button1.setCallbackData("info"); // інфа за вибором користувача

        InlineKeyboardButton button2 = new InlineKeyboardButton();
        button2.setText("Налаштування");
        button2.setCallbackData("settings_menu");

        List<InlineKeyboardButton> row1 = new ArrayList<>();
        row1.add(button1);

        List<InlineKeyboardButton> row2 = new ArrayList<>();
        row2.add(button2);

        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(row1);
        keyboard.add(row2);

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(keyboard);

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Привіт! Цей котик допоможе вам відстежувати актуальний курс валют");
        message.setReplyMarkup(inlineKeyboardMarkup);

        try {
            bot.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendInformation(long chatId, MyBot bot) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText("Назад");
        button.setCallbackData("backToWelcome");

        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(button);

        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(row);

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(keyboard);

        SendMessage message = new SendMessage();
        GettingExchangeRates bankRates = new GettingExchangeRates();
        message.setChatId(chatId);
        try {
            message.setText(bankRates.getExchangeRates(chatId));
        } catch (IOException e) {
            e.printStackTrace();
            message.setText("Не вдалося отримати курси валют");
        }
        message.setReplyMarkup(inlineKeyboardMarkup);

        try {
            bot.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendSettingsMenu(long chatId, MyBot bot) {
        InlineKeyboardButton button1 = new InlineKeyboardButton();
        button1.setText("Кількість знаків після коми");
        button1.setCallbackData("numberOfDecimalPlaces");

        InlineKeyboardButton button2 = new InlineKeyboardButton();
        button2.setText("Банк");
        button2.setCallbackData("bank");

        InlineKeyboardButton button3 = new InlineKeyboardButton();
        button3.setText("Валюти");
        button3.setCallbackData("currencies");

        InlineKeyboardButton button4 = new InlineKeyboardButton();
        button4.setText("Час оповіщень");
        button4.setCallbackData("notificationTime");

        InlineKeyboardButton button5 = new InlineKeyboardButton();
        button5.setText("Назад");
        button5.setCallbackData("backToWelcome");

        List<InlineKeyboardButton> row1 = new ArrayList<>();
        row1.add(button1);

        List<InlineKeyboardButton> row2 = new ArrayList<>();
        row2.add(button2);

        List<InlineKeyboardButton> row3 = new ArrayList<>();
        row3.add(button3);

        List<InlineKeyboardButton> row4 = new ArrayList<>();
        row4.add(button4);

        List<InlineKeyboardButton> row5 = new ArrayList<>();
        row5.add(button5);

        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(row1);
        keyboard.add(row2);
        keyboard.add(row3);
        keyboard.add(row4);
        keyboard.add(row5);

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(keyboard);

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Налаштування");
        message.setReplyMarkup(inlineKeyboardMarkup);

        try {
            bot.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    public void sendNumberOfDecimalPlaces(long chatId, int messageId, MyBot bot) {
        EditMessageText message = new EditMessageText();
        message.setChatId(chatId);
        message.setMessageId(messageId);
        message.setText("Оберіть кількість знаків після коми:");

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        String currentChoice = bot.getDecimalChoices().get(chatId);

        rows.add(createButtonRow("2", currentChoice, "decimal_2"));
        rows.add(createButtonRow("3", currentChoice, "decimal_3"));
        rows.add(createButtonRow("4", currentChoice, "decimal_4"));

        InlineKeyboardButton backButton = new InlineKeyboardButton();
        backButton.setText("Назад до налаштувань");
        backButton.setCallbackData("backToSettings");

        List<InlineKeyboardButton> rowBack = new ArrayList<>();
        rowBack.add(backButton);
        rows.add(rowBack);

        inlineKeyboardMarkup.setKeyboard(rows);
        message.setReplyMarkup(inlineKeyboardMarkup);

        try {
            bot.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendBank(long chatId, int messageId, MyBot bot) {
        EditMessageText message = new EditMessageText();
        message.setChatId(chatId);
        message.setMessageId(messageId);
        message.setText("Оберіть банк:");

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        String currentChoice = bot.getBankChoices().get(chatId);

        rows.add(createButtonRow("PrivatBank", currentChoice, "bank_privat"));
        rows.add(createButtonRow("MonoBank", currentChoice, "bank_mono"));
        rows.add(createButtonRow("NBU", currentChoice, "bank_nbu"));

        InlineKeyboardButton backButton = new InlineKeyboardButton();
        backButton.setText("Назад до налаштувань");
        backButton.setCallbackData("backToSettings");

        List<InlineKeyboardButton> rowBack = new ArrayList<>();
        rowBack.add(backButton);
        rows.add(rowBack);

        inlineKeyboardMarkup.setKeyboard(rows);
        message.setReplyMarkup(inlineKeyboardMarkup);

        try {
            bot.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    private List<InlineKeyboardButton> createButtonRow(String text, String currentChoice, String callbackData) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(currentChoice != null && currentChoice.equals(callbackData) ? text + " ✅" : text);
        button.setCallbackData(callbackData);

        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(button);
        return row;
    }

    public void sendCurrencies(long chatId, int messageId, MyBot bot) {
        EditMessageText message = new EditMessageText();
        message.setChatId(chatId);
        message.setMessageId(messageId);
        message.setText("Выберите валюту:");

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        Set<String> currentChoice = bot.getCurrenciesChoices().get(chatId);

        rows.add(createCurrenciesButtonRow("EUR", currentChoice, "currency_eur"));
        rows.add(createCurrenciesButtonRow("USD", currentChoice, "currency_usd"));

        InlineKeyboardButton backButton = new InlineKeyboardButton();
        backButton.setText("Назад до налаштувань");
        backButton.setCallbackData("backToSettings");

        List<InlineKeyboardButton> rowBack = new ArrayList<>();
        rowBack.add(backButton);
        rows.add(rowBack);

        inlineKeyboardMarkup.setKeyboard(rows);
        message.setReplyMarkup(inlineKeyboardMarkup);

        try {
            bot.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    private List<InlineKeyboardButton> createCurrenciesButtonRow(String text, Set<String> currentChoices, String callbackData) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(currentChoices != null && currentChoices.contains(callbackData) ? text + " ✅" : text);
        button.setCallbackData(callbackData);

        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(button);
        return row;
    }

    public void sendNotificationTimeButton(long chatId, int messageId, MyBot bot) {

        System.out.println("Attempting to edit message with ID: " + messageId + " in chat: " + chatId);

        EditMessageText message = new EditMessageText();
        message.setChatId(chatId);
        message.setMessageId(messageId);
        message.setText("О  котрій годині Вам надсилати актуальні курси?");

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        String currentChoice = bot.getTimeChoices().get(chatId);

        List<InlineKeyboardButton> row1 = new ArrayList<>();

        row1.add(createNotificationButtons("9", currentChoice, "setNotification_9"));
        row1.add(createNotificationButtons("10", currentChoice, "setNotification_10"));
        row1.add(createNotificationButtons("11", currentChoice, "setNotification_11"));

        List<InlineKeyboardButton> row2 = new ArrayList<>();
        row2.add(createNotificationButtons("12", currentChoice, "setNotification_12"));
        row2.add(createNotificationButtons("13", currentChoice, "setNotification_13"));
        row2.add(createNotificationButtons("14", currentChoice, "setNotification_14"));

        List<InlineKeyboardButton> row3 = new ArrayList<>();
        row3.add(createNotificationButtons("15", currentChoice, "setNotification_15"));
        row3.add(createNotificationButtons("16", currentChoice, "setNotification_16"));
        row3.add(createNotificationButtons("17", currentChoice, "setNotification_17"));

        List<InlineKeyboardButton> row4 = new ArrayList<>();
        row4.add(createNotificationButtons("18", currentChoice, "setNotification_18"));
        row4.add(createNotificationButtons(UserStorage.getUser(chatId).isEnableNotifications()?"Вимкнути повідомлення":"Увімкнути повідомлення", currentChoice, "switch_notifications"));


        rows.add(row1);
        rows.add(row2);
        rows.add(row3);
        rows.add(row4);


        InlineKeyboardButton backButton = new InlineKeyboardButton();
        backButton.setText("Назад до налаштувань");
        backButton.setCallbackData("backToSettings");

        List<InlineKeyboardButton> rowBack = new ArrayList<>();
        rowBack.add(backButton);
        rows.add(rowBack);

        inlineKeyboardMarkup.setKeyboard(rows);
        message.setReplyMarkup(inlineKeyboardMarkup);

        try {
            bot.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }




    }

    private InlineKeyboardButton createNotificationButtons(String text, String currentChoice, String callbackData) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(currentChoice != null && currentChoice.equals(callbackData) ? text + " ✅" : text);
        button.setCallbackData(callbackData);
        return button;
    }


}
