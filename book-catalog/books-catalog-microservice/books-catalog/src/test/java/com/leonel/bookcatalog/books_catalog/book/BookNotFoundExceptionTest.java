package com.leonel.bookcatalog.books_catalog.book;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import com.leonel.bookcatalog.book.BookNotFoundException;

@SpringBootTest
@AutoConfigureMockMvc
public class BookNotFoundExceptionTest {
	@Test
    public void testBookNotFoundException() {
        String errorMessage = "Book not found";
        BookNotFoundException exception = assertThrows(BookNotFoundException.class, () -> {
            throw new BookNotFoundException(errorMessage);
        });

        assertEquals(errorMessage, exception.getMessage());
    }
}
