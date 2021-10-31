package com.example.moneyexchangepoint.service;

import com.example.moneyexchangepoint.dto.inputdata.InputDataForPeriod;
import com.example.moneyexchangepoint.dto.MoneyExchangeReportForDay;
import com.example.moneyexchangepoint.dto.MoneyExchangeReportForPeriod;
import com.example.moneyexchangepoint.entity.MoneyExchangeRequest;
import com.example.moneyexchangepoint.repository.MoneyExchangeRequestRepository;
import com.example.moneyexchangepoint.service.util.ConverterExchangeRequestToReport;
import com.example.moneyexchangepoint.service.util.DateAndTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MoneyExchangeServiceReportsImpl implements MoneyExchangeServiceReports {

    MoneyExchangeRequestRepository requestRepository;
    ConverterExchangeRequestToReport requestToReport;
    DateAndTime date;

    @Autowired
    public MoneyExchangeServiceReportsImpl(MoneyExchangeRequestRepository requestRepository,
                                           ConverterExchangeRequestToReport requestToReport,
                                           DateAndTime dateAndTime) {
        this.requestRepository = requestRepository;
        this.requestToReport = requestToReport;
        this.date = dateAndTime;
    }

    @Override
    public MoneyExchangeReportForDay getReportForCurrentDay() {
        ArrayList<MoneyExchangeRequest> arrayRequest = requestRepository.findAllByDateAndState(date.getDate(), "Выполнена");

        return requestToReport.getReportForDay(arrayRequest);
    }

    @Override
    public MoneyExchangeReportForPeriod getReportForPeriod(InputDataForPeriod inputData) {
        ArrayList<MoneyExchangeRequest> arrayRequest = requestRepository.findAllByDateBetweenAndState(inputData.getFirstDate(),
                inputData.getLastDate(), "Выполнена");

        return requestToReport.getReportForPeriod(arrayRequest, inputData.getMoney());
    }
}
