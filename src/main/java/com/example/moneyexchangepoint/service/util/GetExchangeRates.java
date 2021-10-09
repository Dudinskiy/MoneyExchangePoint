package com.example.moneyexchangepoint.service.util;

import com.example.moneyexchangepoint.dto.ExchangeRatesFromJSON;
import com.example.moneyexchangepoint.entity.ExchangeRates;
import com.example.moneyexchangepoint.service.util.DateAndTime;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

@Component
public class GetExchangeRates {
    private static float setOwnBuyRate = 1.0f;
    private static float setOwnSaleRate = 1.0f;
    private String urlAPI = "https://api.privatbank.ua/p24api/pubinfo?json&exchange&coursid=5";

    DateAndTime date;

    @Autowired
    public GetExchangeRates(DateAndTime date) {
        this.date = date;
    }

    public String getJSON(String urlAPI) {
        try {
            URL url = new URL(urlAPI);
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpsURLConnection.getInputStream()));

            StringBuilder json = new StringBuilder();

            while (bufferedReader.ready()) {
                json.append(bufferedReader.readLine());
            }
            bufferedReader.close();
            httpsURLConnection.disconnect();

            return json.toString();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<ExchangeRatesFromJSON> getArrayListFromJSON() {
        String json = getJSON(urlAPI);
        Gson gson = new Gson();
        ExchangeRatesFromJSON[] fromJSON = gson.fromJson(json, ExchangeRatesFromJSON[].class);

        return new ArrayList<>(Arrays.asList(fromJSON));
    }

    public ArrayList<ExchangeRates> getExchangeRates() {
        ArrayList<ExchangeRatesFromJSON> arrayList = getArrayListFromJSON();
        ArrayList<ExchangeRates> ratesArrayList = new ArrayList<>();
        String currentDate = date.getDate();
        String time = date.getTime();

        for (ExchangeRatesFromJSON ratesJSON : arrayList) {
            ExchangeRates rates = new ExchangeRates();

            rates.setCcy(ratesJSON.getCcy());
            rates.setBase_ccy(ratesJSON.getBase_ccy());
            rates.setBuy(Float.parseFloat(ratesJSON.getBuy()) * setOwnBuyRate);
            rates.setSale(Float.parseFloat(ratesJSON.getSale()) * setOwnSaleRate);
            rates.setDate(currentDate);
            rates.setTime(time);

            ratesArrayList.add(rates);
        }

        return ratesArrayList;
    }
}
