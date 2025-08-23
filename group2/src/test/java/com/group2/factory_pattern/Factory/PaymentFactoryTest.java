package com.group2.factory_pattern.Factory;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.group2.factory_pattern.DTO.*;
import com.group2.factory_pattern.Entity.*;
import com.group2.factory_pattern.Enum.PaymentType;
import com.group2.factory_pattern.Exception.PaymentException;
import com.group2.factory_pattern.Mapper.*;
import com.group2.factory_pattern.Repository.*;
import com.group2.factory_pattern.Service.Impl.CreditCardPaymentService;
import com.group2.factory_pattern.Service.Impl.EwalletPaymentService;
import com.group2.factory_pattern.Service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class PaymentFactoryTest {

    @Mock
    CreditCardPaymentService creditCardService;

    @Mock
    EwalletPaymentService ewalletService;

    PaymentFactory paymentFactory;

    @BeforeEach
    void setUp() {
        when(creditCardService.type()).thenReturn(PaymentType.CREDIT_CARD);
        when(ewalletService.type()).thenReturn(PaymentType.EWALLET);

        paymentFactory = new PaymentFactory(List.of(creditCardService, ewalletService));
    }

    @Test
    void getService_creditCard() {
        PaymentService service = paymentFactory.getService(PaymentType.CREDIT_CARD);
        assertNotNull(service);
        assertEquals(creditCardService, service);
    }

    @Test
    void getService_ewallet() {
        PaymentService service = paymentFactory.getService(PaymentType.EWALLET);
        assertNotNull(service);
        assertEquals(ewalletService, service);
    }

    @Test
    void getService_unsupported() {
        PaymentException ex = assertThrows(PaymentException.class,
                () -> paymentFactory.getService(null));

        assertTrue(ex.getMessage().contains("Unsupported payment method"));
    }
}
