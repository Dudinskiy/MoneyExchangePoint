package com.example.moneyexchangepoint.service;

import com.example.moneyexchangepoint.dto.MoneyExchangeResponse;
import com.example.moneyexchangepoint.exception.ValidationException;

public interface MoneyExchangeServiceWork {

    MoneyExchangeResponse saveMoneyExchangeRequest(String userName, String userPhone, String saleMoney,
                                                   float saleMoneyAmount, String buyMoney) throws ValidationException;

    void deleteMoneyExchangeRequest(String userPhone);

    String confirmationExchange(Integer id, String password);

    float buyMoneyAmount(String saleMoney, float saleMoneyAmount, String buyMoney);
}
