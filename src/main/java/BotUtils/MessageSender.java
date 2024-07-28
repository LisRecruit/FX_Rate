package BotUtils;

import BanksRequests.MyBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class MessageSender implements Runnable {
    private final long chatId;
    private final MyBot bot;
    private final String messageText;
    private final User user;

    public MessageSender(long chatId, MyBot bot, String messageText, User user) {
        this.chatId = chatId;
        this.bot = bot;
        this.messageText = messageText;
        this.user = user;
    }

    @Override
    public void run() {
        if (user.isEnableNotifications()) {
            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            message.setText(messageText);

            try {
                bot.execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

    }
}
