package Buttons;

import BanksRequests.MyBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class GetInformation {
    public void sendInformation(long chatId, MyBot bot) {   //клас для збереження інформації юзера
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Here is the information you requested!");

        try {
            bot.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
