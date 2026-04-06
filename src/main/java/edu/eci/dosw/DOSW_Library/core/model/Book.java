package edu.eci.dosw.DOSW_Library.core.model;

import lombok.Data;

@Data
public class Book {
    private String id;
    private String title;
    private String author;
    private int totalCopies;
    private int availableCopies;
}
