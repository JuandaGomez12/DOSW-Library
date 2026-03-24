package edu.eci.dosw.DOSW_Library.controller.mapper;

import edu.eci.dosw.DOSW_Library.controller.dto.LoanDTO;
import edu.eci.dosw.DOSW_Library.core.model.Loan;
import org.springframework.stereotype.Component;

@Component
public class LoanMapper {

    public Loan toModel(LoanDTO dto) {
        Loan loan = new Loan();
        loan.setBook(dto.getBook());
        loan.setUser(dto.getUser());
        loan.setLoanDate(dto.getLoanDate());
        loan.setStatus(dto.getStatus());
        loan.setReturnDate(dto.getReturnDate());
        return loan;
    }

    public LoanDTO toDTO(Loan loan) {
        LoanDTO dto = new LoanDTO();
        dto.setBook(loan.getBook());
        dto.setUser(loan.getUser());
        dto.setLoanDate(loan.getLoanDate());
        dto.setStatus(loan.getStatus());
        dto.setReturnDate(loan.getReturnDate());
        return dto;
    }
}