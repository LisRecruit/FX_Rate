package Jsons;

import BotUtils.User;
import BotUtils.UserStorage;

import java.io.IOException;

public class GettingExchangeRates {
    public String getExchangeRates(long chatId) throws IOException {
        Bank service;
        User user = UserStorage.getUser(chatId);
        String bankName = user.getBank();
        boolean isUsd = user.isUsdEnable();
        boolean isEur = user.isEurEnable();
        int digitsAfterComs = user.getDigitsAfterComs();
        switch (bankName) {
            case "privat24" -> service = new Privat24();
            case "mono" -> service = new MonoBank();
            case "nbu" -> service = new NBU();
            default -> throw new IllegalArgumentException("Unsupported bank: " + bankName);
        }
        return service.getExchangeRates(isUsd, isEur, digitsAfterComs);
    }
}
