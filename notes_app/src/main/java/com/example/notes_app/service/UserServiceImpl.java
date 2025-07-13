package com.example.notes_app.service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import com.example.notes_app.entity.User;
import com.example.notes_app.repository.UserRepository;


@Service
public class UserServiceImpl implements UserService {
	
	
	@Autowired
	private UserRepository userRepo;
	
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
	
	private void dbg(String msg) {
		System.out.println("UserServiceImpl --> "+msg);
	}

	@Override
	public boolean checkUserIdAvailablity(String username) {
		String functionName = "checkUserIdAvailablity() --> ";
		dbg(functionName+ "Begins");
		// This function checks if the new userId thats getting passed is already present in db or not.
		String result = null;
		try {
			dbg(functionName+" The searched username is: "+username);
			result = userRepo.findByUserName(username);
			dbg(functionName+ " result is: "+result);
		}catch(Exception e) {
			dbg(functionName+"Exception -> " +e);
			return false;
		}

		if(result != null && result.equals(username)) {
			dbg(functionName+ "Returning true");
			return true;
		}
		dbg(functionName+ "Returning false");
		return false;
	}

	@Override
	public List<User> findAllUsers() {
		String functionName = "findAllUsers() --> ";
		dbg(functionName+ "Begins");
		List<User> result = new ArrayList<User>();
		try {
			dbg(functionName+"Calling repo layer for data.");
			Iterable<User> users = userRepo.findAll();
			for(User u: users) {
				result.add(u);
			}
			dbg(functionName+" Db Fetch for users Completed successfully.");
			return result;
			
		}catch(Exception e) {
			dbg(functionName+"Exception -> " +e);
			return null;
		}
	}

	@Override
	public Object postUser(User user) {
		String functionName = "postUser() --> ";
		dbg(functionName+ "Begins");
		try {
			dbg(functionName+"Incoming user Object: "+user.toString());
			// Check 1: See if the username is already taken or not.
			boolean usernameTaken = userRepo.findUser(user.getUsername())==null? false: true;
			boolean userEmailTaken = userRepo.findUserEmail(user.getEmail())==null? false:true;
			dbg(functionName+"usernameTaken:: "+usernameTaken+" userEmailTaken:: "+userEmailTaken);
			if(!usernameTaken && !userEmailTaken) {
				user.setPassword(encoder.encode(user.getPassword()));
				User result = userRepo.save(user);
				return result;
			}else if(usernameTaken && userEmailTaken) {
				return "User with same username and email exists.";
			}else if(usernameTaken) {
				return "Username is already taken, Please try with a different username";
			}else {
				return "User Email already used by another user, Please try with a different email";
			}
		}catch(Exception e) {
			dbg(functionName+"Exception -> " +e);
			return e.getMessage();
		}
	}

	@Override
	public String deleteUserByUid(String id) {
		String functionName = "deleteUserByUid() --> ";
		dbg(functionName+ "Begins");
		dbg(functionName+"Incoming id for deletion is: "+id);
		try {
			userRepo.deleteById(id);
			dbg(functionName+"Successfully deleted the user");
			return id;
			
		}catch(Exception e) {
			dbg(functionName+"Exception -> " +e);
			return e.getMessage();
		}
	}

	@Override
	public Object patchUser(Map<String, Object> fields, String requestUsername) {
		String functionName = "patchUser() --> ";
		dbg(functionName+ "Begins");
		String uidToSearch = (String) fields.get("id");
		dbg(functionName+"Incoming userId for updation is: "+uidToSearch);
		if(userRepo.existsById(uidToSearch)) {
			dbg(functionName+"User for patch exists, fetching all details.");
			User existingUser = userRepo.findById(uidToSearch).get();
			if(existingUser.getUsername().equals(requestUsername)) {
				dbg(functionName+"User before updating is: "+existingUser);
				fields.forEach((key, value) -> {
	                Field field = ReflectionUtils.findField(User.class, key);
	                field.setAccessible(true);
	                if(key.equals("password")) {
	                	String hashPass = encoder.encode((String) value);
	                	ReflectionUtils.setField(field, existingUser,hashPass);
	                }else {	                	
	                	ReflectionUtils.setField(field, existingUser, value);
	                }
				});
				dbg(functionName+"Updated record is: "+existingUser.toString());
				return userRepo.save(existingUser);
			}else {
				return "You are not a Authorized user to update this account. Please login in with the account you want to update";
			}
		}else {
			return "User not found for updation. Please check the entered userId";
		}
	}

	
	// This method will return user if either username or id is provided to the parameter
	@Override
	public Object findUserByUsername(String username) {
		String functionName = "findUserByUsername() --> ";
		dbg(functionName+ "Begins");
		dbg(functionName+"Incoming id or username for fetching is: "+username);
		try {
			boolean attempt1 = true;
			User user = null;
			try {
				user = userRepo.findUser(username);
			} catch(Exception e) {
				dbg(functionName+"Failed to fetch by username: " + e.getMessage());
				attempt1 = false;
			}
			if(attempt1 == false || user == null) {
				user = userRepo.findById(username).get();
				dbg(functionName+"Successfully fetched user with id: "+username);
			}
			else {
				dbg(functionName+"Successfully fetched user with username: "+username);
			}
			
			return user;
		}catch(Exception e) {
			dbg(functionName+"Exception -> " +e);
			return e.getMessage();
		}
	}

	@Override
	public Object login(Map<String, Object> userData) {
		String functionName = "login() --> ";
		dbg(functionName+ "Begins");
		dbg(functionName+"Incoming id or username for login is: "+ (String) userData.get("username"));
		dbg(functionName+"If request has come till here it means the entered credentials were correct.");
		return true;
	}
	

}
