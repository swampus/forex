package com.forex.homework.service.utils;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class DatetimeProvider {
    public Date getApplicationDate(){
        return new Date();
    }
}
