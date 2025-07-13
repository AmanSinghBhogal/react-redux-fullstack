package com.example.notes_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.notes_app.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
	
	@Query(value="Select username from User u where u.username = ?1")
	String findByUserName(String username);
	
	@Query(value="from User u where u.username = ?1")
	User findUser(String username);
	
	@Query(value="Select email from User u where u.email = ?1")
	String findUserEmail(String email);
	
	@Query(value="Select username from User u where u.id=?1")
	String findByUserId(String id);
}
