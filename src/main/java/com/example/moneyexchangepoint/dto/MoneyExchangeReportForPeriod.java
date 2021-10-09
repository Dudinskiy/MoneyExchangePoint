package com.example.moneyexchangepoint.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Отчет за указанный период")
public class MoneyExchangeReportForPeriod {

    @Schema(description = "Количество успешных операций обмена по указанной валюте")
    private int amountRequest;
    @Schema(description = "Сумма покупки по указанной валюте")
    private float buyMoney;
    @Schema(description = "Сумма продажи по указанной валюте")
    private float saleMoney;


    public int getAmountRequest() {
        return amountRequest;
    }

    public void setAmountRequest(int amountRequest) {
        this.amountRequest = amountRequest;
    }

    public float getBuyMoney() {
        return buyMoney;
    }

    public void setBuyMoney(float buyMoney) {
        this.buyMoney = buyMoney;
    }

    public float getSaleMoney() {
        return saleMoney;
    }

    public void setSaleMoney(float saleMoney) {
        this.saleMoney = saleMoney;
    }
}
