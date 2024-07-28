package Buttons;

import BanksRequests.MyBot;
import BotUtils.UserStorage;
import Jsons.GettingExchangeRates;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


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
        SendMessage message = new SendMessage();
        GettingExchangeRates bankRates = new GettingExchangeRates();
        message.setChatId(chatId);
        try {
            message.setText(bankRates.getExchangeRates(chatId));
        } catch (IOException e) {
            e.printStackTrace();
            message.setText("Не вдалося отримати курси валют");
        }
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
        button5.setCallbackData("back");

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

    public void sendNumberOfDecimalPlaces(long chatId, MyBot bot) {
        InlineKeyboardButton button1 = new InlineKeyboardButton();
        button1.setText("2");
        button1.setCallbackData("set 2 digits after coma");

        InlineKeyboardButton button2 = new InlineKeyboardButton();
        button2.setText("3");
        button2.setCallbackData("set 3 digits after coma");

        InlineKeyboardButton button3 = new InlineKeyboardButton();
        button3.setText("4");
        button3.setCallbackData("set 4 digits after coma");

        List<InlineKeyboardButton> row1 = new ArrayList<>();
        row1.add(button1);

        List<InlineKeyboardButton> row2 = new ArrayList<>();
        row2.add(button2);

        List<InlineKeyboardButton> row3 = new ArrayList<>();
        row3.add(button3);

        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(row1);
        keyboard.add(row2);
        keyboard.add(row3);

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(keyboard);

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Оберіть кількість знаків після коми");
        message.setReplyMarkup(inlineKeyboardMarkup);

        try {
            bot.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendBank(long chatId, MyBot bot) {
        InlineKeyboardButton button1 = new InlineKeyboardButton();
        button1.setText("НБУ");
        button1.setCallbackData("setNbu");

        InlineKeyboardButton button2 = new InlineKeyboardButton();
        button2.setText("ПриватБанк");
        button2.setCallbackData("setPrivat24");

        InlineKeyboardButton button3 = new InlineKeyboardButton();
        button3.setText("МоноБанк");
        button3.setCallbackData("setMono_bank");

        List<InlineKeyboardButton> row1 = new ArrayList<>();
        row1.add(button1);

        List<InlineKeyboardButton> row2 = new ArrayList<>();
        row2.add(button2);

        List<InlineKeyboardButton> row3 = new ArrayList<>();
        row3.add(button3);

        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(row1);
        keyboard.add(row2);
        keyboard.add(row3);

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(keyboard);

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Оберіть банк");
        message.setReplyMarkup(inlineKeyboardMarkup);

        try {
            bot.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendCurrencies(long chatId, MyBot bot) {
        InlineKeyboardButton button1 = new InlineKeyboardButton();
        button1.setText("USD");
        button1.setCallbackData("setusd");

        InlineKeyboardButton button2 = new InlineKeyboardButton();
        button2.setText("EUR");
        button2.setCallbackData("seteur");

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
        message.setText("Оберіть валюту");
        message.setReplyMarkup(inlineKeyboardMarkup);

        try {
            bot.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendNotificationTime(long chatId, MyBot bot) {

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

        try {
            bot.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
