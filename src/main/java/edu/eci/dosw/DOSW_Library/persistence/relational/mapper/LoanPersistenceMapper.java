package edu.eci.dosw.DOSW_Library.persistence.relational.mapper;

import edu.eci.dosw.DOSW_Library.core.model.Loan;
import edu.eci.dosw.DOSW_Library.persistence.relational.entity.LoanEntity;
import edu.eci.dosw.DOSW_Library.persistence.relational.entity.LoanStatusEnum;
import org.springframework.stereotype.Component;

@Component
public class LoanPersistenceMapper {

    public LoanEntity toEntity(Loan loan) {
        LoanEntity entity = new LoanEntity();
        entity.setLoanDate(loan.getLoanDate());
        entity.setReturnDate(loan.getReturnDate());
        if (loan.getStatus() != null)
            entity.setStatus(LoanStatusEnum.valueOf(loan.getStatus()));
        return entity;
    }

    public Loan toDomain(LoanEntity entity) {
        Loan loan = new Loan();
        loan.setId(entity.getId());
        loan.setLoanDate(entity.getLoanDate());
        loan.setReturnDate(entity.getReturnDate());
        loan.setStatus(entity.getStatus().name());
        if (entity.getBook() != null)
            loan.setBook(entity.getBook().getId());
        if (entity.getUser() != null)
            loan.setUser(entity.getUser().getId());
        return loan;
    }
}
