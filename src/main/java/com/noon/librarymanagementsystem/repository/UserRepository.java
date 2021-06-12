package com.noon.librarymanagementsystem.repository;

import com.noon.librarymanagementsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
