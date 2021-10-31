package com.example.moneyexchangepoint.service;

import com.example.moneyexchangepoint.dto.inputdata.InputDataForRates;
import com.example.moneyexchangepoint.entity.ExchangeRates;
import com.example.moneyexchangepoint.repository.ExchangeRatesRepository;
import com.example.moneyexchangepoint.service.util.GetExchangeRates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExchangeRatesServiceImpl implements ExchangeRatesService {


    ExchangeRatesRepository ratesRepository;
    GetExchangeRates rates;

    @Autowired
    public ExchangeRatesServiceImpl(ExchangeRatesRepository ratesRepository, GetExchangeRates rates) {
        this.ratesRepository = ratesRepository;
        this.rates = rates;
    }

    @Override
    public List<ExchangeRates> saveExchangeRates(InputDataForRates inputData) {
        ArrayList<ExchangeRates> ratesArrayList = rates.getExchangeRates(inputData);
        return ratesRepository.saveAll(ratesArrayList);
    }

    @Override
    public ExchangeRates getExchangeRates(String money, String date) {
        return ratesRepository.findByCcyAndDate(money, date);
    }
}
