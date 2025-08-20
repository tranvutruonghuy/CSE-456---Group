package payment.impl;

import payment.PaymentMethod;

public class CreditCard extends PaymentMethod {

    @Override
    public void printPaymentMethod() {
        System.out.println("Paying by Credit Card.");
    }

}
