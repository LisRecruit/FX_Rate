package Jsons;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;

public class NBU extends Bank {
    private static final String NBU_URL = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json";

    @Override
    public String getExchangeRates(boolean isUsd, boolean isEur, int digitsAfterComs) throws IOException {
        String jsonResponse = fetchData(NBU_URL);
        return parseResponse(jsonResponse, isUsd, isEur, digitsAfterComs);
    }

    @Override
    protected String parseResponse(String jsonResponse, boolean isUsd, boolean isEur, int digitsAfterComs) {
        StringBuilder result = new StringBuilder();
        JsonArray jsonArray = JsonParser.parseString(jsonResponse).getAsJsonArray();
        result.append("Назва банку: Національний банк України\n\n");
        for (JsonElement jsonElement : jsonArray) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            String ccy = jsonObject.get("cc").getAsString();
            if ((isUsd && ccy.equals("USD")) || (isEur && ccy.equals("EUR"))) {
                String rate = formatAndRoundNumber(jsonObject.get("rate").getAsDouble(), digitsAfterComs);
                result.append("Валюта: ").append(ccy).append("/UAH\n")
                        .append("Купівля: ").append(rate).append("\n")
                        .append("Продаж: ").append(rate).append("\n")
                        .append("---------------------------\n");
            }
        }
        return result.toString();
    }
}
