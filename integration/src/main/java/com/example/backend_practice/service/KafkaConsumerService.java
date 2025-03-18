package com.example.backend_practice.service;
// Escuchar mensajes en Kafka
import org.springframework.kafka.annotation.KafkaListener;
// import del Service de Spring Boot
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @KafkaListener(topics = { "${kafka.user-topic}", "${kafka.book-topic}", "${kafka.order-topic}", "${kafka.payment-topic}" })
    public void listen(String message) {
        System.out.println("Received Message: " + message);
    }
}


