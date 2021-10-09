package com.example.moneyexchangepoint.integrationTests.repository;

import com.example.moneyexchangepoint.entity.MoneyExchangeRequest;
import com.example.moneyexchangepoint.repository.MoneyExchangeRequestRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class MoneyExchangeRequestRepositoryIntgrTest {

    @Autowired
    MoneyExchangeRequestRepository requestRepository;

    private static ArrayList<MoneyExchangeRequest> arrayRequest;

    @BeforeAll
    public static void prepareTestData() {
        arrayRequest = new ArrayList<>();

        arrayRequest.add(getMoneyExchangeRequest("(099)123-45-60", "2021-09-22", "Выполнена"));
        arrayRequest.add(getMoneyExchangeRequest("(099)123-45-61", "2021-09-23", "Выполнена"));
        arrayRequest.add(getMoneyExchangeRequest("(099)123-45-62", "2021-09-23", "Выполнена"));
        arrayRequest.add(getMoneyExchangeRequest("(099)123-45-63", "2021-09-24", "Выполнена"));
        arrayRequest.add(getMoneyExchangeRequest("(099)123-45-64", "2021-09-24", "Отменена"));
        arrayRequest.add(getMoneyExchangeRequest("(099)123-45-65", "2021-09-25", "Выполнена"));
        arrayRequest.add(getMoneyExchangeRequest("(099)123-45-66", "2021-09-26", "Выполнена"));
        arrayRequest.add(getMoneyExchangeRequest("(099)123-45-67", "2021-09-26", "Выполнена"));
        arrayRequest.add(getMoneyExchangeRequest("(099)123-45-68", "2021-09-26", "Отменена"));
        arrayRequest.add(getMoneyExchangeRequest("(099)123-45-69", "2021-09-26", "Новая"));
    }

    public static MoneyExchangeRequest getMoneyExchangeRequest(String userPhone, String date, String state) {
        MoneyExchangeRequest request = new MoneyExchangeRequest();

        request.setUserPhone(userPhone);
        request.setDate(date);
        request.setState(state);

        return request;
    }


    @Test
    public void findRequestsByUserPhoneAndState() {
        requestRepository.saveAll(arrayRequest);

        ArrayList<MoneyExchangeRequest> requests = requestRepository.findAllByUserPhoneAndState("(099)123-45-69",
                "Новая");

        Assertions.assertEquals(requests.size(), 1);
        Assertions.assertEquals(requests.get(0).getUserPhone(), "(099)123-45-69");
        Assertions.assertEquals(requests.get(0).getState(), "Новая");
    }


    @Test
    public void findAllRequestsForCurrentDayAndState() {
        requestRepository.saveAll(arrayRequest);

        ArrayList<MoneyExchangeRequest> requests = requestRepository.findAllByDateAndState("2021-09-26",
                "Выполнена");

        Assertions.assertEquals(requests.size(), 2);
        Assertions.assertEquals(requests.get(0).getDate(), "2021-09-26");
        Assertions.assertEquals(requests.get(0).getState(), "Выполнена");
        Assertions.assertEquals(requests.get(1).getDate(), "2021-09-26");
        Assertions.assertEquals(requests.get(1).getState(), "Выполнена");
    }


    @Test
    public void findAllRequestsForPeriodAndState() {
        requestRepository.saveAll(arrayRequest);

        ArrayList<MoneyExchangeRequest> requests = requestRepository.findAllByDateBetweenAndState("2021-09-22",
                "2021-09-25", "Выполнена");

        Assertions.assertEquals(requests.size(), 5);
        Assertions.assertEquals(requests.get(0).getDate(), "2021-09-22");
        Assertions.assertEquals(requests.get(0).getState(), "Выполнена");
        Assertions.assertEquals(requests.get(1).getDate(), "2021-09-23");
        Assertions.assertEquals(requests.get(1).getState(), "Выполнена");
        Assertions.assertEquals(requests.get(2).getDate(), "2021-09-23");
        Assertions.assertEquals(requests.get(2).getState(), "Выполнена");
        Assertions.assertEquals(requests.get(3).getDate(), "2021-09-24");
        Assertions.assertEquals(requests.get(3).getState(), "Выполнена");
        Assertions.assertEquals(requests.get(4).getDate(), "2021-09-25");
        Assertions.assertEquals(requests.get(4).getState(), "Выполнена");
    }
}
