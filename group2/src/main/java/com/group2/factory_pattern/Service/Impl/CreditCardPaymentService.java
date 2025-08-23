package com.group2.factory_pattern.Service.Impl;

import com.group2.factory_pattern.DTO.PaymentRequestDto;
import com.group2.factory_pattern.DTO.PaymentResponseDto;
import com.group2.factory_pattern.Entity.Account;
import com.group2.factory_pattern.Entity.Payment;
import com.group2.factory_pattern.Enum.PaymentType;
import com.group2.factory_pattern.Exception.PaymentException;
import com.group2.factory_pattern.Mapper.PaymentMapper;
import com.group2.factory_pattern.Repository.AccountRepository;
import com.group2.factory_pattern.Repository.PaymentRepository;
import com.group2.factory_pattern.Service.PaymentService;
import org.springframework.transaction.annotation.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CreditCardPaymentService implements PaymentService {
    AccountRepository accountRepository;
    PaymentRepository paymentRepository;
    PaymentMapper paymentMapper;

    @Override
    public PaymentType type() {
        return PaymentType.CREDIT_CARD;
    }

    @Transactional
    @Override
    public PaymentResponseDto pay(PaymentRequestDto request) {
        if (request.getAmount() == null || request.getAmount() <= 0) {
            throw new PaymentException("Invalid payment amount");
        }

        Account account = accountRepository.findByAccountNumber(request.getAccountNumber())
                .orElseThrow(() -> new PaymentException("Account not found with number: " + request.getAccountNumber()));

        Payment payment = paymentMapper.toEntity(request);
        payment.setAccount(account);
        payment.setMethod(type().name());

        try {
            if (account.getCreditLimit() < request.getAmount()) {
                payment.setStatus("DECLINED");
                paymentRepository.save(payment);
                return paymentMapper.toDto(payment);
            }

            account.setCreditLimit(account.getCreditLimit() - request.getAmount());
            accountRepository.save(account);

            payment.setStatus("SUCCESS");

            return paymentMapper.toDto(paymentRepository.save(payment));

        } catch (Exception e) {
            throw new PaymentException("Payment failed: " + e.getMessage());
        }
    }
}