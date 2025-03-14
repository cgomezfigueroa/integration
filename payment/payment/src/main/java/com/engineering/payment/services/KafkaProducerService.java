package com.engineering.payment.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.engineering.payment.entities.Payment;

@Service
public class KafkaProducerService {

    @Value("${kafka.topic}")
    private String topicName;

    @Autowired
    private KafkaTemplate<String, Payment> kafkaTemplate;

    public void sendMessage(Payment message) {
        kafkaTemplate.send(topicName, message);
    }
}
