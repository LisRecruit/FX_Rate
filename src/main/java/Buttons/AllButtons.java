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

//     public void sendNumberOfDecimalPlaces(long chatId, MyBot bot) {
//         InlineKeyboardButton button1 = new InlineKeyboardButton();
//         button1.setText("2");
//         button1.setCallbackData("set 2 digits after coma");

//         InlineKeyboardButton button2 = new InlineKeyboardButton();
//         button2.setText("3");
//         button2.setCallbackData("set 3 digits after coma");

//         InlineKeyboardButton button3 = new InlineKeyboardButton();
//         button3.setText("4");
//         button3.setCallbackData("set 4 digits after coma");


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

    public void sendCurrencies(long chatId, MyBot bot) {
        InlineKeyboardButton button1 = new InlineKeyboardButton();
        button1.setText("USD");
        button1.setCallbackData("setusd");

        InlineKeyboardButton button2 = new InlineKeyboardButton();
        button2.setText("EUR");
        button2.setCallbackData("seteur");


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

    public void sendNotificationTimeButton(long chatId, MyBot bot) {

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Оберіть час оповіщень");

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setResizeKeyboard(true);

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton("9"));
        row1.add(new KeyboardButton("10"));
        row1.add(new KeyboardButton("11"));
        keyboard.add(row1);

        KeyboardRow row2 = new KeyboardRow();
        row2.add(new KeyboardButton("12"));
        row2.add(new KeyboardButton("13"));
        row2.add(new KeyboardButton("14"));
        keyboard.add(row2);

        KeyboardRow row3 = new KeyboardRow();
        row3.add(new KeyboardButton("15"));
        row3.add(new KeyboardButton("16"));
        row3.add(new KeyboardButton("17"));
        keyboard.add(row3);

        KeyboardRow row4 = new KeyboardRow();
        row4.add(new KeyboardButton("18"));
        row4.add(new KeyboardButton("Вимкнути повідомлення"));
        keyboard.add(row4);

        keyboardMarkup.setKeyboard(keyboard);
        message.setReplyMarkup(keyboardMarkup);

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

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
}
