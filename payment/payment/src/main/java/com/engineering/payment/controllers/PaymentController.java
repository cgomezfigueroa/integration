package com.engineering.payment.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.engineering.payment.entities.Payment;
import com.engineering.payment.services.PaymentService;

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

    // Endpoints Consumed:
    // PUT /orders/{orderId} from Order Management Service: Update the order status
    // after payment is processed.

    @GetMapping("/{paymentId}")
    public ResponseEntity<?> getPayment(@PathVariable Integer paymentId) {

        return paymentService.paymentDetailsbyId(paymentId);
    }

    @PostMapping("")
    public Payment postPayment(@RequestBody Payment payment) {

        Payment savedPayment = paymentService.createPayment(payment);
        return savedPayment;
    }

}
