package com.noon.librarymanagementsystem.service.impl;

import com.noon.librarymanagementsystem.entity.Book;
import com.noon.librarymanagementsystem.entity.User;
import com.noon.librarymanagementsystem.exception.NotFoundException;
import com.noon.librarymanagementsystem.repository.UserRepository;
import com.noon.librarymanagementsystem.service.BookService;
import com.noon.librarymanagementsystem.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	private final BookService bookService;

	public UserServiceImpl(UserRepository userRepository, BookService bookService) {
		this.userRepository = userRepository;
		this.bookService = bookService;
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public List<User> findAllUsers() {
		return userRepository.findAll();
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public User findUserById(Long id) {
		return userRepository.findById(id)
				.orElseThrow(() -> new NotFoundException(String.format("User not found with ID %d", id)));
	}

	@Override
	public void createUser(User user) {
		userRepository.save(user);
	}

	@Override
	public void updateUser(User user) {
		userRepository.save(user);
	}

	@Override
	public void deleteUser(Long id) {
		final User user = userRepository.findById(id)
				.orElseThrow(() -> new NotFoundException(String.format("User not found with ID %d", id)));

		userRepository.deleteById(user.getId());
	}

	/**
	 *  At a time, users can only borrow 1 book.
	 *  A user cannot borrow more than 2 books
	 *  A user cannot borrow 2 copies of same book (2 books are treated as same if they have same title and same Author)
	 *  A user can borrow a book for a max of 7 days.
	 * @param id
	 * @param bookNames
	 */

	@Override
	public void burrowBooks(Long id, List<String> bookNames) {

		if(bookNames.size() > 2)
			throw new NotFoundException(String.format("user %d cannot burrow more than 2 books", id));

		for(String bookName : bookNames) {
			List<Book> availableBooks = bookService.searchBooks(bookName).stream().filter(book -> book.getUsers().isEmpty()).collect(Collectors.toList());

			if (availableBooks.isEmpty())
				throw new NotFoundException(String.format("All books named %d are either not present or been burrowed by others", id));
			else if (availableBooks.size() > 1)
				throw new NotFoundException(String.format("user %d cannot burrow 2 books of same copy", id));

			final User user = userRepository.findById(id)
					.orElseThrow(() -> new NotFoundException(String.format("User not found with ID %d", id)));

			user.getBooks().add(availableBooks.get(0));
		}
	}

	@Override
	public void returnBooks(Long id, List<String> bookNames) {
		final User user = userRepository.findById(id)
				.orElseThrow(() -> new NotFoundException(String.format("User not found with ID %d", id)));

		for(String bookName : bookNames) {
			List<Book> tobeReturned = user.getBooks().stream().filter(book -> book.getName().equals(bookName)).collect(Collectors.toList());
			tobeReturned.get(0).removeUsers(user);
		}
	}


}
