package BotUtils;

import java.time.LocalTime;

public class User {

    public User(long chatId) {
        this.chatId=chatId;
    }

    private final long chatId;
    private String bank = "mono";
    private int digitsAfterComs = 4;
    private boolean isUsd = true;
    private boolean isEur = false;


    private boolean enableNotifications = false;
    private LocalTime specificTime = LocalTime.of(9, 0);

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

    public LocalTime getSpecificTime() {
        return specificTime;
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

    public void setSpecificTime(LocalTime specificTime) {
        this.specificTime = specificTime;
    }

    public void switchNotification (){
        if (this.isEnableNotifications()==true){
            this.setEnableNotifications(false);
        } else {
            this.setEnableNotifications(true);
        }
    }
}

