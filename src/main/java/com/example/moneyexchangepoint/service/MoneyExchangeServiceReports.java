package com.example.moneyexchangepoint.service;

import com.example.moneyexchangepoint.dto.inputdata.InputDataForPeriod;
import com.example.moneyexchangepoint.dto.MoneyExchangeReportForDay;
import com.example.moneyexchangepoint.dto.MoneyExchangeReportForPeriod;

public interface MoneyExchangeServiceReports {

    MoneyExchangeReportForDay getReportForCurrentDay();

    MoneyExchangeReportForPeriod getReportForPeriod(InputDataForPeriod inputData);

}
