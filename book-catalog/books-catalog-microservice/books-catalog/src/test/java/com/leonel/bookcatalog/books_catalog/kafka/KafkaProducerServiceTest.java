package com.leonel.bookcatalog.books_catalog.kafka;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import com.leonel.bookcatalog.Kafka.KafkaProducerService;
import com.leonel.bookcatalog.book.Book;

@SpringBootTest
@AutoConfigureMockMvc
public class KafkaProducerServiceTest {
	@Mock
    private KafkaTemplate<String, Book> kafkaTemplate;

    @InjectMocks
    private KafkaProducerService kafkaProducerService;

    private String topicName = "test-topic";
    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(kafkaProducerService, "topicName", topicName);
    }

    @Test
    public void testSendMessage() {
		Book book = new Book(67, "Test", "Test");
        kafkaProducerService.sendMessage(book);
        verify(kafkaTemplate).send(topicName, book);
    }
}
