package com.example.moneyexchangepoint.service;

import com.example.moneyexchangepoint.dto.inputdata.InputDataForRates;
import com.example.moneyexchangepoint.entity.ExchangeRates;

import java.util.List;

public interface ExchangeRatesService {

    List<ExchangeRates> saveExchangeRates(InputDataForRates inputData);

    ExchangeRates getExchangeRates(String money, String date);
}
