package com.forex.homework.exception;

public class ExchangeRateNotFoundException extends AbstractForexException {
    public ExchangeRateNotFoundException(String message) {
        super(message);
    }
}
