package com.group2.factory_pattern.Mapper;

import com.group2.factory_pattern.DTO.PaymentRequestDto;
import com.group2.factory_pattern.DTO.PaymentResponseDto;
import com.group2.factory_pattern.Entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    Payment toEntity(PaymentRequestDto dto);

    @Mapping(target = "remainingBalance", source = "account.balance")
    @Mapping(target = "remainingCreditLimit", source = "account.creditLimit")
    PaymentResponseDto toDto(Payment entity);
}
