package com.leonel.bookcatalog.Kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.leonel.bookcatalog.book.Book;

@Service
public class KafkaProducerService {
	@Value("${kafka.topic}")
    private String topicName;

    @Autowired
    private KafkaTemplate<String, Book> kafkaTemplate;

    public void sendMessage(Book message) {
        kafkaTemplate.send(topicName, message);
    }
}
