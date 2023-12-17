package com.forex.homework.exception;

public class AbstractForexException extends RuntimeException {

    public AbstractForexException(String message) {
        super(message);
    }

    public AbstractForexException(String message, Throwable cause) {
        super(message, cause);
    }
}
