package Jsons;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;

public class MonoBank extends Bank {
    private static final String MONOBANK_URL = "https://api.monobank.ua/bank/currency";

    @Override
    public String getExchangeRates(String[] currency) throws IOException {
        String jsonResponse = fetchData(MONOBANK_URL);
        return parseResponse(jsonResponse, currency);
    }

    @Override
    protected String parseResponse(String jsonResponse, String[] currency) {
        StringBuilder result = new StringBuilder();
        JsonArray jsonArray = JsonParser.parseString(jsonResponse).getAsJsonArray();
        result.append("Назва банку: Монобанк\n\n");
        for (JsonElement jsonElement : jsonArray) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            int currencyCodeA = jsonObject.get("currencyCodeA").getAsInt();
            int currencyCodeB = jsonObject.get("currencyCodeB").getAsInt();
            if ((currencyCodeA == 840 && currencyCodeB == 980) || (currencyCodeA == 978 && currencyCodeB == 980)) { // USD-UAH or EUR-UAH
                String ccy = (currencyCodeA == 840) ? "USD" : "EUR";
                for(String cur : currency) {
                    if(cur.equals(ccy)){
                        String baseCcy = "UAH";
                        String rateBuy = jsonObject.get("rateBuy").getAsString();
                        String rateSell = jsonObject.get("rateSell").getAsString();
                        result.append("Валюта: ").append(ccy).append("/").append(baseCcy).append("\n")
                                .append("Купівля: ").append(rateBuy).append("\n")
                                .append("Продаж: ").append(rateSell).append("\n")
                                .append("----------------------------\n");
                    }
                }
            }
        }
        return result.toString();
    }
}
