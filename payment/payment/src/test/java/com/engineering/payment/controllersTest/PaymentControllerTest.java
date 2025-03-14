package com.engineering.payment.controllersTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.engineering.payment.entities.Payment;
import com.engineering.payment.services.KafkaProducerService;
import com.engineering.payment.services.PaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PaymentService paymentService;

    @MockitoBean
    private KafkaProducerService kafkaProducerService;

    private String token = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.w-S-G4v0dTE5r8PajRF756FdYryec6AC2mxomZ7yzVY";
    private HttpHeaders headers = new HttpHeaders();

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        headers.set("Authorization", token);
    }

    @Test
    public void testGetPayment() throws Exception {
        Payment payment = new Payment(1L, true, "Monday", "Card", 1L);
        ResponseEntity<Payment> responsePayment = ResponseEntity.status(HttpStatus.OK).body(payment);

        when(paymentService.paymentDetailsbyId(anyLong())).thenReturn(responsePayment);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/payments/1").header(HttpHeaders.AUTHORIZATION,
                token))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(payment.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isProcessed").value(payment.getIsProcessed()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.paymentDate").value(payment.getPaymentDate()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.paymentType").value(payment.getPaymentType()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.orderId").value(payment.getOrderId()));

    }

    @Test
    public void testGetPayment_NotFound() throws Exception {
        Payment payment = null;
        ResponseEntity<Payment> responsePayment = ResponseEntity.status(HttpStatus.NOT_FOUND).body(payment);

        when(paymentService.paymentDetailsbyId(anyLong())).thenReturn(responsePayment);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/payments/1").header(HttpHeaders.AUTHORIZATION,
                token))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testPostPayment() throws Exception {
        Payment payment = new Payment(1L, true, "Monday", "Card", 1L);
        ObjectMapper mapper = new ObjectMapper();

        when(paymentService.createUpdatePaymentOrder(any(Payment.class))).thenReturn(payment);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/payments").header(HttpHeaders.AUTHORIZATION,
                token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(payment)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(payment.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isProcessed").value(payment.getIsProcessed()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.paymentDate").value(payment.getPaymentDate()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.paymentType").value(payment.getPaymentType()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.orderId").value(payment.getOrderId()));
    }
}
