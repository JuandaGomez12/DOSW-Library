package edu.eci.dosw.DOSW_Library.core.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Loan {
    private String id;
    private String book;
    private String user;
    private LocalDate loanDate;
    private LocalDate returnDate;
    private String status;
}