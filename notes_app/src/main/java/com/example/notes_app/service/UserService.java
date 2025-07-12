package com.example.notes_app.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.notes_app.entity.User;

@Service
public interface UserService {
	
	// Check if given user name is already taken or not:
	boolean checkUserIdAvailablity(String username);
	
	// Fetch All Users:
	List<User> findAllUsers();

	// Post a new User:
	Object postUser(User user);
	
	// Delete Particular user with id
	String deleteUserByUid(String id);
	
	// Return User with given username:
	Object findUserByUsername(String username);
	
	// PATCH Request Handling (i.e, Update an existing record)
	Object patchUser(Map<String, Object> fields, String requestUsername);
}
