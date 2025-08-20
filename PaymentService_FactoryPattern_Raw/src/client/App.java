package client;

import domain.Customer;
import factory.PaymentMethodFactory;
import factory.impl.CreditCardFactory;
import factory.impl.EwalletFactory;
import payment.PaymentMethod;

public class App {
    public static void main(String[] args) throws Exception {
        PaymentMethodFactory ewalletFactory = new EwalletFactory();
        Customer ewalletCustomer = new Customer(ewalletFactory);
        PaymentMethod ewallet = ewalletCustomer.getPaymentMethod();
        ewallet.printPaymentMethod();

        PaymentMethodFactory CreditCardFactory = new CreditCardFactory();
        Customer creaditCardCustomer = new Customer(CreditCardFactory);
        PaymentMethod creditCard = creaditCardCustomer.getPaymentMethod();
        creditCard.printPaymentMethod();
    }
}
