package com.engineering.payment.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.engineering.payment.entities.Payment;
import com.engineering.payment.repositories.PaymentRepository;

import jakarta.validation.Valid;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    // Responsibilities: Manages payment processing for orders.
    // Endpoints Consumed:
    // PUT /orders/{orderId} from Order Management Service: Update the order status
    // after payment is processed.

    public ResponseEntity<Payment> paymentDetailsbyId(Integer id) {
        Optional<Payment> optionalPayment = paymentRepository.findById(id);
        if (optionalPayment.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(optionalPayment.get());
    }

    public Payment createPayment(@Valid @RequestBody Payment payment) {
        Payment savedPayment = paymentRepository.save(payment);
        return savedPayment;
    }

}
