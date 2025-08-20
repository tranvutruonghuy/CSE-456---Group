package com.group2.factory_pattern.DTO;

import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentRequestDto {
    String method;
    Double amount;
    String accountNumber;
}
