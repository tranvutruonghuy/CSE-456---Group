package com.group2.factory_pattern.Exception;

public class PaymentException extends RuntimeException {
    public PaymentException(String message) {
        super(message);
    }
}