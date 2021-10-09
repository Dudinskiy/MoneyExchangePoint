package com.example.moneyexchangepoint.repository;

import com.example.moneyexchangepoint.entity.MoneyExchangeRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface MoneyExchangeRequestRepository extends JpaRepository<MoneyExchangeRequest, Integer> {

    ArrayList<MoneyExchangeRequest> findAllByUserPhoneAndState(String userPhone, String state);

    ArrayList<MoneyExchangeRequest> findAllByDateAndState(String date, String state);

    ArrayList<MoneyExchangeRequest> findAllByDateBetweenAndState(String firstDate, String lastDate, String state);
}
