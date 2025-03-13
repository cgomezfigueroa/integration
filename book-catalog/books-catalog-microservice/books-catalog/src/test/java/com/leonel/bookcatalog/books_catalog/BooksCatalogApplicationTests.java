package com.leonel.bookcatalog.books_catalog;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leonel.bookcatalog.book.Book;
import com.leonel.bookcatalog.book.BookRepository;
import com.leonel.bookcatalog.book.BookResource;

@WebMvcTest(BookResource.class)
class BooksCatalogApplicationTests {
	@Autowired
	private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

	@MockitoBean
	private BookResource bookResource;
	@MockitoBean
	private BookRepository bookRepository;

	private List<Book> books;

	@BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(bookResource).build();
    }

	@Test
	public void createBook_BookCreated() throws Exception{
		int id = 67;
		Book book = new Book(id, "Test", "Test");
		given(bookRepository.save(book)).willReturn(book);
		mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isOk());
	}

	@Test
	public void retrieveAllBooks_allBooks() throws Exception{
		given(bookRepository.findAll()).willReturn(books);
		mockMvc.perform(get("/books"))
                .andExpect(status().isOk());
	}

	@Test
	public void retrieveBook_Book() throws Exception{
		mockMvc.perform(get("/books/67"))
                .andExpect(status().isOk());
	}

	@Test
	public void putBook_bookChanged() throws Exception{
		int id = 67;
		Book book = new Book(id, "TestChange", "Test");
		given(bookRepository.save(book)).willReturn(book);
		mockMvc.perform(put("/books/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isOk());
	}

	@Test
	void contextLoads() {
	}

}
