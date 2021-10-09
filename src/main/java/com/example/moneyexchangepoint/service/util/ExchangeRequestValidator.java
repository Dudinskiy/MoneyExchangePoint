package com.example.moneyexchangepoint.service.util;

import com.example.moneyexchangepoint.exception.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class ExchangeRequestValidator {

    public void validateExchangeRequest(String userPhone, String saleMoney,
                                        float saleMoneyAmount, String buyMoney) throws ValidationException {
        if (userPhone.isEmpty()) {
            throw new ValidationException("Не введен номер телефона");
        }
        if (saleMoney.isEmpty()) {
            throw new ValidationException("Не введена валюта продажи");
        }
        if (buyMoney.isEmpty()) {
            throw new ValidationException("Не введена валюта покупки");
        }
        if (saleMoneyAmount < 0) {
            throw new ValidationException("Сумма продажи не может быть отрицательной");
        }
        if (!saleMoney.equals("UAH") && !saleMoney.equals("USD") && !saleMoney.equals("EUR") && !saleMoney.equals("RUR")) {
            throw new ValidationException("Неверный код валюты продажи. Допустимые значения: UAH, USD, EUR, RUR");
        }
        if (!buyMoney.equals("UAH") && !buyMoney.equals("USD") && !buyMoney.equals("EUR") && !buyMoney.equals("RUR")) {
            throw new ValidationException("Неверный код валюты покупки. Допустимые значения: UAH, USD, EUR, RUR");
        }
    }
}
