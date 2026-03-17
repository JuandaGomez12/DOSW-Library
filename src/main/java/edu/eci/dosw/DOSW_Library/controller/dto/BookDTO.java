package edu.eci.dosw.DOSW_Library.controller.dto;

import lombok.Data;

@Data
public class BookDTO {
    private String title;
    private String author;
    private String id;
    private boolean available;
    private int copies;
}