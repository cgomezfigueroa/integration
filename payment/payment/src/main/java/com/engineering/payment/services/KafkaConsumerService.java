package com.engineering.payment.services;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @KafkaListener(topics = "${kafka.topic}")
    public void listenGroup(String message) {
        System.out.println("A new Payment was processed:" + message);
    }
}
