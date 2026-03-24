package edu.eci.dosw.DOSW_Library.core.model;

import lombok.Data;

@Data
public class Loan {
    private String book;
    private String user;
    private String loanDate;
    private String status;
    private String returnDate;
}