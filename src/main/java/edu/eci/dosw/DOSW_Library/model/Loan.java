package edu.eci.dosw.DOSW_Library.model;

import lombok.Data;

@Data
public class Loan { 
    String book;
    String user;
    String loanDate;
    String status;
    String returnDate;
}