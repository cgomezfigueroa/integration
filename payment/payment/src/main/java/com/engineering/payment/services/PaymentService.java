package com.engineering.payment.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;
import com.engineering.payment.entities.Payment;
import com.engineering.payment.repositories.PaymentRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private RestTemplate restTemplate;

    private String token = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.w-S-G4v0dTE5r8PajRF756FdYryec6AC2mxomZ7yzVY";

    public ResponseEntity<Payment> paymentDetailsbyId(Long id) {
        Optional<Payment> optionalPayment = paymentRepository.findById(id);
        if (optionalPayment.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(optionalPayment.get());
    }

    @SuppressWarnings("finally")
    public Payment createUpdatePaymentOrder(@RequestBody Payment payment) {
        Payment newPayment = createPayment(payment);
        try {
            updateOrderStatus(newPayment.getOrderId());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } finally {
            return newPayment;
        }

    }

    public Payment createPayment(@RequestBody Payment payment) {
        Payment savedPayment = paymentRepository.save(payment);
        return savedPayment;
    }

    public void updateOrderStatus(Long orderId) throws JsonMappingException, JsonProcessingException {
        String endPoint = "http://localhost:8080/orders/" + orderId;
        JsonNode payLoad = modifyOrderStatus(orderId);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", token);

        HttpEntity<JsonNode> entity = new HttpEntity<>(payLoad, headers);
        ResponseEntity<Void> response = restTemplate.exchange(endPoint, HttpMethod.PUT, entity, Void.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            System.out.println("Update successful");
        } else {
            System.out.println("Update failed");
        }
    }

    public JsonNode modifyOrderStatus(Long orderId) throws JsonMappingException, JsonProcessingException {
        String endPoint = "http://localhost:8080/orders/" + orderId;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(endPoint, HttpMethod.GET, entity, String.class);

        ObjectMapper mapper = new ObjectMapper();

        JsonNode payLoad = mapper.readTree(response.getBody());
        ((ObjectNode) payLoad).put("status", "Paid");

        return payLoad;
    }

}
