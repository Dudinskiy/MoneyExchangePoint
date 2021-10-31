package com.example.moneyexchangepoint.service;

import com.example.moneyexchangepoint.dto.inputdata.InputDataForConfirmation;
import com.example.moneyexchangepoint.dto.inputdata.InputDataForDelete;
import com.example.moneyexchangepoint.dto.inputdata.InputDataForRequest;
import com.example.moneyexchangepoint.dto.MoneyExchangeResponse;
import com.example.moneyexchangepoint.exception.ValidationException;

public interface MoneyExchangeServiceWork {

    MoneyExchangeResponse saveMoneyExchangeRequest(InputDataForRequest inputData) throws ValidationException;

    void deleteExchangeRequestByPhone(InputDataForDelete inputData);

    void deleteExchangeRequestById(Integer id);

    String confirmationExchange(InputDataForConfirmation inputData);

    float buyMoneyAmount(String saleMoney, float saleMoneyAmount, String buyMoney);
}
