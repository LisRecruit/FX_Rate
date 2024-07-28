package BotUtils;

import BanksRequests.MyBot;
import Buttons.AllButtons;

import java.time.LocalTime;
import java.util.Timer;
import java.util.TimerTask;

public class User {

    public User(long chatId) {
        this.chatId=chatId;
    }

    private final long chatId;
    private String bank = "mono";
    private int digitsAfterComs = 4;
    private boolean isUsd = true;
    private boolean isEur = false;


    private boolean enableNotifications = true;
    private LocalTime userNotificationTime = LocalTime.of(20, 11);

    public boolean isUsdEnable(){
        return isUsd;
    }
    public boolean isEurEnable(){
        return isEur;
    }
    public void setUsd (boolean isUsd){
        this.isUsd=isUsd;
    }
    public void setEur (boolean isEur){
        this.isEur=isEur;
    }


    public String getBank() {
        return bank;
    }

    public int getDigitsAfterComs() {
        return digitsAfterComs;
    }


    public boolean isEnableNotifications() {
        return enableNotifications;
    }

    public LocalTime getUserNotificationTime() {
        return userNotificationTime;
    }


    public void setBank(String bank) {
        this.bank = bank;
    }

    public void setDigitsAfterComs(int digitsAfterComs) {
        this.digitsAfterComs = digitsAfterComs;
    }

    public long getChatId() {
        return chatId;
    }

    public void setEnableNotifications(boolean enableNotifications) {
        this.enableNotifications = enableNotifications;
    }

    public void setUserNotificationTime(LocalTime userNotificationTime) {
        this.userNotificationTime = userNotificationTime;
    }

    public void switchNotification (){
        if (this.isEnableNotifications()==true){
            this.setEnableNotifications(false);
        } else {
            this.setEnableNotifications(true);
        }
    }



    public class NotificatorUser {
        public NotificatorUser(MyBot bot){
            this.bot=bot;
        }
        private final MyBot bot;
        private Timer taskTimer = new Timer();
        private SendNotification sendNotification = new SendNotification();

        long delay = getDelayUntil(userNotificationTime);
        long period = 24 * 60 * 60 * 1000; //24h

        public void startTimer(){

            taskTimer.scheduleAtFixedRate(sendNotification, delay, period);
        }

        public class SendNotification extends TimerTask {

            private final AllButtons sendInfo = new AllButtons();
            @Override
            public void run() {
                sendInfo.sendInformation(chatId, bot);
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

}

