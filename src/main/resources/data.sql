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

INSERT INTO exchange_rates (ccy, base_ccy, sale, buy, date, time)
VALUES (null, null, 0, 0, null, null);


CREATE TABLE exchange_request
(
    id                INTEGER AUTO_INCREMENT PRIMARY KEY,
    user_Name         varchar(50),
    user_Phone        varchar(50),
    sale_Money        varchar(50),
    amount_Sale_Money float,
    buy_Money         varchar(50),
    amount_Buy_Money  float,
    date              varchar(50),
    time              varchar(50),
    password          varchar(50),
    state             varchar(50)
);

INSERT INTO exchange_request (user_Name, user_Phone, sale_Money, amount_Sale_Money, buy_Money, amount_Buy_Money,
                              date, time, password, state)
VALUES (null, null, null, 0,
        null, 0, null, null, null, null);
