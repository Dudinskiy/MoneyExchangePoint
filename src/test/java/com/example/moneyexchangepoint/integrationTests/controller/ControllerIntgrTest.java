package com.example.moneyexchangepoint.integrationTests.controller;

import com.example.moneyexchangepoint.controller.Controller;
import com.example.moneyexchangepoint.dto.MoneyExchangeReportForDay;
import com.example.moneyexchangepoint.dto.MoneyExchangeResponse;
import com.example.moneyexchangepoint.entity.ExchangeRates;
import com.example.moneyexchangepoint.entity.MoneyExchangeRequest;
import com.example.moneyexchangepoint.exception.ValidationException;
import com.example.moneyexchangepoint.repository.ExchangeRatesRepository;
import com.example.moneyexchangepoint.repository.MoneyExchangeRequestRepository;

import com.example.moneyexchangepoint.service.util.DateAndTime;
import com.example.moneyexchangepoint.service.ExchangeRatesServiceImpl;
import com.example.moneyexchangepoint.service.MoneyExchangeServiceReportsImpl;
import com.example.moneyexchangepoint.service.MoneyExchangeServiceWorkImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
public class ControllerIntgrTest {

    @Autowired
    MoneyExchangeRequestRepository requestRepository;
    @Autowired
    Controller controller;
    @Autowired
    MoneyExchangeServiceWorkImpl serviceWork;
    @Autowired
    MoneyExchangeServiceReportsImpl serviceReports;
    @Autowired
    ExchangeRatesServiceImpl ratesService;
    @Autowired
    ExchangeRatesRepository ratesRepository;
    @Autowired
    DateAndTime date;


    @Test
    public void getExchangeRatesAndSaveItToDB() {
        ratesService.saveExchangeRates();

        ExchangeRates rates = ratesRepository.findByCcyAndDate("USD", date.getDate());

        Assertions.assertEquals(rates.getCcy(), "USD");
        Assertions.assertEquals(rates.getDate(), date.getDate());
    }


    @Test
    public void saveRequestToDBAndGetResponse() throws ValidationException {
        ratesService.saveExchangeRates();

        MoneyExchangeResponse response = controller.saveExchangeRequest("Alex", "(099) 123-45-67",
                "UAH", 1000.0f, "USD");

        Assertions.assertEquals(response.getUserPhone(), "(099) 123-45-67");
        Assertions.assertNotNull(response.getPassword());
        Assertions.assertNotEquals(response.getBuyMoneyAmount(), 0);

        MoneyExchangeRequest savedRequest = requestRepository.getById(response.getId());

        Assertions.assertEquals(savedRequest.getState(), "Новая");
        Assertions.assertEquals(savedRequest.getUserName(), "Alex");
        Assertions.assertEquals(savedRequest.getUserPhone(), "(099) 123-45-67");
        Assertions.assertEquals(savedRequest.getSaleMoney(), "UAH");
        Assertions.assertEquals(savedRequest.getAmountSaleMoney(), 1000.0f);
        Assertions.assertEquals(savedRequest.getBuyMoney(), "USD");
    }


    @Test
    public void confirmationRequestSuccess() throws ValidationException {
        ratesService.saveExchangeRates();
        MoneyExchangeResponse response = controller.saveExchangeRequest("Alex", "(099) 123-45-67",
                "UAH", 1000.0f, "USD");

        String confResponse = controller.confirmationExchangeRequest(response.getId(), response.getPassword());

        Assertions.assertEquals(confResponse, "Пароль верный");

        MoneyExchangeRequest savedRequest = requestRepository.getById(response.getId());

        Assertions.assertEquals(savedRequest.getState(), "Выполнена");
    }


    @Test
    public void confirmationRequestFail() throws ValidationException {
        ratesService.saveExchangeRates();
        MoneyExchangeResponse response = controller.saveExchangeRequest("Alex", "(099) 123-45-67",
                "UAH", 1000.0f, "USD");

        String confResponse = controller.confirmationExchangeRequest(response.getId(), "xxxx");

        Assertions.assertEquals(confResponse, "Пароль неверный");

        MoneyExchangeRequest savedRequest = requestRepository.getById(response.getId());

        Assertions.assertEquals(savedRequest.getState(), "Отменена");
    }


    @Test
    public void deleteRequest() throws ValidationException {
        ratesService.saveExchangeRates();
        MoneyExchangeResponse response = controller.saveExchangeRequest("Alex", "(050) 123-45-67",
                "UAH", 1000.0f, "USD");

        controller.deleteExchangeRequest("(050) 123-45-67");

        Assertions.assertFalse(requestRepository.existsById(response.getId()));
    }


    @Test
    public void getReportForCurrentDay() throws ValidationException {
        ratesService.saveExchangeRates();
        MoneyExchangeResponse response_1 = controller.saveExchangeRequest("Alex", "(099) 123-45-67",
                "UAH", 1000.0f, "USD");
        controller.confirmationExchangeRequest(response_1.getId(), response_1.getPassword());
        MoneyExchangeResponse response_2 = controller.saveExchangeRequest("Dima", "(050) 123-45-67",
                "UAH", 1000.0f, "EUR");
        controller.confirmationExchangeRequest(response_2.getId(), response_2.getPassword());


        MoneyExchangeReportForDay report = controller.finishWork();

        Assertions.assertEquals(report.getAmountRequest(), 2);
        Assertions.assertEquals(report.getBuyUAH(), 2000.0f);
        Assertions.assertEquals(report.getSaleUAH(), 0);
        Assertions.assertEquals(report.getBuyUSD(), 0);
        Assertions.assertNotEquals(report.getSaleUSD(), 0);
        Assertions.assertEquals(report.getBuyEUR(), 0);
        Assertions.assertNotEquals(report.getSaleEUR(), 0);
        Assertions.assertEquals(report.getBuyRUR(), 0);
        Assertions.assertEquals(report.getSaleRUR(), 0);
    }
}
