package com.example.backend_practice.service;

// Marcar método como prueba
import org.junit.jupiter.api.Test;

// Verificar si un método fue llamado
import static org.mockito.Mockito.verify;
// Verificar si un método fue ejecutado tantas veces
import static org.mockito.Mockito.times;
// Crear un objeto "espía" del objeto original que mantiene el mismo comportamiento
import static org.mockito.Mockito.spy;

public class KafkaConsumerTest {

    @Test
    public void testKafkaConsumer() {
        
        KafkaConsumerService consumerService  = spy(new KafkaConsumerService());

        consumerService.listen("Holi");

        // Verificar que el método se haya llamado 1 vez desde ese topic
        verify(consumerService, times(1)).listen("Holi");
    }
}
