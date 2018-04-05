package com.qa.service.repository;

import static javax.transaction.Transactional.TxType.REQUIRED;
import static javax.transaction.Transactional.TxType.SUPPORTS;

import java.util.Collection;

import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import com.qa.domain.Book;
import com.qa.util.JSONUtil;

@Transactional(SUPPORTS)
@Default //Default method used by compiler, instead of BookMapRepository
public class BookDBRepository implements BookRepository {

	@PersistenceContext(unitName = "primary")
	private EntityManager manager; //Interfaces with database -> persistence.xml

	@Inject	//Tells bean container to take over life-cycle management of object
	private JSONUtil util;

	@Override
	public String getAllBooks() {
		Query query = manager.createQuery("Select a FROM Book a"); //New query created with manager, selects * from books
		Collection<Book> books = (Collection<Book>) query.getResultList(); //Executes query object and returns collection of objects
		return util.getJSONForObject(books); //Returns JSON for book collection
	}

	@Override
	@Transactional(REQUIRED)
	public String createBook(String book) {
		Book aBook = util.getObjectForJSON(book, Book.class);	//Gets JSON object for book
		manager.persist(aBook);	//Adds book
		return "{\"message\": \"book has been sucessfully added\"}";
	}

	@Override
	@Transactional(REQUIRED)
	public String updateBook(Long id, String bookToUpdate) {
		Book updatedBook = util.getObjectForJSON(bookToUpdate, Book.class); //Gets JSON for book and puts in object
		Book bookFromDB = findBook(id); //Finds book based on ID
		if (bookToUpdate != null) {
			bookFromDB = updatedBook;		//Checks to make sure book is not null, updates book
			manager.merge(bookFromDB);
		}
		return "{\"message\": \"book sucessfully updated\"}";
	}

	@Override
	@Transactional(REQUIRED)
	public String deleteBook(Long id) {
		Book bookInDB = findBook(id);	//Takes in ID of book to be deleted
		if (bookInDB != null) {
			manager.remove(bookInDB);	//If object found, book deleted
		}
		return "{\"message\": \"book sucessfully deleted\"}";
	}

	private Book findBook(Long id) {
		return manager.find(Book.class, id);  //Finds book from ID
	}

	public void setManager(EntityManager manager) {		//Setter for EntityManager
		this.manager = manager;
	}

	public void setUtil(JSONUtil util) {	//Setter for util
		this.util = util;
	}

}
