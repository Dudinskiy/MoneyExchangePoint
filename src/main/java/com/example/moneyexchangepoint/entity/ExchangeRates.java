package com.example.moneyexchangepoint.entity;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;

@Entity
@Table(name = "exchange_rates")
@Schema(description = "Курс обмена валют")
public class ExchangeRates {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Идентификатор")
    @Column
    private Integer id;

    @Column
    @Schema(description = "Валюта обмена (доллар, евро, рубль)", example = "USD")
    private String ccy;

    @Column
    @Schema(description = "Базовая валюта (гривна)", example = "UAH")
    private String base_ccy;

    @Column
    @Schema(description = "Курс покупки", example = "26.45")
    private float buy;

    @Column
    @Schema(description = "Курс продажи", example = "26.85")
    private float sale;

    @Column
    @Schema(description = "Дата", example = "2021-09-30")
    private String date;

    @Column
    @Schema(description = "Время", example = "12:00")
    private String time;

    public ExchangeRates() {
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCcy() {
        return ccy;
    }

    public void setCcy(String ccy) {
        this.ccy = ccy;
    }

    public String getBase_ccy() {
        return base_ccy;
    }

    public void setBase_ccy(String base_ccy) {
        this.base_ccy = base_ccy;
    }

    public float getBuy() {
        return buy;
    }

    public void setBuy(float buy) {
        this.buy = buy;
    }

    public float getSale() {
        return sale;
    }

    public void setSale(float sale) {
        this.sale = sale;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
