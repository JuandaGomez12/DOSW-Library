package edu.eci.dosw.DOSW_Library.controller.dto;

import lombok.Data;

@Data
public class LoanDTO {
    private String book;
    private String user;
    private String loanDate;
    private String status;
    private String returnDate;
}