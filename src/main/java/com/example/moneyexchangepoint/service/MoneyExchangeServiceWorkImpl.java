package com.example.moneyexchangepoint.service;

import com.example.moneyexchangepoint.dto.inputdata.InputDataForConfirmation;
import com.example.moneyexchangepoint.dto.inputdata.InputDataForDelete;
import com.example.moneyexchangepoint.dto.inputdata.InputDataForRequest;
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
    public MoneyExchangeResponse saveMoneyExchangeRequest(InputDataForRequest inputData) throws ValidationException {

        validator.validateExchangeRequest(inputData.getUserPhone(), inputData.getSaleMoney(),
                inputData.getSaleMoneyAmount(), inputData.getBuyMoney());

        MoneyExchangeRequest request = new MoneyExchangeRequest();

        request.setUserName(inputData.getUserName());
        request.setUserPhone(inputData.getUserPhone());
        request.setSaleMoney(inputData.getSaleMoney());
        request.setAmountSaleMoney(inputData.getSaleMoneyAmount());
        request.setBuyMoney(inputData.getBuyMoney());
        request.setAmountBuyMoney(buyMoneyAmount(inputData.getSaleMoney(), inputData.getSaleMoneyAmount(), inputData.getBuyMoney()));
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
    public void deleteExchangeRequestByPhone(InputDataForDelete inputData) {
        ArrayList<MoneyExchangeRequest> requestArrayList = requestRepository.findAllByUserPhoneAndState(inputData.getUserPhone(), "Новая");
        requestRepository.deleteAll(requestArrayList);
    }

    @Override
    public void deleteExchangeRequestById(Integer id) {
        requestRepository.deleteById(id);
    }

    @Override
    public String confirmationExchange(InputDataForConfirmation inputData) {
        String response;
        MoneyExchangeRequest request = requestRepository.getById(inputData.getId());
        if (inputData.getPassword().equals(request.getPassword())) {
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
