package com.leonel.bookcatalog.books_catalog.book;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import com.leonel.bookcatalog.book.Book;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;	

@SpringBootTest
@AutoConfigureMockMvc
public class BookTest {
	private static Validator validator;

    @BeforeAll
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidBook() {
        Book book = new Book(1, "Valid Name", "Valid Author");
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testInvalidName() {
        Book book = new Book(1, "A", "Valid Author");
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertEquals(1, violations.size());
        ConstraintViolation<Book> violation = violations.iterator().next();
        assertEquals("Name should be longer than 2 characters", violation.getMessage());
    }

    @Test
    public void testInvalidAuthor() {
        Book book = new Book(1, "Valid Name", "A");
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertEquals(1, violations.size());
        ConstraintViolation<Book> violation = violations.iterator().next();
        assertEquals("Name should be longer than 2 characters", violation.getMessage());
    }

    @Test
    public void testToString() {
        Book book = new Book(1, "Valid Name", "Valid Author");
        String expected = "Book [id=1, name=Valid Name, author=Valid Author]";
        assertEquals(expected, book.toString());
    }

    @Test
    public void testGettersAndSetters() {
        Book book = new Book(1, "test", "test");
        book.setId(1);
        book.setName("Valid Name");
        book.setAuthor("Valid Author");

        assertEquals(1, book.getId());
        assertEquals("Valid Name", book.getName());
        assertEquals("Valid Author", book.getAuthor());
    }
}
