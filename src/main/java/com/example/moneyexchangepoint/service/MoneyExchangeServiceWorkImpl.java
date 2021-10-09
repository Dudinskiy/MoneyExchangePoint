package com.example.moneyexchangepoint.service;

import com.example.moneyexchangepoint.dto.MoneyExchangeResponse;
import com.example.moneyexchangepoint.entity.ExchangeRates;
import com.example.moneyexchangepoint.entity.MoneyExchangeRequest;
import com.example.moneyexchangepoint.exception.ValidationException;
import com.example.moneyexchangepoint.repository.MoneyExchangeRequestRepository;
import com.example.moneyexchangepoint.service.util.DateAndTime;
import com.example.moneyexchangepoint.service.util.ExchangeRequestValidator;
import com.example.moneyexchangepoint.service.util.OneTimePassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MoneyExchangeServiceWorkImpl implements MoneyExchangeServiceWork {

    MoneyExchangeRequestRepository requestRepository;
    ExchangeRatesServiceImpl exchangeRatesService;
    OneTimePassword password;
    DateAndTime date;
    ExchangeRequestValidator validator;

    @Autowired
    public MoneyExchangeServiceWorkImpl(MoneyExchangeRequestRepository requestRepository,
                                        ExchangeRatesServiceImpl exchangeRatesService,
                                        OneTimePassword password,
                                        DateAndTime date,
                                        ExchangeRequestValidator validator) {
        this.requestRepository = requestRepository;
        this.exchangeRatesService = exchangeRatesService;
        this.password = password;
        this.date = date;
        this.validator = validator;
    }


    @Override
    public MoneyExchangeResponse saveMoneyExchangeRequest(String userName, String userPhone, String saleMoney,
                                                          float saleMoneyAmount, String buyMoney) throws ValidationException {

        validator.validateExchangeRequest(userPhone, saleMoney, saleMoneyAmount, buyMoney);

        MoneyExchangeRequest request = new MoneyExchangeRequest();

        request.setUserName(userName);
        request.setUserPhone(userPhone);
        request.setSaleMoney(saleMoney);
        request.setAmountSaleMoney(saleMoneyAmount);
        request.setBuyMoney(buyMoney);
        request.setAmountBuyMoney(buyMoneyAmount(saleMoney, saleMoneyAmount, buyMoney));
        request.setDate(date.getDate());
        request.setTime(date.getTime());
        request.setPassword(password.getPassword());
        request.setState("Новая");

        MoneyExchangeRequest requestSaved = requestRepository.save(request);

        MoneyExchangeResponse response = new MoneyExchangeResponse();

        response.setId(requestSaved.getId());
        response.setUserPhone(requestSaved.getUserPhone());
        response.setBuyMoneyAmount(requestSaved.getAmountBuyMoney());
        response.setPassword(requestSaved.getPassword());

        return response;
    }

    @Override
    public void deleteMoneyExchangeRequest(String userPhone) {
        ArrayList<MoneyExchangeRequest> requestArrayList = requestRepository.findAllByUserPhoneAndState(userPhone, "Новая");
        requestRepository.deleteAll(requestArrayList);
    }

    @Override
    public String confirmationExchange(Integer id, String password) {
        String response;
        MoneyExchangeRequest request = requestRepository.getById(id);
        if (password.equals(request.getPassword())) {
            response = "Пароль верный";
            request.setState("Выполнена");
            request.setTime(date.getTime());
        } else {
            response = "Пароль неверный";
            request.setState("Отменена");
        }
        requestRepository.save(request);

        return response;
    }

    @Override
    public float buyMoneyAmount(String saleMoney, float saleMoneyAmount, String buyMoney) {
        float buyMoneyAmount;

        if (saleMoney.equals(buyMoney)) {
            buyMoneyAmount = saleMoneyAmount;

        } else if (saleMoney.equals("UAH") || buyMoney.equals("UAH")) {
            if (saleMoney.equals("UAH")) {
                ExchangeRates rates = exchangeRatesService.getExchangeRates(buyMoney, date.getDate());
                buyMoneyAmount = saleMoneyAmount / rates.getSale();
            } else {
                ExchangeRates rates = exchangeRatesService.getExchangeRates(saleMoney, date.getDate());
                buyMoneyAmount = saleMoneyAmount * rates.getBuy();
            }

        } else {
            ExchangeRates ratesSale = exchangeRatesService.getExchangeRates(saleMoney, date.getDate());
            ExchangeRates ratesBuy = exchangeRatesService.getExchangeRates(buyMoney, date.getDate());
            buyMoneyAmount = saleMoneyAmount * (ratesSale.getBuy() / ratesBuy.getSale());
        }

        return Math.round(buyMoneyAmount * 100) / 100f;
    }
}
