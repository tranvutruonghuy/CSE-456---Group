package com.group2.factory_pattern.Service;

import com.group2.factory_pattern.DTO.*;
import com.group2.factory_pattern.Enum.PaymentType;

public interface PaymentService {
    PaymentType type();
    PaymentResponseDto pay(PaymentRequestDto request);
}
