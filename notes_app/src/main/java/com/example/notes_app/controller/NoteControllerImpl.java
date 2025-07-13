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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.notes_app.entity.Note;
import com.example.notes_app.service.NoteService;


@RestController
@RequestMapping("/notes")
public class NoteControllerImpl implements NoteController{
	
	@Autowired
	NoteService noteService;
	
	private void dbg(String msg) {
		System.out.println("NoteControllerImpl --> "+msg);
	}
	
	@GetMapping("/fetchAll")
	@Override
	public ResponseEntity<Object> fetchNotes() {
		dbg("/notes/fetchAll Invoked");
		String functionName = "fetchNotes() --> ";
		dbg(functionName+"Begin.");
		Map<String, Object> response = new HashMap<>();
		response.put("respondedAt", LocalDateTime.now());
		Map<String, Object> result = new HashMap<>();
		try {
			dbg(functionName+"Sending call to service layer...");
			result.put("data", noteService.fetchAllNotes());
			response.put("response", result);
			response.put("status", "Success");
			dbg(functionName+"Response Successfully created.");
			return new ResponseEntity<>(response, HttpStatus.OK);
		}catch(Exception e) {
			dbg(functionName + "Exception while fetching all notes: "+e);
			response.put("response", null);
			response.put("status", "Failure");
			response.put("failureMsg", e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping
	@Override
	public ResponseEntity<Object> postNote(@RequestBody Note note,@AuthenticationPrincipal UserDetails userDetails) {
		dbg("/notes Invoked");
		String functionName = "postNote() --> ";
		dbg(functionName+"Begin.");
		dbg(functionName+"Incoming request is from username: "+userDetails.getUsername());
		Map<String, Object> response = new HashMap<>();
		response.put("respondedAt", LocalDateTime.now());
		Map<String, Object> result = new HashMap<>();
		try {
			dbg(functionName+"Sending call to service layer...");
			Object noteResult = noteService.postNote(note, userDetails.getUsername());
			if(noteResult.getClass() == Note.class) {
				dbg(functionName+"Note Created Successfully.");
				result.put("data", noteResult);
				response.put("response", result);
				response.put("status", "Success");
				return new ResponseEntity<>(response, HttpStatus.OK);
			}else {
				String t = (String) noteResult;
				dbg(functionName+"Post of note failed with msg: "+t);
				response.put("response", null);
				response.put("status", "Failure");
				response.put("failureMsg",noteResult);
				if(t.equals("User is not Authorized to create this record. Please check the user you are logged in with"))
					return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
				else
					return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
			}
		}catch(Exception e) {
			dbg(functionName + "Exception while posting note: "+e);
			response.put("response", null);
			response.put("status", "Failure");
			response.put("failureMsg", e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
	}

	@PatchMapping
	@Override
	public ResponseEntity<Object> updateNote(@RequestBody Map<String, Object> fields,@AuthenticationPrincipal UserDetails userDetails) {
		dbg("/notes Invoked");
		String functionName = "updateNote() --> ";
		dbg(functionName+"Begin.");
		dbg(functionName+"Incoming request is from username: "+userDetails.getUsername());
		Map<String, Object> response = new HashMap<>();
		response.put("respondedAt", LocalDateTime.now());
		Map<String, Object> result = new HashMap<>();
		try {
			dbg(functionName+"Sending call to service layer...");
			Object noteResult = noteService.patchNote(fields, userDetails.getUsername());
			if(noteResult.getClass() == Note.class) {
				dbg(functionName+"Note Updated Successfully.");
				result.put("data", noteResult);
				response.put("response", result);
				response.put("status", "Success");
				return new ResponseEntity<>(response, HttpStatus.OK);
			}else {
				String t = (String) noteResult;
				dbg(functionName+"Updation of note failed with msg: "+t);
				response.put("response", null);
				response.put("status", "Failure");
				response.put("failureMsg",t);
				if(t.equals("User is Not Authorized to update this note."))
					return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
				else
					return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
			}
			
		}catch(Exception e) {
			dbg(functionName + "Exception while updating note: "+e);
			response.put("response", null);
			response.put("status", "Failure");
			response.put("failureMsg", e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
	}

	
	@DeleteMapping
	@Override
	public ResponseEntity<Object> deleteNote(@RequestParam String id,@AuthenticationPrincipal UserDetails userDetails) {
		dbg("/notes Invoked");
		String functionName = "deleteNote() --> ";
		dbg(functionName+"Begin.");
		dbg(functionName+"Incoming request is from username: "+userDetails.getUsername());
		Map<String, Object> response = new HashMap<>();
		response.put("respondedAt", LocalDateTime.now());
		Map<String, Object> result = new HashMap<>();
		try {
			dbg(functionName+"Sending call to service layer...");
			String noteResult = (String) noteService.deleteNote(id, userDetails.getUsername());
			if(noteResult.equals("Success")) {
				dbg(functionName+"Note deleted Successfully.");
				result.put("data", "Note deleted Successfully.");
				response.put("response", result);
				response.put("status", "Success");
				return new ResponseEntity<>(response, HttpStatus.OK);
			}else {
				dbg(functionName+"Deletion of note failed with msg: "+noteResult);
				response.put("response", null);
				response.put("status", "Failure");
				response.put("failureMsg",noteResult);
				if(noteResult.equals("User is Unauthorized to delete this record.")) {
					return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
				} else if(noteResult.equals("No note found with given id. Invalid Note id for deletion")) {
					return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
				}else {
					return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
				}
			}
		}catch(Exception e) {
			dbg(functionName + "Exception while deleting note: "+e);
			response.put("response", null);
			response.put("status", "Failure");
			response.put("failureMsg", e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
	}

	

}
