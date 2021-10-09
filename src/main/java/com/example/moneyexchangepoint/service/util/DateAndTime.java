package com.example.moneyexchangepoint.service.util;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Component
public class DateAndTime {

    public String getDate(){
        LocalDate date = LocalDate.now();
        return date.format(DateTimeFormatter.ofPattern("u-MM-dd"));
    }

    public String getTime(){
        LocalTime time = LocalTime.now();
        return time.format(DateTimeFormatter.ofPattern("HH:mm"));
    }
}
