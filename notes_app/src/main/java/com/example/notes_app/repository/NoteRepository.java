package com.example.notes_app.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.notes_app.entity.Note;

@Repository
public interface NoteRepository extends CrudRepository<Note, String>{

}
