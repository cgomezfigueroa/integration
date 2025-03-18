package com.engineering.payment.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.engineering.payment.entities.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
