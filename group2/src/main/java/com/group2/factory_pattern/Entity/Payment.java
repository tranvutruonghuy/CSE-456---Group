package com.group2.factory_pattern.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long paymentId;
    String method;
    Double amount;
    String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accountId")
    @ToString.Exclude
    Account account;

    public Payment(String method, Double amount, String status) {
        this.method = method;
        this.amount = amount;
        this.status = status;
    }
}
