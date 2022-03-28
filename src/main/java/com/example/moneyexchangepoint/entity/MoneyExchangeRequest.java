package com.example.moneyexchangepoint.entity;

import javax.persistence.*;

@Entity
@Table(name = "exchange_request")
public class MoneyExchangeRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column
    private String userName;

    @Column
    private String userPhone;

    @Column
    private String saleMoney;

    @Column
    private float saleMoneyAmount;

    @Column
    private String buyMoney;

    @Column
    private float buyMoneyAmount;

    @Column
    private String date;

    @Column
    private String time;

    @Column
    private String password;

    @Column
    private String state;

    public MoneyExchangeRequest() {
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public void setSaleMoneyAmount(float amountSaleMoney) {
        this.saleMoneyAmount = amountSaleMoney;
    }

    public String getBuyMoney() {
        return buyMoney;
    }

    public void setBuyMoney(String buyMoney) {
        this.buyMoney = buyMoney;
    }

    public float getBuyMoneyAmount() {
        return buyMoneyAmount;
    }

    public void setBuyMoneyAmount(float amountBuyMoney) {
        this.buyMoneyAmount = amountBuyMoney;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
