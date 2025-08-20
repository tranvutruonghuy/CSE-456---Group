package factory.impl;

import factory.PaymentMethodFactory;
import payment.PaymentMethod;
import payment.impl.Ewallet;

public class EwalletFactory implements PaymentMethodFactory {

    @Override
    public PaymentMethod createPaymentMethod() {
        return new Ewallet();
    }

}
