package com.noon.librarymanagementsystem.service;

import com.noon.librarymanagementsystem.entity.User;

import java.util.List;

public interface UserService {

	public List<User> findAllUsers();

	public User findUserById(Long id);

	public void createUser(User user);

	public void updateUser(User user);

	public void deleteUser(Long id);

	public void burrowBooks(Long id, List<String> bookNames);

	public void returnBooks(Long id, List<String> bookNames);

}
