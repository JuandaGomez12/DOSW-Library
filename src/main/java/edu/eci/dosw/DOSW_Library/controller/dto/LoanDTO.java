package edu.eci.dosw.DOSW_Library.controller.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class LoanDTO {
    private String id;
    private String book;
    private String user;
    private LocalDate loanDate;
    private LocalDate returnDate;
    private String status;
}