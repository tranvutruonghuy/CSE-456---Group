package com.group2.factory_pattern.Repository;

import com.group2.factory_pattern.Entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}