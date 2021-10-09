package com.example.moneyexchangepoint.service;

import com.example.moneyexchangepoint.dto.MoneyExchangeReportForDay;
import com.example.moneyexchangepoint.dto.MoneyExchangeReportForPeriod;

public interface MoneyExchangeServiceReports {

    MoneyExchangeReportForDay getReportForCurrentDay();

    MoneyExchangeReportForPeriod getReportForPeriod(String money, String firstDate, String lastDate);

}
