package Jsons;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public abstract class Bank {
    protected String fetchData(String apiUrl) throws IOException {
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();
        System.out.println("GET response code: " + responseCode);

        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new IOException("GET request not worked. Response code: " + responseCode);
        }
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }

    public static String formatAndRoundNumber(double number, int digitsAfterComs) {
        double scale = Math.pow(10, digitsAfterComs);
        double roundedNumber = Math.round(number * scale) / scale;
        String format = "%." + digitsAfterComs + "f";

        return String.format(format, roundedNumber);
    }

    public abstract String getExchangeRates(boolean isUsd, boolean isEur, int digitsAfterComs) throws IOException;

    protected abstract String parseResponse(String jsonResponse, boolean isUsd, boolean isEur, int digitsAfterComs);
}

