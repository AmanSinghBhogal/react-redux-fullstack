package com.example.notes_app.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.notes_app.entity.User;
import com.example.notes_app.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/users")
public class UserControllerImpl implements UserController {
	
	@Autowired
	private UserService userService;
	
	private void dbg(String msg) {
		System.out.println("UserControllerImpl --> "+msg);
	}

	@GetMapping("/checkUsername")
	@Override
	public ResponseEntity<Object> checkUsernameInDB(@RequestParam(required=false) String username) {
		dbg("/users/checkUsername Invoked");
		String functionName = "checkUsernameInDB() --> ";
		Map<String, Object> response = new HashMap<>();
		response.put("respondedAt", LocalDateTime.now());
		try {
			dbg(functionName + "Begins");
			dbg(functionName+"Incoming username: "+username);
			Map<String, Object> result = new HashMap<>();
			Map<String, Object> isAlreadyPresent = new HashMap<>();
			if(userService.checkUserIdAvailablity(username)) {
				dbg(functionName + "Returning True");
				isAlreadyPresent.put("alreadyPresent", true);
				result.put("data", isAlreadyPresent);
				response.put("response", result);
				response.put("status", "Success");
				return new ResponseEntity<>(response,HttpStatus.OK);
			}
			else {
				dbg(functionName + "Returning False");
				isAlreadyPresent.put("alreadyPresent", false);
				result.put("data", isAlreadyPresent);
				response.put("response", result);
				response.put("status", "Success");
				return new ResponseEntity<>(response ,HttpStatus.OK);
			}
			
		}catch(Exception e) {
			dbg(functionName + "Exception while checking username: "+e);
			response.put("status", "Failure");
			response.put("failureMsg", e.getMessage());
			return new ResponseEntity<>(response ,HttpStatus.BAD_REQUEST);
		}
	}

	// Only user signed in as admin can see all Users
	@GetMapping
	@Override
	public ResponseEntity<Object> fetchAllUsers(@AuthenticationPrincipal UserDetails userDetails) {
		dbg("/users Invoked");
		String functionName = "fetchAllUsers() --> ";
		dbg(functionName+"Begins");
		dbg(functionName+"UserName: "+userDetails.getUsername());
		Map<String, Object> response = new HashMap<>();
		response.put("respondedAt", LocalDateTime.now());
		if(userDetails.getUsername().equals("admin")) {
			try {
				dbg(functionName+"Sending call to service layer...");
				Map<String, Object> result = new HashMap<>();
				result.put("data", userService.findAllUsers());
				response.put("response", result);
				response.put("status", "Success");
				dbg(functionName+"Response Successfully created.");
				return new ResponseEntity<>(response, HttpStatus.OK);
			}catch(Exception e) {
				dbg(functionName + "Exception while checking username: "+e);
				response.put("status", "Failure");
				response.put("failureMsg", e.getMessage());
				return null;
			}
		} else {
			response.put("response", null);
			response.put("status","Failure");
			response.put("failureMsg","Unauthorized! Only admin can see all users");
			return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
		}
	}

