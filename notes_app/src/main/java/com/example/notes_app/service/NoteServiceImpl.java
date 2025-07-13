package com.example.notes_app.service;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import com.example.notes_app.entity.Note;
import com.example.notes_app.entity.User;
import com.example.notes_app.repository.NoteRepository;
import com.example.notes_app.repository.UserRepository;

@Service
public class NoteServiceImpl implements NoteService {
	
	@Autowired
	NoteRepository noteRepo;
	
	@Autowired
	UserRepository userRepo;
	
	private void dbg(String msg) {
		System.out.println("NoteServiceImpl --> "+msg);
	}

	@Override
	public Object fetchAllNotes() {
		String functionName = "fetchAllNotes() --> ";
		dbg(functionName+"Begin.");
		try {
			Iterable<Note> Inotes = noteRepo.findAll();
			dbg(functionName+"Fetch Successfully Done.");
			List<Note> notes = new ArrayList<>();
			for(Note n: Inotes)
				notes.add(n);
			return notes;
		}catch(Exception e) {
			dbg(functionName+"Exception -> " +e);
			return e.getMessage();
		}
	}

	// A user can update only its own note.
	@Override
	public Object patchNote(Map<String, Object> fields, String username) {
		String functionName = "patchNote() --> ";
		dbg(functionName+"Begins.");
		dbg(functionName+"Incoming request is from user: "+username);
		try {
			String noteId = (String) fields.get("id");
			if(noteId != null && noteRepo.existsById(noteId)) {
				dbg(functionName+"Fetching existing note details...");
				Note existingNote = noteRepo.findById(noteId).get();
				dbg(functionName+"Note before updation: "+existingNote.toString());
				dbg(functionName+"Checking if current user is authorized to modify this note...");
				String NoteUsername = (String) userRepo.findByUserId(existingNote.getUser_id());
				dbg(functionName+"Note Username:: "+NoteUsername);
				if(NoteUsername.equals(username)) {
					fields.forEach((key, value) -> {
						dbg(functionName+"Updating key:: "+key);
		                Field field = ReflectionUtils.findField(Note.class, key);
		                field.setAccessible(true);
		                ReflectionUtils.setField(field, existingNote, value);
					});
					existingNote.setUpdated_at(Timestamp.valueOf(LocalDateTime.now()));
					dbg(functionName+"Updated note before save: "+existingNote.toString());
					return noteRepo.save(existingNote);
				}else {
					dbg(functionName+"User is not authorized to modify this record..");
					return "User is Not Authorized to update this note.";
				}
			}else {
				dbg(functionName+"No Note found with given id.");
				return "Invalid Note Id for updation.";
			}
		}catch(Exception e) {
			dbg(functionName+"Exception -> " +e);
			return e.getMessage();
		}
	}

	// a user can only delete its own note. while admin can delete any note.
	@Override
	public Object deleteNote(String id, String username) {
		String functionName = "deleteNote() --> ";
		dbg(functionName+"Begins.");
		dbg(functionName+"Incoming request is from user: "+username);
		try {
			dbg(functionName+"Note id incoming for deletion is: "+id);
			dbg(functionName+"Checking if note exists with this id...");
			if(noteRepo.existsById(id)) {
				dbg(functionName+"Note exists.");
				dbg(functionName+"Fetching note details for finding its Owner.");
				Note n = noteRepo.findById(id).get();
				dbg(functionName+"Checking if the user is authorized to delete this note...");
				String NoteUsername = (String) userRepo.findByUserId(n.getUser_id());
				if(NoteUsername.equals(username) || username.equals("admin")) {
					noteRepo.delete(n);
					dbg(functionName+"Successfully deleted the note.");
					return "Success";
				}else {
					dbg(functionName+"Nor a admin user neither an authorized user to delete this note.");
					return "User is Unauthorized to delete this record.";
				}
			}else {
				dbg(functionName+"No note found with given id.");
				return "No note found with given id. Invalid Note id for deletion";
			}
		}catch(Exception e) {
			dbg(functionName+"Exception -> " +e);
			return e.getMessage();
		}
	}

	@Override
	public Object postNote(Note note, String username) {
		String functionName = "postNote() --> ";
		dbg(functionName+"Begin");
		try {
			dbg(functionName+"Incoming note for post is: "+note.toString());
			dbg(functionName+"fetching user details for user id: "+note.getUser_id());
			String db_username = userRepo.findByUserId(note.getUser_id());
			if(db_username == null) {
				return "Invalid user for creating the note. Please check user id";
			}else if(!db_username.equals(username)) {
				return "User is not Authorized to create this record. Please check the user you are logged in with";
			}else {
				note.setCreated_at(Timestamp.valueOf(LocalDateTime.now()));
				note.setUpdated_at(null);
				dbg(functionName+"Created_at:: "+note.getCreated_at());
				return noteRepo.save(note);
			}
		}catch(Exception e) {
			dbg(functionName+"Exception -> " +e);
			return e.getMessage();
		}
	}

}
