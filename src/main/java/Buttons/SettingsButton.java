package Buttons;

import BanksRequests.MyBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class SettingsButton {
    public void sendSettingsMenu(long chatId, MyBot bot) {
        InlineKeyboardButton button1 = new InlineKeyboardButton();
        button1.setText("Кількість знаків після коми");
        button1.setCallbackData("setting_1");

        InlineKeyboardButton button2 = new InlineKeyboardButton();
        button2.setText("Банк");
        button2.setCallbackData("setting_2");

        InlineKeyboardButton button3 = new InlineKeyboardButton();
        button3.setText("Валюти");
        button3.setCallbackData("setting_3");

        InlineKeyboardButton button4 = new InlineKeyboardButton();
        button4.setText("Час оповіщень");
        button4.setCallbackData("setting_4");

        List<InlineKeyboardButton> row1 = new ArrayList<>();
        row1.add(button1);

        List<InlineKeyboardButton> row2 = new ArrayList<>();
        row2.add(button2);

        List<InlineKeyboardButton> row3 = new ArrayList<>();
        row3.add(button3);

        List<InlineKeyboardButton> row4 = new ArrayList<>();
        row4.add(button4);

        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(row1);
        keyboard.add(row2);
        keyboard.add(row3);
        keyboard.add(row4);

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
}
