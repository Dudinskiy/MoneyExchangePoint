package com.example.moneyexchangepoint.service.util;

import org.springframework.stereotype.Component;

@Component
public class OneTimePassword {

    public String getPassword() {
        int x = (int) Math.round((Math.random() + 0.1) * 100000);
        String str = Integer.toString(x);

        return str.substring(0, 4);
    }
}
