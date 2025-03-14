package com.leonel.bookcatalog.book;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class BookNotFoundException extends RuntimeException{
	public BookNotFoundException(String message) {
		super(message);
	}
}
 