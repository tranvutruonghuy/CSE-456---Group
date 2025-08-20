package domain;

import factory.PaymentMethodFactory;
import payment.PaymentMethod;

public class Customer {
    private PaymentMethod paymentMethod;

    public Customer(PaymentMethodFactory factory) {
        this.paymentMethod = factory.createPaymentMethod();
    }

    public PaymentMethod getPaymentMethod() {
        return this.paymentMethod;
    }

    public void setPaymentMethod(PaymentMethodFactory factory) {
        this.paymentMethod = factory.createPaymentMethod();
    }
}
