package com.engineering.payment.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.engineering.payment.entities.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

}
