package com.example.moneyexchangepoint.service;

import com.example.moneyexchangepoint.entity.ExchangeRates;

import java.util.List;

public interface ExchangeRatesService {

    List<ExchangeRates> saveExchangeRates();

    ExchangeRates getExchangeRates(String money, String date);
}
