package BotUtils;

import java.time.LocalTime;

public class Users {

    public Users(long chatId) {
        this.chatId=chatId;
    }

    private final long chatId;
    private String bank = "/nbu";
    private int digitsAfterComs = 2;
    private String currency[] = new String[]{"USD", ""};
    private boolean enableNotifications = false;
    private LocalTime specificTime = LocalTime.of(9, 0);

    public String getBank() {
        return bank;
    }

    public int getDigitsAfterComs() {
        return digitsAfterComs;
    }

    public String[] getCurrency() {
        return currency;
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

    public void setCurrency(String[] currency) {
        this.currency = currency;
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

