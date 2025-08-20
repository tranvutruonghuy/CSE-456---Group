package factory.impl;

import factory.PaymentMethodFactory;
import payment.PaymentMethod;
import payment.impl.CreditCard;

public class CreditCardFactory implements PaymentMethodFactory {

    @Override
    public PaymentMethod createPaymentMethod() {
        return new CreditCard();
    }

}
