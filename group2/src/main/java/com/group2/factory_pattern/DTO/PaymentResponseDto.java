package com.group2.factory_pattern.DTO;

import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentResponseDto {
    Long paymentId;
    String method;
    String status;
    String accountNumber;
    Double remainingBalance;
    Double remainingCreditLimit;
}
