package payment.impl;

import payment.PaymentMethod;

public class Ewallet extends PaymentMethod {

    @Override
    public void printPaymentMethod() {
        System.out.println("Paying by E-wallet.");
    }

}
