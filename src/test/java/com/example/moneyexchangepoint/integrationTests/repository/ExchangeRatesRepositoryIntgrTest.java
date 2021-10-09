package com.example.moneyexchangepoint.integrationTests.repository;

import com.example.moneyexchangepoint.entity.ExchangeRates;
import com.example.moneyexchangepoint.repository.ExchangeRatesRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)

public class ExchangeRatesRepositoryIntgrTest {

    @Autowired
    ExchangeRatesRepository ratesRepository;

    private static ArrayList<ExchangeRates> arrayRates;


    @BeforeAll
    public static void prepareTestData() {
        arrayRates = new ArrayList<>();

        arrayRates.add(getExchangeRates("USD", "2021-09-22"));
        arrayRates.add(getExchangeRates("EUR", "2021-09-22"));
        arrayRates.add(getExchangeRates("RUR", "2021-09-22"));
    }

    public static ExchangeRates getExchangeRates(String ccy, String date) {
        ExchangeRates rates = new ExchangeRates();
        rates.setCcy(ccy);
        rates.setDate(date);

        return rates;
    }


    @Test
    public void findRatesByCcyAndDate() {
        ratesRepository.saveAll(arrayRates);

        ExchangeRates rates = ratesRepository.findByCcyAndDate("USD", "2021-09-22");

        Assertions.assertEquals(rates.getCcy(), "USD");
        Assertions.assertEquals(rates.getDate(), "2021-09-22");
    }
}
