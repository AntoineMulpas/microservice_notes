package com.medilabo.microservice_notes.services;

import com.medilabo.microservice_notes.exceptions.NoteNotFoundException;
import com.medilabo.microservice_notes.models.Note;
import com.medilabo.microservice_notes.repositories.NoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@DataMongoTest(includeFilters = @ComponentScan.Filter(Service.class))
@ExtendWith(SpringExtension.class)
class NoteServiceTest {

    @Autowired
    private NoteService underTest;

    @Autowired
    private NoteRepository noteRepository;

    private Note note;

    @BeforeEach
    void setUp() {
        noteRepository.deleteAll();
        note = new Note("123GYU2331", "Diabetes.", "12");
    }

    @Test
    void addNote() {
        //when(noteRepository.insert(note)).thenReturn(note);
        Note insertedNote = underTest.addNote(note);
        assertEquals(note.getNote(), insertedNote.getNote());
        assertEquals(note.getPatient(), insertedNote.getPatient());
    }

    @Test
    void findNoteById() throws NoteNotFoundException {
        noteRepository.save(note);
        Note foundNote = underTest.findNoteById(note.getId());
        assertEquals(note.getNote(), foundNote.getNote());
        assertEquals(note.getPatient(), foundNote.getPatient());
    }

    @Test
    void findNoteByIdShouldThrowIfNoteDoesNotExist() {
        assertThrows(NoteNotFoundException.class, () -> underTest.findNoteById("1234v"));
    }

    @Test
    void findAllNote() {
        noteRepository.save(note);
        int size = underTest.findAllNote().size();
        assertEquals(1, size);
    }

    @Test
    void deleteNoteById() {
        noteRepository.save(note);
        assertDoesNotThrow(() -> underTest.deleteNoteById(note.getId()));
    }

    @Test
    void deleteNoteByIdShouldThrowIfNoteDoesNotExist() {
        assertThrows(NoteNotFoundException.class, () -> underTest.deleteNoteById("23HIU21H"));
    }

    @Test
    void updateNoteById() throws NoteNotFoundException {
        noteRepository.save(note);
        Note noteToUpdate = note;
        String update_note = "Update note";
        noteToUpdate.setNote(update_note);
        Note updatedNote = underTest.updateNoteById(note.getId(), noteToUpdate);
        assertEquals(updatedNote.getNote(), noteToUpdate.getNote());
    }
}