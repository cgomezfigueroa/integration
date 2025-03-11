package com.leonel.book;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class BookResource {
	private BookRepository repository;

	public BookResource(BookRepository repository){
		this.repository = repository;
	}

	@GetMapping("/books")
	public List<Book> retrieveBooks() {
		return repository.findAll();
	}

	@GetMapping("/books/{id}")
	public EntityModel<Book> retrieveBook(@RequestParam int id) {
		Optional<Book> book = repository.findById(id);
		if (book.isEmpty()) 
			throw new BookNotFoundException("id: " + id);
		
		EntityModel<Book> entityModel = EntityModel.of(book.get());
		WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveBooks());
		entityModel.add(link.withRel("all-books"));
		
		return entityModel;
	}
	
	@PostMapping("/books")
	public ResponseEntity<Book> createBook(@Valid @RequestBody Book book) {
		Book savedBook = repository.save(book);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(savedBook.getId())
				.toUri();
		return ResponseEntity.created(location ).build();
	}
	

}
