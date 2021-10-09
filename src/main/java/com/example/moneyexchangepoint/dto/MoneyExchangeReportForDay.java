package com.example.moneyexchangepoint.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Отчет за день")
public class MoneyExchangeReportForDay {

    @Schema(description = "Количество успешных операций обмена")
    private int amountRequest;
    @Schema(description = "Сумма купленной гривны")
    private float buyUAH;
    @Schema(description = "Сумма проданной гривны")
    private float saleUAH;
    @Schema(description = "Сумма купленного доллара")
    private float buyUSD;
    @Schema(description = "Сумма проданного доллара")
    private float saleUSD;
    @Schema(description = "Сумма купленного евро")
    private float buyEUR;
    @Schema(description = "Сумма проданного евро")
    private float saleEUR;
    @Schema(description = "Сумма купленного рубля")
    private float buyRUR;
    @Schema(description = "Сумма проданного рубля")
    private float saleRUR;

    public int getAmountRequest() {
        return amountRequest;
    }

    public void setAmountRequest(int amountRequest) {
        this.amountRequest = amountRequest;
    }

    public float getBuyUAH() {
        return buyUAH;
    }

    public void setBuyUAH(float buyUAH) {
        this.buyUAH = buyUAH;
    }

    public float getSaleUAH() {
        return saleUAH;
    }

    public void setSaleUAH(float saleUAH) {
        this.saleUAH = saleUAH;
    }

    public float getBuyUSD() {
        return buyUSD;
    }

    public void setBuyUSD(float buyUSD) {
        this.buyUSD = buyUSD;
    }

    public float getSaleUSD() {
        return saleUSD;
    }

    public void setSaleUSD(float saleUSD) {
        this.saleUSD = saleUSD;
    }

    public float getBuyEUR() {
        return buyEUR;
    }

    public void setBuyEUR(float buyEUR) {
        this.buyEUR = buyEUR;
    }

    public float getSaleEUR() {
        return saleEUR;
    }

    public void setSaleEUR(float saleEUR) {
        this.saleEUR = saleEUR;
    }

    public float getBuyRUR() {
        return buyRUR;
    }

    public void setBuyRUR(float buyRUR) {
        this.buyRUR = buyRUR;
    }

    public float getSaleRUR() {
        return saleRUR;
    }

    public void setSaleRUR(float saleRUR) {
        this.saleRUR = saleRUR;
    }
}
