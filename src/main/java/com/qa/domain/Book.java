package com.qa.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Book {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;
	private String title;
	private String author;
	private String genre;
	private String isbn;

	public Book() {

	}

	public Book(String title, String author, String genre, String isbn) {
		this.title = title;
		this.author = author;
		this.genre = genre;
		this.isbn =isbn;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}
	
	public String getISBN() {
		return isbn;
	}
	
	public void setISBN(String isbn) {
		this.isbn = isbn;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
