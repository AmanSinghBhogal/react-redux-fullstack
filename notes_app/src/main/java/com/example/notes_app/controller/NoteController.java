package com.example.notes_app.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RestController;

import com.example.notes_app.entity.Note;

@RestController
public interface NoteController {

	// Fetch all Notes
	ResponseEntity<Object> fetchNotes();

	// Post a new Note. A note can only be created it the signed in user and the user in the note body matches.
	ResponseEntity<Object> postNote(Note note, UserDetails userDetails);
	
	// Update a note: only user who created that note can update it.
	ResponseEntity<Object> updateNote(Map<String, Object> fields, UserDetails userDetails);
	
	// Delete a note: only admin or the owner of a note can delete it.
	ResponseEntity<Object> deleteNote(String id, UserDetails userDetails);
	
}
