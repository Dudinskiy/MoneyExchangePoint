package com.example.moneyexchangepoint.unitTests.service;

import com.example.moneyexchangepoint.entity.ExchangeRates;
import com.example.moneyexchangepoint.repository.MoneyExchangeRequestRepository;

import com.example.moneyexchangepoint.service.util.DateAndTime;
import com.example.moneyexchangepoint.service.ExchangeRatesServiceImpl;
import com.example.moneyexchangepoint.service.MoneyExchangeServiceWorkImpl;
import com.example.moneyexchangepoint.service.util.ExchangeRequestValidator;
import com.example.moneyexchangepoint.service.util.OneTimePassword;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class MoneyExchangeServiceWorkImplTest {

    @Mock
    private MoneyExchangeRequestRepository requestRepository;
    @Mock
    private ExchangeRatesServiceImpl exchangeRatesService;
    @Mock
    private OneTimePassword password;
    @Mock
    private DateAndTime date;
    @Mock
    private ExchangeRequestValidator validator;

    private MoneyExchangeServiceWorkImpl serviceWork;

    private static ExchangeRates ratesUAH_USD;
    private static ExchangeRates ratesUAH_EUR;
    private static ExchangeRates ratesUAH_RUR;


    @BeforeAll
    public static void prepareTestData() {
        ratesUAH_USD = getExchangeRates("USD", 26.45f, 26.85f);
        ratesUAH_EUR = getExchangeRates("EUR", 30.9f, 31.5f);
        ratesUAH_RUR = getExchangeRates("RUR", 0.35f, 0.38f);
    }


    @BeforeEach
    public void init() {
        serviceWork = new MoneyExchangeServiceWorkImpl(requestRepository, exchangeRatesService, password, date, validator);
    }

    public static ExchangeRates getExchangeRates(String ccy, float buy, float sale) {
        ExchangeRates rates = new ExchangeRates();
        rates.setCcy(ccy);
        rates.setBuy(buy);
        rates.setSale(sale);

        return rates;
    }


    @Test
    public void buyMoneyAmountWhenSaleUAH() {
        float UAH_UAH = serviceWork.buyMoneyAmount("UAH", 1000.0f, "UAH");

        when(exchangeRatesService.getExchangeRates(any(), any())).thenReturn(ratesUAH_USD);
        float UAH_USD = serviceWork.buyMoneyAmount("UAH", 1000.0f, "USD");

        when(exchangeRatesService.getExchangeRates(any(), any())).thenReturn(ratesUAH_EUR);
        float UAH_EUR = serviceWork.buyMoneyAmount("UAH", 1000.0f, "EUR");

        when(exchangeRatesService.getExchangeRates(any(), any())).thenReturn(ratesUAH_RUR);
        float UAH_RUR = serviceWork.buyMoneyAmount("UAH", 1000.0f, "RUR");

        Assertions.assertEquals(UAH_UAH, 1000.0f, 0.01);
        Assertions.assertEquals(UAH_USD, 37.24f, 0.01);
        Assertions.assertEquals(UAH_EUR, 31.75f, 0.01);
        Assertions.assertEquals(UAH_RUR, 2631.58f, 0.01);
    }


    @Test
    public void buyMoneyAmountWhenSaleUSD() {
        float USD_USD = serviceWork.buyMoneyAmount("USD", 1000.0f, "USD");

        when(exchangeRatesService.getExchangeRates(any(), any())).thenReturn(ratesUAH_USD);
        float USD_UAH = serviceWork.buyMoneyAmount("USD", 1000.0f, "UAH");

        when(exchangeRatesService.getExchangeRates(any(), any())).thenReturn(ratesUAH_USD, ratesUAH_EUR);
        float USD_EUR = serviceWork.buyMoneyAmount("USD", 1000.0f, "EUR");

        when(exchangeRatesService.getExchangeRates(any(), any())).thenReturn(ratesUAH_USD, ratesUAH_RUR);
        float USD_RUR = serviceWork.buyMoneyAmount("USD", 1000.0f, "RUR");

        Assertions.assertEquals(USD_USD, 1000.0f, 0.01);
        Assertions.assertEquals(USD_UAH, 26450.0f, 0.01);
        Assertions.assertEquals(USD_EUR, 839.68f, 0.01);
        Assertions.assertEquals(USD_RUR, 69605.27f, 0.01);
    }


    @Test
    public void buyMoneyAmountWhenSaleEUR() {
        float EUR_EUR = serviceWork.buyMoneyAmount("EUR", 1000.0f, "EUR");

        when(exchangeRatesService.getExchangeRates(any(), any())).thenReturn(ratesUAH_EUR);
        float EUR_UAH = serviceWork.buyMoneyAmount("EUR", 1000.0f, "UAH");

        when(exchangeRatesService.getExchangeRates(any(), any())).thenReturn(ratesUAH_EUR, ratesUAH_USD);
        float EUR_USD = serviceWork.buyMoneyAmount("EUR", 1000.0f, "USD");

        when(exchangeRatesService.getExchangeRates(any(), any())).thenReturn(ratesUAH_EUR, ratesUAH_RUR);
        float EUR_RUR = serviceWork.buyMoneyAmount("EUR", 1000.0f, "RUR");

        Assertions.assertEquals(EUR_EUR, 1000.0f, 0.01);
        Assertions.assertEquals(EUR_UAH, 30900.0f, 0.01);
        Assertions.assertEquals(EUR_USD, 1150.84f, 0.01);
        Assertions.assertEquals(EUR_RUR, 81315.79f, 0.01);
    }


    @Test
    public void buyMoneyAmountWhenSaleRUR() {
        float RUR_RUR = serviceWork.buyMoneyAmount("RUR", 1000.0f, "RUR");

        when(exchangeRatesService.getExchangeRates(any(), any())).thenReturn(ratesUAH_RUR);
        float RUR_UAH = serviceWork.buyMoneyAmount("RUR", 1000.0f, "UAH");

        when(exchangeRatesService.getExchangeRates(any(), any())).thenReturn(ratesUAH_RUR, ratesUAH_USD);
        float RUR_USD = serviceWork.buyMoneyAmount("RUR", 1000.0f, "USD");

        when(exchangeRatesService.getExchangeRates(any(), any())).thenReturn(ratesUAH_RUR, ratesUAH_EUR);
        float RUR_EUR = serviceWork.buyMoneyAmount("RUR", 1000.0f, "EUR");

        Assertions.assertEquals(RUR_RUR, 1000.0f, 0.01);
        Assertions.assertEquals(RUR_UAH, 350.0f, 0.01);
        Assertions.assertEquals(RUR_USD, 13.04f, 0.01);
        Assertions.assertEquals(RUR_EUR, 11.11f, 0.01);
    }
}
