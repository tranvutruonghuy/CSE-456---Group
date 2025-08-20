package com.group2.factory_pattern.Service.Impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import com.group2.factory_pattern.DTO.*;
import com.group2.factory_pattern.Entity.*;
import com.group2.factory_pattern.Exception.PaymentException;
import com.group2.factory_pattern.Mapper.*;
import com.group2.factory_pattern.Repository.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EwalletPaymentServiceTest {

    @Mock
    AccountRepository accountRepository;

    @Mock
    PaymentRepository paymentRepository;

    @Mock
    PaymentMapper paymentMapper;

    @InjectMocks
    EwalletPaymentService ewalletPaymentService;

    @Test
    void pay_success() {
        PaymentRequestDto request = new PaymentRequestDto("E-Wallet", 100.0, "AC1");
        Account account = new Account("AC1", 500.0, 1000.0);
        Payment payment = new Payment("E-Wallet", 100.0, null);
        PaymentResponseDto response = new PaymentResponseDto(1L, "E-Wallet", "SUCCESS", "AC1", 400.0, 1000.0);

        when(accountRepository.findByAccountNumber("AC1")).thenReturn(Optional.of(account));
        when(paymentMapper.toEntity(request)).thenReturn(payment);
        when(paymentRepository.save(payment)).thenReturn(payment);
        when(paymentMapper.toDto(payment)).thenReturn(response);

        PaymentResponseDto result = ewalletPaymentService.pay(request);

        assertEquals("SUCCESS", result.getStatus());
        assertEquals(400.0, account.getBalance());
    }

    @Test
    void pay_declined_balance() {
        PaymentRequestDto request = new PaymentRequestDto("E-Wallet", 600.0, "AC1");
        Account account = new Account("AC1", 500.0, 1000.0);
        Payment payment = new Payment("E-Wallet", 600.0, null);
        PaymentResponseDto response = new PaymentResponseDto(1L, "E-Wallet", "DECLINED", "AC1", 500.0, 1000.0);

        when(accountRepository.findByAccountNumber("AC1")).thenReturn(Optional.of(account));
        when(paymentMapper.toEntity(request)).thenReturn(payment);
        when(paymentRepository.save(payment)).thenReturn(payment);
        when(paymentMapper.toDto(payment)).thenReturn(response);

        PaymentResponseDto result = ewalletPaymentService.pay(request);

        assertEquals("DECLINED", result.getStatus());
        assertEquals(500.0, account.getBalance());
    }

    @Test
    void pay_negativeAmount() {
        PaymentRequestDto request = new PaymentRequestDto("E-Wallet", -50.0, "AC1");

        PaymentException ex = assertThrows(PaymentException.class,
                () -> ewalletPaymentService.pay(request));

        assertEquals("Invalid payment amount", ex.getMessage());
    }
    @Test
    void pay_nullAmount() {
        PaymentRequestDto request = new PaymentRequestDto("E-Wallet", null, "AC1");

        PaymentException ex = assertThrows(PaymentException.class,
                () -> ewalletPaymentService.pay(request));

        assertEquals("Invalid payment amount", ex.getMessage());
    }

    @Test
    void pay_accountNotFound() {
        PaymentRequestDto request = new PaymentRequestDto("E-Wallet", 50.0, "AC2");
        when(accountRepository.findByAccountNumber("AC2")).thenReturn(Optional.empty());

        PaymentException ex = assertThrows(PaymentException.class,
                () -> ewalletPaymentService.pay(request));

        assertEquals("Account not found with number: AC2", ex.getMessage());
    }

    @Test
    void pay_exceptionDuringSave() {
        PaymentRequestDto request = new PaymentRequestDto("E-Wallet", 100.0, "AC1");
        Account account = new Account("AC1", 500.0, 1000.0);
        Payment payment = new Payment("E-Wallet", 100.0, null);

        when(accountRepository.findByAccountNumber("AC1")).thenReturn(Optional.of(account));
        when(paymentMapper.toEntity(request)).thenReturn(payment);

        when(paymentRepository.save(any(Payment.class))).thenThrow(new RuntimeException("Payment failed"));

        PaymentException ex = assertThrows(PaymentException.class,
                () -> ewalletPaymentService.pay(request));

        assertTrue(ex.getMessage().contains("Payment failed"));
    }
}
