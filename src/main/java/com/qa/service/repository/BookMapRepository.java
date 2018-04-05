package com.qa.service.repository;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import javax.inject.Inject;

import com.qa.domain.Book;
import com.qa.util.JSONUtil;

@Alternative //Alternative method used by compiler, instead of BookDBRepository
@ApplicationScoped //Bean and objects will live for as long as the application is running
public class BookMapRepository implements BookRepository {

	private final Long INITIAL_COUNT = 1L;
	private Map<Long, Book> bookMap;
	private Long ID;

	@Inject //Tells bean container to take over life-cycle management of object
	private JSONUtil util;

	public BookMapRepository() {
		this.bookMap = new HashMap<Long, Book>(); //Creates new HashMap
		ID = INITIAL_COUNT;
		initBookMap();
	}
//Some methods such as this one have the same names as BookDBRepository. Allows BookRepository to remain the same when using different methods
	@Override	//Calls to new map-based methods are contained within overridden methods e.g. initBookMap called within BookMapRepository
	public String getAllBooks() {
		return util.getJSONForObject(bookMap.values());
	}

	@Override
	public String createBook(String book) {
		ID++;
		Book newBook = util.getObjectForJSON(book, Book.class);
		bookMap.put(ID, newBook);
		return book;
	}

	@Override
	public String updateBook(Long id, String bookToUpdate) {
		Book newBook = util.getObjectForJSON(bookToUpdate, Book.class);
		bookMap.put(id, newBook);
		return bookToUpdate;
	}

	@Override
	public String deleteBook(Long id) {
		bookMap.remove(id);
		return "{\"message\": \"book sucessfully removed\"}";
	}

	private void initBookMap() {
		Book book = new Book("A Hitchiker's Guide to the Galaxy", "Douglas Adams", "Sci-Fi", "1234");
		bookMap.put(1L, book);
	}

}
