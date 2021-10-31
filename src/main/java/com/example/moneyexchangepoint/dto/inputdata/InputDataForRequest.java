package com.example.moneyexchangepoint.dto.inputdata;

public class InputDataForRequest {
    private String userName;
    private String userPhone;
    private String saleMoney;
    private float saleMoneyAmount;
    private String buyMoney;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getSaleMoney() {
        return saleMoney;
    }

    public void setSaleMoney(String saleMoney) {
        this.saleMoney = saleMoney;
    }

    public float getSaleMoneyAmount() {
        return saleMoneyAmount;
    }

    public void setSaleMoneyAmount(float saleMoneyAmount) {
        this.saleMoneyAmount = saleMoneyAmount;
    }

    public String getBuyMoney() {
        return buyMoney;
    }

    public void setBuyMoney(String buyMoney) {
        this.buyMoney = buyMoney;
    }
}
