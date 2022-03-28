package com.example.moneyexchangepoint.unitTests.service;

import com.example.moneyexchangepoint.entity.MoneyExchangeRequest;

import com.example.moneyexchangepoint.service.util.ConverterExchangeRequestToReport;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;


public class ConverterExchangeRequestToReportTest {

    private final ConverterExchangeRequestToReport toReport = new ConverterExchangeRequestToReport();
    private static ArrayList<MoneyExchangeRequest> arrayRequest;

    @BeforeAll
    public static void prepareTestData() {
        arrayRequest = new ArrayList<>();

        arrayRequest.add(getExchangeRequest("UAH", 1000.12f, "USD", 100.12f));
        arrayRequest.add(getExchangeRequest("UAH", 1000.0f, "EUR", 90.0f));
        arrayRequest.add(getExchangeRequest("UAH", 1000.0f, "RUR", 2500.0f));
        arrayRequest.add(getExchangeRequest("USD", 100.12f, "UAH", 1000.0f));
        arrayRequest.add(getExchangeRequest("EUR", 95.0f, "USD", 110.0f));
    }

    public static MoneyExchangeRequest getExchangeRequest(String saleMoney, float amountSaleMoney, String buyMoney, float amountBuyMoney) {
        MoneyExchangeRequest request = new MoneyExchangeRequest();

        request.setSaleMoney(saleMoney);
        request.setSaleMoneyAmount(amountSaleMoney);
        request.setBuyMoney(buyMoney);
        request.setBuyMoneyAmount(amountBuyMoney);

        return request;
    }

    @Test
    public void getAmountRequestByUAH() {
        int amount = toReport.getAmountRequestByMoney(arrayRequest, "UAH");
        Assertions.assertEquals(amount, 4);
    }

    @Test
    public void getAmountRequestByUSD() {
        int amount = toReport.getAmountRequestByMoney(arrayRequest, "USD");
        Assertions.assertEquals(amount, 3);
    }

    @Test
    public void getAmountRequestByEUR() {
        int amount = toReport.getAmountRequestByMoney(arrayRequest, "EUR");
        Assertions.assertEquals(amount, 2);
    }

    @Test
    public void getAmountRequestByRUR() {
        int amount = toReport.getAmountRequestByMoney(arrayRequest, "RUR");
        Assertions.assertEquals(amount, 1);
    }


    @Test
    public void getBuyUAH() {
        float buy = toReport.getBuyMoney(arrayRequest, "UAH");
        Assertions.assertEquals(buy, 3000.12f, 0.01);
    }

    @Test
    public void getBuyUSD() {
        float buy = toReport.getBuyMoney(arrayRequest, "USD");
        Assertions.assertEquals(buy, 100.12f, 0.01);
    }

    @Test
    public void getBuyEUR() {
        float buy = toReport.getBuyMoney(arrayRequest, "EUR");
        Assertions.assertEquals(buy, 95.0f, 0.01);
    }

    @Test
    public void getBuyRUR() {
        float buy = toReport.getBuyMoney(arrayRequest, "RUR");
        Assertions.assertEquals(buy, 0.0f, 0.01);
    }


    @Test
    public void getSaleUAH() {
        float sale = toReport.getSaleMoney(arrayRequest, "UAH");
        Assertions.assertEquals(sale, 1000.0f, 0.01);
    }

    @Test
    public void getSaleUSD() {
        float sale = toReport.getSaleMoney(arrayRequest, "USD");
        Assertions.assertEquals(sale, 210.12f, 0.01);
    }

    @Test
    public void getSaleEUR() {
        float sale = toReport.getSaleMoney(arrayRequest, "EUR");
        Assertions.assertEquals(sale, 90.0f, 0.01);
    }

    @Test
    public void getSaleRUR() {
        float sale = toReport.getSaleMoney(arrayRequest, "RUR");
        Assertions.assertEquals(sale, 2500.0f, 0.01);
    }
}
