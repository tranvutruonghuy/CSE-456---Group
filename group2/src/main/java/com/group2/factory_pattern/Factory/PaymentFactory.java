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
    private final Map<PaymentType, PaymentService> services = new HashMap<>();

    @Autowired
    public PaymentFactory(List<PaymentService> serviceList) {
        for (PaymentService service : serviceList) {
            if (service instanceof EwalletPaymentService) {
                services.put(PaymentType.EWALLET, service);
            } else if (service instanceof CreditCardPaymentService) {
                services.put(PaymentType.CREDIT_CARD, service);
            }
        }
    }

    public PaymentService getService(PaymentType type) {
        if (!services.containsKey(type)) {
            throw new PaymentException("Unsupported payment method: " + type);
        }
        return services.get(type);
    }
}

