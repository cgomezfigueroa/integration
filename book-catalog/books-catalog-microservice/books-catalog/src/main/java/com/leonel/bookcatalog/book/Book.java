package com.leonel.bookcatalog.book;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;

@Entity
public class Book {
	@Id
	@GeneratedValue
	private int id;
	
	@Size(min = 2, message = "Name should be longer than 2 characters")
	private String name;
	
	@Size(min = 2, message = "Name should be longer than 2 characters")
	private String author;

	protected Book(){

	}

	public Book(int id, @Size(min = 2, message = "Name should be longer than 2 characters") String name,
			@Size(min = 2, message = "Name should be longer than 2 characters") String author) {
		this.id = id;
		this.name = name;
		this.author = author;
	}

	public long getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", name=" + name + ", author=" + author + "]";
	}
}
