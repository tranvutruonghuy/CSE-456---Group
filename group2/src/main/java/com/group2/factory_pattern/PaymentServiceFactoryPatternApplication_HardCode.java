package com.group2.factory_pattern;

import com.group2.factory_pattern.Config.ApplicationConfig;
import com.group2.factory_pattern.DTO.AccountRequestDto;
import com.group2.factory_pattern.DTO.PaymentRequestDto;
import com.group2.factory_pattern.DTO.PaymentResponseDto;
import com.group2.factory_pattern.Entity.Account;
import com.group2.factory_pattern.Enum.PaymentType;
import com.group2.factory_pattern.Factory.PaymentFactory;
import com.group2.factory_pattern.Service.AccountService;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class PaymentServiceFactoryPatternApplication_HardCode {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);

        AccountService accountService = context.getBean(AccountService.class);
        PaymentFactory paymentFactory = context.getBean(PaymentFactory.class);


        //Mock entities
        AccountRequestDto account1 = new AccountRequestDto("AC1", 500.0, 1000.0);
        AccountRequestDto account2 = new AccountRequestDto("AC2", 500.0, 1000.0);

        accountService.createAccount(account1);
        accountService.createAccount(account2);

        System.out.println("\nPAYMENT SERVICE DEMO (Factory Pattern) - GROUP 2");
        System.out.println("=============================================");

        System.out.println("Initialize account information...");
        System.out.println("Account AC1 information: ");
        System.out.println(accountService.findByAccountNumber("AC1"));

        System.out.println("Account AC2 information: ");
        System.out.println(accountService.findByAccountNumber("AC2"));

        System.out.println();

        System.out.println("=============================================");
        System.out.println("DEMO for E-Wallet Payment using Account Number: AC1 ");
        System.out.println("Account AC1 information at the beginning: ");
        System.out.println(accountService.findByAccountNumber("AC1"));
        System.out.println();
        PaymentType type = PaymentType.EWALLET;
        System.out.println("1. Payment success situation:");
        System.out.println("Before payment: ");
        System.out.println(accountService.findByAccountNumber("AC1"));
        System.out.println();

        PaymentRequestDto request = new PaymentRequestDto(300.0, "AC1");
        PaymentResponseDto response = paymentFactory.getService(type).pay(request);
        System.out.println("Payment amount: " + request.getAmount());
        getResult(response);
        System.out.println();

        System.out.println("Account information after payment: ");
        System.out.println(accountService.findByAccountNumber("AC1"));
        System.out.println("------------");
        System.out.println();

        System.out.println("2. Payment declined situation (Insufficient balance):");
        System.out.println("Before payment: ");
        System.out.println(accountService.findByAccountNumber("AC1"));
        System.out.println();

        PaymentRequestDto request2 = new PaymentRequestDto(300.0, "AC1");
        PaymentResponseDto response2 = paymentFactory.getService(type).pay(request2);
        System.out.println("Payment amount: " + request2.getAmount());
        getResult(response2);
        System.out.println();

        System.out.println("Account information after payment: ");
        System.out.println(accountService.findByAccountNumber("AC1"));
        System.out.println("------------");
        System.out.println();

        System.out.println("3. Catch error situation (Invalid payment amount): (amount = -100) ");

        PaymentRequestDto request3 = new PaymentRequestDto(-100.0, "AC1");
        try{
            paymentFactory.getService(type).pay(request3);
        }catch(Exception e){
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println("------------");
        System.out.println();

        System.out.println("4. Catch error situation (Invalid account number): (accountNumber = AC3) ");
        PaymentRequestDto request4 = new PaymentRequestDto(300.0, "AC3");
        try{
            paymentFactory.getService(type).pay(request4);
        }catch(Exception e){
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println("------------");
        System.out.println();

        System.out.println("=============================================");
        System.out.println();

        System.out.println("DEMO for Credit Card Payment using Account Number: AC2 ");
        System.out.println("Account AC2 information at the beginning: ");
        System.out.println(accountService.findByAccountNumber("AC2"));
        System.out.println();
        type = PaymentType.CREDIT_CARD;
        System.out.println("1. Payment success situation:");
        System.out.println("Before payment: ");
        System.out.println(accountService.findByAccountNumber("AC2"));
        System.out.println();

        PaymentRequestDto request5 = new PaymentRequestDto(300.0, "AC2");
        PaymentResponseDto response5 = paymentFactory.getService(type).pay(request5);
        System.out.println("Payment amount: " + request5.getAmount());
        getResult(response5);
        System.out.println();

        System.out.println("Account information after payment: ");
        System.out.println(accountService.findByAccountNumber("AC2"));
        System.out.println("------------");
        System.out.println();

        System.out.println("2. Payment declined situation (Insufficient balance):");
        System.out.println("Before payment: ");
        System.out.println(accountService.findByAccountNumber("AC2"));
        System.out.println();

        PaymentRequestDto request6 = new PaymentRequestDto(2000.0, "AC2");
        PaymentResponseDto response6 = paymentFactory.getService(type).pay(request6);
        System.out.println("Payment amount: " + request6.getAmount());
        getResult(response6);
        System.out.println();

        System.out.println("Account information after payment: ");
        System.out.println(accountService.findByAccountNumber("AC2"));
        System.out.println("------------");
        System.out.println();

        System.out.println("3. Catch error situation (Invalid payment amount): (amount = -100) ");

        PaymentRequestDto request7 = new PaymentRequestDto(-100.0, "AC2");
        try{
            paymentFactory.getService(type).pay(request7);
        }catch(Exception e){
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println("------------");
        System.out.println();

        System.out.println("4. Catch error situation (Invalid account number): (accountNumber = AC3) ");
        PaymentRequestDto request8 = new PaymentRequestDto(300.0, "AC3");
        try{
            paymentFactory.getService(type).pay(request8);
        }catch(Exception e){
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println("------------");
        System.out.println();

        System.out.println("====================THE END OF DEMO====================");
        System.out.println();

    }
    public static void getResult(PaymentResponseDto response){
        System.out.println("Payment information:");
        System.out.println("Method: " + response.getMethod());
        System.out.println("Status: " + response.getStatus());
        System.out.println("Remaining Balance: " + response.getRemainingBalance());
        System.out.println("Remaining Credit Limit: " + response.getRemainingCreditLimit());
    }
}
