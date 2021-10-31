package com.example.moneyexchangepoint.dto;

import io.swagger.v3.oas.annotations.media.Schema;


@Schema(description = "Ответ, возвращаемый сервисом после сохранения заявки на обмен валюты")
public class MoneyExchangeResponse {

    @Schema(description = "Идентификатор заявки")
    private Integer id;

    @Schema(description = "Номер мобильного телефона клиента", example = "(050) 123-45-67")
    private String userPhone;

    @Schema(description = "Сумма покупаемой клиентом валюты")
    private float buyMoneyAmount;

    @Schema(description = "ОТП-пароль (четырехзначное число)", example = "2209")
    private String password;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public float getBuyMoneyAmount() {
        return buyMoneyAmount;
    }

    public void setBuyMoneyAmount(float buyMoneyAmount) {
        this.buyMoneyAmount = buyMoneyAmount;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
