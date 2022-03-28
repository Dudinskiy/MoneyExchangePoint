package com.example.moneyexchangepoint.controller;

import com.example.moneyexchangepoint.dto.*;
import com.example.moneyexchangepoint.dto.inputdata.*;
import com.example.moneyexchangepoint.entity.ExchangeRates;
import com.example.moneyexchangepoint.exception.ValidationException;
import com.example.moneyexchangepoint.service.ExchangeRatesServiceImpl;
import com.example.moneyexchangepoint.service.MoneyExchangeServiceReportsImpl;
import com.example.moneyexchangepoint.service.MoneyExchangeServiceWorkImpl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/exchange")
public class ControllerImpl implements Controller {

    private final ExchangeRatesServiceImpl ratesService;
    private final MoneyExchangeServiceWorkImpl serviceWork;
    private final MoneyExchangeServiceReportsImpl serviceReports;

    @Autowired
    public ControllerImpl(ExchangeRatesServiceImpl ratesService,
                          MoneyExchangeServiceWorkImpl serviceWork,
                          MoneyExchangeServiceReportsImpl serviceReports) {
        this.ratesService = ratesService;
        this.serviceWork = serviceWork;
        this.serviceReports = serviceReports;
    }

    @Override
    @PostMapping("/start")
    public List<ExchangeRates> startWork(@RequestBody InputDataForRates inputData) {
        return ratesService.saveExchangeRates(inputData);
    }

    @Override
    @PostMapping("/saveRequest")
    public MoneyExchangeResponse saveExchangeRequest(@RequestBody InputDataForRequest inputData) throws ValidationException {
        return serviceWork.saveMoneyExchangeRequest(inputData);
    }

    @Override
    @PostMapping("/confirmation")
    public String confirmationExchangeRequest(@RequestBody InputDataForConfirmation inputData) {
        return serviceWork.confirmationExchange(inputData);
    }

    @Override
    @DeleteMapping("/deleteByPhone")
    public ResponseEntity<String> deleteRequestByPhone(@RequestBody InputDataForDelete inputData) {
        serviceWork.deleteExchangeRequestByPhone(inputData);
        return new ResponseEntity<>("Неподтвержденные заявка(ки) с номером телефона " + inputData.getUserPhone() + " удалена(ны)", HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<String> deleteRequestById(@PathVariable Integer id) {
        serviceWork.deleteExchangeRequestById(id);
        return new ResponseEntity<>("Заявка c номером " + id + " удалена", HttpStatus.OK);
    }

    @Override
    @GetMapping(value = "/finish")
    public MoneyExchangeReportForDay finishWork() {
        return serviceReports.getReportForCurrentDay();
    }

    @Override
    @GetMapping("/reportForPeriod")
    public MoneyExchangeReportForPeriod getReportForPeriod(@RequestBody InputDataForPeriod inputData) {
        return serviceReports.getReportForPeriod(inputData);
    }

    @GetMapping("/home")
    public String home(){
        return "home";
    }
}
