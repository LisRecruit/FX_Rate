package Jsons;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;

public class Privat24 extends Bank {
    private static final String PRIVATBANK_URL = "https://api.privatbank.ua/p24api/pubinfo?json&exchange&coursid=5";

    @Override
    public String getExchangeRates(boolean isUsd, boolean isEur, int digitsAfterComs) throws IOException {
        String jsonResponse = fetchData(PRIVATBANK_URL);
        return parseResponse(jsonResponse, isUsd, isEur, digitsAfterComs);
    }

    @Override
    protected String parseResponse(String jsonResponse, boolean isUsd, boolean isEur, int digitsAfterComs) {
        StringBuilder result = new StringBuilder();
        JsonArray jsonArray = JsonParser.parseString(jsonResponse).getAsJsonArray();
        result.append("Назва банку: Приват24\n\n");
        for (JsonElement jsonElement : jsonArray) {
            String ccy = jsonElement.getAsJsonObject().get("ccy").getAsString();
            String baseCcy = jsonElement.getAsJsonObject().get("base_ccy").getAsString();
            String buy = formatAndRoundNumber(jsonElement.getAsJsonObject().get("buy").getAsDouble(), digitsAfterComs);
            String sale = formatAndRoundNumber(jsonElement.getAsJsonObject().get("sale").getAsDouble(), digitsAfterComs);
            if ((isUsd && ccy.equals("USD")) || (isEur && ccy.equals("EUR"))) {
                result.append("Валюта: ").append(ccy).append("/").append(baseCcy).append("\n")
                        .append("Купівля: ").append(buy).append("\n")
                        .append("Продаж: ").append(sale).append("\n")
                        .append("----------------------------\n");
            }
        }
        return result.toString();
    }
}
