package com.noon.librarymanagementsystem.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", length = 100, nullable = false,unique = true)
	private String name;

	@Column(name = "isMembershipActive", length = 5, nullable = false)
	private Boolean isMembershipActive;

	@Column(name = "createdDate", length = 10, nullable = false)
	private LocalDateTime createdDate;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE , CascadeType.REMOVE}, mappedBy = "users")
	private Set<Book> books = new HashSet<Book>();

	public User(String name, Boolean isMembershipActive) {
		this.name = name;
		this.isMembershipActive = isMembershipActive;
		this.createdDate = LocalDateTime.now();
	}

}
