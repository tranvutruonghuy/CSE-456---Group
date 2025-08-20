package com.group2.factory_pattern.Service;

import com.group2.factory_pattern.DTO.*;

public interface PaymentService {
    PaymentResponseDto pay(PaymentRequestDto request);
}
