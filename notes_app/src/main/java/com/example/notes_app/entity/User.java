package com.example.notes_app.entity;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@Entity
@Table(name="users")
public class User {
	
	@Id
	@UuidGenerator 
	private String id;
	
	@Column(name="username")
	private String username;
	
	@Column(name="email")
	private String email;
	
	@Column(name="password_hash")
	private String password;

	public String getId() {
		return id;
	}

	public void setId(String uid) {
		this.id = uid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "User [\nuid=" + id + ", \nusername=" + username + ", \nemail=" + email + ", \npassword=" + password + "\n]";
	}
	
	

}
