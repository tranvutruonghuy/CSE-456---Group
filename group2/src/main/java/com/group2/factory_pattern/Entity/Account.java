package com.group2.factory_pattern.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long accountId;
    String accountNumber;
    Double balance;
    Double creditLimit;
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    List<Payment> payments = new ArrayList<>();

    public Account(String accountNumber, Double balance, Double creditLimit) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.creditLimit = creditLimit;
    }
}
