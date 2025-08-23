package com.group2.factory_pattern.Factory;

import com.group2.factory_pattern.Enum.PaymentType;
import com.group2.factory_pattern.Exception.PaymentException;
import com.group2.factory_pattern.Service.Impl.CreditCardPaymentService;
import com.group2.factory_pattern.Service.Impl.EwalletPaymentService;
import com.group2.factory_pattern.Service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class PaymentFactory {
    private final Map<PaymentType, PaymentService> services;

    @Autowired
    public PaymentFactory(List<PaymentService> serviceList) {
        Map<PaymentType, PaymentService> map = new EnumMap<>(PaymentType.class);
        for (PaymentService s : serviceList) map.put(s.type(), s);
        this.services = Collections.unmodifiableMap(map);
    }

    public PaymentService getService(PaymentType type) {
        if (!services.containsKey(type)) {
            throw new PaymentException("Unsupported payment method: " + type);
        }
        return services.get(type);
    }
}

