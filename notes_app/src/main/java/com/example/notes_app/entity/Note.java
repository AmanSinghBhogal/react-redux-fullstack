package com.example.notes_app.entity;

import java.sql.Timestamp;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="notes")
public class Note {
	
	@Id
	@UuidGenerator
	private String id;
	
	@Column
	private String user_id;
	
	@Column
	private String title;
	
	@Column
	private String content;
	
	@Column
	private String color;
	
	@Column
	private Timestamp created_at;
	
	@Column
	private Timestamp updated_at;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Timestamp getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Timestamp created_at) {
		this.created_at = created_at;
	}

	public Timestamp getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(Timestamp updated_at) {
		this.updated_at = updated_at;
	}

	@Override
	public String toString() {
		return "Note [\nid=" + id + ", \nuser_id=" + user_id + ", \ntitle=" + title + ", \ncontent=" + content + ", \ncolor="
				+ color + ", \ncreated_at=" + created_at + ", \nupdated_at=" + updated_at + "\n]";
	}
	

}
