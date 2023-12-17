package com.forex.homework.exception;

public class AccountNotFoundException extends AbstractForexException {
    public AccountNotFoundException(String message) {
        super(message);
    }
}
