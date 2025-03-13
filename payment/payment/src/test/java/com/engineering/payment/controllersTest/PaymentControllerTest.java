package com.engineering.payment.controllersTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.engineering.payment.controllers.PaymentController;
import com.engineering.payment.entities.Payment;
import com.engineering.payment.services.PaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(PaymentController.class)
@AutoConfigureMockMvc
public class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PaymentService paymentService;

    @Test
    public void testGetPayment() throws Exception {
        Payment payment = new Payment(1, false, "Monday", "Card", 1);
        ResponseEntity<Payment> responsePayment = ResponseEntity.status(HttpStatus.OK).body(payment);

        when(paymentService.paymentDetailsbyId(anyInt())).thenReturn(responsePayment);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/payments/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(payment.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(payment.getStatus()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.paymentDate").value(payment.getPaymentDate()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.paymentType").value(payment.getPaymentType()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.orderId").value(payment.getOrderId()));

    }

    @Test
    public void testGetPayment_NotFound() throws Exception {
        Payment payment = null;
        ResponseEntity<Payment> responsePayment = ResponseEntity.status(HttpStatus.NOT_FOUND).body(payment);

        when(paymentService.paymentDetailsbyId(anyInt())).thenReturn(responsePayment);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/payments/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

    }

    @Test
    public void testPostPayment() throws Exception {
        Payment payment = new Payment(1, false, "Monday", "Card", 1);
        ObjectMapper mapper = new ObjectMapper();

        when(paymentService.createPayment(any(Payment.class))).thenReturn(payment);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(payment)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(payment.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(payment.getStatus()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.paymentDate").value(payment.getPaymentDate()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.paymentType").value(payment.getPaymentType()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.orderId").value(payment.getOrderId()));
    }
}
