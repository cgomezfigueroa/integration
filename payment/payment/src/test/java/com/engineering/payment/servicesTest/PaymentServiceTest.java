package com.engineering.payment.servicesTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.engineering.payment.entities.Payment;
import com.engineering.payment.repositories.PaymentRepository;
import com.engineering.payment.services.PaymentService;

@SpringBootTest
@AutoConfigureMockMvc
public class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentService paymentService;

    @Test
    public void testPaymentDetailsbyId() {
        Optional<Payment> expected = Optional.of(new Payment(1, false, "Monday", "Card", 1));
        ResponseEntity<Payment> expectedResponse = ResponseEntity.status(HttpStatus.OK).body(expected.get());

        when(paymentRepository.findById(anyInt()))
                .thenReturn(expected);

        ResponseEntity<Payment> result = paymentService.paymentDetailsbyId(1);

        Assertions.assertEquals(expectedResponse, result);
    }

    @Test
    public void testPaymentDetailsbyId_NotFound() {
        Optional<Payment> expected = Optional.empty();
        ResponseEntity<Payment> expectedResponse = ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        when(paymentRepository.findById(anyInt()))
                .thenReturn(expected);

        ResponseEntity<Payment> result = paymentService.paymentDetailsbyId(1);
        Assertions.assertEquals(expectedResponse, result);
    }

    @Test
    public void createPayment() {
        Payment expected = new Payment(1, false, "Monday 10 March 2025", "Credit Card", 1);
        when(paymentRepository.save(any(Payment.class))).thenReturn(expected);
        Payment result = paymentService.createPayment(expected);

        Assertions.assertEquals(expected, result);
    }
}
