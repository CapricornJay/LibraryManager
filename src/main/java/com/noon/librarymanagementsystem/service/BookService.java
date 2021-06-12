package com.noon.librarymanagementsystem.service;

import com.noon.librarymanagementsystem.entity.Book;

import java.util.List;

public interface BookService {

	public List<Book> findAllBooks();
	
	public List<Book> searchBooks(String keyword);

	public List<Book> getAvailableBooks(String keyword);

	public Book findBookById(Long id);

	public void createBook(Book book);

	public void updateBook(Book book);

	public void deleteBook(Long id);

}
