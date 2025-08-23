//package com.group2.factory_pattern;
//
//import com.group2.factory_pattern.Config.ApplicationConfig;
//import com.group2.factory_pattern.DTO.AccountRequestDto;
//import com.group2.factory_pattern.DTO.PaymentRequestDto;
//import com.group2.factory_pattern.DTO.PaymentResponseDto;
//import com.group2.factory_pattern.Enum.PaymentType;
//import com.group2.factory_pattern.Factory.PaymentFactory;
//import com.group2.factory_pattern.Service.AccountService;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.annotation.AnnotationConfigApplicationContext;
//
//import java.util.Scanner;
//
//@SpringBootApplication
//public class PaymentServiceFactoryPatternApplication {
//    static Scanner sc = new Scanner(System.in);
//
//    public static void main(String[] args) {
//        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
//
//        AccountService accountService = context.getBean(AccountService.class);
//        PaymentFactory paymentFactory = context.getBean(PaymentFactory.class);
//
//        AccountRequestDto account1 = new AccountRequestDto("AC1", 500.0, 1000.0);
//        AccountRequestDto account2 = new AccountRequestDto("AC2", 100.0, 200.0);
//
//        accountService.createAccount(account1);
//        accountService.createAccount(account2);
//
//        while (true) {
//            try {
//                System.out.println("\nPAYMENT SERVICE DEMO (Factory Pattern) - GROUP 2");
//                System.out.print("Enter account number: ");
//                String accountNumber = sc.nextLine();
//
//                System.out.print("Enter account amount: ");
//                double amount = Double.parseDouble(sc.nextLine());
//
//                System.out.println("Select method: 1.CreditCard  2.EWallet");
//                int method = Integer.parseInt(sc.nextLine());
//
//                PaymentType type;
//                if (method == 1) {
//                    type = PaymentType.CREDIT_CARD;
//                } else if (method == 2) {
//                    type = PaymentType.EWALLET;
//                } else {
//                    System.out.println("Invalid selection! Please choose 1 or 2.");
//                    continue;
//                }
//                PaymentRequestDto request = new PaymentRequestDto(amount, accountNumber);
//
//                PaymentResponseDto response = paymentFactory.getService(type).pay(request);
//
//                System.out.println("Result:");
//                System.out.println("Method: " + response.getMethod());
//                System.out.println("Status: " + response.getStatus());
//                System.out.println("Remaining Balance: " + response.getRemainingBalance());
//                System.out.println("Remaining Credit Limit: " + response.getRemainingCreditLimit());
//
//            } catch (Exception e) {
//                System.out.println("Error: " + e.getMessage());
//            }
//
//            System.out.println("Do you want to continue? (y/n): ");
//            String condition = sc.nextLine();
//            if (!condition.equalsIgnoreCase("y"))
//                break;
//        }
//    }
//}
//
//
