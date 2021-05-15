package com.example.easynotes.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.easynotes.exception.ResourceNotFoundException;
import com.example.easynotes.model.Note;
import com.example.easynotes.repository.NoteRepository;

@RestController
@RequestMapping("/api")
public class NoteController {

	
	@Autowired
	NoteRepository noteRepo ;
	
	//GET ALL NOTES
	@GetMapping("/notes")
	public List <Note> getAllNotes(){
		return noteRepo.findAll();
	}
	
	//CREATE A NEW NOTE
	
	@PostMapping("/notes")
	public Note createNote(@Valid @RequestBody Note note){
		return noteRepo.save(note);
	}
	
	//GET SINGLE NOTE
	
	@GetMapping("/notes/{id}")
	public Note getNoteById(@PathVariable(value = "id") Long noteId){
		return noteRepo.findById(noteId).orElseThrow(()-> new ResourceNotFoundException("Note","id",noteId));
	}
	
	//UPDATE A NOTE
	@PutMapping("/notes/{id}")
	public Note updateNote(@PathVariable(value = "id") Long noteId,
	                                        @Valid @RequestBody Note noteDetails) {

	    Note note = noteRepo.findById(noteId)
	            .orElseThrow(() -> new ResourceNotFoundException("Note", "id", noteId));

	    note.setTitle(noteDetails.getTitle());
	    note.setContent(noteDetails.getContent());

	    Note updatedNote = noteRepo.save(note);
	    return updatedNote;
	}
	
	//DELETE A NOTE
	@DeleteMapping("/notes/{id}")
	public ResponseEntity<?> deleteNote(@PathVariable(value = "id") Long noteId) {
	    Note note = noteRepo.findById(noteId)
	            .orElseThrow(() -> new ResourceNotFoundException("Note", "id", noteId));

	    noteRepo.delete(note);

	    return ResponseEntity.ok().build();
	}
	
}
