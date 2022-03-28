package com.example.moneyexchangepoint.repository;


import com.example.moneyexchangepoint.entity.ExchangeRates;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ExchangeRatesRepository extends JpaRepository<ExchangeRates, String> {

    ExchangeRates findByCcyAndDate(String ccy, String date);


}
