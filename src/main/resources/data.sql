CREATE TABLE exchange_rates
(
    id       INTEGER AUTO_INCREMENT PRIMARY KEY,
    ccy      varchar(50),
    base_ccy varchar(50),
    sale     float,
    buy      float,
    date     varchar(50),
    time     varchar(50)
);


CREATE TABLE exchange_request
(
    id                INTEGER AUTO_INCREMENT PRIMARY KEY,
    user_Name         varchar(50),
    user_Phone        varchar(50),
    sale_Money        varchar(50),
    sale_Money_Amount float,
    buy_Money         varchar(50),
    buy_Money_Amount  float,
    date              varchar(50),
    time              varchar(50),
    password          varchar(50),
    state             varchar(50)
);


