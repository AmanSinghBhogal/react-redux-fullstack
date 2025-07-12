package com.example.notes_app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.notes_app.entity.User;
import com.example.notes_app.entity.UserPrincipal;
import com.example.notes_app.repository.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {
	
	@Autowired
	UserRepository userRepo;
	
	private void dbg(String msg) {
		System.out.println("MyUserDetailsService --> "+msg);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		String functionName = "loadUserByUsername --> ";
		dbg(functionName+"Begins");
		try {
			dbg(functionName+"Trying to fetch the user details for username: "+username);
			User user = userRepo.findUser(username);
			if (user == null) {
				dbg(functionName+"User Not Found");
				throw new UsernameNotFoundException("Username Not found");
			}
			return new UserPrincipal(user);
			
		}catch(Exception e) {
			dbg(functionName+"Exception -> " +e);
			return null;
		}
	}

}
