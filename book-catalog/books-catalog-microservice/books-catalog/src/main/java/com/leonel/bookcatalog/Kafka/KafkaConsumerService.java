package com.leonel.bookcatalog.Kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {
	@KafkaListener(topics = "${kafka.topic}")
    public void listenGroup(String message) {
        System.out.println("book-catalog updated: " + message);
    }
}
