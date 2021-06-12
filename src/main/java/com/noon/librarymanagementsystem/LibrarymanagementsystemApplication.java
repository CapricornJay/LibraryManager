package com.noon.librarymanagementsystem;

import com.noon.librarymanagementsystem.entity.Book;
import com.noon.librarymanagementsystem.entity.Publisher;
import com.noon.librarymanagementsystem.entity.User;
import com.noon.librarymanagementsystem.service.BookService;
import com.noon.librarymanagementsystem.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class LibrarymanagementsystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibrarymanagementsystemApplication.class, args);
	}

	@Bean
	public CommandLineRunner initialCreate(BookService bookService, UserService userService) {
		return (args) -> {


			// Creating Books
			Book book = new Book("jayanth Book", "Test author");

			Publisher publisher = new Publisher("Test publisher name");

			book.addPublishers(publisher);

			bookService.createBook(book);

			Book book1 = new Book("noon book", "Test author");

			Publisher publisher1 = new Publisher("Test publisher name1");

			book1.addPublishers(publisher1);

			bookService.createBook(book1);

			Book book2 = new Book("jayanth Book", "Test author");

			Publisher publisher2 = new Publisher("Test publisher name2");

			book2.addPublishers(publisher2);

			bookService.createBook(book2);

			System.out.println("Getting single copy of book");
			System.out.println(bookService.searchBooks("noon book").get(0).getName());

			System.out.println("\n\n\n");
			System.out.println("Getting multiple copy of books");
			bookService.searchBooks("jayanth Book").stream().forEach( e -> System.out.println("Name : "+ e.getName() + "\n" + "Id : "+ e.getId()) );

			System.out.println("\n\n\n");
			System.out.println("Removing book");
			bookService.deleteBook(1L);
			bookService.findAllBooks().stream().forEach( e -> System.out.println("Name : "+ e.getName() + "\n" + "Id : "+ e.getId()) );

			System.out.println("\n\n\n");
			System.out.println("Adding book");
			bookService.createBook(book);
			bookService.findAllBooks().stream().forEach( e -> System.out.println("Name : "+ e.getName() + "\n" + "Id : "+ e.getId()) );

			System.out.println("\n\n\n");
			System.out.println("Creating Users");
			User user = new User("Test user name", true);
			User user1 = new User("Test user name1", true);
			User user2 = new User("Test user name2", true);

			userService.createUser(user);
			userService.createUser(user1);
			userService.createUser(user2);

			userService.findAllUsers().stream().forEach( e -> System.out.println("Name : "+ e.getName() + "\n" + "Id : "+ e.getId()) );

			System.out.println("\n\n\n");
			System.out.println("Get available books");
			bookService.getAvailableBooks("jayanth Book").stream().forEach( e -> System.out.println("Name : "+ e.getName() + "\n" + "Id : "+ e.getId()) );

		};
	}
}
