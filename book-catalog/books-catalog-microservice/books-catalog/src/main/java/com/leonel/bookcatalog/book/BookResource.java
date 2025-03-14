package com.leonel.bookcatalog.book;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.leonel.bookcatalog.Kafka.KafkaProducerService;

import jakarta.validation.Valid;

@RestController
public class BookResource {

	@Autowired
	private BookRepository repository;

	@Autowired
    KafkaProducerService kafkaProducerService;

	public BookResource(BookRepository repository){
		this.repository = repository;
	}

	@GetMapping(path = "/books")
	public List<Book> retrieveAllBooks() {
		return repository.findAll();
	}

	@GetMapping(path = "/books/{id}")
	public EntityModel<Book> retrieveBook(@PathVariable int id) {
		Optional<Book> book = repository.findById(id);
		if (book.isEmpty()) 
			throw new BookNotFoundException("id: " + id);
		
		EntityModel<Book> entityModel = EntityModel.of(book.get());
		WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAllBooks());
		entityModel.add(link.withRel("all-books"));
		
		return entityModel;
	}
	
	@PostMapping(path = "/books")
	public ResponseEntity<Book> createBook(@Valid @RequestBody Book book) {
		Book savedBook = repository.save(book);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(savedBook.getId())
				.toUri();

		kafkaProducerService.sendMessage(savedBook);
		return ResponseEntity.created(location).build();
	}
	
	@PutMapping(path = "books/{id}")
	public ResponseEntity<Object> putBook(@PathVariable int id, @RequestBody Book book) {
		Optional<Book> existingBook = repository.findById(id);
		if (!existingBook.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		book.setId(id);
		Book savedBook = repository.save(book);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(savedBook.getId())
				.toUri();
				
		kafkaProducerService.sendMessage(savedBook);
		return ResponseEntity.created(location).build();
	}
}
