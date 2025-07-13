package com.example.notes_app.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RestController;

import com.example.notes_app.entity.User;

@RestController
public interface UserController {

	// Checks if the new username that user is trying to create is already present in the DB or not.
	ResponseEntity<Object> checkUsernameInDB(String username);
	
	// Fetch all users List. Only Admin user can see this result
	ResponseEntity<Object> fetchAllUsers(UserDetails userDetails);
	
	// Register a new User:
	ResponseEntity<Object> registerUser(User user);
	
	// Delete User: Only signed in user can delete his own account and admin can delete anyone's account.
	ResponseEntity<Object> deleteUser(UserDetails userDetails, String Id);
	
	// Patch User: only signed in user can update its record.
	ResponseEntity<Object> updateUser(UserDetails userDetails, Map<String, Object> fields);
	
	// Login user:
	ResponseEntity<Object> login(Map<String, Object> userData);
}
