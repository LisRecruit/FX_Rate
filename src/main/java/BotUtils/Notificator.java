package BotUtils;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Notificator {
    private TelegramLongPollingBot bot;
    private Timer timer;

    public Notificator(TelegramLongPollingBot bot) {
        this.bot = bot;
        this.timer = new Timer(true);
    }

    public void scheduleDailyNotification(long chatId, LocalTime time, String messageText) {
        TimerTask dailyTask = new TimerTask() {
            @Override
            public void run() {
                sendNotification(chatId, messageText);
            }
        };

        long delay = getDelayUntil(time);
        long period = 24 * 60 * 60 * 1000; // 24 hours in milliseconds

        timer.scheduleAtFixedRate(dailyTask, delay, period);
    }



    private void sendNotification(long chatId, String messageText) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(messageText);

        try {
            bot.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private long getDelayUntil(LocalTime notificationTime) {
        LocalTime now = LocalTime.now();
        long delay;

        if (now.isBefore(notificationTime)) {
            delay = now.until(notificationTime, java.time.temporal.ChronoUnit.MILLIS);
        } else {
            delay = now.until(notificationTime.plusHours(24), java.time.temporal.ChronoUnit.MILLIS);
        }

        return delay;
    }
}
