package BanksRequests;

import Jsons.GettingExchangeRates;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Component
public class MyBot extends TelegramLongPollingBot {
    @Override
    public void onUpdateReceived(Update update) {
        String chatId = update.getMessage().getChatId().toString();

        System.out.println("Get message " + update.getMessage().getText());
        String userComand = update.getMessage().getText();
        switch (userComand){
            case "/start":{
                sendMessage(chatId, "Вітаю у курсах від котика");
                break;
            }
            case "/test":{
                sendMessage(chatId, "це тестове повідомлення");

            }
            case "/testButtons":{
                SendMessage response = new SendMessage(chatId,"Menu");
                ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
                List<KeyboardRow> keyboard = new ArrayList<>();

                KeyboardRow row1 = new KeyboardRow();
                row1.add(new KeyboardButton("курс приват"));
                row1.add(new KeyboardButton("row 1, button2"));
                keyboard.add(row1);

                KeyboardRow row2 = new KeyboardRow();
                row2.add(new KeyboardButton("row 2, button1 and only 1"));
                keyboard.add(row2);

                // Установка клавиатуры к сообщению
                keyboardMarkup.setKeyboard(keyboard);
                response.setReplyMarkup(keyboardMarkup);

                try {
                    execute(response); // Отправка сообщения
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }

            }
            case "курс приват": {
                GettingExchangeRates getRate = new GettingExchangeRates();
                try {
                    getRate.getExchangeRates("/privat24");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }

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

    @Override
    public String getBotUsername() {
        return "FX_RateBot";
    }

    @Override
    public String getBotToken() {
        return "6792941997:AAGEJjcgCzy-C-X6hA56ORT74LAvQGNMkiI";
    }

    public void sendMessage(String chatId, String text) {
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
