package com.noon.librarymanagementsystem.controller;

import com.noon.librarymanagementsystem.entity.User;
import com.noon.librarymanagementsystem.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class UserController {

	private final UserService authorService;

	public UserController(UserService authorService) {
		this.authorService = authorService;
	}

	@RequestMapping("/users")
	public String findAllAuthors(Model model) {
		final List<User> users = authorService.findAllUsers();

		model.addAttribute("users", users);
		return "list-users";
	}

	@RequestMapping("/author/{id}")
	public String findAuthorById(@PathVariable("id") Long id, Model model) {
		final User user = authorService.findUserById(id);

		model.addAttribute("user", user);
		return "list-user";
	}

	@RequestMapping("/add-user")
	public String createAuthor(User user, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "add-user";
		}

		authorService.createUser(user);
		model.addAttribute("user", authorService.findAllUsers());
		return "redirect:/users";
	}

	@GetMapping("/updateAuthor/{id}")
	public String showUpdateForm(@PathVariable("id") Long id, Model model) {
		final User user = authorService.findUserById(id);

		model.addAttribute("user", user);
		return "update-user";
	}

	@RequestMapping("/update-user/{id}")
	public String updateAuthor(@PathVariable("id") Long id, User user, BindingResult result, Model model) {
		if (result.hasErrors()) {
			user.setId(id);
			return "update-user";
		}

		authorService.updateUser(user);
		model.addAttribute("user", authorService.findAllUsers());
		return "redirect:/users";
	}

	@RequestMapping("/remove-author/{id}")
	public String deleteAuthor(@PathVariable("id") Long id, Model model) {
		authorService.deleteUser(id);

		model.addAttribute("author", authorService.findAllUsers());
		return "redirect:/users";
	}

}
