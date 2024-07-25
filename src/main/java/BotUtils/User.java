package BotUtils;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.sql.Time;
import java.time.LocalTime;

public class User {

    String bank = "/nbu";
    int digitsAfterComs = 2;
    String currency = "USD";
    boolean enableNotifications = false;
    LocalTime specificTime = LocalTime.of(9, 0);

    public String getBank() {
        return bank;
    }

    public int getDigitsAfterComs() {
        return digitsAfterComs;
    }

    public String getCurrency() {
        return currency;
    }

    public boolean isEnableNotifications() {
        return enableNotifications;
    }

    public LocalTime getSpecificTime() {
        return specificTime;
    }



}

