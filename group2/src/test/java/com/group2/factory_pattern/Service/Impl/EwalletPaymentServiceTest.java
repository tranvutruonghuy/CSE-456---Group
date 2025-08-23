package com.group2.factory_pattern.Service.Impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.group2.factory_pattern.DTO.PaymentRequestDto;
import com.group2.factory_pattern.DTO.PaymentResponseDto;
import com.group2.factory_pattern.Entity.Account;
import com.group2.factory_pattern.Entity.Payment;
import com.group2.factory_pattern.Enum.PaymentType;
import com.group2.factory_pattern.Exception.PaymentException;
import com.group2.factory_pattern.Mapper.PaymentMapper;
import com.group2.factory_pattern.Repository.AccountRepository;
import com.group2.factory_pattern.Repository.PaymentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

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
        PaymentRequestDto request = new PaymentRequestDto(100.0, "AC1");
        Account account = new Account("AC1", 500.0, 1000.0);
        Payment payment = new Payment();
        PaymentResponseDto response = new PaymentResponseDto(1L, "EWALLET", "SUCCESS", "AC1", 400.0, 1000.0);

        when(accountRepository.findByAccountNumber("AC1")).thenReturn(Optional.of(account));
        when(paymentMapper.toEntity(request)).thenReturn(payment);
        when(paymentRepository.save(payment)).thenReturn(payment);
        when(paymentMapper.toDto(payment)).thenReturn(response);

        PaymentResponseDto result = ewalletPaymentService.pay(request);

        assertEquals("SUCCESS", result.getStatus());
        assertEquals(400.0, account.getBalance());
        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void pay_declined_balance() {
        PaymentRequestDto request = new PaymentRequestDto(600.0, "AC1");
        Account account = new Account("AC1", 500.0, 1000.0);
        Payment payment = new Payment();
        PaymentResponseDto response = new PaymentResponseDto(1L, "EWALLET", "DECLINED", "AC1", 500.0, 1000.0);

        when(accountRepository.findByAccountNumber("AC1")).thenReturn(Optional.of(account));
        when(paymentMapper.toEntity(request)).thenReturn(payment);
        when(paymentRepository.save(payment)).thenReturn(payment);
        when(paymentMapper.toDto(payment)).thenReturn(response);

        PaymentResponseDto result = ewalletPaymentService.pay(request);

        assertEquals("DECLINED", result.getStatus());
        assertEquals(500.0, account.getBalance());
        assertEquals("DECLINED", payment.getStatus());
    }

    @Test
    void pay_negativeAmount() {
        PaymentRequestDto request = new PaymentRequestDto(-50.0, "AC1");

        PaymentException ex = assertThrows(PaymentException.class,
                () -> ewalletPaymentService.pay(request));

        assertEquals("Invalid payment amount", ex.getMessage());
    }

    @Test
    void pay_nullAmount() {
        PaymentRequestDto request = new PaymentRequestDto(null, "AC1");

        PaymentException ex = assertThrows(PaymentException.class,
                () -> ewalletPaymentService.pay(request));

        assertEquals("Invalid payment amount", ex.getMessage());
    }

    @Test
    void pay_accountNotFound() {
        PaymentRequestDto request = new PaymentRequestDto(50.0, "AC2");
        when(accountRepository.findByAccountNumber("AC2")).thenReturn(Optional.empty());

        PaymentException ex = assertThrows(PaymentException.class,
                () -> ewalletPaymentService.pay(request));

        assertEquals("Account not found with number: AC2", ex.getMessage());
    }

    @Test
    void pay_exceptionDuringSave() {
        PaymentRequestDto request = new PaymentRequestDto(100.0, "AC1");
        Account account = new Account("AC1", 500.0, 1000.0);
        Payment payment = new Payment();

        when(accountRepository.findByAccountNumber("AC1")).thenReturn(Optional.of(account));
        when(paymentMapper.toEntity(request)).thenReturn(payment);
        when(paymentRepository.save(any(Payment.class))).thenThrow(new RuntimeException("Payment failed"));

        PaymentException ex = assertThrows(PaymentException.class,
                () -> ewalletPaymentService.pay(request));

        assertTrue(ex.getMessage().contains("Payment failed"));
    }

    @Test
    void type_shouldReturnEwallet() {
        assertEquals(PaymentType.EWALLET,
                ewalletPaymentService.type());
    }
}
