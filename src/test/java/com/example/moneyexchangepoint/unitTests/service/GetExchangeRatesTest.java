package com.example.moneyexchangepoint.unitTests.service;

import com.example.moneyexchangepoint.service.util.DateAndTime;
import com.example.moneyexchangepoint.service.util.GetExchangeRates;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class GetExchangeRatesTest {

    GetExchangeRates rates;
    @Mock
    DateAndTime date;

    private String urlAPI = "https://api.privatbank.ua/p24api/pubinfo?json&exchange&coursid=5";

    @BeforeEach
    public void init() {
        rates = new GetExchangeRates(date);
    }


    @Test
    public void getJSONTest() {
        String json = rates.getJSON(urlAPI);
        Assertions.assertNotNull(json);
        System.out.println(json);
    }
}
