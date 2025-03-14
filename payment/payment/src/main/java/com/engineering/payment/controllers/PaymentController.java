package com.engineering.payment.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.engineering.payment.entities.Payment;
import com.engineering.payment.services.KafkaProducerService;
import com.engineering.payment.services.PaymentService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    @Autowired
    KafkaProducerService kafkaProducerService;

    @GetMapping("/{paymentId}")
    public ResponseEntity<?> getPayment(@PathVariable Long paymentId) {

        return paymentService.paymentDetailsbyId(paymentId);
    }

    @PostMapping("")
    public Payment postPayment(@Valid @RequestBody Payment payment) {

        Payment savedPayment = paymentService.createUpdatePaymentOrder(payment);
        kafkaProducerService.sendMessage(savedPayment);

        return savedPayment;
    }
}
