package com.example.notes_app.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.notes_app.entity.Note;

@Service
public interface NoteService {
	
	// Fetch All Notes:
	List<Note> fetchAllNotes();
	
	// Update a Note:  PATCH Request Handling (i.e, Update an existing record)
	Note patchNote(Map<String, Object> fields);
	
	// Delete a Note:
	String deleteNote(String id);
	
	// Post a new Note:
	Note postNote(Note note);

}
