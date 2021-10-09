package com.example.moneyexchangepoint.service.util;

import com.example.moneyexchangepoint.dto.MoneyExchangeReportForDay;
import com.example.moneyexchangepoint.dto.MoneyExchangeReportForPeriod;
import com.example.moneyexchangepoint.entity.MoneyExchangeRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class ConverterExchangeRequestToReport {

    public int getAmountRequest(ArrayList<MoneyExchangeRequest> arrayRequest) {
        return arrayRequest.size();
    }

    public int getAmountRequestByMoney(ArrayList<MoneyExchangeRequest> arrayRequest, String money) {
        int amount = 0;
        for (MoneyExchangeRequest request : arrayRequest) {
            if (money.equals(request.getSaleMoney()) || money.equals(request.getBuyMoney())) {
                amount++;
            }
        }
        return amount;
    }

    public float getBuyMoney(ArrayList<MoneyExchangeRequest> arrayRequest, String money) {
        float buyMoney = 0;
        for (MoneyExchangeRequest request : arrayRequest) {
            if (money.equals(request.getSaleMoney())) {
                buyMoney = buyMoney + request.getAmountSaleMoney();
            }
        }
        return buyMoney;
    }

    public float getSaleMoney(ArrayList<MoneyExchangeRequest> arrayRequest, String money) {
        float saleMoney = 0;
        for (MoneyExchangeRequest request : arrayRequest) {
            if (money.equals(request.getBuyMoney())) {
                saleMoney = saleMoney + request.getAmountBuyMoney();
            }
        }
        return saleMoney;
    }

    public MoneyExchangeReportForDay getReportForDay(ArrayList<MoneyExchangeRequest> arrayRequest) {
        MoneyExchangeReportForDay report = new MoneyExchangeReportForDay();

        report.setAmountRequest(getAmountRequest(arrayRequest));
        report.setBuyUAH(getBuyMoney(arrayRequest, "UAH"));
        report.setSaleUAH(getSaleMoney(arrayRequest, "UAH"));
        report.setBuyUSD(getBuyMoney(arrayRequest, "USD"));
        report.setSaleUSD(getSaleMoney(arrayRequest, "USD"));
        report.setBuyEUR(getBuyMoney(arrayRequest, "EUR"));
        report.setSaleEUR(getSaleMoney(arrayRequest, "EUR"));
        report.setBuyRUR(getBuyMoney(arrayRequest, "RUR"));
        report.setSaleRUR(getSaleMoney(arrayRequest, "RUR"));

        return report;
    }

    public MoneyExchangeReportForPeriod getReportForPeriod(ArrayList<MoneyExchangeRequest> arrayRequest, String money) {
        MoneyExchangeReportForPeriod report = new MoneyExchangeReportForPeriod();

        report.setAmountRequest(getAmountRequestByMoney(arrayRequest, money));
        report.setBuyMoney(getBuyMoney(arrayRequest, money));
        report.setSaleMoney(getSaleMoney(arrayRequest, money));

        return report;
    }
}
