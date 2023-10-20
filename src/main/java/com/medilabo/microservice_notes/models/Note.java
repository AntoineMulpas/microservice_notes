package com.medilabo.microservice_notes.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "notes")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Note {

    @Id
    private String id;
    private String note;
    private String patient;

    public Note(String note, String patient) {
        this.note = note;
        this.patient = patient;
    }
}
