package com.example.notes_app.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.notes_app.entity.Note;

@Service
public interface NoteService {
	
	// Fetch All Notes:
	Object fetchAllNotes();
	
	// Update a Note:  PATCH Request Handling (i.e, Update an existing record)
	Object patchNote(Map<String, Object> fields, String username);
	
	// Delete a Note:
	Object deleteNote(String id, String username);
	
	// Post a new Note:
	Object postNote(Note note, String username);

}
