package Jsons;

import BotUtils.Users;

import java.io.IOException;

public class GettingExchangeRates {
    public String getExchangeRates(Users user) throws IOException {
        Bank service;
        String bankName = user.getBank();
        String currency[] = user.getCurrency();
        switch (bankName) {
            case "/privat24" -> service = new Privat24();
            case "/mono" -> service = new MonoBank();
            case "/nbu" -> service = new NBU();
            default -> throw new IllegalArgumentException("Unsupported bank: " + bankName);
        }
        return service.getExchangeRates(currency);
    }
}
