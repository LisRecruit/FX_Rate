package Jsons;

import java.io.IOException;

public class GettingExchangeRates {
    public String getExchangeRates(String bank) throws IOException {
        Bank service;
        switch (bank) {
            case "/privat24" -> service = new Privat24();
            case "/mono" -> service = new MonoBank();
            case "/nbu" -> service = new NBU();
            default -> throw new IllegalArgumentException("Unsupported bank: " + bank);
        }
        return service.getExchangeRates();
    }
}
