package com.engineering.payment.servicesTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.engineering.payment.entities.Payment;
import com.engineering.payment.repositories.PaymentRepository;
import com.engineering.payment.services.PaymentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@SpringBootTest
@AutoConfigureMockMvc
public class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private PaymentService mockPaymentService;

    @InjectMocks
    private PaymentService paymentService;

    private String token = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.w-S-G4v0dTE5r8PajRF756FdYryec6AC2mxomZ7yzVY";

    @Test
    public void testPaymentDetailsbyId() {
        Optional<Payment> expected = Optional.of(new Payment(1L, true, "Monday", "Card", 1L));
        ResponseEntity<Payment> expectedResponse = ResponseEntity.status(HttpStatus.OK).body(expected.get());

        when(paymentRepository.findById(anyLong()))
                .thenReturn(expected);

        ResponseEntity<Payment> result = paymentService.paymentDetailsbyId(1L);

        Assertions.assertEquals(expectedResponse, result);
    }

    @Test
    public void testPaymentDetailsbyId_NotFound() {
        Optional<Payment> expected = Optional.empty();
        ResponseEntity<Payment> expectedResponse = ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        when(paymentRepository.findById(anyLong()))
                .thenReturn(expected);

        ResponseEntity<Payment> result = paymentService.paymentDetailsbyId(1L);
        Assertions.assertEquals(expectedResponse, result);
    }

    @Test
    public void modifyOrderStatus() {

    }

    @Test
    public void testCreatePayment() {
        Payment expected = new Payment(1L, true, "Monday 10 March 2025", "Credit Card", 1L);

        when(paymentRepository.save(any(Payment.class))).thenReturn(expected);

        Payment result = paymentService.createPayment(expected);
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void testModifyOrderStatus() throws Exception {
        Long orderId = 1L;
        String endPoint = "http://localhost:8080/orders/" + orderId;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        String jsonResponse = "{\"id\":\"1\", \"status\":\"Paid\"}";
        ResponseEntity<String> responseEntity = new ResponseEntity<>(jsonResponse, HttpStatus.OK);

        when(restTemplate.exchange(eq(endPoint), eq(HttpMethod.GET), eq(entity), eq(String.class)))
                .thenReturn(responseEntity);

        JsonNode result = paymentService.modifyOrderStatus(orderId);

        assertNotNull(result);
        assertEquals("Paid", result.get("status").asText());
    }

    // @Test
    // public void testUpdateOrderStatus() throws Exception {
    // ObjectMapper mapper = new ObjectMapper();
    // Long orderId = 1L;
    // String endPoint = "http://localhost8080/orders/" + orderId;

    // JsonNode payLoad = mapper.createObjectNode().put("id", 1).put("status",
    // "Paid");
    // HttpHeaders headers = new HttpHeaders();
    // headers.set("Content-Type", "application/json");
    // headers.set("Authorization", token);

    // HttpEntity<JsonNode> entity = new HttpEntity<>(payLoad, headers);

    // ResponseEntity<Void> responseEntity = new ResponseEntity<>(HttpStatus.OK);

    // when(paymentService.modifyOrderStatus(orderId)).thenReturn(payLoad);
    // when(restTemplate.exchange(eq(endPoint), eq(HttpMethod.PUT), eq(entity),
    // eq(Void.class)))
    // .thenReturn(responseEntity);

    // paymentService.updateOrderStatus(orderId);

    // verify(restTemplate, times(1)).exchange(eq(endPoint), eq(HttpMethod.PUT),
    // eq(entity), eq(Void.class));

    // }

    @Test
    public void testCreateUpdatePaymentOrder_Success() throws JsonProcessingException {
        Payment payment = new Payment();
        payment.setId(1L);
        payment.setOrderId(1L);

        when(paymentService.createPayment(payment)).thenReturn(payment);

        Payment result = paymentService.createUpdatePaymentOrder(payment);
        doNothing().when(mockPaymentService).updateOrderStatus(payment.getOrderId());

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(1L, result.getOrderId());
    }

    @Test
    public void testCreateUpdatePaymentOrder_Exception() throws JsonProcessingException {
        Payment payment = new Payment();
        payment.setId(1L);
        payment.setOrderId(1L);

        when(paymentService.createPayment(payment)).thenReturn(payment);
        doThrow(new JsonProcessingException("Error") {
        }).when(mockPaymentService).updateOrderStatus(payment.getOrderId());

        Payment result = paymentService.createUpdatePaymentOrder(payment);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(1L, result.getOrderId());
    }
}
