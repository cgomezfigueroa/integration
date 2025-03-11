package com.leonel.book;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import jakarta.validation.constraints.Size;

@Data
public class Book {
	@Id
	@GeneratedValue
	private long id;
	
	@Size(min = 2, message = "Name should be longer than 2 characters")
	private String name;
	
	@Size(min = 2, message = "Name should be longer than 2 characters")
	private String author;
}
