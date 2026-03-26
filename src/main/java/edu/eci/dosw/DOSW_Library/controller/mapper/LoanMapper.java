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
        loan.setReturnDate(dto.getReturnDate());
        loan.setStatus(dto.getStatus());
        return loan;
    }

    public LoanDTO toDTO(Loan loan) {
        LoanDTO dto = new LoanDTO();
        dto.setId(loan.getId());
        dto.setBook(loan.getBook());
        dto.setUser(loan.getUser());
        dto.setLoanDate(loan.getLoanDate());
        dto.setReturnDate(loan.getReturnDate());
        dto.setStatus(loan.getStatus());
        return dto;
    }
}