package com.group2.factory_pattern.Exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PaymentException.class)
    public ResponseEntity<String> handlePaymentException(PaymentException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