	@PostMapping("/register")
	@Override
	public ResponseEntity<Object> registerUser(@RequestBody User user) {
		dbg("/users Invoked");
		String functionName = "registerUser() --> ";
		dbg(functionName+"Begins");
		Map<String, Object> response = new HashMap<>();
		response.put("respondedAt", LocalDateTime.now());
		Map<String, Object> result = new HashMap<>();
		Object saveResult = new Object();
		try {
			dbg(functionName+"Sending call to service layer...");
			saveResult = userService.postUser(user);
			if(saveResult.getClass() == User.class)
				result.put("data", saveResult);
			else
				result.put("data", null);
			response.put("response", result);
			if(result.get("data") != null) {
				response.put("status", "Success");				
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
		}catch(Exception e) {
			dbg(functionName + "Exception while posting user: "+e);
			response.put("status", "Failure");
			response.put("failureMsg", e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		response.put("status", "Failure");
		response.put("failureMsg", saveResult);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	// A particular user can only delete its own account while a admin can delete anyone's account.
	@DeleteMapping
	@Override
	public ResponseEntity<Object> deleteUser(@AuthenticationPrincipal UserDetails userDetails,@RequestParam String Id) {
		dbg("/users Invoked");
		String functionName = "deleteUser() --> ";
		dbg(functionName+"Begins");
		dbg(functionName+"Incoming request is from username: "+userDetails.getUsername());
		Map<String, Object> response = new HashMap<>();
		response.put("respondedAt", LocalDateTime.now());
		Map<String, Object> result = new HashMap<>();
		Object userResult = userService.findUserByUsername(Id);
		if(userResult.getClass() == User.class) {
			User t = (User) userResult;
			dbg(functionName+"Fetched User is: "+t.toString());
			if(t.getUsername().equals(userDetails.getUsername()) || userDetails.getUsername().equals("admin")) {
				try {
					dbg(functionName+"Sending call to service layer...");
					String deleteResult = userService.deleteUserByUid(Id);
					if(deleteResult == Id) {
						dbg(functionName+"Successfully Deleted user with userId: "+Id);
						Map<String,Object> data = new HashMap<>();
						data.put("Id", deleteResult);
						result.put("data", data);
						response.put("response", result);
						response.put("status", "Success");
						return new ResponseEntity<>(response, HttpStatus.OK);
					} else {
						response.put("response", null);
						response.put("status", "Failure");
						response.put("failureMsg", deleteResult);
						return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
					}
				}catch(Exception e) {
					dbg(functionName + "Exception while deleting user: "+e);
					response.put("response", null);
					response.put("status", "Failure");
					response.put("failureMsg", e.getMessage());
					return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
				}
			}else {
				response.put("response", null);
				response.put("status", "Failure");
				response.put("failureMsg","You are not Authorized to delete this user. Please contact Admin.");
				return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
			}
			
		}else {
			response.put("response", null);
			response.put("status", "Failure");
			response.put("failureMsg","The user you want to delete does not exist.");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		
	}

	// Only the user who owns the account can update it.
	@PatchMapping
	@Override
	public ResponseEntity<Object> updateUser(@AuthenticationPrincipal UserDetails userDetails,@RequestBody Map<String, Object> fields) {
		dbg("/users Invoked");
		String functionName = "updateUser() --> ";
		dbg(functionName+"Begins");
		dbg(functionName+"Incoming request is from username: "+userDetails.getUsername());
		Map<String, Object> response = new HashMap<>();
		response.put("respondedAt", LocalDateTime.now());
		Map<String, Object> result = new HashMap<>();
		try {
			dbg(functionName+"Sending call to service layer...");
			Object serviceResult = userService.patchUser(fields, userDetails.getUsername());
			if(serviceResult.getClass() == User.class) {
				result.put("data", serviceResult);
				response.put("response", result);
				response.put("status", "Success");
				return new ResponseEntity<>(response, HttpStatus.OK);
			}else {
				String t = (String) serviceResult;
				response.put("response", null);
				response.put("status", "Failure");
				response.put("failureMsg",t);
				if(t.equals("You are not a Authorized user to update this account. Please login in with the account you want to update")) {
					return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
				}else {
					return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
				}
			}
		} catch(Exception e) {
			dbg(functionName + "Exception while updating user: "+e);
			response.put("response", null);
			response.put("status", "Failure");
			response.put("failureMsg", e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping
	@Override
	public ResponseEntity<Object> login(Map<String, Object> userData) {
		dbg("/users Invoked");
		String functionName = "login() --> ";
		dbg(functionName+"Begins");
		Map<String, Object> response = new HashMap<>();
		response.put("respondedAt", LocalDateTime.now());
		try {
			dbg(functionName+"Sending call to service layer...");
			boolean result = (boolean) userService.login(userData);
			response.put("response", "Success");
			response.put("status", "Success");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch(Exception e) {
			dbg(functionName + "Exception while updating user: "+e);
			response.put("response", null);
			response.put("status", "Failure");
			response.put("failureMsg", e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
	}

}
